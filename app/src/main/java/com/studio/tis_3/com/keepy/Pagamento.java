package com.studio.tis_3.com.keepy;

import java.util.Date;

public class Pagamento {

    private int id;
    private float  valorPago;
    private int idCliente;
    private Date data;

    public Pagamento(float valorPago, int idCliente) {
        this.valorPago = valorPago;
        this.idCliente = idCliente;
        }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getValorPago() {
        return valorPago;
    }

    public void setValorPago(float valorPago) {
        this.valorPago = valorPago;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Pagamento(int id, float valorPago, int idCliente, Date data) {
        this.id = id;
        this.valorPago = valorPago;
        this.idCliente = idCliente;
        this.data = data;
    }

    public Pagamento() {

    }
}
