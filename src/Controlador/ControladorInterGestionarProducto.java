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
import modelo.Producto;
import vista.InterGestionarProducto;
import vista.InterProducto;

/**
 *
 * @author JEFFERSON
 */
public class ControladorInterGestionarProducto implements ActionListener, MouseListener {

    InterGestionarProducto interProd;
    Producto producto;
    ProductoDao csprod;
    CategoriasDao cscat;

    public ControladorInterGestionarProducto(InterGestionarProducto interProd, Producto producto, ProductoDao csprod, CategoriasDao cscat) {
        this.interProd = interProd;
        this.producto = producto;
        this.csprod = csprod;
        this.cscat = cscat;
        this.interProd.jButton_actualizar.addActionListener(this);
        this.interProd.jButton_eliminar.addActionListener(this);
        this.interProd.jTable_productos.addMouseListener(this);
        this.interProd.btnBuscar.addActionListener(this);
        this.interProd.id_scond.setVisible(false);
        this.interProd.id_scond.setEnabled(false);
        this.interProd.setSize(new Dimension(900, 500));
        this.interProd.setTitle("Gestionar Productos");
        //Cargar tabla
        csprod.mostrarTablaProducto(producto, interProd.jTable_productos);
        cscat.CargarComboCategoria(interProd.jComboBox_categoria);
        //insertar imagen en nuestro Jlabel
        ImageIcon wallpaper = new ImageIcon("src/img/fondo3.jpg");
        Icon icono = new ImageIcon(wallpaper.getImage().getScaledInstance(900, 500, WIDTH));
        interProd.jLabel_wallpaper.setIcon(icono);
        this.interProd.repaint();
        cscat.CargarComboCategoria(interProd.jComboBox_categoria);

        this.interProd.jButton_actualizar.setEnabled(false);
        this.interProd.jButton_eliminar.setEnabled(false);

    }

    //METODO PARA limpiar 
    private void Limpiar() {
        interProd.id_scond.setText("");
        interProd.txt_nombre.setText("");
        interProd.txt_cantidad.setText("");
        interProd.txt_precio.setText("");
        interProd.txt_descripcion.setText("");
        interProd.jComboBox_categoria.setSelectedIndex(0);
        this.interProd.jButton_actualizar.setEnabled(false);
        this.interProd.jButton_eliminar.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == interProd.jButton_actualizar) {

            //----------------------------------UPDATE------------------------------------------------------------------------
            String categoria = "";
            categoria = interProd.jComboBox_categoria.getSelectedItem().toString().trim();

            //Validar campos
            if (interProd.txt_nombre.getText().isEmpty() || interProd.txt_cantidad.getText().isEmpty() || interProd.txt_precio.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Complete todos los campos");

            } else {

                if (categoria.equalsIgnoreCase("Seleccione Categoria: ")) {
                    JOptionPane.showMessageDialog(null, "Seleccione Categoria");
                } else {

                    producto.setNombre(interProd.txt_nombre.getText().trim());
                    producto.setCantidad(Integer.parseInt(interProd.txt_cantidad.getText().trim()));
                    String precioTXT = "";
                    double Precio = 0.0;

                    precioTXT = interProd.txt_precio.getText().trim();
                    boolean aux = false;
                    //Si el usuario ,(como) como punto decimal, lo transformamos a punto(.)
                    for (int i = 0; i < precioTXT.length(); i++) {
                        if (precioTXT.charAt(i) == ',') {
                            String precioNuevo = precioTXT.replace(",", ".");
                            Precio = Double.parseDouble(precioNuevo);
                            aux = true;
                        }
                    }

                    //Evaluamos la condicion
                    if (aux == true) {
                        producto.setPrecio(Precio);
                    } else {
                        Precio = Double.parseDouble(precioTXT);
                        producto.setPrecio(Precio);
                    }
                    producto.setDescripcion(interProd.txt_descripcion.getText().trim());
                    //idcategoria, cargar metodo que obtiene el idcategoria

                    producto.setIdCategoria(cscat.IdCategoria(this.interProd.jComboBox_categoria));
                    producto.setEstado(1);

                    if (csprod.actualizar(producto, Integer.parseInt(interProd.id_scond.getText()))) {

                        csprod.mostrarTablaProducto(producto, interProd.jTable_productos);

                        this.Limpiar();

                        JOptionPane.showMessageDialog(null, "Registro Actualizado");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al ACTUALIZAR");
                    }

                }

            }
        }

        if (e.getSource() == interProd.jButton_eliminar) {

            //----------------------------------ELIMINAR------------------------------------------------------------------------
            int fila = interProd.jTable_productos.getSelectedRow();
            if (fila >= 0) {
                int pregunta = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar", "  Pregunta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (pregunta == 0) {
                    if (!csprod.eliminar(Integer.parseInt(interProd.id_scond.getText()))) {

                        JOptionPane.showMessageDialog(null, "Producto Eliminado!");
                        csprod.mostrarTablaProducto(producto, interProd.jTable_productos);
                        this.Limpiar();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al eliminar producto");
                    }

                } else {
                    this.Limpiar();
                    interProd.jTable_productos.clearSelection();
                }
            } else {
                
                JOptionPane.showMessageDialog(null, "Eliminación inválida");
            }

        }
        
        
        
        if (e.getSource() == interProd.btnBuscar) {
            
                producto.setBus(interProd.txtBuscar.getText());
                if (csprod.buscarTablaProductos(producto, interProd.jTable_productos)) {

                } else {

                    csprod.buscarTablaProductos(producto, interProd.jTable_productos);
                }
            

        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == interProd.jTable_productos) {
            int fila = interProd.jTable_productos.getSelectedRow();

            if (fila >= 0) {
                interProd.id_scond.setText(interProd.jTable_productos.getValueAt(fila, 0).toString());
                interProd.txt_nombre.setText(interProd.jTable_productos.getValueAt(fila, 1).toString());
                interProd.txt_cantidad.setText(interProd.jTable_productos.getValueAt(fila, 2).toString());
                interProd.txt_precio.setText(interProd.jTable_productos.getValueAt(fila, 3).toString());
                interProd.txt_descripcion.setText(interProd.jTable_productos.getValueAt(fila, 4).toString());
                interProd.jComboBox_categoria.setSelectedItem(interProd.jTable_productos.getValueAt(fila, 5).toString());
                this.interProd.jButton_actualizar.setEnabled(true);
                this.interProd.jButton_eliminar.setEnabled(true);

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
