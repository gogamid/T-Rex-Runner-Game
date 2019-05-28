 package game;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import acm.graphics.*;
import acm.program.*;
import acm.util.RandomGenerator;

public class Game extends GraphicsProgram {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() {
		setBackground(Color.CYAN);
		addKeyListeners();
		welcome();
	}

	@Override
	public void run() {
		addRex();
		addClouds();
		addGround();
		addMoon();
		addStars();
		addObstacles();
		addCounter();
	}

	/**
	 * opening scene
	 */
	private void welcome() {
		GLabel l = new GLabel("T-Rex Runner");
		l.setFont("Times-40");
		add(l, (getWidth() - l.getWidth()) / 2, (getHeight() - l.getAscent()) / 2);

		GLabel l2 = new GLabel("Imron Gamidli");
		l2.setFont("Times-40");
		add(l2, (getWidth() - l.getWidth()) / 2, l2.getAscent() + (getHeight() - l.getAscent()) / 2);

		GLabel l1 = new GLabel("Instructions: SHIFT-jumping DOWN-bending CLICK to begin");
		l1.setFont("Times-25");
		add(l1, 0, getHeight() - 2 * l1.getHeight());

		readHighScore();

		waitForClick();
		removeAll();
		setBackground(white);

	}

	/**
	 * read high score from a file
	 */
	private void readHighScore() {
		try {
			br = new BufferedReader(new FileReader("highScore.txt"));
			highScoreString = br.readLine();
			highScore = Integer.parseInt(highScoreString);

		} catch (FileNotFoundException e) {
			System.err.println("The file you specified does not exist.");
		} catch (IOException e) {
			System.err.println("Some other IO exception occured. Message: " + e.getMessage());
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * rex added to the screen
	 */
	private void addRex() {
		add(MyConstants.REX, MyConstants.REX_LOCATION);
	}

	/**
	 * clouds added their threads started
	 */
	private void addClouds() {
		clouds = new Cloud[3];
		int dx = getWidth();
		for (int i = 0; i < clouds.length; i++) {
			clouds[i] = new Cloud();
			add(clouds[i], dx, 250);
			cloudThread = new Thread(clouds[i]);
			cloudThread.start();
			dx += getWidth() / 3;
		}

	}

	/**
	 * grounds added their threads started
	 */
	private void addGround() {
		int dx = 0;
		for (int i = 0; i < MyConstants.GROUNDS_ARR.length; i++) {
			MyConstants.GROUNDS_ARR[i] = new Ground("ground.png", i);
			add(MyConstants.GROUNDS_ARR[i], dx, getHeight() - MyConstants.GROUNDS_ARR[i].getHeight());
			groundThread = new Thread(MyConstants.GROUNDS_ARR[i]);
			groundThread.start();
			dx += MyConstants.GROUNDS_ARR[i].getWidth();
		}

	}

	/**
	 * adds invisible moon
	 */
	private void addMoon() {
		moon = new Moon();
		add(moon, getWidth() - moon.getWidth(), 250);
		moon.setVisible(false);
		moonThread = new Thread(moon);
		moonThread.start();

	}

	/**
	 * adds invisible stars
	 */
	private void addStars() {
		stars = new Stars();
		add(stars, getWidth() - stars.getWidth(), 240);
		stars.setVisible(false);
		starThread = new Thread(stars);
		starThread.start();

	}

	/**
	 * adds visible obstacles
	 */
	private void addObstacles() {
		int dx = getWidth();
		double dy = getHeight();
		for (int i = 0; i < MyConstants.OBSTACLE_ARR.length; i++) {
			MyConstants.OBSTACLE_ARR[i] = new Obstacle(dx, dy, i);
			add(MyConstants.OBSTACLE_ARR[i], dx, dy);
			obsThread = new Thread(MyConstants.OBSTACLE_ARR[i]);
			obsThread.start();
			dx += getWidth() / 3;
		}

	}

	/**
	 * counts the score also shifts the day
	 */
	private void addCounter() {
		addHighScore();
		initialiseScore();
		while (!GameOver) {
			countScore();
			if (yourScore % 100 == 0)
				playLvlUpSound();
			if (yourScore == shifter)
				shiftingDay();
		}
		GameOver();
	}

	/**
	 * Initializes current score
	 */
	private void initialiseScore() {
		yourScoreL = new GLabel("YOUR SCORE: " + yourScore);
		yourScoreL.setFont("Arial-40");
		yourScoreL.setColor(black);
	}

	/**
	 * counts and displays the score
	 */
	private void countScore() {
		yourScoreL.setLabel("YOUR SCORE: " + yourScore);
		add(yourScoreL, 10, getHeight() / 3);
		yourScore++;
		pause(100);

	}

	/**
	 * adds high score to the screen
	 */
	private void addHighScore() {
		highScoreL = new GLabel("High Score: " + highScore);
		highScoreL.setFont("Arial-40");
		highScoreL.setColor(Color.RED);
		add(highScoreL, getWidth() - highScoreL.getWidth() - 50, getHeight() / 3);

	}

	/**
	 * shifting day and night
	 */
	private void shiftingDay() {
		setBackground((shift) ? black : white);
		yourScoreL.setColor((shift) ? white : black);

		moon.setVisible(shift);
		stars.setVisible(shift);
		for (Cloud cl : clouds)
			cl.setVisible(!shift);
		shifter += 100;
		shift = !shift;

	}

	/**
	 * plays level up sound
	 */
	private void playLvlUpSound() {
		lvlUpT = new Thread(lvlUpSound);
		lvlUpT.start();
		Obstacle.step += 0.8;
	}

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_SPACE: // jump
			if (MyConstants.REX.getY() == MyConstants.REX_LOCATION.getY() || MyConstants.REX.getY() == 454) {
				jumSound();
				jumpStart();
			}
			break;

		case KeyEvent.VK_DOWN: // bend
			if (MyConstants.REX.getY() == MyConstants.REX_LOCATION.getY())
				bendStart();
			break;

		}
	}

