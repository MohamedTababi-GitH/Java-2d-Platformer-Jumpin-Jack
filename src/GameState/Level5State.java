package GameState;

import Audio.AudioPlayer;
import Entity.*;
import Entity.Enemies.Burg;
import Entity.Enemies.Fry;
import Entity.Enemies.Ice;
import Main.GamePanel;
import TileMap.Background;
import TileMap.TileMap;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Level5State extends GameState {

	private TileMap tileMap;
	private int score;
	private Background bg;

	private Player player;

	private ArrayList<Enemy> enemies;

	//time limit
	/*
	private int timeLimit;       // Total time allowed for the level
	private int remainingTime;
	 // Time remaining for the level
	 */
	private final long updateTimeInterval = 1000; // 1000 milliseconds = 1 second
	private long lastUpdateTime;

	private final int timeAwarded = 10;



	// Caesar
	ArrayList<Juice> juices;
	ArrayList<Dumbbell> dumbbells;
	private ArrayList<Explosion> explosions;

	private HUD hud;
	Trophy trophy;

	private AudioPlayer bgMusic;

	public Level5State(GameStateManager gsm) {
		this.gsm = gsm;
	}

	public void init() {

		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/citytileset.png");
		tileMap.loadMap("/Maps/level1-6.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);

		bg = new Background("/Backgrounds/nightcitybg.gif", 0.1);

		player = HUD.getPlayer();
		int php = player.getHealth();
		int psc = player.getScore();
		player = new Player(tileMap, php, psc);
		player.setPosition(45, 195);

		populateEnemies();
		spawnDumbbells();
		spawnJuice();
		spawnTrophy();

		explosions = new ArrayList<Explosion>();

		hud = new HUD(player, player.getScore(), player.getHealth());

		bgMusic = new AudioPlayer("/Music/level1.mp3");
		bgMusic.clip.loop(Clip.LOOP_CONTINUOUSLY);
		bgMusic.play();


		//time limit

		player.timeLimit = 100;  // 300 seconds = 5 minutes
		player.remainingTime = player.timeLimit;


	}

	public void addTime(int time) {
		player.remainingTime += time;
	}

	private void populateEnemies() {

		enemies = new ArrayList<Enemy>();

		Enemy s;
		Burg b;
		Point[] points = new Point[] {
				new Point(1245, 15),
				new Point(1185, 135),
				new Point(1815, 195),
				new Point(2115, 195),
				new Point(2265, 105),
		};
		for(int i = 0; i < points.length; i++) {
			if (i%5==0){
				s = new Fry(tileMap);
				s.setPosition(points[i].x, points[i].y);
				enemies.add(s);}
			else if (i%2==0){
				s = new Ice(tileMap);
				s.setPosition(points[i].x, points[i].y);
				enemies.add(s);}
			else{
				s = new Burg(tileMap);
				s.setPosition(points[i].x, points[i].y);
				enemies.add(s);}
		}



	}

	//spawn trophy
	private void spawnTrophy(){
		trophy =new Trophy(tileMap);
		trophy.setPosition(2685, 195);
	}


	private void spawnDumbbells(){
		dumbbells = new ArrayList<Dumbbell>();
		Dumbbell d;

		Point[] points = {
				new Point(1995, 45),
				new Point(2025, 45),
				new Point(2055, 45),
		};
		for(int i = 0; i < points.length; i++) {
			d = new Dumbbell(tileMap);
			d.setPosition(points[i].x, points[i].y);
			dumbbells.add(d);
		}
	}



	// Caesar code
	private void spawnJuice(){
		juices = new ArrayList<Juice>();
		Juice j;

		Point[] points = {
				new Point(945, 45)
		};
		for(int i = 0; i < points.length; i++) {
			j = new Juice(tileMap);
			j.setPosition(points[i].x, points[i].y);
			juices.add(j);
		}

	}



	public void update() {
		long currentTime = System.currentTimeMillis();
		long elapsedTime = currentTime - lastUpdateTime;

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
		for(int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.getPlayerLocation(player.getx(), player.gety());
			e.update();
			if(e.isDead()) {
				enemies.remove(i);
				i--;
				explosions.add(
						new Explosion(e.getx(), e.gety()));
				if(e.awardsPoints) {
					score +=50;
					player.setScore(player.getScore() + score);
					if (e.maxHealth != 5)
						player.setScore(player.getScore() + (score/2));
					if (e.maxHealth > 5)
						player.setScore(player.getScore() + (score/2));
				}
			}
		}

		// update explosions
		for (int i = 0; i < explosions.size(); i++) {
			explosions.get(i).update();
			if (explosions.get(i).shouldRemove()) {
				explosions.remove(i);
				i--;
			}
		}
		tileMap.setPosition(
				GamePanel.WIDTH / 2 - player.getx(),
				GamePanel.HEIGHT / 2 - player.gety()
		);

		// set background
		bg.setPosition(tileMap.getx(), tileMap.gety());

		// attack enemies
		player.checkAttack(enemies);
		score += player.checkDumbbells(dumbbells) * 10;

		// Caesar
		player.checkJuice(juices);

		//special trophy collision check
		if (trophy.intersects(player)){
			bgMusic.stop();
			//play a jingle here
			gsm.setState(GameStateManager.WINNINGSTATE);
			bg.update();
			gsm.update();
		}



		// Check if the update interval has passed
		if (elapsedTime >= updateTimeInterval) {
			// Update the timer
			player.remainingTime--;


			// Check if time has run out
			if (player.remainingTime <= 0) {

				// Transition to game over state
				bgMusic.stop();
				gsm.setState(GameStateManager.GAMEOVERSTATE);
			}

			// Update lastUpdateTime to the current time
			lastUpdateTime = currentTime;
			// update all enemies

			//update player score
			player.setScore(player.getScore() + score);
		}
	}

	public void draw(Graphics2D g) {
		// draw bg
		bg.draw(g);

		// draw tilemap
		tileMap.draw(g);

		//draw trophy
		trophy.draw(g);
		// draw player
		player.draw(g);



		// draw enemies
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}

		// draw dumbbells
		for(int i = 0; i < dumbbells.size(); i++) {
			dumbbells.get(i).draw(g);
		}
// Caesar code
		for(int i = 0; i < juices.size(); i++) {
			juices.get(i).draw(g);
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

	public void keyPressed(int k) throws NullPointerException{
		if(k == KeyEvent.VK_A) player.setLeft(true);
		if(k == KeyEvent.VK_D) player.setRight(true);
		if(k == KeyEvent.VK_W) player.setUp(true);
		if(k == KeyEvent.VK_S) player.setDown(true);
		if(k == KeyEvent.VK_SPACE) player.setJumping(true);
		//if(k == KeyEvent.VK_E) player.setGliding(true);
		if(k == KeyEvent.VK_L) player.setScratching();
		//if(k == KeyEvent.VK_F) player.setFiring();
	}

	public void keyReleased(int k) throws NullPointerException{
		if(k == KeyEvent.VK_A) player.setLeft(false);
		if(k == KeyEvent.VK_D) player.setRight(false);
		if(k == KeyEvent.VK_W) player.setUp(false);
		if(k == KeyEvent.VK_S) player.setDown(false);
		if(k == KeyEvent.VK_SPACE) player.setJumping(false);
		//if(k == KeyEvent.VK_E) player.setGliding(false);
	}

}












