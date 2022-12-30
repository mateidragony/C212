import java.util.Scanner;

public class Task3 {


    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        System.out.println("Enter a value: ");
        int n = s.nextInt();

        System.out.println("Change is: " + change(n, ""));

    }

    public static String change(int money, String change){

        if(money == 0)
            return change;
        else{
            if(money / 25 > 0){
                return change(money-25, change.concat("Q"));
            } else if(money / 10 > 0){
                return change(money-10, change.concat("D"));
            } else if (money / 5 > 0) {
                return change(money-5, change.concat("N"));
            } else {
                return change(money-1, change.concat("P"));
            }
        }

    }


}
