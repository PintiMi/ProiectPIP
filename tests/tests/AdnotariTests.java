package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Test;

import classes.Adnotare;

class AdnotariTests {
	
	Adnotare adnotare = new Adnotare();

	@Test
	public void testAdnotare() {
		assertNotNull(adnotare); // se testeaza daca obiectul este null
	}
	
	@Test
	public void testRemoveLastElement() {

		adnotare.listaAdnotari.add(adnotare.makeRectangle(10, 10, 100, 50));
		adnotare.listaCulori.add(new Color(255,0,0));
		adnotare.textsList.add(new String("adnotare1"));
		adnotare.secondPointList.add(new Point(100, 50));

		assertEquals(1, adnotare.listaAdnotari.size());
		assertEquals(1, adnotare.secondPointList.size());
		assertEquals(1, adnotare.listaCulori.size());
		assertEquals(1, adnotare.textsList.size()); // se testeaza daca s-au adaugat obiectele

		System.out.println("Lista de adnotari: " + adnotare.listaAdnotari.size() + ", Lista de texte ale adnotarilor:" + adnotare.textsList.size() + ", Lista punctelor:"
				+ adnotare.secondPointList.size() + ", Lista culorilor: " + adnotare.listaCulori.size());

		adnotare.listaAdnotari.remove(adnotare.listaAdnotari.size() - 1);
		adnotare.secondPointList.remove(adnotare.secondPointList.size() - 1);
		adnotare.listaCulori.remove(adnotare.listaCulori.size() - 1);
		adnotare.textsList.remove(adnotare.textsList.size() - 1);

		System.out.println("Lista de adnotari: " + adnotare.listaAdnotari.size() + ", Lista de texte ale adnotarilor:" + adnotare.textsList.size() + ", Lista punctelor:"
				+ adnotare.secondPointList.size() + ", Lista culorilor: " + adnotare.listaCulori.size());

		assertEquals(0, adnotare.listaAdnotari.size());
		assertEquals(0, adnotare.secondPointList.size());
		assertEquals(0, adnotare.listaCulori.size());
		assertEquals(0, adnotare.textsList.size()); // se testeaza daca s-au golit listele
	}
	
	@Test
	public void testGetPreferredSize() {
		adnotare.imagePath = "assets/traffic.jpeg";
		try {
			adnotare.inputImage = ImageIO.read(new File(adnotare.imagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Dimensiune fotografie 1.jpg: " + adnotare.inputImage.getWidth() + " x " + adnotare.inputImage.getHeight());
		System.out.println(adnotare.getPreferredSize());

		double methodWidth = adnotare.getPreferredSize().getWidth();
		double imageWidth = adnotare.inputImage.getWidth();
		double methodHeight = adnotare.getPreferredSize().getHeight();
		double imageHeight =  adnotare.inputImage.getHeight();

		// se testeaza daca metoda getPreferredSize() returneaza o dimensiune egala cu cea a imaginii
		assertEquals(methodWidth, imageWidth,1);
		assertEquals(methodHeight, imageHeight,1);

	}

	@Test
	public void testMakeRectangle() {
		Shape r1 = adnotare.makeRectangle(10, 10, 200, 100);
		Shape r2 = adnotare.makeRectangle(10, 10, 200, 100);
		assertNotNull(r1); // se testeaza daca se realizeaza forma
		assertEquals(r1, r2); // se testeaza daca cele 2 forme au aceleasi dimensiuni
		assertNotSame(r1, r2); // se testeaza daca sunt 2 forme diferite
	}

	@Test
	public void testLoadImage() {
		assertNull(adnotare.inputImage); // imagine null
		adnotare.imagePath = "assets/traffic.jpeg";
		try {
			adnotare.inputImage = ImageIO.read(new File(adnotare.imagePath)); // se testeaza daca se incarca imaginea in program
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertNotNull(adnotare.inputImage); // imagine not null
	}

}
