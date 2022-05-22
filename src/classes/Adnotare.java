package classes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.io.RandomAccessFile;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Adnotare extends JPanel {

	private static final long serialVersionUID = 1L;
	public ArrayList<Shape> listaAdnotari = new ArrayList<Shape>();
	public ArrayList<String> textsList = new ArrayList<String>();
	public ArrayList<Point> secondPointList = new ArrayList<Point>();
	public ArrayList<Color> listaCulori = new ArrayList<Color>();
	Point startDrag, endDrag;
	public BufferedImage inputImage;
	File selectedFile;
	public String imagePath;
	Point firstPoint = null, secondPoint = null;
	JFileChooser fileChooser;
	String textAdnotare;
	RandomAccessFile randFile = null;
	String path, imageName, info;
	Graphics2D g2d;
	BufferedWriter writer = null;
	public int culori[] = { 255, 0, 0 };

	public Adnotare() {

		addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {
				startDrag = new Point(e.getX(), e.getY());
				endDrag = startDrag;
				firstPoint = startDrag;
			}

			public void mouseReleased(MouseEvent e) {
				Shape adnotare = makeRectangle(startDrag.x, startDrag.y, e.getX(), e.getY());
				listaAdnotari.add(adnotare);
				startDrag = null;
				endDrag = null;
				
				secondPoint = new Point(e.getX(), e.getY());
				secondPointList.add(secondPoint);
				
				Color c = new Color(culori[0], culori[1], culori[2]);
				listaCulori.add(c);

				boolean loop=true;
				while (loop) {
					String input = JOptionPane.showInputDialog("Ce reprezinta adnotarea?");
					if (input == null) {
						loop = false;
						removeLastElement();
					}
					else {
						if (input.length() > 0) {

							loop = false;							
							textAdnotare = input;
							String t = new String(textAdnotare);
							textsList.add(t);
							writeToFile();
						}
					}
				}
			}

		});

		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				endDrag = new Point(e.getX(), e.getY());
				repaint();
			}
		});
	}
	
	public void removeLastElement(){
		int index = listaAdnotari.size() - 1;
		int index2 = secondPointList.size() - 1;
		int index3 = listaCulori.size() - 1;
		listaAdnotari.remove(index);
		secondPointList.remove(index2);
		listaCulori.remove(index3);
	}
	

	public Dimension getPreferredSize() {
		return inputImage == null ? new Dimension(200, 200) : new Dimension(inputImage.getWidth(), inputImage.getHeight());
	}

	public Rectangle2D.Float makeRectangle(int x1, int y1, int x2, int y2) {
		return new Rectangle2D.Float(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
	}

	public void incarcareImagine() {
		fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System
				.getProperty("user.dir")));
		int result = fileChooser.showSaveDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			selectedFile = fileChooser.getSelectedFile();
			imagePath = selectedFile.getAbsolutePath();
			try {
				inputImage = ImageIO.read(new File(imagePath));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (selectedFile == null) {
			System.exit(0);
		}
	}
	
	public void writeToFile() {
		imageName = selectedFile.getName();
		imageName = imageName.replaceFirst("[.][^.]+$", "");
		path = "logs/" + imageName + ".txt";
		info = textAdnotare + ", intre punctele : P1(x1= "
				+ firstPoint.x + ", y1= " + firstPoint.y
				+ ")" + "; P2(x2= " + secondPoint.x
				+ ", y2= " + secondPoint.y + ")";

		System.out.println(imageName + ": " + textAdnotare + ", intre punctele: P1 (x1 = " + firstPoint.getX() + ", y1 = " + firstPoint.getY()
		+ ")" + " si P2 (x2 = " + secondPoint.getX() + ", y2 = " + secondPoint.getY() + ")");
		System.out.println("-----");

		try {								
			writer = new BufferedWriter(new FileWriter(path, true));
			writer.write(info);
			writer.newLine();
			writer.close();
			randFile = new RandomAccessFile(path, "rw");
		} catch (IOException e1) {
			e1.printStackTrace();
		}	
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g2d = (Graphics2D) g.create();

		if (inputImage != null) {
			g2d.drawImage(inputImage, 0, 0, this);
		}
		
		g2d.setStroke(new BasicStroke(3f));

		for (int i = 0; i < listaAdnotari.size(); i++) {
			g2d.setPaint(listaCulori.get(i));
			g2d.draw(listaAdnotari.get(i));
			for (int j = 0; j < textsList.size(); j++) {
				if (textAdnotare != null) {
					Font font = new Font("Helvetica", Font.ITALIC | Font.BOLD, 15);
					g2d.setPaint(listaCulori.get(j));
					g2d.setFont(font);
					g2d.drawString(textsList.get(j), secondPointList.get(j).x + 5, secondPointList.get(j).y + 15);
				}
			}
		}

		if (startDrag != null && endDrag != null) {
			g2d.setPaint(Color.BLACK);
			Shape r = makeRectangle(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
			g2d.draw(r);
		}
		repaint();
	}
}
