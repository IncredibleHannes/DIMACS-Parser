/**
 * SAT-Solving WS 17/18
 * Assignment 02
 *
 * Sven Bizu & Sylvia Siegel
 */

package main.java;

import java.io.*;

public class Main {

     public static void main(String[] args) {
        System.out.println("Please enter the path to your file in DIMACS format");

        // reading in input file location
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(isr);
        String inputPath = null;
        try {
            inputPath = reader.readLine();
            reader.close();
        } catch (IOException e) {
            System.out.println("No correct input provided. Exiting");
            e.printStackTrace();
        }

        // read the input file
        File inputFile = new File(inputPath);
         try {
             DimacsFile parser = new DimacsFile(inputFile);
             parser.printOutput(inputPath);
         } catch (IOException e) {
             e.printStackTrace();
         }

     }







}
