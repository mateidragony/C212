import java.util.Scanner;

public class Task5 {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.println("Enter the number for the multiplication table: ");
        int num = s.nextInt();

        for(int i=1; i<=num; i++){
            System.out.println(num + " x " + i + " = "+ i*num);
        }
    }
}
