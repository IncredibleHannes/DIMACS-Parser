package main.java;

import java.io.*;
import java.util.ArrayList;

public class DimacsFile {

  private DNFFormular dnfFormular;

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

  public void printOutput(String input) {
      System.out.println("File: " + input);
      System.out.println("Problem line: #vars: " + getNumberOfVariables() + ", #clauses: " + getNumberOfClauses() );
      System.out.println("Positive pure Literals: [" + getPositivePureLiterals().toString() + "]");
      System.out.println("Negative pure Literals: [" + getNegativePureLiterals().toString() + "]");
      System.out.println("Unit clauses: [" + getUnitClauses().toString() + "]");
  }
}
