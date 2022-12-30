import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ImageEditorDisplay {
    // Constants
    final static Dimension IMAGE_DIMENSIONS = new Dimension(500, 500);

    public static void main(String[] args) {
        JFrame frame = getFrame(); // keep the code for making the frame organized into methods
        frame.setVisible(true);
    }

    private static JFrame getFrame() {
        // Basic frame properties
        JFrame frame = new JFrame("Image Editor!");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1200, 600));
        // mainPanel is the overall panel, which contains 3 panels
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS)); // formats elements horizontally

        JTextField inputPathField = new JTextField("cake.ppm", 30); // default text and size
        JPanel rightImagePanel = new JPanel();
        JPanel leftImagePanel = new JPanel();

        // Modular panel pieces: left, middle, and right
        JPanel leftPanel = getLeftPanel(leftImagePanel, inputPathField, rightImagePanel);
        JPanel middlePanel = getMiddlePanel(rightImagePanel, inputPathField);
        JPanel rightPanel = getRightPanel(rightImagePanel, inputPathField, leftImagePanel);

        // Final mainPanel and frame preparations before main() displays it
        mainPanel.add(leftPanel);
        mainPanel.add(middlePanel);
        mainPanel.add(rightPanel);

        frame.add(mainPanel);
        frame.pack();

        return frame;
    }

    private static JPanel getLeftPanel(JPanel leftImagePanel, JTextField inputPathField, JPanel rightImagePanel) {
        // Left Panel: contains a subpanel for the image, a text field, and a button
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        // leftImagePanel is a panel within leftPanel which contains only the component that draws the image
        // when an image needs to change, swap it out. This is why its defined statically in the class
        // Add the component that draws the image, but no constructor args because it doesn't draw an image
        DrawnImageComponent leftImage = new DrawnImageComponent();
        leftImage.setPreferredSize(IMAGE_DIMENSIONS); // this ensures the image isn't too small to see
        leftImagePanel.add(leftImage);

        // Other elements
        JButton selectImage = new JButton("Select Image");
        class SelectImageHandler implements ActionListener {
            // Loads the image specified in the inputPathField and replaces the left image with it,
            // throwing an error if the image cannot be found. Clears the right image, too.
            public void actionPerformed(ActionEvent e) {
                BufferedImage newImage;
                try {
                    newImage = EditorHelperMethods.resolveImageFromString(inputPathField.getText());
                } catch (IOException ex) {
                    System.out.println("Error: image '" + inputPathField.getText() + "' could not be found: " + ex.getMessage());
                    return;
                }
                replaceImage(leftImagePanel, newImage);
                replaceImage(rightImagePanel, null);
            }
        }
        selectImage.addActionListener(new SelectImageHandler());

        // Last steps with the panel

        leftPanel.add(leftImagePanel);
        leftPanel.add(inputPathField);
        leftPanel.add(selectImage);

        return leftPanel;
    }

    private static JPanel getMiddlePanel(JPanel rightImagePanel, JTextField inputPathField) {
        // Middle panel has many buttons, one per transformation, and that's it
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));

        List<JButton> transformButtons = Arrays.asList(new JButton("Grayscale"), new JButton("Flip Horizontal"),
                new JButton("Negate Red"), new JButton("Negate Green"), new JButton("Negate Blue"),
                new JButton("Just the Reds"), new JButton("Just the Greens"), new JButton("Just the Blues"));

        for(int i=0; i<transformButtons.size(); i++){
            JButton button = transformButtons.get(i);

            int code = i+1;
            button.addActionListener(e -> {
                BufferedImage newImg;
                try {
                    newImg = EditorHelperMethods.transformImage(inputPathField.getText(), code);
                } catch (IOException ex) {
                    System.out.println("Error: image '" + inputPathField.getText() + "' could not be found: " + ex.getMessage());
                    ex.printStackTrace();
                    System.out.println("rah rah");
                    return;
                }
                replaceImage(rightImagePanel, newImg);

            });

            middlePanel.add(button);
        }

        return middlePanel;
    }

    private static JPanel getRightPanel(JPanel rightImagePanel, JTextField inputPathField, JPanel leftImagePanel) {
        // Similar to left panel, but has the save button and an extra button that reuses the current output
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        DrawnImageComponent rightImage = new DrawnImageComponent();
        rightImage.setPreferredSize(IMAGE_DIMENSIONS);
        rightImagePanel.add(rightImage);
        JTextField outputPathField = new JTextField(30);
        // TODO: create save and reuse buttons with appropriate text and event listeners. They are defined below

        JButton saveButton = new JButton("Save Image");
        JButton reuseButton = new JButton("Reuse Image");

        saveButton.addActionListener(e -> {
            if (outputPathField.getText().equals("")) {
                outputPathField.setText("out.ppm"); // default name for saving if one isn't entered
            } else if(!outputPathField.getText().endsWith(".ppm")){
                outputPathField.setText(outputPathField.getText()+".ppm");
            }
            try {
                EditorHelperMethods.saveTempImage(outputPathField.getText());
            } catch (IOException ex) {
                System.out.println("Error: image '" + outputPathField.getText() + "' could not be found: " + ex.getMessage());
            }
        });

        reuseButton.addActionListener(e -> {
            String fileName = outputPathField.getText();
            if(!fileName.endsWith(".ppm"))
                fileName = "out.ppm";

            inputPathField.setText(fileName);

            BufferedImage outImage;
            try {
                EditorHelperMethods.saveTempImage(fileName);
                outImage = EditorHelperMethods.resolveImageFromString(fileName);
            } catch (IOException ex) {
                System.out.println("Error: image '" + fileName + "' could not be found: " + ex.getMessage());
                return;
            }

            outputPathField.setText("");
            replaceImage(rightImagePanel, null);
            replaceImage(leftImagePanel, outImage);
        });

        rightPanel.add(rightImagePanel);
        rightPanel.add(outputPathField);
        rightPanel.add(saveButton);
        rightPanel.add(reuseButton);

        return rightPanel;
    }

    private static void replaceImage(JPanel imagePanel, BufferedImage newImage) {
        // This helper method takes in the imagePanel containing the image component to be modified, and the new image to display.
        // It then removes the component inside the corresponding panel, generates the new one based on newImage and adds it, then
        // redraws the panel so the changes apply visually
        imagePanel.remove(0);
        DrawnImageComponent newImageComponent = new DrawnImageComponent(newImage);
        newImageComponent.setPreferredSize(IMAGE_DIMENSIONS);
        imagePanel.add(newImageComponent);
        imagePanel.revalidate();
    }

    private static void performTransform(int transformCode, JPanel rightImagePanel, JTextField inputPathField) {
        // This helper method avoids duplicate code in the actionPerformed method bodies for each event handler.
        // It just needs to know the transform code we want to use, then will call the method in EditorHelperMethods
        // that is used to transform the image with that given transform code and the filename typed into the input field
        // Lastly, replace the right image with that new image.
        BufferedImage newImage;
        try {
            newImage = EditorHelperMethods.transformImage(inputPathField.getText(), transformCode);
        } catch (IOException ex) {
            System.out.println("Error: image '" + inputPathField.getText() + "' could not be found: " + ex.getMessage());
            return;
        }
        replaceImage(rightImagePanel, newImage);
    }
}