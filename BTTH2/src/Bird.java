import java.awt.*;
import javax.swing.ImageIcon;

public class Bird {
    private int x, y, width, height;
    private int velocity;
    private final int GRAVITY = 1;
    private Image birdImage;

    public Bird(int x, int y) {
        this.x = x;
        this.y = y;
        this.velocity = 0;
        this.birdImage = new ImageIcon("../assets/flappybird.png").getImage();
        this.width = 50; 
        this.height = 40;
    }

    public void update() {
        velocity += GRAVITY;
        y += velocity;
    }

    public void flap() {
        velocity = -10; 
    }

    public void draw(Graphics g) {
        g.drawImage(birdImage, x, y, width, height, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getHeight() { return height; }
}