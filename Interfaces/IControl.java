package Interfaces;

import java.util.List;

public interface IControl {
	public void clear();
	
	public void Refresh(List<ISelfmovable> data);
	
	public void TransferSolid(List<ISolid> data);
	
	public void TransferFinish(List<IFinish> data);
	
	public IViewable Collision(IViewable object);
	public boolean CarCollision(IViewable object);
	
	public void writeScoreToFile();
	 
	public void FrogEnds();
	public void FrogDied();
	public void refreshFrog();
	
	public boolean nextFrog();
	 
	public boolean Finish();
	public boolean Restart();
	
	public double getRoomW();
	public double getRoomH();
	
	public void setRoomWH(double rW, double rH);
	
	public void Takt();
	public int getActualTime();
	public String getTime();
	public boolean timesUp();
	
	public void calcScore();
	public String getScore();
	
	public String getFrogLeft();
	 
}
