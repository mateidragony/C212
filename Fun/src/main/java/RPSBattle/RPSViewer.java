package RPSBattle;

import javax.swing.*;
import java.awt.*;

public class RPSViewer extends JPanel {

    final static int FPS = 60;
    final static int NUM_FIGHTERS = 50;
    final static int ENTITY_SIZE = 20;

    final static Dimension bounds = new Dimension(1000, 750);

    final RockPaperScissors rps;

    public RPSViewer(){
        rps = new RockPaperScissors(new Rectangle(bounds));
        this.setPreferredSize(bounds);
    }


    @Override public void paintComponent(Graphics graphics){
        Graphics2D g = (Graphics2D)graphics;

        g.setColor(Color.white);
        g.fillRect(0,0, getWidth(), getHeight());

        rps.animate();
        rps.draw(g, this);

    }


    public static void main(String[] args) {
        JFrame frame = new JFrame();
        RPSViewer rpsViewer = new RPSViewer();

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(rpsViewer);
        frame.pack();
        frame.setVisible(true);

        Timer t = new Timer(1000/FPS, e -> rpsViewer.repaint());
        t.start();
    }
}
