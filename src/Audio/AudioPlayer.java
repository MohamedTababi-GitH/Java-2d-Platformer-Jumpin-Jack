package Audio;

import javax.sound.sampled.*;
import java.io.InputStream;

public class AudioPlayer {
	
	public Clip clip;

	
	public AudioPlayer(String s) {
		
		try {
			InputStream audioStream = getClass().getResourceAsStream(s);
			if (audioStream == null) {
				throw new RuntimeException("Audio resource not found: " + s);
			}

			AudioInputStream ais = AudioSystem.getAudioInputStream(audioStream);
			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat(
				AudioFormat.Encoding.PCM_SIGNED,
				baseFormat.getSampleRate(),
				16,
				baseFormat.getChannels(),
				baseFormat.getChannels() * 2,
				baseFormat.getSampleRate(),
				false
			);
			AudioInputStream dais =
				AudioSystem.getAudioInputStream(
					decodeFormat, ais);
			clip = AudioSystem.getClip();
			clip.open(dais);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void play() {

		if(clip == null) return;
		clip.stop();
		clip.setFramePosition(0);
		clip.start();
	}
	
	public void stop() {
		if(clip.isRunning()) clip.stop();
	}
	
	public void close() {
		stop();
		clip.close();
	}
	
}














