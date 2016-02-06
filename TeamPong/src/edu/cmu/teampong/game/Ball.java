package edu.cmu.teampong.game;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author pchambino
 */
public class Ball extends Entity {
    private int diameter;

    public Ball(Game game, int d) {
        super(game);
        this.diameter = d;
    }

    public int getDiameter() {
        return diameter;
    }

    @Override
    public void setVelocityX(int dx) {
        int maxVel = 3*game.velocity;
        if (dx > maxVel)
            dx = maxVel;

        super.setVelocityX(dx);
    }

    @Override
    public void move(long delta) {
        // ball hits top or bottom limits
        if (dy < 0 && y < 0 || dy > 0 && y+diameter > game.getHeight())
        {
            // plays pong sound
            game.playClip(game.pongClip);

            // change direction
            dy *= -1;
        }

        // paddle da esquerda ganha!
        if (dx > 0 && x+diameter > game.getWidth())
            game.win(game.paddles.get(0));

        // paddle da direita ganha!
        if (dx < 0 && x < 0)
            game.win(game.paddles.get(1));

        super.move(delta);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillOval(x, y, diameter, diameter);
    }
}
