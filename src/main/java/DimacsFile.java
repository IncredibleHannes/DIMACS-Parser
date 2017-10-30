package main.java;

import java.io.*;
import java.util.ArrayList;

public class DimacsFile {

  private ArrayList<String> dimacsFile;

  public DimacsFile(File inputFile) throws IOException {
    dimacsFile = parseFile(inputFile);
  }

  private ArrayList<String> parseFile(File inputFile) throws IOException {
    FileReader fileReader = new FileReader(inputFile);
    BufferedReader reader = new BufferedReader(fileReader);

    ArrayList<String> lines = new ArrayList<String>();
    String line = reader.readLine();
    while (line != null) {
      lines.add(line);
      line = reader.readLine();
    }

    lines.removeIf((String line1) -> {
      return line1.startsWith("c",0) || line1.length() < 2;
    });

    if (!checkValidity(lines)){
      throw new IOException("Invalid input file!");
    }
    return lines;
  }

  private Boolean checkValidity(ArrayList<String> lines){
    return true;
  }

  private ArrayList<ArrayList<Integer>> getIntList(){
    ArrayList<ArrayList<Integer>> tmpDimacsFile = new ArrayList<ArrayList<Integer>>();
    for(String line : dimacsFile){
      if(line.startsWith("p",0))
        continue;
      ArrayList<Integer> numberLine = new ArrayList<Integer>();
      for(String token: line.split(" ")){
        numberLine.add(Integer.parseInt(token));
      }
      tmpDimacsFile.add(numberLine);
    }
    return tmpDimacsFile;
  }

  private ArrayList<Integer> getLiterals(){
    ArrayList<ArrayList<Integer>> intList = getIntList();
    ArrayList<Integer> literals = new ArrayList<Integer>();
    for(ArrayList<Integer> clause : intList) {
      for(int literal : clause) {
        if(!literals.contains(literal))
          literals.add(literal);
      }
    }
    return literals;
  }

  public int getNumberOfVariables() {
    return Integer.parseInt(dimacsFile.get(0).split(" ")[2]);
  }

  public int getNumberOfClauses(){
    return Integer.parseInt(dimacsFile.get(0).split(" ")[3]);
  }

  public int getNumberOfUsedLiterals() {
    return getLiterals().size();
  }

  public int getClauseCount() {
      return dimacsFile.size() - 1;
  }

  public ArrayList<Integer> getPureLiterals(){
    ArrayList<Integer> pureLiterals = getLiterals();
    ArrayList<Integer> resultPureLiterals = getLiterals();
    for(int literal : pureLiterals) {
      if (pureLiterals.contains(literal * -1)){
        resultPureLiterals.remove((Integer) literal);
        resultPureLiterals.remove((Integer) (literal * -1));
      }
    }
    return resultPureLiterals;
  }

  public ArrayList<Integer> getPositivePureLiterals(){
    ArrayList<Integer> pureLiterals = getPureLiterals();
    pureLiterals.removeIf((Integer x) -> {return x < 0;});
    return pureLiterals;
  }

  public ArrayList<Integer> getNegativePureLiterals(){
    ArrayList<Integer> pureLiterals = getPureLiterals();
    pureLiterals.removeIf((Integer x) -> {return x > 0;});
    return pureLiterals;
  }

  public ArrayList<Integer> getUnitClauses(){
    ArrayList<ArrayList<Integer>> intList = getIntList();
    ArrayList<Integer> unitClauses = new ArrayList<Integer>();
    for(ArrayList<Integer> clause : intList){
      if(clause.size() == 2){
        unitClauses.add(clause.get(0));
      }
    }
    return unitClauses;
  }

  public void printOutput(String input) {
      System.out.println("File: " + input);
      System.out.println("Problem line: #vars: " + getNumberOfVariables() + ", #clauses: " + getNumberOfClauses() );
      System.out.println("Literal count: " + getNumberOfUsedLiterals());
      System.out.println("Clause count: " + getClauseCount());
      System.out.println("Positive pure Literals: [" + getPositivePureLiterals().toString() + "]");
      System.out.println("Negative pure Literals: [" + getNegativePureLiterals().toString() + "]");
      System.out.println("Unit clauses: [" + getUnitClauses().toString() + "]");
  }
}
