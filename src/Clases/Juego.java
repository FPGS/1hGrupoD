package Clases;
import java.applet.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Juego extends Applet implements Runnable{
	public static final int ANCHO = 1800;
	public static final int ALTO = 1000;
	
	Image imagen;
	Graphics graphicsImagen;
	
	Image fondo;
	Image animacionMovimientoDerechaScorpion[];
	Image animacionMovimientoIzquierdaScorpion[];
	Image animacionEstaticoScorpion[];
	Image animacionPatadaScorpion[];
	Image animacionPunetazoScorpion[];
	Image animacionDanadoScorpion[];
	
	Image animacionMovimientoDerechaRyu[];
	Image animacionMovimientoIzquierdaRyu[];
	Image animacionEstaticoRyu[];
	Image animacionPatadaRyu[];
	Image animacionPunetazoRyu[];
	Image animacionDanadoRyu[];
	
	static Personaje scorpion;
	static Personaje ryu;
	Thread animacion;
	String[] countDown = {"3","2","1","FIGHT!"};
	boolean dibujarCuentaAtras = true; //Este booleano sirve para desactivar el contador y las animaciones hasta que acabe la cuenta atras
	int contadorInicio = 0;
	int indice = 0; //Nos sirve para recorrer el vector de counDown para el 3 2 1 Fight
	
	public static final int ROWS = 1;
	public static final int COLUMNS = 10;
	int time = 100;
	int contador = 0;
	static List<PlayerLife> player1;
	static List<PlayerLife> player2;
	
	public void init() {
		setSize(ANCHO, ALTO); //Redimensionamos el applet
		
		animacion = new Thread(this); //Instanciamos la animacion
		
		imagen = createImage (ANCHO, ALTO); //Creamos la manera de evitar el parpadeo con la imagen y su graphics
		graphicsImagen = imagen.getGraphics();
		fondo = getImage(getCodeBase(), "imagenes/peleafondo2.png"); //Creamos la imagen del fondo
		
		setHud(); //Este método instancia las listas player1 y 2 y las carga con objetos de la clase PlayerLife
		
		setAnimacionesScorpion(); //Este método genera los vectores con imagenes necesarios para las animaciones
		scorpion = new Personaje(100, 500, animacionMovimientoDerechaScorpion, animacionMovimientoIzquierdaScorpion, animacionEstaticoScorpion, animacionPatadaScorpion, animacionPunetazoScorpion, animacionDanadoScorpion); //Instanciamos el personaje ryu pasándole las animaciones que va a tener
		
		setAnimacionesRyu();
		ryu = new Personaje(1400, 500, animacionMovimientoDerechaRyu, animacionMovimientoIzquierdaRyu, animacionEstaticoRyu, animacionPatadaRyu, animacionPunetazoRyu, animacionDanadoRyu); //Instanciamos el personaje ryu pasándole las animaciones que va a tener
		ryu.distanciaPatada += 100; //Le sumamos a esto 100 para que al dar la patada se mueva un poco y cuadre Ryu
		
	}
	
	public void start() {
		animacion.start();
	}
	
	public void paint(Graphics g) {
		graphicsImagen.drawImage(fondo, 0, 0, ANCHO, ALTO, this);
		
		dibujarAnimaciones(); //Ponemos todas las animaciones en marcha haciendo que se llamen continuamente
		dibujarContador(); //Dibujamos el contador de 3 2 1 fight
		dibujarHud(); //Este método dibuja el HUD
		
		g.drawImage(imagen, 0, 0, this);
	}

	private void dibujarContador() {
		if(dibujarCuentaAtras){
			if(indice==3) { //Preguntamos si indice ya está en el GO para dibujarlo mas al medio
				graphicsImagen.setColor(Color.white);
				graphicsImagen.setFont(new Font("Chiller", Font.BOLD, 200));
				graphicsImagen.drawString(countDown[indice], 680, 450); // X cambia
			}else {
				graphicsImagen.setColor(Color.white);
				graphicsImagen.setFont(new Font("Castellar", Font.BOLD, 200));
				graphicsImagen.drawString(countDown[indice], 840, 450);
			}
		}
	}

	private void dibujarAnimaciones() {
		scorpion.estatico(graphicsImagen, this);
		scorpion.movimientoDerecha(graphicsImagen, this); //Las funciones se llaman desde el principio y cuando se usan es al pulsar los botones y sus atributos son puestos a 0
		scorpion.movimientoIzquierda(graphicsImagen, this);
		scorpion.patada(graphicsImagen, this, player2, ryu); //Le pasamos la lista de vida del otro personaje para quitarle vida si le damos
		scorpion.punetazo(graphicsImagen, this, player2, ryu);
		scorpion.danado(graphicsImagen, this);
		
		ryu.estatico(graphicsImagen, this);
		ryu.movimientoDerecha(graphicsImagen, this); //Las funciones se llaman desde el principio y cuando se usan es al pulsar los botones y sus boolean son puestos a true
		ryu.movimientoIzquierda(graphicsImagen, this);
		ryu.patada(graphicsImagen, this, player1, scorpion);
		ryu.punetazo(graphicsImagen, this, player1, scorpion);
		ryu.danado(graphicsImagen, this);
	}

	
	public void update(Graphics g) {
		paint(g);
	} 
	
	public void run() {
		do {
			if(contador>=1000) {
				time--;
				contador = 0;
			}
			if(contadorInicio>=1000 && dibujarCuentaAtras) {
				indice++;
				contadorInicio = 0;
				if(indice>=countDown.length)
					dibujarCuentaAtras = false;
			}
			if(dibujarCuentaAtras)
				contadorInicio += 100;
			
			repaint();
			
			try {
				Thread.sleep(100);
			}catch(InterruptedException e) {}
			if(!dibujarCuentaAtras) //Solo si el 3 2 1 ha acabado empezaremos a bajarle el tiempo al contador de 100
				contador += 100;
		}while(true);
	}
	
	public boolean keyDown(Event e, int tecla) {
		if(!dibujarCuentaAtras) { //Si el contador de 3 2 1 ha finalizado
			if(tecla==97) { //Tecla A
				scorpion.stopEstatico();
				scorpion.stopDerecha();
				scorpion.stopPatada();
				scorpion.stopPunetazo();
				scorpion.moverIzquierda();
			}
			if(tecla==100) { //Tecla D
				scorpion.stopEstatico();
				scorpion.stopIzquierda();
				scorpion.stopPunetazo();
				scorpion.stopPatada();
				scorpion.moverDerecha();
			}
			if(tecla==121) { //Tecla Y
				scorpion.stopEstatico();
				scorpion.stopIzquierda();
				scorpion.stopDerecha();
				scorpion.stopPunetazo();
				scorpion.darPatada();
				
			}
			if(tecla==116) { //Tecla T
				scorpion.stopEstatico();
				scorpion.stopIzquierda();
				scorpion.stopDerecha();
				scorpion.stopPatada();
				scorpion.darPunetazo();
			}
			if(tecla==1006) { //Flecha izquierda
				ryu.stopEstatico();
				ryu.stopDerecha();
				ryu.stopPatada();
				ryu.stopPunetazo();
				ryu.moverIzquierda();
			}
			if(tecla==1007) { //Flecha derecha
				ryu.stopEstatico();
				ryu.stopIzquierda();
				ryu.stopPunetazo();
				ryu.stopPatada();
				ryu.moverDerecha();
			}
			if(tecla==109) { //Tecla M
				ryu.stopEstatico();
				ryu.stopIzquierda();
				ryu.stopDerecha();
				ryu.stopPunetazo();
				ryu.darPatada();
				
			}
			if(tecla==110) { //Tecla N
				ryu.stopEstatico();
				ryu.stopIzquierda();
				ryu.stopDerecha();
				ryu.stopPatada();
				ryu.darPunetazo();
			}
		}
		return true;
	}

	public void setAnimacionesScorpion() {
		animacionMovimientoDerechaScorpion = new Image[6]; //Instanciamos el vector de imagenes de la animacion
		for(int i = 0; i<animacionMovimientoDerechaScorpion.length; i++)
			animacionMovimientoDerechaScorpion[i] = getImage(getCodeBase(), "imagenes/scorpion"+(i+1)+".png");
		
		animacionMovimientoIzquierdaScorpion = new Image[6]; //Instanciamos el vector de imagenes de la animacion
		for(int i = 0; i<animacionMovimientoIzquierdaScorpion.length; i++)
			animacionMovimientoIzquierdaScorpion[i] = getImage(getCodeBase(), "imagenes/scorpion"+(i+7)+".png");
		
		animacionEstaticoScorpion = new Image[1]; //Instanciamos el vector de imagees de la animacion, SI ES RYU SON 5 IMAGENES
		for(int i = 0; i<animacionEstaticoScorpion.length; i++)
			animacionEstaticoScorpion[i] = getImage(getCodeBase(), "imagenes/scorpion"+(i+1)+".png");
		
		animacionPatadaScorpion = new Image[6]; //Instanciamos el vector de imagenes de la animacion
		for(int i = 0; i<animacionPatadaScorpion.length; i++)
			animacionPatadaScorpion[i] = getImage(getCodeBase(), "imagenes/scorpion"+(i+19)+".png");

		animacionPunetazoScorpion = new Image[3]; //Instanciamos el vector de imagenes de la animacion
		for(int i = 0; i<animacionPunetazoScorpion.length; i++)
			animacionPunetazoScorpion[i] = getImage(getCodeBase(), "imagenes/scorpion"+(i+25)+".png");
		
		animacionDanadoScorpion = new Image[3]; //Instanciamos el vector de imagenes de la animacion
		for(int i = 0; i<animacionDanadoScorpion.length; i++)
			animacionDanadoScorpion[i] = getImage(getCodeBase(), "imagenes/scorpion"+(i+99)+".png");
	}
	
	public void setAnimacionesRyu() {
		animacionMovimientoDerechaRyu = new Image[6]; //Instanciamos el vector de imagenes de la animacion
		for(int i = 0; i<animacionMovimientoDerechaRyu.length; i++)
			animacionMovimientoDerechaRyu[i] = getImage(getCodeBase(), "imagenesInvertidas/ryu"+(i+7)+".gif");
		
		animacionMovimientoIzquierdaRyu = new Image[6]; //Instanciamos el vector de imagenes de la animacion
		for(int i = 0; i<animacionMovimientoIzquierdaRyu.length; i++)
			animacionMovimientoIzquierdaRyu[i] = getImage(getCodeBase(), "imagenesInvertidas/ryu"+(i+1)+".gif");
		
		animacionEstaticoRyu = new Image[5]; //Instanciamos el vector de imagenes de la animacion, SI ES RYU SON 5 IMAGENES
		for(int i = 0; i<animacionEstaticoRyu.length; i++)
			animacionEstaticoRyu[i] = getImage(getCodeBase(), "imagenesInvertidas/ryu"+(i+13)+".gif");
		
		animacionPatadaRyu = new Image[6]; //Instanciamos el vector de imagenes de la animacion
		for(int i = 0; i<animacionPatadaRyu.length; i++)
			animacionPatadaRyu[i] = getImage(getCodeBase(), "imagenesInvertidas/ryu"+(i+23)+".png");
		
		animacionPunetazoRyu = new Image[3]; //Instanciamos el vector de imagenes de la animacion
		for(int i = 0; i<animacionPunetazoRyu.length; i++)
			animacionPunetazoRyu[i] = getImage(getCodeBase(), "imagenesInvertidas/ryu"+(i+20)+".gif");
		
		animacionDanadoRyu = new Image[3]; //Instanciamos el vector de imagenes de la animacion
		for(int i = 0; i<animacionDanadoRyu.length; i++)
			animacionDanadoRyu[i] = getImage(getCodeBase(), "imagenesInvertidas/ryu"+(i+99)+".gif");
	}
	
	public void setHud() {
		player1 = new ArrayList(); //Cada una de estas listas es un vector de bloques de la barra de vida
		player2 = new ArrayList();
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				player1.add(new PlayerLife((j * 40) + 100, (i * 12) + 30, 40, 50, Color.green)); //Cada bloque de la barra de vida es un objeto de la clase PlayerLife con su posicion x e y
			}
		}
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				player2.add(new PlayerLife(1660 - (j * 40), (i * 12) + 30, 40, 50, Color.green));
			}
		}
	}
	
	public void dibujarHud() {
		for (int i = 0; i < player1.size(); i++) {
			player1.get(i).dibujar(player1, graphicsImagen); //Dibujamos cada uno de los bloques de la barra de vida
		}
		for (int i = 0; i < player2.size(); i++) {
			player2.get(i).dibujar(player2, graphicsImagen);
		}
		graphicsImagen.setColor(Color.white); //Esto crea el bordecito de la barra de vida de cada jugador
		graphicsImagen.drawRect(100, 30, 400, 50);
		graphicsImagen.drawRect(1300, 30, 400, 50);
		
		graphicsImagen.setFont(new Font("Castellar", Font.BOLD, 30)); //Esto dibuja el nombre de cada jugador
		graphicsImagen.drawString("Jugador 1", 100, 110);
		graphicsImagen.drawString("Jugador 2", 1490, 110);
		
		graphicsImagen.drawString("Tiempo", 860, 40);
		graphicsImagen.setFont(new Font("Castellar", Font.BOLD, 40)); //Esto dibuja el nombre de cada jugador
		graphicsImagen.drawString("" + time, 900, 85);
	}
	
}
