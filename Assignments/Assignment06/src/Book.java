import java.text.DecimalFormat;

/**
 *  Author: Matei Cloteaux
 *  Program Description:
 *  Book Class
 *  Step-by-Step:
 *  Class that stores the price, author, title, and edition of a book
 */


public class Book {

    public final DecimalFormat df = new DecimalFormat("#.##");

    private double price;
    private Author author;
    private String title;
    private int edition;



    public Book(String title, double price, int edition, Author author){
        this.title = title;
        this.price = price;
        this.edition = edition;
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public Author getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public int getEdition() {
        return edition;
    }

    public String getOrdinal(int i){
                        //   0th  1st   2nd   3rd   4th   5th   6th   7th   8th   9th
        String[] endings = {"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th"};
        return i + (i%100==11 || i%100==12 || i%100==13 ? "th" : endings[i % 10]);
    }

    @Override
    public String toString(){

        return title + " (" + getOrdinal(edition) + " edition), by "+author.toString()+", available for $" + df.format(price);
    }
}
