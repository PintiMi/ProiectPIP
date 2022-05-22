package classes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Gui {
	
	JFrame frame;

	public Gui() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {

				Adnotare adnotare = new Adnotare();

				frame = new JFrame("Aplicatie de adnotari pe poze pentru construirea setului de date ML");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				JMenuBar menuBar = new JMenuBar();

				JMenu fileMenu = new JMenu("File");
				JMenu editMenu = new JMenu("Edit");
				JMenuItem browseItm = new JMenuItem("Import");
				JMenuItem undoItm = new JMenuItem("Undo");
				JMenuItem clearItm = new JMenuItem("Clear all");

				browseItm.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						adnotare.listaAdnotari.clear();
						adnotare.incarcareImagine();
					}
				});


				undoItm.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						if (adnotare.listaAdnotari.size() > 0) {
							int index = adnotare.listaAdnotari.size() - 1;
							adnotare.listaAdnotari.remove(index);
							byte b;
							long length;
							try {
								length = adnotare.randFile.length() - 1;
								do {
									length -= 1;
									adnotare.randFile.seek(length);
									b = adnotare.randFile.readByte();
								} while (b != 10 && length > 0);
								if (length == 0) {
									adnotare.randFile.setLength(length);
								} else {
									adnotare.randFile.setLength(length + 1);
								}
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}

					}
				});

				clearItm.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if (adnotare.listaAdnotari.size() != 0) {
							int confirm = JOptionPane.showConfirmDialog(frame,
									"Stergeti tot?");
							if (confirm == JOptionPane.YES_OPTION) {
								adnotare.listaAdnotari.clear();
								try {
									adnotare.writer = new BufferedWriter(
											new FileWriter(adnotare.path));
									adnotare.writer.write("");
									adnotare.writer.close();
								} catch (IOException e1) {
									e1.printStackTrace();
								}
							}
						}
						

					}
				});	


				frame.setJMenuBar(menuBar);
				menuBar.add(fileMenu);
				fileMenu.add(browseItm);
				menuBar.add(editMenu);
				editMenu.add(undoItm);
				editMenu.add(clearItm);

				frame.add(adnotare);
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});	
	}
} 