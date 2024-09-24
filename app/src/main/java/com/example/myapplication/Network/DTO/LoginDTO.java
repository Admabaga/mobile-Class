package com.example.myapplication.Network.DTO;

import java.io.Serializable;

public class LoginDTO implements Serializable {
    private String usuario;
    private String password;
    private Boolean loggIn;
    private String saldo;
    private Integer numeroCuenta;
    private Boolean estado;
    private Long idCuenta;

    public LoginDTO(String usuario, String password, Boolean loggIn, String saldo, Integer numeroCuenta, Boolean estado, Long idCuenta) {
        this.usuario = usuario;
        this.password = password;
        this.loggIn = loggIn;
        this.saldo = saldo;
        this.numeroCuenta = numeroCuenta;
        this.estado = estado;
        this.idCuenta = idCuenta;
    }

    public LoginDTO() {
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getLoggIn() {
        return loggIn;
    }

    public void setLoggIn(Boolean loggIn) {
        this.loggIn = loggIn;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public Integer getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(Integer numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Long getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Long idCuenta) {
        this.idCuenta = idCuenta;
    }
}
