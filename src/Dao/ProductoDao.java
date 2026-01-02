/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import conexion.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import modelo.Categoria;
import modelo.Producto;

/**
 *
 * @author JEFFERSON
 */
public class ProductoDao extends Conexion{

    public boolean guardar(Producto objeto) {
        boolean respuesta = false;
        String msg;
        msg = "insert into producto(nombre,cantidad,precio,descripcion,idCategoria,estado) values(?,?,?,?,?,?)";
        try {
            cn = Conexion.getConexion();
            ps = cn.prepareStatement(msg);
            ps.setString(1, objeto.getNombre());
            ps.setInt(2, objeto.getCantidad());
            ps.setDouble(3, objeto.getPrecio());
            ps.setString(4, objeto.getDescripcion());
            ps.setInt(5, objeto.getIdCategoria());
            ps.setInt(6, objeto.getEstado());

            if (ps.executeUpdate() > 0) {
                respuesta = true;
            }
            cn.close();
        } catch (SQLException e) {
            System.out.println("Error al guardar producto: " + e);
        }
        return respuesta;
    }

    //METODO PARA CONSULTAR SI EXISTE el producto o ya esta registrado
    public boolean existeProducto(String producto) {
        boolean respuesta = false;
        String sql = "select nombre from producto where nombre = '" + producto + "';";
        Statement st;

        try {
            cn = Conexion.getConexion();
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                respuesta = true;
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar producto: " + e);
        }
        return respuesta;
    }

    //actualizar
    public boolean actualizar(Producto objeto, int idProducto) {
        boolean respuesta = false;
        String msg;
        msg = "update producto set nombre=?, cantidad=?, precio=?, descripcion=?, idCategoria=?, estado=? where idProducto = '" + idProducto + "'";

        try {
            cn = Conexion.getConexion();
            ps = cn.prepareStatement(msg);
            ps.setString(1, objeto.getNombre());
            ps.setInt(2, objeto.getCantidad());
            ps.setDouble(3, objeto.getPrecio());
            ps.setString(4, objeto.getDescripcion());
            ps.setInt(5, objeto.getIdCategoria());
            ps.setInt(6, objeto.getEstado());

            if (ps.executeUpdate() > 0) {
                respuesta = true;
            }
            cn.close();
        } catch (SQLException e) {
            System.out.println("Error al actualizar producto: " + e);
        }
        return respuesta;
    }

    //Metodo eliminar
    public boolean eliminar(int idProducto) {
        boolean respuesta = false;
        String msg;
        msg = "delete from producto where idProducto = '" + idProducto + "'";

        try {
            cn = Conexion.getConexion();
            ps = cn.prepareStatement(msg);
            ps.executeUpdate();

            if (ps.executeUpdate() > 0) {
                respuesta = true;
            }
            cn.close();
        } catch (SQLException e) {
            System.out.println("Error al eliminar producto: " + e);
        }
        return respuesta;
    }
    
    
    public void mostrarTablaProducto(Producto prod, JTable paramTable) {

        cn = Conexion.getConexion();

        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int filas, int columnas) {
                if (columnas == 6) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        
        TableRowSorter<TableModel> ordenarTable = new TableRowSorter<TableModel>(modelo);
        paramTable.setRowSorter(ordenarTable);

        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Precio");
        modelo.addColumn("Descripcion");
        modelo.addColumn("Categoría");

        paramTable.setModel(modelo);
        String sql = "select prod.idProducto, prod.nombre, prod.cantidad, "
                + "prod.precio,prod.descripcion as desprod,cat.descripcion as descat from producto prod " +
                    "inner join categorias cat " +
                    "on prod.idCategoria = cat.idCategoria";

        try {
            ps = cn.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {

                prod.setIdProducto(rs.getInt("idProducto"));
                prod.setNombre(rs.getString("nombre"));
                prod.setCantidad(rs.getInt("cantidad"));
                prod.setPrecio(rs.getDouble("precio"));
                prod.setDescripcion(rs.getString("desprod"));
                prod.setDescripcion2(rs.getString("descat"));
                
                
                modelo.addRow(prod.Registro());
            }
            paramTable.setModel(modelo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error tabla" + e.toString());

        } 
    }
    
    public boolean buscarTablaProductos(Producto prod, JTable paramTable) {

        cn = Conexion.getConexion();

        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int filas, int columnas) {
                if (columnas == 6) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        
        TableRowSorter<TableModel> ordenarTable = new TableRowSorter<TableModel>(modelo);
        paramTable.setRowSorter(ordenarTable);

        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Precio");
        modelo.addColumn("Descripcion");
        modelo.addColumn("Categoría");

        paramTable.setModel(modelo);
        String sql = "select prod.idProducto, prod.nombre, prod.cantidad, "
                + "prod.precio,prod.descripcion as desprod,cat.descripcion as descat from producto prod " +
                    "inner join categorias cat " +
                    "on prod.idCategoria = cat.idCategoria " +
             "WHERE prod.idCategoria LIKE '%"+ prod.getBus()+"%' "+
             "OR cat.descripcion LIKE '%"+ prod.getBus()+"%' "+
             "OR prod.nombre LIKE '%"+ prod.getBus()+"%' "+
             "OR prod.cantidad LIKE '%"+ prod.getBus()+"%' "+
             "OR prod.precio LIKE '%"+ prod.getBus()+"%' "+
             "OR prod.idCategoria LIKE '%"+ prod.getBus()+"%' ";


        try {
            ps = cn.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {

                prod.setIdProducto(rs.getInt("idProducto"));
                prod.setNombre(rs.getString("nombre"));
                prod.setCantidad(rs.getInt("cantidad"));
                prod.setPrecio(rs.getDouble("precio"));
                prod.setDescripcion(rs.getString("desprod"));
                prod.setDescripcion2(rs.getString("descat"));
                
                
                modelo.addRow(prod.Registro());
            }
            paramTable.setModel(modelo);
            return true;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error tabla" + e.toString());
            return false;

        } 
    }

}
