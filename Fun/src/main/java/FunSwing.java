import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class FunSwing {

    private static JPanel optionsPanel;
    private static ImageViewer imageViewer;
    private static final Dimension size = new Dimension(600,400);

    private static JPanel colorsPanel;
    private static JPanel choicePanel;

    private static boolean[] shouldDraw = new boolean[3];

    private static Color color = new Color(100,100,100);

    public static void initOptionsPanel(){
        optionsPanel = new JPanel();
        optionsPanel.setPreferredSize(size);

        optionsPanel.setLayout(new BorderLayout());

        initColorsPanel();
        initChoicePanel();

        optionsPanel.add(colorsPanel, BorderLayout.CENTER);
        optionsPanel.add(choicePanel, BorderLayout.EAST);
    }
    public static void initImageViewer(){
        imageViewer = new ImageViewer();
        imageViewer.setPreferredSize(size);
    }


    public static void initColorsPanel(){
        colorsPanel = new JPanel(new GridLayout(3,2));

        JLabel redLabel = new JLabel("Red");
        initColorsSliders(redLabel);

        JLabel greenLabel = new JLabel("Green");
        initColorsSliders(greenLabel);

        JLabel blueLabel = new JLabel("Blue");
        initColorsSliders(blueLabel);


        resetColorBorder();
    }
    public static void initColorsSliders(JLabel label){
        label.setFont(new Font("Dialog", Font.BOLD, 15));
        label.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        colorsPanel.add(label);
        JSlider slider = new JSlider(0, 255, 100);
        slider.addChangeListener(e -> resetColorBorder());
        colorsPanel.add(slider);
    }
    public static void resetColorBorder(){

        JSlider redSlider = (JSlider)colorsPanel.getComponent(1);
        JSlider greenSlider = (JSlider)colorsPanel.getComponent(3);
        JSlider blueSlider = (JSlider)colorsPanel.getComponent(5);

        color = new Color(redSlider.getValue(), greenSlider.getValue(), blueSlider.getValue());

        MatteBorder colorBorder = new MatteBorder(5,5,5,5,color);
        TitledBorder titleBorder = new TitledBorder("Color");
        titleBorder.setTitleFont(new Font("Dialog", Font.BOLD, 20));


        colorsPanel.setBorder(BorderFactory.createCompoundBorder(titleBorder,colorBorder));
    }

    public static void initChoicePanel(){

        choicePanel = new JPanel();
        choicePanel.setLayout(new GridLayout(3,2));

        JCheckBoxMenuItem rectangle = new JCheckBoxMenuItem("Draw Rectangle");
        rectangle.addActionListener(e -> shouldDraw[0] = !shouldDraw[0]);
        choicePanel.add(rectangle);

        JCheckBoxMenuItem circle = new JCheckBoxMenuItem("Draw Circle");
        circle.addActionListener(e -> shouldDraw[1] = !shouldDraw[1]);
        choicePanel.add(circle);

        choicePanel.add(new JLabel("YO1"));
        choicePanel.add(new JLabel("YO2"));

        choicePanel.add(new JLabel("YO3"));
        choicePanel.add(new JLabel("YO4"));


        TitledBorder titleBorder = new TitledBorder("What to Draw?");
        titleBorder.setTitleFont(new Font("Dialog", Font.BOLD, 20));
        choicePanel.setBorder(titleBorder);

    }




    public static void main(String[] args) {

        JFrame frame = new JFrame("Frame");

        initOptionsPanel();
        initImageViewer();

        JTabbedPane tabPane = new JTabbedPane();
        tabPane.add("Image Options", optionsPanel);
        tabPane.add("Image", imageViewer);
        tabPane.addChangeListener(e -> imageViewer.repaint());

        frame.add(tabPane);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();

    }

    private static class ImageViewer extends JPanel{

        @Override
        public void paintComponent(Graphics g){

            g.setColor(color);
            if(shouldDraw[0]){
                g.fillRect(getWidth()/4, getHeight()/4, getWidth()/5, getHeight()/10);
            }
            if(shouldDraw[1]){
                g.fillOval(3*getWidth()/4, getHeight()/2, getWidth()/8, getWidth()/8);
            }

        }

    }



}
