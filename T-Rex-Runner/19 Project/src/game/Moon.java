package game;

import acm.graphics.*;

/**
 * This class is for creating Moon with Runnable interface
 * 
 * @author Imron Gamidli
 *
 */
public class Moon extends GImage implements Runnable {

	/**
	 * Empty constructor with a star image referring to superclass
	 */
	public Moon() {
		super("moonSlim.png");

	}

	@Override
	public void run() {
		GPoint prevMoonLoc = new GPoint(getLocation());
		boolean moonShift = true;
		while (!Game.GameOver) {
			move(-MyConstants.MOON_STEP, 0);
			if (getX() < -getWidth()) {
				setLocation(prevMoonLoc);
				// shifting the moon form
				setImage((moonShift) ? "moonFat.png" : "moonSlim.png");
				moonShift = !moonShift;
			}
			pause(MyConstants.MOON_PAUSE);
		}

	}

}