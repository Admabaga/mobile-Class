package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.Network.ClienteApi;
import com.example.myapplication.Network.DTO.LoginDTO;
import com.example.myapplication.Network.RespuestaErrorBackend;
import com.example.myapplication.Network.RespuestaServicios;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {
    private Button botonVolver;
    private Button botonIniciarSesion;
    private EditText usuario;
    private EditText password;
    private ClienteApi clienteApi;
    private TextView respuestaServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        botonVolver = findViewById(R.id.botonVolverLogin);
        botonIniciarSesion = findViewById(R.id.botonIniciarSesion);
        usuario = findViewById(R.id.correoLogin);
        password = findViewById(R.id.editTextTextPassword);
        respuestaServer = findViewById(R.id.responseLogin);
        Retrofit retrofit = new Retrofit.Builder()

//                    .baseUrl("https://banco-backend-znok.onrender.com")
                .baseUrl("http://192.168.20.22:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        clienteApi = retrofit.create(ClienteApi.class);
        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
            }
        });

        botonIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        botonIniciarSesion.setOnClickListener(v -> {
            LoginDTO loginDTO = new LoginDTO();
            loginDTO.setUsuario(usuario.getText().toString());
            loginDTO.setPassword(password.getText().toString());
            enviarPeticionInicioSesion(loginDTO);
        });
    }
    private void enviarPeticionInicioSesion(final LoginDTO loginDTO) {
        runOnUiThread(() -> {
            try {
                Call<RespuestaServicios> call = clienteApi.inicioSesion(loginDTO);
                call.enqueue(new Callback<RespuestaServicios>() {
                    @Override
                    public void onResponse(Call<RespuestaServicios> call, Response<RespuestaServicios> response) {
                        if (response.isSuccessful()) {
                            RespuestaServicios respuesta = response.body();
                            Intent intent = new Intent(Login.this, Lobby.class);
                            startActivity(intent);
                        } else {
                            try {
                                String errorBody = response.errorBody().string();
                                if (errorBody.isEmpty()) {
                                    mostrarError("Error desconocido.");
                                } else {
                                    Gson gson = new Gson();
                                    RespuestaErrorBackend errorResponse = gson.fromJson(errorBody, RespuestaErrorBackend.class);
                                    String mensajeError = errorResponse.getMessage();
                                    mostrarError(mensajeError);
                                }
                            } catch (Exception e) {
                                mostrarError("Error al procesar la respuesta del servidor.");
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<RespuestaServicios> call, Throwable t) {
                        mostrarError(t.getMessage());
                    }
                });
            } catch (Exception e) {
                mostrarError(e.getMessage());
            }
        });
    }
    private void mostrarError(String mensajeError) {
        respuestaServer.setText(mensajeError);
    }
}