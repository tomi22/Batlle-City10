package General;

import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Visitor.Elemento;
import Visitor.Visitante;


public abstract class GameObject implements Elemento {
	
	//Atributos Graficos de GUI.
	protected JLabel grafico;
	protected String path;
	protected Visitante visitante;
	protected String path_dinamico;
	protected String extension=".png";
	protected final int tamanio_celda=32;
	protected Rectangle rectangulo;
	
	//Atributos Logica
	protected float x;
	protected float y;
	protected boolean avanzable;
	protected boolean destructible;
	protected int vida;

	public float getX(){
		return Math.round(x*100f)/100f;
	}
	
	public float getY(){
		
		return Math.round(y*100f)/100f;
	}
	
	public void moverX(double x){
		this.x+=x;
	}
	
	public void moverY(double d){
		this.y+=d;
	}
		
	public int morir(){
		path="";
		path_dinamico="";
		avanzable=true;
		refrescarPosicion();
		return 0;
	}
	
	public void plomo(){
		vida--;
		path_dinamico=""+vida;
		
		refrescarPosicion();
	}
	
	public int getVida(){
		return vida;
	}
	
	//Metodos Graficos
	
	public JLabel getGrafico(){
		refrescarPosicion();
		return grafico;
	}
	
	public void refrescarPosicion(){
		grafico.setIcon(new ImageIcon(new ImageIcon("src/Aplicacion/resources/"+getPath()).getImage().getScaledInstance(tamanio_celda,tamanio_celda, Image.SCALE_SMOOTH)));
		grafico.setBounds((int)(tamanio_celda*x),(int)(tamanio_celda*y),tamanio_celda,tamanio_celda);
		grafico.repaint();
	}
	
	public String getPath(){
		return path+path_dinamico+extension;
	}
	
	public void setPath(String ruta){
		path=ruta;
	}

	public abstract boolean getAvanzable();

	public abstract Rectangle getRectangulo();
	
	public abstract Visitante getVisitante();
	
	public abstract boolean colision(GameObject obj1, GameObject obj2);
		
	
}
