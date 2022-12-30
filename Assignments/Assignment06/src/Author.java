/**
 *  Author: Matei Cloteaux
 *  Program Description:
 *  Author Class
 *  Step-by-Step:
 *  Class that stores a name, email, and date of birth of an author
 *  Used in the Book and BetterBook classes
 */


import java.util.Arrays;

public class Author {

    private String name;
    private String email;
    private final int[] dob; //Date format Month/Day/Year


    public Author(String name, String email, int[] dob){
        this.name = name;
        this.email = email;
        this.dob = Arrays.copyOf(dob, dob.length);
    }

    public Author(String name, int[] dob){
        this.name = name;
        email = null;
        this.dob = Arrays.copyOf(dob, dob.length);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int[] getDob() {
        return dob;
    }

    public String getDobStr(){
        StringBuilder dateStr = new StringBuilder();
        for(int date : dob)
            dateStr.append(date).append("/");
        dateStr.deleteCharAt(dateStr.length()-1); //removes last

        return dateStr.toString();
    }

    @Override
    public String toString(){
        return email != null ? (name + " (" + email + ")"+", born "+ getDobStr()) : (name + " (no email)"+", born "+ getDobStr());
    }
}
