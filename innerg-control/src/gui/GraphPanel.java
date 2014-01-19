package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import data.Stream;


public class GraphPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private static int size = 500;
	
	private Stream stream;
	Float[] ori;
	Float[] acc;
	
	public void setStream(Stream s){
		stream = s;
	}

	public void paintComponent(Graphics g)  { 
		 Graphics2D g2 = (Graphics2D) g;
		 g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //Antialiasing
		 
		 super.paintComponent(g2);
		 
		 if (stream!=null && (ori=stream.getOri())!=null && (acc=stream.getAcc())!=null) {
			 System.out.println(ori[0]);
			 
			 g2.setColor(Color.blue);
		//	 g2.draw
		//	 g2.drawLine(150, 150, (int)(150+10*acc[1]), (int)(150+10*acc[2]));
			 
			 double angle = Math.atan2(acc[1], acc[2]);
			 g2.rotate(angle, size/2, size/2);
			// g2.rotate(ori[0]*Math.PI/180,150,150);
			 g2.fillRect(150, 245, 200, 10);
			
			 
			 
		 }
		
	}
}
