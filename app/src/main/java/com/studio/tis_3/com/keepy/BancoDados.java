package com.studio.tis_3.com.keepy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BancoDados extends SQLiteOpenHelper {
    private static final int Versao_Banco = 1;
    //VARIAVEIS PARA O BANCO
    private static final String BANCO_PRODUTO = "bd_produto";
    //criacao da tabelas no SQL
    private static final String TABELA_PRODUTOS = "tb_produto";
    private static final String TABELA_CLIENTE = "tb_cliente";
    private static final String TABELA_VENDAS = "tb_vendas";
    private static final String TABELA_ITEM_VENDA = "tb_item_venda";
    private static final String TABELA_PAGAMENTO = "tb_pagamento";

    //campos das colunas
    //campos comuns
    private static final String COLUNA_ID = "ID";
    private static final String COLUNA_NOME = "Nome";
    // campos exclusivos
    //tabela cliente
    private static final String COLUNA_SOBRENOME = "Sobrenome";
    private static final String COLUNA_EMAIL = "Email";
    private static final String COLUNA_TELEFONE = "telefone";
    private static final String COLUNA_SALDODEVIDO = "Saldo_Devido";
    private static final String COLUNA_STATUS = "Status";
    //tabela produto
    private static final String COLUNA_VALOR = "Valor";

    // tabela venda
    private static final String COLUNA_DATA = "Data";
    private static final String COLUNA_VALOR_VENDA = "total_venda";

    //tabela itens vendas

    private static final String COLUNA_QUANTIDADE = "Quantidade";

    //tabela pagamentos
    private static final String COLUNA_VALORPAGO = "valor_pago";

    //chaves estrangeiras
    private static final String COLUNA_ID_VENDA = "id_venda";
    private static final String COLUNA_ID_PRODUTO = "id_Produto";
    private static final String COLUNA_ID_CLIENTE = "id_Cliente";


    //Query para criar as tabelas
    //tabela de produtos
    private static final String QUERY_TABELA_PRODUTO = "CREATE TABLE " + TABELA_PRODUTOS + "("
            + COLUNA_ID + " INTEGER PRIMARY KEY , " + COLUNA_NOME + " TEXT,"
            + COLUNA_VALOR + " REAL)";
    //tabela de clientes
    private static final String QUERY_TABELA_CLIENTE = "CREATE TABLE " + TABELA_CLIENTE + "("
            + COLUNA_ID + " INTEGER PRIMARY KEY , "
            + COLUNA_NOME + " TEXT,"
            + COLUNA_SOBRENOME + " TEXT,"
            + COLUNA_EMAIL + " TEXT,"
            + COLUNA_TELEFONE + " TEXT,"
            + COLUNA_SALDODEVIDO + " REAL,"
            + COLUNA_STATUS + " REAL)";
    private static final String QUERY_TABELA_VENDA = "CREATE TABLE " + TABELA_VENDAS + "("
            + COLUNA_ID + " INTEGER PRIMARY KEY , "
            + COLUNA_ID_CLIENTE + " INTEGER,"
            + COLUNA_DATA + " DATE  DEFAULT CURRENT_TIMESTAMP ,"
            + COLUNA_VALOR_VENDA + " REAL)";
    private static final String QUERY_TABELA_ITENS_VENDA = "CREATE TABLE " + TABELA_ITEM_VENDA + "("
            + COLUNA_ID + " INTEGER PRIMARY KEY , "
            + COLUNA_ID_PRODUTO + " INTEGER,"
            + COLUNA_ID_VENDA + " INTEGER,"
            + COLUNA_QUANTIDADE + " INTEGER)";


    private static final String QUERY_TABELA_PAGAMENTO = "CREATE TABLE " + TABELA_PAGAMENTO + "("
            + COLUNA_ID + " INTEGER PRIMARY KEY , "
            + COLUNA_ID_CLIENTE + " INTEGER,"
            + COLUNA_DATA + " DATE  DEFAULT CURRENT_TIMESTAMP ,"
            + COLUNA_VALORPAGO + " REAL)";


    BancoDados(Context context) {
        super(context, BANCO_PRODUTO, null, Versao_Banco);


    }


    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL(QUERY_TABELA_PRODUTO);
        db.execSQL(QUERY_TABELA_CLIENTE);
        db.execSQL(QUERY_TABELA_PAGAMENTO);
        db.execSQL(QUERY_TABELA_VENDA);
        db.execSQL(QUERY_TABELA_ITENS_VENDA);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //CRUD

    void salvarPagamento(int idCliente, float valorPago) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUNA_ID_CLIENTE, idCliente);
        values.put(COLUNA_VALORPAGO, valorPago);


        db.insert(TABELA_PAGAMENTO, null, values);
        db.close();
        atualizaSaldo(idCliente, valorPago);


    }


    void salvarProduto(Produto produto) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUNA_NOME, produto.getNomeProduto());
        values.put(COLUNA_VALOR, produto.getValorProduto());

        db.insert(TABELA_PRODUTOS, null, values);
        db.close();


    }

    void salvarCliente(Cliente cliente) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUNA_NOME, cliente.getNomeCliente());
        values.put(COLUNA_SOBRENOME, cliente.getSobreNome());
        values.put(COLUNA_EMAIL, cliente.getEmail());
        values.put(COLUNA_TELEFONE, cliente.getTelefone());
        values.put(COLUNA_SALDODEVIDO, cliente.getSaldoDevido());
        values.put(COLUNA_STATUS, cliente.getStatus());

        database.insert(TABELA_CLIENTE, null, values);
        database.close();

    }


    private Produto selecionarProduto(int idProduto) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABELA_PRODUTOS, new String[]{COLUNA_ID, COLUNA_NOME, COLUNA_VALOR},
                COLUNA_ID + "= ?", new String[]{String.valueOf(idProduto)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        assert cursor != null;
        Produto p;
        p = new Produto(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                Float.valueOf(cursor.getString(2)));
        cursor.close();

        db.close();
        return p;


    }

    void atualizaSaldo(int idCliente, float pagamento) {


        List<Cliente> listacliente = listarTodosOsClientes();
        int status = 1;
        float soma = 0;

        // verifica se o cliente pagou o debito
        for (int i = 0; i < listacliente.size(); i++) {
            if (listacliente.get(i).getIdCliente() == idCliente) {
                soma = listacliente.get(i).getSaldoDevido() + pagamento;
                if (soma > 0)
                    status = 1;
                else
                    status = 0;


            }


        }

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE " + TABELA_CLIENTE + " SET " + COLUNA_STATUS + " = " + status + " ," + COLUNA_SALDODEVIDO + " = " + COLUNA_SALDODEVIDO + " + " + pagamento + " WHERE ID= " + idCliente;

        db.execSQL(query);

        db.close();


    }

    public void atualizaProduto(Produto produto) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUNA_NOME, produto.getNomeProduto());
        values.put(COLUNA_VALOR, produto.getValorProduto());
        db.update(TABELA_PRODUTOS, values, COLUNA_ID + "= ?", new String[]{String.valueOf(produto.getIdProduto())});
        db.close();


    }

    public void atualizaCliente(Cliente cliente) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUNA_NOME, cliente.getNomeCliente());
        values.put(COLUNA_SOBRENOME, cliente.getSobreNome());
        values.put(COLUNA_EMAIL, cliente.getEmail());
        values.put(COLUNA_TELEFONE, cliente.getTelefone());
        values.put(COLUNA_SALDODEVIDO, cliente.getSaldoDevido());
        values.put(COLUNA_STATUS, cliente.getStatus());
        db.update(TABELA_CLIENTE, values, COLUNA_ID + "= ?", new String[]{String.valueOf(cliente.getIdCliente())});
        db.close();


    }

    void deletaProduto(int i) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABELA_PRODUTOS, COLUNA_ID + " = ? ", new String[]{String.valueOf(i)});
        db.close();


    }

    ArrayList<String> todosPagamentosUmCliente(int idCliente,String mes){

        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<String> listaPagamentos= new ArrayList<>();

        String query = "select (" + COLUNA_VALOR_VENDA + ") from " + TABELA_VENDAS + " where strftime('%m/%Y'," + COLUNA_DATA + ") = '"+mes+"' and "+COLUNA_ID_CLIENTE+"= "+idCliente;

        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            do {
                listaPagamentos.add(c.getString(0));
            } while (c.moveToNext());


        }
        c.close();
        db.close();


        return listaPagamentos;

    }




    public List<Produto> listarTodosOsProdutos() {

        SQLiteDatabase db = this.getWritableDatabase();
        List<Produto> listaProdutos = new ArrayList<>();
        String query = "SELECT * FROM " + TABELA_PRODUTOS;
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                Produto p = new Produto(Integer.parseInt(c.getString(0)), c.getString(1), Float.valueOf(c.getString(2)));

                listaProdutos.add(p);

            } while (c.moveToNext());


        }
        c.close();
        db.close();

        return listaProdutos;
    }

    //percorre o banco para verificar e retorna os meses apartir da primeira venda


    String totalArrecadado() {
        SQLiteDatabase db = this.getReadableDatabase();
        String total;


        String query = "select sum(" + COLUNA_VALOR_VENDA + ") from " + TABELA_VENDAS + " where strftime('%m'," + COLUNA_DATA + ") = strftime('%m','now')";


        Cursor c = db.rawQuery(query, null);
        if (c != null)
            c.moveToFirst();

        total = c.getString(0);
        if (total.isEmpty())
            total = "0";

        c.close();
        db.close();


        return total;


    }

    String totalPagaNoMes() {
        SQLiteDatabase db = this.getReadableDatabase();
        String total;


        String query = "select sum(" + COLUNA_VALORPAGO + ") from " + TABELA_PAGAMENTO + " where strftime('%m'," + COLUNA_DATA + ") = strftime('%m','now')";


        Cursor c = db.rawQuery(query, null);
        if (c != null)
            c.moveToFirst();

        total = c.getString(0);

        c.close();
        db.close();


        return total;


    }

    String totalVendas() {
        SQLiteDatabase db = this.getReadableDatabase();
        String total;


        String query = "select count(" + COLUNA_ID + ") from " + TABELA_VENDAS + " where strftime('%m'," + COLUNA_DATA + ") = strftime('%m','now')";


        Cursor c = db.rawQuery(query, null);
        if (c != null)
            c.moveToFirst();

        total = c.getString(0);

        c.close();
        db.close();


        return total;


    }

    String totalProdutos() {
        SQLiteDatabase db = this.getReadableDatabase();
        String total;


        String query = "select sum(" + COLUNA_QUANTIDADE + ") from "+TABELA_ITEM_VENDA;


        Cursor c = db.rawQuery(query, null);
        if (c != null)
            c.moveToFirst();

        total = c.getString(0);

        c.close();
        db.close();


        return total;


    }









    Set<String> listarMeses() {
        SQLiteDatabase db = this.getWritableDatabase();

        Set<String> listaDeMeses = new HashSet<>();

        String query = "select strftime('%m/%Y'," + COLUNA_DATA + ") from " + TABELA_VENDAS;

        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            do {
                listaDeMeses.add(c.getString(0));
            } while (c.moveToNext());


        }
        c.close();
        db.close();


        return listaDeMeses;


    }


    public List<Cliente> listarTodosOsClientes() {

        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<Cliente> listaClientes = new ArrayList<>();

        String query = "SELECT * FROM " + TABELA_CLIENTE;

        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                Cliente cl = new Cliente(Integer.parseInt(
                        c.getString(0)),//id
                        c.getString(1), //nome
                        c.getString(2), //sobrenome
                        c.getString(3), //email
                        c.getString(4),//telefone
                        Float.valueOf(c.getString(5)),
                        Integer.parseInt(c.getString(6)));

                listaClientes.add(cl);
            } while (c.moveToNext());


        }
        c.close();
        db.close();

        return listaClientes;
    }

    void apagarCliente(int i) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABELA_CLIENTE, COLUNA_ID + " = ? ", new String[]{String.valueOf(i)});
        db.close();


    }


    public void salvarVenda(float somatorio, int idCliente, List<Integer> idProdutos, ArrayList<String> quantidade) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUNA_ID_CLIENTE, idCliente);
        values.put(COLUNA_VALOR_VENDA, somatorio);
        database.insert(TABELA_VENDAS, null, values);


        salvarItem(idProdutos, quantidade);

        atualizaSaldo(idCliente, -somatorio);
        database.close();

    }

    private void salvarItem(List<Integer> idProdutos, ArrayList<String> quantidade) {
        SQLiteDatabase database = this.getWritableDatabase();
        String ultimoId = "last_insert_rowid()";
        String query = "insert into " + TABELA_ITEM_VENDA + " (" + COLUNA_ID_PRODUTO + "," + COLUNA_ID_VENDA + "," + COLUNA_QUANTIDADE + ") values ";
        for (int i = 0; i < quantidade.size(); i++) {
            if (i < quantidade.size() - 1)
                query += " (" + idProdutos.get(i) + "," + ultimoId + "," + quantidade.get(i) + "),";
            else
                query += " (" + idProdutos.get(i) + "," + ultimoId + "," + quantidade.get(i) + ")";

        }
        database.execSQL(query);


    }
}
