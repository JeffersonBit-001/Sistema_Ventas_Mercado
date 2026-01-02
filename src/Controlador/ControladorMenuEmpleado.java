/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Dao.BoletaIndividualDao;
import Dao.CategoriasDao;
import Dao.ClienteDao;
import Dao.ReporteClienteDao;
import Dao.PagosDao;
import Dao.ProductoDao;
import FondosImg.ImagenFondoDeskot;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.Categoria;
import modelo.Cliente;
import modelo.Producto;
import modelo.Usuario;
import vista.FormAdmSistema;
import vista.InterCategoria;
import vista.InterGestionarCategoria;
import vista.InterGestionarCliente;
import vista.InterGestionarProducto;
import vista.InterPagos;
import vista.InterProducto;
import vista.InterReporteCliente;
import vista.MenuTienda;

/**
 *
 * @author JEFFERSON
 */
public class ControladorMenuEmpleado implements ActionListener {

    MenuTienda emp;
    ImagenFondoDeskot img = new ImagenFondoDeskot();

    public ControladorMenuEmpleado(MenuTienda emp) {
        this.emp = emp;
        this.emp.dskPaneSistema.setBorder(img);
        this.emp.jMenuItem1_nuevo_cliente.addActionListener(this);
        this.emp.jMenuItem1_nuevo_cliente.addActionListener(this);
        this.emp.jMenuItem2_gestionar_cliente.addActionListener(this);
        this.emp.jMenuItem3_nuevo_producto.addActionListener(this);
        this.emp.jMenuItem4_gestionar_producto.addActionListener(this);
        this.emp.jMenuItem5_actualizar_stock.addActionListener(this);
        this.emp.jMenuItem6_nueva_categoria.addActionListener(this);
        this.emp.jMenuItem7_gestionar_categoria.addActionListener(this);
        this.emp.jMenuItem9_nueva_venta.addActionListener(this);
        this.emp.jMenuItem10_gestionar_venta.addActionListener(this);
        this.emp.jMenuItem11_reportes_clientes.addActionListener(this);
        this.emp.jMenuItem12_reportes_categorias.addActionListener(this);
        this.emp.jMenuItem13_reporte_productos.addActionListener(this);
        this.emp.jMenuItem14_reporte_ventas.addActionListener(this);
        this.emp.mncerrarPanel.addActionListener(this);
        this.emp.volvermenu.addActionListener(this);
        this.emp.setTitle("Panel Empleado");
        this.emp.setVisible(false);
        this.emp.jMenuItem5_actualizar_stock.setVisible(false);
        this.emp.jMenuItem5_actualizar_stock.setEnabled(false);
        this.emp.jMenuItem1_nuevo_cliente.setVisible(false);
        this.emp.jMenuItem1_nuevo_cliente.setEnabled(false);
        //this.emp.setSize(new Dimension(1400, 900));
        this.emp.jtxNombreEmpleado.setForeground(Color.BLACK);
        this.emp.setExtendedState(emp.MAXIMIZED_BOTH);
        this.emp.setLocationRelativeTo(null);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == emp.jMenuItem3_nuevo_producto) {
            Producto prod = new Producto();

            ProductoDao consultaProd = new ProductoDao();
            CategoriasDao consultaCat = new CategoriasDao();
            InterProducto gestionProd = new InterProducto();
            //CONTROLADOR PRODUCTO
            ControladorInterProducto controlProd = new ControladorInterProducto(gestionProd, prod, consultaProd, consultaCat);

            emp.dskPaneSistema.add(gestionProd);
            gestionProd.setVisible(true);
            gestionProd.setTitle("Añadir Producto");
        }

        if (e.getSource() == emp.jMenuItem4_gestionar_producto) {
            Producto prod = new Producto();
            ProductoDao consultaProd = new ProductoDao();
            CategoriasDao consultaCat = new CategoriasDao();
            InterGestionarProducto interGes = new InterGestionarProducto();
            //CONTROLADOR GESTION
            ControladorInterGestionarProducto controlGestionProd = new ControladorInterGestionarProducto(interGes, prod, consultaProd, consultaCat);
            emp.dskPaneSistema.add(interGes);
            interGes.setVisible(true);
            interGes.setTitle("Gestión");
        }

        if (e.getSource() == emp.jMenuItem6_nueva_categoria) {
            Categoria cat = new Categoria();
            CategoriasDao catDao = new CategoriasDao();
            InterCategoria interCat = new InterCategoria();
            //CONTROLADOR CATEGORIA
            ControladorInterCategoria controlCat = new ControladorInterCategoria(interCat, cat, catDao);
            emp.dskPaneSistema.add(interCat);
            interCat.setVisible(true);
            interCat.setTitle("Añadir Categoría");
        }

