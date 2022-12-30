import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Task1 {


    public static void main(String[] args) throws FileNotFoundException {

        File f = new File("Labs/Lab07/test.txt");
        Scanner s = new Scanner(f);

        int sum = 0;
        int num = s.nextInt();
        while(s.hasNextLine()){
            sum += s.nextInt();
        }

        System.out.println("Mean: "+sum/(num*1.0));

    }

}
