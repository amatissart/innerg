package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import data.Stream;


public class GraphPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Stream stream;
	Float[] ori;
	
	public void setStream(Stream s){
		stream = s;
	}

	public void paintComponent(Graphics g)  { 
		 Graphics2D g2 = (Graphics2D) g;
		 g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //Antialiasing
		 
		 super.paintComponent(g2);
		 
		 if (stream!=null && (ori=stream.getOri())!=null) {
			 System.out.println(ori[0]);
			 
			 g2.rotate(ori[0]*Math.PI/180,150,150);
			 g2.setColor(Color.DARK_GRAY);
			 g2.fill3DRect(100, 100, 100, 100, true);
		 }
		
	}
}
