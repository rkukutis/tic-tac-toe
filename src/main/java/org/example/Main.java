package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

//        Hangman hangman = new Hangman(3, scanner, "pasta", "hello", "world");
//        hangman.initialize();

        var ticTacToe = new TicTacToe(scanner, 4, true);
        ticTacToe.initialize();
    }
}