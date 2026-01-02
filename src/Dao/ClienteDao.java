/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;


import conexion.Conexion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import modelo.Cliente;

/**
 *
 * @author JEFFERSON
 */
public class ClienteDao extends Conexion {

    public void registrarCliente(Cliente cl) {
        
        String consulta = "insert into clientes(nombre,apellido,dni,direccion,celular,tipo) values (?,?,?,?,?,?)";
        try {
            cn = Conexion.getConexion();
            ps = cn.prepareStatement(consulta);
            
            ps.setString(1, cl.getNombre());
            ps.setString(2, cl.getApellido());
            ps.setInt(3, cl.getDni());
            ps.setString(4, cl.getCorreo());
            ps.setInt(5, cl.getTelefono());
            ps.setString(6, "NE");
            ps.executeUpdate();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error 1" + e.toString());
        }

    }

    public void modificarCliente(Cliente cl) {
        
        String consulta = "update clientes set clientes.nombre = ?,clientes.apellido = ?,clientes.dni = ?,clientes.direccion = ?,"
                + "clientes.celular = ? WHERE clientes.id_cliente = ? and clientes.tipo='NE';";
        try {
            cn = Conexion.getConexion();
            ps = cn.prepareStatement(consulta);
            ps.setString(1, cl.getNombre());
            ps.setString(2, cl.getApellido());
            ps.setInt(3, cl.getDni());
            ps.setString(4, cl.getCorreo());
            ps.setInt(5, cl.getTelefono());
            ps.setInt(6, cl.getId());

            ps.executeUpdate();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error 2" + e.toString());
        }
    }

    public void eliminarCliente(Cliente cl) {
        String consulta = "DELETE from clientes WHERE clientes.id_cliente=?;";

        
        try {

            cn = Conexion.getConexion();
            ps = cn.prepareStatement(consulta);
            ps.setInt(1, cl.getId());
            ps.executeUpdate();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error3");

        }
    }

    public void eliminarClienteUpdate(Cliente cl) {
        String consulta = "update clientes set clientes.tipo='E' WHERE clientes.id_cliente = ? and clientes.tipo='NE'";

        
        try {

            cn = Conexion.getConexion();
            ps = cn.prepareStatement(consulta);
            ps.setInt(1, cl.getId());
            ps.executeUpdate();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error3");

        }
    }

    public void mostrarTablaCliente(Cliente cl, JTable paramTable) {

        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int filas, int columnas) {
                if (columnas == 7) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        /*FormAdmSistema adm=new FormAdmSistema();
        modelo=(DefaultTableModel) adm.jTable1.getModel();*/
        TableRowSorter<TableModel> ordenarTable = new TableRowSorter<TableModel>(modelo);
        paramTable.setRowSorter(ordenarTable);

        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("DNI");
        modelo.addColumn("Dirección");
        modelo.addColumn("Celular");
        modelo.addColumn("Fecha");

        paramTable.setModel(modelo);
        String sql = "select clientes.id_cliente,clientes.nombre,clientes.apellido,"
                + "clientes.dni,clientes.direccion,clientes.celular,clientes.fecha "
                + "from clientes "
                + "where clientes.tipo='NE';";

        try {
            cn = Conexion.getConexion();
            ps = cn.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {

                cl.setId(rs.getInt(1));
                cl.setNombre(rs.getString(2));
                cl.setApellido(rs.getString(3));
                cl.setDni(rs.getInt(4));
                cl.setCorreo(rs.getString(5));
                cl.setTelefono(rs.getInt(6));
                cl.setFecha(rs.getString(7));
                /*for (int j=0;j<datos.length;j++){
                    datos[j]=rs.getString((j+1));
                    
                }*/
                modelo.addRow(cl.Registro());
            }
            paramTable.setModel(modelo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error tabla" + e.toString());

        }
    }

    public boolean buscarCliente(Cliente cl, JTable paramTable, JComboBox combo) {

        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int filas, int columnas) {
                if (columnas == 7) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        TableRowSorter<TableModel> ordenarTable = new TableRowSorter<TableModel>(modelo);
        paramTable.setRowSorter(ordenarTable);

        String combo2 = combo.getSelectedItem().toString();

        String consulta = "";

        if (combo2.equalsIgnoreCase("Id")) {
            consulta = "select clientes.id_cliente,clientes.nombre,clientes.apellido,"
                    + "clientes.dni,clientes.direccion,clientes.celular,clientes.fecha "
                    + "from clientes"
                    + " WHERE clientes.tipo='NE' and clientes.id_cliente =" + cl.getDni();

        } else if (combo2.equalsIgnoreCase("Dni")) {
            consulta = "select clientes.id_cliente,clientes.nombre,clientes.apellido,"
                    + "clientes.dni,clientes.direccion,clientes.celular,clientes.fecha "
                    + "from clientes"
                    + " WHERE clientes.tipo='NE' and clientes.dni=" + cl.getDni();
        }

        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("DNI");
        modelo.addColumn("Dirección");
        modelo.addColumn("Celular");
        modelo.addColumn("Fecha");

        paramTable.setModel(modelo);

        try {
            ps = cn.prepareStatement(consulta);

            rs = ps.executeQuery();

            while (rs.next()) {
                cl.setId(rs.getInt(1));
                cl.setNombre(rs.getString(2));
                cl.setApellido(rs.getString(3));
                cl.setDni(rs.getInt(4));
                cl.setCorreo(rs.getString(5));
                cl.setTelefono(rs.getInt(6));
                cl.setFecha(rs.getString(7));
                modelo.addRow(cl.Registro());
            }

            paramTable.setModel(modelo);
            return true;

        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null, "Elegir una opción correcta"+e.toString());
            return false;
        }
    }

    public List obtenerListaDni(Cliente cl) {
        //con = cn.EstablecerConexion();
        List<Cliente> ListaStDn = new ArrayList();
        String consulta = "select clientes.id_cliente, clientes.dni from clientes where clientes.tipo='NE'";
        try {
            cn = Conexion.getConexion();
            ps = cn.prepareStatement(consulta);
            rs = ps.executeQuery();
            while (rs.next()) {
                Cliente cliDn = new Cliente();
                cliDn.setId_fac_pag(rs.getInt("id_cliente"));
                cliDn.setDni(rs.getInt("dni"));
                ListaStDn.add(cliDn);
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Error obtener clientes dni" + e.toString());

        }
        return ListaStDn;
    }

    public void verificarDNI(Cliente cl) {
        List<Cliente> ListaStDn = this.obtenerListaDni(cl);
        for (int i = 0; i < ListaStDn.size(); i++) {
            if (cl.getDni() == ListaStDn.get(i).getDni()) {
                cl.setVer("Incorrecto");
            }
        }
    }
    
    
    public void verificarIDCli(Cliente cl) {
        List<Cliente> ListaStDn = this.obtenerListaDni(cl);
        for (int i = 0; i < ListaStDn.size(); i++) {
            if (cl.getId_fac_pag()== ListaStDn.get(i).getId_fac_pag()) {
                cl.setVer("Incorrecto");
            }
        }
    }
    
}
