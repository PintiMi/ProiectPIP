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
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

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
				JMenu colorsMenu = new JMenu("Colors");
				
				ButtonGroup colors = new ButtonGroup();
				JRadioButton redColor = new JRadioButton("Red");
				JRadioButton blueColor = new JRadioButton("Blue");
				JRadioButton greenColor = new JRadioButton("Green");
				JRadioButton yellowColor = new JRadioButton("Yellow");
				JRadioButton orangeColor = new JRadioButton("Orange");
				JRadioButton brownColor = new JRadioButton("Brown");
				JRadioButton purpleColor = new JRadioButton("Purple");
				JRadioButton pinkColor = new JRadioButton("Pink");
				JRadioButton cyanColor = new JRadioButton("Cyan");
				JRadioButton whiteColor = new JRadioButton("White");
				JRadioButton grayColor = new JRadioButton("Gray");
				JRadioButton blackColor = new JRadioButton("Black");

				redColor.setSelected(true);
				colors.add(redColor);
				colors.add(blueColor);
				colors.add(greenColor);
				colors.add(yellowColor);
				colors.add(orangeColor);
				colors.add(brownColor);
				colors.add(purpleColor);
				colors.add(pinkColor);
				colors.add(cyanColor);
				colors.add(whiteColor);
				colors.add(grayColor);
				colors.add(blackColor);

				browseItm.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						adnotare.listaAdnotari.clear();
						adnotare.textsList.clear();
						adnotare.secondPointList.clear();
						adnotare.listaCulori.clear();
						adnotare.incarcareImagine();
					}
				});


				undoItm.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						if (adnotare.listaAdnotari.size() > 0 && adnotare.textsList.size() > 0 && adnotare.secondPointList.size() > 0) {
							int index1 = adnotare.listaAdnotari.size() - 1;
							int index2 = adnotare.textsList.size() - 1;
							int index3 = adnotare.secondPointList.size() - 1;
							int index4 = adnotare.listaCulori.size() - 1;
							adnotare.listaAdnotari.remove(index1);
							adnotare.textsList.remove(index2);
							adnotare.secondPointList.remove(index3);
							adnotare.listaCulori.remove(index4);

							System.out.println("List of Shapes: " + adnotare.listaAdnotari.size() + ", List of Texts:" + adnotare.textsList.size() + ", List of Points:"
									+ adnotare.secondPointList.size() + ", List of Colors: " + adnotare.listaCulori.size());
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
							int confirm = JOptionPane.showConfirmDialog(frame, "Stergeti toate adnotarile?");
							if (confirm == JOptionPane.YES_OPTION) {
								adnotare.listaAdnotari.clear();
								adnotare.textsList.clear();
								adnotare.secondPointList.clear();
								adnotare.listaCulori.clear();

								System.out.println("List of Shapes: " + adnotare.listaAdnotari.size() + ", List of Texts:" + adnotare.textsList.size() + ", List of Points:"
										+ adnotare.secondPointList.size() + ", List of Colors: " + adnotare.listaCulori.size());
								try {
									adnotare.writer = new BufferedWriter(new FileWriter(adnotare.path));
									adnotare.writer.write("");
									adnotare.writer.close();
								} catch (IOException e1) {
									e1.printStackTrace();
								}
							}
						}
						

					}
				});	
				
				redColor.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						adnotare.culori[0] = 255;
						adnotare.culori[1] = 0;
						adnotare.culori[2] = 0;
					}
				});

				blueColor.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						adnotare.culori[0] = 0;
						adnotare.culori[1] = 0;
						adnotare.culori[2] = 255;
					}
				});

				greenColor.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						adnotare.culori[0] = 0;
						adnotare.culori[1] = 204;
						adnotare.culori[2] = 0;
					}
				});

				yellowColor.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						adnotare.culori[0] = 255;
						adnotare.culori[1] = 204;
						adnotare.culori[2] = 0;
					}
				});

				orangeColor.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						adnotare.culori[0] = 255;
						adnotare.culori[1] = 102;
						adnotare.culori[2] = 0;
					}
				});

				brownColor.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						adnotare.culori[0] = 153;
						adnotare.culori[1] = 76;
						adnotare.culori[2] = 0;
					}
				});

				purpleColor.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						adnotare.culori[0] = 153;
						adnotare.culori[1] = 0;
						adnotare.culori[2] = 153;
					}
				});

				pinkColor.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						adnotare.culori[0] = 255;
						adnotare.culori[1] = 51;
						adnotare.culori[2] = 255;
					}
				});

				cyanColor.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						adnotare.culori[0] = 0;
						adnotare.culori[1] = 255;
						adnotare.culori[2] = 255;
					}
				});

				whiteColor.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						adnotare.culori[0] = 255;
						adnotare.culori[1] = 255;
						adnotare.culori[2] = 255;
					}
				});

				grayColor.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						adnotare.culori[0] = 192;
						adnotare.culori[1] = 192;
						adnotare.culori[2] = 192;
					}
				});

				blackColor.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						adnotare.culori[0] = 0;
						adnotare.culori[1] = 0;
						adnotare.culori[2] = 0;
					}
				});


				frame.setJMenuBar(menuBar);
				menuBar.add(fileMenu);
				fileMenu.add(browseItm);
				menuBar.add(editMenu);
				menuBar.add(colorsMenu);
				editMenu.add(undoItm);
				editMenu.add(clearItm);
				
				colorsMenu.add(redColor);
				colorsMenu.add(blueColor);
				colorsMenu.add(greenColor);
				colorsMenu.add(yellowColor);
				colorsMenu.add(orangeColor);
				colorsMenu.add(brownColor);
				colorsMenu.add(purpleColor);
				colorsMenu.add(pinkColor);
				colorsMenu.add(cyanColor);
				colorsMenu.add(whiteColor);
				colorsMenu.add(grayColor);
				colorsMenu.add(blackColor);

				frame.add(adnotare);
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});	
	}
} 