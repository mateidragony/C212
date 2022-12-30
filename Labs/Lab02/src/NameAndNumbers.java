import java.util.Scanner;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class NameAndNumbers {

    public static void main(String[] args) {
        String name;
        int x;
        int y;
        Scanner s = new Scanner(System.in);


        System.out.println("Enter name: ");
        name = s.nextLine();
        System.out.println("Enter x: ");
        x = s.nextInt();
        System.out.println("Enter y: ");
        y = s.nextInt();

        System.out.println("User's name is "+name);
        System.out.println("User entered x = "+x+" and y = "+y);
        System.out.println(x+" + "+y+" = "+(x+y));
        System.out.println(x+" - "+y+" = "+(x-y));
        System.out.println(x+" * "+y+" = "+(x*y));
        System.out.println(x+" / "+y+" = "+(x/y));


        System.out.println();
        System.out.println("Now the one line code:");
        System.out.println();
        run();
    }

    public static void run(){
        IntStream.range(0,1).peek(e -> System.out.println("Enter name: "))
                .forEach(e -> Stream.of(new Scanner(System.in).nextLine())
                        .peek(r -> System.out.println("Enter x: "))
                        .forEach(name -> IntStream.of(new Scanner(System.in).nextInt())
                                .peek(t -> System.out.println("Enter y: "))
                                .forEach(x -> IntStream.of(new Scanner(System.in).nextInt())
                                        .peek(y -> System.out.println("User's name is "+name))
                                        .peek(y -> System.out.println("User entered x = "+x+" and y = "+y))
                                        .peek(y -> System.out.println(x+" + "+y+" = "+(x+y)))
                                        .peek(y -> System.out.println(x+" - "+y+" = "+(x-y)))
                                        .peek(y -> System.out.println(x+" * "+y+" = "+(x*y)))
                                        .forEach(y -> System.out.println(x+" / "+y+" = "+(x/y))))));
    }

}
