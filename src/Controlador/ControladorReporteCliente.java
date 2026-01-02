/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Dao.BoletaIndividualDao;
import Dao.ReporteClienteDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import static java.awt.image.ImageObserver.WIDTH;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import modelo.Cliente;
import vista.InterBoletaIndividual;
import vista.InterReporteCliente;
import vista.MenuTienda;

/**
 *
 * @author JEFFERSON
 */
public class ControladorReporteCliente implements ActionListener, MouseListener {

    InterReporteCliente reporCli;
    Cliente cl;
    ReporteClienteDao csCli;
    MenuTienda emp;

    public ControladorReporteCliente(InterReporteCliente reporCli, Cliente cl, ReporteClienteDao csCli, MenuTienda emp) {
        this.reporCli = reporCli;
        this.cl = cl;
        this.csCli = csCli;
        this.emp = emp;
        csCli.mostrarDatos(reporCli.jtbTablaDeReportes);
        this.reporCli.btnBuscarReporteVentas.addActionListener(this);
        this.reporCli.btnEliminarReporteCliente.addActionListener(this);
        this.reporCli.jtbTablaDeReportes.addMouseListener(this);
        this.reporCli.btnRestaurarReporteCliente.addActionListener(this);
        this.reporCli.btnImprimirBoletaReport.addActionListener(this);
        this.reporCli.btnActualizar.addActionListener(this);
        reporCli.txtIdReporteCliente.setEnabled(false);
        reporCli.txtIdReporteCliente.setVisible(false);
        this.reporCli.cmbActualizar.setEnabled(false);
        this.reporCli.btnEliminarReporteCliente.setEnabled(false);
        this.reporCli.btnImprimirBoletaReport.setEnabled(false);
        this.reporCli.btnActualizar.setEnabled(false);
        ImageIcon wallpaper = new ImageIcon("src/img/fondo3.jpg");
        Icon icono = new ImageIcon(wallpaper.getImage().getScaledInstance(1240, 570, WIDTH));
        reporCli.jLabel1.setIcon(icono);
        this.reporCli.repaint();
        //this.reporCli.btnEliminarReporteCliente.setVisible(false);
        //this.reporCli.btnEliminarReporteCliente.setEnabled(false);
        this.ordenarTablaReporClie();
    }

    public void ordenarTablaReporClie() {
        reporCli.jtbTablaDeReportes.getColumnModel().getColumn(0).setPreferredWidth(30);
        reporCli.jtbTablaDeReportes.getColumnModel().getColumn(0).setResizable(false);
        reporCli.jtbTablaDeReportes.getColumnModel().getColumn(1).setPreferredWidth(160);
        reporCli.jtbTablaDeReportes.getColumnModel().getColumn(1).setResizable(false);
        reporCli.jtbTablaDeReportes.getColumnModel().getColumn(2).setPreferredWidth(90);
        reporCli.jtbTablaDeReportes.getColumnModel().getColumn(2).setResizable(false);
        reporCli.jtbTablaDeReportes.getColumnModel().getColumn(3).setPreferredWidth(90);
        reporCli.jtbTablaDeReportes.getColumnModel().getColumn(3).setResizable(false);
        reporCli.jtbTablaDeReportes.getColumnModel().getColumn(4).setPreferredWidth(90);
        reporCli.jtbTablaDeReportes.getColumnModel().getColumn(4).setResizable(false);
        reporCli.jtbTablaDeReportes.getColumnModel().getColumn(5).setPreferredWidth(90);
        reporCli.jtbTablaDeReportes.getColumnModel().getColumn(5).setResizable(false);
    }

    public void limpiarTabla() {
        reporCli.txtBuscarNombreReporte.setText("");
        reporCli.txtIdReporteCliente.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == reporCli.btnBuscarReporteVentas) {
            if (reporCli.txtBuscarNombreReporte.getText().equals("")) {

                JOptionPane.showMessageDialog(null, "Completa los campos");

            } else {

                cl.setNombre(reporCli.txtBuscarNombreReporte.getText());
                csCli.buscarTablaReportCliente(reporCli.jtbTablaDeReportes, cl);
                this.ordenarTablaReporClie();

            }

        }

