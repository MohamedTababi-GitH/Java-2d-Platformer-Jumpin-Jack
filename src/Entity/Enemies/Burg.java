package Entity.Enemies;

import Entity.*;
import TileMap.TileMap;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;

public class Burg extends Enemy{

    //need enemy state for burg, slug will stay dumb
    private EnemyState state;
    private int startcounter = 0;

    private BufferedImage[] sprites;

    public Burg(TileMap tm) {

        super(tm);
        //it should start out as patrolling
        state = EnemyState.START;

        moveSpeed = 0.5;
        maxSpeed = 0.5;
        fallSpeed = 0.2;
        maxFallSpeed = 10.0;

        width = 30;
        height = 30;
        cwidth = 20;
        cheight = 20;

        health = maxHealth = 5;
        damage = 1;



        // load sprites
        try {

            BufferedImage spritesheet = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/Sprites/Enemies/burger.gif"
                    )
            );

            sprites = new BufferedImage[2];
            for(int i = 0; i < sprites.length; i++) {
                sprites[i] = spritesheet.getSubimage(
                        i * width,
                        0,
                        width,
                        height
                );
            }

        }
        catch(Exception e) {
            e.printStackTrace();
        }

        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(300);

        right = true;
        facingRight = true;

    }

    private void moveLeft() {
        left = true;
        right = false;
        facingRight = false;
        if (!falling) {
            dx = -moveSpeed;
        }
    }

    private void moveRight() {
        left = false;
        right = true;
        facingRight = true;
        if (!falling) {
            dx = moveSpeed;
        }
    }

    private void rushLeft() {
        left = true;
        right = false;
        facingRight = false;
        if (!falling) {
            dx = -moveSpeed * 2.5;
        }
    }

    private void rushRight() {
        left = false;
        right = true;
        facingRight = true;
        if (!falling) {
            dx = moveSpeed * 2.5;
        }
    }

    private void start(){
            dy += fallSpeed;
            if(dy > maxFallSpeed) dy = maxFallSpeed;
        startcounter++;

        if(startcounter > 100){
            state = EnemyState.PATROLLING;
        }
    }

    private boolean caughtIn4k(){
        if((x - playerX) <= 70 && (playerX - x) <= 70 &&
                (y - playerY) <= 30 && (playerY - y) <= 50){
            return true;
        }
        return false;
    }
    private void patrolling() {
        // default movement
        if(left) {
            dx -= moveSpeed;
            if(dx < -maxSpeed) {
                dx = -maxSpeed;
            }
        }
        else if(right) {
            dx += moveSpeed;
            if(dx > maxSpeed) {
                dx = maxSpeed;
            }
        }

        // to not fall
        if(!bottomLeft) {
                y = lastY;
                x = lastX + 1;
                moveRight();
            }
            else if (!bottomRight){
                y = lastY;
                x = lastX - 1;
                moveLeft();
            }
            falling = false;
        lastX = x;
        lastY = y;

        if(health == 1){
            state = EnemyState.LOW_HEALTH;
        }
        if(caughtIn4k()){
            state = EnemyState.ALERT;
        }
    }

    private void alert() {
        if (playerX < x) {
            rushLeft();
        } else if (playerX > x) {
            rushRight();
        }
        if(health == 1){
            state = EnemyState.LOW_HEALTH;
        }
        if (!caughtIn4k()){
            state = EnemyState.PATROLLING;
        }

    }

    private void lowHealth() {
        if (playerX < x) {
            rushRight();
        } else if (playerX > x) {
            rushLeft();
        }
        if (!caughtIn4k()){
            state = EnemyState.PATROLLING;
        }
    }



    private void getNextPosition() {

        //I want a couple different cases of movement

        switch (state) {
            case START:
                start();
                break;
            case PATROLLING:
                patrolling();
                break;
            case ALERT:
                alert();
                break;
            case LOW_HEALTH:
                lowHealth();
                break;
            // Add more states as needed
        }


/*
        // default movement
        if(left) {
            dx -= moveSpeed;
            if(dx < -maxSpeed) {
                dx = -maxSpeed;
            }
        }
        else if(right) {
            dx += moveSpeed;
            if(dx > maxSpeed) {
                dx = maxSpeed;
            }
        }

        // falling
        if(falling) {
            dy += fallSpeed;
        }
*/
        //player spotted

        //write code




    }

    public void update() {

        // update position
        getNextPosition();
        checkTileMapCollision();
        setPosition(xtemp, ytemp);

        // check flinching
        if(flinching) {
            long elapsed =
                    (System.nanoTime() - flinchTimer) / 1000000;
            if(elapsed > 400) {
                flinching = false;
            }
        }

        // if it hits a wall, go other direction
        if(right && dx == 0) {
            right = false;
            left = true;
            facingRight = false;
        }
        else if(left && dx == 0) {
            right = true;
            left = false;
            facingRight = true;
        }



        // update animation
        animation.update();

    }

    public void draw(Graphics2D g) {

        //if(notOnScreen()) return;

        setMapPosition();

        super.draw(g);

    }

}
