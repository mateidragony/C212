import java.util.Scanner;

public class Task5 {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.println("Enter first number:");
        int one = s.nextInt();
        System.out.println("Enter second number:");
        int two = s.nextInt();

        System.out.println("The sum is: "+add(one,two));
    }


    public static int add(int x, int y){
        return x+y;
    }
}
