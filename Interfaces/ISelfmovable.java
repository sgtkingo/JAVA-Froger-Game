package Interfaces;

public interface ISelfmovable extends IViewable{
	public void moveLeft();
	public void moveRight();
	
	public void setRoomWH();
	public void setPozition(double x, double y);
	
	public void setSpeed(int speed);
	public boolean isOutOfRoom();
}
