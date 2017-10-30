/**
 * SAT-Solving WS 17/18
 * Assignment 02
 *
 * Sven Bizu & Sylvia Siegel
 */

package com.company;

import java.io.*;

public class Main {

     public static void main(String[] args) {
        System.out.println("Please enter the path to your file in DIMACS format");

        // reading in input file location
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(isr);
        String inputPath = null;
        try {
           // D:\Dropbox\03uni\17-18ws\SAT Solving\Assignments\as02\sat_instances\sat_instances\aim-100-1_6-no-1.cnf
            //D:\Dropbox\03uni\17-18ws\SAT Solving\Assignments\as02\sat_instances\sat_instances\goldb-heqc-k2mul.cnf
            inputPath = reader.readLine();
            reader.close();
        } catch (IOException e) {
            System.out.println("No correct input provided. Exiting");
            e.printStackTrace();
        }

        // read the input file
        File inputFile = new File(inputPath);
        SimpleParser parser = new SimpleParser();
         try {
             parser.runParser(inputFile);
         } catch (IOException e) {
             e.printStackTrace();
         }

     }







}
