import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import AStar.Location;

public class RobloxAssessment {

    public static void main(String[] args) {
        List<Integer> lst = Arrays.asList(1,2,3,4,5);

        List<Integer> bst = lst.stream().filter(e -> e < 3).toList();

        System.out.println(Arrays.toString(bst.toArray()));

    }

}
