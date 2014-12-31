import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundStuff {
	public void shoot() {
		try {
			AudioInputStream audio = AudioSystem.getAudioInputStream(new File(
					"x.wav"));
			Clip clip = AudioSystem.getClip();
			clip.open(audio);
			clip.start();
		}

		catch (UnsupportedAudioFileException uae) {
			System.out.println(uae);
		} catch (IOException ioe) {
			System.out.println(ioe);
		} catch (LineUnavailableException lua) {
			System.out.println(lua);
		}
	}
}
