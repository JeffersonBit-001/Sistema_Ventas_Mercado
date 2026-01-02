
package modelo;


public class Producto extends Mostrar {
    //Atributos
    private int idProducto;
    private String nombre;
    private int cantidad;
    private double precio;
    private String descripcion;
    
    private int estado;
    private String nomCat;
    private int comprobar;
    private int cantidad2;
    //Constructor

    public Producto() {
    }
    

    public Producto(int idProducto, String nombre, int cantidad, double precio, String descripcion, int estado, String nomCat, int comprobar, int cantidad2) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.descripcion = descripcion;
        this.estado = estado;
        this.nomCat = nomCat;
        this.comprobar = comprobar;
        this.cantidad2 = cantidad2;
    }

    public Producto(int idProducto, String nombre, int cantidad, double precio, String descripcion, int estado, String nomCat, int comprobar, int cantidad2, int idCategoria, String descripcion2, int estado2, String bus) {
        super(idCategoria, descripcion2, estado2, bus);
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.descripcion = descripcion;
        this.estado = estado;
        this.nomCat = nomCat;
        this.comprobar = comprobar;
        this.cantidad2 = cantidad2;
    }

    
    

    

   
   
    //Metodos set and get

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    
    public String getNomCat() {
        return nomCat;
    }

    public void setNomCat(String nomCat) {
        this.nomCat = nomCat;
    }
    
    
    
    
    @Override
    public Object[] Registro() {
        Object[] fila = {getIdProducto(), getNombre(),
            getCantidad(), getPrecio(), getDescripcion(), super.getDescripcion2()};
        return fila;
    }

    public int getComprobar() {
        return comprobar;
    }

    public void setComprobar(int comprobar) {
        this.comprobar = comprobar;
    }

    public int getCantidad2() {
        return cantidad2;
    }

    public void setCantidad2(int cantidad2) {
        this.cantidad2 = cantidad2;
    }

    
}
