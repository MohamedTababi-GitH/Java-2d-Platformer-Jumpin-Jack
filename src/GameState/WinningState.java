package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class WinningState extends GameState {

    private Font titleFont;

    public WinningState(GameStateManager gsm) {
        this.gsm = gsm;

        titleFont = new Font("Century Gothic", Font.PLAIN, 28);
    }

    public void init() {
    }

    public void update() {
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.GREEN);
        g.setFont(titleFont);
        g.drawString("Congratulations! You Won!", 80, 70);
    }

    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ENTER) {
            // Restart the game when the Enter key is pressed
            gsm.setState(GameStateManager.MENUSTATE);
        }
    }
    public void keyReleased(int k) {
    }
}
