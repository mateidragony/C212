import java.util.Scanner;

public class Seasons {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.println("Enter the month:");
        int month = s.nextInt();
        System.out.println("Enter the date:");
        int day = s.nextInt();

        if( (month < 3) || (month == 12 && day >= 22) || (month == 3 && day <=21))
            System.out.println("It is winter!");
        if( (month >= 3 && month < 6) || (month == 6 && day <=20))
            System.out.println("It is Spring!");
        if( (month >= 6 && month < 9) || (month == 9 && day <=22))
            System.out.println("It is Summer!");
        if( (month >= 9 && month < 12) || (month == 12 && day <= 21))
            System.out.println("It is Fall!");

    }
}
