import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Task2 {


    public static void main(String[] args) throws FileNotFoundException {
        File f = new File("Labs/Lab07/nintendo.txt");
        Scanner s = new Scanner(f);

        int sum = 0;
        while(s.hasNextLine()){
            sum += s.nextInt();
        }

        System.out.println("Total: "+sum);
        System.out.println("Games you can buy: "+sum/50);
        System.out.println("Remaining money: "+sum%50);
    }
}
