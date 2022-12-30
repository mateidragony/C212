package Wordle;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static Wordle.Wordle.LIGHT;
import static Wordle.Wordle.TIMER_DURATION;

public class AlertPanel extends JPanel {

    private final Wordle wordle;

    AlertPanel(Wordle wordle){
        this.wordle = wordle;
        setOpaque(false);
        setPreferredSize(new Dimension(100,100));
    }

    @Override
    protected void paintComponent(Graphics g) {

        g.setFont(Letter.letterFont.deriveFont(15f));

        if(wordle.notWordTimer > 0){

            drawMessage("Not in word list",0,0, g);

            wordle.notWordTimer--;
        }

        if(wordle.missingLettersTimer > 0){
            drawMessage("Not enough letters",0,0, g);

            wordle.missingLettersTimer--;
        }


        if(wordle.gameOver && wordle.revealTimer == TIMER_DURATION * wordle.WORD_SIZE){

            drawMessage("Game Over", 0,-15, g);
            drawMessage("Press Enter to play again",0, 16, g);

            if(wordle.lostGame && !wordle.wonGame) {
                if(wordle.evil) {
                    drawMessage("The word was: " + wordle.evilDictionary.get(0), 0, 47, g);
                }
                else
                    drawMessage("The word was: " + wordle.secretWord, 0, 47, g);
            }

            //Save Game
            if(!wordle.evil && wordle.saveGame) {
                if (wordle.wonGame) {
                    saveData("" + wordle.numTries);
                } else if (wordle.lostGame) {
                    saveData("lost");
                }
                GuessDist.main(wordle.WORD_SIZE+"");
            }

        }

    }

    public void drawMessage(String message, int xOffset, int yOffset, Graphics g){
        Rectangle2D letterBorder = g.getFontMetrics().getStringBounds(message, g);
        int letterX =  (int)((getWidth() - letterBorder.getWidth()) / 2) + xOffset;
        int letterY =  getHeight() - (int)((getHeight() - letterBorder.getHeight()) / 2) - 6 + yOffset;


        if(wordle.theme == LIGHT)
            g.setColor(Color.black);
        else
            g.setColor(Color.white);
        g.fillRoundRect(letterX - 8, (int)((getHeight() - letterBorder.getHeight()) / 2) - 8 + yOffset,
                (int)letterBorder.getWidth() + 16, (int)letterBorder.getHeight() + 16,
                8, 8);

        if(wordle.theme == LIGHT)
            g.setColor(Color.white);
        else
            g.setColor(Color.black);
        g.drawString(message, letterX,letterY);

    }


    public void saveData(String numTries){
        LocalDateTime now = LocalDateTime.now();

        String data = Wordle.dtf.format(now) + ", " + wordle.secretWord + ", "
                + wordle.WORD_SIZE + ", " + numTries + "\n";

        try {
            Files.write(Paths.get("Fun/src/main/java/Wordle/wordleData.txt"), data.getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }

        wordle.saveGame = false;
    }
}
