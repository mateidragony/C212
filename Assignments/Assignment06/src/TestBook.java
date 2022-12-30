/**
 *  Author: Matei Cloteaux
 *  Program Description:
 *  Book Class Tester
 *  Step-by-Step:
 *  Tests multiple instances of a book
 *  Tests modifier methods
 *  Tests modifying authors
 *  Tests accessor methods
 */


public class TestBook {

    public static void main(String[] args) {

        Author jk = new Author("JK Rowling", "jkr@email.com", new int[]{10,25,2004});
        Author bob = new Author("Bob Smith", new int[]{10,3,19});
        Book harryPotter = new Book("Harry Potter", 100.64, 200, jk);
        Book b = new Book("Book!", 23.40, 2, bob);

        System.out.println(b); //Should print: Book! (2nd edition), by Bob Smith (no email), born 10/3/19, available for $23.4
        System.out.println(harryPotter); //Should print: Harry Potter (200th edition), by JK Rowling (jkr@email.com), born 10/25/2004, available for $100.64

        jk.setName("RAHHHHHH");
        jk.setEmail("jo.hotmail.fr");
        System.out.println(harryPotter); //Should print: Harry Potter (200th edition), by RAHHHHHH (jo.hotmail.fr), born 10/25/2004, available for $100.64

        harryPotter.setPrice(2.34);
        System.out.println(harryPotter); //Should print: Harry Potter (200th edition), by RAHHHHHH (jo.hotmail.fr), born 10/25/2004, available for $2.34

        System.out.println(harryPotter.getAuthor()); //Should print: RAHHHHHH (jo.hotmail.fr), born 10/25/2004
        System.out.println(harryPotter.getEdition());//Should print: 200
        System.out.println(harryPotter.getTitle()); //Should print: Harry Potter
        System.out.println(harryPotter.getPrice()); //Should print: 2.34

        System.out.println(b.getAuthor()); //Should print: Bob Smith (no email), born 10/3/19
        System.out.println(b.getEdition()); //Should print: 2
        System.out.println(b.getTitle()); //Should print: Book!
        System.out.println(b.getPrice());//Should print: 23.4

    }
}
