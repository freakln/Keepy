package com.studio.tis_3.com.keepy;

public class Cliente {

    private int idCliente;
    private String nomeCliente;
    private String sobreNome;
    private String email;
    private String telefone;
    private float saldoDevido;
    private int status;

    public Cliente() {
    }

    public Cliente(int idCliente, String nomeCliente, String sobreNome, String email, String telefone, float saldoDevido, int status) {
        this.idCliente = idCliente;
        this.nomeCliente = nomeCliente;
        this.sobreNome = sobreNome;
        this.email = email;
        this.telefone = telefone;
        this.saldoDevido = saldoDevido;
        this.status = status;
    }

    public Cliente(String nomeCliente, String sobreNome, String email, String telefone, float saldoDevido, int podeComprar) {
        this.nomeCliente = nomeCliente;
        this.sobreNome = sobreNome;
        this.email = email;
        this.telefone = telefone;
        this.saldoDevido = saldoDevido;
        this.status = podeComprar;
    }

    public Cliente(String nomeCliente, String sobreNome, String email, String telefone) {
        this.nomeCliente = nomeCliente;
        this.sobreNome = sobreNome;
        this.email = email;
        this.telefone = telefone;
        this.saldoDevido = 0;
        this.status = 1;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getSobreNome() {
        return sobreNome;
    }

    public void setSobreNome(String sobreNome) {
        this.sobreNome = sobreNome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public float getSaldoDevido() {
        return saldoDevido;
    }

    public void setSaldoDevido(float saldoDevido) {
        this.saldoDevido = saldoDevido;
    }


}
