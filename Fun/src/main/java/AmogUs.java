import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;


public class AmogUs extends JPanel implements KeyListener {

    int x, y;
    Image bg;
    boolean left, right, up, down;
    static final int width = 1100, height = 700;
    static final double imgRatio = 2.235;
    static final int imageSize = 1700;


    public AmogUs() {

        this.setPreferredSize(new Dimension(width, height));
        this.setLocation(80, 80);
        this.setVisible(true);
        this.setFocusable(true);
        this.addKeyListener(this);

        x = 1900;
        y = 850;

        try {
            bg = ImageIO.read(new File("C:\\Users\\Matei Cloteaux\\Downloads\\Images\\world.jpg"));
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }


    //3800 x 1700
    public void paintComponent(Graphics g) {

        g.setColor(Color.white);
        g.fillRect(0,0,width,height);

        int imgX = -x;
        int imgY = -y;

        imgX = Math.max(-imgX, 0);
        imgX = Math.min(-imgX, (int)(imageSize * imgRatio));
        imgX = Math.max(imgX,  -(int) (imageSize * imgRatio) + width);

        imgY = Math.max(-imgY, 0);
        imgY = Math.min(-imgY, imageSize);
        imgY = Math.max(imgY,  -imageSize + height);


        g.drawImage(bg, imgX, imgY, (int) (imageSize * imgRatio), imageSize, this);

        handlePlayerMovement();

        g.setColor(Color.BLACK);

        int drawPlayerX = width / 2 - 10;
        int drawPlayerY = height / 2 - 60;

        drawPlayerX = x < 0 ? x + drawPlayerX : drawPlayerX;
        drawPlayerX = x > imageSize * imgRatio - width ? drawPlayerX + (int)(x-imageSize*imgRatio+width) : drawPlayerX;

        drawPlayerY = y < 0 ? y + drawPlayerY : drawPlayerY;
        drawPlayerY = y > imageSize - height ? drawPlayerY + y-imageSize + height : drawPlayerY;


        g.fillRect(drawPlayerX, drawPlayerY, 10, 10);

        g.drawString("X "+x+"     ImgX: "+imgX,100,50);
        g.drawString("Y "+y,100,100);
    }

    public void handlePlayerMovement(){
        if (up)
            y -= 8;
        if (down)
            y += 8;
        if (left)
            x -= 8;
        if (right)
            x += 8;

        if(x < -width/2 + 10)
            x = -width/2 + 10;
        if(x > imageSize * imgRatio - width/2.0 )
            x = (int)(imageSize * imgRatio) - width/2;

        if(y < -height/2 + 60)
            y = -height/2 + 60;
        if(y > imageSize - height/2.0 + 20)
            y = imageSize - height/2 + 20;

    }


    public static JMenuBar generateMenuBar() {
        JMenuBar jmb = new JMenuBar();

        JMenuItem exit = new JMenuItem();
        exit.setIcon(new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                g.setColor(Color.red);
                g.fillRect(x, y, getIconWidth(), getIconHeight());
                g.setColor(Color.black);
                g.drawString("X", x + 15, y + 13);
            }

            @Override
            public int getIconWidth() {
                return 40;
            }

            @Override
            public int getIconHeight() {
                return 20;
            }
        });
        exit.addActionListener(e -> System.exit(0));
        jmb.add(exit);


        return jmb;
    }


    public static void main(String[] args) {

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int frameX = screen.width / 2 - width / 2;
        int frameY = screen.height / 2 - height / 2 - 100;

        JFrame myFrame = new JFrame("FRAME!!!!!!");
        AmogUs au = new AmogUs();
        ImageIcon img = new ImageIcon("C:\\Users\\Matei Cloteaux\\Downloads\\Images\\cube.png");

        myFrame.setLocation(frameX, frameY);
        myFrame.add(au);
        myFrame.setUndecorated(true);
        myFrame.setVisible(true);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setJMenuBar(generateMenuBar());
        myFrame.setSize(au.getPreferredSize());
        myFrame.setIconImage(img.getImage());


        Timer t = new Timer(1000 / 60, e -> au.repaint());
        t.start();
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if ((key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A))
            left = true;
        if ((key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D))
            right = true;

        if ((key == KeyEvent.VK_UP || key == KeyEvent.VK_W))
            up = true;
        if ((key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S))
            down = true;
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if ((key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A))
            left = false;
        if ((key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D))
            right = false;

        if ((key == KeyEvent.VK_UP || key == KeyEvent.VK_W))
            up = false;
        if ((key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S))
            down = false;
    }
}
