import java.util.Scanner;
import java.time.Month;
import java.util.stream.IntStream;

public class Months {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter the Month: ");
        int m = s.nextInt();

        try {
            String month = getMonthNumber(m);
            System.out.println("It is " + month);
        } catch (IllegalArgumentException ex){
            System.out.println(m + ": That is not a valid month!!!");
        }

        run();
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


    public static void run(){

        IntStream.range(0,1).peek(e -> System.out.println("Enter the month: "))
                .forEach(e -> IntStream.of(new Scanner(System.in).nextInt())
                        .forEach(i -> System.out.println(i == 1 ? "It is January" : i == 2 ? "It is February"
                                : i == 3 ? "It is March" : i == 4 ? "It is April" : i == 5 ? "It is May"
                                : i == 6 ? "It is June" : i == 7 ? "It is July" : i == 8 ? "It is August"
                                : i == 9 ? "It is September" : i == 10 ? "It is October" : i == 11 ? "It is November"
                                : i == 12 ? "It is December" : e+": That is not a valid month!!!")));


    }
}
