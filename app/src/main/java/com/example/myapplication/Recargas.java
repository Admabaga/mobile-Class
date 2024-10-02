package com.example.myapplication;

import android.app.Activity;
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
import com.google.gson.Gson;

import java.text.DecimalFormat;

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
        LoginDTO datosRecibidos = (LoginDTO) intent.getSerializableExtra("datos");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.16.57.195:8080")
//                .baseUrl("http://192.168.20.22:8080")
//                .baseUrl("https://banco-backend-znok.onrender.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        clienteApi = retrofit.create(ClienteApi.class);

        renderizarInfoCuenta(saldo, numeroCuenta, estado, datosRecibidos);
        volverLobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent(Recargas.this, Lobby.class);
                LoginDTO datosActuales = new LoginDTO();
                datosActuales.setSaldo(datosRecibidos.getSaldo());
                resultIntent.putExtra("datosActualizados", datosActuales);
                finish();
            }
        });

        recargar.setOnClickListener(v -> {
            String valorARecargar = valorRecarga.getText().toString();
            try {
                ServiciosDTO serviciosDTO = new ServiciosDTO();
                serviciosDTO.setValor(Double.parseDouble(valorARecargar));
                Long idCuenta = datosRecibidos.getIdCuenta();
                enviarPeticionRecargar(serviciosDTO, idCuenta, datosRecibidos);
            } catch (NumberFormatException e) {
                respuestaServer.setText("Valor inválido. Debe ser un número.");
            }
        });
    }

    public void renderizarInfoCuenta(TextView saldo, TextView numeroCuenta, TextView estado, LoginDTO datosRecibidos) {
        saldo.setText("Saldo: " + datosRecibidos.getSaldo());
        numeroCuenta.setText("Número de cuenta: " + datosRecibidos.getNumeroCuenta());

        if (!datosRecibidos.getEstado()) {
            estado.setText("Cuenta: Inactiva");
        } else {
            estado.setText("Cuenta: Activa");
        }
    }

    private void enviarPeticionRecargar(final ServiciosDTO serviciosDTO, Long idCuenta, LoginDTO datosRecibidos) {
        runOnUiThread(() -> {
            try {
                Call<ServiciosDTO> call = clienteApi.consignar(serviciosDTO, idCuenta);
                call.enqueue(new Callback<ServiciosDTO>() {
                    @Override
                    public void onResponse(Call<ServiciosDTO> call, Response<ServiciosDTO> response) {
                        if (response.isSuccessful()) {
                            ServiciosDTO serviciosDTO1 = response.body();
                            saldo.setText("Saldo: "+numeros(serviciosDTO1.getSaldo()));
                            respuestaServer.setText("Recarga exitosa.");
                            datosRecibidos.setSaldo(numeros(serviciosDTO1.getSaldo()));
                        } else {
                            try {
                                String errorBody = response.errorBody().string();
                                if (errorBody.isEmpty()) {
                                    mostrarError("Error desconocido.");
                                } else {
                                    Gson gson = new Gson();
                                    RespuestaErrorBackend errorResponse = gson.fromJson(errorBody, RespuestaErrorBackend.class);
                                    mostrarError(errorResponse.getMessage());
                                }
                            } catch (Exception e) {
                                mostrarError("Error al procesar la respuesta del servidor.");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ServiciosDTO> call, Throwable t) {
                        mostrarError("Error de conexión: " + t.getMessage());
                    }
                });
            } catch (Exception e) {
                mostrarError("Error: " + e.getMessage());
            }
        });
    }

    private void mostrarError(String mensajeError) {
        respuestaServer.setText(mensajeError);
    }

    public String numeros(Double numero){
        DecimalFormat formato = new DecimalFormat("###,###,##0.0");
        return formato.format(numero);
    }
}