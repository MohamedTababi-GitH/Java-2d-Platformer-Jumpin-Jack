package GameState;

import Main.GamePanel;
import TileMap.*;
import Entity.*;
import Entity.Enemies.*;
import Audio.AudioPlayer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Level2State extends GameState {

	private TileMap tileMap;
	private Background bg;

	private Player player;

	private ArrayList<Enemy> enemies;
	ArrayList<Dumbbell> dumbbells;
	private ArrayList<Explosion> explosions;

	private HUD hud;
	Trophy trophy;

	private AudioPlayer bgMusic;

	public Level2State(GameStateManager gsm) {
		this.gsm = gsm;
	}

	public void init() {

		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/grasstileset.gif");
		tileMap.loadMap("/Maps/elpipi.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);

		bg = new Background("/Backgrounds/pinkcitybg.gif", 0.1);

		player = new Player(tileMap);
		player.setPosition(100, 100);

		populateEnemies();
		spawnDumbbells();
		spawnTrophy();

		explosions = new ArrayList<Explosion>();

		hud = new HUD(player);

		bgMusic = new AudioPlayer("/Music/level1-1.mp3");
		bgMusic.play();

	}

	private void populateEnemies() {

		enemies = new ArrayList<Enemy>();

		Slugger s;
		Burg b;
		Point[] points = new Point[] {
				new Point(200, 100),
				new Point(860, 200),
				new Point(1525, 200),
				new Point(1680, 200),
				new Point(1800, 200)
		};
		for(int i = 0; i < points.length; i++) {
			s = new Slugger(tileMap);
			s.setPosition(points[i].x, points[i].y);
			enemies.add(s);
		}
		for(int i = 0; i < points.length; i++) {
			b = new Burg(tileMap);
			b.setPosition(points[i].x, points[i].y);
			enemies.add(b);
		}


	}

	//spawn trophy
	private void spawnTrophy(){
		trophy =new Trophy(tileMap);
		trophy.setPosition(3100,200);
	}

	private void spawnDumbbells(){
		dumbbells = new ArrayList<Dumbbell>();
		Dumbbell d;

		Point[] points = {
				new Point(100,200),
				new Point(200,100),
				new Point(1000,200),
				new Point(2000,200),
				new Point(1500,100),
				new Point(300,200)
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
			//play a jingle here
			// Transition to game over state
			gsm.setState(GameStateManager.GAMEOVERSTATE);
		}



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
		player.checkDumbbells(dumbbells);

		//special trophy collision check
		if (trophy.intersects(player)){
			bgMusic.stop();
			//play a jingle here
			WinningState l2 = new WinningState(gsm);
			l2.init();
			gsm.setState(GameStateManager.WINNINGSTATE);
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












