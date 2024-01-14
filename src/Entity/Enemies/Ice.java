package Entity.Enemies;

import Entity.Animation;
import Entity.Enemy;
import TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Ice extends Enemy{

    //need enemy state for burg, slug will stay dumb
    private EnemyState state;
    private int startcounter = 0;

    private BufferedImage[] sprites;

    public Ice(TileMap tm) {

        super(tm);
        //it should start out as patrolling
        state = EnemyState.START;

        moveSpeed = 0.2;
        maxSpeed = 0.2;
        fallSpeed = 0.2;
        maxFallSpeed = 10.0;

        width = 30;
        height = 30;
        cwidth = 20;
        cheight = 20;

        health = maxHealth = 9;
        damage = 1;





        // load sprites
        try {

            BufferedImage spritesheet = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/Sprites/Enemies/icecream.gif"
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
        maxSpeed = 0.2;
        left = true;
        right = false;
        facingRight = false;
        if (!falling) {
            dx = -moveSpeed;
        }
    }

    private void moveRight() {
        maxSpeed = 0.2;
        left = false;
        right = true;
        facingRight = true;
        if (!falling) {
            dx = moveSpeed;
        }
    }

    private void rushLeft() {
        maxSpeed = 0.5;
        left = true;
        right = false;
        facingRight = false;
        if (!falling) {
            dx = -moveSpeed * 2.5;
        }
    }

    private void rushRight() {
        maxSpeed = 0.5;
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

    public boolean caughtIn4k(int range){
        if((x - playerX) <= range && (playerX - x) <= range &&
                (y - playerY) <= 300 && (playerY - y) <= 300){
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

        if(health == 1 && caughtIn4k(150)){
            state = EnemyState.LOW_HEALTH;
        }
        if(caughtIn4k(120)){
            state = EnemyState.ALERT;
        }
    }

    private void alert() {
        if(!bottomLeft && !bottomRight){
            dy += fallSpeed;
            if(dy > maxFallSpeed) dy = maxFallSpeed;
            return;
        }
        if (playerX < x) {
            rushLeft();
        } else if (playerX > x) {
            rushRight();
        }
        if(health == 1){
            state = EnemyState.LOW_HEALTH;
        }
        if (!caughtIn4k(90)){
            state = EnemyState.PATROLLING;
        }

    }

    private void lowHealth() {
        if(!bottomLeft && !bottomRight){
            dy += fallSpeed;
            if(dy > maxFallSpeed) dy = maxFallSpeed;
            return;
        }
        if (playerX < x) {
            rushRight();
        } else if (playerX > x) {
            rushLeft();
        }



        if (!caughtIn4k(150)){
            state = EnemyState.PATROLLING;
        }
    }



    private void getNextPosition() {

        //I want a couple different cases of movement

        if (y > tileMap.getHeight()-getHeight()/2-10) {
            awardsPoints = false;
            health = 0;
            dead = true;
        }

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
