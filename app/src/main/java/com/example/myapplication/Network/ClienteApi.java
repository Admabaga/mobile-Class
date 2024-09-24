package com.example.myapplication.Network;


import com.example.myapplication.Network.DTO.LoginDTO;
import com.example.myapplication.Network.DTO.UsuarioDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ClienteApi {

    @POST("/usuarios")
    Call<RespuestaServicios> registroUsuario(
            @Body UsuarioDTO usuarioDTO
    );

    @POST("/usuarios/log")
    Call<RespuestaServicios> inicioSesion(
            @Body LoginDTO loginDTO
    );

}
