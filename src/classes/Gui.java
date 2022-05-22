package classes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Gui {
	
	JFrame frame;

	public Gui() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				frame = new JFrame("Proiect PIP");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				Adnotare d = new Adnotare();

				JMenuBar menuBar = new JMenuBar();
				frame.setJMenuBar(menuBar);
				JButton browseBtn = new JButton("Import");
				browseBtn.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						if(e.getSource()==browseBtn){
							d.shapes.clear();
							d.file = new JFileChooser();
							d.file.setCurrentDirectory(new File(System.getProperty("user.dir")));
							int result = d.file.showSaveDialog(null);
							if (result == JFileChooser.APPROVE_OPTION) {
								d.selectedFile = d.file.getSelectedFile();
								d.path = d.selectedFile.getAbsolutePath();
								try {
									d.image = ImageIO.read(new File(d.path));
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}
					}
				});
				menuBar.add(browseBtn);

				JButton undoBtn = new JButton("Undo");
				undoBtn.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						if(d.shapes.size()>0){
							int index = d.shapes.size() - 1;
							d.shapes.remove(index);
						}

					}
				});
				menuBar.add(undoBtn);

				JButton clearBtn = new JButton("Clear all");
				clearBtn.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						if(d.shapes.size() != 0){
							int confirm = JOptionPane.showConfirmDialog(frame, "Stergeti toate adnotarile?");
							if(confirm == JOptionPane.YES_OPTION){
								d.shapes.clear();
							}
						}

					}
				});
				menuBar.add(clearBtn);		

				frame.add(d);
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}
}
