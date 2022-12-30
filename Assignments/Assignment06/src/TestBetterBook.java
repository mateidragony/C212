/**
 *  Author: Matei Cloteaux
 *  Program Description:
 *  BetterBook Class Tester
 *  Step-by-Step:
 *  Tests multiple better book instances
 *  Test changing author aspects
 *  Tests revise method
 */

public class TestBetterBook {

    public static void main(String[] args) {

        Author jk = new Author("JK Rowling", "jkr@email.com", new int[]{10,25,2004});
        Author bob = new Author("Bob Smith", new int[]{10,3,19});
        Author joel = new Author("Joel Johns", "jj@e.mail.com", new int[]{10,3,19});
        Author sammy = new Author("Sammy Smith", "ss.smith@gmail.com",new int[]{10,3,19});


        BetterBook b = new BetterBook("Hello!", 20.93, 11, new Author[]{jk, bob, joel, sammy});
        BetterBook b2 = new BetterBook("BOOK 2 WOOOO", 1324.134, 141, new Author[]{jk,joel});

        System.out.println(b); // Should print: Hello! (11th edition), by JK Rowling (jkr@email.com), Bob Smith, Joel Johns (jj@e.mail.com) and Sammy Smith (ss.smith@gmail.com), available for $20.93

        BetterBook betterB = b.revise();
        System.out.println(betterB); // Should print: Hello! (12th edition), by JK Rowling (jkr@email.com), Bob Smith, Joel Johns (jj@e.mail.com) and Sammy Smith (ss.smith@gmail.com), available for $41.86

        System.out.println(b2); // Should print: BOOK 2 WOOOO (141st edition), by JK Rowling (jkr@email.com) and Joel Johns (jj@e.mail.com), available for $1324.13

        BetterBook betterB2 = b2.revise();
        System.out.println(betterB2); // Should print: BOOK 2 WOOOO (142nd edition), by JK Rowling (jkr@email.com) and Joel Johns (jj@e.mail.com), available for $2648.26


        jk.setName("Not JK Rowling");
        jk.setEmail(null);
        System.out.println(b); //Should Print: Hello! (11th edition), by Not JK Rowling, Bob Smith, Joel Johns (jj@e.mail.com) and Sammy Smith (ss.smith@gmail.com), available for $20.93
        System.out.println(betterB); //Should Print: Hello! (12th edition), by Not JK Rowling, Bob Smith, Joel Johns (jj@e.mail.com) and Sammy Smith (ss.smith@gmail.com), available for $41.86
    }

}
