package org.example;

import java.util.Scanner;

public class Hangman {
    private final Scanner scanner;
    private String[] mysteryWords;
    private String selectedWord;
    private boolean[] guessArray;
    private int tries;
    private int triesFailed;

    public Hangman(Scanner scanner, String... mysteryWords) {
        this.mysteryWords = mysteryWords;
        this.tries = 3;
        this.triesFailed = 0;
        this.scanner = scanner;
    }

    public Hangman(int tries, Scanner scanner, String... mysteryWords) {
        this.mysteryWords = mysteryWords;
        this.tries = tries;
        this.triesFailed = 0;
        this.scanner = scanner;
    }

    public void initialize(){
        selectedWord = mysteryWords[(int) Math.floor(Math.random() * mysteryWords.length)];
        this.guessArray = new boolean[selectedWord.length()];
        System.out.println(getGuesses());
        while (true){
            if (triesFailed == tries) {
                System.out.println("You have lost...");
                return;
            }
            if (allArrayElementsTrue(guessArray)){
                System.out.println("You have won!");
                return;
            }
            System.out.println("Enter a letter");
            if (!guess(scanner.nextLine())){
                System.out.println("The mystery word doesn't have this letter");
            }
            drawHangman();
            System.out.println(getGuesses());
        }
    }

    private String getGuesses(){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < guessArray.length; i++){
            if (!guessArray[i]) {
                stringBuilder.append("*");
            } else {
                stringBuilder.append(selectedWord.substring(i, i+1));
            }
        }
        return stringBuilder.toString();
    }

    private boolean allArrayElementsTrue(boolean[] arr){
        for (boolean value : arr){
            if (!value) return false;
        }
        return true;
    }

    private boolean guess(String letter){
        System.out.println("You guessed the letter: " + letter.toUpperCase());
        boolean outcome = false;
        for (int i = 0; i < selectedWord.length(); i++){
            if (selectedWord.substring(i, i+1).equals(letter)){
                guessArray[i] = true;
                outcome = true;
            }
        }
        if (!outcome) triesFailed++;
        return outcome;
    }
    private void drawHangman(){
        double triesAttemptedPercent = (double) triesFailed / tries;
        int switchedSegments = 0;
        if (triesAttemptedPercent <= 0.25 && triesAttemptedPercent != 0){
            switchedSegments = 1;
        } else if (triesAttemptedPercent > 0.25 && triesAttemptedPercent <= 0.5){
            switchedSegments = 2;
        } else if (triesAttemptedPercent > 0.5 && triesAttemptedPercent <= 0.75) {
            switchedSegments = 3;
        } else if (triesFailed == tries ) {
            switchedSegments = 4;
        }
        String[] segments = {" |-------",
                " |      | ",
                " |",
                " |",
                " |",
                " |",
                " |",
                "/|\\"};
        String[] altSegments = {
                " |      O",
                " |     /|\\ ",
                " |     /-\\",
                " |     | |",
        };
        for (int i = 0; i < switchedSegments; i++){
            segments[i+2] = altSegments[i];
        }
        for (String segment : segments){
            System.out.println(segment);
        }
    }
}
