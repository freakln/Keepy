package com.studio.tis_3.com.keepy;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.List;

public class telaPagamento extends AppCompatActivity {
    BancoDados db = new BancoDados(this);
    EditText campoNome,campoVlpago;
    TextView campoValorDevido;
    List<Cliente> clientes;

    int idDoCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_pagamento);
        campoNome = findViewById(R.id.txt_escolhe_cliente);
        campoValorDevido = findViewById(R.id.tl_pg_lb_saldoDevido);
        campoVlpago= findViewById(R.id.camp_valor_pg);
        //textwatcher para validacao
        campoVlpago.addTextChangedListener(textWatcher);
        campoNome.addTextChangedListener(textWatcher);
        campoValorDevido.addTextChangedListener(textWatcher);


        ListView listView = new ListView(this);
        clientes = db.listarTodosOsClientes();
        ArrayList<String> arrayList = new ArrayList<>();

        for (Cliente c : clientes) {
            arrayList.add(String.valueOf(c.getNomeCliente()) + " " + c.getSobreNome());


        }


        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);

        Builder builder = new Builder(telaPagamento.this);
        builder.setCancelable(true);
        builder.setView(listView);
        final AlertDialog dialog = builder.create();


        campoNome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                campoNome.setText(arrayAdapter.getItem(position));
                dialog.dismiss();

                String valor = "Saldo devido: R$" + String.valueOf(clientes.get(position).getSaldoDevido());
                campoValorDevido.setText(valor);
                idDoCliente = clientes.get(position).getIdCliente();


            }
        });

        //botoes

        Button btn_ok = findViewById(R.id.btn_tl_pg_ok);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (campoNome.getText().toString().isEmpty()) {
                    campoNome.setError("O campo não pode ser vazio");
                } else if (campoVlpago.getText().toString().isEmpty()) {
                    campoVlpago.setError("O campo não pode ser vazio");
                } else {

                    criarCaixaDeDialgoCad(campoNome.getText().toString(), Float.valueOf(campoVlpago.getText().toString()));

                }


            }
        });


    }


    public void criarCaixaDeDialgoCad(String nome, float valor) {
        {
            AlertDialog caixaDialgo;
            Builder criador;
            criador = new Builder(this);
            criador.setTitle("Cadastrar Pagamento")
                    .setMessage("Cadastar pagamento para o cliente  " + nome + " com o valor de R$" + valor)
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();

                        }
                    })
                    .setPositiveButton(R.string.cad, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            campoNome = findViewById(R.id.txt_escolhe_cliente);
                            campoValorDevido = findViewById(R.id.tl_pg_lb_saldoDevido);
                                campoVlpago = findViewById(R.id.camp_valor_pg);

                            db.salvarPagamento(idDoCliente, Float.parseFloat(campoVlpago.getText().toString()));
                            clientes= db.listarTodosOsClientes();
                            campoNome.setText("");
                            campoValorDevido.setText("");
                            campoVlpago.setText("");

                            new Builder(telaPagamento.this)
                                    .setMessage("Pagamento salvo com sucesso")
                                    .show();


                        }
                    });

            caixaDialgo = criador.create();
            caixaDialgo.show();


        }


    }

    private TextWatcher textWatcher =  new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String nome =campoNome.getText().toString();
            String sDevivo = campoValorDevido.getText().toString();
            String vPago = campoVlpago.getText().toString();

            if (nome.isEmpty() && !sDevivo.isEmpty()){

                campoValorDevido.setText("");
            }





        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };










}