package Wordle;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static Wordle.Wordle.*;

public class Letter extends JLabel {

    public static Color[] colors = {Color.WHITE, new Color(0x797C7E), new Color(0xC4B556), new Color(0x72AB62),
            Color.BLACK, Color.WHITE, new Color(0xD4D6DA), new Color(0xC1C2C7), new Color(0x4C4C4F), Color.WHITE};

    public static final Color[] lightMode = {Color.WHITE, new Color(0x797C7E), new Color(0xC4B556), new Color(0x72AB62),
            Color.BLACK, Color.WHITE, new Color(0xD4D6DA), new Color(0xC1C2C7), new Color(0x4C4C4F), Color.WHITE};

    public static final Color[] darkMode = {new Color(0x121213), new Color(0x3A3A3C), new Color(0xB0A039),
            new Color(0x5B8E4C), Color.WHITE, Color.WHITE, new Color(0x818384), new Color(0x3A3A3C),
            new Color(0x565758), new Color(0x121213)};

    public static final Color[] evilMode = {new Color(0x962525), new Color(0x3A3A3C), new Color(0xB0A039),
            new Color(0x5B8E4C), Color.WHITE, Color.WHITE, new Color(0x818384), new Color(0x3A3A3C),
            new Color(0x565758), new Color(0x491A1A)};



    public static final int BLANK = 0, GRAY = 1, YELLOW = 2, GREEN = 3, BLANK_TEXT = 4,
            COLORED_TEXT = 5, BLANK_KEYBOARD = 6, BLANK_OUTLINE = 7, BLANK_TEXT_OUTLINE = 8, BACKGROUND = 9;
    public static final int SIZE = 50;
    public static Font letterFont;


    private String letter;
    private int state;

    public Letter(String letter, int state) {
        super();

        setPreferredSize(new Dimension(SIZE, SIZE));
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        setAlignmentY(Component.CENTER_ALIGNMENT);
        setAlignmentX(Component.CENTER_ALIGNMENT);

        this.letter = letter;
        this.state = state;

        try {
            letterFont = Font.createFont(Font.TRUETYPE_FONT,
                    new File("Fun/src/main/java/Wordle/NeueHelveticaBQ-Bold.otf")).deriveFont(30f);
        } catch (FontFormatException | IOException e) {
            letterFont = new Font("ARIAL", Font.BOLD, 30);
            e.printStackTrace();
        }
    }


    @Override protected void paintComponent(Graphics graph) {

        Graphics2D g = (Graphics2D)graph;

        // Draw background
        g.setColor(colors[state]);
        g.fillRect(0,0,SIZE-1,SIZE-1);

        // Draw border
        g.setColor(state != BLANK ? new Color(0x0000000, true) :
                letter.equals(" ") ? colors[BLANK_OUTLINE] : colors[BLANK_TEXT_OUTLINE]);
        g.drawRect(0,0,SIZE-1,SIZE-1);

        g.setColor(state == BLANK ? colors[BLANK_TEXT] : colors[COLORED_TEXT]);
        g.setFont(letterFont);

        Rectangle2D letterBorder = g.getFontMetrics().getStringBounds(letter, g);
        int letterX =  (int)((SIZE - letterBorder.getWidth()) / 2);
        int letterY =  SIZE - (int)((SIZE - letterBorder.getHeight()) / 2) - 6;

        g.drawString(letter, letterX, letterY);

    }


    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public static void setTheme(int theme){
        switch(theme){
            case LIGHT -> colors = lightMode;
            case DARK -> colors = darkMode;
            case EVIL -> colors = evilMode;
        }
    }

    public static Letter[] convertToLetters(String s){

        Letter[] letters = new Letter[s.length()];

        for (int i = 0; i < s.length(); i++)
            letters[i] = new Letter(s.charAt(i)+"", BLANK);

        return letters;
    }
}
