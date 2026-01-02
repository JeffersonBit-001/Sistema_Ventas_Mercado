/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Dao.CategoriasDao;
import Dao.ProductoDao;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.Categoria;
import modelo.Producto;
import vista.InterCategoria;

/**
 *
 * @author JEFFERSON
 */
public class ControladorInterCategoria implements ActionListener {

    InterCategoria interCat;
    Categoria categoria;
    CategoriasDao cscat;

    public ControladorInterCategoria(InterCategoria interCat, Categoria categoria, CategoriasDao cscat) {
        this.interCat = interCat;
        this.categoria = categoria;
        this.cscat = cscat;
        
        
        this.interCat.btnGuardar.addActionListener(this);
        this.interCat.setSize(new Dimension(400, 200));
        this.interCat.setTitle("Nueva Categoria");

    }

    private void Limpiar() {
        interCat.txt_descripcion.setText("");
        interCat.txt_descripcion.requestFocus();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == interCat.btnGuardar) {

            //----------------------------------Registrar------------------------------------------------------------------------
            

            //Validamos campos vacios
            if (interCat.txt_descripcion.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Complete todos los campos");
            } else {
                if (!cscat.existeCategoria(interCat.txt_descripcion.getText().trim())) {
                    categoria.setDescripcion2(interCat.txt_descripcion.getText().trim());
                    categoria.setEstado2(1);
                    if (cscat.guardar(categoria)) {
                        Limpiar();
                        JOptionPane.showMessageDialog(null, "Registro Guardado");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al guardar");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "La categoria ya esta registrada");
                }

            }

        }

    }
}
