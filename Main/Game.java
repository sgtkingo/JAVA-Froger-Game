package Main;

import java.util.ArrayList;
import java.util.List;


import Interfaces.IControl;
import Interfaces.IMovable;
import Interfaces.IRoom;
import Interfaces.ISelfmovable;
import Items.Car;
import Items.Log;
import Items.Turtle;
import Rooms.BasicRoom;



import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Game extends Application {
		
	
		//Objekty pro pripravu zobrazen� score, casu atd
		Text scoreT,timeT,leftT;
		Text scoreL, timeL;
		Text Message;
		Font defaultFont=new Font(20);
		Font bigFont=new Font(32);
		
		
		//Casovac hry
		AnimationTimer timer;
		
		//Stage: Okno
		Stage PrimalStage;
		
		//Room nastavuje Scenu pro Stage (to co jde videt)
		IRoom room;
		//Root pro vsechny viditeln� objekty 
		Group root;
		
		
		//Hrac a object controler co bude slouzit jako rozhran� mezi hrou, hracem a objecty
		IMovable Frog;
		IControl Controler;
		
		//Seznam kde se ukladaj� a mazou vsechny aktu�ln� ISelfmovable objecty (klada, zelva atd..)
		List<ISelfmovable> ObjectBuffer;
		
		
		//Pomocn� promenne pro rizen� mistnosti
		private int max_floor,actionCounter,offset;
		private boolean actionTimer, turtleTimer;
		
		
		//Start() je hlavn� metoda kazde JavaFX applikace
		public void start(Stage stage) throws Exception {
			//Inicializace nejdule�it�j��ch v�c�
			Controler= new GameControler();
			ObjectBuffer= new ArrayList<ISelfmovable>();
			
			
			PrimalStage=stage;
			
			//Start hry
			startGame();
			
			
			//Ovladan� hra�e
			PrimalStage.getScene().setOnKeyPressed((KeyEvent event) -> {
				if(event.getCode()==KeyCode.W) {
					Frog.moveUp();
					
				}
				if(event.getCode()==KeyCode.S) {
					Frog.moveDown();
				}

				if(event.getCode()==KeyCode.A) {
					Frog.moveLeft();
				}

				if(event.getCode()==KeyCode.D) {
					Frog.moveRight();
				}
				});
			
			
			//Rozbrazen� sceny
			PrimalStage.show();
		}
		
		public Scene createScene() {
			return room.getScene();
		}
		
		public void startGame() {
			
			//Vy�ist�n� prom�n�ch
			System.out.println("Starting new game..");
			Controler.clear();
			ObjectBuffer.clear();
			actionTimer=true;
			actionCounter=0;
			
			
			//Vytvo�en� m�stnosti
			room=new BasicRoom(root,Controler);
			root=room.getRoot();
			
			//Offset slou�� k �telovan� pozice pater, �eknu z�tra vice
			offset=room.getOffset();
			max_floor=20;
			
			
			//Nastaven� Stage
			PrimalStage.setTitle("Frogger");
			PrimalStage.setScene(createScene());
			
			
			//P�idat ��bu a texty
			addFrog();
			prepareTexts();
			
			
			
			//!!! Inicializace �asova�e hry
	        timer = new AnimationTimer() {
	        	
	        	//Jeho vnit�n� metoda kterou je t�eba implementovat, vol� se automaticky dokola
	            @Override
	            public void handle(long now) {
	            	try {
						Thread.sleep(10); //Sleep zajist� periodu vol�n� co 10ms
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
	            	updateAll(); //Updatuje stav v�ech objetu hry
	            }
	        };
	        timer.start();
		}
		
		public void prepareTexts() {
			leftT=new Text();
			setUpText(leftT,32,room.getFloor(max_floor));
			
			scoreL=new Text();
			scoreL.setText("Score: ");
			setUpText(scoreL,64,room.getFloor(max_floor));
			scoreT=new Text();
			setUpText(scoreT,scoreL.getX()+64,room.getFloor(max_floor));
			
			timeL= new Text();
			timeL.setText("Time: ");
			setUpText(timeL,800,room.getFloor(max_floor));
			timeT=new Text();
			setUpText(timeT,timeL.getX()+64,room.getFloor(max_floor));
			
		}
		
		public void setUpText(Text text, double x,double y) {
			text.setX(x);
			text.setY(y);
			text.toFront();
			text.setFont(defaultFont);
			root.getChildren().add(text);
		}
		
		public void refreshText() {
			leftT.setText(Controler.getFrogLeft());
			scoreT.setText(Controler.getScore());
			timeT.setText(Controler.getTime());	
		}
		
		public void SceneControl() { 
			//Zde se vytva�ej� nov� objecty ISelfmovable v r�mci hry, kl�dy, ��by atd.
			
			if(Controler.timesUp()) {
				//System.out.println("Times UP! ");
				turtleTimer=!turtleTimer;
				if(actionCounter==3) {
					addMovingObject(new Car(0,room.getFloor(3),Controler,0));
					addMovingObject(new Car(1,room.getFloor(4),Controler,1));
					addMovingObject(new Car(0,room.getFloor(5),Controler,2));
				}
				
				
				addMovingObject(new Log(0,room.getFloor(7),Controler));
				
				if(turtleTimer)addMovingObject(new Turtle(1,room.getFloor(8)-offset/4,Controler));

				addMovingObject(new Log(1,room.getFloor(9)-offset/2,Controler));
				
				if(turtleTimer) addMovingObject(new Turtle(0,room.getFloor(11)-offset,Controler));
				else
				addMovingObject(new Turtle(1,room.getFloor(12)-offset,Controler));
				
				if(actionTimer) {
					if(actionCounter<=3) {
						addMovingObject(new Log(0,room.getFloor(13)-offset,Controler));
						actionCounter++;
					}
					else {
						actionTimer=!actionTimer;
						actionCounter=0;
					}
				}
				else {
					if(actionCounter<=3) {
						addMovingObject(new Log(1,room.getFloor(14)-offset,Controler));
						actionCounter++;
					}
					else {
						actionTimer=!actionTimer;
						actionCounter=0;
					}
				}
				
				if(turtleTimer)addMovingObject(new Log(0,room.getFloor(16)-offset,Controler));
				else addMovingObject(new Log(1,room.getFloor(17)-offset,Controler));
				
				if(!turtleTimer)addMovingObject(new Turtle(0,room.getFloor(18)-offset,Controler));
			}
		}

		
		public void addMovingObject(ISelfmovable object) {
			ObjectBuffer.add(object);
			root.getChildren().add(object.getNode());
		}
		
		public void deleteObject(ISelfmovable object) {
			root.getChildren().remove(object.getNode());
		}
		
		public void addFrog() {
			System.out.println("Frog add!");
			Frog=new Frog(Controler);
			Controler.refreshFrog();
			
			root.getChildren().add(Frog.getNode());
		}
		
		//Update hry
		public void updateAll() {
			Frog.update();
			
			//Zept� se jestli m� vytvo�it novou ��bu
			if(Controler.nextFrog()) {
				root.getChildren().remove(Frog.getNode());
				addFrog();
			}
			
			//Zept� se jestli m� resetovat hru
			if(Controler.Restart()) {
				try {
					System.out.println("YOU LOSE");
					Message= new Text(400,400, "YOU LOSE!!!");
					Message.setFont(bigFont);
					Message.toFront();
					root.getChildren().add(Message);
					System.out.println("Message show.");
					Thread.sleep(100);
					
					restartGame();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			
			//Zept� se jestli hr�� vyhr�l
			if(Controler.Finish()) {
				try {
					System.out.println("YOU WIN");
					Message= new Text(Controler.getRoomW()/2,Controler.getRoomH()/2, "YOU WIN!!!");
					Message.setFont(bigFont);
					Message.toFront();
					root.getChildren().add(Message);
					System.out.println("Message show.");
					Thread.sleep(100);
					
					restartGame();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			//Projede seznam v�ech ISelfmovable objektu z ObjectBuffer a ka�deho se zept� jestli je mrtv�
			for(ISelfmovable O:ObjectBuffer) {
				if(O.isDead()) {
					//Prvn� je sma�e z rootu
					deleteObject(O);
				}
			}
			
			//Zjednudu�en� z�pis horn�ho, ma�e je s�m ze sebe
			ObjectBuffer.removeIf(ISelfmovable::isDead);
			
			//Zavol� metodu update() na ka�d� zb�vaj�c�
			ObjectBuffer.forEach(ISelfmovable::update);
			
			//Aktualizije seznam �iv�ch objekt� pro Controlera
			Controler.Refresh(ObjectBuffer);
			
			//Aktualizece casu 
			Controler.Takt();
			
			//refresh m�stnosti
			refreshText();
			SceneControl();
		}
		
		public void restartGame() throws InterruptedException {
			timer.stop();
			System.out.println("Restart game..");
			Controler.writeScoreToFile();
			Thread.sleep(500);
			
			startGame();		
		}
		
		//Main vol� launch("MyApp"), ten vol� start()
		public static void main(String[] args) {		
			System.out.println("Runing FX app...");
			launch("MyApp");
		}
	}
