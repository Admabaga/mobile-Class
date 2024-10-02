package com.example.myapplication;

import android.app.Activity;
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

import java.text.DecimalFormat;

public class Lobby extends AppCompatActivity {
    private Button recarga;
    private Button retiro;
    private Button movimiento;
    private Button transferencia;
    private Button cerrarSesion;
    private TextView saldo;
    private TextView numeroCuenta;
    private TextView estado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lobby);
        Intent intent = getIntent();
        LoginDTO datosRecibidos = (LoginDTO) intent.getSerializableExtra("datos");
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
        saldo = findViewById(R.id.saldo);
        estado = findViewById(R.id.estado);
        numeroCuenta = findViewById(R.id.numeroCuenta);

        Intent intentActualizar = getIntent();
        LoginDTO actualizarData = (LoginDTO) intentActualizar.getSerializableExtra("datosActualizados");

        // Combinar los datos originales con los actualizados
        if (actualizarData != null) {
            renderizarInfoCuenta(saldo, numeroCuenta, estado, actualizarData);

        }else {
            renderizarInfoCuenta(saldo, numeroCuenta, estado, datosRecibidos);
        }

        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Lobby.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        recarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Lobby.this, Recargas.class);
                intent.putExtra("datos", datosRecibidos);
                startActivity(intent);
            }
        });

        retiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Lobby.this, Retiros.class);
                intent.putExtra("datos", datosRecibidos);
                startActivity(intent);
            }
        });

        movimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Lobby.this, Movimiento.class);
                intent.putExtra("datos", datosRecibidos);
                startActivity(intent);
            }
        });

        transferencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Lobby.this, Transferencias.class);
                intent.putExtra("datos", datosRecibidos);
                startActivity(intent);
            }
        });

    }

    public void renderizarInfoCuenta(TextView saldo, TextView numeroCuenta, TextView estado, LoginDTO datos){
        String saldoTexto = "Saldo: " + datos.getSaldo();
        String numeroCuentaTexto = "Numero de cuenta: " + datos.getNumeroCuenta();
        String estadoTexto = datos.getEstado() ? "Cuenta: Activa" : "Cuenta: Inactiva";
        saldo.setText(saldoTexto);
        numeroCuenta.setText(numeroCuentaTexto);
        estado.setText(estadoTexto);
    }

}