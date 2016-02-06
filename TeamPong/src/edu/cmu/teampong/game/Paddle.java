package edu.cmu.teampong.game;



import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 *
 * @author pchambino
 */
public class Paddle extends Entity {
    private int width;
    private int height;
    private Color c;
    private int pontuacao;

    public Paddle(Game game, int width, int height, Color c) {
        super(game);
        this.width = width;
        this.height = height;
        this.c = c;
        this.pontuacao = 0;
        dx = 0;
        dy = 0;
    }

    public void setPontuacao(int p) {
        this.pontuacao = p;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public void move(long delta) {
        if (y <= 0) {
        	y = 0;
        	return;
        } else if (y+height >= game.getHeight()) {
            y = game.getHeight()-height;
            return;
        }
        super.move(delta);;
    }

    public void goUP(int dy) {
        //setVelocityY(0);
    	this.y = this.y - dy;

        //setVelocityY(-game.velocity);
    }

    public void goDOWN(int dy) {
        //setVelocityY(0);
    	//move((long)dy);
    	this.y = this.y + dy;
   
        //setVelocityY(game.velocity);
    }

    public void stopMoving() {
        return;
    }

    // draws a rectangle
    @Override
    public void draw(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(c);
        g.fillRoundRect(x, y, width, height,10,10);
    }
    
}
