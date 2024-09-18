package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Lobby extends AppCompatActivity {
    private Button recarga;
    private Button retiro;
    private Button movimiento;
    private Button transferencia;
    private Button cerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lobby);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recarga = findViewById(R.id.botonRecarga);
        retiro = findViewById(R.id.botonRetiro);
        movimiento = findViewById(R.id.botonMovimiento);
        transferencia = findViewById(R.id.botonTransferencia);
        cerrarSesion = findViewById(R.id.buttonCerrarSesion);

        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Lobby.this, MainActivity.class);
                startActivity(intent);
            }
        });

        recarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Lobby.this, Recargas.class);
                startActivity(intent);
            }
        });

        retiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Lobby.this, Retiros.class);
                startActivity(intent);
            }
        });

        movimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Lobby.this, Movimiento.class);
                startActivity(intent);
            }
        });

        transferencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Lobby.this, Transferencias.class);
                startActivity(intent);
            }
        });
    }
}