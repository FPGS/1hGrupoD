package Clases;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerLife extends Rectangle {
	Color color;
	
	public PlayerLife(int posX, int posY, int anchura, int altura, Color color) {
		super(posX, posY, anchura, altura);
		this.color = color;
	}

	public void dibujar(List<PlayerLife> l, Graphics g) {
		if (l.size() > 4) {
			g.setColor(color);
			g.fillRect(x, y, width, height);
		} else {
			g.setColor(Color.RED);
			g.fillRect(x, y, width, height);
		}
	}

}
