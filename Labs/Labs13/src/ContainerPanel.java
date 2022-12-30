import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class ContainerPanel extends JComponent implements MouseMotionListener {


    private BufferedImage mario;
    private int x, y;
    private ArrayList<Obstacle> bombs;

    private boolean gameOver = false;

    public ContainerPanel(Dimension size){

        setFocusable(true);

        try {
            mario = ImageIO.read(new File("Labs/Labs13/src/mario.png"));
        } catch(IOException ex){
            ex.printStackTrace();
        }

        bombs = new ArrayList<>();
        Random randy = new Random();

        for(int i=0; i<4; i++){
            bombs.add(new Obstacle(randy.nextInt(size.width), randy.nextInt(size.height),
                    randy.nextInt(2,10), randy.nextInt(2,10), size));
        }

        addMouseMotionListener(this);
    }



    @Override
    public void paintComponent(Graphics g){

        g.setColor(Color.white);
        g.fillRect(0,0,getWidth(),getHeight());

        if(!gameOver) {

            g.drawImage(mario, x - mario.getWidth() / 2, y - mario.getHeight() / 2, this);

            for (Obstacle o : bombs) {
                o.animate();
                o.draw(g, this);

                if(o.getHitBox().intersects(new Rectangle(x - mario.getWidth() / 2, y - mario.getHeight() / 2,
                        mario.getWidth(),mario.getHeight())))
                    gameOver = true;
            }
        } else{
            g.setColor(Color.black);
            g.drawString("Game Over!", 300, 300);
        }
    }



    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }
}
