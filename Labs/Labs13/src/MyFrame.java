import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MyFrame extends JFrame {


    public MyFrame(){

        setCursor( getToolkit().createCustomCursor(
                new BufferedImage( 1, 1, BufferedImage.TYPE_INT_ARGB ),
                new Point(),
                null ) );

        Dimension size = new Dimension(800,600);
        setSize(size);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        ContainerPanel marioGame = new ContainerPanel(size);
        add(marioGame);
        setVisible(true);

        Timer t = new Timer(1000/60, e -> marioGame.repaint());
        t.start();


    }



    public static void main(String[] args) {

        new MyFrame();
    }


}