        if (e.getSource() == reporCli.btnEliminarReporteCliente) {
            int fila = reporCli.jtbTablaDeReportes.getSelectedRow();
            if (fila >= 0) {
                int pregunta = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar", "  Pregunta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (pregunta == 0) {
                    cl.setId(Integer.parseInt(reporCli.txtIdReporteCliente.getText()));
                    csCli.eliminarTablaDetalleReportClienteUpdate(cl);
                    csCli.eliminarTablaReportFacturaClienteUpdate(cl);
                    csCli.mostrarDatos(reporCli.jtbTablaDeReportes);
                    this.limpiarTabla();
                    this.ordenarTablaReporClie();

                    JOptionPane.showMessageDialog(null, "Eliminación Exitosa");

                } else {
                    reporCli.jtbTablaDeReportes.clearSelection();
                }
            } else {

                JOptionPane.showMessageDialog(null, "Eliminación inválida");
            }
        }

        if (e.getSource() == reporCli.btnRestaurarReporteCliente) {
            this.limpiarTabla();
            csCli.mostrarDatos(reporCli.jtbTablaDeReportes);
            this.ordenarTablaReporClie();
            this.reporCli.btnEliminarReporteCliente.setEnabled(false);
            this.reporCli.btnImprimirBoletaReport.setEnabled(false);
            this.reporCli.cmbActualizar.setEnabled(false);
            this.reporCli.btnActualizar.setEnabled(false);

        }

        if (e.getSource() == reporCli.btnImprimirBoletaReport) {
            this.reporCli.btnEliminarReporteCliente.setEnabled(false);
            InterBoletaIndividual interBoleta = new InterBoletaIndividual();

            cl.setId_fac_pag(Integer.parseInt(reporCli.txtIdReporteCliente.getText()));

            //csdtFac.conseguirNombreCliente(cl);
            //cl.setVer2(emp.jtxNombreEmpleado.getText());
            //csbl.imprimir(cl);
            BoletaIndividualDao csbl = new BoletaIndividualDao();
            ControladorBoletaIndividual controladoBoleta = new ControladorBoletaIndividual(cl, csbl, interBoleta);
            emp.dskPaneSistema.add(interBoleta);
            interBoleta.setVisible(true);

        }

        if (e.getSource() == reporCli.btnActualizar) {
            if (reporCli.cmbActualizar.getSelectedItem().toString().equals("--Selecciona--")) {
                JOptionPane.showMessageDialog(null, "Selecciona una opción");
            } else {
                cl.setId(Integer.parseInt(reporCli.txtIdReporteCliente.getText()));
                csCli.ActualizarTablaReportFacturaClienteUpdate(cl, reporCli.cmbActualizar.getSelectedItem().toString());
                csCli.mostrarDatos(reporCli.jtbTablaDeReportes);
                this.ordenarTablaReporClie();
                this.reporCli.btnEliminarReporteCliente.setEnabled(false);
                this.reporCli.btnImprimirBoletaReport.setEnabled(false);
                this.reporCli.cmbActualizar.setEnabled(false);
                this.reporCli.btnActualizar.setEnabled(false);
                JOptionPane.showMessageDialog(null, "Actualizado con éxito");
            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent e
    ) {
        if (e.getSource() == reporCli.jtbTablaDeReportes) {
            int fila = reporCli.jtbTablaDeReportes.getSelectedRow();

            if (fila >= 0) {
                reporCli.txtIdReporteCliente.setText(reporCli.jtbTablaDeReportes.getValueAt(fila, 0).toString());
                reporCli.cmbActualizar.setSelectedItem(reporCli.jtbTablaDeReportes.getValueAt(fila, 5).toString());
                this.reporCli.btnEliminarReporteCliente.setEnabled(true);
                this.reporCli.btnImprimirBoletaReport.setEnabled(true);
                this.reporCli.cmbActualizar.setEnabled(true);
                this.reporCli.btnActualizar.setEnabled(true);

            } else {
                JOptionPane.showMessageDialog(null, "Fila no seleccionada");
                this.ordenarTablaReporClie();
            }

        }

    }

    @Override
    public void mousePressed(MouseEvent e
    ) {

    }

    @Override
    public void mouseReleased(MouseEvent e
    ) {

    }

    @Override
    public void mouseEntered(MouseEvent e
    ) {

    }

    @Override
    public void mouseExited(MouseEvent e
    ) {

    }

}
