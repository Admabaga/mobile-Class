package com.example.myapplication.Network;


import com.example.myapplication.Network.DTO.LoginDTO;
import com.example.myapplication.Network.DTO.ServiciosDTO;
import com.example.myapplication.Network.DTO.UsuarioDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ClienteApi {

    @POST("/usuarios")
    Call<RespuestaServicios> registroUsuario(
            @Body UsuarioDTO usuarioDTO
    );

    @POST("/usuarios/log")
    Call<LoginDTO> inicioSesion(
            @Body LoginDTO loginDTO
    );

    @POST("/consignaciones/{cuentaId}")
    Call<RespuestaRegistro> consignar(
            @Body ServiciosDTO serviciosDTO,
            @Query("cuentaId") String cuentaId
    );
}
