import java.util.ArrayList;
import java.util.Scanner;

public class Task3 {

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        System.out.println("Enter the size of the vectors: ");
        int size = s.nextInt();

        ArrayList<Integer> v1 = new ArrayList<>();
        ArrayList<Integer> v2 = new ArrayList<>();

        System.out.println("Enter the " + size + " coefficients of vector 1: ");
        for (int i = 0; i < size; i++) {
            v1.add(s.nextInt());
        }
        System.out.println("Enter the " + size + " coefficients of vector 2: ");
        for (int i = 0; i < size; i++) {
            v2.add(s.nextInt());
        }

        System.out.println("The Euclidean distance is: " + dist(v1,v2));

    }

    public static double dist(ArrayList<Integer> v1, ArrayList<Integer> v2){
        int sqrt = 0;
        for (int i = 0; i < v1.size(); i++) {
            sqrt += Math.pow(v1.get(i) - v2.get(i), 2);
        }
        return Math.sqrt(sqrt);
    }


}
