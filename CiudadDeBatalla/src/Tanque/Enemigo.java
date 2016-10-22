package Tanque;

import java.util.LinkedList;
import java.util.Random;

import General.GameObject;
import Proyectil.Proyectil;
import Visitor.Visitante;
import Visitor.VisitanteConcreto;
import Visitor.VisitanteConcretoEnemigo;

public abstract class Enemigo extends Tanque {
	protected int recompensa;
	protected Visitante visitante=new VisitanteConcretoEnemigo();
	

	public int getRecompensa(){
		return recompensa;
	}
	
	public abstract Proyectil disparar();
	
	public int morir(){
		path="";
		path_dinamico="";
		refrescarPosicion();
		return recompensa;
	}

	public void afectar(Visitante v){
		v.visit(this);
	}
	public boolean colision(GameObject obj1, GameObject obj2){
		
		boolean b=false;
		 if(obj1.getAvanzable() && obj2.getAvanzable()){
			 obj1.afectar(new VisitanteConcretoEnemigo());
		     b=true;
		 }
	      else	{
			 	b=!(this.getRectangulo().intersects(obj1.getRectangulo()) 
			 			&& this.getRectangulo().intersects(obj2.getRectangulo())) ;
			 	
			 	if (this.getRectangulo().intersects(obj1.getRectangulo())){
			 			obj1.afectar(new VisitanteConcretoEnemigo());
			 			this.afectar(obj1.getVisitante());
			 	}
			 	
			 	if (this.getRectangulo().intersects(obj2.getRectangulo())){
			 			obj2.afectar(new VisitanteConcretoEnemigo());
			 			this.afectar(obj2.getVisitante());
			 	}
			 
			 
		 }
		
		return b;
	}
	
	public Visitante getVisitante(){
		return visitante;
	}
}
