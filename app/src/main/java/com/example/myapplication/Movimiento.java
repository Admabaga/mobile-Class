package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Network.ClienteApi;
import com.example.myapplication.Network.DTO.LoginDTO;
import com.example.myapplication.Network.DTO.MovimientoAdaptador;
import com.example.myapplication.Network.DTO.MovimientoDTO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Movimiento extends AppCompatActivity {
    private Button volverLobby;
    private TextView saldo;
    private TextView numeroCuenta;
    private TextView estado;
    List<MovimientoDTO> movimientos = new ArrayList<>();
    private ClienteApi clienteApi;
    private TextView respuestaServer;
    private RecyclerView recyclerView;
    private MovimientoAdaptador adaptadorData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_movimiento);
        Intent intent = getIntent();
        LoginDTO datosRecibidos = (LoginDTO) intent.getSerializableExtra("datos");
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        volverLobby = findViewById(R.id.volverLobby);
        saldo = findViewById(R.id.saldoMovimiento);
        estado = findViewById(R.id.estado);
        numeroCuenta = findViewById(R.id.numeroCuenta);
        respuestaServer = findViewById(R.id.movimientoResponse);
        recyclerView = findViewById(R.id.render);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        renderizarInfoCuenta(saldo, numeroCuenta, estado, datosRecibidos);

        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://172.16.57.231:8080")
                .baseUrl("http://192.168.20.22:8080")
//                .baseUrl("https://banco-backend-znok.onrender.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        clienteApi = retrofit.create(ClienteApi.class);
        Long idCuenta = datosRecibidos.getIdCuenta();

        enviarPeticionTraerMovimientos(movimientos, idCuenta);
        volverLobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Movimiento.this, Lobby.class);
                startActivity(intent);
                finish();
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
    private void enviarPeticionTraerMovimientos(final List<MovimientoDTO> movimientos, Long idCuenta) {
        Call<List<MovimientoDTO>> call = clienteApi.traerMovimientos(idCuenta);
        call.enqueue(new Callback<List<MovimientoDTO>>() {
            @Override
            public void onResponse(Call<List<MovimientoDTO>> call, Response<List<MovimientoDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    movimientos.addAll(response.body());
                    actualizarRecyclerView();
                } else {
                    mostrarError("Error desconocido.");
                }
            }

            @Override
            public void onFailure(Call<List<MovimientoDTO>> call, Throwable t) {
                mostrarError("Error de conexión: " + t.getMessage());
            }
        });
    }

    private void mostrarError(String mensajeError) {
        respuestaServer.setText(mensajeError);
    }
    private void actualizarRecyclerView() {
        adaptadorData = new MovimientoAdaptador(movimientos);
        recyclerView.setAdapter(adaptadorData);
    }

}