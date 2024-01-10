package Entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class HUD {
	
	private Player player;
	
	private BufferedImage[] image;
	private Font font;
	
	public HUD(Player p) {
		player = p;
		try {
			image = new BufferedImage[2];
			image[0] = ImageIO.read(
				getClass().getResourceAsStream(
						"/HUD/heart.gif"
				)
			);
			image[1] = ImageIO.read(
					getClass().getResourceAsStream(
							"/HUD/points.gif"
					)
			);
			font = new Font("Courier New", Font.BOLD, 14);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g) {

		for(int i = 0; i < player.getHealth(); i++){
			g.drawImage(image[0], 8 + (i * 16), 10, null);
		}

		g.drawImage(image[1], 8, 27, null);
		g.setFont(font);
		g.setColor(Color.getHSBColor(0.85F,0.13F,0.13F));
		//OUTLINE
		g.drawString(
			player.getScore()+"",
			26,
			40
		);
		g.drawString(
				player.getScore()+"",
				24,
				40
		);
		g.drawString(
				player.getScore()+"",
				25,
				41
		);
		g.drawString(
				player.getScore()+"",
				25,
				39
		);

		//ACTUAL FONT
		g.setColor(Color.WHITE);
		g.drawString(
				player.getScore()+"",
				25,
				40
		);
		
	}
	
}













