import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

/*
 * ICS3U Summative
 */
public class HopeStar {

	public static String playerName = "swag";

	public static void main(String[] args) {

		GamePanel gamePanel = new GamePanel();

		JFrame gameScreen = new JFrame();
		gameScreen.setSize(906, 709);
		gameScreen.setTitle("All Hail the Hopestar");
		gameScreen.setLocationRelativeTo(null);
		gameScreen.setResizable(false);
		gameScreen.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		gameScreen.add(gamePanel);
		gameScreen.setVisible(true);
		gamePanel.run();

	}
}