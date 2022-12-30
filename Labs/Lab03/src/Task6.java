import java.util.Scanner;

public class Task6 {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.println("Input any number: ");
        int num = s.nextInt();

        System.out.println("Proper divisors of "+num+" are:");
        for(int i=1; i<num; i++){
            if(num%i == 0)
                System.out.println(i);
        }
    }

}
