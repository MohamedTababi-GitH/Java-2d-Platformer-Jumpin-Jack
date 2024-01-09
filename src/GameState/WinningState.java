package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import TileMap.Background;
import java.awt.*;
import java.awt.event.KeyEvent;

public class WinningState extends GameState {

    private Background bg;
    private Font titleFont;

    public WinningState(GameStateManager gsm) {
        this.gsm = gsm;
        try {

        titleFont = new Font("Century Gothic", Font.PLAIN, 28);
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
