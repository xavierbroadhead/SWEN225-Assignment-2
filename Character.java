/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.28.0.4148.608b7c78e modeling language!*/


import java.awt.Point;
import java.util.*;

// line 35 "model.ump"
// line 99 "model.ump"
public class Character extends Card implements Drawable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Character Attributes
  private char displayCharacter;
  private Point startPos;

  //Character Associations
  private Player player;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Character(String aName, char aDisplayCharacter, Point aStartPos)
  {
    super(aName);
    displayCharacter = aDisplayCharacter;
    startPos = aStartPos;
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

  public boolean setStartPos(Point aStartPos)
  {
    boolean wasSet = false;
    startPos = aStartPos;
    wasSet = true;
    return wasSet;
  }

  public char getDisplayCharacter()
  {
    return displayCharacter;
  }

  public Point getStartPos()
  {
    return startPos;
  }
  /* Code from template association_GetOne */
  public Player getPlayer()
  {
    return player;
  }

  public void delete()
  {
    Player existingPlayer = player;
    player = null;
    if (existingPlayer != null)
    {
      existingPlayer.delete();
    }
  }

  @Override
  public String draw(){
	  return String.valueOf(getDisplayCharacter());
  }

  @Override
  public String toString(){
	  String filling = "| Character: " + this.getName() + " (" + getDisplayCharacter() + ")" + " |";
	  String border = "";
	  
	  for (int i = 0; i < filling.length(); i++) {
		  border += "-";
	  }
	  
	  return border + "\n" + filling + "\n" + border + "\n";
  }
}