        if (e.getSource() == emp.jMenuItem7_gestionar_categoria) {
            Categoria cat = new Categoria();
            CategoriasDao catDao = new CategoriasDao();
            InterGestionarCategoria interCat = new InterGestionarCategoria();
            //CONTROLADOR  GESTION CATEGORIA
            ControladorInterGestionarCategoria controlGesCat = new ControladorInterGestionarCategoria(interCat, cat, catDao);
            emp.dskPaneSistema.add(interCat);
            interCat.setVisible(true);
            interCat.setTitle("Gestionar Producto");
        }

        if (e.getSource() == emp.jMenuItem2_gestionar_cliente) {
            Cliente cli = new Cliente();
            ClienteDao consultacliente = new ClienteDao();
            InterGestionarCliente interCli = new InterGestionarCliente();
            //CONTROLADOR CLIENTE
            ControladorCliente controlCliente = new ControladorCliente(interCli, cli, consultacliente);
            emp.dskPaneSistema.add(interCli);
            interCli.setVisible(true);
            interCli.setTitle("Gestionar Cliente");
        }

        if (e.getSource() == emp.jMenuItem9_nueva_venta) {
            Cliente cli = new Cliente();
            ClienteDao consultacliente = new ClienteDao();
            Producto prod = new Producto();
            ProductoDao consultaProd = new ProductoDao();
            PagosDao consultaPag = new PagosDao();

            InterPagos interPagos = new InterPagos();
            emp.dskPaneSistema.add(interPagos);
            //CONTROLADOR PAGOS
            ControladorPagos controlPagos = new ControladorPagos(consultaPag,
                    cli, consultacliente, prod, consultaProd, interPagos, emp);
            interPagos.setVisible(true);
            interPagos.setTitle("Pagos");

        }

        if (e.getSource() == emp.jMenuItem10_gestionar_venta) {
            Cliente cl = new Cliente();
            ReporteClienteDao csncl = new ReporteClienteDao();
            InterReporteCliente interReport = new InterReporteCliente();
            emp.dskPaneSistema.add(interReport);
            //CONTROLADOR PRODUCTOS
            ControladorReporteCliente controlReport = new ControladorReporteCliente(interReport, cl, csncl, emp);
            interReport.setVisible(true);
            interReport.setTitle("Reporte del Cliente");

        }

        if (e.getSource() == emp.jMenuItem11_reportes_clientes) {
            int pregunta = JOptionPane.showConfirmDialog(null, "¿Estás seguro de querer imprimir este reporte de clientes?", "  Pregunta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (pregunta == 0) {
                Cliente cl = new Cliente();
                BoletaIndividualDao bol = new BoletaIndividualDao();
                bol.imprimirClientes(cl);
                JOptionPane.showMessageDialog(null, "Creación Exitosa, Revisa tu escritorio");
            }

        }
        if (e.getSource() == emp.jMenuItem12_reportes_categorias) {
            int pregunta = JOptionPane.showConfirmDialog(null, "¿Estás seguro de querer imprimir este reporte de categorías?", "  Pregunta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (pregunta == 0) {
                Cliente cl = new Cliente();
                BoletaIndividualDao bol = new BoletaIndividualDao();
                bol.imprimirCategorias(cl);
                JOptionPane.showMessageDialog(null, "Creación Exitosa, Revisa tu escritorio");
            }

        }
        if (e.getSource() == emp.jMenuItem13_reporte_productos) {
            int pregunta = JOptionPane.showConfirmDialog(null, "¿Estás seguro de querer imprimir este reporte de clientes?", "  Pregunta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (pregunta == 0) {
                Cliente cl = new Cliente();
                BoletaIndividualDao bol = new BoletaIndividualDao();
                bol.imprimirProductos(cl);
                JOptionPane.showMessageDialog(null, "Creación Exitosa, Revisa tu escritorio");
            }

        }
        if (e.getSource() == emp.jMenuItem14_reporte_ventas) {

            int pregunta = JOptionPane.showConfirmDialog(null, "¿Estás seguro de querer imprimir este reporte de clientes?", "  Pregunta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (pregunta == 0) {
                Cliente cl = new Cliente();
                BoletaIndividualDao bol = new BoletaIndividualDao();
                bol.imprimirVentas(cl);
                JOptionPane.showMessageDialog(null, "Creación Exitosa, Revisa tu escritorio");
            }
        }

        if (e.getSource() == emp.mncerrarPanel) {
            int pregunta = JOptionPane.showConfirmDialog(null, "¿Estás seguro que deseas salir?", "  Pregunta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (pregunta == 0) {
                System.exit(0);
            }
        }

        if (e.getSource() == emp.volvermenu) {

            FormAdmSistema fadm = new FormAdmSistema();
            Usuario usu = new Usuario();

            ControladorTabbedPane controlTabbedPane = new ControladorTabbedPane(fadm, usu);
            fadm.txtNombreAdministrador.setText(emp.jtxNombreEmpleado.getText());
            //fadm.txtNombreAdministrador.setForeground(Color.BLACK);
            fadm.setVisible(true);
            emp.setVisible(false);
        }

        
    }
}
