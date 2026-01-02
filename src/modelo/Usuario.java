/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;


/**
 *
 * @author JEFFERSON
 */
public class Usuario extends Persona implements detalle_pago {

    private String usuario = "";
    private String pass = "";
    private String rol = "";
    private int cant_aut_ven;
    private int contadorus = 0;
    private int contadorem = 0;
    private double precio_adicion;
    private String identificacion;

    public Usuario() {

    }

    public Usuario(String usuario, String pass, String rol, int cant_aut_ven, String cod, String nombre, String apellido,
            int dni, String correo, String direccion, int telefono, int id, String fecha, double sueldo, double benef_desc, double pag_final,
            double precio_adicion, int id_fac_pag, String hora, String identificacion, String ver,String ver2) {
        super(cod, nombre, apellido, dni, correo, direccion, telefono, id, fecha, sueldo, benef_desc, pag_final, id_fac_pag, hora,ver,ver2);
        this.usuario = usuario;
        this.pass = pass;
        this.rol = rol;
        this.cant_aut_ven = cant_aut_ven;
        this.precio_adicion = precio_adicion;
        this.identificacion = identificacion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public int getCant_aut_ven() {
        return cant_aut_ven;
    }

    public void setCant_aut_ven(int cant_aut_ven) {
        this.cant_aut_ven = cant_aut_ven;
    }

    public double getPrecio_adicion() {
        return precio_adicion;
    }

    public void setPrecio_adicion(double precio_adicion) {
        this.precio_adicion = precio_adicion;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    @Override
    public double pago() {
        if (getRol().equals("Administrador")) {
            return 1400;

        }
        if (getRol().equals("Empleado")) {
            return 1200;
        }
        return 0;
    }

    @Override
    public double descuento_Beneficio() {
        return getCant_aut_ven() * getPrecio_adicion();
//super.setBenef_desc(Integer.parseInt(jt.getText())*Integer.parseInt(jt2.getText()));
    }

    @Override
    public double pagoFinal() {
        return pago() + descuento_Beneficio();
    }

    @Override
    public int ccontador1() {
        return contadorus++;
    }

    public int ccontador2() {
        return contadorem++;
    }

    @Override
    public Object[] Registro() {
        Object[] fila = {super.getId(), super.getNombre(),
            super.getApellido(), super.getDni(), super.getCorreo(), super.getTelefono(), getUsuario(),
            getPass(), getRol()};
        return fila;

    }

    @Override
    public Object[] RegistroTablaPagos() {
        Object[] fila2 = {super.getId_fac_pag(), super.getNombre(), super.getDni(),
            super.getSueldo(), getCant_aut_ven(), getPrecio_adicion(), super.getBenef_desc(),
            super.getPag_final(), super.getHora(), super.getFecha()};
        return fila2;
    }

}
