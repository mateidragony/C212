import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class PPMHelperMethods {
    // This file is already complete: it came from the A5 solution. You're welcome to replace it with your own code,
    // but you can totally just leave it alone. You'll have to look over it to see the names of the methods, though.
    // Note that some students for A5 modified in the input 3D array. As we've discussed many times, this is bad practice,
    // so if your solution did it that way, please do NOT replace this with your code!
    private final static int MAX_COLOR_VALUE = 255;
    public static int[][][] parsePPMFile(String path) throws FileNotFoundException, ArrayIndexOutOfBoundsException {
        File file = new File(path);

        Scanner sc = new Scanner(file);

        // Parsing header information
        sc.nextLine(); // dump first line "P3"
        int cols = sc.nextInt(); // i.e. width first
        int rows = sc.nextInt(); // i.e. height second
        sc.nextInt(); // dump max value "255"
        // Define array
        int[][][] result = new int[rows][cols][3];
        // Parsing body information
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                for (int channel = 0; channel < 3; channel++) {
                    result[row][col][channel] = sc.nextInt();
                }
            }
        }
        sc.close();
        return result;
    }

    public static void printToPPMFile(String path, int[][][] image) throws FileNotFoundException {
        File file = new File(path);
        PrintWriter pw = new PrintWriter(file);
        pw.println("P3");
        pw.println(image[0].length + " " + image.length); // cols rows
        pw.println(MAX_COLOR_VALUE);
        for (int[][] row : image) {
            for (int[] pixel : row) {
                for (int channel : pixel) {
                    pw.print(channel + " ");
                }
                pw.print("    ");
            }
            pw.println();
        }
        pw.close();
    }



    public static int[][][] negateRed(int[][][] image) {
        return negateAColor(image, 0);
    }
    public static int[][][] negateGreen(int[][][] image) {
        return negateAColor(image, 1);
    }
    public static int[][][] negateBlue(int[][][] image) {
        return negateAColor(image, 2);
    }

    // Helper method to avoid duplicated code in negation methods above
    private static int[][][] negateAColor(int[][][] image, int channel) throws ArrayIndexOutOfBoundsException {
        int[][][] result = new int[image.length][image[0].length][3];
        for (int row = 0; row < image.length; row++) {
            for (int col = 0; col < image[0].length; col++) {
                result[row][col][0] = (channel == 0 ? MAX_COLOR_VALUE - image[row][col][0] : image[row][col][0]);
                result[row][col][1] = (channel == 1 ? MAX_COLOR_VALUE - image[row][col][1] : image[row][col][1]);
                result[row][col][2] = (channel == 2 ? MAX_COLOR_VALUE - image[row][col][2] : image[row][col][2]);
            }
        }
        return result;
    }

    public static int[][][] flipHorizontal(int[][][] image) throws ArrayIndexOutOfBoundsException {
        int[][][] result = new int[image.length][image[0].length][3];
        for (int row = 0; row < image.length; row++) {
            for (int col = 0; col < image[0].length / 2; col++) { // go thru left half of image, not incl. middle for odd # of cols
                for (int channel = 0; channel < 3; channel++) {
                    result[row][col][channel] = image[row][image[0].length-1-col][channel];
                    result[row][image[0].length-1-col][channel] = image[row][col][channel];
                }
            }
        }
        return result;
    }
    public static int[][][] grayScale(int[][][] image) {
        int[][][] result = new int[image.length][image[0].length][3];
        for (int row = 0; row < image.length; row++) {
            for (int col = 0; col < image[0].length; col++) {
                int averageValue = image[row][col][0] + image[row][col][1] + image[row][col][2] / 3;
                result[row][col][0] = averageValue;
                result[row][col][1] = averageValue;
                result[row][col][2] = averageValue;
            }
        }
        return result;
    }

    private static int[][][] flattenRed(int[][][] image) {
        return flattenAChannel(image, 0);
    }
    private static int[][][] flattenGreen(int[][][] image) {
        return flattenAChannel(image, 1);
    }
    private static int[][][] flattenBlue(int[][][] image) {
        return flattenAChannel(image, 2);
    }

    // Helper method to avoid duplicated code in flatten methods above
    private static int[][][] flattenAChannel(int[][][] image, int channel) {
        int[][][] result = new int[image.length][image[0].length][3];
        for (int row = 0; row < image.length; row++) {
            for (int col = 0; col < image[0].length; col++) {
                result[row][col][0] = (channel == 0 ? 0 : image[row][col][0]);
                result[row][col][1] = (channel == 1 ? 0 : image[row][col][1]);
                result[row][col][2] = (channel == 2 ? 0 : image[row][col][2]);
            }
        }
        return result;
    }

    // Methods corresponding to actual menu operations:
    public static int[][][] justTheReds(int[][][] image) {
        return flattenGreen(flattenBlue(image));
    }
    public static int[][][] justTheGreens(int[][][] image) {
        return flattenRed(flattenBlue(image));
    }
    public static int[][][] justTheBlues(int[][][] image) {
        return flattenRed(flattenGreen(image));
    }
}