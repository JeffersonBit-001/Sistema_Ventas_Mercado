/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;


import Dao.ClienteDao;
import vista.InterGestionarCliente;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import static java.awt.image.ImageObserver.WIDTH;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import modelo.Cliente;

/**
 *
 * @author JEFFERSON
 */
public class ControladorCliente implements ActionListener, MouseListener {

    InterGestionarCliente interCli;
    Cliente cl;
    ClienteDao cscl;

    public ControladorCliente(InterGestionarCliente interCli, Cliente cl, ClienteDao cscl) {
        this.interCli = interCli;
        this.cl = cl;
        this.cscl = cscl;
        this.interCli.btnRegistrarClie.addActionListener(this);
        this.interCli.btnModificarCliente.addActionListener(this);
        this.interCli.btnNuevoCliente.addActionListener(this);
        this.interCli.btnEliminarCliente.addActionListener(this);
        this.interCli.btnBuscarCliente.addActionListener(this);
        this.interCli.tablaCliente.addMouseListener(this);
        this.interCli.btnRestaurarTablaCliente.addActionListener(this);
        this.interCli.txtIdGeCli.setEnabled(false);
        this.interCli.txtFecGeCli.setEnabled(false);
        this.interCli.txtIdGeCli.setVisible(false);
        this.interCli.txtFecGeCli.setVisible(false);
        this.interCli.lvlIdCliente.setVisible(false);
        this.interCli.jlbFechaCliente.setVisible(false);
        this.interCli.btnModificarCliente.setEnabled(false);
        this.interCli.btnEliminarCliente.setEnabled(false);
        ImageIcon wallpaper = new ImageIcon("src/img/fondo3.jpg");
        Icon icono = new ImageIcon(wallpaper.getImage().getScaledInstance(1300, 700, WIDTH));
        interCli.jLabel2.setIcon(icono);
        this.interCli.repaint();
        cscl.mostrarTablaCliente(cl, interCli.tablaCliente);
        this.ordenarTablaClientes();

    }

    public void ordenarTablaClientes() {
        interCli.tablaCliente.getColumnModel().getColumn(0).setPreferredWidth(30);
        interCli.tablaCliente.getColumnModel().getColumn(0).setResizable(false);
        interCli.tablaCliente.getColumnModel().getColumn(1).setPreferredWidth(120);
        interCli.tablaCliente.getColumnModel().getColumn(1).setResizable(false);
        interCli.tablaCliente.getColumnModel().getColumn(2).setPreferredWidth(120);
        interCli.tablaCliente.getColumnModel().getColumn(2).setResizable(false);
        interCli.tablaCliente.getColumnModel().getColumn(3).setPreferredWidth(100);
        interCli.tablaCliente.getColumnModel().getColumn(3).setResizable(false);
        interCli.tablaCliente.getColumnModel().getColumn(4).setPreferredWidth(90);
        interCli.tablaCliente.getColumnModel().getColumn(4).setResizable(false);
        interCli.tablaCliente.getColumnModel().getColumn(5).setPreferredWidth(100);
        interCli.tablaCliente.getColumnModel().getColumn(5).setResizable(false);
        interCli.tablaCliente.getColumnModel().getColumn(6).setPreferredWidth(150);
        interCli.tablaCliente.getColumnModel().getColumn(6).setResizable(false);

    }

    public void limpiarTabla() {
        interCli.txtNomGeCli.setText("");
        interCli.txtApGeCli.setText("");
        interCli.txtDniGeCli.setText("");
        interCli.txtDirGeCli.setText("");
        interCli.txtCelGeCli.setText("");
        interCli.txtIdGeCli.setText("");
        interCli.txtFecGeCli.setText("");
        this.interCli.btnModificarCliente.setEnabled(false);
        this.interCli.btnEliminarCliente.setEnabled(false);

    }

    public boolean verficarDatosClientes(Cliente cl) {
        try {
            //cl.setId(Integer.parseInt(buscar.getText()));
            cl.setNombre(interCli.txtNomGeCli.getText());
            cl.setApellido(interCli.txtApGeCli.getText());
            cl.setTelefono(Integer.parseInt(interCli.txtCelGeCli.getText()));
            cl.setDireccion(interCli.txtDirGeCli.getText());
            cl.setDni(Integer.parseInt(interCli.txtDniGeCli.getText()));
            return true;
        } catch (Exception e) {

            return false;
        }
    }

