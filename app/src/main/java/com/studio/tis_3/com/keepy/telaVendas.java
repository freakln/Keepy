package com.studio.tis_3.com.keepy;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class telaVendas extends AppCompatActivity {
    BancoDados db = new BancoDados(this);
    float somatorio = 0;
    List<Cliente> clientes;
    List<Produto> produtos;
    List<String> quantidade;
    ListView listViewPedidos;

    ArrayList<String> lista_pedidos_array;
    ArrayList<String> listaQuantidade;
    ArrayList<String> listaTotal;
    List<Integer> idProdutos;

    TextView txtProduto;
    TextView txtUnidade;
    TextView txtTotal;
    TextView txtValorTotal;
    String teste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_vendas);
        txtProduto = findViewById(R.id.textViewProduto);
        txtUnidade = findViewById(R.id.textViewUnidade);
        txtTotal = findViewById(R.id.textViewTotal);
        txtValorTotal = findViewById(R.id.tl_venda_txt_totalPedido);
        listViewPedidos = findViewById(R.id.tl_venda_listView);


        final Spinner spinner, spinner2, sp_quantidade;
        clientes = db.listarTodosOsClientes();
        produtos = db.listarTodosOsProdutos();
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<String> listaProduto = new ArrayList<>();
        quantidade = new ArrayList<>();
        listaQuantidade = new ArrayList<>();
        listaTotal = new ArrayList<>();
        lista_pedidos_array = new ArrayList<>();
        idProdutos = new ArrayList<>();

        //lista de clientes
        arrayList.add("- Selcione o Cliente -");
        listaProduto.add("- Selecione o Produto -");

        // declarando elementos da pagina
        spinner = findViewById(R.id.tl_vendas_dd_cliente);
        spinner2 = findViewById(R.id.tl_vendas_dd_produto);
        sp_quantidade = findViewById(R.id.tl_venda_sp_quantidade);

        ImageButton btn_Add = findViewById(R.id.tl_vendas_btn_add);
        Button btn_cadastrar = findViewById(R.id.tl_venda_btn_cadastrar);
        Button btn_cancel = findViewById(R.id.tl_venda_btn_cancelar);

        for (int i = 0; i < 100; i++) {
            quantidade.add(String.valueOf(i));
        }


        class ListaAdapter extends BaseAdapter {
            Context context;
            ArrayList<String> listaProduto;
            ArrayList<String> listaUnidade;
            ArrayList<String> listaTotal;

            public ListaAdapter(Context context, ArrayList<String> array, ArrayList<String> array2, ArrayList<String> array3) {
                this.context = context;
                this.listaProduto = array;
                this.listaUnidade = array2;
                this.listaTotal = array3;
            }

            @Override
            public boolean areAllItemsEnabled() {
                return super.areAllItemsEnabled();
            }

            @Override
            public int getCount() {
                return listaProduto.size();
            }


            @Override
            public Object getItem(int position) {
                return listaProduto.get(position);
            }

            @Override
            public long getItemId(int position) {
                return listaProduto.indexOf(listaProduto.get(position));
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {

                Viewholder viewholder = new Viewholder();
                if (convertView == null) {
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(R.layout.list_view_delete, null);

                    viewholder.nomePoduto = convertView.findViewById(R.id.lv_vendas_txt_produto);
                    viewholder.unidades = convertView.findViewById(R.id.lv_vendas_txt_unidade);
                    viewholder.valorTotal = convertView.findViewById(R.id.lv_vendas_txt_valorTotal);

                    viewholder.delete = convertView.findViewById(R.id.lv_btn_delete);

                    convertView.setTag(viewholder);


                } else {
                    viewholder = (Viewholder) convertView.getTag();

                }
                viewholder.nomePoduto.setText(listaProduto.get(position));
                viewholder.unidades.setText(listaUnidade.get(position));
                viewholder.valorTotal.setText(listaTotal.get(position));
                viewholder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        somatorio =0;
                        for (int i = 0; i < listaTotal.size(); i++) {
                            somatorio += Float.valueOf(listaTotal.get(i));
                        }


                        somatorio = somatorio - Float.valueOf(listaTotal.get(position));

                        listaProduto.remove(position);
                        listaUnidade.remove(position);
                        listaTotal.remove(position);
                        idProdutos.remove(position);
                        txtValorTotal.setText(NumberFormat.getCurrencyInstance().format(somatorio));
                        notifyDataSetChanged();


                    }
                });


                return convertView;
            }

            class Viewholder {
                TextView nomePoduto;
                TextView unidades;
                TextView valorTotal;

                ImageView delete;

            }


        }


        //listando cliente e produtos na dropdown
        for (Cliente c : clientes) {
            arrayList.add(String.valueOf(c.getNomeCliente()) + " " + c.getSobreNome());
        }
        for (Produto p : produtos) {
            listaProduto.add(String.valueOf(p.getNomeProduto()));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.custom_spinner, arrayList);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, R.layout.custom_spinner, listaProduto);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.custom_spinner, quantidade);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner2.setAdapter(adapter1);
        sp_quantidade.setAdapter(adapter2);


        // botoes
        btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String linha = spinner2.getSelectedItem().toString();
                lista_pedidos_array.add(linha);
                idProdutos.add(produtos.get(spinner2.getSelectedItemPosition()-1).getIdProduto());
                listaQuantidade.add(sp_quantidade.getSelectedItem().toString());
                // calculo do valor pago o -1 serve pois na lista foi adicionado o `-selecione produto-` movendo a primeira posicao
                float valorPago = Integer.parseInt(sp_quantidade.getSelectedItem().toString()) * produtos.get(spinner2.getSelectedItemPosition() - 1).getValorProduto();
                listaTotal.add(String.valueOf(valorPago));
                sp_quantidade.setSelection(0);


                if (!lista_pedidos_array.isEmpty()) {
                    txtProduto.setVisibility(View.VISIBLE);
                    txtUnidade.setVisibility(View.VISIBLE);
                    txtTotal.setVisibility(View.VISIBLE);

                }
                 somatorio = 0;
                for (int i = 0; i < listaTotal.size(); i++) {
                    somatorio += Float.parseFloat(listaTotal.get(i));
                }
                ListaAdapter listaAdapter = new ListaAdapter(telaVendas.this, lista_pedidos_array, listaQuantidade, listaTotal);
                listViewPedidos.setAdapter(listaAdapter);
                teste = listViewPedidos.toString();

                txtValorTotal.setText(NumberFormat.getCurrencyInstance().format(somatorio));


            }
        });


        btn_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spinner.getSelectedItemPosition()!=0) {
                    db.salvarVenda(somatorio, clientes.get(spinner.getSelectedItemPosition() - 1).getIdCliente(), idProdutos, listaQuantidade);
                }else
                    Toast.makeText(telaVendas.this,"Selecione o cliente"+somatorio,Toast.LENGTH_SHORT).show();


            }
        });





    }


}




