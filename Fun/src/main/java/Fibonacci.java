import java.util.ArrayList;

public class Fibonacci {

    public static void main(String[] args) {

        ArrayList<Long> fibs = new ArrayList<>();
        fibs.add(1L); fibs.add(1L);

        int num = 50;
        //12586269025
        for(int i=2; i<num; i++){
            long fib = fibs.get(i-1) + fibs.get(i-2);
            fibs.add(fib);
        }

        System.out.println("fib("+num+") = "+fibs.get(num-1));

    }

}
