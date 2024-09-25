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

import com.example.myapplication.Network.DTO.LoginDTO;

public class Retiros extends AppCompatActivity {
    private Button volverLobby;
    private TextView saldo;
    private TextView numeroCuenta;
    private TextView estado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_retiros);
        Intent intent = getIntent();
        LoginDTO datosRecibidos = (LoginDTO) intent.getSerializableExtra("datos");
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        volverLobby = findViewById(R.id.volverLobby);
        saldo = findViewById(R.id.saldoRetiro);
        estado = findViewById(R.id.estado);
        numeroCuenta = findViewById(R.id.numeroCuenta);
        renderizarInfoCuenta(saldo, numeroCuenta, estado, datosRecibidos);

        volverLobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Retiros.this, Lobby.class);
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
}