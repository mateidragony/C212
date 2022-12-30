import java.util.Arrays;
import java.util.Collections;

public class Task5 {


    public static void main(String[] args) {
        String[] names = {"Elena", "Thomas", "Hamilton", "Suzie", "Phil", "Matt", "Alex","Emma",
                "John", "James", "Jane", "Emily", "Daniel", "Neda","Aaron", "Kate"};
        int[] times = {341, 273, 278, 329, 445, 402, 388, 275, 243, 334, 412, 393, 299, 343, 317,
                265};

        int bestTime = Integer.MAX_VALUE;
        int secondBestTime = 0;
        String fastest = "";
        String secondFastest = "";
        double sum = 0;

        for(int i=0; i<times.length; i++){
            if(times[i] < bestTime){
                secondBestTime = bestTime;
                secondFastest = fastest;
                bestTime = times[i];
                fastest = names[i];
            } else if(times[i] < secondBestTime){
                secondBestTime = times[i];
                secondFastest = names[i];
            }

            sum += times[i];
        }

        double avg = sum/times.length;


        System.out.println("Fastest runner is "+fastest+ " with "+ bestTime + " minutes");
        System.out.println("Second fastest runner is "+secondFastest+ " with "+secondBestTime + " minutes");
        System.out.println("Average time: "+avg);
        System.out.println("All the runner with better times than average: ");


        for(int i=0; i<times.length; i++){

            if(times[i] < avg)
                System.out.println(names[i]);

        }

    }


}
