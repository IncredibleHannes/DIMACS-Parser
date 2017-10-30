package main.java;

import java.util.ArrayList;
import java.util.Iterator;

public class DNFFormular implements Iterable<Clause> {

  public ArrayList<Clause> clauses = new ArrayList<Clause>();

  public DNFFormular(ArrayList<String> lines){
    for(String line : lines){
      clauses.add(new Clause(line));
    }
  }

  public int getNumberOfVariables() {
    return getAllVariables().size();
  }

  public int getNumberOfClauses(){
    return this.clauses.size();
  }

  public ArrayList<Integer> getPureLiterals(){
    ArrayList<Integer> pureLiterals = getAllVariables();
    ArrayList<Integer> resultPureLiterals = getAllVariables();
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
    ArrayList<Integer> unitClauses = new ArrayList<Integer>();
    for(Clause clause : clauses){
      if(clause.size() == 2){
        unitClauses.add(clause.get(0));
      }
    }
    return unitClauses;
  }

  private ArrayList<Integer> getAllVariables(){
    ArrayList<Integer> allVariables = new ArrayList<Integer>();
    for (Clause clause : clauses){
      clause.forEach((Integer variable) -> {
        if(!allVariables.contains(variable))
          allVariables.add(variable);
      });
    }
    return allVariables;
  }

  public Iterator<Clause> iterator(){
    return clauses.iterator();
  }
}
