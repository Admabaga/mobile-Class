package com.example.myapplication.Network;


import com.example.myapplication.Network.DTO.LoginDTO;
import com.example.myapplication.Network.DTO.ServiciosDTO;
import com.example.myapplication.Network.DTO.UsuarioDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ClienteApi {

    @POST("/usuarios")
    Call<RespuestaRegistro> registroUsuario(
            @Body UsuarioDTO usuarioDTO
    );

    @POST("/usuarios/log")
    Call<LoginDTO> inicioSesion(
            @Body LoginDTO loginDTO
    );

    @POST("/consignaciones/{idCuenta}")
    Call<ServiciosDTO> consignar(
            @Body ServiciosDTO serviciosDTO,
            @Path("idCuenta") Long idCuenta
    );

    @POST("/retiros/{idCuenta}")
    Call<ServiciosDTO> retirar(
            @Body ServiciosDTO serviciosDTO,
            @Path("idCuenta") Long idCuenta
    );
}
