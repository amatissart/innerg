package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;


public class GraphPanel extends JPanel {
	
	private Float[] acc,ori;
	

	public void setAcc(Float[] values){
		acc = values;
	}
	
	public void setOri(Float[] values){
		ori = values;
	}

	public void paintComponent(Graphics g)  { 
		 Graphics2D g2 = (Graphics2D) g;
		 super.paintComponent(g2);
		 
		 if (ori!=null) {
			 g2.rotate(ori[0]*Math.PI/180);
			 g2.setColor(Color.DARK_GRAY);
			 g2.draw3DRect(100, 100, 100, 100, true);
		 }
		
	}
}
