import java.util.Scanner;

public class Task2 {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.println("Enter length: ");
        int l = s.nextInt();
        System.out.println("Enter width: ");
        int w = s.nextInt();
        System.out.println("Enter char symbol");
        char symbol = s.next().charAt(0);

        try{
            System.out.println(getRectangleString(l,w,symbol));
        } catch(IllegalArgumentException ex){
            System.out.println("Invalid Dimensions");
            ex.printStackTrace();
        }
    }

    public static String getRectangleString(int w, int l, char symbol) throws IllegalArgumentException{
        StringBuilder str = new StringBuilder();

        if(w < 1 || l < 1)
            throw new IllegalArgumentException();


        for(int i=0; i<w; i++){
            str.append(String.valueOf(symbol).repeat(l));
            str.append("\n");
        }

        return str.toString();
    }
}
