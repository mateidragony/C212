import java.util.Arrays;
import java.util.Scanner;

public class Task4 {


    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.println("Enter the size of the arrays: ");
        int size = s.nextInt();

        int[] v1 = new int[size];
        int[] v2 = new int[size];

        System.out.println("Enter the " + size + " elements of array 1: ");
        for(int i=0; i<size; i++){
            v1[i] = s.nextInt();
        }
        System.out.println("Enter the " + size + " elements of array 2: ");
        for(int i=0; i<size; i++){
            v2[i] = s.nextInt();
        }

        System.out.println("The new array is: "+ Arrays.toString(arraySum(v1,v2)));
    }




    public static int[] arraySum(int[] a1, int[] a2){

        int[] sum = new int[a1.length];

        for(int i=0; i<sum.length; i++){
            sum[i] = a1[i]+a2[i];
        }

        return sum;
    }

}
