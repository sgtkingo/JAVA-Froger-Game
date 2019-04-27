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
		
	
		//Objekty pro pripravu zobrazení score, casu atd
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
		//Root pro vsechny viditelné objekty 
		Group root;
		
		
		//Hrac a object controler co bude slouzit jako rozhraní mezi hrou, hracem a objecty
		IMovable Frog;
		IControl Controler;
		
		//Seznam kde se ukladají a mazou vsechny aktuální ISelfmovable objecty (klada, zelva atd..)
		List<ISelfmovable> ObjectBuffer;
		
		
		//Pomocné promenne pro rizení mistnosti
		private int max_floor,actionCounter,offset;
		private boolean actionTimer, turtleTimer;
		
		
		//Start() je hlavní metoda kazde JavaFX applikace
		public void start(Stage stage) throws Exception {
			//Inicializace nejduležitìjších vìcí
			Controler= new GameControler();
			ObjectBuffer= new ArrayList<ISelfmovable>();
			
			
			PrimalStage=stage;
			
			//Start hry
			startGame();
			
			
			//Ovladaní hraèe
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
			
			
			//Rozbrazení sceny
			PrimalStage.show();
		}
		
		public Scene createScene() {
			return room.getScene();
		}
		
		public void startGame() {
			
			//Vyèistìní promìných
			System.out.println("Starting new game..");
			Controler.clear();
			ObjectBuffer.clear();
			actionTimer=true;
			actionCounter=0;
			
			
			//Vytvoøení místnosti
			room=new BasicRoom(root,Controler);
			root=room.getRoot();
			
			//Offset slouží k štelovaní pozice pater, øeknu zítra vice
			offset=room.getOffset();
			max_floor=20;
			
			
			//Nastavení Stage
			PrimalStage.setTitle("Frogger");
			PrimalStage.setScene(createScene());
			
			
			//Pøidat žábu a texty
			addFrog();
			prepareTexts();
			
			
			
			//!!! Inicializace èasovaèe hry
	        timer = new AnimationTimer() {
	        	
	        	//Jeho vnitøní metoda kterou je tøeba implementovat, volá se automaticky dokola
	            @Override
	            public void handle(long now) {
	            	try {
						Thread.sleep(10); //Sleep zajistí periodu volání co 10ms
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
	            	updateAll(); //Updatuje stav všech objetu hry
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
			//Zde se vytvaøejí nové objecty ISelfmovable v rámci hry, klády, žáby atd.
			
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
			
			//Zeptá se jestli má vytvoøit novou žábu
			if(Controler.nextFrog()) {
				root.getChildren().remove(Frog.getNode());
				addFrog();
			}
			
			//Zeptá se jestli má resetovat hru
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
			
			
			//Zeptá se jestli hráè vyhrál
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
			
			//Projede seznam všech ISelfmovable objektu z ObjectBuffer a každeho se zeptá jestli je mrtvý
			for(ISelfmovable O:ObjectBuffer) {
				if(O.isDead()) {
					//První je smaže z rootu
					deleteObject(O);
				}
			}
			
			//Zjednudušený zápis horního, maže je sám ze sebe
			ObjectBuffer.removeIf(ISelfmovable::isDead);
			
			//Zavolá metodu update() na každý zbývající
			ObjectBuffer.forEach(ISelfmovable::update);
			
			//Aktualizije seznam živých objektù pro Controlera
			Controler.Refresh(ObjectBuffer);
			
			//Aktualizece casu 
			Controler.Takt();
			
			//refresh místnosti
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
		
		//Main volá launch("MyApp"), ten volá start()
		public static void main(String[] args) {		
			System.out.println("Runing FX app...");
			launch("MyApp");
		}
	}
