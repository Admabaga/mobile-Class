package com.example.myapplication.Network;

public class RespuestaRegistro {
    private String message;
    private String respuestaJson;
    private String userId;
    private String saldo;

    public RespuestaRegistro(String message, String respuestaJson, String userId, String saldo) {
        this.message = message;
        this.respuestaJson = respuestaJson;
        this.userId = userId;
        this.saldo = saldo;
    }

    public RespuestaRegistro() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRespuestaJson() {
        return respuestaJson;
    }

    public void setRespuestaJson(String respuestaJson) {
        this.respuestaJson = respuestaJson;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }
}
