package Controlador;

import Dao.BoletaIndividualDao;
import Dao.ClienteDao;
import Dao.PagosDao;
import Dao.ProductoDao;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import static java.awt.image.ImageObserver.WIDTH;
import java.text.DecimalFormat;
import java.util.Random;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import modelo.Cliente;
import modelo.Producto;
import vista.InterPagos;
import vista.MenuTienda;
import vista.InterBoletaIndividual;

public class ControladorPagos implements ActionListener, MouseListener, DocumentListener, ItemListener {

    PagosDao pag;
    Cliente cli;
    ClienteDao cliDao;
    Producto prod;
    ProductoDao prodDao;
    InterPagos interPagos;
    MenuTienda emp;
    String nomccc="";

    public ControladorPagos(PagosDao pag, Cliente cli, ClienteDao cliDao, Producto prod, ProductoDao prodDao, InterPagos interPagos,
            MenuTienda emp) {
        this.pag = pag;
        this.cli = cli;
        this.cliDao = cliDao;
        this.prod = prod;
        this.prodDao = prodDao;
        this.interPagos = interPagos;
        this.emp = emp;

        SwingUtilities.invokeLater(() -> {
            // Asegúrate de no alterar el JComboBox durante una actualización de texto
            if (interPagos.cbxCliente.isPopupVisible()) {
                interPagos.cbxCliente.setPopupVisible(false);
                interPagos.cbxCliente.setPopupVisible(true);
            } else {
                interPagos.cbxCliente.setPopupVisible(true);
            }
            nomccc = "abrir";
            
        });

        interPagos.btnBuscarPlacaVehiculoDN.addActionListener(this);

        interPagos.btnCalcularFactura.addActionListener(this);
        interPagos.btnModificarClienteFactura.addActionListener(this);
        interPagos.btnEliminarClienteFactura.addActionListener(this);
        interPagos.BtnLimpiarCasilleros.addActionListener(this);
        interPagos.tablaRegistroCliente.addMouseListener(this);
        interPagos.btnEliminarClienteFactura.addActionListener(this);
        interPagos.BtnLimpiarCasilleros.addActionListener(this);
        interPagos.btnBuscarFactura.addActionListener(this);
        interPagos.btnrestaurar.addActionListener(this);
        interPagos.btnCalcularPrecioTotalFactura.addActionListener(this);
        interPagos.btnImportaDetalleFactura.addActionListener(this);
        interPagos.cbxCliente.addItemListener(this);
        interPagos.btnImportarBoletaFactura.addActionListener(this);
        // Agregar DocumentListener a los JTextField
        interPagos.jtxDniClienteFactura.getDocument().addDocumentListener(this);
        interPagos.txtProductos.getDocument().addDocumentListener(this);
        //interPagos.getTextFieldVentas().getDocument().addDocumentListener(this);

        interPagos.jtxDniClienteFactura.getDocument().putProperty("owner", interPagos.jtxDniClienteFactura);
        interPagos.txtProductos.getDocument().putProperty("owner", interPagos.txtProductos);
        //interPagos.jtxDniClienteFactura.getDocument().putProperty("owner", interPagos.jtxDniClienteFactura);

        interPagos.txtCodPago.setEnabled(false);

        //interPagos.jlisstProductos.setVisible(false);
        //interPagos.jLayeredPane1.setLayout(null);
        //interPagos.tablaRegistroCliente.setBounds(10, 70, 770, 990);
        //interPagos.lista_clientes.setBounds(10, 0, 180, 100);
        //interPagos.lista_clientes.setBackground(Color.PINK);
        this.interPagos.txtCantproductos.setEnabled(false);

        //this.interPagos.txtIgvProductos.setEnabled(false);
        this.interPagos.txtPreciproductos.setEnabled(false);
        this.interPagos.txtIdDetalleFactura.setEnabled(false);
        this.interPagos.jlblIdDetalleFactura.setVisible(false);
        this.interPagos.txtIdDetalleFactura.setVisible(false);
        this.interPagos.btnNuevoCliente.setVisible(false);
        this.interPagos.btnCalcularPrecioTotalFactura.setEnabled(false);
        this.interPagos.btnImportaDetalleFactura.setEnabled(false);
        this.interPagos.btnImportarBoletaFactura.setEnabled(false);
        this.interPagos.btnBuscarFactura.setEnabled(false);
        //this.interPagos.btnBuscarFactura.setVisible(false);
        this.interPagos.btnCalcularFactura.setEnabled(false);
        this.interPagos.btnModificarClienteFactura.setEnabled(false);
        this.interPagos.btnEliminarClienteFactura.setEnabled(false);
        this.interPagos.btnrestaurar.setEnabled(false);
        this.interPagos.BtnLimpiarCasilleros.setEnabled(false);

        this.interPagos.cbxCargarProductosFatura.setEnabled(false);
        this.interPagos.txtCantidadProductoFactura.setEnabled(false);

        this.interPagos.bcxBuscarClientesFactura.setEnabled(false);
        this.interPagos.txtBuscarClienteFactura.setEnabled(false);

        this.interPagos.txtProductos.setEnabled(false);

        pag.mostrarTablaFacturaCliente(interPagos.tablaRegistroCliente);

        ImageIcon wallpaper = new ImageIcon("src/img/fondo3.jpg");
        Icon icono = new ImageIcon(wallpaper.getImage().getScaledInstance(1300, 560, WIDTH));
        interPagos.lbltxtImg.setIcon(icono);
        this.interPagos.repaint();

        if (interPagos.tablaRegistroCliente.getRowCount() > 0) {
            String cantidadRepuesta = interPagos.tablaRegistroCliente.getValueAt(0, 2).toString();
            String codl = interPagos.tablaRegistroCliente.getValueAt(0, 6).toString();
            interPagos.jtxDniClienteFactura.setEnabled(false);
            interPagos.jtxDniClienteFactura.setText(cantidadRepuesta);
            interPagos.txtCodPago.setEnabled(false);
            interPagos.txtCodPago.setText(codl);
            interPagos.btnBuscarPlacaVehiculoDN.setEnabled(false);
            cli.setDni(Integer.parseInt(interPagos.jtxDniClienteFactura.getText()));
            cli.setCod_pago(codl);
            this.interPagos.btnCalcularPrecioTotalFactura.setEnabled(true);
            this.interPagos.btnCalcularFactura.setEnabled(true);
            this.interPagos.btnrestaurar.setEnabled(true);
            this.interPagos.cbxCargarProductosFatura.setEnabled(true);
            this.interPagos.txtCantidadProductoFactura.setEnabled(true);
            this.interPagos.BtnLimpiarCasilleros.setEnabled(true);
            this.interPagos.bcxBuscarClientesFactura.setEnabled(true);
            this.interPagos.txtBuscarClienteFactura.setEnabled(true);
            this.interPagos.btnBuscarFactura.setEnabled(true);
            this.interPagos.txtProductos.setEnabled(true);
            //interPagos.lista_clientes.setBounds(10, 0, 180, 25);
        }
        //interPagos.jLayeredPane1.add(interPagos.tablaRegistroCliente, Integer.valueOf(0));
        //interPagos.jLayeredPane1.add(interPagos.lista_clientes, Integer.valueOf(1));
        pag.conseguirIdUsuario(cli, emp.jtxNombreEmpleado);

    }

