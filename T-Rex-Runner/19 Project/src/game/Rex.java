package game;

import acm.graphics.*;

/**
 * This runnable class is for creating and jumping Rex
 * 
 * @author Imron Gamidli
 * 
 */
public class Rex extends GImage implements Runnable {

	/**
	 * Constructor for setting walking Rex
	 * 
	 * @param imageName
	 * 
	 */
	public Rex(String imageName) {
		super(imageName);
	}

	@Override
	public void run() {
		setImage("rexJump.png");
		double dy = -17;

		// jumping 50pixels
		while (dy != 12.5) {
			move(0, dy);

			// gravity decreases when falling
			dy = (dy > 0) ? (dy + 0.5) : (dy + 1);
			pause(MyConstants.REX_PAUSE);
		}
		setLocation(MyConstants.REX_LOCATION);

		// after jump Rex walks again
		setImage("rexWalk.gif");
	}

}
