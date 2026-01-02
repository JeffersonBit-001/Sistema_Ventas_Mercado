/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import conexion.Conexion;
import java.sql.CallableStatement;
import java.sql.Connection;
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
public class ReporteClienteDao extends Conexion {


    /*Connection con;
    ResultSet rs;
    Statement st;
    CallableStatement cs;*/
    public boolean verficarBusqueda(Cliente cl, JTextField busqueda) {
        try {
            cl.setDni(Integer.parseInt(busqueda.getText()));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void mostrarDatos(JTable paramTable) {
        
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
        /*FormAdmSistema adm=new FormAdmSistema();
        modelo=(DefaultTableModel) adm.jTable1.getModel();*/
        TableRowSorter<TableModel> ordenarTable = new TableRowSorter<TableModel>(modelo);
        paramTable.setRowSorter(ordenarTable);

        String sql = "select fac.id as idFac, concat_ws(' ', cl.nombre,cl.apellido) as nom, " +
                        " " +
                        "                cl.dni as dni, fac.codigo, fac.fecha, fac.estado  from factura fac " +
                        "                inner join detalle_factura det " +
                        "                on fac.id = det.id_factura " +
                        "                inner join clientes cl " +
                        "                on fac.id_cliente = cl.id_cliente " +
                        "                inner join producto prod " +
                        "                on det.id_producto = prod.idProducto " +
                        "                group by fac.id " +
                        "                order by fac.id asc";

        modelo.addColumn("ID");
        modelo.addColumn("Cliente");
        modelo.addColumn("DNI");
        modelo.addColumn("Código");
        modelo.addColumn("Fecha");
        modelo.addColumn("Estado");
        paramTable.setModel(modelo);

        String[] datos = new String[6];
        try {
            cn = Conexion.getConexion();
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                for (int i = 0; i < 6; i++) {
                    datos[i] = rs.getString((i + 1));
                }

                modelo.addRow(datos);
            }

            paramTable.setModel(modelo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error de mostrar datos" + e.toString());
        }

    }

    public void buscarTablaReportCliente(JTable paramTable, Cliente cl) {
        
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
        /*FormAdmSistema adm=new FormAdmSistema();
        modelo=(DefaultTableModel) adm.jTable1.getModel();*/
        TableRowSorter<TableModel> ordenarTable = new TableRowSorter<TableModel>(modelo);
        paramTable.setRowSorter(ordenarTable);


        String consulta = "select fac.id as idFac, concat_ws(' ', cl.nombre,cl.apellido) as nom, " +
            " " +
            "                cl.dni as dni, fac.codigo ,fac.fecha, fac.estado from factura fac " +
            "                inner join detalle_factura det  " +
            "                on fac.id = det.id_factura  " +
            "                inner join clientes cl " +
            "                on fac.id_cliente = cl.id_cliente " +
            "                inner join producto prod " +
            "                on det.id_producto = prod.idProducto " +
            "                where cl.dni like '%"+cl.getNombre()+"%'or concat_ws(' ', cl.nombre,cl.apellido) like '%"+cl.getNombre()+"%' " +
            "                or date_format(fac.fecha,'%M') like '%"+cl.getNombre()+"%' " +
            "                group by fac.id " +
            "                order by fac.id asc";

        

        modelo.addColumn("ID");
        modelo.addColumn("Cliente");
        modelo.addColumn("DNI");
        modelo.addColumn("Código");
        modelo.addColumn("Fecha");
        modelo.addColumn("Estado");  
        paramTable.setModel(modelo);

        String[] datos = new String[6];
        try {
            cn = Conexion.getConexion();
            ps = cn.prepareStatement(consulta);
            rs = ps.executeQuery();

            while (rs.next()) {
                for (int i = 0; i < 6; i++) {
                    datos[i] = rs.getString((i + 1));
                }

                modelo.addRow(datos);
            }

            paramTable.setModel(modelo);

        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null, "Error de mostrar datos" + e.toString());

        }

    }

    public void eliminarTablaDetalleReportClienteUpdate(Cliente cl) {
        //String consulta = "update detalle_factura set detalle_factura.tipo2='E' where detalle_factura.id_factura=? and detalle_factura.tipo2='NE' and detalle_factura.tipo='C'";
        String consulta = "delete from detalle_factura where id_factura = "+cl.getId();
        
        try {

            cn = Conexion.getConexion();
            ps = cn.prepareStatement(consulta);
            ps.executeUpdate();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error3" + e.toString());

        }
    }

    public void eliminarTablaReportFacturaClienteUpdate(Cliente cl) {
        //String consulta = "update factura2 set factura2.tipo2='E' where detalle_factura.id_factura=? and detalle_factura.tipo2='NE' and detalle_factura.tipo='C'";
        String consulta = "delete from factura where id = "+cl.getId();
        
        try {

            cn = Conexion.getConexion();
            ps = cn.prepareStatement(consulta);
            ps.executeUpdate();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error3" + e.toString());

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
                cl.setVer("Correcto");
            }
        }
    }
    
    
    public void ActualizarTablaReportFacturaClienteUpdate(Cliente cl, String nom) {
        //String consulta = "update factura2 set factura2.tipo2='E' where detalle_factura.id_factura=? and detalle_factura.tipo2='NE' and detalle_factura.tipo='C'";
        String consulta = "update factura set estado = ? where id = "+cl.getId();
        
        try {

            cn = Conexion.getConexion();
            ps = cn.prepareStatement(consulta);
            ps.setString(1, nom);
            ps.executeUpdate();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error3" + e.toString());

        }
    }

}
