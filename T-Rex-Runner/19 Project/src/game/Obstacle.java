package game;

import acm.graphics.*;

/**
 * This class for creating obstacles such as Birds and Cactuses
 * 
 * @author Imron Gamidli
 *
 */
public class Obstacle extends GImage implements Runnable {
	/**
	 * Constructor with parameters such as width and height of the window and
	 * obstacle number
	 * 
	 * @param winX
	 * @param winY
	 * @param obstN
	 */
	public Obstacle(double winX, double winY, int obstN) {
		super("cact1Small.png");
		this.winY = winY;
		this.winX = winX;
		this.obstN = obstN;
		identifyPrevObst();
	}

	/**
	 * method for identifying the ground number
	 */
	private void identifyPrevObst() {
		if (obstN == 0)
			prevObsN = 2;
		if (obstN == 1)
			prevObsN = 0;
		if (obstN == 2)
			prevObsN = 1;
	}

	@Override
	public void run() {

		while (!Game.GameOver) {
			move(-step, 0);
			checkCollision();
			if (getX() < -getWidth()) {
				addNewTypeOfObstacle();
			}
			pause(MyConstants.OBS_PAUSE);
		}

	}

	/**
	 * Method for adding new random type of obstacle at random X and if it a bird at
	 * random Y axis
	 */
	private void addNewTypeOfObstacle() {
		// random obstacle
		randomObsPic = Game.rg.nextInt(5);
		setImage(obsStr[randomObsPic]);

		/*
		 * random location regarding to X axis if it is a bird choose one of its three
		 * positions regarding to Y axis
		 **/
		setLocation(getNewX(),
				(obsStr[randomObsPic].equals("bird.gif") ? winY - 36 * Game.rg.nextInt(1, 3) : winY - getHeight() - 4));

	}

	/**
	 * Method for generation new random X but more than 300 difference between
	 * previous obstacle
	 * 
	 * @return new X position
	 */
	private double getNewX() {
		prevObsX = MyConstants.OBSTACLE_ARR[prevObsN].getX() + MyConstants.OBSTACLE_ARR[prevObsN].getWidth();
		return (winX - prevObsX > 300) ? winX : prevObsX + 300;
	}

	/**
	 * collision of an obstacle with Rex
	 */
	private void checkCollision() {
		if (getBounds().intersects(MyConstants.REX.getBounds()) && Game.GameOver == false) {
			Game.GameOver = true;
		}
	}

	private double winY;
	private double winX;
	private int obstN;
	private int prevObsN;
	private int randomObsPic;
	private double prevObsX;

	// changes according to Score
	public static double step = 10;

	private final String[] obsStr = { "bird.gif", "cact1Big.png", "cact1Small.png", "cact2.png", "cact3.png" };

}
