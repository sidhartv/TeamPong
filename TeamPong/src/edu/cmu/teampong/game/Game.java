package edu.cmu.teampong.game;


import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

public class Game extends Canvas {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6573752918429252246L;
	private JFrame frame;
    // true while game is running
    private boolean running;
    // to pause the game
    private boolean pause;
    // for the game begin
    private boolean begin;
    // is fullscreen
    private boolean fullscreen = false;
    // strategy buffer
    private BufferStrategy strategy;
    // graphics to be drawn
    private Graphics2D g;
    // size of window
    private final Dimension size = new Dimension(800, 600);
    // a list of entities
    private ArrayList<Entity> entities;
    // the paddles
    public ArrayList<Paddle> paddles;
    // ball
    public Ball ball;
    // velocidade das paddles
    public final int velocity = 200;
    // game types, single player has AI
    private enum gameType {SINGLE_PLAYER, TWO_PLAYERS};
    private gameType type;

    public Game() {
        // frame
        frame = new JFrame("Ping Pong");

        // add keyboard listener
        //addKeyListener(new Keyboard());
        
        // add canvas
        frame.add(this);

        // set close operation
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // disable repaint
        frame.setIgnoreRepaint(true);
        setIgnoreRepaint(true);

        // set frame size
        frame.setSize(size);

        // set not resizable
        frame.setResizable(false);

        // center frame
        frame.setLocationRelativeTo(null);

        // set visible and request focus
        frame.setVisible(true);

        // create buffer
        createBufferStrategy(3);
        strategy = getBufferStrategy();

        // request focus
        requestFocusInWindow();

        // game
        init();
        goToMenu();
        //gameLoop();
    }

    // adds an entity to the game
    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    private void init() {
        //load entities
        entities = new ArrayList<Entity>(3);
        paddles = new ArrayList<Paddle>(2);

        // size of paddles
        int width = 15;
        int height = 150;

        paddles.add(new Paddle(this, width, height, Color.decode("#e05252")));
        paddles.add(new Paddle(this, width, height, Color.decode("#3ed6ea")));

        // size of ball
        int diameter = 20;
        
        ball = new Ball(this, diameter);
        
        // init variables
        running = true;
        pause = false;
        begin = true;
    }

    private void quit() {
        System.exit(0);
    }

    private void startGame() {

        // size of paddles
        int width = paddles.get(0).getWidth();
        int height = paddles.get(0).getHeight();

        // inicial x os paddles
        int x = 20;

        // set initial positions
        paddles.get(0).setPosition( x, size.height/2-height/2);
        paddles.get(1).setPosition( size.width-x-width, size.height/2-height/2);

        // set initial velocity to zero
        paddles.get(0).stopMoving();
        paddles.get(1).stopMoving();

        // velocity of ball
        Random rand = new Random();
        int dx, dy;
        if (rand.nextInt(2) == 0) {
            dx = velocity;
            if (rand.nextInt(2) == 0)
                dy = dx / 2;
            else
                dy = -dx / 2;
        } else {
            dx = - velocity;
            if (rand.nextInt(2) == 0)
                dy = dx / 2;
            else
                dy = -dx / 2;
        }

        // size of ball
        int diameter = ball.getDiameter();

        ball.setPosition( size.width/2-diameter/2, size.height/2-diameter/2);
        ball.setVelocity(dx, dy);
    }

    public void receiveValues(int paddle, int moveBy) {
    	paddles.get(paddle).goUP(moveBy * 3);
    }
    public void receiveStart() {
    	begin = false;
    }
    public void receiveStop() {
    	begin = true;
    }
    
    
    public void goToMenu() {
        pause = false;
        begin = true;
        startGame();

        // set pontuacao inicial para zero
        paddles.get(0).setPontuacao(0);
        paddles.get(1).setPontuacao(0);
    }

    public void win(Paddle paddle) {
        paddle.setPontuacao(paddle.getPontuacao()+1);

        startGame();
    }