    private void limpiarTabla() {
        this.interPagos.jtxDniClienteFactura.setText("");
        this.interPagos.txtCantidadProductoFactura.setText("");
        this.interPagos.cbxCargarProductosFatura.setSelectedIndex(0);
        this.interPagos.bcxBuscarClientesFactura.setSelectedIndex(0);
        this.interPagos.txtBuscarClienteFactura.setText("");
        this.interPagos.txtCantproductos.setText("");
        //this.interPagos.txtIgvProductos.setText("");
        this.interPagos.txtPreciproductos.setText("");
        this.pag.mostrarTablaFacturaCliente(interPagos.tablaRegistroCliente);
        this.interPagos.btnBuscarPlacaVehiculoDN.setEnabled(true);
        this.interPagos.jtxDniClienteFactura.setEnabled(true);
        this.interPagos.btnCalcularFactura.setEnabled(false);
        this.interPagos.btnModificarClienteFactura.setEnabled(false);
        this.interPagos.btnEliminarClienteFactura.setEnabled(false);
        this.interPagos.btnrestaurar.setEnabled(false);
        this.interPagos.BtnLimpiarCasilleros.setEnabled(false);
        this.interPagos.cbxCargarProductosFatura.setEnabled(false);
        this.interPagos.txtCantidadProductoFactura.setEnabled(false);
        //cl.setId_iden2(Integer.parseInt(emp.jtxNombreEmpleado.getText()));
    }

