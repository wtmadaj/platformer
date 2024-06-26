package src.entities;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Entity {

    protected float x;
    protected float y;
    protected int width, height;
    protected Rectangle2D.Float hitBox;

    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    protected void drawHitBox(Graphics g) {
        //For debugging hit box
        g.setColor(Color.PINK);
        g.drawRect((int) hitBox.x, (int) hitBox.y, (int) hitBox.width, (int) hitBox.height);
    }

    protected void initHitBox(float x, float y, int width, int height) {
        hitBox = new Rectangle2D.Float(x, y, width, height);
    }

//    public void updateHitBox() {
//        hitBox.x = (int) x;
//        hitBox.y = (int) y;
//    }

    protected Rectangle2D.Float getHitBox() {
        return hitBox;
    }
}
