import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

public class Obstacle {


    protected int x, y, dx, dy;
    protected BufferedImage img;
    private Dimension bounds;

    public Obstacle(int x, int y, int dx, int dy, Dimension bounds){
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.bounds = bounds;

        try{
            img = ImageIO.read(new File("Labs/Labs13/src/bomb.png"));
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public void draw(Graphics g, ImageObserver io){
        g.drawImage(img, x-img.getWidth()/2, y-img.getHeight()/2, io);
    }

    public Rectangle getHitBox(){
        return new Rectangle(x-img.getWidth()/2, y-img.getHeight()/2,img.getWidth(),img.getHeight());
    }

    public void animate(){
        x += dx;
        y += dy;

        if(x > bounds.width)
            dx *= -1;
        if(y > bounds.height)
            dy *= -1;
        if(x < 0)
            dx *= -1;
        if(y < 0)
            dy *= -1;
    }

}
