package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TicTacToe {
    private final String[][] matrix;
    private boolean currentPlayerIsX;
    private boolean gameIsOngoing;
    private final Scanner scanner;
    private boolean computerOponent;

    public TicTacToe(Scanner scanner, int size, boolean computerOponent) {
        this.matrix = new String[size][size];
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix.length; j++){
                matrix[i][j] = " ";
            }
        }
        this.currentPlayerIsX = true;
        this.gameIsOngoing = true;
        this.scanner = scanner;
        this.computerOponent = computerOponent;
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
            if (computerOponent){
                currentPlayerIsX = !currentPlayerIsX;
                System.out.println("hello from computer");
                computerMakeMove();
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
        boolean outcome = false;
        int winRows = 0;
        int sameElementsInRow = 0;
        String symbol = currentPlayerIsX ? "X" : "O";
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix.length; j++){
                if (symbol.equals(matrix[i][j])){
                    sameElementsInRow++;
                }
            }
            if (sameElementsInRow == matrix.length){
                winRows++;
            } else {
                sameElementsInRow = 0;
            }
        }
        if (winRows > 0) outcome = true;
        return outcome;
    }
    private boolean checkIfPlayerWonColumn(){
        boolean outcome = false;
        int winColumns = 0;
        int sameElementsInColumn = 0;
        String symbol = currentPlayerIsX ? "X" : "O";
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix.length; j++){
                if (symbol.equals(matrix[j][i])){
                    sameElementsInColumn++;
                }
            }
            if (sameElementsInColumn == matrix.length){
                winColumns++;
            } else {
                sameElementsInColumn = 0;
            }
        }
        if (winColumns > 0) outcome = true;
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

    private void remove(int row, int column){
        matrix[row][column] = " ";
    }

    private void computerMakeRandomMove(){
        List<int[]> freeSpaces = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix.length; j++){
                if (matrix[i][j].equals(" ")){
                    int[] freeSpace = {i,j};
                    freeSpaces.add(freeSpace);
                }
            }
        }
        int[] selectedMove = freeSpaces.get((int) Math.floor(Math.random() * freeSpaces.size()));
        place(selectedMove[0], selectedMove[1]);
    }

    private void computerMakeMove(){
        List<int[]> freeSpaces = new ArrayList<>();
        List<int[]> winningMoves = new ArrayList<>();
        // find free spaces
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix.length; j++){
                if (matrix[i][j].equals(" ")){
                    int[] freeSpace = {i,j};
                    freeSpaces.add(freeSpace);
                }
            }
        }
        // find winning spaces
        System.out.println("free spaces: " + freeSpaces.size());
        currentPlayerIsX = !currentPlayerIsX;
        for (int[] space : freeSpaces){
            place(space[0], space[1]);
            if (checkIfPlayerWon()){
                winningMoves.add(space);
            }
            remove(space[0], space[1]);
    }
        System.out.println("winning spaces: " + winningMoves.size());
        for (int[] move : winningMoves){
            System.out.println(Arrays.toString(move));
        }
        currentPlayerIsX = !currentPlayerIsX;
        // make a move
        if (winningMoves.size() > 0){
            int[] selectedMove = winningMoves.get((int) Math.floor(Math.random() * winningMoves.size()));
            place(selectedMove[0], selectedMove[1]);
        } else {
            computerMakeRandomMove();
        }
    }
}
