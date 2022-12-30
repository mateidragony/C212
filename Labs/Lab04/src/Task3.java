import java.util.Scanner;

public class Task3 {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.println("Enter length: ");
        int l = s.nextInt();
        System.out.println("Enter width: ");
        int w = s.nextInt();

        for(int i=0; i<w; i++){
            for(int j=0; j<l; j++){
                System.out.print("*");
            }
            System.out.println();
        }
    }

}
