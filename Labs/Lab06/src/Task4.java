import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Task4 {

    public static int[][] transpose(int[][] in){

        int[][] transpose = new int[in[0].length][in.length];

        for(int r=0; r<in.length; r++){
            for(int c=0; c<in[0].length; c++){
                transpose[c][r] = in[r][c];
            }
        }

        return transpose;
    }


    public static void main(String[] args) {

        int[][][] matrices = new int[][][]{ {{1,5,7}, {2,4,5}, {9,9,4}, {0,0,0}} };

        run(matrices[0]);

        System.out.println();

        for(int[][] matrix : matrices){
            int[][] t = transpose(matrix);

            for(int[] row : t){
                System.out.println(Arrays.toString(row));
            }

            System.out.println();
        }
    }

    public static void run(int[][] matrix){


        IntStream.range(0, matrix[0].length)
                .mapToObj(r -> IntStream.range(0, matrix.length).map(c -> matrix[c][r]).toArray())
                .map(Arrays::toString).forEach(System.out::println);

    }

}
