/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Dao.CategoriasDao;
import Dao.ProductoDao;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import static java.awt.image.ImageObserver.WIDTH;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import modelo.Categoria;
import modelo.Producto;
import vista.InterCategoria;
import vista.InterGestionarCategoria;
import vista.InterGestionarProducto;
import vista.InterProducto;

/**
 *
 * @author JEFFERSON
 */
public class ControladorInterGestionarCategoria implements ActionListener, MouseListener {

    InterGestionarCategoria interCat;
    Categoria categoria;
    CategoriasDao cscat;

    public ControladorInterGestionarCategoria(InterGestionarCategoria interCat, Categoria categoria, CategoriasDao cscat) {
        this.interCat = interCat;
        this.categoria = categoria;
        this.cscat = cscat;
        this.interCat.jButton_actualizar.addActionListener(this);
        this.interCat.jButton_eliminar.addActionListener(this);
        this.interCat.jTable_categorias.addMouseListener(this);
        this.interCat.btnBuscar.addActionListener(this);
        this.interCat.id_scond.setVisible(false);
        this.interCat.id_scond.setEnabled(false);
        this.interCat.setSize(new Dimension(592, 406));
        this.interCat.setTitle("Gestionar Categorías");
        //Cargar tabla
        cscat.mostrarTablaCategoria(categoria, interCat.jTable_categorias);
        //insertar imagen en nuestro Jlabel
        ImageIcon wallpaper = new ImageIcon("src/img/fondo3.jpg");
        Icon icono = new ImageIcon(wallpaper.getImage().getScaledInstance(900, 500, WIDTH));
        interCat.jLabel_wallpaper.setIcon(icono);
        this.interCat.repaint();

        this.interCat.jButton_actualizar.setEnabled(false);
        this.interCat.jButton_eliminar.setEnabled(false);

    }

    //METODO PARA limpiar 
    private void Limpiar() {
        interCat.id_scond.setText("");
        interCat.txt_descripcion.setText("");
        this.interCat.jButton_actualizar.setEnabled(false);
        this.interCat.jButton_eliminar.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == interCat.jButton_actualizar) {

            //----------------------------------UPDATE------------------------------------------------------------------------
            if (!interCat.txt_descripcion.getText().isEmpty()) {

                categoria.setDescripcion2(interCat.txt_descripcion.getText().trim());
                if (cscat.actualizar(categoria, Integer.parseInt(interCat.id_scond.getText()))) {

                    cscat.mostrarTablaCategoria(categoria, interCat.jTable_categorias);
                    this.Limpiar();
                    JOptionPane.showMessageDialog(null, "Categoria Actualizada");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al actualizar Categoria");
                }

            } else {
                JOptionPane.showMessageDialog(null, "Seleccione una categoria");
            }

        }

        if (e.getSource() == interCat.jButton_eliminar) {

            //----------------------------------ELIMINAR------------------------------------------------------------------------
            int fila = interCat.jTable_categorias.getSelectedRow();
            if (fila >= 0) {
                int pregunta = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar", "  Pregunta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (pregunta == 0) {

                    if (!cscat.eliminar(Integer.parseInt(interCat.id_scond.getText()))) {

                        cscat.mostrarTablaCategoria(categoria, interCat.jTable_categorias);
                        this.Limpiar();
                        JOptionPane.showMessageDialog(null, "Categoria Eliminada");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al eliminar Categoria");
                    }

                } else {
                    this.Limpiar();
                    interCat.jTable_categorias.clearSelection();
                }
            } else {

                JOptionPane.showMessageDialog(null, "Eliminación inválida");
            }

        }

        if (e.getSource() == interCat.btnBuscar) {
            
                categoria.setBus(interCat.txtBuscar.getText());
                if (cscat.buscarTablaCategoria(categoria, interCat.jTable_categorias)) {

                } else {

                    cscat.mostrarTablaCategoria(categoria, interCat.jTable_categorias);
                }
            

        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == interCat.jTable_categorias) {
            int fila = interCat.jTable_categorias.getSelectedRow();

            if (fila >= 0) {
                interCat.id_scond.setText(interCat.jTable_categorias.getValueAt(fila, 0).toString());
                interCat.txt_descripcion.setText(interCat.jTable_categorias.getValueAt(fila, 1).toString());
                this.interCat.jButton_actualizar.setEnabled(true);
                this.interCat.jButton_eliminar.setEnabled(true);

            } else {
                JOptionPane.showMessageDialog(null, "Error fila");

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
