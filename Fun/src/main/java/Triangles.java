import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Triangles extends JPanel implements KeyListener{

    private ArrayList<Polygon> triangles;
    private final int width, height;
    private boolean keyPressed;

    public Triangles(){
        width = 500;
        height = 500;
        triangles = new ArrayList<>();

        setPreferredSize(new Dimension(500,500));
        addKeyListener(this);
        setFocusable(true);
    }

    @Override
    public void paintComponent(Graphics g){

        if(keyPressed) {

            Polygon triangle;
            do{
                triangle = generateTriangle((int) (Math.random() * 50) + 10);
            } while(!isTriangleValid(triangle));

            triangles.add(triangle);
            System.out.println(triangles.size());
        }

        g.setColor(Color.black);
        g.fillRect(0,0,width,height);

        g.setColor(new Color(0x2E7533));
        for(Polygon triangle : triangles){
            g.drawPolygon(triangle);
        }
    }

    public boolean isTriangleValid(Polygon triangle){
        int[] xPoints = {0, width, width, 0};
        int[] yPoints = {0, 0, height, height};
        Polygon bounds = new Polygon(xPoints, yPoints, 4);

        if(!(intesect(triangle,bounds)))
            return false;
        for(Polygon tri : triangles){
            if(intesect(tri, triangle))
                return false;
        }

        return true;
    }


    public Polygon generateTriangle(int size){
        Point center = new Point((int)(Math.random()*width), (int)(Math.random()*height));
        int[] xPoints = new int[3];
        int[] yPoints = new int[3];
        for(int i=0; i<3; i++){
            int xRand = (int)(Math.random() * size) - size/2;
            int yRand = (int)(Math.random() * size) - size/2;

            xPoints[i] = center.x + xRand;
            yPoints[i] = center.y + yRand;
        }

        return new Polygon(xPoints, yPoints, xPoints.length);
    }


    public boolean intesect(Polygon p1, Polygon p2){

        Area area1 = new Area(p1);
        Area area2 = new Area(p2);
        area1.intersect(area2);

        return !area1.isEmpty();
    }




    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyPressed(KeyEvent e) {
        keyPressed = true;
    }
    @Override
    public void keyReleased(KeyEvent e) {
        keyPressed = false;
    }


    public static void main(String[] args) {
        Triangles tri = new Triangles();
        JFrame myFrame = new JFrame();

        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setSize(tri.getPreferredSize());
        myFrame.add(tri);
        myFrame.setVisible(true);

        Timer t = new Timer(1000/60, e -> tri.repaint());
        t.start();
    }



}
