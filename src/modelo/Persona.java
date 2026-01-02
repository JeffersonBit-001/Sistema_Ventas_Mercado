/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;


/**
 *
 * @author JEFFERSON
 */
public abstract class Persona {

    private int id=0;
    private String cod;
    private String nombre = "";
    private String apellido = "";
    private int dni = 0;
    private String correo = "";
    private String direccion = "";
    private int telefono = 0;
    private String fecha;
    private String hora;
    private double sueldo;
    private double benef_desc;
    private double pag_final;
    private int id_fac_pag;
    private String ver = "";
    private String ver2 = "";

    public Persona() {
    }

    public Persona(String cod, String nombre, String apellido, int dni, String correo, String direccion,
            int telefono, int id, String fecha, double sueldo, double benef_desc, double pag_final, int id_fac_pag, String hora, String ver, String ver2) {
        this.cod = cod;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.correo = correo;
        this.direccion = direccion;
        this.telefono = telefono;
        this.id = id;
        this.fecha = fecha;
        this.sueldo = sueldo;
        this.benef_desc = benef_desc;
        this.pag_final = pag_final;
        this.id_fac_pag = id_fac_pag;
        this.hora = hora;
        this.ver = ver;
        this.ver2 = ver2;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    public double getBenef_desc() {
        return benef_desc;
    }

    public void setBenef_desc(double benef_desc) {
        this.benef_desc = benef_desc;
    }

    public double getPag_final() {
        return pag_final;
    }

    public void setPag_final(double pag_final) {
        this.pag_final = pag_final;
    }

    public int getId_fac_pag() {
        return id_fac_pag;
    }

    public void setId_fac_pag(int id_fac_pag) {
        this.id_fac_pag = id_fac_pag;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getVer2() {
        return ver2;
    }

    public void setVer2(String ver2) {
        this.ver2 = ver2;
    }

    public abstract double pago();

    public abstract double descuento_Beneficio();

    public abstract double pagoFinal();

    public abstract int ccontador1();

    public abstract Object[] Registro();

}
