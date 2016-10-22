package General;

import java.awt.Rectangle;

public interface Movible {

	float getX();

	void refrescarPosicion();

	float getY();

	boolean colision(GameObject obj1, GameObject obj2);

	Rectangle getRectangulo();

	float getVelocidadMov();
	
	int getDireccion();

}
