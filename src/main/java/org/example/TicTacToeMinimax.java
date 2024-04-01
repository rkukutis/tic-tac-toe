package org.example;

import java.util.Arrays;
import java.util.Scanner;

public class TicTacToeMinimax {
    private final String[][] matrix;

    private boolean gameIsOngoing;
    private final Scanner scanner;

    public TicTacToeMinimax(Scanner scanner) {
        this.matrix = new String[3][3];
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix.length; j++){
                matrix[i][j] = " ";
            }
        }
        this.gameIsOngoing = true;
        this.scanner = scanner;
    }

    public void initialize(){
        while(gameIsOngoing){
            System.out.println("Enter row to place X");
            int row = Integer.parseInt(scanner.nextLine());
            System.out.println("Enter column to place X");
            int col = Integer.parseInt(scanner.nextLine());
            if (row < 0 || col < 0 || row >= matrix.length || col >= matrix.length ||
                    !matrix[row][col].equals(" ")) {
                System.out.println("Invalid square!");
                continue;
            }
            matrix[row][col] = "X";
            drawBoard(matrix);
            if (checkIfPlayerWon(matrix) == 10){
                gameIsOngoing = false;
                System.out.println("Player X has won!" );
                break;
            }
            if(checkIfTied(matrix)){
                System.out.println("Nobody has won!");
                break;
            }
            System.out.println("Computer makes a move");
            int[] bestMove = findBestMove(matrix);
            matrix[bestMove[0]][bestMove[1]] = "O";
            drawBoard(matrix);
            if (checkIfPlayerWon(matrix) == -10){
                gameIsOngoing = false;
                System.out.println("Player O has won!" );
                break;
            }
            if(checkIfTied(matrix)){
                System.out.println("Nobody has won!");
                break;
            }
            System.out.println("It's X player's turn!");
        }
        System.out.println("Game ended");
    }

    private void drawBoard(String[][] board){
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board.length; j++){
                System.out.print(" |" + board[i][j] + "| ");
            }
            System.out.println();
        }
    }

    // returns 10 if X won, -10 if O won and 0 otherwise
    private int checkIfPlayerWon(String[][] board){
        if (checkIfPlayerWonRow(board) == 10
                || checkIfPlayerWonColumn(board) == 10
                || checkIfPlayerWonDiagonal(board) == 10){
            return 10;
        }
        if (checkIfPlayerWonRow(board) == -10
                || checkIfPlayerWonColumn(board) == -10
                || checkIfPlayerWonDiagonal(board) == -10){
            return -10;
        }
        return 0;
    }

    private int checkIfPlayerWonRow(String[][] board){
        boolean xWon = false;
        boolean oWon = false;
        int oNumInRow = 0;
        int xNumInRow = 0;

        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board.length; j++){
                if ((board[i][j]).equals("X")){
                    xNumInRow++;
                }
                if (board[i][j].equals("O")){
                    oNumInRow++;
                }
            }
            if (xNumInRow == board.length){
                xWon = true;
            } else {
                xNumInRow = 0;
            }
            if (oNumInRow == board.length){
                oWon = true;
            } else {
                oNumInRow = 0;
            }
        }
        if (xWon) return 10;
        if (oWon) return -10;
        return 0;
    }
    private int checkIfPlayerWonColumn(String[][] board){
        boolean xWon = false;
        boolean oWon = false;
        int oNumInColumn = 0;
        int xNumInColumn = 0;

        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board.length; j++){
                if ((board[j][i]).equals("X")){
                    xNumInColumn++;
                }
                if (board[j][i].equals("O")){
                    oNumInColumn++;
                }
            }
            if (xNumInColumn == board.length){
                xWon = true;
            } else {
                xNumInColumn = 0;
            }
            if (oNumInColumn == board.length){
                oWon = true;
            } else {
                oNumInColumn = 0;
            }
        }
        if (xWon) return 10;
        if (oWon) return -10;
        return 0;
    }
    private int checkIfPlayerWonDiagonal(String[][] board){
        int xRepeatLeft = 0;
        int oRepeatLeft = 0;
        int xRepeatRight= 0;
        int oRepeatRight= 0;
        for (int i = 0; i < board.length; i++){
            if((board[i][i]).equals("X")) xRepeatLeft++;
            if(board[Math.abs(2 - i)][i].equals("X")) xRepeatRight++;
            if((board[i][i]).equals("O")) oRepeatLeft++;
            if(board[Math.abs(2 - i)][i].equals("O")) oRepeatRight++;

        }
        if (xRepeatRight == board.length || xRepeatLeft == board.length) return 10;
        if (oRepeatRight == board.length || oRepeatLeft == board.length) return -10;
        return 0;
    }

    private boolean checkIfTied(String[][] board){
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board.length; j++){
                if (board[i][j].equals(" ")) return false;
            }
        }
        return true;
    }

    private int[] findBestMove(String[][] board){
            int bestScore = Integer.MAX_VALUE;
            int[] bestMove = {-1, -1};

            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    if (board[i][j].equals(" ")) {
                        board[i][j] = "O";
                        int score = minimax(board, 0, true);
                        System.out.println("Move" + "(" + i + ", " + j + ") score: " + score);
                        board[i][j] = " ";
                        if (score < bestScore) {
                            bestScore = score;
                            bestMove[0] = i;
                            bestMove[1] = j;
                        }
                    }
                }
            }
    System.out.println("best value " + bestScore);
    System.out.println("best move is " + Arrays.toString(bestMove));
    return bestMove;
    }

    private int minimax(String[][] board, int depth, boolean isMaximizer) {
        int score = checkIfPlayerWon(board);
        if (checkIfTied(board) && score == 0) return 0;
        if (score == 10) return score - depth;
        if (score == -10) return score + depth;

        if (isMaximizer) {
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    if (board[i][j].equals(" ")) {
                        board[i][j] = "X";
                        best = Math.max(best, minimax(board, depth + 1, false));
                        board[i][j] = " ";
                    }
                }
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    if (board[i][j].equals(" ")) {
                        board[i][j] = "O";
                        best = Math.min(best, minimax(board, depth + 1, true));
                        board[i][j] = " ";
                    }
                }
            }
            return best;
        }
    }
}
