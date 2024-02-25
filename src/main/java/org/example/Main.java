package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Would you like to play a nice game of tic-tac-toe? y/n");
            String play = scanner.nextLine();
            if (play.equals("y")) {
                System.out.println("Would you like to play against another human(h) or a computer(c)?");
                String opponentType = scanner.nextLine();
                if (opponentType.equals("h")) {
                    new TicTacToe(scanner, 3, false).initialize();
                } else if (opponentType.equals("c")) {
                    System.out.println("Would you like to play against an easy (e) or hard (h) opponent?");
                    String difficulty = scanner.nextLine();
                    if (difficulty.equals("e")){
                        new TicTacToe(scanner, 3, true).initialize();
                    } else if (difficulty.equals("h")) {
                        new TicTacToeMinimax(scanner).initialize();
                    } else {
                        System.out.println("Please select either 0 for easy or 1 for hard opponent");
                    }
                } else {
                    System.out.println("Please select either 0 for human or 1 for computer opponent");
                }
            } else if (play.equals("n")) {
                System.out.println("Goodbye!");
                break;
            }
        }
    }
}