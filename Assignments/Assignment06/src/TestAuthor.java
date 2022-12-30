/**
 *  Author: Matei Cloteaux
 *  Program Description:
 *  Author Class Tester
 *  Step-by-Step:
 *  Tests multiple instances of Authors
 *  Tests Modifier Methods
 *  Tests Accessor Methods
 */

import java.util.Arrays;

public class TestAuthor {

    public static void main(String[] args) {
        Author jk = new Author("JK Rowling", "jkr@email.com", new int[]{10,25,2004});
        Author bob = new Author("Bob Smith", new int[]{10,3,19});

        System.out.println(jk); //Should print: JK Rowling (jkr@email.com), born 10/25/2004
        System.out.println(bob); //Should print: Bob Smith (no email), born 10/3/19

        bob.setName("Not Bob");
        System.out.println(bob); //Should print: Not Bob (no email), born 10/3/19

        bob.setEmail("bob.gmail.com"); //Should print: Not Bob (bob.gmail.com), born 10/3/19
        System.out.println(bob);

        System.out.println(jk); //Should not have changed: JK Rowling (jkr@email.com), born 10/25/2004

        jk.setEmail(null);
        System.out.println(jk); //Should print JK Rowling (null), born 10/25/2004
        jk.setEmail("jk.email.com");

        System.out.println(bob.getEmail()); //Should print: bob.gmail.com
        System.out.println(bob.getName()); //Should print: Not Bob
        System.out.println(Arrays.toString(bob.getDob())); //Should print : [10, 3, 19]
        System.out.println(bob.getDobStr()); //Should print: 10/3/19

        System.out.println(jk.getEmail()); //Should print: jk.email.com
        System.out.println(jk.getName()); //Should print: JK Rowling
        System.out.println(Arrays.toString(jk.getDob())); //Should print: [10, 25, 2004]
        System.out.println(jk.getDobStr()); //Should print: 10/25/2004
    }

}
