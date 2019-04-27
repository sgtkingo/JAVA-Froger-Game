package Items;

import Interfaces.Direction;
import Interfaces.IControl;
import Interfaces.ISelfmovable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Car extends Direction implements ISelfmovable{
	private IControl Controler;
	
	private Image defaultImg;
	private ImageView EntityVieW; 
	private double roomW;
	
	private double step=1.5;
	private int direction;
	private boolean life;
	
	public Car(int dir, double floor, IControl control, int type){
		life=true;
		Controler=control;
		direction=dir;
		defaultImg=new Image("res/car2.png");
		if(type==0)defaultImg=new Image("res/car2.png");
		if(type==1)defaultImg=new Image("res/car3.png");
		if(type==2)defaultImg=new Image("res/car4.png");

		EntityVieW= new ImageView(defaultImg);
		
		setRoomWH();
		if(direction==LEFT)
		setPozition(roomW-defaultImg.getWidth(),floor);
		if(direction==RIGHT)
		setPozition(0,floor);
	}
	
	@Override
	public Node getNode() {
		return EntityVieW;
	}

	@Override
	public void update() {
		switch(direction) {
		case LEFT: {
			moveLeft();
			break;
		}
		case RIGHT: {
			moveRight();
			break;
		}
		default: return;
		}
	}

	@Override
	public boolean isDead() {
		return !life;
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
	public void moveLeft() {
		direction=LEFT;
		if( !isOutOfRoom() ){
			EntityVieW.setTranslateX(EntityVieW.getTranslateX()-step);
		}
		else
			life=false;
	}

	@Override
	public void moveRight() {
		direction=RIGHT;
		if( !isOutOfRoom() ){
			EntityVieW.setTranslateX(EntityVieW.getTranslateX()+step);
		}
		else
			life=false;
	}

	@Override
	public void setRoomWH() {
		roomW=Controler.getRoomW();
	}

	@Override
	public void setSpeed(int speed) {
		step=speed;
	}

	@Override
	public boolean isOutOfRoom() {
		if(direction==RIGHT && getX()+step>=roomW)return true;
		if(direction==LEFT && getX()-step<0)return true;
		return false;
	}

	@Override
	public void setPozition(double x, double y) {
		getNode().setLayoutX(x);
		getNode().setLayoutY(y);
	}

}
