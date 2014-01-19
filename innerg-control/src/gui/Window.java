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

import data.Stream;
import data.StreamThread;


public class Window extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	private static Color background = new Color(210,227,210);
	
	private Stream stream;
	private StreamThread st;
	
	private JPanel southPanel;
	private JPanel zone;
	private GraphPanel graph;
	private JButton callButton = new JButton("Update");
	private JButton startButton;
	private JButton stopButton;
	private JPanel buttons = new JPanel();
	
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
		graph.setPreferredSize(new Dimension(500,500));
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
	    	graph.setStream(s);
	    	zone.setBorder(BorderFactory.createTitledBorder("Serial port : "+stream.port));
	    }
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==callButton){
			 try {
				if(stream!=null){
					st = new StreamThread(stream);
					st.start();
				}
				
			} catch (Exception e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(this,e1.getMessage());
			}
		}
		
		else if(e.getSource()==startButton){
			 setStream(new Stream(graph));
		}
		
		else if(e.getSource()==stopButton){
			 if(st!=null)
				 st.interrupt();
		}
	}
		
	
	public void exit() {
		System.exit(0);	
	}

}
