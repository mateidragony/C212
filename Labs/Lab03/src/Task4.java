import java.util.Scanner;

public class Task4 {

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        System.out.println("Enter first number: ");
        int num1 = s.nextInt();
        System.out.println("Enter second number: ");
        int num2 = s.nextInt();
        System.out.println("Enter jump: ");
        int jump = s.nextInt();

        System.out.println("Here is your count: ");

        if(num1 < num2) {
            while (num1 <= num2) {
                System.out.println(num1);
                num1 += jump;
            }
        } else {
            while(num1 >= num2){
                System.out.println(num1);
                num1 -= jump;
            }
        }
    }
}
