package com.example.myapplication.Network.DTO;

public class MovimientoDTO {
    private Long id;
    private String tipoMovimiento;
    private String fecha;
    private String valor;

    public MovimientoDTO(Long id, String tipoMovimiento, String fecha, String valor) {
        this.id = id;
        this.tipoMovimiento = tipoMovimiento;
        this.fecha = fecha;
        this.valor = valor;
    }

    public MovimientoDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
