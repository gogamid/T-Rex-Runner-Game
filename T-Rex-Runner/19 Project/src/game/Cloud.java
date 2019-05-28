package game;

import acm.graphics.*;

/**
 * This class is for creating Clouds with Runnable interface
 * 
 * @author Imron Gamidli
 *
 */
public class Cloud extends GImage implements Runnable {

	/**
	 * Empty constructor with a cloud image referring to superclass
	 */
	public Cloud() {
		super("cloud.png");
	}

	@Override
	public void run() {
		GPoint cloudPrevLoc = new GPoint(getLocation());
		while (!Game.GameOver) {
			move(-MyConstants.CLOUD_STEP, 0);
			if (getX() < -getWidth())
				setLocation(cloudPrevLoc);
			pause(MyConstants.CLOUD_PAUSE);
		}

	}

}