	/**
	 * jump thread begins
	 */
	private void jumSound() {
		jumpT = new Thread(jumpSound);
		jumpT.start();

	}

	/**
	 * Rex goes down
	 */
	private void bendStart() {
		MyConstants.REX.setImage("rexDown.gif");
		MyConstants.REX.setLocation(50, MyConstants.REX_BEND_Y);
	}

	/**
	 * jumping thread starts
	 */
	private void jumpStart() {
		rexThreadJump = new Thread(MyConstants.REX);
		rexThreadJump.start();
		MyConstants.REX.setLocation(MyConstants.REX_LOCATION);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// return Rex to walking position after we no longer press down
		if (MyConstants.REX.getY() == MyConstants.REX_BEND_Y)
			switch (e.getKeyCode()) {
			case KeyEvent.VK_DOWN: {// rise from bending
				MyConstants.REX.setLocation(MyConstants.REX_LOCATION);
				MyConstants.REX.setImage("rexWalk.gif");
			}
				break;

			}

	}

	/**
	 * Game over scene
	 */
	private void GameOver() {

		// gameOver sound
		overT = new Thread(gameOverSound);
		overT.start();

		remove(MyConstants.REX);
		setBackground(white);
		yourScoreL.setColor(black);

		// GameOver and Restart labels
		GImage over = new GImage("GameOver.png");
		GImage restart = new GImage("restart.png");
		over.scale(2);
		restart.scale(2);
		add(over, (getWidth() - over.getWidth()) / 2, getHeight() / 2 - over.getHeight());
		add(restart, (getWidth() - restart.getWidth()) / 2, getHeight() / 2 + restart.getHeight() / 2);

		// high score
		if (highScore < yourScore) {
			highScore = yourScore;
			writeNewHighScore();
		}

		waitForClick();
		removeAll();
		beginNewGame();

	}

	/**
	 * starts a new game
	 */
	private void beginNewGame() {
		MyConstants.REX.setImage("rexWalk.gif");
		GameOver = false;
		yourScore = 0;
		shifter = 200;
		shift = true;
		Obstacle.step = 10;
		run();

	}

	/**
	 * Overwrites a new HIGH SCHORE
	 */
	private void writeNewHighScore() {

		try {
			pw = new PrintWriter(new FileWriter("highScore.txt"), false);
			pw.println(highScore);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		new Game().start();
	}

	// rex stuff
	private Thread rexThreadJump;

	// score stuff
	private int highScore;
	private GLabel highScoreL;
	private String highScoreString;
	private int yourScore;
	private GLabel yourScoreL;
	private BufferedReader br = null;
	PrintWriter pw;

	// shifting stuff
	private int shifter = 200;
	private boolean shift = true;

	// moon stuff
	private Moon moon;
	private Thread moonThread;

	// cloud stuff
	private Cloud[] clouds;
	private Thread cloudThread;

	// stars stuff
	private Stars stars;
	private Thread starThread;

	// obstacle stuff
	Thread obsThread;

	// ground stuff
	Thread groundThread;

	// sound stuff
	private JumpSound jumpSound = new JumpSound();
	private Thread jumpT;
	private GameOverSound gameOverSound = new GameOverSound();
	private Thread overT;
	private LevelUpSound lvlUpSound = new LevelUpSound();
	private Thread lvlUpT;

	// colors
	Color white = new Color(255, 250, 250);
	Color black = Color.BLACK;

	public static RandomGenerator rg = RandomGenerator.getInstance();
	public static boolean GameOver = false;

}
