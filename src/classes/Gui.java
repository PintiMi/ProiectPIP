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
	JMenuBar menuBar;	
	JMenu fileMenu;	
	JMenu editMenu;
	JMenu colorsMenu;	
	JMenuItem browseItm;	
	JMenuItem undoItm;	
	JMenuItem clearItm;
	ButtonGroup colorsGroup;
	JRadioButton redColor;
	JRadioButton blueColor;	
	JRadioButton greenColor;
	JRadioButton yellowColor;
	JRadioButton orangeColor;
	
	Adnotare adnotare = new Adnotare();

	public Gui() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				frame = new JFrame("Aplicatie de adnotari pe poze pentru construirea setului de date ML");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				adnotare.incarcareImagine();
				creareMeniuOptiuni();
				adaugaCuloriInButtonGroup();

				browseItm.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						stergereAdnotariCurente(); 
						adnotare.incarcareImagine();
					}
				});


				undoItm.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {

						if (adnotare.listaAdnotari.size() > 0 && adnotare.textsList.size() > 0 && adnotare.secondPointList.size() > 0) {
							stergeUltimaAdnotare();
							System.out.println("Lista de adnotari: " + adnotare.listaAdnotari.size() + ", Lista de texte ale adnotarilor:" + adnotare.textsList.size() + ", Lista punctelor:"
									+ adnotare.secondPointList.size() + ", Lista culorilor: " + adnotare.listaCulori.size());
						}

					}
				});

				clearItm.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						if (adnotare.listaAdnotari.size() != 0) {
							int confirm = JOptionPane.showConfirmDialog(frame, "Stergeti toate adnotarile?");
							if (confirm == JOptionPane.YES_OPTION) {
								stergereAdnotariCurente(); 
								clearTextFile();
								System.out.println("Lista de adnotari: " + adnotare.listaAdnotari.size() + ", Lista de texte ale adnotarilor:" + adnotare.textsList.size() + ", Lista punctelor:"
										+ adnotare.secondPointList.size() + ", Lista culorilor: " + adnotare.listaCulori.size());
							}
						}
						

					}
				});
				
				adaugaListenerCulori();
				adaugaElementeInMeniu();
				
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});	
	}
	
	public void adaugaElementeInMeniu() {
		frame.setJMenuBar(menuBar);
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(colorsMenu);
		fileMenu.add(browseItm);
		editMenu.add(undoItm);
		editMenu.add(clearItm);				
		colorsMenu.add(redColor);
		colorsMenu.add(blueColor);
		colorsMenu.add(greenColor);
		colorsMenu.add(yellowColor);
		colorsMenu.add(orangeColor);
		frame.add(adnotare);
	}
	
	public void adaugaListenerCulori() {
		ColorsActionListener actionListener = new ColorsActionListener();
		redColor.addActionListener(actionListener);
		blueColor.addActionListener(actionListener);
		greenColor.addActionListener(actionListener);
		yellowColor.addActionListener(actionListener);
		orangeColor.addActionListener(actionListener);
	}
	
	public void creareMeniuOptiuni() {
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		editMenu = new JMenu("Edit");				
		browseItm = new JMenuItem("Browse...");
		undoItm = new JMenuItem("Undo");
		clearItm = new JMenuItem("Clear all");				
		colorsMenu = new JMenu("Colors");		
		colorsGroup = new ButtonGroup(); 
		redColor = new JRadioButton("Red");
		blueColor = new JRadioButton("Blue");
		greenColor = new JRadioButton("Green");
		yellowColor = new JRadioButton("Yellow");
		orangeColor = new JRadioButton("Orange");
	}
	
	public void adaugaCuloriInButtonGroup() {
		redColor.setSelected(true);
		colorsGroup.add(redColor);
		colorsGroup.add(blueColor);
		colorsGroup.add(greenColor);
		colorsGroup.add(yellowColor);
		colorsGroup.add(orangeColor);
	}
	
	public void stergereAdnotariCurente() {
		adnotare.listaAdnotari.clear();
		adnotare.textsList.clear();
		adnotare.secondPointList.clear();
		adnotare.listaCulori.clear();
	}
	
	public void stergeUltimaAdnotare() {
		int index1 = adnotare.listaAdnotari.size() - 1;
		int index2 = adnotare.textsList.size() - 1;
		int index3 = adnotare.secondPointList.size() - 1;
		int index4 = adnotare.listaCulori.size() - 1;
		adnotare.listaAdnotari.remove(index1);
		adnotare.textsList.remove(index2);
		adnotare.secondPointList.remove(index3);
		adnotare.listaCulori.remove(index4);

		
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
	
	public void clearTextFile() {
		try {
			adnotare.writer = new BufferedWriter(new FileWriter(adnotare.path));
			adnotare.writer.write("");
			adnotare.writer.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void schimbaCuloare(int r, int g, int b){
		adnotare.culori[0] = r;
		adnotare.culori[1] = g;
		adnotare.culori[2] = b;
	}
	
	class ColorsActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            JRadioButton button = (JRadioButton) event.getSource();
            if (button == redColor) {
            	schimbaCuloare(255, 0, 0);
                System.out.println ( "Selected color: red" );
            } else if (button == blueColor) {
            	schimbaCuloare(0, 0, 255);
                System.out.println ( "Selected color: blue" );
            } else if (button == greenColor) {
            	schimbaCuloare(0, 204, 0);
                System.out.println ( "Selected color: green" );
            } else if (button == yellowColor) {
            	schimbaCuloare(255, 204, 0);
                System.out.println ( "Selected color: yellow" );
            } else if (button == orangeColor) {
            	schimbaCuloare(255, 102, 0);
                System.out.println ( "Selected color: orange" );
            } 
        }
	}
} 