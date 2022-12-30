/**
 *  Author: Matei Cloteaux
 *  Program Description:
 *  Text based game where a player can play a 2-player or 1-player version of
 *  tic-tac-toe.
 *  Step-by-Step:
 *  Note that every input keeps looping until the player enters a valid input
 *  First ask the user whether they want to play against a bot, play against another player,
 *  see the previous game, or quit the program
 *  If they play alone, they can choose the difficulty of the bot
 *  If they play against another player, they each input their names
 *  Rand int to determine who goes first.
 *  Then, they play tic-tac-toe by inputting what row/column they want to place their
 *  symbol. The game is stored in a 2D array of characters.
 *  After every turn, check if a player won or if there are no possible positions to play in (draw).
 *  Once play is over, determine winner or draw, then return to main menu.
 */

import java.util.*;

public class TicTacToe {
    // Static variables for the TicTacToe class, effectively configuration options
    // Use these instead of hard-coding ' ', 'X', and 'O' as symbols for the game
    // In other words, changing one of these variables should change the program
    // to use that new symbol instead without breaking anything
    // Do NOT add additional static variables to the class!
    static final char emptySpaceSymbol = ' ';
    static final char playerOneSymbol = 'X';
    static final char playerTwoSymbol = 'O';



    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        ArrayList<char[][]> prevGame = new ArrayList<>();
        String[] playerNames = new String[2];

        String displayMenu = """
                1. Single Player
                2. Two Player
                D. Display Last Match
                Q. QUIT""";
        List<String> validCommands = Arrays.asList("1","2","d","q");

        String difficultyChoices = """
                E: Easy
                M: Medium
                H: Hard
                Choose difficulty:""";
        List<String> validDifficulties = Arrays.asList("e","m","h");


        System.out.println("----------------------");
        System.out.println("Welcome to TicTacToe!");
        System.out.println("----------------------");
        System.out.println();

        outer:
        while(true){
            System.out.println(displayMenu);
            String cmd = s.nextLine().toLowerCase();

            while(!validCommands.contains(cmd)){
                System.out.println("Unrecognized Command. Try again.");
                System.out.println(displayMenu);
                cmd = s.nextLine().toLowerCase();
            }

            switch (cmd) {
                case "q":
                    break outer;
                case "d":
                    if (prevGame.isEmpty())
                        System.out.println("No match found.");
                    else
                        runGameHistory(playerNames, prevGame);
                    break;
                case "1":
                    System.out.println("Enter your name:");
                    playerNames[0] = s.nextLine();

                    System.out.println(difficultyChoices);
                    String diff = s.nextLine().toLowerCase();

                    while (!validDifficulties.contains(diff)) {
                        System.out.println("Invalid Difficulty. Try again.");
                        System.out.println(difficultyChoices);
                        diff = s.nextLine().toLowerCase();
                    }

                    switch (diff) {
                        case "e" -> playerNames[1] = "Easy Bot";
                        case "m" -> playerNames[1] = "Medium Bot";
                        case "h" -> playerNames[1] = "Hard Bot";
                    }

                    prevGame = runOnePlayerGame(playerNames, diff);

                    break;
                case "2":
                    System.out.println("Enter player 1 name:");
                    playerNames[0] = s.nextLine();
                    System.out.println("Enter player 2 name:");
                    playerNames[1] = s.nextLine();

                    prevGame = runTwoPlayerGame(playerNames);
                    break;
            }
        }

