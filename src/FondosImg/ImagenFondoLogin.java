/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FondosImg;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author JEFFERSON
 */
    public class ImagenFondoLogin extends JPanel {

        private Image img;
        //fondoLog2

        @Override
        public void paint(Graphics g) {
            img = new ImageIcon(getClass().getResource("/img/img_log.png")).getImage();
            g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
            setOpaque(false);
            super.paint(g);
        }
    }
