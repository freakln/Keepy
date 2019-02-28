package com.studio.tis_3.com.keepy;

public class Item {


    private int idProduto;
    private int Quantidade;
    private int Id;


    public Item() {
    }

    public Item(int idProduto, int quantidade) {
        this.idProduto = idProduto;
        Quantidade = quantidade;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public int getQuantidade() {
        return Quantidade;
    }

    public void setQuantidade(int quantidade) {
        Quantidade = quantidade;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}