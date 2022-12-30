import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Task4 {

    public static void main(String[] args) throws FileNotFoundException {

        File f = new File("Labs/Lab07/employees.txt");
        Scanner s = new Scanner(f);

        int num = s.nextInt();
        int sum = 0;
        String bestEmployee = "";
        int best = Integer.MIN_VALUE;
        String worstEmployee = "";
        int worst = Integer.MAX_VALUE;

        while(s.hasNextLine()){
            String name = s.next();
            int salary = s.nextInt();

            if(salary > best){
                best = salary;
                bestEmployee = name;
            }
            if(salary < worst){
                worst = salary;
                worstEmployee = name;
            }

            sum += salary;
        }


        PrintWriter pw = new PrintWriter("Labs/Lab07/analysis.txt");
        pw.println("Average Salary: "+ sum/(num*1.0));
        pw.println("Employee with highest salary: "+bestEmployee);
        pw.println("Employee with lowest salary: "+worstEmployee);
        pw.close();

    }



}
