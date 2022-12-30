/**
 *  Author: Matei Cloteaux
 *  Program Description:
 *  Text based game where players try to guess digits of a random number
 *  Step-by-Step:
 *  Store player names and guesses while generating random number
 *  Then, generate random double between 1 and 0, if <0.5, player 1 starts
 *  Then, while the players haven't guessed all the correct digits,
 *  keep making them guess. Then, check if their guess contains
 *  digits of the secret number. If it does, update the string which
 *  contains correct guesses.
 *  Once out of loop, declare winner.
 */

import java.util.Random;
import java.util.Scanner;


public class CodeCracker {

    public static void main(String[] args) {

        //Init stuff
        Random randy = new Random();
        Scanner s = new Scanner(System.in);
        String secret = "" + randy.nextInt(100,1000);
        int gameCounter = 0;
        String correctGuesses = "XXX";

        //System.out.println("The secret Number is: "+ secret);

        //print statements
        System.out.println("Welcome to Code Cracker by Matei Cloteaux");
        System.out.println("Enter player 1 name: ");
        String p1Name = s.nextLine();
        System.out.println("Enter player 2 name: ");
        String p2Name = s.nextLine();
        System.out.println("The computer has thought of a secret number ");
        System.out.println("Flipping coin...");
        //Coin toss
        String p1, p2;
        if(Math.random() > 0.5){
            p1 = p1Name;
            p2 = p2Name;
        } else {
            p1 = p2Name;
            p2 = p1Name;
        }
        System.out.println(p1+" gets the first turn...");

        //run game
        while(!correctGuesses.equals(secret)){
            gameCounter++;

            if(gameCounter % 2 != 0){
                System.out.println(p1 + " make a guess: ");
            } else {
                System.out.println(p2 + " make a guess: ");
            }

            String guess = s.nextLine();
            boolean isGuessValid = guess.length() == 3 && isNumber(guess);
            while(!isGuessValid){
                System.out.println("Enter a 3 digit number bruh: ");
                guess = s.nextLine();
                isGuessValid = guess.length() == 3 && isNumber(guess);
            }

            //loop through each digit in their guess
            //check if the digit == digit of secret
            for(int i=0; i<guess.length(); i++){
                char digit = guess.charAt(i);
                if(digit == secret.charAt(i)){
                    //replace the X with the correct digit
                    correctGuesses = correctGuesses.substring(0,i) + secret.charAt(i) + correctGuesses.substring(i+1);
                }
            }

            System.out.println("You guessed: "+guess);
            System.out.println("So far, you have these digits correct: "+correctGuesses);
        }


        //Check who wins
        if(gameCounter % 2 != 0){
            System.out.println(p1 + " Wins!!!");
        } else {
            System.out.println(p2 + " Wins!!");
        }

        System.out.println("Bye bye... :(");

    }


    public static boolean isNumber(String s){
        try{
            Integer.parseInt(s);
        }catch(NumberFormatException ex){
            return false;
        }
        return true;
    }

}
