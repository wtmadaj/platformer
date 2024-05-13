package src.main;

import src.main.inputs.KeyboardInputs;
import src.main.inputs.MouseInputs;

import javax.swing.*;
import java.awt.*;

import static src.main.Game.GAME_HEIGHT;
import static src.main.Game.GAME_WIDTH;


public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;

    private Game game;

    public GamePanel(Game game) {
        mouseInputs = new MouseInputs(this);
        this.game = game;

        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void setPanelSize() {
        Dimension dim = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        this.setMinimumSize(dim);
        this.setPreferredSize(dim);
        this.setMaximumSize(dim);

    }

    public void updateGame() {
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        game.render(g);
    }

    public Game getGame() {
        return game;
    }
}
