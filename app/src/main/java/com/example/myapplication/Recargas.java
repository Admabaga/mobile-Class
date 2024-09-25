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
import com.example.myapplication.Network.DTO.ServiciosDTO;
import com.example.myapplication.Network.RespuestaErrorBackend;
import com.example.myapplication.Network.RespuestaRegistro;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Recargas extends AppCompatActivity {
    private Button volverLobby;
    private TextView respuestaServer;
    private Button recargar;
    private TextView saldo;
    private TextView numeroCuenta;
    private TextView estado;
    private EditText valorRecarga;
    private ClienteApi clienteApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recargas);
        Intent intent = getIntent();
        LoginDTO datosRecibidos = (LoginDTO) intent.getSerializableExtra("datos");
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        volverLobby = findViewById(R.id.volverLobby);
        respuestaServer = findViewById(R.id.responseRecarga);
        recargar = findViewById(R.id.botonRecargar);
        saldo = findViewById(R.id.saldoRecarga);
        estado = findViewById(R.id.estado);
        numeroCuenta = findViewById(R.id.numeroCuenta);
        valorRecarga = findViewById(R.id.valorARecargar);
        Retrofit retrofit = new Retrofit.Builder()

//                    .baseUrl("https://banco-backend-znok.onrender.com")
                .baseUrl("http://192.168.20.22:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        clienteApi = retrofit.create(ClienteApi.class);

        renderizarInfoCuenta(saldo, numeroCuenta, estado, datosRecibidos);
        volverLobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Recargas.this, Lobby.class);
                startActivity(intent);
                finish();
            }
        });

        recargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServiciosDTO serviciosDTO = new ServiciosDTO();
                serviciosDTO.setValor(Double.valueOf(valorRecarga.getText().toString()));
                String cuentaId = String.valueOf(datosRecibidos.getIdCuenta());
                String nuevoSaldo = String.valueOf(Double.valueOf(datosRecibidos.getSaldo()) + Double.valueOf(serviciosDTO.getValor()));
//                enviarPeticionRecargar(serviciosDTO, cuentaId);

            }
        });

    }
    public void renderizarInfoCuenta(TextView saldo, TextView numeroCuenta, TextView estado, LoginDTO datosRecibidos){
        saldo.setText("Saldo: "+datosRecibidos.getSaldo());
        numeroCuenta.setText("Numero de cuenta: "+datosRecibidos.getNumeroCuenta());
        if (datosRecibidos.getEstado()!= true){
            estado.setText("Cuenta: Inactiva");
        }else {
            estado.setText("Cuenta: Activa");
        }
    }
    private void enviarPeticionRecargar(final ServiciosDTO serviciosDTO, String cuentaId) {
        runOnUiThread(() -> {
            try {
                Call<RespuestaRegistro> call = clienteApi.consignar(serviciosDTO, cuentaId);
                call.enqueue(new Callback<RespuestaRegistro>() {
                    @Override
                    public void onResponse(Call<RespuestaRegistro> call, Response<RespuestaRegistro> response) {
                        if (response.isSuccessful()) {
                            respuestaServer.setText("Recarga exitosa!");
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
    private void mostrarError(String mensajeError) {
        respuestaServer.setText(mensajeError);
    }
}