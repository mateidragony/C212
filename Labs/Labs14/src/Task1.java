import java.util.Scanner;

public class Task1 {


    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.println("Enter a number:");
        int n = s.nextInt();

        System.out.println("The sum is: "+ recursiveAdd(n));


    }

    public static int recursiveAdd(int n){
        if(n == 1)
            return 1;
        else
            return n*n + recursiveAdd(n-1);
    }

}
