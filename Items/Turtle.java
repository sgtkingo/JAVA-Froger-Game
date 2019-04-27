package Items;

import java.util.Random;

import Interfaces.Direction;
import Interfaces.IControl;
import Interfaces.ISelfmovable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Turtle extends Direction implements ISelfmovable{
	private IControl Controler;
	
	private Image defaultImg;
	private ImageView EntityVieW; 
	private double roomW;
	
	private double step=0.5;
	private int direction;
	private boolean life;
	
	Random rng;
	
	public Turtle(int dir, double floor, IControl control){
		life=true;
		Controler=control;
		direction=dir;
		defaultImg=new Image("res/Turtle.png");
		EntityVieW= new ImageView(defaultImg);
		rng= new Random();
		
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
		if (getRNG()!=1)
		step+=0.1;
		else
		step=0.5;
		
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
	
	private int getRNG() {
		 return rng.nextInt(12);
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
