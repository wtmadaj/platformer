package src.entities;

import src.main.Game;
import src.main.utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static src.main.utils.Constants.PlayerConstants.*;
import static src.main.utils.HelpMethods.*;

public class Player extends Entity {

    private BufferedImage[][] animations;
    private int animationTick = 0;
    private int animationIndex = 0;
    private int animationSpeed = 15;
    private int playerAction = IDLE;
    private boolean isMoving = false;
    private boolean attacking = false;
    private boolean left, up, right, down, jump;
    private float playerSpeed = 2.0f;
    private int[][] levelData;
    private float xDrawOffset = 21 * Game.SCALE, yDrawOffset = 4 * Game.SCALE;

    //Jumping / gravity variables
    private float airSpeed = 0f;
    private float gravity = 0.04f*Game.SCALE;
    private float jumpSpeed = -2.25f*Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f*Game.SCALE;
    private boolean inAir = false;


    public Player(float x, float y, int width, int height) {
        super(x,y, width, height);
        loadAnimations();
        initHitBox(x, y, 20*Game.SCALE, 27*Game.SCALE);
    }

    public void update() {
        updatePosition();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics g) {
        g.drawImage((Image) animations[playerAction][animationIndex], (int) (hitBox.x - xDrawOffset), (int) (hitBox.y - yDrawOffset), width, height, null);
//        drawHitBox(g); /* For debugging hit box */
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

        if(inAir) {
            if(airSpeed < 0) {
                playerAction = JUMPING;
            } else {
                playerAction = FALLING;
            }
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

        if(jump) {
            jump();
        }
        if(!left && !right && !inAir) return;
        if (!inAir) {
            if(!IsEntityOnFloor(hitBox, levelData)) {
                inAir = true;
            }
        }

        float xSpeed = 0;
        if(left)
            xSpeed -= playerSpeed;
        if(right)
            xSpeed += playerSpeed;

        if(inAir) {
            if(CanMoveHere(hitBox.x, hitBox.y + airSpeed, hitBox.width, hitBox.height, levelData)) {
                hitBox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            } else {
                hitBox.y = GetEntityYPosUnderRoofOrAboveFloor(hitBox, airSpeed);
                if(airSpeed > 0) {
                    resetInAir();
                } else {
                    airSpeed = fallSpeedAfterCollision;
                }
                updateXPos(xSpeed);
            }
        }
        else {
            updateXPos(xSpeed);
        }
        isMoving = true;
    }

    private void jump() {
        if(inAir) return;

        inAir = true;
        airSpeed = jumpSpeed;
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    private void updateXPos(float xSpeed) {
        if(CanMoveHere(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, levelData)) {
            hitBox.x += xSpeed;
        } else {
            hitBox.x = GetEntityXPosNextToWall(hitBox, xSpeed);
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

    public void loadLevelData(int[][] levelData) {
        this.levelData = levelData;
        if(!IsEntityOnFloor(hitBox, levelData)) {
            inAir = true;
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

    public void setJump(boolean jump) {
        this.jump = jump;
    }
}
