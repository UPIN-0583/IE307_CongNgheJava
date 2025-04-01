import java.awt.*;
import javax.swing.ImageIcon;

public class Pipe {
    private int x, y, width, height;
    private boolean isTop;
    private boolean passed;
    private Image pipeImage;

    public Pipe(int x, int y, boolean isTop) {
        this.x = x;
        this.y = y;
        this.isTop = isTop;
        this.passed = false;
        this.pipeImage = new ImageIcon(isTop ? "../assets/toppipe.png" : "../assets/bottompipe.png").getImage();
        this.width = 80;
        this.height = 300;
    }

    public void update() {
        x -= 5;
    }

    public void draw(Graphics g) {
        g.drawImage(pipeImage, x, y, width, height, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public int getX() { return x; }
    public int getWidth() { return width; }
    public boolean isTop() { return isTop; }
    public boolean isPassed() { return passed; }
    public void setPassed(boolean passed) { this.passed = passed; }
}