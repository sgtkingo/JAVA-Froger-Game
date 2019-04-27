package Rooms;

import java.util.ArrayList;
import java.util.List;

import Interfaces.IControl;
import Interfaces.IFinish;
import Interfaces.IRoom;
import Interfaces.ISolid;
import Items.Finish;
import Items.Road;
import Items.Waterlilly;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

public class BasicRoom implements IRoom{
	
	//Vytvoøí novou místnost
	
	protected final int DEFAULT_ROOM_WEIGHT=960;
	protected final int DEFAULT_ROOM_HEIGHT=672;
	protected final int block=32;
	
	//Mezery mezi objekty
	protected int offSet=24;
	
	protected Group root;
	protected Scene workplace;
	protected IControl Controller;
	
	
	protected List<ISolid> Roads=new ArrayList<ISolid>();
	protected List<ISolid> Waterlilly=new ArrayList<ISolid>();
	protected List<IFinish> Ends=new ArrayList<IFinish>();
	
	public BasicRoom(Group r, IControl e) {
		root=r;
		Controller=e;
		
		createRoom();
	}
	
	private void render() {
		System.out.println("Rendering new room..");
		for(ISolid O: Roads) {
			root.getChildren().add(O.getNode());
		}
		for(ISolid O: Waterlilly) {
			root.getChildren().add(O.getNode());
		}
		for(IFinish O: Ends) {
			root.getChildren().add(O.getNode());
		}
		
		//Posílání dat pro Controler, at o nich ví
		Controller.TransferFinish(Ends);
		Controller.TransferSolid(Roads);
		Controller.TransferSolid(Waterlilly);
		
		Controller.setRoomWH(DEFAULT_ROOM_WEIGHT, DEFAULT_ROOM_HEIGHT);
	}
	
	
	//vrátíé vytvoøenou scenu
	@Override
	public Scene getScene() {
		return workplace;
	}
	
	
	//vytvoøí místnost
	private void createRoom() {
		createScene();
		clear();
		
		createRoads();
		createWaterlilly();
		createFinish();
		
		render();
	}
	
	private void createWaterlilly() {
			for(int i=0;i<DEFAULT_ROOM_WEIGHT/block;i++) {
				Waterlilly.add(new Waterlilly(i*block,getFloor(10)-offSet+4));
			}
			for(int i=0;i<DEFAULT_ROOM_WEIGHT/block;i++) {
				Waterlilly.add(new Waterlilly(i*block,getFloor(15)-offSet));
			}
	}
	
	private void createRoads() {
		for(int j=1;j<7;j++) {
			for(int i=0;i<DEFAULT_ROOM_WEIGHT/block;i++) {
				Roads.add(new Road(i*block,getFloor(j)));
			}
		}
	}
	
	private void createFinish() {
			for(int i=0;i<DEFAULT_ROOM_WEIGHT/block;i++) {
				Ends.add(new Finish(i*block,getFloor(20)));
			}
	}
	
	private void createScene() {
		root=new Group();
		workplace= new Scene(root,DEFAULT_ROOM_WEIGHT,DEFAULT_ROOM_HEIGHT,Color.AQUA);
	}
	
	public Group getRoot() {
		return root;
	}
	
	private void clear() {
		Roads.clear();
		Ends.clear();
	}
	
	public double getFloor(int f) {
		if(f*32<DEFAULT_ROOM_HEIGHT && f*32>0)
		return DEFAULT_ROOM_HEIGHT-((f*block));
		else
		return 0;
	}

	@Override
	public int getOffset() {
		return offSet;
	}

}
