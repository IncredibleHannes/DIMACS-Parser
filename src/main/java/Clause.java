package main.java;

import java.util.ArrayList;
import java.util.Iterator;

public class Clause implements Iterable<Integer> {

  public ArrayList<Integer> variables = new ArrayList<Integer>();

  public Clause(String line){
    String[] variables = line.split(" ");
    for(String variable : variables){
      if (!variable.startsWith("0", 0))
        this.variables.add(Integer.parseInt(variable));
    }
  }

  public int get(int i){
    return variables.get(i);
  }

  public int size(){
    return variables.size();
  }

  public Iterator<Integer> iterator(){
    return variables.iterator();
  }
}
