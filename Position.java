/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.28.0.4148.608b7c78e modeling language!*/



// line 69 "model.ump"
// line 116 "model.ump"
public class Position
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Position Attributes
  private Drawable contents;

  //Position Associations
  private Player player;
  private int x;
  private int y;


  public Position(int x, int y)
  {
	  this.x = x;
	  this.y = y;
  }


  public int getX() {
	  return x;
  }
  public int getY() {
	  return y;
  }
  
  public boolean setContents(Drawable aContents)
  {
    boolean wasSet = false;
    contents = aContents;
    wasSet = true;
    return wasSet;
  }

  public Drawable getContents()
  {
    return contents;
  }
  /* Code from template association_GetOne */
  public Player getPlayer()
  {
    return player;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setPlayer(Player aNewPlayer)
  {
    this.player = aNewPlayer;
    return true;
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "contents" + "=" + (getContents() != null ? !getContents().equals(this)  ? getContents().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "player = "+(getPlayer()!=null?Integer.toHexString(System.identityHashCode(getPlayer())):"null");
  }
}