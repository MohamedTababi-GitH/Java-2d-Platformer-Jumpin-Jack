package GameState;

import Audio.AudioPlayer;
import TileMap.Background;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuState extends GameState {
	
	private Background bg;
	
	private int currentChoice = 0;
	private String[] options = {
		"Start",
		"Quit"
	};
	
	private Color titleColor;
	private Font titleFont;
	
	private Font font;

	private AudioPlayer music;
	
	public MenuState(GameStateManager gsm) {
		
		this.gsm = gsm;
		
		try {
			music = new AudioPlayer("/Music/title.mp3");
			music.clip.loop(Clip.LOOP_CONTINUOUSLY);
			bg = new Background("/Backgrounds/pinkcitybg.gif", 1);
			bg.setVector(0.7, 0);
			
			titleColor = new Color(0, 64, 64);
			titleFont = new Font(
					"Impact",
					Font.PLAIN,
					28);
			
			font = new Font("Courier New", Font.PLAIN, 12);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void init() {}
	
	public void update() {
		bg.update();
	}
	
	public void draw(Graphics2D g) {
		
		// draw bg
		bg.draw(g);
		g.setColor(Color.RED);
		g.setFont(titleFont);
		g.drawString("Jumpin' Jack", 78, 68);
		// draw title
		g.setColor(titleColor);
		g.drawString("Jumpin' Jack", 80, 70);


		g.setFont(font);
		g.drawString("Use W, S, and Spacebar to navigate!", 30, 100);
		
		// draw menu options
		for(int i = 0; i < options.length; i++) {
			if(i == currentChoice) {
				g.setColor(Color.BLACK);
			}
			else {
				g.setColor(Color.RED);
			}
			g.drawString(options[i], 145, 140 + i * 15);
		}
		
	}
	
	private void select() {
		if(currentChoice == 0) {
			music.stop();
			gsm.setState(GameStateManager.LEVEL1STATE);
		}
		if(currentChoice == 1) {
			// help
		}
		if(currentChoice == 2) {
			System.exit(0);
		}
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_SPACE){
			select();
		}
		if(k == KeyEvent.VK_W) {
			currentChoice--;
			if(currentChoice == -1) {
				currentChoice = options.length - 1;
			}
		}
		if(k == KeyEvent.VK_S) {
			currentChoice++;
			if(currentChoice == options.length) {
				currentChoice = 0;
			}
		}
	}
	public void keyReleased(int k) {}
	
}










