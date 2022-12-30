package Wordle;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;

import static Wordle.Letter.*;
import static Wordle.Wordle.*;


//Add you win
//Then work on evil
//Then add languages

public class WordleViewer {

    private static final char[] alphabet = {'Q','W','E','R','T','Y','U','I','O','P',
        'A','S','D','F','G','H','J','K','L','Z','X','C','V','B','N','M'};

    private static final JLabel[] letters = new JLabel[26];

    private static JPanel alphabetPanel1, alphabetPanel2, alphabetPanel3, wordlePanel;

    public static void main(String[] args) {

        FlatLightLaf.setup(); //setting the look and feel
        UIManager.put("TitlePane.foreground", Color.white);
        UIManager.put("RootPane.background", Color.black);
        JFrame.setDefaultLookAndFeelDecorated(true);

        JFrame frame = new JFrame("Super Mega Wordle");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setIconImage(new ImageIcon("Fun/src/main/java/Wordle/wordle.png").getImage());
        Wordle wordle = new Wordle();

        //JFrame.setDefaultLookAndFeelDecorated(true);

        JPanel mainPanel = getMainPanel(wordle);
        frame.add(mainPanel);
        frame.setJMenuBar(getMenuBar(wordle, frame));

        frame.pack();

        frame.setVisible(true);

        new Timer(1000/60, e -> {
            setKeyboardColors(wordle);
            mainPanel.repaint();
            resetBackgrounds();

            if(wordle.pack) {
                frame.pack();
                wordle.pack = false;
            }

        }).start();
    }


    public static JPanel getMainPanel(Wordle wordle){
        JPanel mainPanel = new JPanel();
        mainPanel.repaint();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        mainPanel.add(getWordlePanel(wordle));
        mainPanel.add(getAlphabetPanel());
        return mainPanel;
    }

    public static JPanel getAlphabetPanel(){
        JPanel alphabetPanel = new JPanel();

        alphabetPanel.setLayout(new BoxLayout(alphabetPanel, BoxLayout.Y_AXIS));

        for (int i = 0; i < alphabet.length; i++) {
            JLabel letter = new JLabel(alphabet[i]+"", SwingConstants.CENTER);
            letter.setFont(Letter.letterFont.deriveFont(17f));
            letter.setOpaque(true);
            letter.setPreferredSize(new Dimension(25,45));
            letters[i] = letter;
        }

        alphabetPanel1 = new JPanel();
        alphabetPanel1.setLayout(new FlowLayout(FlowLayout.CENTER, 10,5));
        alphabetPanel1.setBackground(colors[BACKGROUND]);
        for (int i=0; i<10; i++)
            alphabetPanel1.add(letters[i]);

        alphabetPanel2 = new JPanel();
        alphabetPanel2.setLayout(new FlowLayout(FlowLayout.CENTER, 10,5));
        alphabetPanel2.setBackground(colors[BACKGROUND]);
        for (int i=10; i<19; i++)
            alphabetPanel2.add(letters[i]);

        alphabetPanel3 = new JPanel();
        alphabetPanel3.setLayout(new FlowLayout(FlowLayout.CENTER, 10,5));
        alphabetPanel3.setBackground(colors[BACKGROUND]);
        for (int i=19; i<26; i++)
            alphabetPanel3.add(letters[i]);

        alphabetPanel.add(alphabetPanel1);
        alphabetPanel.add(alphabetPanel2);
        alphabetPanel.add(alphabetPanel3);

        return alphabetPanel;
    }

    public static JPanel getWordlePanel(Wordle wordle){

        wordlePanel = new JPanel();
        wordlePanel.add(new JSeparator());
        wordlePanel.add(wordle);
        wordlePanel.add(new JSeparator());

        wordlePanel.setBackground(colors[BACKGROUND]);

        return wordlePanel;
    }

    public static void setKeyboardColors(Wordle wordle){

        if(wordle.revealTimer == TIMER_DURATION * wordle.WORD_SIZE) {
            for (int i = 0; i < letters.length; i++) {
                if (wordle.enteredLetters.containsKey(alphabet[i])) {
                    switch (wordle.enteredLetters.get(alphabet[i])) {
                        case GRAY -> letters[i].setBackground(colors[GRAY]);
                        case GREEN -> letters[i].setBackground(colors[GREEN]);
                        case YELLOW -> letters[i].setBackground(colors[YELLOW]);
                    }
                    letters[i].setForeground(colors[COLORED_TEXT]);
                } else {
                    letters[i].setBackground(colors[BLANK_KEYBOARD]);
                    letters[i].setForeground(colors[BLANK_TEXT]);
                }

            }
        }
    }

    public static JMenuBar getMenuBar(Wordle wordle, JFrame frame){

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.BLACK);
        menuBar.setForeground(Color.white);
        menuBar.setOpaque(true);

        JMenu settings = new JMenu("Settings");

        JMenu wordSize = new JMenu("Word Size");

        JSlider sizeSlider = new JSlider(3,18, 5);
        sizeSlider.setMinorTickSpacing(1);
        sizeSlider.setMajorTickSpacing(5);
        sizeSlider.setPaintTicks(true);
        sizeSlider.setPaintLabels(true);
        sizeSlider.setSnapToTicks(true);
        sizeSlider.addChangeListener(e -> {
            wordle.WORD_SIZE = sizeSlider.getValue();
            wordle.reset();
            frame.pack();
        });
        wordSize.add(sizeSlider);

        settings.add(wordSize);


        settings.add(getLanguageMenu(wordle));
        settings.add(getThemeMenu(wordle));
        settings.add(getEvilMenu(wordle));


        menuBar.add(settings);

        return menuBar;
    }


    public static JMenu getLanguageMenu(Wordle wordle){
        JMenu language = new JMenu("Language");

        for (int i = 0; i < languages.length; i++) {
            int finalI = i;
            JMenuItem lang = new JMenuItem(languageNames[i]);
            lang.addActionListener(e -> {
                wordle.language = languages[finalI];
                wordle.reset();
            });
            language.add(lang);
        }

        JMenuItem all = new JMenuItem("All");
        all.addActionListener(e -> {
            if(!wordle.language.equals("all")){
                wordle.getDictionary(languages);
                wordle.reset();
            }
        });

        language.add(all);

        return language;
    }

    public static JMenu getThemeMenu(Wordle wordle){
        JMenu theme = new JMenu("Theme");
        JMenuItem lightMode = new JMenuItem("Light Mode");
        JMenuItem darkMode = new JMenuItem("Dark Mode");
        lightMode.addActionListener(e -> wordle.theme = LIGHT);
        darkMode.addActionListener(e -> wordle.theme = DARK);

        theme.add(lightMode);
        theme.add(darkMode);

        return theme;
    }

    public static JMenu getEvilMenu(Wordle wordle){
        JMenu evil = new JMenu("Evil?");
        JMenuItem evilButton = new JMenuItem("Evil >:)");
        JMenuItem notEvilButton = new JMenuItem("Regular");
        evilButton.addActionListener(e ->{
            wordle.evil = true;
            wordle.reset();
        });
        notEvilButton.addActionListener(e ->{
            wordle.evil = false;
            wordle.reset();
        });

        evil.add(evilButton);
        evil.add(notEvilButton);

        return evil;
    }

    public static void resetBackgrounds(){
        alphabetPanel1.setBackground(colors[BACKGROUND]);
        alphabetPanel2.setBackground(colors[BACKGROUND]);
        alphabetPanel3.setBackground(colors[BACKGROUND]);

        wordlePanel.setBackground(colors[BACKGROUND]);
    }

}
