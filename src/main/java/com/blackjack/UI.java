package com.blackjack;

import java.util.Scanner;

/**
 * Created by admin on 9/5/16.
 */
public class UI {

    //Some methods for displaying output, and getting input

    static Scanner stringScanner = new Scanner(System.in);
    static Scanner numberScanner = new Scanner(System.in);

    public void output(String output) {
        System.out.println(output);
    }

    public String input() {
        return stringScanner.nextLine();
    }


    public String input(String prompt) {
        output(prompt);
        return stringScanner.nextLine();
    }

    // Can add a doubleInput, if needed. Some validation would be useful
    // but this method isn't used in this program. See other programs for validator classes.
    /*

    public int intInput() {
        return numberScanner.nextInt();
    }

    */

    public static void close() {
        numberScanner.close();
        stringScanner.close();
    }

}
