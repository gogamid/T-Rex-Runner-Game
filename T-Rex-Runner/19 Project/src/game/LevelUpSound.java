package game;

import java.io.FileInputStream;

import javazoom.jl.player.Player;



public class LevelUpSound implements Runnable {


	private static void playLevelUpSoundSound() {
		try {

			FileInputStream fis = new FileInputStream("lvlUp.mp3");
			Player playMP3 = new Player(fis);

			playMP3.play();

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	@Override
	public void run() {
		playLevelUpSoundSound();

	}
}
