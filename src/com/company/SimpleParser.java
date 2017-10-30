package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SimpleParser {

    private int numberOfVariables;
    private int numberOfClauses;
    // counts the seen literals, used to compute acutal number of variables & count # of occurrences of a variable
    private Map<String, Integer> usedLiterals;
    private int clauseCount;
    private Map<String, Integer> usedVariables;
    private ArrayList<String> purePositiveLiterals;
    private ArrayList<String> pureNegativeLiterals;
    private ArrayList<String> unitClauses;

    public void runParser(File inputFile) throws IOException {
        try {
            parseFile(inputFile);
        } catch (FileNotFoundException e) {
            System.out.println("File "+inputFile.toString()+"not found. Please restart. ");
            throw e;
            //e.printStackTrace();
            //System.exit(1);
        } catch (IOException e) {
            System.out.println("Could not read the file. Try again.");
            throw e;
            //e.printStackTrace();
           // System.exit(1);
        }
        createOutput(inputFile.toString());
    }

    /**
     * parses the input file, initiating all used values and updating them accordingly
     * @param inputFile
     * @throws IOException if an error occurs while reading the file
     */
    private void parseFile(File inputFile) throws IOException {

        FileReader fileReader = new FileReader(inputFile);
        BufferedReader reader = new BufferedReader(fileReader);
        String line = reader.readLine();
        Boolean readingClauses = false;
        // skipping the comment section
        while (line.startsWith("c")) {
            line = reader.readLine();
        }
        if (line.startsWith("p")){
            String[] lines = line.split(" ");
            if (!lines[1].equalsIgnoreCase("cnf")){
                System.err.println("Provided file is not in cnf. Please input a file in cnf format. ");
                throw new IOException("wrong input file");
            }

            readingClauses = true;
            // initiate all counters
            numberOfVariables = Integer.parseInt(lines[2]);
            numberOfClauses = Integer.parseInt(lines[3]);
            usedLiterals = new HashMap<>();
            clauseCount = 0;
            unitClauses = new ArrayList<>();
        }
        while (readingClauses){
            line = reader.readLine();
            if (line == null){
                readingClauses = false;
                break;
            }
            else {
                clauseCount ++;
                String[] lines = line.split(" ");
                int literalCount = 0;
                for (String l:lines) {
                    if (l.equals("0")){
                        if (literalCount == 1){
                            unitClauses.add(String.valueOf(clauseCount));
                        }
                        break;
                    }
                    else{
                        addToHashMap(usedLiterals, l, 1);
                        literalCount ++;
                    }
                }
            }
        }

    }


    /**
     * creates the output stats of a parsed file
     * @param input inputpath
     */
    private void createOutput(String input) {
        System.out.println("File: " + input);
        System.out.println("Problem line: #vars: " +numberOfVariables+ ", #clauses: " +numberOfClauses );
        Integer variableCount = computeVariableCount();
        System.out.println("Variable count: " +variableCount);
        System.out.println("Literal count: " + usedLiterals.size());
        System.out.println("Clause count: " +clauseCount);
        Map.Entry maxOccurancePair = findMaxOccuranceVariable();
        System.out.println("Maximal Occurrence of Variable " +maxOccurancePair.getKey()+ " with " +maxOccurancePair.getValue()+  " occurrence");
        System.out.println("Positive pure Literals: [" + purePositiveLiterals.toString() + "]");
        System.out.println("Negative pure Literals: [" + pureNegativeLiterals.toString() + "]");
        System.out.println("Unit clauses: [" + unitClauses + "]");
        //result is not the same as given in the assignments example but looks correct when you look into the file
        // except unit clauses are not only the clauses that just contain one literal
    }

    /**
     * finds the coccurrance of the most abundant variable
     * @return Map.Entry set of the most abundant variable
     */
    private Map.Entry findMaxOccuranceVariable() {
        Map.Entry entry = null;
        for (Map.Entry m :usedVariables.entrySet()){
            if (entry==null){
                entry = m;
            }
            Integer valOld = (Integer) entry.getValue();
            Integer valNew = (Integer) m.getValue();
            if(valNew > valOld){
                entry = m;
            }
        }
        return entry;
    }

    /**
     * Merges the positive & negative literals together into one variable count
     * additionally computes the pure positive & pure negative literals and saves the result in the class variable
     * @return the number of found variables
     */
    private Integer computeVariableCount() {
        Integer varCount = 0;
        usedVariables = new HashMap<>();
        purePositiveLiterals = new ArrayList<>();
        pureNegativeLiterals = new ArrayList<>();

        for (Map.Entry m:usedLiterals.entrySet()) {
            Integer val  = (Integer) m.getValue();
            String key = (String) m.getKey();

            if (key.startsWith("-")){
                // found negative occurance
                String keyp = key.substring(1);
                addToHashMap(usedVariables, keyp, val);
                if (purePositiveLiterals.contains(keyp)){
                    purePositiveLiterals.remove(keyp);
                }
                else{
                    pureNegativeLiterals.add(keyp);
                }
            }
            else{ // positive occurence of variable
                addToHashMap(usedVariables, key, val);
                if (pureNegativeLiterals.contains(key)){
                    pureNegativeLiterals.remove(key);
                }
                else {
                    purePositiveLiterals.add(key);
                }
            }
        }
        varCount = usedVariables.size();
        return varCount;
    }

    /**
     * helper class to update an entrance in an hashmap. The key gets added to map. If the key already exists a new value
     * is assigned containing the sum of the previous value and value.
     * @param map
     * @param key
     * @param value
     */
    private void addToHashMap(Map map, String key, Integer value){
        Integer res = (Integer) map.get(key);
        if (res != null){
            map.put(key, (res+value));
        }
        else {
            map.put(key, value);
        }
    }

    public int getNumberOfClauses() {
        return numberOfClauses;
    }

    public int getNumberOfVariables() {
        return numberOfVariables;
    }

    public int getNumberOfUsedLiterals() {
        return usedLiterals.size();
    }

    public int getClauseCount() {
        return clauseCount;
    }
}
