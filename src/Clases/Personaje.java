package Clases;
import java.awt.*;
import java.applet.*;
import java.util.ArrayList;
import java.util.List;

public class Personaje extends Rectangle{
	Image imagenesMovimientoDerecha[]; //Vector de imagenes de la animacion de movimiento hacia la derecha
	Image imagenesMovimientoIzquierda[];
	Image imagenesEstatico[];
	Image imagenesPatada[];
	Image imagenesPunetazo[];
	Image imagenesDanado[];
	
	int actualMovDerecha = 0;
	int actualMovIzquierda = 0;
	int actualEstatico = 0;
	int actualPatada = 0;
	int actualPunetazo = 0;
	int actualDanado = 0;
	
	boolean puedeEstatico = true;
	boolean puedeDerecha = true;
	boolean puedeIzquierda = true;
	boolean puedePatada = true;
	boolean puedePunetazo = true;
	boolean puedeDanado = false;
	
	int distanciaPatada = 0;
	
	public Personaje(int x, int y, Image movDerecha[], Image movIzquierda[], Image estatico[], Image pat[], Image pun[], Image dan[]) {
		super(x, y, 250, 420);
		imagenesMovimientoDerecha = movDerecha;
		imagenesMovimientoIzquierda = movIzquierda;
		imagenesEstatico = estatico;
		imagenesPatada = pat;
		imagenesPunetazo = pun;
		imagenesDanado = dan;
	}
	
	public void estatico(Graphics g, Applet a) {
		if(puedeEstatico) { //Pregunta si se puede hacer la animacion
			if(actualEstatico<imagenesEstatico.length) { //Aquí se comprueba que actual no es la ultima 
				g.drawImage(imagenesEstatico[actualEstatico], x, y, width, height, a); //Se pinta la imagen de actual y se suma uno, cuando actual llega a la ultima imagen se restablece a la primera
				actualEstatico++;
			}else {
				actualEstatico = 0;
				g.drawImage(imagenesEstatico[actualEstatico], x, y, width, height, a); //Se pinta la imagen de actual y se suma uno, cuando actual llega a la ultima imagen se restablece a la primera			
				actualEstatico++;
			}
		}
	}
	
	public void movimientoDerecha(Graphics g, Applet a) {
		if(puedeDerecha) { 
			if(actualMovDerecha<imagenesMovimientoDerecha.length) { //Aquí se comprueba que actual no es la ultima 
				g.drawImage(imagenesMovimientoDerecha[actualMovDerecha], x, y, width, height, a); //Se pinta la imagen de actual y se suma uno, cuando actual llega a la ultima imagen se restablece a la primera
				actualMovDerecha++;
				x += 30;
				if(actualMovDerecha>=imagenesMovimientoDerecha.length) { //Si la animación ha acabado
					puedeEstatico = true; //Volvemos a realizar la animacion estatico
					puedeDerecha = false;
					actualMovDerecha = 0;
				}
			}
			limitacionMovimiento(); //Impedimos que se salga de la ventana
		}
	}
	
	public void movimientoIzquierda(Graphics g, Applet a) {
		if(puedeIzquierda) {
			if(actualMovIzquierda<imagenesMovimientoIzquierda.length) { //Aquí se comprueba que actual no es la ultima 
				g.drawImage(imagenesMovimientoIzquierda[actualMovIzquierda], x, y, width, height, a); //Se pinta la imagen de actual y se suma uno, cuando actual llega a la ultima imagen se restablece a la primera
				actualMovIzquierda++;
				x -= 30;
				if(actualMovIzquierda>=imagenesMovimientoIzquierda.length) {
					puedeEstatico = true;
					puedeIzquierda = false;
					actualMovIzquierda = 0;
				}
			}
			limitacionMovimiento();
		}
	}
	
	
	public void patada(Graphics g, Applet a, List<PlayerLife> l, Personaje p) { //Recibe la lista del otro para quitarle vida
		if(puedePatada) {
			if(actualPatada<imagenesPatada.length) { //Aquí se comprueba que actual no es la ultima 
				if(actualPatada<=2) {
					g.drawImage(imagenesPatada[actualPatada], x, y, width, height, a); //Se pinta la imagen de actual y se suma uno, cuando actual llega a la ultima imagen se restablece a la primera
					actualPatada++;
				}else { //Si la animacion de la patada está a medias la dibujamos mas grande para que cuadre
					g.drawImage(imagenesPatada[actualPatada], x-distanciaPatada, y, width+100, height, a); //Se pinta la imagen de actual y se suma uno, cuando actual llega a la ultima imagen se restablece a la primera
					actualPatada++;
					
					if(actualPatada==imagenesPatada.length-1) { //Preguntamos si la animación está en su ultimo frame
						if(Juego.scorpion.intersects(Juego.ryu)) { 
							l.remove(l.size()-1);
							l.remove(l.size()-1);
							p.puedeDanado = true;
						}
					}
				}
				if(actualPatada>=imagenesPatada.length) {
					puedeEstatico = true;
					puedePatada = false;
					actualPatada = 0;
					//Juego.player2.remove(Juego.player2.size()-1);
				}
	
			}
		}
	}
	
