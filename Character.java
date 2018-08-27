/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.28.0.4148.608b7c78e modeling language!*/


import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.util.*;

import javax.swing.Icon;
import javax.swing.JLabel;

// line 35 "model.ump"
// line 99 "model.ump"
public class Character extends Card implements Drawable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Character Attributes
  private JLabel displayIcon;
  private Point startPos;

  //Character Associations
  private Player player;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Character(String aName, Color displayColor, Point aStartPos)
  {
    super(aName);
    startPos = aStartPos;
    
    //Draw this character as a solid color on the board
    this.displayIcon = new JLabel(new Icon() {
    	
    	@Override
    	public int getIconHeight() {
    		return Board.TILE_HEIGHT;
    	}
    	
    	@Override
    	public int getIconWidth() {
    		return Board.TILE_WIDTH;
    	}

		@Override
		public void paintIcon(Component comp, Graphics g, int x, int y) {
			g.setColor(displayColor);
			g.fillRect(x, y, Board.TILE_WIDTH, Board.TILE_HEIGHT);
		}
    });
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setStartPos(Point aStartPos)
  {
    boolean wasSet = false;
    startPos = aStartPos;
    wasSet = true;
    return wasSet;
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
  public JLabel draw(){
	  return this.displayIcon;
  }
}