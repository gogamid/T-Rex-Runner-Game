package game;

import acm.graphics.*;

/**
 * This class is for creating Ground which extends GImage
 * 
 * @author Imron Gamidli
 *
 */
public class Ground extends GImage implements Runnable {

	/**
	 * Constructor with parameters of image string and ground number
	 * 
	 * @param name
	 * @param groundN
	 */
	public Ground(String name, int groundN) {
		super(name);
		this.groundN = groundN;
		identifyPrevGround();
	}

	/**
	 * method for identifying the ground number
	 */
	private void identifyPrevGround() {
		if (groundN == 0)
			prevGroundN = 2;
		if (groundN == 1)
			prevGroundN = 0;
		if (groundN == 2)
			prevGroundN = 1;
	}

	@Override
	public void run() {

		// move and complete each other
		while (!Game.GameOver) {
			move(-Obstacle.step, 0);
			if (getX() < -getWidth()) {
				prevGroundX = MyConstants.GROUNDS_ARR[prevGroundN].getX() + MyConstants.GROUNDS_ARR[prevGroundN].getWidth() - Obstacle.step;
				prevGroundY = MyConstants.GROUNDS_ARR[prevGroundN].getY();
				setLocation(prevGroundX, prevGroundY);
			}
			pause(MyConstants.OBS_PAUSE);
			
		}

	}
	private int groundN;
	private int prevGroundN;
	private double prevGroundX;
	private double prevGroundY;
}
