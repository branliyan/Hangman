package hangman;

/**
 * Hangman Name: Brandon Liyanage
 * Description: A Game of Hangman. The user has 7
 * tries to get the right answer. The Categories are Sports and Movies 
 * Date:April 4 2022 
 * Lost Modified: April 4 2022
 *
 * @author LiyanaB7812
 */
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Hangman {

    /**
     * @param args the command line arguments
     */
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        String[] sports = {"SPORTS", "JORDAN", "CROSBY", "BRADY"};
        String[] movies = {"MOVIES", "DARK KNIGHT", "THE AVENGERS", "MY LITTLE PONY PRINCESS"};

        String[][] words = {sports, movies};

        int randomCat = (int) ((1 - 0 + 1) * Math.random()); // chooses the catagory index

        int randomWord = (int) (((words[randomCat].length - 1) - 1 + 1) * Math.random()) + 1; // chooses the word number id from the array
        String word = words[randomCat][randomWord]; // assigns the word onto a string

        char[] letters = word.toCharArray(); //assigns the word into a char array storing each character which will later all be converted to "-" (this is the array that is used to show what the user has gotten right)
        char[] wordLetters = word.toCharArray();  // assigns the word into a char array storing each charater (this is to be used to compare with []letters to see if the user has infact gotten a right character) 
        char[][] storageGuess = {letters, wordLetters}; // 2d array that stores the original word and the blank word that the user has to guess ([x][y]. The x values stores the values that the user will have to guess. The y values store the unaltered word)
        int wordCount = 1;

        for (int i = 0; i < storageGuess[0].length; i++) {//converts all the letters in the words into "-" characters (not including spaces)
            if (storageGuess[0][i] != ' ') { // if the program detects that there is a letter in the array, it converts it into "-"
                storageGuess[0][i] = '-';
            } else { // if the program detects that there is a space, adds +1 to the word count since it means that there are more than 1 words 
                wordCount++;
            }
        }
        int numErrors = 0;
        int errors = 0;
        boolean winCondition = false; //boolean flag to see whether the person won the game or not
        do {
            System.out.println("Category: " + words[randomCat][0]); // prints out the number of catagories
            System.out.println("Number of Words: " + wordCount);

            for (int i = 0; i < storageGuess[0].length; i++) { // for loop that prints out all the values that the user has gotten right so far
                System.out.print(storageGuess[0][i]);
            }
            System.out.println(" ");
            
            drawFigure(errors); //calls the method that prints out the stick figure depending on how many errors the person has made

            System.out.println("");
            System.out.print("Enter a letter: ");
            char letterGuess = input.next().charAt(0); // gets a character from the user
            letterGuess = Character.toUpperCase(letterGuess); // converts the char that the use entered into upper case so that the program can read it 

            storageGuess = isLetterPresent(storageGuess, letterGuess);  //method that checks if the letter entered is one that is present in the array storing all the values. if it is present, it will figure out which ones the user has gotten right

            if (errorCheck(storageGuess, letterGuess)[0] == true) { // statement that uses the errorCheck method to see if the user entered the wrong character. If it returns true, then it means that the user has made an error
                errors++;
            }

            if (errorCheck(storageGuess, letterGuess)[1] == true) {//if this returns true, then the person has won the game
                winCondition = true;

            }
            
            for (int i = 0; i < 50; i++) { // Adds spaces so that it doesnt clutter the screen
                System.out.println(" ");
            }

        } while (errors != 7 && winCondition == false); // program will keep continuing while the player is guessing and will only stop if the person gets the right answer or losses the game

        if (winCondition == true) {
            System.out.println("Congratulations, You have guessed correct!");
        } else {
            System.out.println("Uh oh, You have lost... Looks like poor man Dave is a goner");
            System.out.println("The word was: " + word);
        }
    }

    public static char[][] isLetterPresent(char storageGuess[][], char letterGuess) { // function to check whether the letter entered by the user is present in the array and also changes the letter if so        
        for (int i = 0; i < storageGuess[0].length; i++) { // checks if the user has gotten the right answer
            if (letterGuess == storageGuess[1][i]) { // this means that the user got the right letter
                storageGuess[0][i] = letterGuess;
            }
        }
        return storageGuess;
    }

    public static boolean[] errorCheck(char storageGuess[][], char letterGuess) { // this method has 2 purposes, the first one is to see whether the person has made an errors and the second one is to see whether the person has one the game 
        boolean condition[] = new boolean[2]; // [0] is the check of whether the person has made an error [1] is the check of whether the person has completed the game 
        condition[0] = true;
        condition[1] = true;
        for (int i = 0; i < storageGuess[0].length; i++) { // traverses the array and checks to see if the it recognizes the letter that the user entered
            if (letterGuess == storageGuess[1][i]) { // if it sees that the user has entered a correct number, then it tells the program to turn the error flag into false
                condition[0] = false;
            }
        }

        for (int i = 0; i < storageGuess[0].length; i++) { // for loop that checks to see whether the user has guessed all the right answers
            if (storageGuess[0][i] != storageGuess[1][i]) { // if it recognizes that the guessed letters are not the same as the letters in the word that the person is trying to guess, then the person has not gotten it right and so it flags it as false
                condition[1] = false; // this means that the user has not one the game yet
            }
        }
        return condition; // if it returns, true, then the person made an error
    }

    public static void drawFigure(int errors) { // this is a procedure that draws out the character depending on how many errors the user has gotten
        if (errors == 1) {
            System.out.println(" O    -{Howdy! The Name's Dave, nice to meet you}");
        }
        if (errors == 2) {
            System.out.println(" O");
            System.out.print(" |");
        }
        if (errors == 3) {
            System.out.println(" O");
            System.out.print("/");
            System.out.print("|");
        }
        if (errors == 4) {
            System.out.println(" O    -{I'm starting to have a bad feeling 'bout this...}");
            System.out.print("/");
            System.out.print("|");
            System.out.println("\\");
        }
        if (errors == 5) {
            System.out.println(" O");
            System.out.print("/");
            System.out.print("|");
            System.out.println("\\");
            System.out.println(" |");
        }
        if (errors == 6) {
            System.out.println(" O    -{Help Me Mommy!!!!! I got one leg left!!}");
            System.out.print("/");
            System.out.print("|");
            System.out.println("\\");
            System.out.println(" |");
            System.out.print("/");
        }
        if (errors == 7) {
            System.out.println(" O");
            System.out.print("/");
            System.out.print("|");
            System.out.println("\\");
            System.out.println("|");
            System.out.print("/ ");
            System.out.println("  \\");
        }

    }

}
