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
            titleColor = Color.RED;
            titleFont = new Font("Century Gothic", Font.PLAIN, 18);
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
        String output = "Game Over!";
        // Drawing logic for the game over screen
        g.setColor(Color.BLACK);
        g.setFont(titleFont);
        g.drawString(output, 99, 70);
        g.drawString(output, 101, 70);
        g.drawString(output, 100, 69);
        g.drawString(output, 100, 71);
        g.setColor(titleColor);
        g.drawString(output, 100, 70);
        // Add more text or graphics for the game over screen
        // Drawing logic for the game over screen
        output = "Press Spacebar to restart!";
        g.setColor(Color.BLACK);
        g.setFont(titleFont);
        g.drawString(output, 49, 170);
        g.drawString(output, 51, 170);
        g.drawString(output, 50, 169);
        g.drawString(output, 50, 171);
        g.setColor(titleColor);
        g.drawString(output, 50, 170);
    }

    @Override
    public void keyPressed(int k) {
        // Handle key presses in the game over screen
        if (k == KeyEvent.VK_SPACE) {
            // Restart the game or go back to the main menu
            gsm.setState(GameStateManager.MENUSTATE); // Change to the appropriate state
        }
    }

    @Override
    public void keyReleased(int k) {
        // Handle key releases in the game over screen
    }
}

