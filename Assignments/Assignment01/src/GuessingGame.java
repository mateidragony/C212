/**
 *  Author: Matei Cloteaux
 *
 *  Program Description:
 *  Text based game where players try to guess as
 *  close to a randomly generated secret number. Closest guess wins.
 *
 *  Step-by-Step:
 *  Store player names and guesses while generating random number
 *  Then, calculate the differences of the guesses and random
 *  number. Then, state winner.
 */

import java.util.Scanner;


public class GuessingGame {

    public static void main(String[] args) {
        //create scanner
        Scanner s = new Scanner(System.in);
        //get player 1 name
        System.out.println("Enter player 1 name: ");
        String p1Name = s.nextLine();
        //get player 2 nam
        System.out.println("Enter player 2 name: ");
        String p2Name = s.nextLine();
        //Generate random Number
        System.out.println("Generating random number...");
        int rand = (int)(Math.random()*100)+1;
        System.out.println("Alright, I've got it");
        //Get player guesses
        System.out.println(p1Name+" guess your number:");
        int p1Guess = s.nextInt();
        System.out.println(p2Name+" guess your number:");
        int p2Guess = s.nextInt();
        //print out the secret number and player's guesses
        System.out.println("The secret number was: "+rand);
        //calculate absolute value of the player's difference
        int p1Diff = Math.abs(rand - p1Guess);
        int p2Diff = Math.abs(rand - p2Guess);
        //Print out player differences
        //if player guessed smaller than random number, print subtraction the correct way
        if(rand - p1Guess < 0){
            System.out.println(p1Name+" guessed "+p1Guess+" with a difference of "+p1Guess+" - "+rand+" = "+(p1Guess-rand));
        } else {
            System.out.println(p1Name+" guessed "+p1Guess+" with a difference of "+rand+" - "+p1Guess+" = "+(rand-p1Guess));
        }
        if(rand - p2Guess < 0){
            System.out.println(p2Name+" guessed "+p2Guess+" with a difference of "+p2Guess+" - "+rand+" = "+(p2Guess-rand));
        } else {
            System.out.println(p2Name+" guessed "+p2Guess+" with a difference of "+rand+" - "+p2Guess+" = "+(rand-p2Guess));
        }


        //if both players guessed the same number
        if(p1Guess == p2Guess){
            System.out.println("Since you both guessed the same number, the first player wins");
            System.out.println(p1Name+" WINS!!!!!");
            //Stop code from running any longer
            System.exit(0);
        }


        //if player 1 has a closer guess
        if(p1Diff < p2Diff){
            System.out.println(p1Name + " WINS!!!!");
        }
        //if player 2 has a closer guess
        else if(p2Diff < p1Diff){
            System.out.println(p2Name + " WINS!!!!");
        }
        //if their guesses are equidistant
        else{
            System.out.println("Since your guesses are equally far from the secret number, the \nplayer with the lower number wins ");
            if(p1Guess < p2Guess)
                System.out.println(p1Name + " WINS!!!!");
            else
                System.out.println(p2Name + " WINS!!!!");
        }

    }

}
