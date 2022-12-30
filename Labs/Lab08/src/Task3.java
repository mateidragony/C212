import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Task3 {

    public static void main(String[] args){
        Scanner s = new Scanner(System.in);

        System.out.println("Enter word to look for: ");
        String word = s.nextLine();


        while(true) {
            try {
                System.out.println("Enter file name: ");
                String fileName = s.nextLine();
                File f = new File("Labs/Lab07/" + fileName);
                Scanner fileReader = new Scanner(f);
                int num = 0;
                while(fileReader.hasNext()){
                    if(fileReader.next().equals(word))
                        num++;
                }
                System.out.println(word + " appears "+num+" times in the file.");

                break;

            } catch(FileNotFoundException ex){
                System.out.println("File not found!");
            }
        }
    }
}
