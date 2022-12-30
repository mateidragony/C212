import java.util.Scanner;
import java.util.stream.IntStream;

public class Task2 {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.println("Enter first number: ");
        int num1 = s.nextInt();
        System.out.println("Enter second number: ");
        int num2 = s.nextInt();
        System.out.println("Enter jump: ");
        int jump = s.nextInt();

        System.out.println("Here is your count: ");
        while(num1 <= num2){
            System.out.println(num1);
            num1 += jump;
        }


        System.out.println();
        System.out.println();
        run();
    }


    public static void run(){
        IntStream.range(0,1).peek(e -> System.out.println("Enter first number: "))
                .forEach(e -> IntStream.of(new Scanner(System.in).nextInt())
                        .peek(n1 -> System.out.println("Enter second number: "))
                        .forEach(n1 -> IntStream.of(new Scanner(System.in).nextInt())
                                .peek(n2 -> System.out.println("Enter jump: "))
                                .forEach(n2 -> IntStream.of(new Scanner(System.in).nextInt())
                                        .peek(j -> System.out.println("Here is your count: "))
                                        .forEach(j -> IntStream.range(n1,n2+1)
                                                .filter(num -> (num - n1) % j == 0)
                                                .forEach(System.out::println)))));
    }

}
