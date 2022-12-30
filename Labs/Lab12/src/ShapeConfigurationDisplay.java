import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class ShapeConfigurationDisplay {
    private static final Dimension SHAPE_DIMS = new Dimension(100, 100);
    public static void main(String[] args) {
        JFrame frame = getFrame();
        frame.setVisible(true);
    }

    private static JFrame getFrame() {
        // TODO the usual getFrame stuff. Window size 300x300

        JFrame frame = new JFrame("WOOOOO");
        frame.setPreferredSize(new Dimension(300,300));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        ShapePanel drawnShapePanel = new ShapePanel();

        ArrayList<JCheckBox> colorsGroup = new ArrayList<>();
        ButtonGroup shapesGroup = new ButtonGroup();

        JPanel colorsPanel = getColorsPanel(colorsGroup, drawnShapePanel);
        JPanel shapesPanel = getShapesPanel(shapesGroup, drawnShapePanel);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

        mainPanel.add(colorsPanel);
        mainPanel.add(shapesPanel);
        mainPanel.add(drawnShapePanel);

        frame.add(mainPanel);
        frame.pack();

        return frame;
    }

    private static JPanel getColorsPanel(ArrayList<JCheckBox> colorsGroup, ShapePanel drawnShapePanel) {
        // TODO there's only one thing to do in this method, the rest is done for you.
        JPanel colorsPanel = new JPanel();
        colorsPanel.add(new JLabel("Colors:"));

        JCheckBox redCheckBox = new JCheckBox("Red");

        JCheckBox blueCheckBox = new JCheckBox("Blue");
        class ColorCheckBoxEventListener implements ItemListener {
            public void itemStateChanged(ItemEvent e) {
                drawnShapePanel.setBlue(blueCheckBox.isSelected());
                drawnShapePanel.setRed(redCheckBox.isSelected());
                drawnShapePanel.repaint();
            }
        }
        redCheckBox.addItemListener(new ColorCheckBoxEventListener());
        blueCheckBox.addItemListener(new ColorCheckBoxEventListener());
        colorsGroup.add(redCheckBox);
        colorsGroup.add(blueCheckBox);

        colorsPanel.add(redCheckBox);
        colorsPanel.add(blueCheckBox);

        return colorsPanel;
    }

    private static JPanel getShapesPanel(ButtonGroup shapesGroup, ShapePanel drawnShapePanel) {
        JPanel shapesPanel = new JPanel();
        JRadioButton circleButton = new JRadioButton("Circle");
        JRadioButton squareButton = new JRadioButton("Square");

        class ShapeButtonEventListener implements ItemListener {
            public void itemStateChanged(ItemEvent e) {

                if(circleButton.isSelected())
                    drawnShapePanel.setShapeCode(ShapePanel.CIRCLE_CODE);
                else
                    drawnShapePanel.setShapeCode(ShapePanel.SQUARE_CODE);

                drawnShapePanel.repaint();
            }
        }


        circleButton.addItemListener(new ShapeButtonEventListener());
        squareButton.addItemListener(new ShapeButtonEventListener());
        shapesGroup.add(circleButton);
        shapesGroup.add(squareButton);
        circleButton.setSelected(true);
        shapesPanel.add(new JLabel("Shape:"));
        shapesPanel.add(circleButton);
        shapesPanel.add(squareButton);

        return shapesPanel;
    }



    private static class ShapePanel extends JPanel{

        public static final int CIRCLE_CODE = 1;
        public static final int SQUARE_CODE = 2;

        private int shapeCode;
        private boolean isRed, isBlue;

        public void setShapeCode(int shapeCode) {
            this.shapeCode = shapeCode;
        }

        public void setRed(boolean red) {
            isRed = red;
        }

        public void setBlue(boolean blue) {
            isBlue = blue;
        }

        @Override
        public void paintComponent(Graphics g){
            g.setColor(Color.white);
            g.fillRect(0,0,getWidth(),getHeight());

            Color color = new Color(0,0,0);
            if(isRed)
                color = new Color(255, color.getGreen(), color.getBlue());
            if(isBlue)
                color = new Color(color.getRed(), color.getGreen(), 255);

            g.setColor(color);
            if(shapeCode == CIRCLE_CODE){
                g.fillOval(getWidth()/2 - SHAPE_DIMS.width,0,SHAPE_DIMS.width,SHAPE_DIMS.height);
            } else {
                g.fillRect(getWidth()/2 - SHAPE_DIMS.width,0,SHAPE_DIMS.width,SHAPE_DIMS.height);
            }

        }

    }



}
