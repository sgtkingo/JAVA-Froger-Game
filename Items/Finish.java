package Items;

import Interfaces.IFinish;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Finish implements IFinish{

	private Image defaultImg, lockImg;
	private ImageView EntityVieW; 
	
	boolean lock;
	
	public Finish(double x, double y){
		defaultImg=new Image("res/waterlilly.png");
		lockImg=new Image("res/Heart.png");
		EntityVieW= new ImageView(defaultImg);
		EntityVieW.toBack();
		
		lock=false;
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
		return (getNode().getTranslateX()+getNode().getLayoutX());
	}

	@Override
	public double getY() {
		return (getNode().getTranslateY()+getNode().getLayoutY());
	}

	@Override
	public void setPozition(double x, double y) {
		getNode().setLayoutX(x);
		getNode().setLayoutY(y);
	}

	@Override
	public void iFinish() {
		EntityVieW.setImage(lockImg);
		lock=true;
	}

	@Override
	public boolean isLock() {
		return lock;
	}

}
