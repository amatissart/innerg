package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;


public class MyMenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;

	public MyMenuBar(final Window f){
		super();
		JMenu menu = new JMenu("Menu");
		JButton quit = new JButton("Quitter");
		quit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            		f.exit();
            }});
		menu.add(quit);
		
		this.add(menu);
	}
}
