package src.main.utils;

import src.main.Game;

import java.awt.geom.Rectangle2D;

public class HelpMethods {

    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] levelData){
        if(!isSolid(x, y, levelData))
            if(!isSolid(x + width, y + height, levelData))
                if(!isSolid(x + width, y, levelData))
                    if(!isSolid(x, y + height, levelData))
                        return true;
        return false;
    }

    private static boolean isSolid(float x, float y, int[][] lvlData) {
        if (x < 0 || x >= Game.GAME_WIDTH)
            return true;
        if (y < 0 || y >= Game.GAME_HEIGHT)
            return true;

        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;

        int value = lvlData[(int) yIndex][(int) xIndex];

        if (value >= 48 || value < 0 || value != 11)
            return true;
        return false;
    }

    public static float GetEntityXPosNextToWall(Rectangle2D.Float hitBox, float xSpeed) {
        int currentTile = (int)(hitBox.x / Game.TILES_SIZE);
        if(xSpeed > 0) {
            //right
            int tileXPos = currentTile * Game.TILES_SIZE;
            int xOffset = (int)(Game.TILES_SIZE - hitBox.width);

            return tileXPos + xOffset - 1;
        } else {
            //left
            return currentTile * Game.TILES_SIZE;
        }
    }

    public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitBox, float airSpeed) {
        int currentTile = (int)(hitBox.y / Game.TILES_SIZE);
        if(airSpeed > 0) {
            // Falling or touching ground
            int tileYPos = currentTile * Game.TILES_SIZE;
            int yOffset = (int)(Game.TILES_SIZE - hitBox.height);
            return tileYPos + yOffset - 1;
        } else {
            // Jumping
            return currentTile * Game.TILES_SIZE;
        }
    }

    public static boolean IsEntityOnFloor(Rectangle2D.Float hitBox, int[][] levelData) {
        // Check pixel below bottom left and bottom right corners

        if(!isSolid(hitBox.x, hitBox.y + hitBox.height + 1, levelData))
            if(!isSolid(hitBox.x + hitBox.width, hitBox.y + hitBox.height + 1, levelData))
                return false;
        return true;
    }
}
