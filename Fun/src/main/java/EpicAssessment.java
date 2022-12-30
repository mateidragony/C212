import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class EpicAssessment {


    public static void main(String[] args) throws IOException {

        JFileChooser chooser = new JFileChooser();
        if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            BufferedImage img = ImageIO.read(chooser.getSelectedFile());
            JFrame myFrame = new JFrame();
            myFrame.setIconImage(img);
            myFrame.setVisible(true);
        }


        //transpose("RACE CAR", "CAR RACE");
    }


    public static void add(ArrayList<String> arr){
        arr.add("CACA");
        arr.set(1,"ONE!!!");
    }

    public static void transpose(String s1, String s2){

        char[] s1Chars = s1.toCharArray();

        for(int i=0; i<s1.length(); i++){

            char s2Char = s2.charAt(i);

            while(s2Char != s1.charAt(i)){
                System.out.println(s1);
                int index = s1.substring(i).indexOf(s2Char)+i;

                s1Chars[index] = s1.charAt(index-1);
                s1Chars[index-1] = s1.charAt(index);
                s1 = new String(s1Chars);
            }
        }
        System.out.println(s1);

    }

}