        System.out.println("Goodbye!");
    }

    // Given a state, return a String which is the textual representation of the tic-tac-toe board at that state.
    private static String displayGameFromState(char[][] state) {
        // Hint: Make use of the newline character \n to get everything into one String
        // It would be best practice to do this with a loop, but since we hardcode the game to only use 3x3 boards
        // it's fine to do this without one.

        StringBuilder display = new StringBuilder();

        for(int r=0; r<state.length; r++){
            char[] row = state[r];
            for(int i=0; i<row.length; i++){
                display.append(" ").append(row[i]).append(" ");
                if(i<row.length-1)
                    display.append("|");
            }
            if(r<state.length-1)
                display.append("\n-----------\n");
        }

        return display.toString();
    }

    // Returns the state of a game that has just started.
    // This method is implemented for you. You can use it as an example of how to utilize the static class variables.
    // As you can see, you can use it just like any other variable, since it is instantiated and given a value already.
    private static char[][] getInitialGameState() {
        return new char[][]{{emptySpaceSymbol, emptySpaceSymbol, emptySpaceSymbol},
                {emptySpaceSymbol, emptySpaceSymbol, emptySpaceSymbol},
                {emptySpaceSymbol, emptySpaceSymbol, emptySpaceSymbol}};
    }


    // Given the player names, run the two-player game.
    // Return an ArrayList of game states of each turn -- in other words, the gameHistory
    private static ArrayList<char[][]> runTwoPlayerGame(String[] playerNames) {

        ArrayList<char[][]> gameHistory = new ArrayList<>();
        char[][] gameState = getInitialGameState();
        int gameCounter = 0;

        char[] playerSymbols = {playerOneSymbol, playerTwoSymbol};
        int firstPlayerIndex = 0;
        System.out.println("Tossing a coin to see who goes first...");
        if(Math.random() > 0.5)
            firstPlayerIndex = 1;
        System.out.println(playerNames[firstPlayerIndex] + " goes first:");




        while(!checkWin(gameState) && !checkDraw(gameState)) {
            gameHistory.add(copyOfGameState(gameState));
            String player = gameCounter%2 == 0 ? playerNames[firstPlayerIndex] : playerNames[1-firstPlayerIndex];
            char playerSymbol = gameCounter%2 == 0 ? playerSymbols[firstPlayerIndex] : playerSymbols[1-firstPlayerIndex];

            System.out.println(displayGameFromState(gameState));
            System.out.println(player + "'s turn:");
            runPlayerMove(playerSymbol, gameState);

            gameCounter++;
        }

        gameHistory.add(copyOfGameState(gameState));
        System.out.println(displayGameFromState(gameState));

        if(checkDraw(gameState))
            System.out.println("Draw!");
        else
            System.out.println(((gameCounter-1)%2 == 0 ? playerNames[firstPlayerIndex] : playerNames[1-firstPlayerIndex]) + " wins!");

        return gameHistory;
    }
    // Given the player names (where player two is "Easy Computer", "Medium Computer", etc.), and the difficulty,
    // Run the one-player game.
    // Return an ArrayList of game states of each turn -- in other words, the gameHistory
    private static ArrayList<char[][]> runOnePlayerGame(String[] playerNames, String difficulty) {

        ArrayList<char[][]> gameHistory = new ArrayList<>();
        char[][] gameState = getInitialGameState();
        int gameCounter = 0;

        //flip coin (if 0, player goes first)
        System.out.println("Flipping coin to see who goes first...");
        int playerTurn = 0;
        if(Math.random() > 0.5) {
            playerTurn = 1;
            System.out.println(playerNames[1]+ " goes first.");
        } else
            System.out.println(playerNames[0] + " goes first.");


        while(!checkWin(gameState) && !checkDraw(gameState)){
            gameHistory.add(copyOfGameState(gameState));
            System.out.println(displayGameFromState(gameState));

            //player's turn
            if(gameCounter % 2 == playerTurn){
                System.out.println(playerNames[0] + "'s turn:");
                runPlayerMove(playerOneSymbol, gameState);
            } else {
                System.out.println(playerNames[1] + "'s turn:");
                switch(difficulty){
                    case "e" -> getEasyCPUMove(gameState);
                    case "m" -> gameState = getMediumCPUMove(gameState);
                    case "h" -> gameState = getHardCPUMove(gameState);
                }
            }

            gameCounter++;
        }

        gameHistory.add(copyOfGameState(gameState));
        System.out.println(displayGameFromState(gameState));

        if(checkDraw(gameState))
            System.out.println("Draw!");
        else{
            String winner = (gameCounter-1) % 2 == playerTurn ? playerNames[0] : playerNames[1];
            System.out.println(winner + " wins!");
        }


        return gameHistory;
    }


    // Repeatedly prompts player for move in current state, returning new state after their valid move is made
    private static void runPlayerMove(char playerSymbol, char[][] currentState) {
        int[] move = getInBoundsPlayerMove();
        while(!checkValidMove(move, currentState)){
            System.out.println("That spot is already taken. Try again.");
            move = getInBoundsPlayerMove();
        }

        currentState[move[0]][move[1]] = playerSymbol;
    }
    // Repeatedly prompts player for move. Returns [row, column] of their desired move such that row & column are on
    // the 3x3 board, but does not check for availability of that space.
    private static int[] getInBoundsPlayerMove() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter row: ");
        int row = sc.nextInt();

        System.out.println("Enter column: ");
        int col = sc.nextInt();

        while((row < 0 || col > 2) || (col < 0 || row > 2)){
            System.out.println("This position is not on the board. Try Again");

            System.out.println("Enter row: ");
            row = sc.nextInt();
            System.out.println("Enter column: ");
            col = sc.nextInt();
        }

        return new int[]{row,col};
    }


    // Given a [row, col] move, return true iff a space is unclaimed.
    // Doesn't need to check whether move is within bounds of the board.
    private static boolean checkValidMove(int[] move, char[][] state) {
        return state[move[0]][move[1]] == emptySpaceSymbol;
    }
    // Given a state, return true iff some player has won in that state
    private static boolean checkWin(char[][] state) {
        // TODO
        // Hint: no need to check if player one has won and if player two has won in separate steps,
        // you can just check if the same symbol occurs three times in any row, col, or diagonal (except empty space symbol)
        // But either implementation is valid: do whatever makes most sense to you.

        // Horizontals
        for (char[] row : state) {
            char symbolCheck = row[0];
            boolean currentHorizontal = true;

            if(symbolCheck != emptySpaceSymbol) {
                for (int c = 1; c < state[0].length; c++) {
                    if (row[c] != symbolCheck) {
                        currentHorizontal = false;
                        break;
                    }
                }

                if (currentHorizontal) {
                    return true;
                }
            }
        }
        // Verticals
        for(int c=0; c<state[0].length; c++){
            char symbolCheck = state[0][c];
            boolean currentVertical = true;

            if(symbolCheck != emptySpaceSymbol) {
                for (int r = 1; r < state.length; r++) {
                    if (state[r][c] != symbolCheck) {
                        currentVertical = false;
                        break;
                    }
                }

                if (currentVertical) {
                    return true;
                }
            }
        }
        // Diagonals
        char diag1Check = state[0][0];
        char diag2Check = state[state.length-1][0];

        boolean diagOne = diag1Check != emptySpaceSymbol;
        boolean diagTwo = diag2Check != emptySpaceSymbol;
        for(int r=1; r<state.length; r++){
            if(state[r][r] != diag1Check)
                diagOne = false;
            if(state[state.length-1-r][r] != diag2Check)
                diagTwo = false;
        }

        return diagOne || diagTwo;
    }

    // Given a state, simply checks whether all spaces are occupied. Does not care or check if a player has won.
    private static boolean checkDraw(char[][] state) {
        return getValidMoves(state).isEmpty();
    }


    // Given a [row, col] move, the symbol to add, and a game state,
    // Return a NEW array (do NOT modify the argument currentState) with the new game state
    private static char[][] makeMove(int[] move, char symbol, char[][] currentState) {
        // TODO:
        // Hint: Make use of Arrays.copyOf() somehow to copy a 1D array easily
        // You may need to use it multiple times for a 1D array
        char[][] newState = new char[currentState.length][currentState[0].length];
        for(int i=0; i< currentState.length; i++){
            newState[i] = Arrays.copyOf(currentState[i], currentState[i].length);
        }

        newState[move[0]][move[1]] = symbol;

        return newState;
    }


    // For all AI methods, assume the AI is player two.

    // Given a game state, return a new game state with the move from the easy AI
    // The easy AI should simply make a move at random from the available spaces
    private static char[][] getEasyCPUMove(char[][] gameState) {
        ArrayList<int[]> validMoves = getValidMoves(gameState);

        int[] myMove = validMoves.get((int)(Math.random()* validMoves.size()));
        gameState[myMove[0]][myMove[1]] = playerTwoSymbol;

        return gameState;
    }

    // Given a game state, return a new game state with move from the hard AI
    // The behavior of the medium AI is up to you, as long as its skill/algorithm
    // is better than easy but worse than hard
    private static char[][] getMediumCPUMove(char[][] gameState) {
        // Flip a coin to see if CPU smart or dumb
        return Math.random() > 0.5 ? getHardCPUMove(gameState) : getEasyCPUMove(gameState);
    }

    // Given a game state, return a new game state with move from the hard AI
    // The hard AI follows the algorithm in the PDF to ensure a tie (or win if possible)
    private static char[][] getHardCPUMove(char[][] gameState) {
        // Determine all available spaces
        ArrayList<int[]> available = getValidMoves(gameState);
        // If there is a winning move available, make that move
        for(int[] move : available){
            char[][] possibleState = makeMove(move,playerTwoSymbol,gameState);
            if(checkWin(possibleState))
                return possibleState;
        }
        // If not, check if opponent has a winning move, and if so, make a move there
        for(int[] move : available){
            char[][] possibleState = makeMove(move,playerOneSymbol,gameState);
            if(checkWin(possibleState))
                return makeMove(move,playerTwoSymbol,gameState);
        }
        // If not, move on center space if possible
        if(checkValidMove(new int[]{1,1}, gameState))
            return makeMove(new int[]{1,1}, playerTwoSymbol, gameState);
        // If not, move on corner spaces if possible
        if(checkValidMove(new int[]{0,0}, gameState))
            return makeMove(new int[]{0,0}, playerTwoSymbol, gameState);
        if(checkValidMove(new int[]{0,2}, gameState))
            return makeMove(new int[]{0,2}, playerTwoSymbol, gameState);
        if(checkValidMove(new int[]{2,0}, gameState))
            return makeMove(new int[]{2,0}, playerTwoSymbol, gameState);
        if(checkValidMove(new int[]{2,2}, gameState))
            return makeMove(new int[]{2,2}, playerTwoSymbol, gameState);
        // Otherwise, move in any available spot
        int[] move = available.get((int)(Math.random()*available.size()));
        return makeMove(move, playerTwoSymbol, gameState);
    }

    // Given a game state, return an ArrayList of [row, column] positions that are unclaimed on the board
    private static ArrayList<int[]> getValidMoves(char[][] gameState) {
        ArrayList<int[]> validMoves = new ArrayList<>();

        for(int r=0; r< gameState.length; r++){
            for(int c=0; c< gameState[0].length; c++){
                if(gameState[r][c] == emptySpaceSymbol)
                    validMoves.add(new int[]{r,c});
            }
        }

        return validMoves;
    }

    // Given player names and the game history, display the past game as in the PDF sample code output
    private static void runGameHistory(String[] playerNames, ArrayList<char[][]> gameHistory) {

        //determine which player went first
        int firstPlayerIndex = 0;

        outer:
        for(char[] row : gameHistory.get(1)){
            for(char index : row){
                if(index == playerOneSymbol)
                    break outer;
                else if(index == playerTwoSymbol) {
                    firstPlayerIndex = 1;
                    break outer;
                }
            }
        }


        String header = playerNames[0]+" ("+playerOneSymbol+") vs "+playerNames[1]+ " ("+playerTwoSymbol+")";

        System.out.println("-".repeat(header.length()));
        System.out.println(header);
        System.out.println("-".repeat(header.length()));

        System.out.println(displayGameFromState(gameHistory.get(0)));

        for(int i=1; i<gameHistory.size(); i++){
            if(i%2 != 0){
                System.out.println(playerNames[firstPlayerIndex]+":");
                System.out.println(displayGameFromState(gameHistory.get(i)));
            } else {
                System.out.println(playerNames[1-firstPlayerIndex]+":");
                System.out.println(displayGameFromState(gameHistory.get(i)));
            }
        }

        if(checkDraw(gameHistory.get(gameHistory.size()-1)))
            System.out.println("Draw!");
        else
            System.out.println(((gameHistory.size()-1) % 2 != 0 ? playerNames[firstPlayerIndex] : playerNames[1-firstPlayerIndex]) + " wins!");


    }

    private static char[][] copyOfGameState(char[][] gameState){
        char[][] copy = new char[gameState.length][gameState[0].length];

        for(int i=0; i< gameState.length; i++){
            copy[i] = Arrays.copyOf(gameState[i], gameState[i].length);
        }

        return copy;
    }
}