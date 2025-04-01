import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private Bird bird;
    private ArrayList<Pipe> pipes;
    private Timer timer;
    private Image background;
    private boolean gameOver;
    private int score;
    private boolean gameStarted;
    private Random rand;

    public GamePanel() {
        rand = new Random();
        resetGame();
        
        this.background = new ImageIcon("../assets/flappybirdbg.png").getImage();
        this.timer = new Timer(20, this);

        addKeyListener(this);
        setFocusable(true);
    }

    private void resetGame() {
        this.bird = new Bird(50, 250);
        this.pipes = new ArrayList<>();
        this.gameOver = false;
        this.gameStarted = false;
        this.score = 0;
        spawnPipes();
    }

    public void spawnPipes() {
        int gap = 200;
        int minHeight = 50;
        int maxHeight = 250;
        int height = minHeight + rand.nextInt(maxHeight - minHeight);

        pipes.add(new Pipe(500, height - 300, true));
        pipes.add(new Pipe(500, height + gap, false));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), null);

        bird.draw(g);
        for (Pipe pipe : pipes) {
            pipe.draw(g);
        }

        // Draw score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("Score: " + score, 20, 40);

        if (gameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Game Over!", 100, 250);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Press R to restart", 100, 300);
        } else if (!gameStarted) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Press SPACE or ENTER to start", 30, 300);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameStarted && !gameOver) {
            bird.update();

            for (int i = 0; i < pipes.size(); i++) {
                Pipe pipe = pipes.get(i);
                pipe.update();
                
                if (!pipe.isTop() && pipe.getX() + pipe.getWidth() < bird.getX() && !pipe.isPassed()) {
                    pipe.setPassed(true);
                    score++;
                }

                if (pipe.getX() < -50) {
                    pipes.remove(i);
                    i--;
                }
            }

            if (pipes.size() < 2) {
                spawnPipes();
            }

            checkCollisions();
        }
        repaint();
    }

    public void checkCollisions() {
        if (bird.getY() < 0 || bird.getY() + bird.getHeight() > getHeight()) {
            gameOver();
        }

        for (Pipe pipe : pipes) {
            if (bird.getBounds().intersects(pipe.getBounds())) {
                gameOver();
            }
        }
    }

    private void gameOver() {
        gameOver = true;
        gameStarted = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if ((e.getKeyCode() == KeyEvent.VK_SPACE) || (e.getKeyCode() == KeyEvent.VK_ENTER) ) {
            if (!gameOver && !gameStarted) {
                gameStarted = true;
                timer.start();
            }
            if (!gameOver) {
                bird.flap();
            }
        }
        
        if (e.getKeyCode() == KeyEvent.VK_R && gameOver) {
            resetGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
}