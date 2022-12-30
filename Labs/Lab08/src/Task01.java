import java.util.Scanner;
import java.time.Month;

public class Task01 {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        try {
            System.out.println("Enter the Month: ");
            String month = getMonthNumber(s.nextInt());
            System.out.println("It is " + month);
        } catch (IllegalArgumentException ex){
            System.out.println("That is not a valid month!!!");
            ex.printStackTrace();
        }
    }

    public static String getMonthNumber(int monthNumber) throws IllegalArgumentException{

        String month;

        switch(monthNumber){
            case 1 -> month = "January";
            case 2 -> month = "February";
            case 3 -> month = "March";
            case 4 -> month = "April";
            case 5 -> month = "May";
            case 6 -> month = "June";
            case 7 -> month = "July";
            case 8 -> month = "August";
            case 9 -> month = "September";
            case 10 -> month = "October";
            case 11 -> month = "November";
            case 12 -> month = "December";
            default -> throw new IllegalArgumentException();
        }

        return month;
    }
}
