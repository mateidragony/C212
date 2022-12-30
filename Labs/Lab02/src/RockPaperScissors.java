import java.util.Locale;
import java.util.Scanner;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class RockPaperScissors {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.println("Enter name of player 1:");
        String p1Name = s.nextLine();
        System.out.println("Enter the choice");
        String p1 = s.nextLine().toUpperCase();
        System.out.println("Enter name of player 2:");
        String p2Name = s.nextLine();
        System.out.println("Enter the choice");
        String p2 = s.nextLine().toUpperCase();


        if(p1.equals(p2))
            System.out.println("TIE!!!!!!!!");
        else if( (p1.equals("R") && p2.equals("S")) || (p1.equals("S") && p2.equals("P")) || p1.equals("P") && p2.equals("R"))
            System.out.println(p1Name);
        else
            System.out.println(p2Name);


        System.out.println();
        System.out.println();
        System.out.println();
        run();

    }


    public static void run(){
        IntStream.range(0,1).peek(e1 -> System.out.println("Enter name of player 1: "))
                .forEach(e1 -> Stream.of(new Scanner(System.in).nextLine())
                        .peek(p1Name -> System.out.println("Enter the choice"))
                        .forEach(p1Name -> Stream.of(new Scanner(System.in).nextLine().toUpperCase())
                                .peek(p1 -> System.out.println("Enter the name of player 2:"))
                                .forEach(p1 -> Stream.of(new Scanner(System.in).nextLine())
                                        .peek(p2Name -> System.out.println("Enter the choice"))
                                        .forEach(p2Name -> Stream.of(new Scanner(System.in).nextLine().toUpperCase())
                                                .forEach(p2 -> System.out.println(p1.equals(p2) ? "TIE!"
                                                        : (p1.equals("R") && p2.equals("S")) || (p1.equals("S") && p2.equals("P"))
                                                        || p1.equals("P") && p2.equals("R") ? p1Name : p2Name))))));
    }

}
