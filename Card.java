/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.28.0.4148.608b7c78e modeling language!*/


import java.util.*;

import javax.swing.JLabel;
import javax.swing.JPanel;

// line 19 "model.ump"
// line 82 "model.ump"
public abstract class Card
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Card Attributes
  private String name;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Card(String aName)
  {
    name = aName;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }
  
  public abstract JLabel getLabel();


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "]";
  }
}