package com.studio.tis_3.com.keepy;


import android.app.admin.DeviceAdminInfo;
import android.telephony.CellIdentityTdscdma;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Venda {

    private int idVenda;
    private int idProduto;
     private float precoTotal;
    private int idCliente;
    private Date data;
    private List<Item> idItens;

    public Venda() {
    }


    public Venda(int idVenda, int idProduto, float precoTotal, int idCliente, Date data, List<Item> idItens) {
        this.idVenda = idVenda;
        this.idProduto = idProduto;
        this.precoTotal = precoTotal;
        this.idCliente = idCliente;
        this.data = data;
        this.idItens = idItens;
    }


    public Venda(int idProduto, int idCliente) {
        this.idProduto = idProduto;
        this.idCliente = idCliente;
        this.idItens = new ArrayList<>();
        this.data=  new Date();


    }
}