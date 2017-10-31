package main.java;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Collections;
import java.util.Comparator;
import java.lang.Math;

public class DNFFormular implements Iterable<Clause> {

  public ArrayList<Clause> clauses = new ArrayList<Clause>();

  public DNFFormular(ArrayList<String> lines){
    for(String line : lines){
      clauses.add(new Clause(line));
    }
  }

  public int getNumberOfVariables() {
    ArrayList<Integer> allVariables = getAllVariables();
    Iterator<Integer> allVariablesIterator = allVariables.iterator();
    while (allVariablesIterator.hasNext()){
      int x = allVariablesIterator.next();
      if(allVariables.contains(x * -1))
        allVariablesIterator.remove();
    }
    return allVariables.size();
  }

  public int getNumberOfClauses(){
    return this.clauses.size();
  }

  public int getNumberOfLiterals(){
    int num = 0;
    for(Clause clause : clauses)
      num += clause.size();
    return num;
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

  public int getMostOccurantVariable(){
    HashMap<Integer,Integer> x = getVariableOccurance();
    final Optional<Map.Entry<Integer, Integer>> max = x.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue));
    return max.get().getKey();
  }
  public int getMostOccurantVariableCount(){
    HashMap<Integer,Integer> x = getVariableOccurance();
    final Optional<Map.Entry<Integer, Integer>> max = x.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue));
    return max.get().getValue();
  }

  private HashMap<Integer,Integer> getVariableOccurance(){
    ArrayList<Integer> allVariables = new ArrayList<Integer>();
    for (Clause clause : clauses){
      clause.forEach((Integer variable) -> allVariables.add(Math.abs(variable)));
    }
    Collections.sort(allVariables);
    HashMap<Integer,Integer> result = new HashMap<Integer,Integer>();
    for(Integer variable : allVariables){
      Integer x = result.get(variable);
      if(x != null)
        result.put(variable, x + 1);
      else
        result.put(variable, 1);
    }
    return result;
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
