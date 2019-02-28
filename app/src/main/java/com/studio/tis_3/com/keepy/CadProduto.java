package com.studio.tis_3.com.keepy;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



public class CadProduto extends AppCompatActivity {


  private   EditText campoNome, campoValor;
  private  Button btn_cadastrar_cadProduto;
    AlertDialog caixaDialgo;
    AlertDialog.Builder criador;


    BancoDados db = new BancoDados(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_produto);
        campoNome = findViewById(R.id.campo_nome_cadProduto);
        campoValor = findViewById(R.id.campo_Valor_cadProduto);

        campoNome.addTextChangedListener(cadastrarTexto);
        campoValor.addTextChangedListener(cadastrarTexto);
        btn_cadastrar_cadProduto = findViewById(R.id.botao_Cadastrar_cadProd);
        Button btn_cancelar_cadProduto = findViewById(R.id.btn_Cancelar_cadProd);

        btn_cadastrar_cadProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (campoNome.getText().toString().isEmpty()) {
                    campoNome.setError("O campo não pode ser vazio");
                } else if (campoValor.getText().toString().isEmpty()) {
                    campoValor.setError("O campo não pode ser vazio");
                } else {

                    criarCaixaDeDialgoCad(campoNome.getText().toString(), Float.valueOf(campoValor.getText().toString()));


                }


            }
        });
        btn_cancelar_cadProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void criarCaixaDeDialgoCad(String nome,float valor) {
        criador = new AlertDialog.Builder(this);
        criador.setTitle("Salvar Produto")
                .setMessage("Deseja salvar o produto " + nome + " com o valor de R$" + valor)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();

                    }
                })
                .setPositiveButton(R.string.cad, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        db.salvarProduto(new Produto(campoNome.getText().toString(), Float.valueOf(campoValor.getText().toString())));
                        campoNome.setText("");
                        campoValor.setText("");

                        new AlertDialog.Builder(CadProduto.this)
                                .setMessage("Produto salvo com sucesso")
                                .show();


                    }
                });

        caixaDialgo = criador.create();
        caixaDialgo.show();


    }

    private TextWatcher cadastrarTexto = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String nomeInput = campoNome.getText().toString().trim();
            String valorInput = campoValor.getText().toString().trim();

            btn_cadastrar_cadProduto.setEnabled(!nomeInput.isEmpty() && !valorInput.isEmpty());


        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    void voltarMenu(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);



    }


}