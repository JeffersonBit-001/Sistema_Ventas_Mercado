/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Dao.UsuarioDao;
import java.awt.Color;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.Usuario;
import vista.FormAdmSistema;
import vista.MenuTienda;

/**
 *
 * @author JEFFERSON
 */
public class ControladorTabbedPane implements ActionListener {

    FormAdmSistema adm;
    Usuario us;
    int k = 0;

    //FormEmpSistema emp;
    public ControladorTabbedPane(FormAdmSistema adm, Usuario us) {
        this.adm = adm;
        this.us = us;

        //this.emp = emp;
        this.adm.btnRecursosAdm.addActionListener(this);
        this.adm.btnPanUsuarios.addActionListener(this);
        this.adm.btnCerrarSessionUsuario.addActionListener(this);
        this.adm.setLocationRelativeTo(null);
        this.adm.setResizable(false);
        this.adm.setTitle("Panel Administrador");
        this.adm.setExtendedState(MAXIMIZED_BOTH);
        this.adm.txtNombreAdministrador.setForeground(Color.BLACK);
        adm.jTabbedPaneAdministrador.setVisible(false);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == adm.btnPanUsuarios) {
            if (k == 0) {
                Usuario usu = new Usuario();
                UsuarioDao consultausu = new UsuarioDao();
                ControladorFormUsuarios controlUsuarios = new ControladorFormUsuarios(usu, consultausu, adm);
                adm.jTabbedPaneAdministrador.setVisible(true);
                adm.jTabbedPaneAdministrador.setSelectedIndex(0);
                k++;
            } else {
                adm.jTabbedPaneAdministrador.setVisible(true);
                adm.jTabbedPaneAdministrador.setSelectedIndex(0);
            }
        }

        if (e.getSource() == adm.btnRecursosAdm) {
            MenuTienda emp = new MenuTienda();
            ControladorMenuEmpleado controlMenuEmp = new ControladorMenuEmpleado(emp);
            emp.jtxNombreEmpleado.setText(adm.txtNombreAdministrador.getText());
            emp.jtxNombreEmpleado.setForeground(Color.BLACK);
            emp.setVisible(true);
            adm.setVisible(false);

        }

        if (e.getSource() == adm.btnCerrarSessionUsuario) {
            int pregunta = JOptionPane.showConfirmDialog(null, "¿Estás seguro que deseas salir?", "  Pregunta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (pregunta == 0) {
                System.exit(0);
            }
        }
    }
}
