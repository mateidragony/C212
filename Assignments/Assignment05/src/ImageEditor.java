/**
 *  Author: Matei Cloteaux
 *  Program Description:
 *  PPM Image editor/viewer
 *  Step-by-Step:
 *  First ask user for a valid input file
 *  Read that file and store the RGB values of image in a 3d int array
 *  Display the image in a JFrame
 *  Then, ask the user to enter a valid output location
 *  Then, ask the user what kind of edit they want to make to the image
 *  Go through the array and make the correct changes to the image
 *  Display the new image
 *  Then ask the user if they want to make another edit, use a new input, or use
 *  a new output
 *  Keep repeating the previous steps until user exits program
 */


import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.List;


public class ImageEditor {

    static int[][][] pixels;
    static int rows;
    static int cols;
    static int maxVal;
    static String[] header;

    public static void main(String[] args) throws FileNotFoundException{

        Scanner s = new Scanner(System.in);
        System.out.println("Portable Pixmap (PPM) Image Editor!");
        System.out.println();

        boolean newInput = true;
        boolean newOutput = true;
        boolean doSomething = true;

        String outFileName = "";

        outer:
        while(true) {
            //get input file
            if(newInput)
                readInputFile(s);

            //get output file
            if(newOutput) {
                System.out.println("Enter name of output file: ");
                String name = s.nextLine();
                while(name.isEmpty())
                    name = s.nextLine();

                outFileName = "Assignments/Assignment05/sampleout/" + name;
            }


            //get user input
            if(doSomething) {
                int choice = getUserInput(s);

                switch (choice) {
                    case 0 -> {break outer;}
                    case 1 -> grayScale(pixels);
                    case 2 -> flipHorizontal(pixels);
                    case 3 -> negateRed(pixels, maxVal);
                    case 4 -> negateGreen(pixels, maxVal);
                    case 5 -> negateBlue(pixels, maxVal);
                    case 6 -> {
                        flattenBlue(pixels);
                        flattenGreen(pixels);
                    }
                    case 7 -> {
                        flattenBlue(pixels);
                        flattenRed(pixels);
                    }
                    case 8 -> {
                        flattenRed(pixels);
                        flattenGreen(pixels);
                    }
                    default -> System.out.println("Bruh what?");
                }
            }

            displayImage(pixels, outFileName);
            writeOutput(header, pixels, outFileName);

            System.out.println("Do you want to do more operations (y or n): ");
            doSomething = s.next().equalsIgnoreCase("y");
            //if they don't want to do something new, just exit the program
            if(!doSomething)
                break;

            System.out.println("Do you want to change input file (y or n): ");
            newInput = s.next().equalsIgnoreCase("y");
            System.out.println("Do you want to change output file (y or n): ");
            newOutput = s.next().equalsIgnoreCase("y");
        }
        System.out.println();
        System.out.println("Goodbye!");
        System.exit(0);
    }


    public static Scanner getInputScanner(Scanner keyBoard){

        Scanner s;
        while(true) {
            System.out.println("Enter name of input file: ");

            String name = keyBoard.nextLine();
            while(name.isEmpty())
                name = keyBoard.nextLine();

            String inFileName = "Assignments/Assignment05/sampleinputs/" + name;

            try {
                s = new Scanner(new File(inFileName));
                break;
            } catch(FileNotFoundException ex){
                System.out.println(inFileName + " not found. Enter a valid file name.");
            }
        }
        return s;
    }
    public static void readInputFile(Scanner keyBoard){
        Scanner fileReader = getInputScanner(keyBoard);
        header = readHeader(fileReader);

        rows = Integer.parseInt(header[1].split(" ")[1].trim());
        cols = Integer.parseInt(header[1].split(" ")[0].trim());
        maxVal = Integer.parseInt(header[2].trim());

        pixels = new int[rows][cols][3];
        readImage(pixels, fileReader);
        displayImage(pixels, "Input");
    }


    public static int getUserInput(Scanner keyBoard){
        String choices = """
                Here are your choices
                [0] exit
                [1] convert to greyscale
                [2] flip horizontally
                [3] negative of red
                [4] negative of green
                [5] negative of blue
                [6] just the reds
                [7] just the greens
                [8] just the blues""";

        List<String> possibleChoices = Arrays.asList("0","1","2","3","4","5","6","7","8");

        System.out.println(choices);
        System.out.println("Enter choice: ");
        String input = keyBoard.next();

        while(!possibleChoices.contains(input)){
            System.out.println("Invalid input");
            System.out.print(choices);
            System.out.println("Enter choice: ");
            input = keyBoard.next();
        }

        return Integer.parseInt(input);
    }


