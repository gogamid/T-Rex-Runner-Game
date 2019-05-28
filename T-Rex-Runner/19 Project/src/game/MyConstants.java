package game;
/*
 * In this interface we are just going to add our constants, no methods!
 */

import acm.graphics.GPoint;

public interface MyConstants {
	 static final int CLOUD_STEP = 2;
	 static final int CLOUD_PAUSE = 30;
	
	 static final double STARS_STEP = 1.3;
	 static final int STARS_PAUSE = 40;
	
	 static final double MOON_STEP = 1;
	 static final int MOON_PAUSE = 40;
	 
	
	 
	 static final int OBS_PAUSE = 35;
	
	 static final GPoint REX_LOCATION = new GPoint(50, 434);
	 static final Rex REX = new Rex("rexWalk.gif");
	 static final int REX_PAUSE = 15;
	 static final int REX_BEND_Y = 454;
	
	static final Ground[] GROUNDS_ARR = new Ground[3];
	
	static final Obstacle[] OBSTACLE_ARR = new Obstacle[3];

	
}
