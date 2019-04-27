package Items;

import Interfaces.ISolid;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Waterlilly implements ISolid{
	private Image defaultImg;
	private ImageView EntityVieW; 
	
	public Waterlilly(double x, double y){
		defaultImg=new Image("res/SolidWaterLilly.png");
		EntityVieW= new ImageView(defaultImg);
		EntityVieW.toBack();
		setPozition(x,y);
	}
	
	public Node getNode() {
		return EntityVieW;
	}

	@Override
	public void update() {
		return;
	}

	@Override
	public boolean isDead() {
		return false;
	}

	@Override
	public double getX() {
		return 0;
	}

	@Override
	public double getY() {
		return 0;
	}

	@Override
	public void setPozition(double x, double y) {
		getNode().setLayoutX(x);
		getNode().setLayoutY(y);
	}
	
}
