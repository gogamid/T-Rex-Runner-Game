package game;

import acm.graphics.*;

/**
 * This class is for creating Stars with Runnable interface
 * 
 * @author Imron Gamidli
 *
 */
public class Stars extends GImage implements Runnable {
	/**
	 * Empty constructor with a star image referring to superclass
	 */
	public Stars() {
		super("stars.png");
	}

	@Override
	public void run() {
		GPoint prevStarLoc = new GPoint(getLocation());
		while (!Game.GameOver) {
			move(-MyConstants.STARS_STEP, 0);
			if (getX() < -getWidth())
				setLocation(prevStarLoc);
			pause(MyConstants.STARS_PAUSE);
		}

	}

}