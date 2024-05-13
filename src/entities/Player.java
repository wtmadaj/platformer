package src.entities;

import src.main.utils.LoadSave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static src.main.utils.Constants.PlayerConstants.*;

public class Player extends Entity {

    private BufferedImage[][] animations;
    private int animationTick = 0;
    private int animationIndex = 0;
    private int animationSpeed = 15;
    private int playerAction = IDLE;
    private boolean isMoving = false;
    private boolean attacking = false;
    private boolean left, up, right, down;
    private float playerSpeed = 2.0f;


    public Player(float x, float y, int width, int height) {
        super(x,y, width, height);
        loadAnimations();
    }

    public void update() {
        updatePosition();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics g) {
        g.drawImage(animations[playerAction][animationIndex], (int) x, (int) y, width, height, null);
    }

    private void updateAnimationTick() {
        animationTick++;
        if(animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if(animationIndex >= GetSpriteAmount(playerAction)) {
                animationIndex = 0;
                attacking = false;
            }
        }
    }

    private void setAnimation() {
        int startAnimation = playerAction;
        if(isMoving) {
            playerAction = RUNNING;
        } else {
            playerAction = IDLE;
        }

        if(attacking) {
            playerAction = ATTACK_1;
        }

        if (startAnimation != playerAction) {
            resetAnimationTick();
        }
    }

    public void resetAnimationTick() {
        animationTick = 0;
        animationIndex = 0;
    }

    private void updatePosition() {
        isMoving = false;

        if(left && !right) {
            x -= playerSpeed;
            isMoving = true;
        } else if(right && !left) {
            x += playerSpeed;
            isMoving = true;
        }

        if(up && !down) {
            y -= playerSpeed;
            isMoving = true;
        } else if(down && !up) {
            y += playerSpeed;
            isMoving = true;
        }
    }

    private void loadAnimations() {
            BufferedImage image = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
            animations = new BufferedImage[9][6];
            for(int j = 0; j < animations.length; j++) {
                for(int i = 0; i < animations[j].length; i++) {
                    animations[j][i] = image.getSubimage(i * 64, j * 40, 64, 40);
                }
            }
    }

    public void resetDirBooleans() {
        left = false;
        up = false;
        right = false;
        down = false;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }
}
