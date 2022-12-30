import java.util.*;

public class Task2 {


    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.println("Enter a string: ");
        String str = s.nextLine();

        System.out.println(Arrays.toString(subSet(str).toArray()));
    }



    public static Set<String> subSet(String s){

        Set<String> subsets = new HashSet<>();

        if(s.length() == 0) {
            subsets.add("");
            return subsets;
        } else {

            subsets.add(s);
            for(int i=0; i<s.length(); i++){
                StringBuilder sb = new StringBuilder(s);
                sb.deleteCharAt(i);
                subsets.addAll(subSet(sb.toString()));
            }

            return subsets;

        }
    }


}
