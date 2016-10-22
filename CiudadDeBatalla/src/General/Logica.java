package General;

import java.text.DecimalFormat;
import java.util.*;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.RoundingMode;

import Obstaculo.Agua;
import Obstaculo.Aguila;
import Obstaculo.Cemento;
import Obstaculo.Ladrillo;
import Obstaculo.Limite;
import Obstaculo.Obstaculo;
import Obstaculo.Vacio;
import Poderes.Powerup;
import Proyectil.Proyectil;
import Tanque.Blindado;
import Tanque.Enemigo;
import Tanque.Jugador;
import Tanque.Rapido;
import Tanque.Tanque;

public class Logica implements Runnable{
	private static final int tamanio_celda = 32;
	protected Jugador miJugador;
	//LISTAS
	protected LinkedList<Enemigo> misEnemigos;
	protected LinkedList<Obstaculo> misObstaculos;
	protected LinkedList<Proyectil> misProyectiles;
	protected LinkedList<JLabel> misImpactos;

	protected GameObject[][] mapa;
	protected Powerup[] miPowerup;
	protected int puntos;
	protected volatile boolean seguir=true;

	public Logica(){
		
		misEnemigos= new LinkedList<Enemigo>();
		misObstaculos=new LinkedList<Obstaculo>();
		misProyectiles = new LinkedList<Proyectil>();
		misImpactos=new LinkedList<JLabel>();
		
		miPowerup= new Powerup[6];
		miJugador=new Jugador(7,17);
	
		//Creacion del mapa
		
		mapa=new GameObject[19][19];
		
		//Para Guardar las rutas de las imagenes 
		
	
			File a= new File("src/Aplicacion/Nivel1.txt");
		try{
			FileReader fr= new FileReader(a);
			BufferedReader br= new BufferedReader(fr);
			String s;
						
			for(int f=0;f<19;f++){
				s=br.readLine();
				for(int c=0; c<19;c++){
					char tipo=s.charAt(c);
					crearGameObject(c,f,tipo); //metodo privado
				}	
			}
			br.close();
		}
		catch(IOException e){e.printStackTrace();}
		
	}
	
	
	private void crearGameObject(int f, int c, char tipo) {
		
		switch(tipo){
		
		case 'P' :{
				mapa[f][c]=new Limite(f,c);
				break;
		}
		case 'a' :{ mapa[f][c]=new Agua(f,c); 
					misObstaculos.addLast((Obstaculo)mapa[f][c]);
					break;
		}
		case 'l' :{ mapa[f][c]=new Ladrillo(f,c);
					misObstaculos.addLast((Obstaculo)mapa[f][c]);
					break;
		}
		case 'c' :{ mapa[f][c]=new Cemento(f,c);
					misObstaculos.addLast((Obstaculo)mapa[f][c]);
					break;
		}
		case ' ' :{ mapa[f][c]=new Vacio(f,c); 
					break;
		}
		case 'e' :{ mapa[f][c]=new Vacio(f,c); 
					Enemigo e=new Blindado(f,c); 
					misEnemigos.addLast((Enemigo) e);
		break;
		}
		case 'x' :{ mapa[f][c]=new Aguila(f,c); 
		break;
		}
		case 'r' :{ mapa[f][c]=new Rapido(f,c); 
		break;
		}
		case 'j' :{ mapa[f][c]=new Vacio(f,c); 
					//miJugador= new Jugador(f,c);
		break;
		}
		}
	}


	//Consultas 
	
	public Tanque getJugador(){
		return miJugador;
	}
	
	public GameObject[][] getMapa(){
		return mapa;
	}
	
	//Comandos
	
	public void gameOver(){
		
	}
	
	public void run(){
	 while(seguir){
		 try{
			 Iterator<Proyectil> itProyectil=misProyectiles.iterator();
			 Proyectil p;
			 while(itProyectil.hasNext()){
				 p=itProyectil.next();
				 
				 for(Enemigo e:misEnemigos){
					if(cuentaInter(e, p)){
						p.afectar(e.getVisitante());
				 		e.afectar(p.getVisitante());
					}
				 }
				 
				 if(p.getVida()>0){
					 if(caminoLibre(p)){
						 p.mover(p.getDireccion());
					 }
				 }
				 else{
					 if(p.getVida()==0){//Si la vida es igual a 0 entonces debo eliminar algo 
						eliminarProyectil(p);
					 	itProyectil.remove();
					 	//ELIMINO OBSTACULOS 
					 	Iterator<Obstaculo> itObstaculo=misObstaculos.iterator();
					 	Obstaculo o;
					 	while(itObstaculo.hasNext()){
					 		o=itObstaculo.next();
					 		if(o.getVida()==0){
					 			eliminarObstaculo(o);
					 			itObstaculo.remove();
					 		}
					 	}
					 	//ELIMINO ENEMIGO 
					 	Iterator<Enemigo> itEnemigo=misEnemigos.iterator();
					 	Enemigo e;
					 	while(itEnemigo.hasNext()){
					 		e=itEnemigo.next();
					 		if(e.getVida()==0){
					 			eliminarEnemigo(e);
					 			itEnemigo.remove();
					 		}
					 	}
					 } // if getvida=0 
				}// Else
			}// fin for each de proyectil
			 
			 
			 for(Enemigo e:misEnemigos){
				 Random rnd= new Random();
				 
				
				 if(caminoLibre(e)){
					 e.mover(e.getDireccion());
				 }
				 else{
			    	 int nuevaDireccion= rnd.nextInt(4)+1;
				     while(e.getDireccion()==nuevaDireccion)
				    	 nuevaDireccion= rnd.nextInt(4)+1;
					 e.setDireccion(nuevaDireccion);
				}
				 e.refrescarPosicion();
			 }
			
			 
			 
			 
			 
	    	 Thread.sleep(75);

		
			 }
	 
	 catch(InterruptedException e){}
	}
}
	public void terminar(){
		seguir=false;
	}
	public void mover(int i) {
			if(caminoLibre(miJugador))
				miJugador.mover(i);
			else
				miJugador.setDireccion(i);
				
		

		
		
			
	}
	
