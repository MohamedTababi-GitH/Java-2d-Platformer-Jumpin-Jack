package Entity;

import Audio.AudioPlayer;
import Entity.Enemies.EnemyState;
import TileMap.TileMap;

public class Enemy extends MapObject {
	protected int health;
	public int maxHealth;
	protected boolean dead;
	public boolean awardsPoints = true;
	protected int damage;

	protected int playerX;
	protected int playerY;

	protected boolean flinching;
	protected long flinchTimer;
	
	public Enemy(TileMap tm) {
		super(tm);
	}
	
	public boolean isDead() { return dead; }
	
	public int getDamage() { return damage; }
	
	public void hit(int damage) {
		if(dead || flinching) return;
		health -= damage;
		if(health < 0) health = 0;
		if(health == 0) dead = true;
		flinching = true;
		flinchTimer = System.nanoTime();
	}

	public void getPlayerLocation(int x, int y){
		playerX = x;
		playerY = y;
	}

	protected void newState(int enemyState){


	}


	public void update() {
		}

	}















