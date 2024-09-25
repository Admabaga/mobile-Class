package com.example.myapplication.Network.DTO;

public class ServiciosDTO {
    private Double valor;
    private Double saldo;
    private Integer cuentaReceptora;

    public ServiciosDTO() {
    }

    public ServiciosDTO(Double valor, Double saldo, Integer cuentaReceptora) {
        this.valor = valor;
        this.saldo = saldo;
        this.cuentaReceptora = cuentaReceptora;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Integer getCuentaReceptora() {
        return cuentaReceptora;
    }

    public void setCuentaReceptora(Integer cuentaReceptora) {
        this.cuentaReceptora = cuentaReceptora;
    }
}
