/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author JEFFERSON
 */
public abstract class Mostrar {
    
    private int idCategoria;
    private String descripcion2;
    private int estado2;
    private String bus
            ;
    public Mostrar() {
    }

    public Mostrar(int idCategoria, String descripcion2, int estado2, String bus) {
        this.idCategoria = idCategoria;
        this.descripcion2 = descripcion2;
        this.estado2 = estado2;
        this.bus = bus;
    }

    

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getDescripcion2() {
        return descripcion2;
    }

    public void setDescripcion2(String descripcion2) {
        this.descripcion2 = descripcion2;
    }

    public int getEstado2() {
        return estado2;
    }

    public void setEstado2(int estado2) {
        this.estado2 = estado2;
    }
    
    
    public abstract Object[] Registro();

    public String getBus() {
        return bus;
    }

    public void setBus(String bus) {
        this.bus = bus;
    }


    
}
