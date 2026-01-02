/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author JEFFERSON
 */
public class Cliente extends Persona implements detalle_pago {

    public static int getContadorcli() {
        return contadorcli;
    }

    public static void setContadorcli(int aContadorcli) {
        contadorcli = aContadorcli;
    }

    private int cant_ser;
    private double pago;
    private double igv = 0.18;
    private static int contadorcli = 0;
    private int id_iden = 0;
    private int id_iden2 = 0;
    private String usu = "";
    //private String ver;
    private String cod_pago;
    
    public Cliente() {

    }

    public Cliente(int cant_ser, double pago, String cod_pago) {
        this.cant_ser = cant_ser;
        this.pago = pago;
        this.cod_pago = cod_pago;
    }

    public Cliente(int cant_ser, double pago, String cod_pago, String cod, String nombre, String apellido, int dni, String correo, String direccion, int telefono, int id, String fecha, double sueldo, double benef_desc, double pag_final, int id_fac_pag, String hora, String ver, String ver2) {
        super(cod, nombre, apellido, dni, correo, direccion, telefono, id, fecha, sueldo, benef_desc, pag_final, id_fac_pag, hora, ver, ver2);
        this.cant_ser = cant_ser;
        this.pago = pago;
        this.cod_pago = cod_pago;
    }

    

    public int getCant_ser() {
        return cant_ser;
    }

    public void setCant_ser(int cant_ser) {
        this.cant_ser = cant_ser;
    }

    public double getPago() {
        return pago;
    }

    public void setPago(double pago) {
        this.pago = pago;
    }

    public double getIgv() {
        return igv;
    }

    public void setIgv(double igv) {
        this.igv = igv;
    }

    public int getId_iden() {
        return id_iden;
    }

    public void setId_iden(int id_iden) {
        this.id_iden = id_iden;
    }

    public int getId_iden2() {
        return id_iden2;
    }

    public void setId_iden2(int id_iden2) {
        this.id_iden2 = id_iden2;
    }

    public String getUsu() {
        return usu;
    }

    public void setUsu(String usu) {
        this.usu = usu;
    }

    @Override
    public double pago() {
        return getPago();
    }

    @Override
    public double descuento_Beneficio() {

        return getIgv() * getPago();
    }

    @Override
    public double pagoFinal() {
        return pago() + descuento_Beneficio();
    }

    @Override
    public int ccontador1() {
        return 0;
    }

    @Override
    public Object[] Registro() {
        Object[] fila = {super.getId(), super.getNombre(),
            super.getApellido(), super.getDni(), super.getCorreo(), super.getTelefono(), super.getFecha()};
        return fila;
    }

    @Override
    public Object[] RegistroTablaPagos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getCod_pago() {
        return cod_pago;
    }

    public void setCod_pago(String cod_pago) {
        this.cod_pago = cod_pago;
    }

    

}
