package Wordle;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;

import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarStyle;
import org.apache.commons.lang3.StringUtils;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

import static Wordle.Letter.*;

public class Wordle extends JPanel implements KeyListener {

    public static final int LIGHT = 0, DARK = 1, EVIL = 2;
    public static final int NUM_ATTEMPTS = 6, TIMER_DURATION = 10;
    public static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    public static final DecimalFormat df = new DecimalFormat("#.#");

    public static final String[] languageNames = {"English", "Français", "Español", "Čeština", "Svenska",
            "Hrvatski", "Dansk", "Nederlands", "Srpski", "Slovenský", "Slovenščina"};
    public static final String[] languages = {"english", "francais", "spanish", "czech", "swedish",
            "croatian", "danish", "dutch", "serbian", "slovak"};

    protected int WORD_SIZE = 5;

    private JPanel lettersPanel;
    private String wordColors;
    private Letter[] enteredRow;
    protected HashMap<Character, Integer> enteredLetters;
    protected int missingLettersTimer, notWordTimer, revealTimer;
    protected int theme, numTries;

    protected boolean wonGame, lostGame, gameOver, pack, saveGame, evil;

    protected List<String> dictionary, evilDictionary;
    protected String secretWord, language = languages[0];
    private Letter[][] letters;

    public Wordle() {
        super();

        reset();
        //JPanel Stuff
        setFocusable(true);
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setLayout(new OverlayLayout(this));

        this.addKeyListener(this);
    }

    public void reset() {
        pack = true;
        saveGame = true;
        numTries = 0;
        enteredLetters = new HashMap<>();
        enteredRow = null;
        gameOver = false;

        revealTimer = TIMER_DURATION * WORD_SIZE;

        //Init the secret word and dictionary
        if(language.equals("all"))
            dictionary = getDictionary(languages);
        else
            dictionary = getDictionary(language);

        evilDictionary = new ArrayList<>(dictionary);
        secretWord = dictionary.get((int) (Math.random() * dictionary.size())).toUpperCase();
        System.out.println(secretWord);

        //Init letters
        letters = new Letter[NUM_ATTEMPTS][WORD_SIZE];
        letters = Arrays.stream(letters).map(e -> Stream.generate(() -> new Letter(" ", Letter.BLANK))
                .limit(WORD_SIZE).toArray(size -> new Letter[size])).toArray(size -> new Letter[NUM_ATTEMPTS][WORD_SIZE]);

        this.removeAll();

        add(new AlertPanel(this));
        add(getLettersPanel());
    }

    public JPanel getLettersPanel() {
        lettersPanel = new JPanel();

        lettersPanel.setBackground(colors[BACKGROUND]);
        lettersPanel.setLayout(new GridLayout(NUM_ATTEMPTS, WORD_SIZE, 5, 5));
        lettersPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        Arrays.stream(letters).flatMap(Stream::of).forEach(lettersPanel::add);

        return lettersPanel;
    }


    @Override
    protected void paintComponent(Graphics g) {

        Letter.setTheme(theme);
        if(evil)
            Letter.setTheme(EVIL);

        lettersPanel.setBackground(colors[BACKGROUND]);

        if (enteredRow != null) {
            lostGame = Arrays.stream(letters).noneMatch(row -> row[0].getState() == Letter.BLANK); //You lost
            wonGame = Arrays.stream(enteredRow).filter(l -> l.getState() == Letter.GREEN).count() == WORD_SIZE; //You won
            gameOver = wonGame || lostGame;
        }

        g.setFont(Letter.letterFont);

        if (revealTimer < TIMER_DURATION * (WORD_SIZE)) {

            for (int i = WORD_SIZE - 1; i >= 0; i--) {

                char c = wordColors.charAt(i);
                int state = c == 'g' ? Letter.GREEN : c == 'y' ? Letter.YELLOW : Letter.GRAY;

                if (i == 0) {
                    enteredRow[0].setState(state);
                } else if (revealTimer / (i * TIMER_DURATION) >= 1) {
                    enteredRow[i].setState(state);
                    break;
                }
            }

            revealTimer++;
        }

    }

