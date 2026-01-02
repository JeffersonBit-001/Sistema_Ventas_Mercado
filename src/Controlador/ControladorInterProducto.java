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
import javax.swing.JOptionPane;
import modelo.Producto;
import vista.InterProducto;

/**
 *
 * @author JEFFERSON
 */
public class ControladorInterProducto implements ActionListener {

    InterProducto interProd;
    Producto prod;
    ProductoDao csprod;
    CategoriasDao cscat;
    
    

    public ControladorInterProducto(InterProducto interProd, Producto prod, ProductoDao csprod, CategoriasDao cscat) {
        this.interProd = interProd;
        this.prod = prod;
        this.csprod = csprod;
        this.cscat = cscat;
        this.interProd.jButton_guardar.addActionListener(this);
        this.interProd.setSize(new Dimension(400, 300));
        this.interProd.setTitle("Nuevo Producto");
        cscat.CargarComboCategoria(interProd.jComboBox_categoria);

    }

    private void Limpiar() {
        interProd.txt_nombre.setText("");
        interProd.txt_nombre.requestFocus();
        interProd.txt_cantidad.setText("");
        interProd.txt_precio.setText("");
        interProd.txt_descripcion.setText("");
    }
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == interProd.jButton_guardar) {

            //----------------------------------Registrar------------------------------------------------------------------------
            String categoria = "";
            categoria = interProd.jComboBox_categoria.getSelectedItem().toString().trim();

            //Validar campos
            if (interProd.txt_nombre.getText().equals("") || interProd.txt_cantidad.getText().equals("") || interProd.txt_precio.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Complete todos los campos");
                interProd.txt_nombre.setBackground(Color.red);
                interProd.txt_cantidad.setBackground(Color.red);
                interProd.txt_precio.setBackground(Color.red);
            } else {
                //consulta para ver si el prod ya esta registrado
                if (!csprod.existeProducto(interProd.txt_nombre.getText().trim())) {

                    if (categoria.equalsIgnoreCase("Seleccione Categoria: ")) {
                        JOptionPane.showMessageDialog(null, "Seleccione Categoria");
                    } else {

                        prod.setNombre(interProd.txt_nombre.getText().trim());
                        prod.setCantidad(Integer.parseInt(interProd.txt_cantidad.getText().trim()));
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
                            prod.setPrecio(Precio);
                        } else {
                            Precio = Double.parseDouble(precioTXT);
                            prod.setPrecio(Precio);
                        }
                        prod.setDescripcion(interProd.txt_descripcion.getText().trim());
                        //idcategoria, cargar metodo que obtiene el idcategoria
                        
                        prod.setIdCategoria(cscat.IdCategoria(this.interProd.jComboBox_categoria));
                        prod.setEstado(1);

                        if (csprod.guardar(prod)) {
                            JOptionPane.showMessageDialog(null, "Registro GUARDADO");
                            interProd.txt_nombre.setBackground(Color.green);
                            interProd.txt_cantidad.setBackground(Color.green);
                            interProd.txt_precio.setBackground(Color.green);
                            interProd.txt_descripcion.setBackground(Color.green);

                            cscat.CargarComboCategoria(interProd.jComboBox_categoria);
                            this.Limpiar();
                        } else {
                            JOptionPane.showMessageDialog(null, "Error al GUARDAR");
                        }

                    }

                } else {
                    JOptionPane.showMessageDialog(null, "El producto ya esta registrado");
                }

            }

        }

    }
}
