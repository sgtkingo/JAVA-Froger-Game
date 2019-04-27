package Main;

import Interfaces.Direction;
import Interfaces.IControl;
import Interfaces.IFinish;
import Interfaces.IMovable;
import Interfaces.ISolid;
import Interfaces.IViewable;
import Items.Car;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Frog extends Direction implements IMovable{
	private Image defaultImg;
	private ImageView EntityVieW; 
	private IControl Controler;
	
	private IViewable bounds;
	
	private double roomW, roomH;
	
	private final int step=33;
	private double offSetX;
	private int direction;
	private boolean life;
	
	Frog(IControl control){	
		Controler=control;
		defaultImg=new Image("res/Frog.png");
		EntityVieW= new ImageView(defaultImg);
		
		setRoomWH();
		setPozition(roomW/2,roomH-step);
		System.out.println("Frog create! My pozition: "+getX()+"|"+getY());
		offSetX=0;
		
		makeBounds(Controler.Collision(this));
	}
	@Override
	public Node getNode() {
		return EntityVieW;
	}

	@Override
	public void update() {
		
		//Kontroluje zda zaba nespadla do vody (není v kolizi)
		EntityVieW.toFront();
		if(!isColized()) {
			life=false;
			Controler.FrogDied();
		}
		//Jinak kontroluje jestli není ve Finisi
		else Finish();
		
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
	public void moveUp() {
		direction=UP;
		if( !isOutOfRoom() ){
			EntityVieW.setTranslateY(EntityVieW.getTranslateY()-step);
		}	
		else
			System.out.println("Out of room!");
	}

	@Override
	public void moveDown() {
		direction=DOWN;
		if( !isOutOfRoom() ){
			EntityVieW.setTranslateY(EntityVieW.getTranslateY()+step);
		}	
		else
			System.out.println("Out of room!");
	}

	@Override
	public void moveLeft() {
		direction=LEFT;
		if( !isOutOfRoom() ){
			EntityVieW.setTranslateX(EntityVieW.getTranslateX()-(step));
		}
		else
			System.out.println("Out of room!");
	}

	@Override
	public void moveRight() {
		direction=RIGHT;
		if( !isOutOfRoom() ){
			EntityVieW.setTranslateX(EntityVieW.getTranslateX()+(step));
		}	
		else
			System.out.println("Out of room!");
	}

	@Override
	public void setPozition(double x, double y) {
		getNode().setTranslateX(x);
		getNode().setTranslateY(y);
	}

	@Override
	public boolean isColized() {
		
		//!!!Oprava restartu hry pro kolizi s autem, staèí vrátit false a nic víc
		if(Controler.CarCollision(this)) {
			return false;
		}
		//Tvorba pouta s objektem s kterým je v kolizi
		if(bounds!=null && makeBounds(Controler.Collision(this))!=null) {
			if(!(bounds instanceof ISolid))
			{
				//System.out.println("Frog make bounds with "+bounds.toString());
				
				//Nastavení pozice na pozici pouta
				offSetX=bounds.getX();
				setPozition(offSetX, getY());
			}
			return true;
		}
		else
		{
			offSetX=0;
			return false;
		}

	}

	@Override
	public boolean isOutOfRoom() {
		if(direction==RIGHT && getX()+step>=roomW)return true;
		if(direction==LEFT && getX()-step<0)return true;
		if(direction==UP && getY()-step<=0)return true;
		if(direction==DOWN && getY()+step>=roomH)return true;
		return false;
	}

	@Override
	public IViewable makeBounds(IViewable Object) {
		bounds=Object;	
		return Object;
	}
	@Override
	public void Finish() {
		//Zjistí je je pouto finish, pokud ano oveøí jestli není zamklý
		if(bounds instanceof IFinish) {
			if(!((IFinish) bounds).isLock())
			{
				((IFinish) bounds).iFinish();
				Controler.FrogEnds();
			}
			else
				Controler.FrogDied();
		}
	}
	@Override
	public void setRoomWH() {
		roomH=Controler.getRoomH();
		roomW=Controler.getRoomW();
	}

}
