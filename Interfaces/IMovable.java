package Interfaces;


public interface IMovable extends IViewable{
		public void moveUp();
		public void moveDown();
		public void moveLeft();
		public void moveRight();
		
		public void setPozition(double x, double y);
		
		public boolean isColized();
		public boolean isOutOfRoom();
		
		public void setRoomWH();
		
		public void Finish();
		
		public IViewable makeBounds(IViewable Object);

}
