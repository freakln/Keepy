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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListarProduto extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    BancoDados db = new BancoDados(this);

    ArrayAdapter<String> adptador;
    ListView listaDeProdutos;
    ArrayList<String> arrayList;
    AlertDialog boxDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_produto);
        FloatingActionButton btn_back = findViewById(R.id.btn_ft_back);

        listarProdutosTela();
                btn_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

    }


    public void listarProdutosTela() {
        listaDeProdutos = findViewById(R.id.listViewProdutos);

        List<Produto> produto = db.listarTodosOsProdutos();


        arrayList = new ArrayList<>();

        adptador = new ArrayAdapter<>(ListarProduto.this, android.R.layout.simple_list_item_1, arrayList);
        listaDeProdutos.setAdapter(adptador);

        for (Produto p : produto) {
            arrayList.add(String.valueOf(p.getIdProduto()) + "-" + p.getNomeProduto());
            adptador.notifyDataSetChanged();


        }
        registerForContextMenu(listaDeProdutos);


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
                Toast.makeText(this, "item clicado", Toast.LENGTH_SHORT).show();
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
            caixaDeDialogoExcluirProduto(info.position);


        } else {
            return false;
        }
        return true;


    }

    public void deletarProduto(int i) {


        db.deletaProduto(i);
        listarProdutosTela();


    }


    public void caixaDeDialogoExcluirProduto(int i) {

        List<Produto> produto = db.listarTodosOsProdutos();
        final int getId = produto.get(i).getIdProduto();

        //TO DO


        AlertDialog.Builder builder = new AlertDialog.Builder(this);


        builder.setTitle("Excluir Produto");
        builder.setMessage("Deseja excluir o produto " + produto.get(i).getNomeProduto() + " com o valor de R$" + produto.get(i).getValorProduto() + " " + getId);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();

            }
        });

        builder.setPositiveButton(R.string.menuDeletar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deletarProduto(getId);


            }
        });

        boxDialog = builder.create();
        boxDialog.show();


    }

    public void alertaEditarProduto(final int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ListarProduto.this);
        final List<Produto> produtos = db.listarTodosOsProdutos();
        View mView = getLayoutInflater().inflate(R.layout.dialogo_atualizar_produto, null);

        final EditText campoNome = mView.findViewById(R.id.dlg_campo_nome);
        final EditText campoValor = mView.findViewById(R.id.dlg_campo_valorP);
        Button cad = mView.findViewById(R.id.btn_dlg_prod_ok);
        Button cancel = mView.findViewById(R.id.btn_dlg_prod_cancel);


        campoNome.setText(produtos.get(i).getNomeProduto());
        campoValor.setText(String.valueOf(produtos.get(i).getValorProduto()));
        builder.setView(mView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        cad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Produto p = new Produto(produtos.get(i).getIdProduto(),campoNome.getText().toString(),Float.valueOf(campoValor.getText().toString()));



                        db.atualizaProduto(p);

                new AlertDialog.Builder(ListarProduto.this)
                        .setMessage("Produto salvo com sucesso")
                        .show();
                listarProdutosTela();
                alertDialog.dismiss();

            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    alertDialog.dismiss();
            }
        });






    }





}
