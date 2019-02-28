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

public class CadastroCliente extends AppCompatActivity {

  private  EditText campoNome,campoSobreNome,campoEmail,campoDDD,campoTelefone;
    Button btn_cadastro_cliente,btn_cancelar;
    AlertDialog caixaDialgo;
    AlertDialog.Builder criador;

    BancoDados db  = new BancoDados(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente);



        campoNome =findViewById(R.id.campo_nome_cadCli);
        campoSobreNome =findViewById(R.id.campo_Snome_cadCli);
        campoEmail= findViewById(R.id.campo_mail_cadCli);
        campoDDD = findViewById(R.id.campo_ddd);
        campoTelefone= findViewById(R.id.campo_phone_cadCli);


        campoNome.addTextChangedListener(cadastrarTexto);
        campoSobreNome.addTextChangedListener(cadastrarTexto);
        campoDDD.addTextChangedListener(cadastrarTexto);
        campoEmail.addTextChangedListener(cadastrarTexto);
        campoTelefone.addTextChangedListener(cadastrarTexto);




        btn_cadastro_cliente =findViewById(R.id.botao_cadastrar_cadCli);
        btn_cancelar= findViewById(R.id.botao_cancelar_cadCli);

        btn_cadastro_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                criarCaixaDeDialgoCad(campoNome.getText().toString(),
                        campoSobreNome.getText().toString(),
                        campoEmail.getText().toString(),
                        campoDDD.getText().toString()+campoTelefone.getText().toString());








            }
        });



        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();



            }
        });




    }

    public void criarCaixaDeDialgoCad(String nome,String sobrenome,String email ,String telefone) {
        criador = new AlertDialog.Builder(this);
        criador.setTitle("Salvar Produto")
                .setMessage("Deseja salvar o Cliente " + nome + " "+sobrenome+"  Email: "
                        +email+"Telefone: "+telefone)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();

                    }
                })
                .setPositiveButton(R.string.cad, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        db.salvarCliente(new Cliente(
                                campoNome.getText().toString(),
                                campoSobreNome.getText().toString(),
                                campoEmail.getText().toString(),
                                "("+campoDDD.getText().toString()+")"+campoTelefone.getText().toString()




                        ));
                        campoNome.setText("");
                        campoSobreNome.setText("");
                        campoDDD.setText("");
                        campoEmail.setText("");
                        campoTelefone.setText("");

                        new AlertDialog.Builder(CadastroCliente.this)
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
            String snomeInput = campoSobreNome.getText().toString().trim();
            String emailInput =campoEmail.getText().toString().trim();
            String dddInput =  campoDDD.getText().toString().trim();
            String telefoneInput = campoTelefone.getText().toString().trim();



            btn_cadastro_cliente.setEnabled(!nomeInput.isEmpty()&&!snomeInput.isEmpty()&&!emailInput.isEmpty()&&!dddInput.isEmpty()&&!telefoneInput.isEmpty());


        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    void voltarMenu(){
        Intent  intent = new Intent(this,MainActivity.class);
        startActivity(intent);



    }






}