	public void punetazo(Graphics g, Applet a, List<PlayerLife> l, Personaje p) {
		if(puedePunetazo) {
			if(actualPunetazo<imagenesPunetazo.length) { //Aquí se comprueba que actual no es la ultima 
				if(actualPunetazo<=0) {
					g.drawImage(imagenesPunetazo[actualPunetazo], x, y, width, height, a); //Se pinta la imagen de actual y se suma uno, cuando actual llega a la ultima imagen se restablece a la primera
					actualPunetazo++;
				}else { //Si la animacion de la patada está a medias la dibujamos mas grande para que cuadre
					g.drawImage(imagenesPunetazo[actualPunetazo], x-distanciaPatada, y, width+100, height, a); //Se pinta la imagen de actual y se suma uno, cuando actual llega a la ultima imagen se restablece a la primera
					actualPunetazo++;
					
					if(actualPunetazo==imagenesPunetazo.length) { //Preguntamos si la animación está en su ultimo frame
						if(Juego.scorpion.intersects(Juego.ryu)) { 
							l.remove(l.size()-1);
							p.puedeDanado = true;
						}
					}
				}
				if(actualPunetazo>=imagenesPunetazo.length) {
					puedeEstatico = true;
					puedePunetazo = false;
					actualPunetazo = 0;
					//Juego.player2.remove(Juego.player2.size()-1);
				}
				
			}
		}
	}
	
	public void danado(Graphics g, Applet a) { //Recibe la lista del otro para quitarle vida
		if(puedeDanado) {
			stopEstatico();
			stopDerecha();
			stopIzquierda();
			stopPunetazo();
			stopPatada();
			if(actualDanado<imagenesDanado.length) { //Aquí se comprueba que actual no es la ultima 
				g.drawImage(imagenesDanado[actualDanado], x, y, width, height, a); //Se pinta la imagen de actual y se suma uno, cuando actual llega a la ultima imagen se restablece a la primera
				actualDanado++;
			
				if(actualDanado>=imagenesDanado.length) {
					puedeEstatico = true;
					puedeDanado = false;
					actualDanado = 0;
				}
	
			}
		}
	}
	
	public void stopEstatico() { //Esto lo que hace es parar la animación de estatico
		puedeEstatico = false;
	}
	
	public void moverDerecha() { //Esto ejecuta la funcion de mover a la derecha poniendo su actual a 0 permitiendo que funcione
		if(!puedeDanado)
		puedeDerecha = true;
	}
	
	public void stopDerecha() {
		puedeDerecha = false; //Se pone un número alto para evitar que la animación se realice
	}
	
	public void moverIzquierda() {
		if(!puedeDanado)
		puedeIzquierda = true;
	}
	
	public void stopIzquierda() {
		puedeIzquierda = false;
	}
	
	public void darPatada() {
		if(!puedeDanado)
		puedePatada = true;
	}
	
	public void stopPatada() {
		puedePatada = false;
	}	
	
	public void darPunetazo() {
		if(!puedeDanado)
		puedePunetazo = true;
	}
	
	public void stopPunetazo() {
		puedePunetazo = false;
	}	
	
	public void limitacionMovimiento() {
		if(x <= -15) {
			x = 0;
		}
		if(x >= 1580) {
			x = 1580;
		}
	}

}
