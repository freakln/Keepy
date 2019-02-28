package com.studio.tis_3.com.keepy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import com.microsoft.windowsazure.mobileservices.*;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;

import java.net.MalformedURLException;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
BancoDados db = new BancoDados(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView totalArrecadado = findViewById(R.id.txt_total_pc);
        totalArrecadado.setText(NumberFormat.getCurrencyInstance().format(Float.parseFloat(db.totalArrecadado())));
       TextView totalPagoNoMes = findViewById(R.id.txt_total_pagamento);
       totalPagoNoMes.setText(NumberFormat.getCurrencyInstance().format(Float.parseFloat(db.totalPagaNoMes())));
        TextView totalVendas = findViewById(R.id.totalVendas);
        totalVendas.setText(db.totalVendas());
        TextView totalItens = findViewById(R.id.totalProdutos);
        totalItens.setText(db.totalProdutos());



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_cadastrarProduto) {
            abrirTelaDeCadastroProduto();
        } else if (id == R.id.nav_listarProdutos) {
            abrirTelaDeListarProduto();

        } else if (id == R.id.nav_cadastrarCliente) {
            abrirTelaDeCadastroCliente();

        } else if (id == R.id.nav_listarCliente) {
            abrirTelaDeListarCliente();

        } else if (id == R.id.nav_vendasRealizadas){
            abrirTelaDeVenda();
        } else if (id == R.id.nav_pagamento){
            abrirTelaDeCadastroPagamento();
        } else if (id == R.id.nav_pagamento_realizados){
            abrirTelaRelatorio();


        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void abrirTelaDeCadastroProduto(){
        Intent  intent = new Intent(this,CadProduto.class);
        startActivity(intent);


    }

    void abrirTelaDeCadastroPagamento(){
        Intent  intent = new Intent(this,telaPagamento.class);
        startActivity(intent);


    }

    void abrirTelaDeVenda(){
        Intent  intent = new Intent(this,telaVendas.class);
        startActivity(intent);


    }

    void abrirTelaDeListarProduto(){
        Intent  intent = new Intent(this,ListarProduto.class);
        startActivity(intent);


    }
    void abrirTelaDeCadastroCliente(){

        Intent  intent = new Intent(this,CadastroCliente.class);
        startActivity(intent);


    }

    void abrirTelaDeListarCliente() {
        Intent intent = new Intent(this, ListarCliente.class);
        startActivity(intent);

    }
    void abrirTelaRelatorio(){
        Intent intent = new Intent(this, telaRelatorio.class);
        startActivity(intent);


    }



    }
