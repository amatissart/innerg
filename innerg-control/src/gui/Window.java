package gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import data.ProcessingInterface;
import data.Stream;
import data.StreamThread;


public class Window extends JFrame implements ActionListener, ProcessingInterface{
	private static final long serialVersionUID = 1L;
	
	private static Color background = new Color(210,227,210);
	
	private Stream stream;
	private StreamThread st;
	
	private JPanel southPanel;
	private JPanel zone;
	private GraphPanel graph;
	private JButton callButton = new JButton("Read");
	private JButton startButton;
	private JButton stopButton;
	private JPanel buttons = new JPanel();
	JLabel oriText;
	JLabel accText;
	
	
	public Window(){
		super("InnerG - bluetooth communication test") ; // Window title
		
		oriText = new JLabel(" No data ...");
		accText = new JLabel(" No data ...");
		
		setJMenuBar(new MyMenuBar(this));
		this.setLayout(new BorderLayout());
		
		southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout());
		southPanel.setOpaque(true);
		southPanel.setBackground(background);
		southPanel.setBorder(BorderFactory.createTitledBorder("Data"));
		
		buttons.setOpaque(false);
	
		zone = new JPanel();
		graph = new GraphPanel();
		graph.setPreferredSize(new Dimension(500,500));
		zone.setBorder(BorderFactory.createTitledBorder("Serial port : not connected"));
		graph.setBackground(Color.white);
		zone.add(graph);
		
		startButton = new JButton("Connect");
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
		southPanel.add(oriText,BorderLayout.WEST);
		southPanel.add(accText,BorderLayout.NORTH);
		
		this.add(zone,BorderLayout.CENTER);
		this.add(southPanel,BorderLayout.SOUTH);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ; 
	    pack() ;            
	    setVisible(true); 
	   }
	
	public void setStream(Stream s){
		stream = s;
	    graph.setStream(s);
	    zone.setBorder(BorderFactory.createTitledBorder("Serial port : "+stream.port));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==callButton){
			 try {
				if(stream!=null){
					st = new StreamThread(stream,this);
					st.start();
				}
				
			} catch (Exception e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(this,e1.getMessage());
			}
		}
		
		else if(e.getSource()==startButton){
			 setStream(new Stream());
		}
		
		else if(e.getSource()==stopButton){
			 if(st!=null)
				 st.interrupt();
		}
	}
		
	
	public void exit() {
		System.exit(0);	
	}
	
	public void setText(String s1,String s2){
		accText.setText(s1);
		oriText.setText(s2);
	}

	public GraphPanel getGraph() {
		return graph;
	}

	public void update(Float[] acc ,Float[] ori){
		
		graph.repaint();
		setText("Acceleration  x: "+ acc[0]+" y: "+acc[1]+" z: "+acc[2],
				   "Orientation   x: "+ ori[0]+" y: "+ori[1]+" z: "+ori[2]	);
	}

}
