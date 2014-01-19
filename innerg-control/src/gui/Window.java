package gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import data.Stream;


public class Window extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	private static Color background = new Color(210,227,210);
	
	private Stream stream;
	
	private JPanel southPanel;
	private JPanel zone;
	private GraphPanel graph;
	private JButton callButton = new JButton("Update");
	private JButton startButton;
	private JButton stopButton;
	private JPanel buttons = new JPanel();

	private boolean stop;
	
	public Window(){
		super("InnerG - bluetooth communication test") ; // Window title
		
		JLabel prompt = new JLabel(" >  ");
		
		setJMenuBar(new MyMenuBar(this));
		this.setLayout(new BorderLayout());
		
		southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout());
		southPanel.setOpaque(true);
		southPanel.setBackground(background);
		southPanel.setBorder(BorderFactory.createTitledBorder("Télécommande"));
		
		buttons.setOpaque(false);
	
		zone = new JPanel();
		graph = new GraphPanel();
		graph.setPreferredSize(new Dimension(300,300));
		zone.setBorder(BorderFactory.createTitledBorder("Serial port : not connected"));
		graph.setBackground(Color.white);
		zone.add(graph);
		
		startButton = new JButton("Start");
		buttons.add(startButton);
		
		callButton.addActionListener(this);
		callButton.setBackground(new Color(70,110,110));
		callButton.setForeground(Color.white);
		
		stopButton = new JButton("Stop");
		stopButton.addActionListener(this);
		buttons.add(stopButton);
	
		startButton.addActionListener(this);
		
		southPanel.add(callButton,BorderLayout.EAST);
		southPanel.add(buttons,BorderLayout.SOUTH);
		southPanel.add(prompt,BorderLayout.WEST);
		
		this.add(zone,BorderLayout.CENTER);
		this.add(southPanel,BorderLayout.SOUTH);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ; 
	    pack() ;            
	    setVisible(true); 
	   }
	
	public void setStream(Stream s){
		String result = s.start();
	    
	    if(!result.isEmpty())
	    	JOptionPane.showMessageDialog(this,result);
	    else {
	    	stream = s;
	    	zone.setBorder(BorderFactory.createTitledBorder("Serial port : "+stream.port));
	    }
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==callButton){
			 try {
					stream.newLine();
				
			} catch (Exception e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(this,e1.getMessage());
			}
		}
		
		else if(e.getSource()==startButton){
			 setStream(new Stream(this));
		}
		
		else if(e.getSource()==stopButton){
			 stop = true;
		}
	}
		
	
	public void exit() {
		System.exit(0);	
	}


	public void newData(Float[] values) {
		graph.setAcc (Arrays.copyOfRange(values, 0, 2));
		graph.setOri (Arrays.copyOfRange(values, 3, 5));
		graph.repaint();
		
	}
}
