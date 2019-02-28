package com.studio.tis_3.com.keepy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class telaRelatorio extends AppCompatActivity {
    BancoDados db = new BancoDados(this);
    ListView relatorio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_relatorio);
        Button btn_gerar = findViewById(R.id.btn_gerar_relatorio);
        ArrayList<String> tipoRelatorio= new ArrayList<>();
        tipoRelatorio.add("Selecione");
        tipoRelatorio.add("Vendas");
        tipoRelatorio.add("Pagamentos");
        final List<Cliente> clientes = db.listarTodosOsClientes();
        relatorio =findViewById(R.id.lista_view_relatorio);
        ArrayList listaClientes = new ArrayList();

        listaClientes.add("- Selecione o Cliente -");

        //listando cliente e produtos na dropdown
        for (Cliente c : clientes) {
            listaClientes.add(String.valueOf(c.getNomeCliente()) + " " + c.getSobreNome());}

        final Spinner dd_cliente=findViewById(R.id.dd_nome_tl_relatorio);
        final Spinner dropdown = findViewById(R.id.listar_meses);
        Spinner dropdown_data = findViewById(R.id.tipo_relatorios);


        final List<String> lista_meses = new ArrayList<>();

        lista_meses.addAll(db.listarMeses());



        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lista_meses);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(dataAdapter);

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, tipoRelatorio);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown_data.setAdapter(dataAdapter2);

        ArrayAdapter<String> adapterCliente = new ArrayAdapter<>(this,R.layout.custom_spinner,listaClientes);
        adapterCliente.setDropDownViewResource(R.layout.custom_spinner);
        dd_cliente.setAdapter(adapterCliente);


        btn_gerar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<String> listaPagamentos = db.todosPagamentosUmCliente(clientes.get(dd_cliente.getSelectedItemPosition()-1).getIdCliente(),
                        lista_meses.get(dropdown.getSelectedItemPosition()));

                Toast.makeText(telaRelatorio.this,"teste - " +  clientes.get(dd_cliente.getSelectedItemPosition()-1).getIdCliente()+" - "+lista_meses.get(dropdown.getSelectedItemPosition()),Toast.LENGTH_LONG).show();
                ArrayAdapter<String> adapter = new ArrayAdapter<>(telaRelatorio.this, R.layout.custom_spinner, listaPagamentos);

                relatorio.setAdapter(adapter);



            }
        });















    }
}
