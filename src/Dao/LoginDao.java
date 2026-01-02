/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import conexion.Conexion;
import javax.swing.JOptionPane;
import modelo.Usuario;

/**
 *
 * @author JEFFERSON
 */
public class LoginDao extends Conexion{
    
    
    public void IngresarLoginUsuario(Usuario us){

        String sql="select "
                    + "concat_ws(' ', us.nombre,us.apellido) as Nom_com,"
                    + "us.usuario,us.pass,"
                    + "ro.nombre as rol from usuarios as us "
                + "inner join roles ro "
                + "on us.id_rol = ro.id WHERE us.usuario=? AND us.pass=? ";

        try {
            
            
            cn= conexion.Conexion.getConexion();
            ps = cn.prepareStatement(sql);
            
            ps.setString(1,us.getUsuario());
            ps.setString(2, us.getPass());
            
            rs=ps.executeQuery();
            
            if (rs.next()){
                us.setUsuario(rs.getString("usuario"));
                us.setPass(rs.getString("pass"));
                us.setRol(rs.getString("rol"));
                us.setIdentificacion(rs.getString("Nom_com"));
            } else {
                us.setRol("Nada");
                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrecta");
            } 
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }
}
