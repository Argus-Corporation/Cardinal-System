package net.argus.example;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UnsupportedLookAndFeelException;

public class Test {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		JFrame window = new JFrame();
	    window.setSize(200, 200);
	    window.setUndecorated(true);
	    window.setBackground(new Color(0, 0, 0, 0));
	    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    // Cr�ez un nouveau panneau qui dessine la fen�tre de mani�re distordue
	    JPanel panel = new JPanel() {
	      protected void paintComponent(Graphics g) {
	        // Appelez la m�thode paintComponent() de la superclasse
	        super.paintComponent(g);

	        // Cr�ez une transformation affine qui distorde la fen�tre
	        AffineTransform transform = new AffineTransform();
	        transform.scale(1.5, 0.5);
	        transform.shear(-0.5, 0);

	        // Appliquer la transformation � l'objet Graphics2D
	        Graphics2D g2d = (Graphics2D) g;
	        g2d.setTransform(transform);
	      }
	    };
	    panel.setBackground(Color.PINK);
	    panel.add(new JButton());
	    window.add(panel);

	    window.setVisible(true);
	}
		
}
