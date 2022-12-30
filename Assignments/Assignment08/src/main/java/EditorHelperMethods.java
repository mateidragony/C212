import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class EditorHelperMethods {
    // Constants
    public static final int GRAYSCALE_TRANSFORM = 1;
    public static final int FLIP_HORIZONTAL_TRANSFORM = 2;
    public static final int NEGATE_RED_TRANSFORM = 3;
    public static final int NEGATE_GREEN_TRANSFORM = 4;
    public static final int NEGATE_BLUE_TRANSFORM = 5;
    public static final int JUST_RED_TRANSFORM = 6;
    public static final int JUST_GREEN_TRANSFORM = 7;
    public static final int JUST_BLUE_TRANSFORM = 8;
    public static final String IMAGES_PATH_PREFIX = "Assignments/Assignment08/src/main/images/";
    public static final String TEMP_FILE_NAME = "temp.ppm";

    public static String appendPathPrefix(String relativePath) {
        // This method lets our user just type "cake.ppm", which we know really means "src/main/images/cake.ppm"
        return IMAGES_PATH_PREFIX + relativePath;
    }
    public static BufferedImage resolveImageFromString(String relativePath) throws IOException {
        // Creates a new BufferedImage out of the path. Thanks, TwelveMonkeys!
        return ImageIO.read(new File(appendPathPrefix(relativePath)));
    }

    public static BufferedImage transformImage(String relativePath, int transformCode) throws IOException {
        // Gets the input and output 3D arrays ready using PPMHelperMethods.
        int[][][] inputImagePixels = PPMHelperMethods.parsePPMFile(appendPathPrefix(relativePath));
        int[][][] outputImagePixels;
        // Call the appropriate method in PPMHelperMethods to assign the right value to outputImagePixels.
        // For example, if the transform code matches GRAYSCALE_TRANSFORM, call the grayScale method, and so on...
        if (transformCode == GRAYSCALE_TRANSFORM) {
            outputImagePixels = PPMHelperMethods.grayScale(inputImagePixels);
        } else if(transformCode == FLIP_HORIZONTAL_TRANSFORM){
            outputImagePixels = PPMHelperMethods.flipHorizontal(inputImagePixels);
        } else if(transformCode == NEGATE_RED_TRANSFORM){
            outputImagePixels = PPMHelperMethods.negateRed(inputImagePixels);
        } else if(transformCode == NEGATE_GREEN_TRANSFORM){
            outputImagePixels = PPMHelperMethods.negateGreen(inputImagePixels);
        } else if(transformCode == NEGATE_BLUE_TRANSFORM){
            outputImagePixels = PPMHelperMethods.negateBlue(inputImagePixels);
        } else if(transformCode == JUST_RED_TRANSFORM){
            outputImagePixels = PPMHelperMethods.justTheReds(inputImagePixels);
        } else if(transformCode == JUST_GREEN_TRANSFORM){
            outputImagePixels = PPMHelperMethods.justTheGreens(inputImagePixels);
        } else if(transformCode == JUST_BLUE_TRANSFORM){
            outputImagePixels = PPMHelperMethods.justTheBlues(inputImagePixels);
        } else throw new IllegalArgumentException("Unrecognized transform code: " + transformCode);
        // Lastly, save the outputImagePixels to a new PPM file, using the default temp.ppm name
        PPMHelperMethods.printToPPMFile(appendPathPrefix(TEMP_FILE_NAME), outputImagePixels);
        // Once we've saved this image to a file, return the BufferedImage that we get when we read it in
        return resolveImageFromString(TEMP_FILE_NAME);
    }

    public static void saveTempImage(String relativePath) throws IOException {
        // When the user wants to save temp.ppm, they give us the name of the file they want to save to, and we duplicate
        // the current temp.ppm to that new file name. If a file with that name already exists, just overwrite it.
        Files.copy(Path.of(appendPathPrefix(TEMP_FILE_NAME)), Path.of(appendPathPrefix(relativePath)), StandardCopyOption.REPLACE_EXISTING);
    }
}