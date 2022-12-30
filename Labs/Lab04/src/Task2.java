import java.util.Scanner;

public class Task2 {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.println("Enter a number: ");
        int num = s.nextInt();

        for(int i=0; i<num; i++){
            for(int j=0; j<num; j++){
                System.out.print("*");
            }
            System.out.println();
        }
    }
}
