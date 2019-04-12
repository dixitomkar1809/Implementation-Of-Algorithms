/**
 * Author: Omkar Dixit, DC Vishwanath
 */


package ond170030.SP7;

public class HashInteger implements HashInterface{
  Integer field;
  
  // Constructor for int values
  public HashInteger(int value) {
    this.field = new Integer(value);
  }

  // Constructor for string values
  public HashInteger(String value){
    this.field = new Integer(value);
  }

  public int hashCode(){
    return this.field;
  }

  @Override
  public int hashCode2() {
    return this.field + 7;
  }
//  public int hashCode2() {
//    return this.field * this.field;
//  }

  public boolean equals(Object obj){
    return this.field.equals(((HashInteger) obj).field);
  }

  public String toString(){
    return this.field.toString();
  }

}