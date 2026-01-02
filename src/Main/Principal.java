/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;

import Controlador.ControladorFormLogin;
import Dao.LoginDao;
import modelo.Usuario;
import vista.FormLogin;

/**
 *
 * @author JEFFERSON
 */
public class Principal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        FormLogin log = new FormLogin();
        
        Usuario usu = new Usuario();

        LoginDao consultalogin = new LoginDao();
        
        ControladorFormLogin controlLogin = new ControladorFormLogin(usu, consultalogin, log);
        
        
        
    }
    
}
