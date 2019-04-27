package Interfaces;

import javafx.scene.Node;

public interface IViewable {
		public Node getNode();

		public void update();
		public boolean isDead();
		
		public double getX();
		public double getY();
}
