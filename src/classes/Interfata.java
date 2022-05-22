package classes;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Interfata extends JFrame {

	public Interfata() {
			
		setTitle("Proiect PIP");
		setSize(700, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setLayout(new BorderLayout());

		setContentPane(new JLabel(new ImageIcon("/ProiectPIP/assets/bg-img-blue-solid.jpeg")));
		setLayout(new FlowLayout());
		JLabel label = new JLabel();
		add(label);
		setSize(1099, 1000);

		JButton buton = new JButton();
		buton.setIcon(new ImageIcon("/ProiectPIP/assets/next-btn.png"));
		buton.setBounds(800, 700, 900, 800);
		add(buton);
		validate();
	}
}