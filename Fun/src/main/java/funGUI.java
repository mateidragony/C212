import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class funGUI extends JPanel{

    private JButton butt;
    private final int width = 800, height = 800;


    public funGUI() throws IOException{

        setPreferredSize(new Dimension(width,height));

        initComps();
    }



    @Override
    public void paintComponent(Graphics g){

        g.setColor(new Color(0x217B49));
        g.fillRect(0,0, width, height);

        butt.setLocation(400,200);
    }


    public void initComps(){
        butt = new JButton("Butt");
        butt.setPreferredSize(new Dimension(180,60));
        butt.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        butt.setFont(new Font("Tahoma", Font.PLAIN, 20));
        butt.addActionListener(e -> System.out.println("Butt"));

        this.add(butt);

        butt.setLocation(400,200);
    }


    public static void main(String[] args) throws IOException {

        funGUI panel = new funGUI();

        JFrame myFrame = new JFrame("SSM Launcher");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setVisible(true);
        myFrame.add(panel);
        myFrame.setResizable(false);
        myFrame.setSize(panel.getPreferredSize());
        myFrame.setLocation(300, 50);

        Timer t = new Timer(1000/60, timerRepaint -> myFrame.getComponent(0).repaint());

        t.start();

    }


}
