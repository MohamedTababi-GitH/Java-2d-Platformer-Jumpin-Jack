package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import Audio.AudioPlayer;
import TileMap.Background;
import java.awt.*;
import java.awt.event.KeyEvent;

public class WinningState extends GameState {

    private Background bg;
    private Font titleFont;

    private AudioPlayer jingle;

    public WinningState(GameStateManager gsm) {
        this.gsm = gsm;
        try {

            jingle = new AudioPlayer("/SFX/youwin!.mp3");
            jingle.play();
        titleFont = new Font("Century Gothic", Font.PLAIN, 18);
        bg = new Background("/Backgrounds/staywinning.gif", 1);
        bg.setVector(-0.1, 0);

    }
		catch(Exception e) {
        e.printStackTrace();
    }
    }

    public void init() {
    }

    public void update() {
        bg.update();
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.setFont(titleFont);
        g.drawString("Congratulations! You Won!", 49, 70);
        g.drawString("Congratulations! You Won!", 51, 70);
        g.drawString("Congratulations! You Won!", 50, 69);
        g.drawString("Congratulations! You Won!", 50, 71);
        g.setColor(Color.GREEN);
        g.drawString("Congratulations! You Won!", 50, 70);
    }

    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ENTER) {
            // Restart the game when the Enter key is pressed
            gsm.setState(GameStateManager.LEVEL2STATE);
        }
        if (k == KeyEvent.VK_SPACE) {
            // Restart the game when the Enter key is pressed
            gsm.setState(GameStateManager.LEVEL2STATE);
        }
    }
    public void keyReleased(int k) {
    }
}
