package classes;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Interfata extends JFrame implements ActionListener {

	public Interfata() {
			
		setTitle("Proiect PIP");
		setSize(700, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setLayout(new BorderLayout());

		setContentPane(new JLabel(new ImageIcon("assets/bg-img-blue-solid.jpeg")));
		setLayout(new FlowLayout());
		JLabel label = new JLabel();
		add(label);
		setSize(1099, 1000);

		JButton buton = new JButton();
		buton.setIcon(new ImageIcon("assets/next-btn.png"));
		buton.setBounds(900, 800, 800, 700);
		add(buton);
		validate();
		
		JLabel label2 = new JLabel();
		add(label2);
		
		buton.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {

		ImageIcon icon = new ImageIcon("assets/traffic.jpeg");
		JLabel L2 = new JLabel(icon);
		add(L2);
		pack();
		setSize(1099, 1000);

	}
	
}