package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.myapplication.Network.ClienteApi;
import com.example.myapplication.Network.RespuestaErrorBackend;
import com.example.myapplication.Network.RespuestaRegistro;
import com.example.myapplication.Network.DTO.UsuarioDTO;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Registro extends AppCompatActivity {
    private Button botonAtras;
    private Button registrarse;
    private EditText nombreUsuario;
    private EditText apellidoUsuario;
    private EditText password;
    private EditText cedula;
    private EditText correoElectronico;
    private TextView respuestaServer;
    private ClienteApi clienteApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        botonAtras = findViewById(R.id.botonVolverRegistro);
        registrarse = findViewById(R.id.botonRegistrarse);
        nombreUsuario = findViewById(R.id.inputNombreUsuario);
        apellidoUsuario = findViewById(R.id.inputApellidosUsuario);
        password = findViewById(R.id.passwordUsuario);
        cedula = findViewById(R.id.inputIdUsuario);
        correoElectronico = findViewById(R.id.correoUsuario);
        respuestaServer = findViewById(R.id.responseRegistro);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.16.57.195:8080")
//                .baseUrl("http://192.168.20.22:8080/")
//                .baseUrl("https://banco-backend-znok.onrender.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        clienteApi = retrofit.create(ClienteApi.class);
        botonAtras.setOnClickListener(v -> {
            Intent intent = new Intent(Registro.this, MainActivity.class);
            startActivity(intent);
        });

        registrarse.setOnClickListener(v -> {
            UsuarioDTO usuario = new UsuarioDTO(
                    nombreUsuario.getText().toString(),
                    apellidoUsuario.getText().toString(),
                    cedula.getText().toString(),
                    correoElectronico.getText().toString(),
                    password.getText().toString()
            );

            enviarPeticionRegistro(usuario);
        });
    }
    private void enviarPeticionRegistro(final UsuarioDTO usuario) {
        runOnUiThread(() -> {
            try {
                Call<RespuestaRegistro> call = clienteApi.registroUsuario(usuario);
                call.enqueue(new Callback<RespuestaRegistro>() {
                    @Override
                    public void onResponse(Call<RespuestaRegistro> call, Response<RespuestaRegistro> response) {
                        if (response.isSuccessful()) {
                            mensajeExitoso();
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
                    public void onFailure(Call<RespuestaRegistro> call, Throwable t) {
                        mostrarError(t.getMessage());
                    }
                });
            } catch (Exception e) {
                mostrarError(e.getMessage());
            }
        });
    }

    private void mensajeExitoso() {
        respuestaServer.setText("Usuario Registrado!");
    }

    private void mostrarError(String mensajeError) {
        respuestaServer.setText(mensajeError);
    }

}