/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;


import Dao.BoletaIndividualDao;
import Dao.PagosDao;
import com.itextpdf.text.pdf.qrcode.BitMatrix;
import com.itextpdf.text.pdf.qrcode.QRCodeWriter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import java.text.DecimalFormat;

import javax.swing.JOptionPane;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import modelo.Cliente;
import vista.InterBoletaIndividual;

/**
 *
 * @author JEFFERSON
 */
public class ControladorBoletaIndividual implements ActionListener, MouseListener {

    Cliente cl;
    BoletaIndividualDao csbl;
    InterBoletaIndividual interBoleta;

    public void ordenarTabla() {
        interBoleta.tblBoletaFactura.getColumnModel().getColumn(0).setPreferredWidth(30);
        interBoleta.tblBoletaFactura.getColumnModel().getColumn(0).setResizable(false);
        interBoleta.tblBoletaFactura.getColumnModel().getColumn(1).setPreferredWidth(180);
        interBoleta.tblBoletaFactura.getColumnModel().getColumn(1).setResizable(false);
        interBoleta.tblBoletaFactura.getColumnModel().getColumn(2).setPreferredWidth(200);
        interBoleta.tblBoletaFactura.getColumnModel().getColumn(2).setResizable(false);
        interBoleta.tblBoletaFactura.getColumnModel().getColumn(3).setPreferredWidth(100);
        interBoleta.tblBoletaFactura.getColumnModel().getColumn(3).setResizable(false);
        interBoleta.tblBoletaFactura.getColumnModel().getColumn(4).setPreferredWidth(90);

    }

    public ControladorBoletaIndividual(Cliente cl, BoletaIndividualDao csbl, InterBoletaIndividual interBoleta) {
        this.cl = cl;
        this.csbl = csbl;
        this.interBoleta = interBoleta;

        DecimalFormat df = new DecimalFormat("FAC-00000");
        DecimalFormat df2 = new DecimalFormat("CLI00000");
        //DecimalFormat df3 = new DecimalFormat("USU00000");
        csbl.datosCliente(cl);
        
        csbl.precioTablaDetalleFactura(cl);
        //csbl.datosUsuario(cl);

        this.interBoleta.jTextField1.setText(df.format(cl.getId_fac_pag()));
        this.interBoleta.jTextField2.setText(df2.format(cl.getId_iden()));
        this.interBoleta.jTextField3.setText(cl.getNombre());
        this.interBoleta.jTextField4.setText(String.valueOf(cl.getDni()));
        this.interBoleta.jTextField5.setText(cl.getDireccion());
        this.interBoleta.jTextField6.setText(String.valueOf(cl.getTelefono()));
        this.interBoleta.jTextField7.setText(cl.getFecha());
        this.interBoleta.jTextField8.setText(cl.getVer2());
        //this.interBoleta.jTextField9.setText(df3.format(cl.getId_iden2()));
        this.interBoleta.jTextField9.setText(cl.getUsu());
        this.interBoleta.jTextField10.setText(String.valueOf(cl.getCant_ser()));
        this.interBoleta.jTextField11.setText("S/."+String.valueOf(cl.getPago()));
        //this.interBoleta.jTextField12.setText(String.valueOf(cl.getBenef_desc()));
        //this.interBoleta.jTextField13.setText(String.valueOf(cl.getPag_final()));
        this.interBoleta.jlalbNombreClie.setText(cl.getNombre());
        this.interBoleta.jlbNomTrab.setText(cl.getVer2());

        csbl.datosEmpresa(cl);
        this.interBoleta.jTextField14.setText(cl.getCod());
        this.interBoleta.jTextField15.setText(cl.getCorreo());
        this.interBoleta.jTextField16.setText(cl.getDireccion());
        csbl.mostrarTabla(cl, this.interBoleta.tblBoletaFactura);
        //this.interBoleta.xd.setEditable(false);
        this.interBoleta.xd.requestFocus();
        this.ordenarTabla();
        this.interBoleta.btnImprimirBoleta.addActionListener(this);

    }

    public void crear() {
        //String ruta = System.getProperty("user.home");

        int size = 100;
        String FileType = "png";

        String codigo = "https://compuempresa.com/info/sasel-automotriz-eirl-20609926989".trim();
        // Elegir la ruta de la imagen
        String filePath = "C:/Users/JEFFERSON/Desktop/sad";
        //String filePath = "";
        
        
        UUID uuid = UUID.randomUUID();
        String randonName = uuid.toString();

        // Generar el QR 
        com.google.zxing.qrcode.QRCodeWriter qrcode = new com.google.zxing.qrcode.QRCodeWriter();
        try {
            com.google.zxing.common.BitMatrix matrix = qrcode.encode(codigo, BarcodeFormat.QR_CODE, size, size);
            File f = new File(filePath + "/" + randonName + "." + FileType);
            int matrixWidth = matrix.getWidth();
            BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
            image.createGraphics();

            Graphics2D gd = (Graphics2D) image.getGraphics();
            gd.setColor(Color.WHITE); // Fondo
            gd.fillRect(0, 0, size, size);
            gd.setColor(Color.black); // Qr

            for (int b = 0; b < matrixWidth; b++) {
                for (int j = 0; j < matrixWidth; j++) {
                    if (matrix.get(b, j)) {
                        gd.fillRect(b, j, 1, 1);
                    }
                }
            }

            // Mostrar la imagen generada
            ImageIO.write(image, FileType, f);
            Image mImagen = new ImageIcon(filePath + "/" + randonName + "." + FileType).getImage();
            ImageIcon mIcono = new ImageIcon(mImagen.getScaledInstance(interBoleta.jLabel42.getWidth(), interBoleta.jLabel42.getHeight(), 0));
            interBoleta.jLabel42.setIcon(mIcono);

        } catch (WriterException ex) {
            Logger.getLogger(ControladorBoletaIndividual.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ControladorBoletaIndividual.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == interBoleta.btnImprimirBoleta) {

            interBoleta.btnImprimirBoleta.setVisible(false);
            interBoleta.jpanelFac.setVisible(false);
            
            this.crear();
            interBoleta.jLabel42.setVisible(true);
            
            PrinterJob job = PrinterJob.getPrinterJob();

            job.setPrintable(this.interBoleta);

            if (job.printDialog()) {
                try {
                    job.print();
                } catch (PrinterException ex) {
                }
            } else {
                JOptionPane.showMessageDialog(this.interBoleta, "Impresión Cancelada");
                interBoleta.btnImprimirBoleta.setVisible(true);
                interBoleta.jpanelFac.setVisible(true);
                interBoleta.jLabel42.setVisible(false);
            }

            interBoleta.btnImprimirBoleta.setVisible(true);
            interBoleta.jpanelFac.setVisible(true);
            interBoleta.jLabel42.setVisible(false);
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {

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

    /*@Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex == 0) {
            Graphics2D graphics2d = (Graphics2D) graphics;
            graphics2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
            printAll(graphics2d);
            return PAGE_EXISTS;
        } else {
            return NO_SUCH_PAGE;
        }
    }*/
}
