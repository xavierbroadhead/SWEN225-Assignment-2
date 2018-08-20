/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.28.0.4148.608b7c78e modeling language!*/


import java.util.*;

// line 25 "model.ump"
// line 89 "model.ump"
public class Room extends Card
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------
	
	private final char displayCharacter;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Room(String aName, char displayCharacter)
  {
    super(aName);
    this.displayCharacter = displayCharacter;
  }
  
  @Override
  public String toString() {
	  String filling = "| Room: " + this.getName() + " (" + displayCharacter + ")" + " |";
	  String border = "";
	  
	  for (int i = 0; i < filling.length(); i++) {
		  border += "-";
	  }
	  
	  return border + "\n" + filling + "\n" + border + "\n";
  }

	/**
	 * @return the displayCharacter
	 */
	public char getDisplayCharacter() {
		return displayCharacter;
	}

}