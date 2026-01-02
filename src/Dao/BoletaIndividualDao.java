/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import conexion.Conexion;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import modelo.Cliente;

/**
 *
 * @author JEFFERSON
 */
public class BoletaIndividualDao extends Conexion {

    public void datosEmpresa(Cliente cl) {

        String consulta = "select * from empresa";
        try {
            cn = Conexion.getConexion();
            ps = cn.prepareStatement(consulta);
            rs = ps.executeQuery();
            while (rs.next()) {
                cl.setCod(rs.getString("ruc"));
                cl.setCorreo(rs.getString("dueño"));
                cl.setDireccion(rs.getString("direccion"));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "DNI no registrado" + e.toString());

        }

    }

    public void datosCliente(Cliente cl) {

        String consulta = "select cl.id_cliente,us.usuario,concat_ws(' ', us.nombre,us.apellido) as nombre_us,"
                + "concat_ws(' ', cl.nombre,cl.apellido) as nombre_al,cl.dni,cl.direccion,cl.celular,"
                + ""
                + "f.fecha,f.total "
                + "from factura f "
                + "inner join clientes cl "
                + "on f.id_cliente=cl.id_cliente "
                + "inner join usuarios us "
                + "on f.id_usuarios=us.id "
                + "inner join detalle_factura defac "
                + "on f.id=defac.id_factura "
                + "where defac.id_factura=" + cl.getId_fac_pag();
        try {
            cn = Conexion.getConexion();
            ps = cn.prepareStatement(consulta);
            rs = ps.executeQuery();
            while (rs.next()) {
                cl.setId_iden(rs.getInt("id_cliente"));
                //cl.setId_iden2(rs.getInt("id_usuarios"));
                cl.setUsu(rs.getString("usuario"));
                cl.setVer2(rs.getString("nombre_us"));
                cl.setNombre(rs.getString("nombre_al"));
                cl.setDni(rs.getInt("dni"));
                cl.setDireccion(rs.getString("direccion"));
                cl.setTelefono(rs.getInt("celular"));

                cl.setFecha(rs.getString("fecha"));
                cl.setPago(rs.getFloat("total"));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "DNI no registrado" + e.toString());

        }

    }