    //Reads the first three lines of the file
    public static String[] readHeader(Scanner fileReader){

        String[] header = new String[3];

        for(int i=0; i<3; i++){
            header[i] = fileReader.nextLine();
        }

        return header;

    }
    //Reads the rest of the file (the guts)
    public static void readImage(int[][][] pixels, Scanner fileReader){

        for(int r=0; r<pixels.length; r++){

            for(int c=0; c<pixels[0].length; c++){

                for(int pix=0; pix<pixels[0][0].length; pix++){
                    pixels[r][c][pix] = Integer.parseInt(fileReader.next().trim());
                }

            }

        }

    }

    public static void writeOutput(String[] header, int[][][] pixels, String outName) throws FileNotFoundException{

        PrintWriter writer = new PrintWriter(outName);

        for(String s : header){
            writer.println(s.trim());
        }

        for (int[][] pixel : pixels) {

            for (int c = 0; c < pixels[0].length; c++) {

                for (int pix = 0; pix < pixels[0][0].length; pix++) {

                    writer.print(pixel[c][pix] + " ");

                }
                writer.print("  ");

            }
            writer.println();
        }
        writer.close();
    }

    public static void negateRed(int[][][] pixels, int maxValue){

        for (int[][] pixel : pixels) {

            for (int c = 0; c < pixels[0].length; c++) {

                pixel[c][0] = maxValue - pixel[c][0];
            }
        }

    }
    public static void negateGreen(int[][][] pixels, int maxValue) {

        for (int[][] pixel : pixels) {

            for (int c = 0; c < pixels[0].length; c++) {

                pixel[c][1] = maxValue - pixel[c][1];
            }
        }

    }
    public static void negateBlue(int[][][] pixels, int maxValue){

        for (int[][] pixel : pixels) {

            for (int c = 0; c < pixels[0].length; c++) {

                pixel[c][2] = maxValue - pixel[c][2];
            }
        }

    }

    public static void grayScale(int[][][] pixels){
        for (int[][] pixel : pixels) {

            for (int c = 0; c < pixels[0].length; c++) {

                int sum = 0;
                for(int i=0; i<pixels[0][0].length; i++){
                    sum += pixel[c][i];
                }
                int avg = sum/3;
                for(int i=0; i<pixels[0][0].length; i++){
                    pixel[c][i] = avg;
                }
            }
        }
    }

    public static void flipHorizontal(int[][][] pixels){

        for (int r = 0; r<pixels.length; r++) {
            for (int c = 0; c < pixels[0].length/2; c++) {

                for (int pix = 0; pix < pixels[0][0].length; pix++) {

                    int temp = pixels[r][c][pix];
                    pixels[r][c][pix] = pixels[r][pixels[0].length - 1 - c][pix];
                    pixels[r][pixels[0].length - 1 - c][pix] = temp;

                }

            }
        }

    }

    public static void flattenRed(int[][][] pixels){

        for (int[][] pixel : pixels) {

            for (int c = 0; c < pixels[0].length; c++) {

                pixel[c][0] = 0;
            }
        }

    }
    public static void flattenGreen(int[][][] pixels) {

        for (int[][] pixel : pixels) {

            for (int c = 0; c < pixels[0].length; c++) {

                pixel[c][1] = 0;
            }
        }

    }
    public static void flattenBlue(int[][][] pixels){

        for (int[][] pixel : pixels) {

            for (int c = 0; c < pixels[0].length; c++) {

                pixel[c][2] = 0;
            }
        }

    }


    public static void displayImage(int[][][] pixels, String name){

        BufferedImage img = new BufferedImage(pixels[0].length, pixels.length, BufferedImage.TYPE_INT_RGB);
        for(int r=0; r<pixels.length; r++){
            for(int c=0; c<pixels[0].length; c++){
                img.setRGB(c, r, new Color(pixels[r][c][0],pixels[r][c][1],pixels[r][c][2]).getRGB());
            }
        }

        JFrame myFrame = new JFrame(name);
        JLabel imgLabel = new JLabel(new ImageIcon(img));
        myFrame.add(imgLabel);
        myFrame.pack();
        myFrame.setResizable(false);
        myFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        myFrame.setVisible(true);
    }
}
