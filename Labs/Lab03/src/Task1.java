import java.util.Scanner;
import java.util.stream.IntStream;

public class Task1 {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.print("Enter a number: ");
        int num = s.nextInt();
        System.out.println();

        int i = 1;
        while(i<=num){
            System.out.print(i);
            if(i%2 == 0)
                System.out.println(" Even");
            else
                System.out.println(" Odd");

            i++;
        }


        run();
    }

    public static void run(){
        IntStream.range(0,1).peek(e -> System.out.println("Enter a number: "))
                .forEach(e -> IntStream.range(1, new Scanner(System.in).nextInt()+1)
                        .peek(System.out::print)
                        .forEach(x -> System.out.println(x%2==0 ? " Even" : " Odd")));
    }

}
