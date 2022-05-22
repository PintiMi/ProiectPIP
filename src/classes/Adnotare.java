package classes;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

public class Adnotare extends JPanel {

	private static final long serialVersionUID = 1L;
	ArrayList<Shape> shapes = new ArrayList<Shape>();
	Point startDrag, endDrag;
	private BufferedImage image;
	File selectedFile;

	public Adnotare() {

		JFileChooser file = new JFileChooser();
		file.setCurrentDirectory(new File(System.getProperty("user.dir")));

		int result = file.showSaveDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			selectedFile = file.getSelectedFile();
			String path = selectedFile.getAbsolutePath();
			try {
				image = ImageIO.read(new File(path));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {
				startDrag = new Point(e.getX(), e.getY());
				endDrag = startDrag;
				repaint();
			}

			public void mouseReleased(MouseEvent e) {
				Shape r = makeRectangle(startDrag.x, startDrag.y, e.getX(), e.getY());
				shapes.add(r);
				startDrag = null;
				endDrag = null;
				repaint();
			}

		});

		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				endDrag = new Point(e.getX(), e.getY());
				repaint();
			}
		});
	}

	public Dimension getPreferredSize() {
		return image == null ? new Dimension(200, 200) : new Dimension(image.getWidth(), image.getHeight());
	}

	protected void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D) g.create();
		g.drawImage(image, 0, 0, this);
		g2.setStroke(new BasicStroke(3f));

		for (Shape s : shapes) {
			g2.setPaint(Color.RED);
			g2.draw(s);
		}

		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.80f));
		if (startDrag != null && endDrag != null) {
			g2.setPaint(Color.BLACK);
			Shape r = makeRectangle(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
			g2.draw(r);
			repaint();
		}
	}

	private Rectangle2D.Float makeRectangle(int x1, int y1, int x2, int y2) {
		return new Rectangle2D.Float(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
	}
}
