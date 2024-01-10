package GameState;

import Audio.AudioPlayer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class GameOverState extends GameState {

    private Color titleColor;
    private Font titleFont;

    private AudioPlayer jingle;

    public GameOverState(GameStateManager gsm) {
        this.gsm = gsm;

        try {
            jingle = new AudioPlayer("/SFX/death.mp3");
            jingle.play();
            titleColor = new Color(128, 0, 0);
            titleFont = new Font("Century Gothic", Font.PLAIN, 28);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {
        // Initialization logic for the game over state
    }

    @Override
    public void update() {
        // Update logic for the game over state
    }

    @Override
    public void draw(Graphics2D g) {
        // Drawing logic for the game over screen
        g.setColor(titleColor);
        g.setFont(titleFont);
        g.drawString("Game Over", 80, 70);
        // Add more text or graphics for the game over screen
    }

    @Override
    public void keyPressed(int k) {
        // Handle key presses in the game over screen
        if (k == KeyEvent.VK_ENTER) {
            // Restart the game or go back to the main menu
            gsm.setState(GameStateManager.MENUSTATE); // Change to the appropriate state
        }
    }

    @Override
    public void keyReleased(int k) {
        // Handle key releases in the game over screen
    }
}

