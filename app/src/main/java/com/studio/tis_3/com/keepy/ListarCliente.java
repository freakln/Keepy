package com.studio.tis_3.com.keepy;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListarCliente extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    BancoDados db = new BancoDados(this);

    ArrayAdapter<String> adptador;
    ListView listaDeCliente;
    ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_cliente);

        FloatingActionButton btn_back =findViewById(R.id.btn_ft_back_cliente);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        listarClientesTela();


    }


    public void listarClientesTela() {
        listaDeCliente = findViewById(R.id.listViewClientes);

        List<Cliente> clientes = db.listarTodosOsClientes();


        arrayList = new ArrayList<>();

        adptador = new ArrayAdapter<>(ListarCliente.this, android.R.layout.simple_list_item_1, arrayList);
        listaDeCliente.setAdapter(adptador);

        for (Cliente c : clientes) {
            arrayList.add(String.valueOf(c.getNomeCliente()) + " " + c.getSobreNome());
            adptador.notifyDataSetChanged();


        }
        registerForContextMenu(listaDeCliente);


    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.popup_menu, menu);


    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:

                return true;
            case R.id.item2:
                Toast.makeText(this, "item clicado", Toast.LENGTH_SHORT).show();
                return true;
        }

        return false;
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if (item.getItemId() == R.id.item1) {


            alertaEditarProduto(info.position);


        } else if (item.getItemId() == R.id.item2) {
            deletarProduto(info.position);

        } else {
            return false;
        }
        return true;


    }

    public void deletarProduto(int i) {
        final int getI = i;
        final List<Cliente> clientes = db.listarTodosOsClientes();


        AlertDialog.Builder criador = new AlertDialog.Builder(this);
        criador.setTitle("Excluir o Cliente")
                .setMessage("Deseja salvar o Cliente " + clientes.get(i).getNomeCliente())
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();

                    }
                })
                .setPositiveButton(R.string.menuDeletar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        db.apagarCliente(clientes.get(getI).getIdCliente());
                        listarClientesTela();
                    }
                });

        AlertDialog caixaDialgo = criador.create();
        caixaDialgo.show();


    }

    public void alertaEditarProduto(int i) {
        final int getI = i;
        AlertDialog.Builder builder = new AlertDialog.Builder(ListarCliente.this);
        final List<Cliente> clientes = db.listarTodosOsClientes();
        View mView = getLayoutInflater().inflate(R.layout.dlg_att_cliente, null);

        final EditText campoNome = mView.findViewById(R.id.campo_nome_dlg_att_cliente);
        final EditText campoSNome = mView.findViewById(R.id.campo_snome_dlg_att_cliente);
        final EditText campoMail = mView.findViewById(R.id.campo_email_dlg_att_cliente);
        final EditText campoTelefone = mView.findViewById(R.id.campo_telefone_dlg_att_cliente);
        campoNome.setText(clientes.get(i).getNomeCliente());
        campoSNome.setText(clientes.get(i).getSobreNome());
        campoMail.setText(clientes.get(i).getEmail());
        campoTelefone.setText(clientes.get(i).getTelefone());

        builder.setView(mView);


        builder.setNegativeButton(R.string.cancel, null);
        builder.setPositiveButton(R.string.atualizar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Cliente c = new Cliente(
                        clientes.get(getI).getIdCliente(),
                        campoNome.getText().toString(),
                        campoSNome.getText().toString(),
                        campoMail.getText().toString(),
                        campoTelefone.getText().toString(),
                        clientes.get(getI).getSaldoDevido(),
                        clientes.get(getI).getStatus());


                db.atualizaCliente(c);
                listarClientesTela();

            }


        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }


}