    private void testCollisions() {
        boolean hits = false;

        // check: hits left paddle
        Paddle paddle = paddles.get(0);
        if (ball.getPositionX() < paddle.getPositionX()+paddle.getWidth()
                && ball.getPositionY()+ball.getDiameter() > paddle.getPositionY()
                && ball.getPositionY() < paddle.getPositionY()+paddle.getHeight())
            if (ball.getVelocityX() < 0) {
                hits = true;
            }

        // se nao bateu
        if (!hits) {
            // check: hits the right paddle
            paddle = paddles.get(1);
            if (ball.getPositionX()+ball.getDiameter() > paddle.getPositionX()
                    && ball.getPositionY()+ball.getDiameter() > paddle.getPositionY()
                    && ball.getPositionY() < paddle.getPositionY()+paddle.getHeight())
                if (ball.getVelocityX() > 0) {
                    hits = true;
                }
        }

        // hits
        if (hits) {
            // aumenta velocidade em x e altera velocidade em y
            ball.setVelocityX((int)(-ball.getVelocityX()*1.05));
            ball.setVelocityY((int)((ball.getPositionY() + ball.getDiameter()/2 - paddle.getPositionY() - paddle.getHeight()/2)
                    *velocity*1.5/(paddle.getHeight()/2)));
        }
    }

    private void drawBackground(Graphics2D g) {
        g.setColor(Color.WHITE);
       
        // pontuacoes
        g.setFont(new Font("Helvetica", Font.PLAIN, 400));

        // pontuacao da paddle da esquerda
        int pontuacao = paddles.get(0).getPontuacao();
        for (int i = 0; pontuacao >= 10; i++) {
            g.fillRect(110+i*5, 410, 2, 10);

            pontuacao -= 10;
        }

        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
        		RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.drawString(""+pontuacao, 135, 400);

        // pontuacao da paddle da direita
        pontuacao = paddles.get(1).getPontuacao();
        for (int i = 0; pontuacao >= 10; i++) {
            g.fillRect(450+i*5, 410, 2, 10);

            pontuacao -= 10;
        }
        g.drawString(""+pontuacao, 450, 400);
    }

    public void gameLoop() {
        long lastLoop = System.currentTimeMillis();
        long delta;

        while(running) {
            // time since last loop
            delta = System.currentTimeMillis() - lastLoop;
            lastLoop = System.currentTimeMillis();

            // get Graphics
            g = (Graphics2D) strategy.getDrawGraphics();

            // clean screen
            g.setColor(Color.decode("#ebebeb"));
            g.fillRect(0, 0, getWidth(), getHeight());

            // for the entities
            Entity entity;

            if (!pause && !begin) {
                // collisions
                testCollisions();

                // do AI if needed
//                if (type == gameType.SINGLE_PLAYER)
//                    paddles.get(0).doAI();

                // move all entities
                for (int i = 0; i < entities.size(); i++) {
                    entity = entities.get(i);

                    entity.move(delta);
                }
            }

            // draw background
            drawBackground(g);

            if (pause) {
                g.setColor(Color.WHITE);
                g.setFont(new Font("Helvetica", Font.PLAIN, 200));
                g.drawString("Paused", 40, 350);
            }


            // draw all entities
            for (int i = 0; i < entities.size(); i++) {
                entity = entities.get(i);

                entity.draw(g);
            }

            // clear graphics and flip buffer page
            g.dispose();
            strategy.show();

            // does about 100fps
            try { Thread.sleep(10); } catch (InterruptedException ex) {}
        }
    }

    public static void main(String[] args) {
        new Game();
    }

    
    
    /*class Keyboard implements KeyListener {

        public void keyTyped(KeyEvent e) {
        }

        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            // paddle da direita
            if (key == KeyEvent.VK_UP) {
                paddles.get(1).goUP(20);
            } else if (key == KeyEvent.VK_DOWN)
                paddles.get(1).goDOWN(20);
            
            // paddle da esquerda
            if (key == KeyEvent.VK_A)
                paddles.get(0).goUP(20);
            else if (key == KeyEvent.VK_Z)
                paddles.get(0).goDOWN(20);
        }

        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();

            // set game type and start game
            if (key == KeyEvent.VK_SPACE) {
                type = gameType.TWO_PLAYERS;
                begin = false;
            }
            if (key == KeyEvent.VK_UP) {
                paddles.get(1).stopMoving();
            } else if (key == KeyEvent.VK_DOWN)
                paddles.get(1).stopMoving();
            
            // paddle da esquerda
            if (key == KeyEvent.VK_A)
                paddles.get(0).stopMoving();
            else if (key == KeyEvent.VK_Z)
                paddles.get(0).stopMoving();
        }
            
        } */
    }

