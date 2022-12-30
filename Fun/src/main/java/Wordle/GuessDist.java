package Wordle;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class GuessDist extends JPanel{


    private final HashMap<Integer, Integer> guessDistribution;

    public static void main(String... args) {

        JFrame frame = new JFrame("Guess Distribution");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        frame.add(new GuessDist(Integer.parseInt(args[0])));

        frame.pack();
        frame.setVisible(true);
    }


    public GuessDist(int wordSize){

        new Letter("",0);

        guessDistribution = new HashMap<>();
        // Get the data
        try{
            Scanner s = new Scanner(new File("Fun/src/main/java/Wordle/wordleData.txt"));
            while(s.hasNextLine()) {
                String[] data = s.nextLine().split(", ");

                if(data[2].equals(wordSize+"")) {
                    int numTry;

                    if(!data[3].equals("lost"))
                        numTry = Integer.parseInt(data[3]);
                    else
                        numTry = Wordle.NUM_ATTEMPTS+1;

                    if (guessDistribution.containsKey(numTry))
                        guessDistribution.put(numTry, guessDistribution.get(numTry) + 1);
                    else
                        guessDistribution.put(numTry, 1);
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        setPreferredSize(new Dimension(500, 500));
        setBackground(Letter.colors[Letter.BLANK]);
    }


    @Override protected void paintComponent(Graphics g) {

        g.setColor(Letter.colors[Letter.BLANK]);
        g.fillRect(0,0,getWidth(),getHeight());

        paintDist(g);
        paintStats(g);


    }

    public void paintStats(Graphics g){
        int numberXOffset = 40;

        //Draw Statistics Title
        g.setColor(Letter.colors[Letter.BLANK_TEXT]);
        g.setFont(Letter.letterFont.deriveFont(18f));
        Rectangle2D strBounds = g.getFontMetrics().getStringBounds("STATISTICS", g);
        g.drawString("STATISTICS", (int)(getWidth() - strBounds.getWidth())/2, 30);

        int numPlayed = guessDistribution.values().stream().reduce(0, Integer::sum);
        int percentWin = guessDistribution.entrySet().stream().filter(e -> e.getKey() <= Wordle.NUM_ATTEMPTS)
                .map(Map.Entry::getValue).reduce(0, Integer::sum) * 100 / numPlayed;
        String avgGuessNumber = Wordle.df.format(guessDistribution.entrySet().stream()
                .filter(e -> e.getKey() <= Wordle.NUM_ATTEMPTS)
                .map(e -> e.getKey() * e.getValue())
                .reduce(0, Integer::sum) * 1.0 / guessDistribution.entrySet().stream()
                .filter(e -> e.getKey() <= Wordle.NUM_ATTEMPTS)
                .map(Map.Entry::getValue).reduce(0, Integer::sum));

        // Start drawing the stats
        g.setFont(Letter.letterFont.deriveFont(25f));
        // Draw the percentage win number
        strBounds = g.getFontMetrics().getStringBounds(percentWin+"%", g);
        int percentX = (int)(getWidth()- strBounds.getWidth())/2;
        g.drawString(percentWin+"%", percentX, 70);
        // Draw the Average Guess Number
        g.drawString(avgGuessNumber+"", (int)(percentX + strBounds.getWidth() + numberXOffset), 70);
        //Draw the Num Times Played Number
        strBounds = g.getFontMetrics().getStringBounds(numPlayed+"", g);
        g.drawString(numPlayed+"", (int)(percentX - numberXOffset - strBounds.getWidth()), 70);

        //Start drawing the labels for the stats
        g.setFont(Letter.letterFont.deriveFont(11f));
        //Draw the percent win label
        strBounds = g.getFontMetrics().getStringBounds("Win %", g);
        percentX = (int)(getWidth()- strBounds.getWidth())/2;
        g.drawString("Win %", percentX, 85);
        //Draw average guess percent
        g.drawString("Average Number", (int)(percentX + strBounds.getWidth() + numberXOffset) - 12, 85);
        g.drawString("of Guesses", (int)(percentX + strBounds.getWidth() + numberXOffset) + 2, 97);
        //Draw num played label
        strBounds = g.getFontMetrics().getStringBounds("Played", g);
        g.drawString("Played", (int)(percentX - numberXOffset - strBounds.getWidth()) - 5, 85);


    }

    public void paintDist(Graphics g){

        int biggest = Objects.requireNonNull(guessDistribution.entrySet().stream()
                .max(Comparator.comparingInt(Map.Entry::getValue)).orElse(null)).getValue();

        int startX = 45;
        int minWidth = 30;
        int maxWidth = getWidth() - 2 * startX - minWidth;
        int startY = 145;
        int endY = 400;
        int yOffset = 5;
        int barHeight = (endY - startY) / (Wordle.NUM_ATTEMPTS+1) - yOffset;

        //Draw Guess Distribution title
        g.setColor(Letter.colors[Letter.BLANK_TEXT]);
        g.setFont(Letter.letterFont.deriveFont(18f));
        Rectangle2D strBounds = g.getFontMetrics().getStringBounds("GUESS DISTRIBUTION", g);
        g.drawString("GUESS DISTRIBUTION", (int)(getWidth() - strBounds.getWidth())/2, startY + 10);


        //Draw the bars
        for (int i = 1; i <= Wordle.NUM_ATTEMPTS+1; i++) {

            int numGuesses;
            if(guessDistribution.containsKey(i)) {
                numGuesses = guessDistribution.get(i);
                g.setColor(Letter.colors[Letter.GREEN]);
            } else {
                numGuesses = 0;
                g.setColor(Letter.colors[Letter.GRAY]);
            }

            int y = startY + i * (barHeight + yOffset);
            int width = (int)(maxWidth * (1.0 * numGuesses) / biggest) + minWidth;

            g.fillRect(startX, y, width, barHeight);

            g.setFont(Letter.letterFont.deriveFont(15f));
            g.setColor(Letter.colors[Letter.COLORED_TEXT]);
            g.drawString(numGuesses+"", startX + 10, y + 19);
            g.drawString(numGuesses+"", startX + width - 20, y + 19);

            g.setColor(Letter.colors[Letter.BLANK_TEXT]);
            if(i != Wordle.NUM_ATTEMPTS+1)
                g.drawString(i+"", startX - 16, y+19);
            else
                g.drawString("L", startX - 15, y+19);

        }
    }

}
