import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DrawnImageComponent extends JComponent {
    // Yes, our subclasses of JComponent used for drawing can have constructors with arguments!
    // We use this to choose what image to draw, if any. If the default constructor is used OR if the BufferedImage
    // is null, nothing is drawn for the image, and instead, text saying "No image to display" is visible.
    // Since drawing overwrites what was there before, we can just always draw the text and not worry about it being
    // visible, even if the image is very small. That's because we blow up the image to match the same size every time.
    // This means the aspect ratio doesn't appear to be preserved in the app, but the true output file will still have
    // the same resolution as the input.
    private final BufferedImage imageToDraw;

    public DrawnImageComponent() {
        imageToDraw = null;
    }

    public DrawnImageComponent(BufferedImage img){
        imageToDraw = img;
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawString("No image to display", 200, 300);
        g.drawImage(imageToDraw, 0, 0, ImageEditorDisplay.IMAGE_DIMENSIONS.width, ImageEditorDisplay.IMAGE_DIMENSIONS.height, this);
    }
}