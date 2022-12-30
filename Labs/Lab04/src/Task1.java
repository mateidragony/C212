import java.util.Scanner;

public class Task1 {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.println("Enter a number: ");
        int i = s.nextInt();

        System.out.println();
        while(i>0){
            System.out.print("*");
            i--;
        }
    }

}
