package Aplicacion;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;

import General.GameObject;
import General.Logica;
import Proyectil.Proyectil;
import Tanque.Enemigo;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;


public class GUI implements KeyListener,Runnable  {
	
	private static Logica Juego;
	private JFrame ventana;
	private JPanel frame;
	private static int tamanio_celda=32;
	private static JLabel lblPuntos=new JLabel("Puntos : ");
	/**
	 * Launch the application.
	 */
	
			public void run() {
				
					GUI window = new GUI();
					window.ventana.setVisible(true);
						
			}
		
		
	
	/**
	 * Create the application.
	 */
	public GUI() {
		Juego=new Logica();	
		initialize();
		refreshGUI();
	}

	
	/**
	 * Initialize the contents of the frame.
	 */
	
	private void initialize() {
		
		//Creacion del Frame
		
		ventana = new JFrame();
		ventana.setForeground(Color.GRAY);
		ventana.setBounds(100,100,700,700); //560,580
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.getContentPane().setLayout(null);	
		ventana.getContentPane().setBackground(Color.DARK_GRAY);
		//Registramos Oyente Teclas
		ventana.addKeyListener(this);

		frame = new JPanel();
		frame.setLayout(null);	
		frame.setBackground(Color.BLACK);
		frame.setBounds(tamanio_celda,tamanio_celda, 19*tamanio_celda, 19*tamanio_celda);
		ventana.getContentPane().add(frame,BorderLayout.CENTER);
		lblPuntos.setHorizontalAlignment(SwingConstants.LEFT);
		
		
		lblPuntos.setForeground(Color.WHITE);
		lblPuntos.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPuntos.setBounds(546, 11, 94, 14);
		ventana.getContentPane().add(lblPuntos);
	
		
		crearMapa();
		
	}
	
	public void refreshGUI(){	
		
		Iterator<JLabel> itImpactos=Juego.getImpactos().iterator();
		 JLabel i;
		 while(itImpactos.hasNext()){
			 i=itImpactos.next();
			 frame.add(i);
		 }	 
		
		
		frame.repaint();
		frame.repaint();

		lblPuntos.setText("Puntos : "+Juego.getPuntos());
		frame.add(Juego.getJugador().getGrafico());
		
	}
	

	public void crearMapa(){
		GameObject[][] mapa=Juego.getMapa();
		
		for(int f=0;f<mapa.length;f++){
			for(int c=0;c<mapa.length;c++){	
				frame.add(mapa[f][c].getGrafico());
			}
		}
		for(Enemigo e :Juego.getEnemigos()){
			frame.add(e.getGrafico());
		}
		Thread t1= new Thread(Juego); 
		t1.start();
	}


	public void keyPressed(KeyEvent e) {
		
		switch(e.getKeyCode()){
		case (KeyEvent.VK_UP):{ 
			Juego.mover(1); 
			break;
			}
		case (KeyEvent.VK_DOWN):{
			Juego.mover(2);
			break;
			}
		case (KeyEvent.VK_RIGHT):{
			Juego.mover(3);
			break;
			}
		case (KeyEvent.VK_LEFT):{
			Juego.mover(4);
			break;
			}
		case(KeyEvent.VK_F):{
			Juego.getProyectiles().addLast(disparar());
			break;
		} 
		case(KeyEvent.VK_T):{
			Juego.terminar();
		} 
		}
		refreshGUI();
	}

	
	private Proyectil disparar() {
		Proyectil p = Juego.getJugador().disparar();
		frame.add(p.getGrafico());
		return p;
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	
}
