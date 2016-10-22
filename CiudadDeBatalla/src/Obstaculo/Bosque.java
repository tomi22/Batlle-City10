package Obstaculo;

import java.awt.Rectangle;

import javax.swing.JLabel;

import Visitor.Visitante;


public class Bosque extends Obstaculo {
	private static final String ruta="Bosque";
	
	public Bosque(int x,int y){
		this.x=x;
		this.y=y;
		path=ruta;
		path_dinamico="";
		avanzable=false;
		destructible=true;
		grafico=new JLabel();
		refrescarPosicion();
	
	}

	@Override
	public boolean getAvanzable() {
		return avanzable;
	}
	public  Rectangle getRectangulo(){
		return rectangulo;
	}
	
	public void afectar(Visitante v){
		v.visit(this);
		
	}
}