    public void precioTablaDetalleFactura(Cliente cl) {

        String consulta = "";

        consulta = "select det.precio , det.cant_prod_usados from detalle_factura det "
                + "inner join producto prod "
                + "on det.id_producto = prod.idProducto "
                + "inner join factura f "
                + "on f.id = det.id_factura "
                + "where f.id = '" + cl.getId_fac_pag() + "'";

        try {
            cn = Conexion.getConexion();
            ps = cn.prepareStatement(consulta);
            rs = ps.executeQuery();
            //setNombreComboCli(sr1.getString("id_cliente"));
            double pagoAcumulador = 0;
            int a = 0;
            while (rs.next()) {
                cl.setPago(rs.getInt("precio"));
                cl.setCant_ser(rs.getInt("cant_prod_usados"));

                //double pagoAcumulador=0;
                pagoAcumulador = pagoAcumulador + cl.getPago();
                a = a + cl.getCant_ser();

                cl.setPago(pagoAcumulador);

                cl.setCant_ser(a);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar idcliente" + e.toString());
        }

    }

    public void mostrarTabla(Cliente cl, JTable paramTable) {

        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int filas, int columnas) {
                if (columnas == 5) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        /*FormAdmSistema adm=new FormAdmSistema();
        modelo=(DefaultTableModel) adm.jTable1.getModel();*/
        TableRowSorter<TableModel> ordenarTable = new TableRowSorter<TableModel>(modelo);
        paramTable.setRowSorter(ordenarTable);

        modelo.addColumn("Código");
        modelo.addColumn("Producto");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Precio");
        modelo.addColumn("Total");

        paramTable.setModel(modelo);
        String consulta = "select prod.idProducto, prod.nombre, det.cant_prod_usados, prod.precio as pre, det.precio as tot from factura f "
                + "inner join detalle_factura det "
                + "on f.id = det.id_factura "
                + "inner join producto prod "
                + "on det.id_producto = prod.idProducto "
                + "where f.id = " + cl.getId_fac_pag();

        try {
            cn = Conexion.getConexion();
            ps = cn.prepareStatement(consulta);
            rs = ps.executeQuery();
            DecimalFormat df = new DecimalFormat("PROD00000");

            String[] datos = new String[5];

            while (rs.next()) {
                datos[0] = df.format(rs.getInt(1));
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                modelo.addRow(datos);
            }
            paramTable.setModel(modelo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error tabla" + e.toString());

        }
    }

    public void imprimir(Cliente cl) {
        Document documento = new Document();
        try {

            String ruta = System.getProperty("user.home");
            PdfWriter.getInstance(documento, new FileOutputStream(ruta + "/Desktop/" + cl.getNombre().trim() + ".pdf"));
            com.itextpdf.text.Image header = com.itextpdf.text.Image.getInstance("src/Img/BannerPDF.png");
            header.scaleToFit(400, 800);
            header.setAlignment(Chunk.ALIGN_CENTER);
            Paragraph parrafo = new Paragraph();
            parrafo.setAlignment(Paragraph.ALIGN_CENTER);
            DecimalFormat df = new DecimalFormat("FAC00000");
            parrafo.add("BODEGA X.X.X \n "
                    + "Encuentra todo tipo de abarrotes \n "
                    + "A tu disposición. Venta de bíberes\n "
                    + "Código de Factura: " + df.format(cl.getId_fac_pag())
                    + "\n \n \n");
            parrafo.setFont(FontFactory.getFont("Tahoma", 14, Font.BOLD, BaseColor.DARK_GRAY));

            documento.open();
            documento.add(header);
            documento.add(parrafo);

            PdfPTable tablaClientePrin = new PdfPTable(2);

            tablaClientePrin.setWidthPercentage(100);
            float[] tamEncabezado = new float[]{40f, 30f};
            tablaClientePrin.setWidths(tamEncabezado);
            tablaClientePrin.setHorizontalAlignment(Element.ALIGN_LEFT);

            this.datosEmpresa(cl);
            tablaClientePrin.addCell("<<<<Datos Generales>>>> \nRuc: " + cl.getCod() + "\nGerente:" + cl.getCorreo()
                    + "\nDirección: " + cl.getDireccion() + "\nTelf: (+51) 955 240 301 \n");

            this.datosCliente(cl);
            tablaClientePrin.addCell("<<<<Datos Cliente>>>> \nNombre: " + cl.getNombre() + "\nDni: " + cl.getDni()
                    + "\nDirección: " + cl.getDireccion() + "\nTelf: (+51) " + cl.getTelefono() + "\n");

            //PdfPTable tablaCliente2 = new PdfPTable(5);
            documento.add(tablaClientePrin);

            Paragraph parrafo3 = new Paragraph();
            parrafo3.setAlignment(Paragraph.ALIGN_CENTER);
            parrafo3.add("\n \n \nInformación de los Productos \n \n");
            DecimalFormat df2 = new DecimalFormat("PROD00000");
            documento.add(parrafo3);

            PdfPTable tablaCliente = new PdfPTable(5);

            tablaCliente.addCell("Código");
            tablaCliente.addCell("Producto");
            tablaCliente.addCell("Cantidad");
            tablaCliente.addCell("Precio");
            tablaCliente.addCell("Total");

            try {
                cn = Conexion.getConexion();
                ps = cn.prepareStatement("select prod.idProducto, prod.nombre, det.cant_prod_usados, prod.precio as pre, det.precio as tot from factura f "
                        + "inner join detalle_factura det "
                        + "on f.id = det.id_factura "
                        + "inner join producto prod "
                        + "on det.id_producto = prod.idProducto "
                        + "where f.id = " + cl.getId_fac_pag()
                );
                rs = ps.executeQuery();

                if (rs.next()) {
                    do {

                        tablaCliente.addCell(df2.format(rs.getInt(1)));
                        tablaCliente.addCell(rs.getString(2));
                        tablaCliente.addCell(rs.getString(3));
                        tablaCliente.addCell(rs.getString(4));
                        tablaCliente.addCell("S/." + rs.getString(5));

                    } while (rs.next());
                    documento.add(tablaCliente);
                }

            } catch (Exception e) {
            }

            /*Paragraph parrafo2 = new Paragraph();
            parrafo2.setAlignment(Paragraph.ALIGN_CENTER);

            documento.add(parrafo2);*/
            Paragraph parrafo4 = new Paragraph();
            parrafo4.setAlignment(Paragraph.ALIGN_CENTER);
            parrafo4.add("\n \nTotal: S/." + cl.pago());

            documento.add(parrafo4);

            Paragraph parrafo5 = new Paragraph();
            parrafo5.setAlignment(Paragraph.ALIGN_CENTER);
            parrafo5.add("\n \n________________________                                ___________________________"
                    + "\n Firma Cliente: " + cl.getNombre() + "                                   Firma Vendedor: " + cl.getVer2());

            documento.add(parrafo5);

            documento.close();
        } catch (DocumentException | IOException e) {

        }

    }

    public void imprimirClientes(Cliente cl) {
        Document documento = new Document();
        try {

            String ruta = System.getProperty("user.home");
            PdfWriter.getInstance(documento, new FileOutputStream(ruta + "/Desktop/" + "ReporteClientes" + ".pdf"));
            com.itextpdf.text.Image header = com.itextpdf.text.Image.getInstance("src/Img/BannerPDF.png");
            header.scaleToFit(400, 800);
            header.setAlignment(Chunk.ALIGN_CENTER);
            Paragraph parrafo = new Paragraph();
            parrafo.setAlignment(Paragraph.ALIGN_CENTER);
            parrafo.add("BODEGA X.X.X \n "
                    + "Encuentra todo tipo de abarrotes \n "
                    + "A tu disposición. Venta de bíberes\n "
                    + "\n \n \n");
            parrafo.setFont(FontFactory.getFont("Tahoma", 14, Font.BOLD, BaseColor.DARK_GRAY));

            documento.open();
            documento.add(header);
            documento.add(parrafo);

            PdfPTable tablaClientePrin = new PdfPTable(2);

            tablaClientePrin.setWidthPercentage(100);
            float[] tamEncabezado = new float[]{40f, 30f};
            tablaClientePrin.setWidths(tamEncabezado);
            tablaClientePrin.setHorizontalAlignment(Element.ALIGN_LEFT);

            this.datosEmpresa(cl);
            tablaClientePrin.addCell("<<<<Datos Generales>>>> \nRuc: " + cl.getCod() + "\nGerente:" + cl.getCorreo()
                    + "\nDirección: " + cl.getDireccion() + "\nTelf: (+51) 955 240 301 \n");

            //PdfPTable tablaCliente2 = new PdfPTable(5);
            documento.add(tablaClientePrin);

            Paragraph parrafo3 = new Paragraph();
            parrafo3.setAlignment(Paragraph.ALIGN_CENTER);
            parrafo3.add("\n \n \nInformación de los Clientes \n \n");
            DecimalFormat df2 = new DecimalFormat("CLI00000");
            documento.add(parrafo3);

            PdfPTable tablaCliente = new PdfPTable(8);

            tablaCliente.addCell("Código");
            tablaCliente.addCell("Nombre");
            tablaCliente.addCell("Apellido");
            tablaCliente.addCell("DNI");
            tablaCliente.addCell("Dirección");
            tablaCliente.addCell("Celular");
            tablaCliente.addCell("Tipo");
            tablaCliente.addCell("Fecha");

            try {
                cn = Conexion.getConexion();
                ps = cn.prepareStatement("select * from clientes "
                );
                rs = ps.executeQuery();

                if (rs.next()) {
                    do {

                        tablaCliente.addCell(df2.format(rs.getInt(1)));

                        for (int i = 2; i < 9; i++) {
                            tablaCliente.addCell(rs.getString(i));
                        }

                    } while (rs.next());
                    documento.add(tablaCliente);
                }

            } catch (Exception e) {
            }

            /*Paragraph parrafo2 = new Paragraph();
            parrafo2.setAlignment(Paragraph.ALIGN_CENTER);

            documento.add(parrafo2);*/
            Paragraph parrafo5 = new Paragraph();
            parrafo5.setAlignment(Paragraph.ALIGN_CENTER);
            parrafo5.add("\n \n________________________"
                    + "\nFirma Boleta: " + cl.getVer2());

            documento.add(parrafo5);

            documento.close();
        } catch (DocumentException | IOException e) {

        }

    }

    public void imprimirCategorias(Cliente cl) {
        Document documento = new Document();
        try {

            String ruta = System.getProperty("user.home");
            PdfWriter.getInstance(documento, new FileOutputStream(ruta + "/Desktop/" + "ReporteCategorias" + ".pdf"));
            com.itextpdf.text.Image header = com.itextpdf.text.Image.getInstance("src/Img/BannerPDF.png");
            header.scaleToFit(400, 800);
            header.setAlignment(Chunk.ALIGN_CENTER);
            Paragraph parrafo = new Paragraph();
            parrafo.setAlignment(Paragraph.ALIGN_CENTER);
            parrafo.add("BODEGA X.X.X \n "
                    + "Encuentra todo tipo de abarrotes \n "
                    + "A tu disposición. Venta de bíberes\n "
                    + "\n \n \n");
            parrafo.setFont(FontFactory.getFont("Tahoma", 14, Font.BOLD, BaseColor.DARK_GRAY));

            documento.open();
            documento.add(header);
            documento.add(parrafo);

            PdfPTable tablaClientePrin = new PdfPTable(2);

            tablaClientePrin.setWidthPercentage(100);
            float[] tamEncabezado = new float[]{40f, 30f};
            tablaClientePrin.setWidths(tamEncabezado);
            tablaClientePrin.setHorizontalAlignment(Element.ALIGN_LEFT);

            this.datosEmpresa(cl);
            tablaClientePrin.addCell("<<<<Datos Generales>>>> \nRuc: " + cl.getCod() + "\nGerente:" + cl.getCorreo()
                    + "\nDirección: " + cl.getDireccion() + "\nTelf: (+51) 955 240 301 \n");

            //PdfPTable tablaCliente2 = new PdfPTable(5);
            documento.add(tablaClientePrin);

            Paragraph parrafo3 = new Paragraph();
            parrafo3.setAlignment(Paragraph.ALIGN_CENTER);
            parrafo3.add("\n \n \nInformación de las Categorías \n \n");
            DecimalFormat df2 = new DecimalFormat("CAT00000");
            documento.add(parrafo3);

            PdfPTable tablaCliente = new PdfPTable(2);

            tablaCliente.addCell("Código");
            tablaCliente.addCell("Nombre");

            try {
                cn = Conexion.getConexion();
                ps = cn.prepareStatement("select * from categorias "
                );
                rs = ps.executeQuery();

                if (rs.next()) {
                    do {

                        tablaCliente.addCell(df2.format(rs.getInt(1)));
                        tablaCliente.addCell(rs.getString(2));

                    } while (rs.next());
                    documento.add(tablaCliente);
                }

            } catch (Exception e) {
            }

            /*Paragraph parrafo2 = new Paragraph();
            parrafo2.setAlignment(Paragraph.ALIGN_CENTER);

            documento.add(parrafo2);*/
            Paragraph parrafo5 = new Paragraph();
            parrafo5.setAlignment(Paragraph.ALIGN_CENTER);
            parrafo5.add("\n \n________________________"
                    + "\nFirma Boleta: " + cl.getVer2());

            documento.add(parrafo5);

            documento.close();
        } catch (DocumentException | IOException e) {

        }

    }

    public void imprimirProductos(Cliente cl) {
        Document documento = new Document();
        try {

            String ruta = System.getProperty("user.home");
            PdfWriter.getInstance(documento, new FileOutputStream(ruta + "/Desktop/" + "ReporteProductos" + ".pdf"));
            com.itextpdf.text.Image header = com.itextpdf.text.Image.getInstance("src/Img/BannerPDF.png");
            header.scaleToFit(400, 800);
            header.setAlignment(Chunk.ALIGN_CENTER);
            Paragraph parrafo = new Paragraph();
            parrafo.setAlignment(Paragraph.ALIGN_CENTER);
            parrafo.add("BODEGA X.X.X \n "
                    + "Encuentra todo tipo de abarrotes \n "
                    + "A tu disposición. Venta de bíberes\n "
                    + "\n \n \n");
            parrafo.setFont(FontFactory.getFont("Tahoma", 14, Font.BOLD, BaseColor.DARK_GRAY));

            documento.open();
            documento.add(header);
            documento.add(parrafo);

            PdfPTable tablaClientePrin = new PdfPTable(2);

            tablaClientePrin.setWidthPercentage(100);
            float[] tamEncabezado = new float[]{40f, 30f};
            tablaClientePrin.setWidths(tamEncabezado);
            tablaClientePrin.setHorizontalAlignment(Element.ALIGN_LEFT);

            this.datosEmpresa(cl);
            tablaClientePrin.addCell("<<<<Datos Generales>>>> \nRuc: " + cl.getCod() + "\nGerente:" + cl.getCorreo()
                    + "\nDirección: " + cl.getDireccion() + "\nTelf: (+51) 955 240 301 \n");

            //PdfPTable tablaCliente2 = new PdfPTable(5);
            documento.add(tablaClientePrin);

            Paragraph parrafo3 = new Paragraph();
            parrafo3.setAlignment(Paragraph.ALIGN_CENTER);
            parrafo3.add("\n \n \nInformación de los Productos \n \n");
            DecimalFormat df2 = new DecimalFormat("PROD00000");
            DecimalFormat df3 = new DecimalFormat("CAT00000");
            documento.add(parrafo3);

            PdfPTable tablaCliente = new PdfPTable(7);

            tablaCliente.addCell("Código");
            tablaCliente.addCell("Nombre");
            tablaCliente.addCell("Stock");
            tablaCliente.addCell("Precio");
            tablaCliente.addCell("Descripción");
            tablaCliente.addCell("Código Cat");
            tablaCliente.addCell("Descripción");

            try {
                cn = Conexion.getConexion();
                ps = cn.prepareStatement("select prod.idProducto, prod.nombre as nom, prod.cantidad, prod.precio,"
                        + "prod.descripcion, cat.idCategoria, cat.descripcion as des from producto prod "
                        + "inner join categorias cat "
                        + "on prod.idCategoria = cat.idCategoria"
                );
                rs = ps.executeQuery();

                if (rs.next()) {
                    do {

                        tablaCliente.addCell(df2.format(rs.getInt(1)));

                        for (int i = 2; i < 6; i++) {
                            tablaCliente.addCell(rs.getString(i));
                        }
                        tablaCliente.addCell(df3.format(rs.getInt(6)));
                        tablaCliente.addCell(rs.getString(7));

                    } while (rs.next());
                    documento.add(tablaCliente);
                }

            } catch (Exception e) {
            }

            /*Paragraph parrafo2 = new Paragraph();
            parrafo2.setAlignment(Paragraph.ALIGN_CENTER);

            documento.add(parrafo2);*/
            Paragraph parrafo5 = new Paragraph();
            parrafo5.setAlignment(Paragraph.ALIGN_CENTER);
            parrafo5.add("\n \n________________________"
                    + "\nFirma Boleta: " + cl.getVer2());

            documento.add(parrafo5);

            documento.close();
        } catch (DocumentException | IOException e) {

        }

    }

    public void imprimirVentas(Cliente cl) {
        Document documento = new Document();
        try {

            String ruta = System.getProperty("user.home");
            PdfWriter.getInstance(documento, new FileOutputStream(ruta + "/Desktop/" + "ReporteVentas" + ".pdf"));
            com.itextpdf.text.Image header = com.itextpdf.text.Image.getInstance("src/Img/BannerPDF.png");
            header.scaleToFit(400, 800);
            header.setAlignment(Chunk.ALIGN_CENTER);
            Paragraph parrafo = new Paragraph();
            parrafo.setAlignment(Paragraph.ALIGN_CENTER);
            parrafo.add("BODEGA X.X.X \n "
                    + "Encuentra todo tipo de abarrotes \n "
                    + "A tu disposición. Venta de bíberes\n "
                    + "\n \n \n");
            parrafo.setFont(FontFactory.getFont("Tahoma", 14, Font.BOLD, BaseColor.DARK_GRAY));

            documento.open();
            documento.add(header);
            documento.add(parrafo);

            PdfPTable tablaClientePrin = new PdfPTable(2);

            tablaClientePrin.setWidthPercentage(100);
            float[] tamEncabezado = new float[]{40f, 30f};
            tablaClientePrin.setWidths(tamEncabezado);
            tablaClientePrin.setHorizontalAlignment(Element.ALIGN_LEFT);

            this.datosEmpresa(cl);
            tablaClientePrin.addCell("<<<<Datos Generales>>>> \nRuc: " + cl.getCod() + "\nGerente:" + cl.getCorreo()
                    + "\nDirección: " + cl.getDireccion() + "\nTelf: (+51) 955 240 301 \n");

            //PdfPTable tablaCliente2 = new PdfPTable(5);
            documento.add(tablaClientePrin);

            Paragraph parrafo3 = new Paragraph();
            parrafo3.setAlignment(Paragraph.ALIGN_CENTER);
            parrafo3.add("\n \n \nInformación de las Ventas \n \n");
            DecimalFormat df2 = new DecimalFormat("FAC00000");
            documento.add(parrafo3);

            PdfPTable tablaCliente = new PdfPTable(6);

            tablaCliente.addCell("Código");
            tablaCliente.addCell("Cliente");
            tablaCliente.addCell("DNI");
            tablaCliente.addCell("Código");
            tablaCliente.addCell("Fecha");
            tablaCliente.addCell("Estado");

            try {
                cn = Conexion.getConexion();
                ps = cn.prepareStatement("select fac.id as idFac, concat_ws(' ', cl.nombre,cl.apellido) as nom, "
                        + " "
                        + "                cl.dni as dni, fac.codigo, fac.fecha, fac.estado  from factura fac "
                        + "                inner join detalle_factura det "
                        + "                on fac.id = det.id_factura "
                        + "                inner join clientes cl "
                        + "                on fac.id_cliente = cl.id_cliente "
                        + "                inner join producto prod "
                        + "                on det.id_producto = prod.idProducto "
                        + "                group by fac.id "
                        + "                order by fac.id asc"
                );
                rs = ps.executeQuery();

                if (rs.next()) {
                    do {

                        tablaCliente.addCell(df2.format(rs.getInt(1)));

                        for (int i = 2; i < 7; i++) {
                            tablaCliente.addCell(rs.getString(i));
                        }

                    } while (rs.next());
                    documento.add(tablaCliente);
                }

            } catch (Exception e) {
            }

            /*Paragraph parrafo2 = new Paragraph();
            parrafo2.setAlignment(Paragraph.ALIGN_CENTER);

            documento.add(parrafo2);*/
            Paragraph parrafo5 = new Paragraph();
            parrafo5.setAlignment(Paragraph.ALIGN_CENTER);
            parrafo5.add("\n \n________________________"
                    + "\nFirma Boleta: " + cl.getVer2());

            documento.add(parrafo5);

            documento.close();
        } catch (DocumentException | IOException e) {

        }

    }

}
