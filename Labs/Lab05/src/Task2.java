import java.util.Scanner;

public class Task2 {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.println("Enter the size of the vectors: ");
        int size = s.nextInt();

        int[] v1 = new int[size];
        int[] v2 = new int[size];

        System.out.println("Enter the " + size + " coefficients of vector 1: ");
        for(int i=0; i<size; i++){
            v1[i] = s.nextInt();
        }
        System.out.println("Enter the " + size + " coefficients of vector 2: ");
        for(int i=0; i<size; i++){
            v2[i] = s.nextInt();
        }

        int sqrt = 0;
        for(int i=0; i<size; i++){
            sqrt += Math.pow(v1[i] - v2[i] , 2);
        }

        System.out.println("The Euclidean distance is: "+Math.sqrt(sqrt));
    }
}
