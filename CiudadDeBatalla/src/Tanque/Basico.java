package Tanque;

import General.GameObject;
import Proyectil.Proyectil;
import Visitor.Visitante;

public class Basico extends Enemigo {
	protected final static String ruta="Enemigo";

	public Basico(int x,int y){
		 super();
		 recompensa=100;
		 velocidadMov=1;
		 velocidadDisp=1;
		 aguante=1;
	}
	
	public Proyectil disparar() {
		return null;
	}
	
	public void afectar(Visitante v){
		v.visit(this);
		
	}

	@Override
	public float getVelocidadMov() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getAvanzable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Visitante getVisitante() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
