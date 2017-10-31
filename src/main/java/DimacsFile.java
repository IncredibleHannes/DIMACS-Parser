package main.java;

import java.io.*;
import java.util.ArrayList;

public class DimacsFile {

  private DNFFormular dnfFormular;
  private int numberVariables;
  private int numberClauses;

  public DimacsFile(File inputFile) throws IOException {
    dnfFormular = parseFile(inputFile);
  }

  private DNFFormular parseFile(File inputFile) throws IOException {
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
    numberVariables = Integer.parseInt(lines.get(0).split(" ")[2]);
    numberClauses = Integer.parseInt(lines.get(0).split(" ")[3]);
    lines.remove(0);
    return new DNFFormular(lines);
  }

  private Boolean checkValidity(ArrayList<String> lines){
    return true;
  }

  public int getNumberOfVariables() {
    return dnfFormular.getNumberOfVariables();
  }

  public int getNumberOfClauses(){
    return dnfFormular.getNumberOfClauses();
  }

  public int getNumberOfLiterals(){
    return dnfFormular.getNumberOfLiterals();
  }

  public ArrayList<Integer> getPureLiterals(){
    return dnfFormular.getPureLiterals();
  }

  public ArrayList<Integer> getPositivePureLiterals(){
    return dnfFormular.getPositivePureLiterals();
  }

  public ArrayList<Integer> getNegativePureLiterals(){
    return dnfFormular.getNegativePureLiterals();
  }

  public ArrayList<Integer> getUnitClauses(){
    return dnfFormular.getUnitClauses();
  }

  public int getMostOccurantVariable(){
    return dnfFormular.getMostOccurantVariable();
  }

  public int getMostOccurantVariableCount(){
    return dnfFormular.getMostOccurantVariableCount();
  }

  public void printOutput(String input) {
      System.out.println("File: " + input);
      System.out.println("Problem line: #vars: " + numberVariables + ", #clauses: " + numberClauses );
      System.out.println("Variable count:\t\t\t" + getNumberOfVariables());
      System.out.println("Clauses count:\t\t\t" + getNumberOfClauses());
      System.out.println("Literals count:\t\t\t" + getNumberOfLiterals());
      System.out.println("Positive pure Literals: \t[" + getPositivePureLiterals().toString() + "]");
      System.out.println("Negative pure Literals: \t[" + getNegativePureLiterals().toString() + "]");
      System.out.println("Most ofte ocurring variable:\t" + getMostOccurantVariable());
      System.out.println("Times this varibale Occure:\t" + getMostOccurantVariableCount());
      System.out.println("Unit clauses: \t\t\t[" + getUnitClauses().toString() + "]");
  }
}
