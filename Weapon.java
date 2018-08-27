/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.28.0.4148.608b7c78e modeling language!*/


import java.util.*;

import javax.swing.JLabel;

// line 29 "model.ump"
// line 94 "model.ump"
public class Weapon extends Card implements Drawable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Weapon Attributes
  private char displayCharacter;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Weapon(String aName, char aDisplayCharacter)
  {
    super(aName);
    displayCharacter = aDisplayCharacter;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDisplayCharacter(char aDisplayCharacter)
  {
    boolean wasSet = false;
    displayCharacter = aDisplayCharacter;
    wasSet = true;
    return wasSet;
  }

  public char getDisplayCharacter()
  {
    return displayCharacter;
  }

  @Override
  public JLabel draw(){
          return new JLabel(String.valueOf(this.displayCharacter));
  }

  @Override
  public String toString() {
	  String filling = "| Weapon: " + this.getName() + " (" + getDisplayCharacter() + ")" + " |";
	  String border = "";
	  
	  for (int i = 0; i < filling.length(); i++) {
		  border += "-";
	  }
	  
	  return border + "\n" + filling + "\n" + border + "\n";
  }

	@Override
	public JLabel getLabel() {
		return new WeaponJLabel(this);
	}
}