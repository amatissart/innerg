package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import data.Stream;


public class GraphPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private static int size = 500;
	
	private Stream stream;
	Float[] ori;
	Float[] acc;
	int xCircle = size/2;
	int yCircle = 150;
	int sizeCircle = 200;
	Polygon triangle = new Polygon(new int[] {xCircle,xCircle-10,xCircle+10},
					   			   new int[] {yCircle-sizeCircle/2,yCircle +80,yCircle+80} ,3);
	
	public void setStream(Stream s){
		stream = s;
	}

	public void paintComponent(Graphics g)  { 
		 Graphics2D g2 = (Graphics2D) g.create();
		 g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //Antialiasing
		 
		 super.paintComponent(g2);
		 
		 if (stream!=null && (ori=stream.getOri())!=null && (acc=stream.getAcc())!=null) {
			 
			 g2.setColor(Color.blue);
			 
		//	 double angle = Math.atan2(acc[1], acc[2]);
		//	 g2.rotate(angle, size/2, size/2);
			 g2.rotate(ori[1]*Math.PI/180,size/2,size/2+100);
			 
			// int height = (int) (10+ 100* Math.abs(Math.sin(ori[2]*Math.PI/180)));
			 int height = 10;
			 
			// g2.rotate(ori[0]*Math.PI/180,150,150);
			 
			 g2.fillRect(150, 350-height/2, 200, height);
			 g2.dispose();
			 
			 g2 = (Graphics2D) g.create();
			 g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			 
			 g2.setColor(Color.red);
	 		 g2.drawOval(xCircle - sizeCircle/2, yCircle-sizeCircle/2, sizeCircle, sizeCircle);
	 		 g2.rotate(ori[0]*Math.PI/180, xCircle, yCircle);
	 		 g2.fillPolygon(triangle);
			 
			 
			
			 
			 
		 }
		
	}
}
