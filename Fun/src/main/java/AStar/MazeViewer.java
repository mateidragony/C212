package AStar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

public class MazeViewer extends JPanel implements MouseListener {

    final int blockSize;

    int timesPressed = 0;
    boolean pressing;
    int[][] maze;
    List<Node> path;
    HashMap<Integer, Integer> distribution;

    public MazeViewer(){
        distribution = new HashMap<>();

        reset();

        blockSize = 700/maze.length;

        addMouseListener(this);
        setFocusable(true);
        setPreferredSize(new Dimension(maze[0].length*blockSize, maze.length*blockSize));
    }


    public void reset(){
        int num = 0;

        do {

            maze = generateRandomMaze(100, 100, .5);

            Node goal1 = new Node(maze[0].length - 1, maze.length - 1);
            Node goal2 = new Node(0, maze.length - 1);
            Node goal3 = new Node(maze[0].length - 1, 0);
            Node goal4 = new Node(0,0);

            AStar astar = new AStar(new Node(maze[0].length/2, maze.length/2), goal1, goal2, goal3, goal4);
            path = astar.runAlgorithm(maze);
            num++;
        } while (path == null);

        for(Node n : path){
            int dist = (int)Math.round(Math.abs(-n.x + n.y) / Math.sqrt(2));

            if(distribution.containsKey(dist))
                distribution.put(dist, distribution.get(dist)+1);
            else
                distribution.put(dist, 1);
        }

        timesPressed = 0;
    }

    @Override public void paintComponent(Graphics gOld){

        if(pressing)
            timesPressed+=3;

        Graphics2D g = (Graphics2D)gOld;

        for(int i=0; i< maze.length; i++){
            for(int j=0; j<maze[0].length; j++){

                if(i == maze.length/2 && j == maze[0].length/2)
                    g.setColor(Color.red);
                else if((i == maze.length-1 && j == maze[0].length - 1) || (i == 0 && j == maze[0].length - 1)
                            || (i == maze.length-1 && j == 0) || (i == 0 && j == 0))
                    g.setColor(Color.green);
                else if(maze[i][j] == 1)
                    g.setColor(new Color(0xFDD4D4));
                else
                    g.setColor(new Color(0x312618));

                g.fillRect(j*blockSize, i*blockSize, blockSize, blockSize);
            }
        }


        g.setColor(Color.blue);
        for (int i = 0; i < Math.min(timesPressed, path.size()); i++) {
            Node n = path.get(i);
            g.fillRect(n.x * blockSize, n.y * blockSize, blockSize, blockSize);
        }

        if(timesPressed > path.size())
            reset();

    }

    @Override public void mouseClicked(MouseEvent e){}


    public void mousePressed(MouseEvent e) {
        pressing = true;
    }
    public void mouseReleased(MouseEvent e) {
        pressing = true;
    }
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}


    public int[][] generateRandomMaze(int width, int height, double weight){
        int[][] newMaze = new int[height][width];

        for(int i=0; i<height; i++){
            for(int j=0; j<width; j++){

                if(Math.random() > weight)
                    newMaze[i][j] = 1;
                else
                    newMaze[i][j] = AStar.WALL;

            }
        }

        return newMaze;
    }


    public static void main(String[] args) {

        JFrame frame = new JFrame();
        MazeViewer mv = new MazeViewer();

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(mv);
        frame.pack();
        frame.setVisible(true);

        Timer t = new Timer(1000/60, e-> mv.repaint());
        t.start();

    }

}
