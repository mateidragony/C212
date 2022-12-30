import java.util.Scanner;

public class Task4 {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.println("Enter n: ");
        int n = s.nextInt();
        System.out.println("Enter m: ");
        int m = s.nextInt();



        for(int i=0; i<=n; i++){

            for(int j=0; j<=m; j++){
                if(i==0) {
                    if(j==0) {
                        System.out.printf("%-4s", "X");
                        System.out.print(" |  ");
                    } else
                        System.out.printf("%-6s",j);
                }

                else if(j==0) {
                    System.out.printf("%-4s", i);
                    System.out.print(" |  ");
                }
                else
                    System.out.printf("%-6s",j*i);
            }

            System.out.println();
            if(i==0) {
                System.out.println("--------" + "------".repeat(m));
            }
        }
    }
}
