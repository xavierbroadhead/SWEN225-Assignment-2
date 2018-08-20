/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.28.0.4148.608b7c78e modeling language!*/
import java.util.*;

import java.awt.*;


// line 43 "model.ump"
// line 105 "model.ump"
public class Player
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Player Attributes
  private Room room;
  private boolean inGame;

  //Player Associations
  private Point point;
  private Game game;
  private Character character;
  private Hand hand;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Player(Character character, Board board)
  {
    this.inGame = true;
    this.character = character;
    this.hand = new Hand();
    this.point = this.character.getStartPos();
  }
  //------------------------
  // INTERFACE
  //------------------------

  public boolean setRoom(Room aRoom)
  {
    boolean wasSet = false;
    room = aRoom;
    wasSet = true;
    return wasSet;
  }

  public boolean setInGame(boolean aInGame)
  {
    boolean wasSet = false;
    inGame = aInGame;
    wasSet = true;
    return wasSet;
  }

  public Room getRoom()
  {
    return room;
  }

  public boolean getInGame()
  {
    return inGame;
  }
  /* Code from template association_GetOne */
  public Point getPoint()
  {
    return this.point;
  }

  public boolean hasPosition()
  {
    boolean has = point != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_GetOne */
  public Character getCharacter()
  {
    return character;
  }
  /* Code from template association_GetOne */
  public Hand getHand()
  {
    return hand;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setPoint(Point aNewPoint)
  {
    this.point = aNewPoint;
    return true;
  }
  
  public Point requestPoint(Player player, Board.Direction dir) {
	  if (dir == null) return null;
	  if (dir == Board.Direction.NORTH) {
		  return new Point((int) this.point.getX(), (int) this.point.getY() - 1);
	  }
	  if (dir == Board.Direction.EAST) {
		  return new Point((int) this.point.getX() + 1, (int) this.point.getY());
	  }
	  if (dir == Board.Direction.SOUTH) {
		  return new Point((int) this.point.getX(), (int) this.point.getY() + 1);
	  }
	  else {
		  return new Point((int) this.point.getX() - 1, (int) this.point.getY());
	  }
	  
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setGame(Game aGame)
  {
    boolean wasSet = false;
    //Must provide game to player
    if (aGame == null)
    {
      return wasSet;
    }

    //game already at maximum (6)
    if (aGame.numberOfPlayers() >= Game.maximumNumberOfPlayers())
    {
      return wasSet;
    }
    
    Game existingGame = game;
    game = aGame;
    if (existingGame != null && !existingGame.equals(aGame))
    {
      boolean didRemove = existingGame.removePlayer(this);
      if (!didRemove)
      {
        game = existingGame;
        return wasSet;
      }
    }
    game.addPlayer(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setHand(Hand aHand)
  {
    boolean wasSet = false;
    if (aHand == null)
    {
      return wasSet;
    }

    Hand existingHand = hand;
    hand = aHand;
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Point existingPoint = point;
    point = null;
    Game placeholderGame = game;
    this.game = null;
    if(placeholderGame != null)
    {
      placeholderGame.removePlayer(this);
    }
    Character existingCharacter = character;
    character = null;
    if (existingCharacter != null)
    {
      existingCharacter.delete();
    }
    Hand placeholderHand = hand;
    this.hand = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "inGame" + ":" + getInGame()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "room" + "=" + (getRoom() != null ? !getRoom().equals(this)  ? getRoom().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "position = "+(getPoint()!=null?Integer.toHexString(System.identityHashCode(getPoint())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "character = "+(getCharacter()!=null?Integer.toHexString(System.identityHashCode(getCharacter())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "hand = "+(getHand()!=null?Integer.toHexString(System.identityHashCode(getHand())):"null");
  }
}