    public String checkRow(Letter[] row) {

        StringBuilder s = new StringBuilder();

        HashMap<Character, Integer> numOfLetter = new HashMap<>();
        for (int i = 0; i < secretWord.length(); i++) {
            char c = secretWord.charAt(i);

            if (c != row[i].getLetter().charAt(0)) {
                if (!numOfLetter.containsKey(c))
                    numOfLetter.put(c, 1);
                else
                    numOfLetter.put(c, numOfLetter.get(c) + 1);
            }
        }

        for (int i = 0; i < row.length; i++) {

            char c = row[i].getLetter().charAt(0);
            char secretC = secretWord.charAt(i);

            if (c == secretC) {
                s.append("g");
                enteredLetters.put(c, Letter.GREEN);
            } else if (secretWord.contains(c + "") && numOfLetter.containsKey(c) && numOfLetter.get(c) > 0) {
                s.append("y");
                numOfLetter.put(c, numOfLetter.get(c) - 1);

                if (!enteredLetters.containsKey(c) ||
                        (enteredLetters.containsKey(c) && enteredLetters.get(c) != Letter.GREEN)) //Don't overwrite green letter
                    enteredLetters.put(c, Letter.YELLOW);

            } else {
                s.append("r");

                if (!enteredLetters.containsKey(c) ||
                        (enteredLetters.containsKey(c) && enteredLetters.get(c) != Letter.GREEN)) //Don't overwrite green letter
                    enteredLetters.put(c, Letter.GRAY);
            }
        }

        return s.toString();
    }
    public String checkEvilRow(Letter[] row){
        String evil = getEvilColors(row);
        String entered = getEnteredString(row);

        for (int i=0; i<evil.length(); i++) {

            char c = evil.charAt(i);
            char enteredC = entered.charAt(i);

            if (c == 'g') {
                enteredLetters.put(enteredC, Letter.GREEN);
            } else if (c == 'y') {
                if (!enteredLetters.containsKey(enteredC) ||
                        (enteredLetters.containsKey(enteredC) && enteredLetters.get(enteredC) != Letter.GREEN)) //Don't overwrite green letter
                    enteredLetters.put(enteredC, Letter.YELLOW);
            } else {
                if (!enteredLetters.containsKey(enteredC) ||
                        (enteredLetters.containsKey(enteredC) && enteredLetters.get(enteredC) != Letter.GREEN)) //Don't overwrite green letter
                    enteredLetters.put(enteredC, Letter.GRAY);
            }
        }

        return evil;
    }


