package com.example.myapplication.Network;

public class RespuestaRegistro {
    private String message;
    private String respuestaJson;
    private String userId;

    public RespuestaRegistro(String message, String respuestaJson, String userId) {
        this.message = message;
        this.respuestaJson = respuestaJson;
        this.userId = userId;
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
}
