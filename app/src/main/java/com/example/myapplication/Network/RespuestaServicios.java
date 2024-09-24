package com.example.myapplication.Network;

import com.example.myapplication.Network.DTO.LoginDTO;

public class RespuestaServicios {
    private LoginDTO loginDTO;
    private RespuestaRegistro respuestaRegistro;

    public RespuestaServicios() {
    }

    public RespuestaServicios(LoginDTO loginDTO, RespuestaRegistro repuestaRegistro) {
        this.loginDTO = loginDTO;
        this.respuestaRegistro = repuestaRegistro;
    }

    public LoginDTO getLoginDTO() {
        return loginDTO;
    }

    public void setLoginDTO(LoginDTO loginDTO) {
        this.loginDTO = loginDTO;
    }

    public RespuestaRegistro getRespuestaApi() {
        return respuestaRegistro;
    }
}
