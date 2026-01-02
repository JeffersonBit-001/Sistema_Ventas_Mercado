/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;


import conexion.Conexion;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import modelo.Usuario;

/**
 *
 * @author JEFFERSON
 */
public class UsuarioDao extends Conexion {

    /*Connection con;
    ResultSet rs;
    Statement st;
    PreparedStatement ps;*/

    public void registrarUsuario(Usuario us) {

        String consulta = "insert into usuarios(nombre,apellido,dni,correo,celular,usuario,pass,id_rol) values (?,?,?,?,?,?,?,?)";
        int idddd= 0;
        if(us.getRol().equals("Administrador")){
            idddd = 1;
        } else if (us.getRol().equals("Empleado")){
            idddd = 2;
        }

        
        try {
            
            cn = Conexion.getConexion();
            ps = cn.prepareStatement(consulta);

            ps.setString(1, us.getNombre());
            ps.setString(2, us.getApellido());
            ps.setInt(3, us.getDni());
            ps.setString(4, us.getCorreo());
            ps.setInt(5, us.getTelefono());
            ps.setString(6, us.getUsuario());
            ps.setString(7, us.getPass());
            ps.setInt(8, idddd);

            ps.executeUpdate();
            

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error" + e.toString());
            

        }
    }

    public void modificarUsuario(Usuario us) {

        String consulta = "UPDATE usuarios SET nombre = ?, apellido = ?,dni = ?,correo = ?,celular = ?,"
                + "usuario = ?, pass = ?,id_rol = ? WHERE id = ?";
        int idddd= 0;
        if(us.getRol().equals("Administrador")){
            idddd = 1;
        } else if (us.getRol().equals("Empleado")){
            idddd = 2;
        }
        try {
            
            cn = Conexion.getConexion();
            ps = cn.prepareStatement(consulta);
            
            ps.setString(1, us.getNombre());
            ps.setString(2, us.getApellido());
            ps.setInt(3, us.getDni());
            ps.setString(4, us.getCorreo());
            ps.setInt(5, us.getTelefono());
            ps.setString(6, us.getUsuario());
            ps.setString(7, us.getPass());
            ps.setInt(8, idddd);
            ps.setInt(9, us.getId());
            ps.executeUpdate();

            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error mod" + e.toString());
            
        }
    }

    public void eliminar(Usuario us) {

        String consulta = "DELETE from usuarios WHERE id=?;";

        try {
            
            cn = Conexion.getConexion();
            ps = cn.prepareStatement(consulta);


            ps.setInt(1, us.getId());
            ps.executeUpdate();

            
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error");
            
        }
    }
    
    