    private boolean verificarCantidad(Producto prod) {
        try {
            prod.setComprobar(Integer.parseInt(interPagos.txtCantidadProductoFactura.getText()));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //BUSCAR DNI DEL CLIENTE
        if (e.getSource() == interPagos.btnBuscarPlacaVehiculoDN) {
            if (interPagos.jtxDniClienteFactura.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Complete el campo");
            } else {
                cli.setDni(Integer.parseInt(interPagos.jtxDniClienteFactura.getText()));
                cli.setVer("Incorrecto");
                pag.obtenerListaDni(cli);
                pag.verificarDNI(cli);

                if (cli.getVer().equals("Correcto")) {

                    this.interPagos.btnCalcularFactura.setEnabled(true);
                    this.interPagos.cbxCargarProductosFatura.setEnabled(true);
                    this.interPagos.txtCantidadProductoFactura.setEnabled(true);
                    this.interPagos.txtProductos.setEnabled(true);
                    this.interPagos.BtnLimpiarCasilleros.setEnabled(true);
                    this.interPagos.jtxDniClienteFactura.setEnabled(false);

                } else {
                    this.interPagos.btnCalcularFactura.setEnabled(false);
                    this.interPagos.cbxCargarProductosFatura.setEnabled(false);
                    this.interPagos.txtCantidadProductoFactura.setEnabled(false);
                    this.interPagos.BtnLimpiarCasilleros.setEnabled(false);
                    JOptionPane.showMessageDialog(null, "DNI No Encontrado");
                }

            }
        }

        if (e.getSource() == interPagos.btnCalcularFactura) {
            if (interPagos.jtxDniClienteFactura.getText().equals("")
                    || interPagos.cbxCargarProductosFatura.getSelectedItem().toString().equals("--Selecciona--")
                    || interPagos.txtCantidadProductoFactura.getText().equals("")) {

                JOptionPane.showMessageDialog(null, "Selecciona todos los campos");

            } else {

                if (this.verificarCantidad(prod)) {
                    if (Integer.parseInt(interPagos.txtCantidadProductoFactura.getText()) <= 0) {
                        JOptionPane.showMessageDialog(null, "Cantidad inválida");
                    } else {
                        cli.setDni(Integer.parseInt(interPagos.jtxDniClienteFactura.getText()));
                        cli.setVer("Correcto");

                        pag.verificarDatosPago(cli, prod,
                                interPagos.cbxCargarProductosFatura, interPagos.txtCantidadProductoFactura);
                        /*int total = cantidadComprobacion + prod.getCantidad2();
                prod.setCantidad2(total);*/

                        if (interPagos.tablaRegistroCliente.getRowCount() > 0) {

                            interPagos.txtIdDetalleFactura.setText(interPagos.tablaRegistroCliente.getValueAt(0, 0).toString());
                        }

                        if (cli.getVer().equals("Incorrecto")) {
                            JOptionPane.showMessageDialog(null, "Opción ya ingresada");

                        } else {
                            cli.setDni(Integer.parseInt(interPagos.jtxDniClienteFactura.getText()));

                            prod.setNombre(interPagos.cbxCargarProductosFatura.getSelectedItem().toString());
                            prod.setCantidad(Integer.parseInt(interPagos.txtCantidadProductoFactura.getText()));

                            int cantidadComprobacion = prod.getCantidad();

                            pag.conseguirCantidadProductos(prod);
                            int total = prod.getCantidad() - cantidadComprobacion;

                            if (cantidadComprobacion < prod.getCantidad()) {
                                pag.disminuirStock(prod, total);
                                prod.setCantidad(Integer.parseInt(interPagos.txtCantidadProductoFactura.getText()));
                                pag.conseguirIdCliente(cli);

                                pag.conseguirIdProducto(prod);
                                if (interPagos.tablaRegistroCliente.getRowCount() == 0) {
                                    /*CODIGO FACTURA*/
                                    String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
                                    Random random = new Random();
                                    StringBuilder codigo = new StringBuilder();

                                    for (int i = 0; i < 10; i++) {
                                        int indice = random.nextInt(caracteres.length());
                                        codigo.append(caracteres.charAt(indice));
                                    }

                                    String cod = codigo.toString();
                                    cli.setCod_pago(cod);
                                    interPagos.txtCodPago.setText(cod);

                                    pag.insertarFacturaCliente(cli);
                                }

                                //csdtFac.insertarFacturaCliente(cl);
                                //cl.setId_fac_pag(Integer.parseInt(interPagos.txtIdDetalleFactura.getText()));
                                pag.conseguirIDFactura(cli);

                                if (interPagos.tablaRegistroCliente.getRowCount() > 0) {
                                    //int fila12 = interPagos.tablaRegistroCliente.getSelectedRow();
                                    int codId = Integer.parseInt(interPagos.tablaRegistroCliente.getValueAt(0, 0).toString());
                                    cli.setId_fac_pag(codId);
                                }

                                pag.insertarDatosTabladetallePago(cli, prod);
                                //Disminuir stock

                                System.out.println(total);
                                //----------------------------------------
                                pag.mostrarTablaFacturaCliente(interPagos.tablaRegistroCliente);
                                //this.ordenarTabla1();
                                this.interPagos.jtxDniClienteFactura.setEnabled(false);
                                this.interPagos.btnBuscarPlacaVehiculoDN.setEnabled(false);
                                this.interPagos.btnrestaurar.setEnabled(true);
                                this.interPagos.txtCantidadProductoFactura.setText("");
                                this.interPagos.txtProductos.setText("");
                                this.interPagos.cbxCargarProductosFatura.setSelectedIndex(0);
                                this.interPagos.bcxBuscarClientesFactura.setEnabled(true);
                                this.interPagos.txtBuscarClienteFactura.setEnabled(true);
                                this.interPagos.btnModificarClienteFactura.setEnabled(false);
                                this.interPagos.btnEliminarClienteFactura.setEnabled(false);
                                this.interPagos.btnBuscarFactura.setEnabled(true);
                                this.interPagos.btnCalcularPrecioTotalFactura.setEnabled(true);
                                JOptionPane.showMessageDialog(null, "Registro exitoso");
                            } else {//if (prod.getCantidad() - prod.getCantidad2() == 0)
                                //this.ordenarTabla1();
                                JOptionPane.showMessageDialog(null, "Elija una cantidad menor de productos");
                            }
                        }

                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Cantidad inválida");
                }
            }
        }

        /*MODIFICAR*/
        if (e.getSource() == interPagos.btnModificarClienteFactura) {

            if (interPagos.jtxDniClienteFactura.getText().equals("")
                    || interPagos.cbxCargarProductosFatura.getSelectedItem().toString().equals("--Selecciona--")
                    || interPagos.txtCantidadProductoFactura.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Selecciona todos los campos");

            } else {
                if (this.verificarCantidad(prod)) {
                    if (Integer.parseInt(interPagos.txtCantidadProductoFactura.getText()) <= 0) {
                        JOptionPane.showMessageDialog(null, "Cantidad inválida");
                    } else {

                        cli.setDni(Integer.parseInt(interPagos.jtxDniClienteFactura.getText()));
                        cli.setVer("Correcto");

                        pag.verificarDatosPago(cli, prod,
                                interPagos.cbxCargarProductosFatura, interPagos.txtCantidadProductoFactura);

                        int fila2 = interPagos.tablaRegistroCliente.getSelectedRow();
                        String producto_vari = interPagos.tablaRegistroCliente.getValueAt(fila2, 3).toString();

                        if (cli.getVer().equals("Incorrecto")
                                && !interPagos.cbxCargarProductosFatura.getSelectedItem().toString().equals(producto_vari)) {
                            JOptionPane.showMessageDialog(null, "Opción ya ingresada");

                        } else if ((cli.getVer().equals("Correcto"))
                                || (cli.getVer().equals("Incorrecto")
                                && interPagos.cbxCargarProductosFatura.getSelectedItem().toString().equals(producto_vari))) {
                            cli.setDni(Integer.parseInt(interPagos.jtxDniClienteFactura.getText()));

                            prod.setNombre(interPagos.cbxCargarProductosFatura.getSelectedItem().toString());
                            prod.setCantidad(Integer.parseInt(interPagos.txtCantidadProductoFactura.getText()));

                            int cantidadComprobacion = prod.getCantidad();

                            int fila = interPagos.tablaRegistroCliente.getSelectedRow();
                            int cantidadcambio = Integer.parseInt(interPagos.tablaRegistroCliente.getValueAt(fila, 4).toString());

                            pag.conseguirCantidadProductos(prod);
                            int total = prod.getCantidad() - cantidadComprobacion + cantidadcambio;

                            if (cantidadComprobacion <= prod.getCantidad()) {
                                pag.disminuirStock(prod, total);
                                /*
                            prod.setCantidad(Integer.parseInt(interPagos.txtCantidadProductoFactura.getText()));

                            csdtFac.conseguirIdCliente(cl);
                            csdtFac.conseguirIdAutos(aut);
                            csdtFac.conseguirIdServicio(ser);
                            csdtFac.conseguirIdProducto(prod);*/

                                int filaVerifi = interPagos.tablaRegistroCliente.getSelectedRow();
                                String producto = interPagos.tablaRegistroCliente.getValueAt(filaVerifi, 3).toString();
                                int cantidad_prod_se = Integer.parseInt(interPagos.tablaRegistroCliente.getValueAt(fila, 4).toString());

                                prod.setNombre(producto);
                                prod.setCantidad(cantidad_prod_se);

                                pag.conseguirIdCliente(cli);
                                pag.conseguirIdProducto(prod);

                                int id_cli = cli.getId();
                                int id_prod = prod.getIdProducto();
                                int cant = prod.getCantidad();

                                cli.setDni(Integer.parseInt(interPagos.jtxDniClienteFactura.getText()));
                                prod.setNombre(interPagos.cbxCargarProductosFatura.getSelectedItem().toString());
                                prod.setCantidad(Integer.parseInt(interPagos.txtCantidadProductoFactura.getText()));

                                pag.conseguirIdCliente(cli);
                                pag.conseguirIdProducto(prod);

                                cli.setId_fac_pag(Integer.parseInt(interPagos.txtIdDetalleFactura.getText()));
                                pag.modificarFacturaCliente(cli);
                                //ya tengo id cliente (dni)

                                //cl.setId_fac_pag(Integer.parseInt(interPagos.txtIdDetalleFactura.getText()));
                                cli.setCod_pago(interPagos.txtCodPago.getText());
                                pag.conseguirIDFactura(cli);

                                pag.modificarDatosTabladetallePago(cli, prod, id_prod, cant);
                                pag.mostrarTablaFacturaCliente(interPagos.tablaRegistroCliente);
                                this.interPagos.btnModificarClienteFactura.setEnabled(false);
                                this.interPagos.btnEliminarClienteFactura.setEnabled(false);

                                interPagos.txtCantidadProductoFactura.setText("");

                                interPagos.cbxCargarProductosFatura.setSelectedIndex(0);
                                JOptionPane.showMessageDialog(null, "Modificación exitosa");
                            } else {

                                JOptionPane.showMessageDialog(null, "Elija una cantidad menor de productos");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Opción ya ingresada");
                        }

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Cantidad inválida");
                }

            }

        }

        /*ELIMINAR*/
        if (e.getSource() == interPagos.btnEliminarClienteFactura) {
            int fila = interPagos.tablaRegistroCliente.getSelectedRow();
            if (fila >= 0) {

                int pregunta = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar", "  Pregunta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (pregunta == 0) {
                    prod.setNombre(interPagos.cbxCargarProductosFatura.getSelectedItem().toString());

                    int cantidadeliminar = Integer.parseInt(interPagos.tablaRegistroCliente.getValueAt(fila, 4).toString());
                    pag.conseguirCantidadProductos(prod);
                    int total2 = prod.getCantidad() + cantidadeliminar;
                    //devuelve la cantidad de productos
                    pag.disminuirStock(prod, total2);
                    cli.setId_fac_pag(Integer.parseInt(interPagos.txtIdDetalleFactura.getText()));
                    //csdtFac.eliminarDetalleFactura(cl);
                    //csdtFac.eliminarFactura(cl);

                    prod.setNombre(interPagos.cbxCargarProductosFatura.getSelectedItem().toString());

                    pag.conseguirIdProducto(prod);
                    prod.setCantidad(Integer.parseInt(interPagos.txtCantidadProductoFactura.getText()));

                    pag.eliminarDetalleFacturaUpdate(cli, prod);

                    //csdtFac.eliminarFacturaUpdate(cl);
                    pag.mostrarTablaFacturaCliente(interPagos.tablaRegistroCliente);
                    interPagos.txtCantidadProductoFactura.setText("");
                    interPagos.cbxCargarProductosFatura.setSelectedIndex(0);
                    this.interPagos.btnModificarClienteFactura.setEnabled(false);
                    this.interPagos.btnEliminarClienteFactura.setEnabled(false);

                    if (interPagos.tablaRegistroCliente.getRowCount() == 0) {
                        this.limpiarTabla();
                        this.interPagos.bcxBuscarClientesFactura.setEnabled(false);
                        this.interPagos.txtBuscarClienteFactura.setEnabled(false);
                        this.interPagos.btnBuscarFactura.setEnabled(false);

                    }

                    if (interPagos.tablaRegistroCliente.getRowCount() == 0) {
                        pag.eliminarFacturaUpdate(cli);
                        this.interPagos.btnCalcularPrecioTotalFactura.setEnabled(false);
                        this.interPagos.txtCodPago.setText("");
                        this.interPagos.txtProductos.setText("");
                        this.interPagos.jtxDniClienteFactura.requestFocus();
                    }

                    JOptionPane.showMessageDialog(null, "Eliminación exitosa");
                } else {
                    interPagos.tablaRegistroCliente.clearSelection();
                }

            } else {

            }
        }

        /*LIMPIAR*/
        if (e.getSource() == interPagos.BtnLimpiarCasilleros) {
            interPagos.jtxDniClienteFactura.setText("");
            interPagos.txtCantidadProductoFactura.setText("");
            interPagos.cbxCargarProductosFatura.setSelectedIndex(0);

            interPagos.txtCantproductos.setText("");

            //interPagos.txtIgvProductos.setText("");
            interPagos.txtPreciproductos.setText("");
            interPagos.bcxBuscarClientesFactura.setSelectedIndex(0);
            interPagos.txtBuscarClienteFactura.setText("");
            interPagos.txtBuscarClienteFactura.setEditable(true);
            pag.mostrarTablaFacturaCliente(interPagos.tablaRegistroCliente);

            this.interPagos.btnCalcularFactura.setEnabled(false);
            this.interPagos.btnModificarClienteFactura.setEnabled(false);
            this.interPagos.btnEliminarClienteFactura.setEnabled(false);
            this.interPagos.btnrestaurar.setEnabled(false);

            this.interPagos.btnCalcularPrecioTotalFactura.setEnabled(false);
            this.interPagos.btnImportaDetalleFactura.setEnabled(false);
            this.interPagos.btnImportarBoletaFactura.setEnabled(false);
            this.interPagos.btnBuscarFactura.setEnabled(false);
            interPagos.jtxDniClienteFactura.setEnabled(true);

            this.interPagos.txtProductos.setEnabled(false);
            this.interPagos.cbxCargarProductosFatura.setEnabled(false);
            this.interPagos.txtCantidadProductoFactura.setEnabled(false);

            this.interPagos.txtProductos.setText("");
            this.interPagos.cbxCargarProductosFatura.setSelectedIndex(0);
            this.interPagos.txtCantidadProductoFactura.setText("");

            if (interPagos.tablaRegistroCliente.getRowCount() > 0) {
                String cantidadRepuesta = interPagos.tablaRegistroCliente.getValueAt(0, 2).toString();
                interPagos.jtxDniClienteFactura.setEnabled(false);
                interPagos.jtxDniClienteFactura.setText(cantidadRepuesta);
                interPagos.btnBuscarPlacaVehiculoDN.setEnabled(false);
                this.interPagos.btnrestaurar.setEnabled(true);
                this.interPagos.btnCalcularFactura.setEnabled(true);
                this.interPagos.btnBuscarFactura.setEnabled(true);

                this.interPagos.bcxBuscarClientesFactura.setEnabled(true);
                this.interPagos.txtBuscarClienteFactura.setEnabled(true);
                this.interPagos.txtProductos.setEnabled(true);
                this.interPagos.cbxCargarProductosFatura.setEnabled(true);
                this.interPagos.txtCantidadProductoFactura.setEnabled(true);
                //cl.setDni(Integer.parseInt(interPagos.jtxDniClienteFactura.getText()));
                //csdtFac.cargarIdPlaca(cl, interPagos.btnCargarPlacaVehiFatura);
            }

        }

        if (e.getSource() == interPagos.btnBuscarFactura) {

            if (interPagos.txtBuscarClienteFactura.getText().equals("")
                    || interPagos.bcxBuscarClientesFactura.getSelectedItem().toString().equals("--Selecciona--")) {
                JOptionPane.showMessageDialog(null, "Complete todos los campos");
            } else {

                if (interPagos.bcxBuscarClientesFactura.getSelectedItem().toString().equals("Nombre Producto")) {
                    cli.setNombre(interPagos.txtBuscarClienteFactura.getText());
                    if (pag.buscarTablaFacturaCliente(interPagos.tablaRegistroCliente, cli, interPagos.bcxBuscarClientesFactura)) {

                        this.interPagos.btnrestaurar.setEnabled(true);

                    } else {

                        JOptionPane.showMessageDialog(null, "Ingrese datos correctos");
                    }

                    if (interPagos.tablaRegistroCliente.getRowCount() == 0) {
                        //int fila12 = interPagos.tablaRegistroCliente.getSelectedRow();
                        pag.mostrarTablaFacturaCliente(interPagos.tablaRegistroCliente);

                        JOptionPane.showMessageDialog(null, "Datos No Encontrados");
                    }

                }

            }
        }

        /*Restaurar Tabla*/
        if (e.getSource() == interPagos.btnrestaurar) {
            pag.mostrarTablaFacturaCliente(interPagos.tablaRegistroCliente);
        }

        /*CALCULAR PRECIO*/
        if (e.getSource() == interPagos.btnCalcularPrecioTotalFactura) {

            cli.setCod_pago(interPagos.txtCodPago.getText());
            pag.precioTablaDetalleFactura(cli);
            interPagos.txtCantproductos.setText(String.valueOf(cli.getCant_ser()));

            //DecimalFormat df = new DecimalFormat("#.000");
            //interPagos.txtIgvProductos.setText(String.valueOf(df.format(cli.descuento_Beneficio())));ESTOS SI QUIERES MOSTRAR CUANTO ES EL IGV
            //CREAS UN JTEXTFIELD CON ESE NOMBRE txtIgvProductos
            //interPagos.txtPrecioServicios.setText(String.valueOf(cli.pagoFinal())); SI QUIERES CON IGV Y ESO XD SOLO REEMPLAZAS POR EL DE ABAJO
            interPagos.txtPreciproductos.setText(String.valueOf(cli.getPago()));//QUITAS ESTO SI QUIERES LO DE ARRIBA
            interPagos.btnImportaDetalleFactura.setEnabled(true);
            //System.out.println(cl.getPago());
            //System.out.println(cl.getCant_ser());

        }

        if (e.getSource() == interPagos.btnImportaDetalleFactura) {
            if ((interPagos.jtxDniClienteFactura.getText().equals(""))) {
                JOptionPane.showMessageDialog(null, "No hay nada para importar");
            } else {
                cli.setId_fac_pag(Integer.parseInt(interPagos.tablaRegistroCliente.getValueAt(0, 0).toString()));
                int guardar = cli.getId_fac_pag();
                pag.modificarFacPendiente(cli);
                System.out.println("" + guardar);
                this.interPagos.btnCalcularPrecioTotalFactura.setEnabled(false);
                this.interPagos.btnImportaDetalleFactura.setEnabled(false);
                this.interPagos.btnImportarBoletaFactura.setEnabled(true);
                pag.mostrarTablaFacturaCliente(interPagos.tablaRegistroCliente);
                //prod.setCantidad2(0);
                cli.setCant_ser(Integer.parseInt(interPagos.txtCantproductos.getText()));

                this.limpiarTabla();
                interPagos.jtxDniClienteFactura.setEnabled(true);
                interPagos.btnBuscarPlacaVehiculoDN.setEnabled(true);

                interPagos.txtIdDetalleFactura.setText(String.valueOf(guardar));
                this.interPagos.txtCodPago.setText("");
                this.interPagos.txtProductos.setText("");
                JOptionPane.showMessageDialog(null, "Importe con éxito");

            }

        }

        if (e.getSource() == interPagos.btnImportarBoletaFactura) {

            //ConsultaBoletaIndividual csbl = new ConsultaBoletaIndividual();
            InterBoletaIndividual interBoleta = new InterBoletaIndividual();

            cli.setId_fac_pag(Integer.parseInt(interPagos.txtIdDetalleFactura.getText()));
            pag.conseguirNombreCliente(cli);
            cli.setVer2(emp.jtxNombreEmpleado.getText());

            BoletaIndividualDao csbl = new BoletaIndividualDao();
            csbl.imprimir(cli);

            ControladorBoletaIndividual controladoBoleta = new ControladorBoletaIndividual(cli, csbl, interBoleta);
            emp.dskPaneSistema.add(interBoleta);
            interBoleta.setVisible(true);

        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {

        /*JList list = (JList) e.getSource();

            String selectedClient = (String) list.getSelectedValue();

            if (selectedClient != null) {
                System.out.println("Elemento seleccionado: " + selectedClient);

                String[] partes = selectedClient.split(" - ");

                if (partes.length > 0) {

                    String numero = partes[0].trim();
                    System.out.println("Número obtenido: " + numero);

                    interPagos.jtxDniClienteFactura.setText(numero);
                }

                interPagos.lista_clientes.setVisible(false);
            }*/
        if (e.getSource() == interPagos.tablaRegistroCliente) {

            int fila = interPagos.tablaRegistroCliente.getSelectedRow();

            if (fila >= 0) {
                interPagos.txtIdDetalleFactura.setText(interPagos.tablaRegistroCliente.getValueAt(fila, 0).toString());
                interPagos.jtxDniClienteFactura.setText(interPagos.tablaRegistroCliente.getValueAt(fila, 2).toString());
                cli.setDni(Integer.parseInt(interPagos.jtxDniClienteFactura.getText()));
                interPagos.txtProductos.setText(interPagos.tablaRegistroCliente.getValueAt(fila, 3).toString());
                interPagos.cbxCargarProductosFatura.setSelectedItem(interPagos.tablaRegistroCliente.getValueAt(fila, 3).toString());
                interPagos.txtCantidadProductoFactura.setText(interPagos.tablaRegistroCliente.getValueAt(fila, 4).toString());
                this.interPagos.btnModificarClienteFactura.setEnabled(true);
                this.interPagos.btnEliminarClienteFactura.setEnabled(true);

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

    @Override
    public void insertUpdate(DocumentEvent e) {

        JTextField source = (JTextField) e.getDocument().getProperty("owner");

        if (source != null) {
            if (source == interPagos.jtxDniClienteFactura) {
                // Actualiza la lista de productos
                //interPagos.lista_clientes.setVisible(true);
                pag.cargarClientes(interPagos.jtxDniClienteFactura.getText(), interPagos.cbxCliente);
                //pag.cargarClientes(interPagos.jtxDniClienteFactura.getText(), interPagos.lista_clientes);

                
                
                if(nomccc.equals("abrir")){
                    if (interPagos.cbxCliente.isPopupVisible()) {
                        interPagos.cbxCliente.setPopupVisible(false);
                        interPagos.cbxCliente.setPopupVisible(true);
                    } else {
                        interPagos.cbxCliente.setPopupVisible(true);
                    }
                }

            }

            if (source == interPagos.txtProductos) {
                // Actualiza la lista de productos
                //interPagos.lista_clientes.setVisible(true);
                pag.cargarItemProducto(interPagos.cbxCargarProductosFatura, interPagos.txtProductos.getText());

                //pag.cargarClientes(interPagos.jtxDniClienteFactura.getText(), interPagos.lista_clientes);
                if(nomccc.equals("abrir")){
                    if (interPagos.cbxCargarProductosFatura.isPopupVisible()) {
                        interPagos.cbxCargarProductosFatura.setPopupVisible(false);
                        interPagos.cbxCargarProductosFatura.setPopupVisible(true);
                    } else {
                        interPagos.cbxCargarProductosFatura.setPopupVisible(true);
                    }
                }
            }

        } else {
            System.out.println("El JTextField es null");
        }

    }

    @Override
    public void removeUpdate(DocumentEvent e) {

        JTextField source = (JTextField) e.getDocument().getProperty("owner");

        if (source != null) {
            if (source == interPagos.jtxDniClienteFactura) {
                pag.cargarClientes(interPagos.jtxDniClienteFactura.getText(), interPagos.cbxCliente);

                // 
                //interPagos.lista_clientes.setVisible(true);
                //pag.cargarClientes(interPagos.jtxDniClienteFactura.getText(), interPagos.lista_clientes);
                //if (interPagos.jtxDniClienteFactura.getText().equals("")) {
                //  interPagos.lista_clientes.setVisible(false);
                //}
                if(nomccc.equals("abrir")){
                    if (interPagos.cbxCliente.isPopupVisible()) {
                        interPagos.cbxCliente.setPopupVisible(false);
                        interPagos.cbxCliente.setPopupVisible(true);
                    } else {
                        interPagos.cbxCliente.setPopupVisible(true);
                    }
                }

            }

            if (source == interPagos.txtProductos) {
                // Actualiza la lista de productos
                //interPagos.lista_clientes.setVisible(true);
                pag.cargarItemProducto(interPagos.cbxCargarProductosFatura, interPagos.txtProductos.getText());

                //pag.cargarClientes(interPagos.jtxDniClienteFactura.getText(), interPagos.lista_clientes);
                if(nomccc.equals("abrir")){
                    if (interPagos.cbxCargarProductosFatura.isPopupVisible()) {
                        interPagos.cbxCargarProductosFatura.setPopupVisible(false);
                        interPagos.cbxCargarProductosFatura.setPopupVisible(true);
                    } else {
                        interPagos.cbxCargarProductosFatura.setPopupVisible(true);
                    }
                }
            }

        } else {
            System.out.println("El JTextField es null");
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {

                    String selectedItem = interPagos.cbxCliente.getSelectedItem().toString();
                    String selectedItem2 = interPagos.cbxCargarProductosFatura.getSelectedItem().toString();

                    if (!selectedItem.equals("--Selecciona--")) {

                        String[] parts = selectedItem.split(" - ");

                        String numero = parts[0];

                        System.out.println("Número: " + numero);

                        interPagos.jtxDniClienteFactura.setText(numero);
                    }

                    if (!selectedItem2.equals("--Selecciona--")) {
                        System.out.println("Número: " + selectedItem2);
                        interPagos.txtProductos.setText(selectedItem2);
                    }
                }
            });
        }
    }
}
