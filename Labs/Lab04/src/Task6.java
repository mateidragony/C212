import java.util.Scanner;

public class Task6 {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.println("Enter a String");
        String in = s.nextLine();

        System.out.println(verticalString(in));
    }

    public static String verticalString(String in){
        String newStr = "";

        for(int i=0; i<in.length(); i++){
            newStr+=in.charAt(i)+"\n";
        }

        return newStr;
    }
}
