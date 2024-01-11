package GameState;

import Main.GamePanel;
import TileMap.*;
import Entity.*;
import Entity.Enemies.*;
import Audio.AudioPlayer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Level1State extends GameState {
	
	private TileMap tileMap;
	private int score;
	private Background bg;
	
	private Player player;
	
	private ArrayList<Enemy> enemies;
	ArrayList<Dumbbell> dumbbells;
	private ArrayList<Explosion> explosions;
	
	private HUD hud;
	Trophy trophy;
	
	private AudioPlayer bgMusic;
	
	public Level1State(GameStateManager gsm) {
		this.gsm = gsm;
	}
	
	public void init() {
		
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/citytileset.png");
		tileMap.loadMap("/Maps/Tutorial.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);
		
		bg = new Background("/Backgrounds/nightcitybg.gif", 0.1);
		
		player = new Player(tileMap);
		player.setPosition(120, 60);
		
		populateEnemies();
		spawnDumbbells();
		spawnTrophy();
		
		explosions = new ArrayList<Explosion>();
		
		hud = new HUD(player);
		
		bgMusic = new AudioPlayer("/Music/title.mp3");
		bgMusic.play();
		
	}
	
	private void populateEnemies() {
		
		enemies = new ArrayList<Enemy>();
		
		Slugger s;
		Burg b;
		Point[] points = new Point[] {
			new Point(1410, 150),
				new Point(1500, 150),
				new Point(2050, 120),
		};
		for(int i = 0; i < points.length; i++) {
			s = new Slugger(tileMap);
			s.setPosition(points[i].x, points[i].y);
			enemies.add(s);
		}

		
	}

	//spawn trophy
	private void spawnTrophy(){
		trophy =new Trophy(tileMap);
		trophy.setPosition(2970,110);
	}

	private void spawnDumbbells(){
		dumbbells = new ArrayList<Dumbbell>();
		Dumbbell d;

		Point[] points = {
				new Point(630,60),
				new Point(750,60),
				new Point(1950,60),
		};
		for(int i = 0; i < points.length; i++) {
			d = new Dumbbell(tileMap);
			d.setPosition(points[i].x, points[i].y);
			dumbbells.add(d);
		}
	}

	public void update() {

		//player is dead

		// Check for player death
		if (player.isDead()) {
			bgMusic.stop();
			// Transition to game over state
			gsm.setState(GameStateManager.GAMEOVERSTATE);
		}

		//reset score tally for current frame
		score = 0;

		// update player
		player.update();
		tileMap.setPosition(
			GamePanel.WIDTH / 2 - player.getx(),
			GamePanel.HEIGHT / 2 - player.gety()
		);
		
		// set background
		bg.setPosition(tileMap.getx(), tileMap.gety());
		
		// attack enemies
		player.checkAttack(enemies);
		score += player.checkDumbbells(dumbbells) * 10;

		//special trophy collision check
		if (trophy.intersects(player)){
			bgMusic.stop();
			//play a jingle here
			gsm.setState(GameStateManager.LEVEL2STATE);
			bg.update();
			gsm.update();
		}


		
		// update all enemies
		for(int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.update();
			if(e.isDead()) {
				enemies.remove(i);
				i--;
				explosions.add(
					new Explosion(e.getx(), e.gety()));
				score += 50;
			}
		}
		
		// update explosions
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).update();
			if(explosions.get(i).shouldRemove()) {
				explosions.remove(i);
				i--;
			}
		}
		//update player score
		player.setScore(player.getScore() + score);
	}
	
	public void draw(Graphics2D g) {

		// draw bg
		bg.draw(g);
		
		// draw tilemap
		tileMap.draw(g);
		
		// draw player
		player.draw(g);

		//draw trophy
		trophy.draw(g);

		
		// draw enemies
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}

		// draw dumbbells
		for(int i = 0; i < dumbbells.size(); i++) {
			dumbbells.get(i).draw(g);
		}
		
		// draw explosions
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).setMapPosition(
				(int)tileMap.getx(), (int)tileMap.gety());
			explosions.get(i).draw(g);
		}

		// draw hud
		hud.draw(g);
		
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_A) player.setLeft(true);
		if(k == KeyEvent.VK_D) player.setRight(true);
		if(k == KeyEvent.VK_W) player.setUp(true);
		if(k == KeyEvent.VK_S) player.setDown(true);
		if(k == KeyEvent.VK_SPACE) player.setJumping(true);
		if(k == KeyEvent.VK_E) player.setGliding(true);
		if(k == KeyEvent.VK_R) player.setScratching();
		if(k == KeyEvent.VK_F) player.setFiring();
	}
	
	public void keyReleased(int k) {
		if(k == KeyEvent.VK_A) player.setLeft(false);
		if(k == KeyEvent.VK_D) player.setRight(false);
		if(k == KeyEvent.VK_W) player.setUp(false);
		if(k == KeyEvent.VK_S) player.setDown(false);
		if(k == KeyEvent.VK_W) player.setJumping(false);
		if(k == KeyEvent.VK_E) player.setGliding(false);
	}
	
}












