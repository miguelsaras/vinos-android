package com.example.migue.vinos;

/**
 * Created by migue on 28/01/2018.
 */

public class Vinos {


    private Long id;
    private String nombre;
    private String denominacion;
    private boolean probado;
    private String tipo;
    private  String descripcion;
    private int rating;
    private String imagen;

    public Vinos(Long id ,String nombre, String denominacion, boolean probado, String tipo, String descripcion, int rating, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.denominacion = denominacion;
        this.probado = probado;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.rating = rating;
        this.imagen = imagen;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public boolean isProbado() {
        return probado;
    }

    public void setProbado(boolean probado) {
        this.probado = probado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
