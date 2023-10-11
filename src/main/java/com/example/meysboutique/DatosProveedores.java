package com.example.meysboutique;

public class DatosProveedores {
    private String nombreproveedor;
    private String nombreencargado;
    private String direccion;
    private String telefono;

    public DatosProveedores(String nombreproveedor, String nombreencargado, String direccion, String telefono) {
        this.nombreproveedor = nombreproveedor;
        this.nombreencargado = nombreencargado;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public String getNombreProveedor() {
        return nombreproveedor;
    }

    public void setNombreProveedor(String nombreproveedor) {
        this.nombreproveedor = nombreproveedor;
    }

    public String getNombreEncargado() {
        return nombreencargado;
    }

    public void setNombreEncargado(String nombreencargado) {
        this.nombreencargado = nombreencargado;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