    public void mostrarTablaUsuario(Usuario us, JTable paramTable) {
        
        
        
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int filas, int columnas) {
                if (columnas == 9) {
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
        modelo.addColumn("Correo");
        modelo.addColumn("Celular");
        modelo.addColumn("Usuario");
        modelo.addColumn("Pass");
        modelo.addColumn("Rol");

        paramTable.setModel(modelo);
        String sql = "select us.id,us.nombre,us.apellido,us.dni,"
                + "us.correo,us.celular,us.usuario,us.pass,"
                + "rol.nombre from usuarios us inner join roles rol "
                + "on rol.id = us.id_rol ";

        
        try {
            cn = Conexion.getConexion();
            ps = cn.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {
                
                us.setId(rs.getInt(1));
                us.setNombre(rs.getString(2));
                us.setApellido(rs.getString(3));
                us.setDni(rs.getInt(4));
                us.setCorreo(rs.getString(5));
                us.setTelefono(rs.getInt(6));
                us.setUsuario(rs.getString(7));
                us.setPass(rs.getString(8));
                us.setRol(rs.getString(9));
                /*for (int j=0;j<datos.length;j++){
                    datos[j]=rs.getString((j+1));
                    
                }*/       
                modelo.addRow(us.Registro());
            }
            paramTable.setModel(modelo);
            

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error" + e.toString());
           
        }
    }
    
    
    public boolean buscarUsuario(Usuario us,JTable paramTable){
        
        

        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int filas, int columnas) {
                if (columnas == 9) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        TableRowSorter<TableModel> ordenarTable = new TableRowSorter<TableModel>(modelo);
        paramTable.setRowSorter(ordenarTable);



           String sql = "SELECT us.id, us.nombre, us.apellido, us.dni, " +
             "us.correo, us.celular, us.usuario, us.pass, " +
             "rol.nombre " +
             "FROM usuarios us " +
             "INNER JOIN roles rol ON rol.id = us.id_rol " +
             "WHERE us.id like '%" + us.getNombre() + "%' "+
             "OR us.nombre LIKE '%" + us.getNombre() + "%' "+
             "OR us.apellido LIKE '%" + us.getNombre() + "%' "+
             "OR us.correo LIKE '%" + us.getNombre() + "%' "+
             "OR us.celular LIKE '%" + us.getNombre() + "%' "+
             "OR us.usuario LIKE '%" + us.getNombre() + "%' "+
             "OR rol.nombre LIKE '%" + us.getNombre() + "%' "+
             "OR us.dni LIKE '%" + us.getNombre() + "%' ";
        

        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("DNI");
        modelo.addColumn("Correo");
        modelo.addColumn("Celular");
        modelo.addColumn("Usuario");
        modelo.addColumn("Pass");
        modelo.addColumn("Rol");


        paramTable.setModel(modelo);
        

        try {
            cn = Conexion.getConexion();
            ps = cn.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {
                us.setId(rs.getInt(1));
                us.setNombre(rs.getString(2));
                us.setApellido(rs.getString(3));
                us.setDni(rs.getInt(4));
                us.setCorreo(rs.getString(5));
                us.setTelefono(rs.getInt(6));
                us.setUsuario(rs.getString(7));
                us.setPass(rs.getString(8));
                us.setRol(rs.getString(9));
                modelo.addRow(us.Registro());
            }

            paramTable.setModel(modelo);
            return true;

        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null, "Elegir una opción correcta"+e.toString());
            return false;
        }
    }
    
    public List obtenerLista(Usuario us) {
        //con = cn.EstablecerConexion();
        List<Usuario> ListaStUs = new ArrayList();
        String consulta = "select dni,id,usuario from usuarios";
        try {
            cn = Conexion.getConexion();
            ps = cn.prepareStatement(consulta);

            rs = ps.executeQuery();
            while (rs.next()) {
                Usuario usDn = new Usuario();
                usDn.setDni(rs.getInt("dni"));
                usDn.setId_fac_pag(rs.getInt("id"));
                usDn.setUsuario(rs.getString("usuario"));
                ListaStUs.add(usDn);
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Error obtener clientes dni" + e.toString());

        }
        return ListaStUs;
    }

    public void verificarDNI(Usuario us) {
        List<Usuario> ListaStUs = this.obtenerLista(us);
        for (int i = 0; i < ListaStUs.size(); i++) {
            if (us.getDni() == ListaStUs.get(i).getDni()) {
                us.setVer("Incorrecto");
            }
        }
    }
    

    public void verificarID(Usuario us) {
        List<Usuario> ListaStUs = this.obtenerLista(us);
        for (int i = 0; i < ListaStUs.size(); i++) {
            if (us.getId_fac_pag()== ListaStUs.get(i).getId_fac_pag()) {
                us.setVer("Incorrecto");
            }
        }
    }
    
    
    public void verificarUsuario(Usuario us) {
        List<Usuario> ListaStUs = this.obtenerLista(us);
        for (int i = 0; i < ListaStUs.size(); i++) {
            if (ListaStUs.get(i).getUsuario().equals(us.getUsuario())) {
                us.setVer2("Incorrecto");
            }
        }
    }
    
}