    public List<String> getDictionary(String... languages) {
        try {
            List<String> allWords = new ArrayList<>();

            if(languages.length > 1)
                language = "all";
            else
                language = languages[0];

            for (String language : languages) {

                Scanner s = new Scanner(new File("Fun/src/Languages/"+ language +".txt"));

                while (s.hasNextLine()) {
                    String str = s.nextLine();
                    str = StringUtils.stripAccents(str).toUpperCase();
                    allWords.add(str);
                }
            }

            return allWords.stream().filter(e -> e.length() == WORD_SIZE).toList();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String getEnteredString(Letter[] row) {
        StringBuilder s = new StringBuilder();

        for (Letter l : row) {
            s.append(l.getLetter());
        }

        return s.toString();
    }

    @Override public void keyTyped(KeyEvent e) {

        char c = e.getKeyChar();

        if(!gameOver && evil && c == '0')
            beatEvilWordle();

        if (!Character.isAlphabetic(c) && c != '\b' && c != '\n')
            return;

        //Handles player letter input only if the player should be allowed to type
        if (!gameOver && revealTimer == TIMER_DURATION * WORD_SIZE) {

            if (c == '\b') { // backspace
                Arrays.stream(letters).filter(row -> row[0].getState() == Letter.BLANK).findFirst()
                        .flatMap(letterRow -> Arrays.stream(letterRow).filter(letter -> !letter.getLetter().equals(" "))
                                .reduce((one, two) -> two)).ifPresent(letter -> letter.setLetter(" "));
            } else if (c == '\n') { // enter

                enteredRow = Arrays.stream(letters).filter(row -> row[0].getState() == Letter.BLANK)
                        .findFirst().orElse(null); //Find entered row


                assert enteredRow != null;
                if (Arrays.stream(enteredRow).anyMatch(l -> l.getLetter().equals(" "))) { //Missing letters
                    missingLettersTimer = 60;
                } else if (!dictionary.contains(getEnteredString(enteredRow).toUpperCase())) { //Not a word
                    notWordTimer = 60;
                } else { // It's a word, let's go!

                    numTries++;

                    if (enteredRow != null) {
                        revealTimer = 0;
                        if(evil) {
                            wordColors = checkEvilRow(enteredRow);
                            if(evilDictionary.size() < 10)
                                System.out.println(Arrays.toString(evilDictionary.toArray()));
                        }
                        else
                            wordColors = checkRow(enteredRow);
                    }
                }
            } else { // any valid letter
                Arrays.stream(letters).filter(row -> row[0].getState() == Letter.BLANK).findFirst() //Find the first empty row
                        .flatMap(letterRow -> Arrays.stream(letterRow).filter(letter -> letter.getLetter().equals(" "))
                                .findFirst()).ifPresent(letter -> letter.setLetter((c + "").toUpperCase()));
            }
        } else if (gameOver) { //Press enter to play again
            if (c == '\n') {
                reset();
            }
        }


    }
    @Override public void keyPressed(KeyEvent e) {
    }
    @Override public void keyReleased(KeyEvent e) {
    }


    //Evil >:)
    public String getEvilColors(Letter[] enteredRow) {

        HashMap<String, Integer> wordCombos = new HashMap<>();
        getWordCombos(new char[]{'g', 'y', 'r'}, WORD_SIZE, "", wordCombos);
        String entered = getEnteredString(enteredRow).toUpperCase();

        List<String> bestEvilDictionary = new ArrayList<>();

        for (String combo : wordCombos.keySet()) {

            List<String> newEvilDictionary = new ArrayList<>(evilDictionary);

            for (int i = 0; i < combo.length(); i++) {

                char enteredC = entered.charAt(i);
                char c = combo.charAt(i);
                int finalI = i;

                if (c == 'g')
                    newEvilDictionary = newEvilDictionary.stream().filter(e -> e.charAt(finalI) == enteredC).toList();
                else if (c == 'y') {
                    long numOfCharacter = entered.chars().filter(ch -> ch == enteredC).count();

                    newEvilDictionary = newEvilDictionary.stream().filter(e -> e.charAt(finalI) != enteredC)
                            .filter(e -> e.chars().filter(ch -> ch == enteredC).count() == numOfCharacter).toList();
                } else
                    newEvilDictionary = newEvilDictionary.stream().filter(e -> !e.contains(enteredC + "")).toList();

            }
            wordCombos.put(combo, newEvilDictionary.size());

            if(newEvilDictionary.size() > bestEvilDictionary.size())
                bestEvilDictionary = new ArrayList<>(newEvilDictionary);
        }

        //System.out.println(wordCombos);
        evilDictionary = new ArrayList<>(bestEvilDictionary);

        //System.out.println(evilDictionary.size());
        return wordCombos.entrySet().stream().max(Comparator.comparingInt(Map.Entry::getValue))
                .orElseThrow().getKey();
    }

    public void getWordCombos(char[] c, int n, String start, HashMap<String, Integer> wordCombos) {
        if (start.length() >= n) {
            wordCombos.put(start, 0);
        } else {
            for (char x : c) {
                getWordCombos(c, n, start + x, wordCombos);
            }
        }
    }


    public void beatEvilWordle(){
        List<String> evilDictionarySave = new ArrayList<>(evilDictionary);

        String bestWord = "";
        int bestSize = Integer.MAX_VALUE;

       ProgressBar pb = new ProgressBar("Progress Bar", evilDictionary.size());


        for(String s : evilDictionary){

            getEvilColors(Letter.convertToLetters(s));
            if(evilDictionary.size() < bestSize && evilDictionary.size() > 0){
                bestSize = evilDictionary.size();
                bestWord = s;
            }

            evilDictionary = new ArrayList<>(evilDictionarySave);

            pb.step();
        }

        pb.refresh();
        System.out.println(bestWord);

    }



}

