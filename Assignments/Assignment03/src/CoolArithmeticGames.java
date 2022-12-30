/**
 *  Author: Matei Cloteaux
 *  Program Description:
 *  Text based game where a player answers multiple arithmetic questions for
 *  points and a streak
 *  Step-by-Step:
 *  Note that every input keeps looping until the player enters a valid number
 *  First ask the user whether they want to play, quit, or check score
 *  If they play, ask if they want addition or multiplication
 *  Then ask for the maximum number they want to do arithmetic with
 *  Then loop arithmetic questions based on the player's score
 *  Generate two random numbers and check if the player entered the correct answer
 *  If they did, update their score and their streak
 *  Otherwise, reset streak
 */


import java.util.*;

public class CoolArithmeticGames {

    public static int arithGame(int max, int numQuestions, boolean isArithmetic){

        Scanner s = new Scanner(System.in);

        int count = 0;
        int score = 0;

        while(count < numQuestions){
            int num1 = (int)(Math.random()*max + 1);
            int num2 = (int)(Math.random()*max + 1);

            String input;
            if(isArithmetic){
                System.out.println(num1 + " + "+num2 + " = ");
                input = s.nextLine();
                while(isNotNumber(input)){
                    System.out.println("Please enter a valid number.");
                    input = s.nextLine();
                }

                if(parseInt(input) != num1+num2)
                    break;
                else
                    score++;
            } else {
                System.out.println(num1 + " X "+num2 + " = ");
                input = s.nextLine();
                while(isNotNumber(input)){
                    System.out.println("Please enter a valid number.");
                    input = s.nextLine();
                }

                if(parseInt(input) != num1*num2)
                    break;
                else
                    score+=2;
            }

            count++;
        }

        return score;
    }


    public static void main(String[] args){
        int score = 0;
        int streak = 0;
        int bestStreak = 0;
        Scanner s = new Scanner(System.in);

        String selectMessage = """
                    Please make a selection from the following:
                    1. Play Arithmetic Game.
                    2. View Hall of Fame.
                    3. Quit.""";

        System.out.println("Welcome to Cool Arithmetic Games!");

        while(true) {
            //update best streak
            if(streak > bestStreak)
                bestStreak = streak;

            System.out.println(selectMessage);
            int selection = parseInt(s.nextLine());

            //keep asking for input if it is invalid
            while (selection < 1 || selection > 3){
                System.out.println("Invalid selection. Try again.");
                System.out.println(selectMessage);
                selection = parseInt(s.nextLine());
            }

            if(selection == 3)
                break;
            else if(selection == 2){
                System.out.println("===== Hall of Fame =====");
                System.out.println("Your score: " + score);
                System.out.println("Current streak: " + streak);
                System.out.println("Best Streak: "+ bestStreak);
                System.out.println("========================");
            } else {
                System.out.println("Would you like Addition (1) or Multiplication (2)");
                int arithChoice = parseInt(s.nextLine());
                //keep asking for input if it is invalid
                while (arithChoice < 1 || arithChoice > 2){
                    System.out.println("Invalid selection, please try again.");
                    System.out.println("Would you like Addition (1) or Multiplication (2)");
                    arithChoice = parseInt(s.nextLine());
                }

                boolean isAddition = arithChoice == 1;


                System.out.println("Enter the maximum number, which must be greater than your current score ("+score+"):");
                int max = parseInt(s.nextLine());
                //keep asking for input if it is invalid
                while (max <= score){
                    System.out.println("The max number is too low. Try again");
                    System.out.println("Enter the maximum number, which must be greater than your current score ("+score+"):");
                    max = parseInt(s.nextLine());
                }

                int roundScore = arithGame(max, (int)(Math.ceil(score/5.0)+1), isAddition);

                if(roundScore == 0) {
                    System.out.println("You get 0 points for losing :(");
                    streak = 0;
                }
                else{
                    System.out.println("You get "+roundScore+" points for winning");
                    score+=roundScore;
                    streak++;
                }
                System.out.println();

            }
        }

        System.out.println("Goodbye!");

    }

    public static int parseInt(String in){
        int num;
        try{
            num = Integer.parseInt(in);
        } catch(NumberFormatException ex){
            return 0;
        }
        return num;
    }

    public static boolean isNotNumber(String in){
        try{
            Integer.parseInt(in);
        } catch(NumberFormatException ex){
            return true;
        }
        return false;
    }
}
