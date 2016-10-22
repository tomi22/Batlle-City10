package Proyectil;

import General.GameObject;
import Visitor.Visitante;
import Visitor.VisitanteConcretoProyectil;

public class DisparoJugador extends Proyectil {
	
	protected Visitante visitante=new VisitanteConcretoProyectil();

	public DisparoJugador(int dir,float x,float y,int v) {
		super(dir,x,y);
		velocidad= v;
	}

	public void afectar(Visitante v){
		v.visit(this);
		
	}

	
	public boolean colision(GameObject obj1, GameObject obj2){
		boolean b=false;
		 if(obj1.getAvanzable() && obj2.getAvanzable()){
			 obj1.afectar(new VisitanteConcretoProyectil());
		     b=true;
		 }
	      else	{
			 	b=!(r.intersects(obj1.getRectangulo()) 
			 			&& this.getRectangulo().intersects(obj2.getRectangulo())) ;
					System.out.println("Obj1:"+r.intersects(obj2.getRectangulo()));
 					System.out.println("Obj2:"+r.intersects(obj2.getRectangulo()));
			 	if(!obj1.getAvanzable()){
			 		if (r.intersects(obj1.getRectangulo())){
				 		obj1.afectar(new VisitanteConcretoProyectil());
				 		this.afectar(obj1.getVisitante());
				 		
				 	
			 		}
			 	}
		 		else{
		 			if(!obj2.getAvanzable())
		 				if (r.intersects(obj2.getRectangulo())){
		 					obj2.afectar(new VisitanteConcretoProyectil());
		 					this.afectar(obj2.getVisitante());
		 			}
		 		}
	      }
		
		return b;
	}

	@Override
	public Visitante getVisitante() {
		return visitante;
	}


	public void setDireccion(int i) {
		direccion=i;
	}

	@Override
	public float getVelocidadMov() {
		return velocidad/10;
	}
	
}
