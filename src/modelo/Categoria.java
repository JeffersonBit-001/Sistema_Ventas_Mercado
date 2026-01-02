package modelo;

public class Categoria extends Mostrar{

    public Categoria() {
    }

    public Categoria(int idCategoria, String descripcion2, int estado2, String bus) {
        super(idCategoria, descripcion2, estado2, bus);
    }

    
    
    
    @Override
    public Object[] Registro() {
        Object[] fila = {super.getIdCategoria(), super.getDescripcion2(), super.getEstado2()};
        return fila;
    }

}
