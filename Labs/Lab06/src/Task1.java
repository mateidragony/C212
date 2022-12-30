import java.util.ArrayList;
import java.util.Scanner;

public class Task1 {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.println("Enter number of items: ");
        int size = s.nextInt();
        ArrayList<Integer> list = new ArrayList<>();

        int highest = Integer.MIN_VALUE;
        int lowest = Integer.MAX_VALUE;
        double sum = 0;

        System.out.println("Enter "+size+" numbers: ");
        for(int i=0; i<size; i++){
            list.add(s.nextInt());

            highest = Math.max(highest, list.get(i));
            lowest = Math.min(lowest, list.get(i));
            sum += list.get(i);
        }

        System.out.println("Mean: "+sum/size);
        System.out.println("Highest: "+highest);
        System.out.println("Lowest: "+lowest);
    }

}
