package Ejercicio16;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.Event;
import java.applet.*;
import java.util.List;

import Ejercicio15.Block;

import java.util.ArrayList;
import java.awt.Event;
public class Flappy extends Applet implements Runnable {
	public static final int DISTANCIA = 130;
	public static final int POSINICIAL = 250;

	Thread animacion;
	Image imagen;
	Graphics noseve;
	Pajaro pajaro;
	List<Columna> columnas;
	public static boolean fin = false;

	public void init() {
		imagen = createImage(300, 300);
		noseve = imagen.getGraphics();
		pajaro = new Pajaro();
		columnas = new ArrayList<Columna>();
		for (int i = 0; i < 3; i++) {
			columnas.add(new Columna(POSINICIAL + (DISTANCIA * i)));
		}
		setSize(300, 300);

	}

	public void start() {
		animacion = new Thread(this);
		animacion.start();
	}

	public void update(Graphics g) {
		paint(g);
	}

	public void run() {
		do {
			pajaro.actualizar();
			repaint();
			for (int i = 0; i < columnas.size(); i++) {
				columnas.get(i).actualizar(pajaro, animacion);
			}
			if (columnas.get(0).r1.x < 0) {
				columnas.remove(0);
				columnas.add(new Columna(370));
			}
			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
			}
		} while (true); // bucle infinito
	}

	public void paint(Graphics g) {
		noseve.setColor(Color.black);
		noseve.fillRect(0, 0, 300, 300);
		if (fin) {
			noseve.setColor(Color.white);
			noseve.drawString("Game Over", 100, 100);
			animacion.stop();
		}
		pajaro.dibujar(noseve);
		for (int i = 0; i < columnas.size(); i++) {
			columnas.get(i).dibujar(noseve);
		}

		g.drawImage(imagen, 0, 0, this);
	}

	public boolean mouseDown(Event ev, int x, int y) {
		pajaro.y -= 20;
		return true;
	}
}
