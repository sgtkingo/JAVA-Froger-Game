package Interfaces;

import javafx.scene.Group;
import javafx.scene.Scene;

public interface IRoom {
	public Scene getScene();
	public Group getRoot() ;	
	
	public double getFloor(int f);
	public int getOffset();
}
