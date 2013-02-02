import lejos.nxt.Sound;


public class PlaySadRobot {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		playMusic();

	}
	
	public static void playMusic() {
		Sound.playTone(587, 200);
		Sound.playTone(659, 200);
	}
}
