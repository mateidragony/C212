
import org.apache.commons.math3.complex.Complex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;

public class Mandelbrot extends JPanel implements MouseListener, MouseMotionListener {

    BufferedImage img;
    BufferedImage prevImg;

    Thread drawer;
    ImageRunner imageRunner;

    final int size = 700;
    int MAX_PERMUTATIONS = 2048;
    final double escapeCondition = 2;
    final int[] palette = createGradient();

    double startX = -2.5;
    double startY = -2;
    double maxX = 1.5;
    double maxY = 2;

    int mouseX, mouseY;
    Point prevClick;
    boolean zoomingIn = false;


    public Mandelbrot(){

        this.setPreferredSize(new Dimension(size,size));
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.setFocusable(true);

        imageRunner = new ImageRunner();
        drawer = new Thread(imageRunner);
        drawer.start();

        try{
            Thread.sleep(100);
        } catch (InterruptedException ex){
            ex.printStackTrace();
        }

        img = imageRunner.getImg();
        prevClick = new Point(0,0);
    }

    public BufferedImage getImg(){
        return img;
    }

    public void zoom(double scale){
        double xSize = maxX - startX;
        double ySize = maxY - startY;

        double midX = startX + ((mouseX / (size * 1.0)) * (maxX - startX));
        double midY = startY + ((mouseY / (size * 1.0)) * (maxY - startY));


        if(scale < 1) {
            startX = startX + mouseX / (2.0 * size) * xSize;
            startY = startY + mouseY / (2.0 * size) * ySize;

            maxX = startX + xSize * scale;
            maxY = startY + ySize * scale;
        }

        else {
            startX = midX - (xSize/2) * scale;
            startY = midY - (ySize/2) * scale;

            maxX = midX + (xSize/2) * scale;
            maxY = midY + (ySize/2) * scale;
        }

        System.out.println("X: "+xSize+" , Y: "+ySize);
    }
    public int[] createGradient(){

        int[] colorRGBs = new int[2048];

        double[] positions = {0.0,0.16,0.42,0.6425,0.8575,1};
        Color[] colors = {new Color(0,7,100),new Color(32,107,203),
                new Color(237,255,255),new Color(255,170,0),new Color(0,2,0),
                new Color(0,7,100)};

        BufferedImage gradient = new BufferedImage(2048, 1, BufferedImage.TYPE_INT_RGB);

        Graphics2D g = gradient.createGraphics();

        for(int i=0; i<positions.length-1; i++) {
            GradientPaint gp = new GradientPaint((float)((positions[i]) * 1500),1,colors[i],
                    (float)((positions[i+1]) * 1500),1,colors[i+1]);
            g.setPaint(gp);
            g.fillRect((int)(positions[i] * 1500), 0, (int)((positions[i+1]-positions[i])* 1500), 1);
        }

        for(int i=0; i<gradient.getWidth(); i++){
            colorRGBs[i] = gradient.getRGB(i,0);
        }

        return colorRGBs;
    }

    public void paintComponent(Graphics g){


        if(imageRunner.progress() == size*size || !zoomingIn) {
            g.drawImage(img, 0, 0, this);
            zoomingIn = false;
        } else{
            g.fillRect(0,0,size,size);

            int zoomSize = imageRunner.progress()/size/2;

            int imgX = (int)(- zoomSize * (prevClick.x * 1.0 / size));
            int imgY = (int)(- zoomSize * (prevClick.y * 1.0 / size));

            g.drawImage(prevImg, imgX, imgY, size+zoomSize,size+zoomSize, this);
        }
    }

    public void mouseClicked(MouseEvent e) {


        prevImg = img;
        prevClick = new Point(mouseX, mouseY);

        if (e.getButton() == MouseEvent.BUTTON3){
            zoom(2);
            //MAX_PERMUTATIONS /= 2;
        }
        else {
            zoom(0.5);
            zoomingIn = true;
            //MAX_PERMUTATIONS *= 2;
        }

        imageRunner = new ImageRunner();
        drawer = new Thread(imageRunner);
        drawer.start();

        img = imageRunner.getImg();
    }
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseDragged(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }


    private class ImageRunner implements Runnable{

        private final BufferedImage img;
        private int progress;

        public ImageRunner(){
            img = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        }

        public BufferedImage getImg(){
            return img;
        }
        public int progress(){
            return progress;
        }


        public void determinePixel(int x, int y){
            double xPos = startX + ((x / (size * 1.0)) * (maxX - startX));
            double yPos = startY + ((y / (size * 1.0)) * (maxY - startY));

            double[] complex = {xPos,yPos};
            double[] initialComplex = {xPos,yPos};

            int iterations = 0;
            while (Math.pow(complex[0]*complex[0] + complex[1]*complex[1], 0.5) < escapeCondition && iterations < MAX_PERMUTATIONS - 1) {

                double temp = complex[0]*complex[0] - complex[1]*complex[1] + initialComplex[0];
                complex[1] = complex[0]*complex[1] + complex[1]*complex[0] + initialComplex[1];
                complex[0] = temp;

                iterations++;
            }


            int index = (int)((iterations * 1.0) / MAX_PERMUTATIONS * 2048);
            int colorI = (int)(Math.sqrt(index + 10) * 256) % palette.length;


            int color = palette[colorI];

            if(iterations < MAX_PERMUTATIONS / 20)
                color = palette[Math.min(iterations*15, 2047)];

            //color = Color.HSBtoRGB((iterations % 100)/100f - .3f,1,1);

            if (iterations == MAX_PERMUTATIONS - 1)
                color = 0;

            img.setRGB(x, y, color);

        }

        public void generateImage() {
            progress = 0;
            for (int x = size/2; x < size; x++) {
                for (int y = 0; y < size; y++) {

                    determinePixel(x,y);
                    determinePixel(size-x,y);
                    progress += 2;
                }
            }
            System.out.println("prog: " + progress);
        }


        public void run() {
            generateImage();
        }
    }


    public static void main(String[] args) {
        Mandelbrot m = new Mandelbrot();

        JFrame myFrame = new JFrame("MANDELBROT");
        myFrame.add(m);
        myFrame.pack();
        myFrame.setVisible(true);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setIconImage(m.getImg());

        Timer t = new Timer(1000/60, e -> m.repaint());
        t.start();
    }

}


//    public Color[] generatePalette(){
//        Color[] palette = new Color[(int)MAX_PERMUTATIONS];
//
//        for(int i=0; i<MAX_PERMUTATIONS; i++){
//
//            //palette[i] = Color.getHSBColor((1-(i/(float)(MAX_PERMUTATIONS)))-.3f,1,1);
//            double hue = (i / MAX_PERMUTATIONS) - .3;
//            palette[i] = Color.getHSBColor((float)hue, 1, 1);
//
//            //(1-(i/(float)(MAX_PERMUTATIONS)))-.3f
//            //(float)(1-(Math.pow(1.01,-(i+1))))
//
//        }
//
//        return palette;
//    }