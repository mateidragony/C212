import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class AsciiImage {


    static final int MAX_PIX_PER_CHAR = 50;


    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JPanel mainPanel = new JPanel();
        JFrame frame = getFrame(mainPanel);

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                JPanel imageViewer = (JPanel)((JPanel)mainPanel.getComponent(0)).getComponent(0);
                ImageComp image = (ImageComp)imageViewer.getComponent(0);
                replaceImage(frame, imageViewer, image.getImage());
            }
        });

        frame.setVisible(true);
    }

    public static JFrame getFrame( JPanel mainPanel){
        JFrame frame = new JFrame("ASCII Image Viewer");
        frame.setSize(new Dimension(1200, 800));
        frame.setPreferredSize(new Dimension(1200, 800));

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JTabbedPane tabPane = new JTabbedPane();
        tabPane.setFont(new Font("Dialog", Font.PLAIN, 15));

        JSlider sizeSlider = new JSlider(1,MAX_PIX_PER_CHAR, MAX_PIX_PER_CHAR);
        JPanel leftPanel = getLeftPanel(frame, sizeSlider);

        JTextArea asciiImg = new JTextArea("No Image Yet");
        JPanel imagePanel = getImagePanel();
        imagePanel.add(asciiImg);

        mainPanel.add(leftPanel);
        mainPanel.add(Box.createHorizontalStrut(40));
        mainPanel.add(getRightPanel(asciiImg, (JPanel)leftPanel.getComponent(0), sizeSlider));

        tabPane.add("Editor Tab", new JScrollPane(mainPanel));
        tabPane.add("ASCII Image", new JScrollPane(imagePanel));

        frame.add(tabPane);

        frame.pack();

        return frame;
    }

    public static JPanel getLeftPanel(JFrame frame, JSlider sizeSlider){

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        ImageComp image = new ImageComp(new Dimension(frame.getWidth()/2, frame.getHeight()/2));
        JPanel leftImageViewer = new JPanel();
        leftImageViewer.add(image);
        leftPanel.add(leftImageViewer);

        //Size labels and slider
        JLabel widthLabel = new JLabel("No Image");
        JLabel heightLabel = new JLabel("No Image");
        JLabel sizeLabel = new JLabel("Image Dimension: No Image Selected");

        //file panel stuff
        JPanel filePanel = getFilePanel(frame, leftImageViewer, widthLabel, heightLabel, sizeSlider, sizeLabel);
        leftPanel.add(filePanel);

        //size panel stuff
        JPanel sizePanel = getSizePanel(leftImageViewer, widthLabel, heightLabel, sizeSlider, sizeLabel);
        leftPanel.add(sizePanel);


        return leftPanel;
    }
    public static JPanel getFilePanel(JFrame frame, JPanel leftImageViewer, JLabel widthLabel,
                                      JLabel heightLabel, JSlider sizeSlider, JLabel sizeLabel){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        JPanel filePanel = new JPanel();

        JLabel fileLabel = new JLabel("No file selected.");
        fileLabel.setFont(new Font("Dialog", Font.PLAIN, 23));
        fileLabel.setBorder(BorderFactory.createEmptyBorder(0,20,0,0));

        JButton selectFile = new JButton("Select File");
        selectFile.setFont(new Font("Dialog", Font.PLAIN, 23));
        selectFile.addActionListener(e -> {
            try{
                if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                    File file = fileChooser.getSelectedFile();
                    BufferedImage img = ImageIO.read(file);
                    replaceImage(frame, leftImageViewer, img);
                    fileLabel.setText(file.getName());

                    if(img != null) {
                        int width = img.getWidth() * sizeSlider.getValue() / MAX_PIX_PER_CHAR;
                        int height = img.getHeight() * sizeSlider.getValue() / MAX_PIX_PER_CHAR;
                        widthLabel.setText(width + " chars");
                        heightLabel.setText(height + " chars");

                        sizeLabel.setText("Image Dimensions: " + img.getWidth() + " X " + img.getHeight() + " pixels");
                    }
                    else {
                        sizeLabel.setText("Image Dimensions: No Image Selected");
                        widthLabel.setText("No Image");
                        heightLabel.setText("No Image");
                    }
                }
            } catch(IOException ex){
                ex.printStackTrace();
            }
        });

        filePanel.add(selectFile);
        filePanel.add(fileLabel);

        filePanel.setBorder(BorderFactory.createEmptyBorder(10,10,20,10));

        return filePanel;
    }
    public static JPanel getSizePanel(JPanel leftImageViewer, JLabel widthLabel,
                                      JLabel heightLabel, JSlider sizeSlider, JLabel sizeLabel){
        JPanel sizePanel = new JPanel();
        sizePanel.setLayout(new BoxLayout(sizePanel, BoxLayout.Y_AXIS));

        sizeLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        JPanel labelPanel = new JPanel();
        labelPanel.add(sizeLabel);
        sizePanel.add(labelPanel);

        Border border = BorderFactory.createEmptyBorder(10,5,10,5);
        TitledBorder titleBorder = BorderFactory.createTitledBorder(border, "ASCII Image Quality",
                TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION);
        titleBorder.setTitleFont(new Font("Dialog", Font.PLAIN, 15));
        sizeSlider.setBorder(titleBorder);

        sizeSlider.setMinorTickSpacing(1);
        sizeSlider.setPaintTicks(true);
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put( 1, new JLabel("Minimum") );
        labelTable.put( MAX_PIX_PER_CHAR/2, new JLabel("Medium") );
        labelTable.put( MAX_PIX_PER_CHAR, new JLabel("Maximum") );
        sizeSlider.setLabelTable( labelTable );
        sizeSlider.setPaintLabels(true);

        JPanel sizeLabels = new JPanel();
        widthLabel.setFont(new Font("Dialog", Font.PLAIN, 15));
        heightLabel.setFont(new Font("Dialog", Font.PLAIN, 15));
        sizeLabels.add(widthLabel);
        sizeLabels.add(new JLabel(" X "));
        sizeLabels.add(heightLabel);

        sizeSlider.addChangeListener(e -> {
            BufferedImage img = ((ImageComp)(leftImageViewer.getComponent(0))).getImage();

            if(img != null) {
                int width = img.getWidth() * sizeSlider.getValue() / MAX_PIX_PER_CHAR;
                int height = img.getHeight() * sizeSlider.getValue() / MAX_PIX_PER_CHAR;
                widthLabel.setText(width + " chars");
                heightLabel.setText(height + " chars");
            }
        });

        sizePanel.add(sizeSlider);
        sizePanel.add(sizeLabels);


        return sizePanel;
    }

    public static JPanel getRightPanel(JTextArea asciiImg, JPanel imgViewer, JSlider sizeSlider){

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        String[] fontFamilies = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        List<String> monoFonts1 = new ArrayList<>();

        FontRenderContext frc = new FontRenderContext(null, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT, RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT);
        for (String fontFamily : fontFamilies) {
            Font font = new Font(fontFamily, Font.PLAIN, 1);
            Rectangle2D spaceBounds = font.getStringBounds(" ", frc);
            Rectangle2D mBounds = font.getStringBounds("m", frc);
            if (spaceBounds.getWidth() == mBounds.getWidth()) {
                monoFonts1.add(fontFamily);
            }
        }
        String[] monoFontList = new String[monoFonts1.size()];
        JComboBox<String> fontSelect = new JComboBox<>(monoFonts1.toArray(monoFontList));

        JSlider fontSlider = new JSlider(1,50,10);

        JPanel fontPanel = getFontPanel(fontSlider, fontSelect);
        JPanel colorPanel = getColorPanel(asciiImg, imgViewer, sizeSlider, fontSlider, fontSelect);

        rightPanel.add(fontPanel);
        rightPanel.add(colorPanel);

        return rightPanel;
    }
    public static JPanel getFontPanel(JSlider fontSlider, JComboBox<String> fontSelect){
        JPanel fontPanel = new JPanel();
        //fontPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        fontPanel.setLayout(new BoxLayout(fontPanel, BoxLayout.Y_AXIS));
        final String[] selectedFont = new String[1];
        selectedFont[0] = "Dialog";

        JPanel fontSizePanel = new JPanel();

        Border border = BorderFactory.createEmptyBorder(10,10,20,10);
        TitledBorder titleBorder = BorderFactory.createTitledBorder(border, "Font Size",
                TitledBorder.LEFT, TitledBorder.TOP);
        titleBorder.setTitleFont(new Font("Dialog", Font.PLAIN, 15));
        fontSlider.setBorder(titleBorder);

        fontSlider.setMinorTickSpacing(1);
        fontSlider.setMajorTickSpacing(7);
        fontSlider.setPaintTicks(true);
        fontSlider.setPaintLabels(true);

        JLabel fontDisplay = new JLabel("A");
        fontDisplay.setFont(new Font(selectedFont[0], Font.PLAIN, 15));
        fontDisplay.setBorder(border);
        fontSlider.addChangeListener(e -> fontDisplay.setFont(new Font(selectedFont[0], Font.PLAIN, fontSlider.getValue())));


        fontSelect.setFont(new Font("Dialog", Font.PLAIN, 15));
        fontSelect.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true),
                BorderFactory.createEmptyBorder(5,5,5,5)));
        fontSelect.addActionListener(e -> {
            selectedFont[0] = (String)fontSelect.getSelectedItem();
            fontDisplay.setFont(new Font(selectedFont[0], Font.PLAIN, fontSlider.getValue()));
        });
        fontSelect.setMaximumSize(new Dimension(300, 50));
        fontSelect.setSelectedItem("Courier New");

        fontSizePanel.add(fontSlider);
        fontSizePanel.add(fontDisplay);

        fontPanel.add(fontSizePanel);
        fontPanel.add(fontSelect);

        return fontPanel;
    }
    public static JPanel getColorPanel(JTextArea asciiImg, JPanel imgViewer, JSlider sizeSlider, JSlider fontSize,
                                       JComboBox<String> fontSelect){
        JPanel colorPanel = new JPanel();
        //colorPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        colorPanel.setLayout(new BoxLayout(colorPanel, BoxLayout.Y_AXIS));
        colorPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        colorPanel.add(Box.createVerticalStrut(20));

        JCheckBox useColor = new JCheckBox("Use Image Color");
        useColor.setFont(new Font("Dialog", Font.PLAIN, 15));
        colorPanel.add(useColor);

        JCheckBox invertColors = new JCheckBox("Invert Colors");
        invertColors.setFont(new Font("Dialog", Font.PLAIN, 15));
        colorPanel.add(invertColors);

        JCheckBox highContrast = new JCheckBox("Use High Contrast");
        highContrast.setFont(new Font("Dialog", Font.PLAIN, 15));
        colorPanel.add(highContrast);

        colorPanel.add(Box.createVerticalStrut(20));

        JPanel panelBG = getBGPanel();
        panelBG.setAlignmentX(Component.LEFT_ALIGNMENT);
        colorPanel.add(panelBG);

        colorPanel.add(Box.createVerticalStrut(50));

        JButton generate = new JButton("Generate ASCII Art!");
        generate.setFont(new Font("Dialog", Font.BOLD, 25));
        generate.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        generate.addActionListener(e -> go(asciiImg, imgViewer, sizeSlider, fontSize, fontSelect));
        generate.setAlignmentX(Component.LEFT_ALIGNMENT);
        colorPanel.add(generate);

        return colorPanel;
    }
    public static JPanel getBGPanel(){
        JPanel panelBG = new JPanel();

        JButton setBG = new JButton("Set Background Color");
        setBG.setBorder(BorderFactory.createEmptyBorder(10,10,10,50));
        setBG.setFont(new Font("Dialog", Font.PLAIN, 15));
        JLabel color = new JLabel();
        color.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK),
                BorderFactory.createEmptyBorder(20,20,20,20)));
        color.setBackground(Color.WHITE);
        color.setOpaque(true);

        JFrame colorChooser = new JFrame("Color Picker");
        JColorChooser jCC = new JColorChooser(color.getBackground());
        JPanel hsvColor = new JPanel();
        jCC.getSelectionModel().addChangeListener(e -> color.setBackground(jCC.getColor()));

        for (AbstractColorChooserPanel panel : jCC.getChooserPanels()) {
            if ("HSV".equals(panel.getDisplayName())) {
                hsvColor.add(panel);
                panel.getComponent(0).setVisible(false);
            }
        }

        colorChooser.add(hsvColor);
        colorChooser.setResizable(false);
        colorChooser.setLocationByPlatform(true);
        colorChooser.pack();
        setBG.addActionListener(e -> {
            colorChooser.setVisible(true);
        });

        panelBG.add(setBG);
        panelBG.add(color);

        return panelBG;
    }


    public static JPanel getImagePanel(){
        return new JPanel();
    }


    public static void go(JTextArea asciiImg, JPanel ImageViewer, JSlider quality, JSlider fontSize,
                          JComboBox<String> fontSelect){

        BufferedImage img = ((ImageComp)ImageViewer.getComponent(0)).getImage();

        int pixPerCharW = MAX_PIX_PER_CHAR / quality.getValue();
        int pixPerCharH = (int)(pixPerCharW * 2.5);

        int[][] darkness = new int[img.getHeight()/pixPerCharH+1][img.getWidth()/pixPerCharW+1];

        for(int x=0; x<img.getWidth(); x+=pixPerCharW){
            for(int y=0; y<img.getHeight(); y+=pixPerCharH){

                int redSum = 0; int blueSum = 0; int greenSum = 0;
                for(int x1 = x; x1<x+pixPerCharW; x1++){
                    for(int y1=y; y1<y+pixPerCharH; y1++){
                        Color color = new Color(img.getRGB(Math.min(x1,img.getWidth()-1), Math.min(y1,img.getHeight()-1)));
                        redSum += color.getRed();
                        greenSum += color.getGreen();
                        blueSum += color.getBlue();
                    }
                }

                int avgDark = (redSum + blueSum + greenSum) / (3 * pixPerCharW * pixPerCharH);
                darkness[y/pixPerCharH][x/pixPerCharW] = avgDark;
            }
        }


        StringBuilder str = new StringBuilder();

        for(int[] darkRow : darkness){
            for(int dark : darkRow){
                str.append(getCharDarkness(dark));
            }
            str.append('\n');
        }

        asciiImg.setText(str.toString());
        asciiImg.setFont(new Font(fontSelect.getSelectedItem().toString(), Font.BOLD, fontSize.getValue()));
    }
    public static char getCharDarkness(int darkness){
        // ' ', '.', ';', '+', 'l', 'o', 'x', 'b', '&', 'M', '@'

        if(darkness > 232)
            return ' ';
        else if(darkness > 209)
            return '.';
        else if(darkness > 186)
            return ';';
        else if(darkness > 163)
            return '+';
        else if(darkness > 140)
            return 'l';
        else if(darkness > 117)
            return 'o';
        else if(darkness > 94)
            return 'x';
        else if(darkness > 71)
            return 'b';
        else if(darkness > 48)
            return '&';
        else
            return '@';
    }


    public static void replaceImage(JFrame frame, JPanel imageViewer, BufferedImage img){
        imageViewer.remove(0);

        Dimension size = new Dimension(frame.getWidth()/2, frame.getHeight()/2);

        imageViewer.add(new ImageComp(size, img));
        imageViewer.revalidate();
    }




    private static class ImageComp extends JComponent{
        private final BufferedImage imageToDraw;

        private final Dimension size;

        public BufferedImage getImage(){
            return imageToDraw;
        }

        public ImageComp(Dimension maxSize) {
            imageToDraw = null;
            setPreferredSize(maxSize);
            this.size = maxSize;
        }

        public ImageComp(Dimension maxSize, BufferedImage img){
            if(img != null) {
                imageToDraw = img;

                int h = maxSize.height;
                int w = h * img.getWidth() / img.getHeight();

                if (w > maxSize.width) {
                    w = maxSize.width;
                    h = w * img.getHeight() / img.getWidth();
                }
                size = new Dimension(w, h);

                setPreferredSize(size);
            } else {
                imageToDraw = null;
                setPreferredSize(maxSize);
                this.size = maxSize;
            }
        }

        public void paintComponent(Graphics g) {
            g.setColor(Color.BLACK);
            g.drawString("No image to display", size.width/2 - 50,size.height/2);
            g.drawImage(imageToDraw, 0, 0,size.width,size.height, this);
        }

    }

}