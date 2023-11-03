package org.example;

import java.util.Scanner;

public class TicTacToe {
    private final String[][] matrix;
    private boolean currentPlayerIsX;
    private boolean gameIsOngoing;
    private final Scanner scanner;

    public TicTacToe(Scanner scanner, int size) {
        this.matrix = new String[size][size];
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix.length; j++){
                matrix[i][j] = " ";
            }
        }
        this.currentPlayerIsX = true;
        this.gameIsOngoing = true;
        this.scanner = scanner;
    }

    public void initialize(){
        while(gameIsOngoing){
            System.out.println("Enter row to place " + (currentPlayerIsX ? "X" : "O"));
            int row = Integer.parseInt(scanner.nextLine());
            System.out.println("Enter column to place " + (currentPlayerIsX ? "X" : "O"));
            int col = Integer.parseInt(scanner.nextLine());
            if (!place(row, col)){
                System.out.println("Invalid square");
                continue;
            }
            drawMatrix();
            if (checkIfPlayerWon()){
                gameIsOngoing = false;
                System.out.println("Player " + (currentPlayerIsX ? "X" : "O") + " has won!" );
                break;
            }
            if(checkIfTied()){
                System.out.println("Nobody has won!");
                break;
            }
            currentPlayerIsX = !currentPlayerIsX;
            System.out.println("It's " + (currentPlayerIsX ? "X" : "O") + " player's turn!");
        }
        System.out.println("Game ended");
    }

    private void drawMatrix(){
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix.length; j++){
                System.out.print(" |" + matrix[i][j] + "| ");
            }
            System.out.println();
        }
    }

    private boolean checkIfPlayerWon(){
        return checkIfPlayerWonRow()
                || checkIfPlayerWonColumn()
                || checkIfPlayerWonDiagonal();
    }


    private boolean checkIfPlayerWonRow(){
        boolean outcome = true;
        String symbol = currentPlayerIsX ? "X" : "O";
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix.length; j++){
                if (!matrix[i][0].equals(matrix[i][j])){
                    outcome = false;
                }
            }
        }
        return outcome;
    }
    private boolean checkIfPlayerWonColumn(){
        boolean outcome = true;
        String symbol = currentPlayerIsX ? "X" : "O";
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix.length; j++){
                if (!matrix[0][i].equals(matrix[j][i])){
                    outcome = false;
                }
            }
        }
        return outcome;
    }
    private boolean checkIfPlayerWonDiagonal(){
        String symbol = currentPlayerIsX ? "X" : "O";
        int repeat = 0;
        for (int i = 0; i < matrix.length; i++){
            if(symbol.equals(matrix[i][i])) repeat++;
        }

        if (repeat == matrix.length) return true;
        repeat = 0;
        for (int i = 0; i < matrix.length; i++){
            if(symbol.equals(matrix[Math.abs(2 - i)][i])) repeat++;
        }
        return repeat == matrix.length;
    }

    private boolean checkIfTied(){
        boolean tied = true;
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix.length; j++){
                if (matrix[i][j].equals(" ")) tied = false;
            }
        }
        return tied;
    }

    private boolean place(int row, int column){
        if (matrix[row][column] != " " || matrix[row][column].equals(currentPlayerIsX ? "X" : "O")) return false;
        String symbol = currentPlayerIsX ? "X" : "O";
        matrix[row][column] = symbol;
        return true;
    }
}