    public boolean verficarBusqueda(Cliente cl) {
        try {
            //cl.setId(Integer.parseInt(buscar.getText()));
            cl.setDni(Integer.parseInt(interCli.txtBuscarCliente.getText()));
            return true;
        } catch (Exception e) {

            return false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //----------------------------------Registrar------------------------------------------------------------------------
        if (e.getSource() == interCli.btnRegistrarClie) {

            if (this.verficarDatosClientes(cl)) {
                if ((interCli.txtNomGeCli.getText().equals("") || interCli.txtApGeCli.getText().equals("")
                        || interCli.txtDniGeCli.getText().equals("") || interCli.txtDirGeCli.getText().equals("")
                        || String.valueOf(interCli.txtCelGeCli.getText()).equals(""))) {
                    JOptionPane.showMessageDialog(null, "Complete todos los campos");
                } else {
                    if (Integer.parseInt(interCli.txtDniGeCli.getText()) > 9999999 && Integer.parseInt(interCli.txtCelGeCli.getText()) > 900000000) {
                        cl.setDni(Integer.parseInt(interCli.txtDniGeCli.getText()));
                        cl.setVer("Correcto");
                        cscl.obtenerListaDni(cl);
                        cscl.verificarDNI(cl);
                        if (cl.getVer().equals("Incorrecto")) {
                            JOptionPane.showMessageDialog(null, "DNI Existente - Vuelve a ingresar");
                        } else if (cl.getVer().equals("Correcto")) {
                            cl.setNombre(interCli.txtNomGeCli.getText());
                            cl.setApellido(interCli.txtApGeCli.getText());
                            cl.setDni(Integer.parseInt(interCli.txtDniGeCli.getText()));
                            cl.setCorreo(interCli.txtDirGeCli.getText());
                            cl.setTelefono(Integer.parseInt(interCli.txtCelGeCli.getText()));
                            cscl.registrarCliente(cl);
                            cscl.mostrarTablaCliente(cl, interCli.tablaCliente);
                            this.ordenarTablaClientes();
                            this.limpiarTabla();
                            JOptionPane.showMessageDialog(null, "Cliente registrado");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Verificar los datos");
                    }

                }
            } else {
                JOptionPane.showMessageDialog(null, "Verificar los datos");
            }

        }

        //------------------------------------Modificar--------------------------------------------------------------------
        if (e.getSource() == interCli.btnModificarCliente) {
            if (this.verficarDatosClientes(cl)) {
                if ((interCli.txtNomGeCli.getText().equals("") || interCli.txtApGeCli.getText().equals("")
                        || interCli.txtDniGeCli.getText().equals("") || interCli.txtDirGeCli.getText().equals("")
                        || String.valueOf(interCli.txtCelGeCli.getText()).equals(""))) {
                    JOptionPane.showMessageDialog(null, "Complete todos los campos");
                } else {
                    if (Integer.parseInt(interCli.txtDniGeCli.getText()) > 9999999
                            && Integer.parseInt(interCli.txtCelGeCli.getText()) > 900000000) {
                        cl.setDni(Integer.parseInt(interCli.txtDniGeCli.getText()));
                        cl.setVer("Correcto");
                        cscl.obtenerListaDni(cl);
                        cscl.verificarDNI(cl);
                        int fila2 = interCli.tablaCliente.getSelectedRow();
                        String vari;
                        vari = interCli.tablaCliente.getValueAt(fila2, 3).toString();
                        if (cl.getVer().equals("Incorrecto") && !interCli.txtDniGeCli.getText().equals(vari)) {
                            JOptionPane.showMessageDialog(null, "DNI Existente - Vuelve a ingresar");
                        } else if ((cl.getVer().equals("Correcto"))
                                || (cl.getVer().equals("Incorrecto") && interCli.txtDniGeCli.getText().equals(vari))) {
                            cl.setId(Integer.parseInt(interCli.txtIdGeCli.getText()));
                            cl.setNombre(interCli.txtNomGeCli.getText());
                            cl.setApellido(interCli.txtApGeCli.getText());
                            cl.setDni(Integer.parseInt(interCli.txtDniGeCli.getText()));
                            cl.setCorreo(interCli.txtDirGeCli.getText());
                            cl.setTelefono(Integer.parseInt(interCli.txtCelGeCli.getText()));
                            cscl.modificarCliente(cl);
                            cscl.mostrarTablaCliente(cl, interCli.tablaCliente);
                            this.ordenarTablaClientes();
                            this.limpiarTabla();

                            if (interCli.cbxSelecCliente.getSelectedItem().toString().equals("ID")
                                    || interCli.cbxSelecCliente.getSelectedItem().toString().equals("DNI")) {
                                if (this.verficarBusqueda(cl)) {
                                    cl.setDni(Integer.parseInt(interCli.txtBuscarCliente.getText()));
                                    if (cscl.buscarCliente(cl, interCli.tablaCliente, interCli.cbxSelecCliente)) {
                                        this.ordenarTablaClientes();
                                        this.limpiarTabla();
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "Verificar los datos");
                                }
                            }
                            JOptionPane.showMessageDialog(null, "Modificación con Éxito");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Verificar los datos");
                    }

                }
            } else {
                JOptionPane.showMessageDialog(null, "Verificar los datos");
            }
        }
        //------------------------------------Limpiar--------------------------------------------------------------------
        if (e.getSource() == interCli.btnNuevoCliente) {
            this.limpiarTabla();
            interCli.txtNomGeCli.requestFocus();
        }
        //------------------------------------Eliminar--------------------------------------------------------------------
        if (e.getSource() == interCli.btnEliminarCliente) {

            int fila = interCli.tablaCliente.getSelectedRow();
            if (fila >= 0) {
                int pregunta = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar", "  Pregunta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (pregunta == 0) {
                    cl.setId(Integer.parseInt(interCli.txtIdGeCli.getText()));
                    //cscl.eliminarCliente(cl);
                    cscl.eliminarClienteUpdate(cl);
                    cscl.mostrarTablaCliente(cl, interCli.tablaCliente);

                    //interCli.tablaCliente.setCellSelectionEnabled(false);
                    this.ordenarTablaClientes();
                    this.limpiarTabla();
                    JOptionPane.showMessageDialog(null, "Eliminación con Éxito");
                } else {
                    limpiarTabla();
                    interCli.tablaCliente.clearSelection();
                }
            } else {

                //interCli.tablaCliente.setCellSelectionEnabled(false);
                JOptionPane.showMessageDialog(null, "Eliminación inválida");

            }

        }

        //------------------------------------Buscar--------------------------------------------------------------------
        if (e.getSource() == interCli.btnBuscarCliente) {
            if (interCli.cbxSelecCliente.getSelectedItem().toString().equals("ID")
                    || interCli.cbxSelecCliente.getSelectedItem().toString().equals("DNI")) {
                if (this.verficarBusqueda(cl)) {

                    if (Integer.parseInt(interCli.txtBuscarCliente.getText()) > 0) {
                        cl.setVer("Correcto");

                        if (interCli.cbxSelecCliente.getSelectedItem().toString().equals("ID")) {
                            cl.setId_fac_pag(Integer.parseInt(interCli.txtBuscarCliente.getText()));
                            cscl.obtenerListaDni(cl);
                            cscl.verificarIDCli(cl);
                            if (cl.getVer().equals("Correcto")) {
                                cscl.mostrarTablaCliente(cl, interCli.tablaCliente);
                                this.ordenarTablaClientes();
                                this.limpiarTabla();
                                JOptionPane.showMessageDialog(null, "ID no encontrado");
                            } else {
                                if (cscl.buscarCliente(cl, interCli.tablaCliente, interCli.cbxSelecCliente)) {
                                    this.ordenarTablaClientes();
                                    this.limpiarTabla();
                                }
                            }
                        } else if (interCli.cbxSelecCliente.getSelectedItem().toString().equals("DNI")) {
                            cl.setDni(Integer.parseInt(interCli.txtBuscarCliente.getText()));
                            cscl.obtenerListaDni(cl);
                            cscl.verificarDNI(cl);
                            if (cl.getVer().equals("Correcto")) {
                                cscl.mostrarTablaCliente(cl, interCli.tablaCliente);
                                this.ordenarTablaClientes();
                                this.limpiarTabla();
                                JOptionPane.showMessageDialog(null, "DNI no encontrado");
                            } else {
                                if (cscl.buscarCliente(cl, interCli.tablaCliente, interCli.cbxSelecCliente)) {
                                    this.ordenarTablaClientes();
                                    this.limpiarTabla();
                                }
                            }
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "Verificar los datos");
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Verificar los datos");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Selecciona una opción correcta");
            }

        }

        //------------------------------------Limpiar tabla de busqueda --------------------------------------------------------------------
        if (e.getSource()
                == interCli.btnRestaurarTablaCliente) {
            interCli.txtBuscarCliente.setText("");
            interCli.cbxSelecCliente.setSelectedIndex(0);
            cscl.mostrarTablaCliente(cl, interCli.tablaCliente);
            this.ordenarTablaClientes();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == interCli.tablaCliente) {
            int fila = interCli.tablaCliente.getSelectedRow();

            if (fila >= 0) {
                interCli.txtIdGeCli.setText(interCli.tablaCliente.getValueAt(fila, 0).toString());
                interCli.txtNomGeCli.setText(interCli.tablaCliente.getValueAt(fila, 1).toString());
                interCli.txtApGeCli.setText(interCli.tablaCliente.getValueAt(fila, 2).toString());
                interCli.txtDniGeCli.setText(interCli.tablaCliente.getValueAt(fila, 3).toString());
                interCli.txtDirGeCli.setText(interCli.tablaCliente.getValueAt(fila, 4).toString());
                interCli.txtCelGeCli.setText(interCli.tablaCliente.getValueAt(fila, 5).toString());
                interCli.txtFecGeCli.setText(interCli.tablaCliente.getValueAt(fila, 6).toString());
                this.interCli.btnModificarCliente.setEnabled(true);
                this.interCli.btnEliminarCliente.setEnabled(true);

            } else {
                JOptionPane.showMessageDialog(null, "Error fila");
                //this.ordenarTablaUs();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
