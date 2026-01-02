/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import conexion.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import modelo.Categoria;

/**
 *
 * @author JEFFERSON
 */
public class CategoriasDao extends Conexion{
    public boolean guardar(Categoria objeto) {
        boolean respuesta = false;
        String msg;
        msg = "insert into categorias values(?,?,?)";
        try {
            cn = Conexion.getConexion();
            ps = cn.prepareStatement(msg);
            ps.setInt(1, 0);
            ps.setString(2, objeto.getDescripcion2());
            ps.setInt(3, objeto.getEstado2());

            if (ps.executeUpdate() > 0) {
                respuesta = true;
            }
            cn.close();
        } catch (SQLException e) {
            System.out.println("Error al guardar categoria: " + e);
        }
        return respuesta;
    }
    //METODO PARA CONSULTAR SI EXISTE LA CATEGORIA
    public boolean existeCategoria(String categoria) {
        boolean respuesta = false;
        String sql = "select descripcion from categorias where descripcion = '" + categoria + "';";
        Statement st;

        try {
            cn = Conexion.getConexion();
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                respuesta = true;
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar categoria: " + e);
        }
        return respuesta;
    }
    //Metodo actualizar
    public boolean actualizar(Categoria objeto, int idCategoria) {
        boolean respuesta = false;
        String msg;
        msg = "update categorias set descripcion=? where idCategoria = '"+ idCategoria +"'";

        try {
            cn = Conexion.getConexion();
            ps = cn.prepareStatement(msg);
            ps.setString(1, objeto.getDescripcion2());
            

            if (ps.executeUpdate() > 0) {
                respuesta = true;
            }
            cn.close();
        } catch (SQLException e) {
            System.out.println("Error al actualizar categoria: " + e);
        }
        return respuesta;
    }
    //Metodo eliminar
    public boolean eliminar( int idCategoria) {
        boolean respuesta = false;
        String msg;
        msg = "delete from categorias where idCategoria = '"+ idCategoria +"'";

        try {
            cn = Conexion.getConexion();
            ps = cn.prepareStatement(msg);
            ps.executeUpdate();
            

            if (ps.executeUpdate() > 0) {
                respuesta = true;
            }
            cn.close();
        } catch (SQLException e) {
            System.out.println("Error al eliminar categoria: " + e);
        }
        return respuesta;
    }
    
    
    public void CargarComboCategoria(JComboBox comboCat){
        
        String sql= "select * from categorias";
       
        try {
            cn= Conexion.getConexion();
            st= cn.createStatement();
            rs= st.executeQuery(sql);
            comboCat.removeAllItems();
            comboCat.addItem("Seleccione Categoria: ");
            while(rs.next()){
                comboCat.addItem(rs.getString("descripcion"));
            }
            cn.close();
        } catch (SQLException e) {
            System.out.println("Error al cargar categorias ");
        }
        
    }
    
    public int IdCategoria(JComboBox comboCat){
        String sql= "select * from categorias where descripcion = '"+comboCat.getSelectedItem()+"'";
        int obtenerIdCategoriaCombo = 0;
        try {
            cn = Conexion.getConexion();
            st=cn.createStatement();
            rs= st.executeQuery(sql);
            while(rs.next()){
                obtenerIdCategoriaCombo = rs.getInt("idCategoria");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener categoria");
        }
        return obtenerIdCategoriaCombo;
    }
    
    public void mostrarTablaCategoria(Categoria cat, JTable paramTable) {

        cn = Conexion.getConexion();

        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int filas, int columnas) {
                if (columnas == 3) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        
        TableRowSorter<TableModel> ordenarTable = new TableRowSorter<TableModel>(modelo);
        paramTable.setRowSorter(ordenarTable);

        modelo.addColumn("ID");
        modelo.addColumn("Descripción");
        modelo.addColumn("Estado");

        paramTable.setModel(modelo);
        String sql = "select * from categorias";

        try {
            ps = cn.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {

                cat.setIdCategoria(rs.getInt("idCategoria"));
                cat.setDescripcion2(rs.getString("descripcion"));
                cat.setEstado2(rs.getInt("estado"));
                
                
                modelo.addRow(cat.Registro());
            }
            paramTable.setModel(modelo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error tabla" + e.toString());

        } 
    }

    
    public boolean buscarTablaCategoria(Categoria cat, JTable paramTable) {

        cn = Conexion.getConexion();

        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int filas, int columnas) {
                if (columnas == 3) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        
        TableRowSorter<TableModel> ordenarTable = new TableRowSorter<TableModel>(modelo);
        paramTable.setRowSorter(ordenarTable);

        modelo.addColumn("ID");
        modelo.addColumn("Descripción");
        modelo.addColumn("Estado");

        paramTable.setModel(modelo);
        String sql = "select * from categorias where idCategoria = '%"+ cat.getBus()+"%' or descripcion like '%"+cat.getBus()+"%'";


        try {
            ps = cn.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {

                cat.setIdCategoria(rs.getInt("idCategoria"));
                cat.setDescripcion2(rs.getString("descripcion"));
                cat.setEstado2(rs.getInt("estado"));
                
                
                modelo.addRow(cat.Registro());
            }
            paramTable.setModel(modelo);
            return true;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error tabla" + e.toString());
            return false;

        } 
    }
}
