import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Task5 {

    public static void main(String[] args) {

        run();

        Scanner s = new Scanner(System.in);

        System.out.println("Enter M: ");
        int m = s.nextInt();
        System.out.println("Enter N: ");
        int n = s.nextInt();

        String[][] board = new String[m][n];
        String[][] boardWithNums = new String[m][n];

        //generate board
        System.out.println("Matrix: ");
        for(int r=0; r<board.length; r++){
            for(int c=0; c<board[0].length; c++){

                if(Math.random() > 0.5)
                    board[r][c] = "*";
                else
                    board[r][c] = ".";

                System.out.print(board[r][c]+" ");

            }
            System.out.println();
        }

        //look through board to generate numbers
        System.out.println();
        System.out.println("Solution: ");
        for(int r=0; r<board.length; r++){
            for(int c=0; c<board[0].length; c++){

                if(board[r][c].equals("*"))
                    boardWithNums[r][c] = "*";
                else{
                    int numBombs = 0;

                    if(r>0 && board[r-1][c].equals("*"))
                        numBombs++;
                    if(c>0 && board[r][c-1].equals("*"))
                        numBombs++;
                    if(r>0 && c>0 && board[r-1][c-1].equals("*"))
                        numBombs++;

                    if(r<m-1 && board[r+1][c].equals("*"))
                        numBombs++;
                    if(c<n-1 && board[r][c+1].equals("*"))
                        numBombs++;
                    if(r<m-1 && c<n-1 && board[r+1][c+1].equals("*"))
                        numBombs++;

                    if(r<m-1 && c>0 && board[r+1][c-1].equals("*"))
                        numBombs++;
                    if(r>0 && c<n-1 && board[r-1][c+1].equals("*"))
                        numBombs++;

                    boardWithNums[r][c] = numBombs+"";

                }

                System.out.print(boardWithNums[r][c]+ " ");
            }
            System.out.println();
        }

    }



    public static void run(){
        IntStream.range(0,1).peek(e -> System.out.println("Enter M: "))
                .forEach(e1 -> Stream.of(new Scanner(System.in).nextInt())
                        .peek(e -> System.out.println("Enter N: "))
                        .forEach(m -> IntStream.of(new Scanner(System.in).nextInt())
                                .forEach(n -> IntStream.range(0, m)
                                        .peek(r -> IntStream.range(0,n).forEach(c -> System.out.print(Math.random() > 0.3 ? ". " : "* ")))
                                        .forEach(e -> System.out.println()))));

    }

}
