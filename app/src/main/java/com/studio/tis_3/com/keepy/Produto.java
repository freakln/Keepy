package com.studio.tis_3.com.keepy;

public class Produto {

    private int idProduto;
    private String nomeProduto;
    private float valorProduto;

    public Produto() {

    }

    public Produto(int idProduto, String nomeProduto, float valorProduto) {
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.valorProduto = valorProduto;
    }

    public void setIdProduto(int idProduto) {
        idProduto = idProduto;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public Produto(String nomeProduto, float valorProduto) {
        this.nomeProduto = nomeProduto;
        this.valorProduto = valorProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public float getValorProduto() {
        return valorProduto;
    }

    public void setValorProduto(float valorProduto) {
        this.valorProduto = valorProduto;
    }
}
