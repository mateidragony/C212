import java.util.Scanner;

public class RestarauntBill {

    public static void main(String[] args) {
        int eggPrice = 5;
        int pancakePrice = 10;
        int coffeePrice = 8;

        Scanner s = new Scanner(System.in);

        System.out.println("Enter the number of eggs ordered:");
        int numEggs = s.nextInt();
        System.out.println("Enter the number of pancakes ordered:");
        int numCakes = s.nextInt();
        System.out.println("Enter the coffees ordered:");
        int coffee = s.nextInt();

        System.out.println("Bill");

        System.out.printf("%-12s", "Item");
        System.out.printf("%-12s", "Quantity");
        System.out.printf("%-12s", "Cost");
        System.out.println();

        System.out.printf("%-12s", "Eggs");
        System.out.printf("%-12s", numEggs);
        System.out.printf("%-12s", numEggs * eggPrice);
        System.out.println();

        System.out.printf("%-12s", "Pancakes");
        System.out.printf("%-12s", numCakes);
        System.out.printf("%-12s", numCakes * pancakePrice);
        System.out.println();

        System.out.printf("%-12s", "Coffee");
        System.out.printf("%-12s", coffee);
        System.out.printf("%-12s", coffee * coffeePrice);
        System.out.println();

        System.out.println("---------------------------------------------------");
        System.out.printf("%-24s","Total");
        System.out.print(numEggs*eggPrice + numCakes*pancakePrice + coffeePrice*coffee);


    }

}
