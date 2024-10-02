package com.example.myapplication.Network.DTO;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import java.util.List;

public class MovimientoAdaptador extends RecyclerView.Adapter<MovimientoAdaptador.ViewHolder> {
    private List<MovimientoDTO> movimientos;

    public MovimientoAdaptador(List<MovimientoDTO> movimientos) {
        this.movimientos = movimientos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movimientorender, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovimientoDTO movimiento = movimientos.get(position);
        holder.bind(movimiento);
    }

    @Override
    public int getItemCount() {
        return movimientos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tipoMovimientoView;
        private TextView fechaView;
        private TextView valorView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tipoMovimientoView = itemView.findViewById(R.id.tipoMovimientoItem);
            fechaView = itemView.findViewById(R.id.fechaItem);
            valorView = itemView.findViewById(R.id.valorItem);
        }

        public void bind(MovimientoDTO movimiento) {
            tipoMovimientoView.setText("Tipo de Movimiento: " + movimiento.getTipoMovimiento());
            valorView.setText("Valor: " + movimiento.getValor());
            fechaView.setText("Fecha: " + movimiento.getFecha());
        }
    }

}