	public boolean cuentaInter(Movible t,Movible t1){
		float x1=t.getX();
		float y1=t.getY();
		float x2=t1.getX();
		float y2=t1.getY();
		boolean seChocan=false;
		float resX=x1-x2;
		float resY=y1-y2;
		if(1>resX && -1<resX && 1>resY && -1<resY ){
			System.out.println("resX="+resX+" resY="+resY);
			seChocan=true;
		}
		
		return seChocan;
	}
	
	private boolean caminoLibre(Movible t) {
		int i=t.getDireccion();
		t.refrescarPosicion();
		float x=t.getX();
		float y=t.getY();
		int xI;
		int yI;
		boolean b=true;
		switch(i){
			case 1:{
				y-=0.2;
				xI=redondeoAbajo(x);
				yI=redondeoAbajo(y);
						t.refrescarPosicion();				 
				 		GameObject obj1=mapa[xI][yI];
				xI=redondeoArriba(x);
				yI=redondeoAbajo(y);
						GameObject obj2=mapa[xI][yI];
				 		b= t.colision(obj1, obj2);			
				 break;
			}
			case 2:{
				y+=0.2;
				xI=redondeoAbajo(x);
				yI=redondeoArriba(y);
			    		t.refrescarPosicion();	
				 		GameObject obj1=mapa[xI][yI];
				xI=redondeoArriba(x);
				yI=redondeoArriba(y);
						GameObject obj2=mapa[xI][yI];
				 		b= t.colision(obj1, obj2);
				 break;

			}
			case 3:{
				x+=0.2;
				xI=redondeoArriba(x);
				yI=redondeoAbajo(y);
							t.refrescarPosicion();				 
				 			GameObject obj1=mapa[xI][yI];
				xI=redondeoArriba(x);
				yI=redondeoArriba(y);
							GameObject obj2=mapa[xI][yI];
							b= t.colision(obj1, obj2);
				 
				 break;

			}
			case 4:{
				x-=0.2;
				xI=redondeoAbajo(x);
				yI=redondeoArriba(y);
						t.refrescarPosicion();				 
				 		GameObject obj1=mapa[xI][yI];
				xI=redondeoAbajo(x);
				yI=redondeoAbajo(y);
				 		GameObject obj2=mapa[xI][yI];
				 		b= t.colision(obj1, obj2) ;
				break;
			}
		}
		
		return b;
	}

	private int redondeoAbajo(float f){
		DecimalFormat df = new DecimalFormat("##");	
		df.setRoundingMode(RoundingMode.DOWN);
		String xIp;
		xIp=df.format(f); //down
		return Integer.parseInt(xIp);
		
	}
	private int redondeoArriba(float f){
		DecimalFormat dfY = new DecimalFormat("##");
		dfY.setRoundingMode(RoundingMode.CEILING);
		String yIp;
		yIp=dfY.format(f); //down 
		return Integer.parseInt(yIp);
	}
	
	public LinkedList<Enemigo> getEnemigos() {
		return  misEnemigos;
	}


	public Enemigo addEnemigo()  {
			misEnemigos.addLast(new Blindado(0,0));
			return misEnemigos.getLast();
		}
	
	public void eliminarEnemigo(Enemigo g){
		//Si es un Enemigo
		for(Enemigo e : misEnemigos){
			if(e==g){
				if(!misEnemigos.isEmpty()){
					puntos+=g.morir();
					if(puntos==1200) miJugador.subirNivel();
						g=null;		
					break;
				}
				
			}
		}
	}
	
	public void eliminarProyectil(Proyectil g){
		
		//Si es Proyectil
		for(Proyectil e : misProyectiles){
			if(e==g){
				g.morir();
				if(!misProyectiles.isEmpty()){
					JLabel grafico=new JLabel();
					grafico.setIcon(new ImageIcon(new ImageIcon("src/Aplicacion/resources/Impacto.png").getImage().getScaledInstance(tamanio_celda,tamanio_celda, Image.SCALE_SMOOTH)));
					grafico.setBounds((int)(tamanio_celda*g.getX()),(int)(tamanio_celda*g.getY()),tamanio_celda,tamanio_celda);
					misImpactos.addLast(grafico);
					g=null;	
					break;
				}
			}
		}
	}
	
	public void eliminarObstaculo(Obstaculo g){
	
		//Si es un Obstaculo
		for(Obstaculo e : misObstaculos){
			if(e==g){
				if(!misObstaculos.isEmpty()){
					g.morir();
					float x=g.getX();
					float y=g.getY();
					mapa[(int)x][(int)y]=new Vacio((int)x,(int)y);
					g=null;		
					break;
				}
			}
		}
					
	}
	
	public LinkedList<Proyectil> getProyectiles(){
		return misProyectiles;
	}
	
	public LinkedList<Obstaculo> getObstaculos() {
		return misObstaculos;
	}
	
	public int getPuntos(){
		return puntos;
	}
    public void subirNivel(){
    		miJugador.subirNivel();
    }


	public LinkedList<JLabel> getImpactos() {
		return misImpactos;
	}
}
