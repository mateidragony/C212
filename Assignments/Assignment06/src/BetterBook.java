/**
 *  Author: Matei Cloteaux
 *  Program Description:
 *  BetterBook Class
 *  Step-by-Step:
 *  Similar class to book except it can store multiple authors
 *  Also includes a revise method with returns a new book
 *  with double price and increased edition number
 */



import java.text.DecimalFormat;
import java.util.Arrays;

public class BetterBook {

    public final DecimalFormat df = new DecimalFormat("#.##");

    private double price;
    private Author[] authors;
    private String title;
    private int edition;



    public BetterBook(String title, double price, int edition, Author[] authors){
        this.title = title;
        this.price = price;
        this.edition = edition;
        this.authors = Arrays.copyOf(authors, authors.length);
    }


    public BetterBook revise(){
        return new BetterBook(title, price*2, edition+1, authors);
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public Author[] getAuthors() {
        return authors;
    }

    public String getTitle() {
        return title;
    }

    public int getEdition() {
        return edition;
    }

    public String getOrdinal(int i){
        //                   0th  1st   2nd   3rd   4th   5th   6th   7th   8th   9th
        String[] endings = {"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th"};
        return i + (i%100==11 || i%100==12 || i%100==13 ? "th" : endings[i % 10]);
    }

    public String getAuthorStr(){
        StringBuilder str = new StringBuilder();

        for(int i=0; i< authors.length-1; i++){
            Author a = authors[i];
            str.append(a.getName());
            if(a.getEmail() != null)
                str.append(" (").append(a.getEmail()).append(")");
            str.append(", ");
        }
        str.deleteCharAt(str.length()-2); //remove comma
        str.append("and ");

        str.append(authors[authors.length-1].getName());
        if(!authors[authors.length-1].getEmail().equals("no email"))
            str.append(" (").append(authors[authors.length-1].getEmail()).append(")");

        return str.toString();
    }

    @Override
    public String toString(){

        return title + " (" + getOrdinal(edition) + " edition), by "+getAuthorStr()+", available for $" + df.format(price);
    }


}
