package Main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Interfaces.IControl;
import Interfaces.IFinish;
import Interfaces.ISelfmovable;
import Interfaces.ISolid;
import Interfaces.IViewable;
import Items.Car;

public class GameControler implements IControl {
	
	//Uložené aktuální objekty
	protected List<ISolid> Solid;
	protected List<ISelfmovable> Floating;
	protected List<IFinish> Ends;
	
	private double roomW, roomH;
	private int frogsLeft,froghEnd,score,time,actualtime;
	private boolean Finish,Restart,nextFrog;
	
	
	//Controler funguje jako prostøedník mezi hrou, hráèem a objetky
	
	//Hlídá kolize, score, cas, parametry mistnosti...a pøedává je objektùm
	public GameControler() {
		Solid=new ArrayList<ISolid>();
		Floating=new ArrayList<ISelfmovable>();
		Ends=new ArrayList<IFinish>();
		clear();
	}
	
	@Override
	public void clear() {
		frogsLeft=3;
		froghEnd=0;
		score=0;
		actualtime=0;
		time=toSeconds(90);
		
		nextFrog=Finish=Restart=false;
		
		Solid.clear();
		Floating.clear();
		Ends.clear();
	}

	@Override
	public void Refresh(List<ISelfmovable> data) {
		Floating=data;
		
	}
	
	@Override
	public void TransferSolid(List<ISolid> data) {
		Solid.addAll(data);
	}

	@Override
	public void TransferFinish(List<IFinish> data) {
		Ends=data;
	}

	//Hlídá kolize
	@Override
	public IViewable Collision(IViewable object) {
		for(ISolid Tested: Solid) {
			if(Tested.getNode().getBoundsInParent().intersects(object.getNode().getBoundsInParent())) {
				return Tested;
			}
		}
		for(ISelfmovable Tested: Floating) {
			if(Tested.getNode().getBoundsInParent().intersects(object.getNode().getBoundsInParent())) {
				return Tested;
			}
		}
		for(IFinish Tested: Ends) {
			if(Tested.getNode().getBoundsInParent().intersects(object.getNode().getBoundsInParent())) {
				return Tested;
			}
		}
	return null;
	}
	
	@Override
	public boolean CarCollision(IViewable object) {
		for(ISelfmovable Tested: Floating) {
			if((Tested instanceof Car))
			if(Tested.getNode().getBoundsInParent().intersects(object.getNode().getBoundsInParent())) {
				return true;
			}
		}
	return false;
	}

	///!!! Nauèit!
	//Metoda k ukladaní score do souboru
	@Override
	public void writeScoreToFile() {
		 System.out.println("Score saved!   ->Total score: "+score);
		 try {
	            File outFile = new File("score.txt");
	            if( !outFile.exists() )
	            	outFile.createNewFile();
	            
	            BufferedWriter Writer = new BufferedWriter(new FileWriter(outFile, true));
	            
	            Writer.write(getScore());
	            Writer.newLine();
	            
	            Writer.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}

	@Override
	public void FrogEnds() {
		System.out.println("Frog finish!!");
		calcScore();
		froghEnd++;
		if(froghEnd>=3) {
			Finish=true;
		}
		else
			refreshFrog();
	}
	
	@Override
	public void FrogDied() {
		System.out.println("Frog died..");
		frogsLeft--;
		if(frogsLeft<0) {
			Restart=true;
		}
		else
			refreshFrog();
			
	}
	
	@Override
	public boolean nextFrog() {
		return nextFrog;
	}

	@Override
	public boolean Finish() {
		return Finish;
	}

	@Override
	public boolean Restart() {
		return Restart;
	}

	@Override
	public double getRoomW() {
		return roomW;
	}

	@Override
	public double getRoomH() {
		return roomH;
	}

	@Override
	public void setRoomWH(double rW, double rH) {
		roomW=rW;
		roomH=rH;
	}

	@Override
	public void refreshFrog() {
			nextFrog=!nextFrog;
	}

	@Override
	public void Takt() {
		actualtime+=10;
		if(actualtime>=time) Restart=true;
	}

	@Override
	public int getActualTime() {
		return actualtime;
	}

	@Override
	public String getTime() {
		return Integer.toString((time-actualtime)/1000);
	}

	@Override
	public void calcScore() {
		score+=100;
	}

	@Override
	public String getScore() {
		return  Integer.toString(score);
	}

	@Override
	public String getFrogLeft() {
		return  Integer.toString(frogsLeft);
	}
	
	private int toSeconds(int seconds) {
		return seconds*1000;
	}

	@Override
	public boolean timesUp() {
		if(actualtime%300==0) return true;
		else return false;
	}



}
