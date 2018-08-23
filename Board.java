import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import java.awt.*;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.28.0.4148.608b7c78e modeling language!*/



// line 57 "model.ump"
// line 111 "model.ump"
public class Board
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Board Attributes
	
	/* A Map from the name of a room to the area it takes up.
	 * the area is stored as a Set of horizontal lines, the lines
	 * being represented by an int array. The first element of the
	 * array is the row of the line, second is the minimum column
	 * (inclusive), and third is the maximum column (inclusive).*/
    HashMap<String, Set<int[]>> roomAreas = new HashMap<String, Set<int[]>>();
    HashMap<String, List<Position>> roomEntries = new HashMap<String, List<Position>>();
    HashMap<String, List<Position>> roomPerimeters = new HashMap<String, List<Position>>();
    Set<Position> inaccessibleTiles = new HashSet<Position>();
    Position positions[][] = new Position[25][24];
    
    private static final String TOP_BORDER = "===Kitchen======================bAll room=================Conservatory===";
    private static final String BOT_BORDER = "===lOunge=========================Hall===========================Study===";
    private static final String LEFT_BORDER =  "[[[[[[[[[[[[[[[[[[[[[[[Dining[[[[[[[[[[[[[[[[[[[[[[[[";
    private static final String RIGHT_BORDER = "]]]]]]]]]]]]]]]]]]Billiard]]]]]Library]]]]]]]]]]]]]]]";
    
    Game game;
    
    //A drawable representing an empty space
    Drawable empty = new Drawable() {
    	public String draw() {
    		return " ";
    	}
    };

    public Board(Game game)
    {
      this.game = game;
      
      //Construct JFrame
      SwingUtilities.invokeLater(new Runnable() {
    	  public void run() {
    	      MainFrame frame = new MainFrame("Cluedo", game);
    	      frame.setSize(1024, 576);
    	      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	      frame.setVisible(true);
    	  }
      });
      
      //Fill the board with empty tiles
      for (int r = 0; r < 25; r++) {
    	  for (int c = 0; c < 24; c++) {
    		  positions[r][c] = new Position(c, r);
    		  positions[r][c].setContents(empty);
    	  }
      }

      //Set the areas of rooms
      
      Set<int[]> area = new HashSet<int[]>(
    		  Arrays.asList(
    				  		new int[] {1, 0, 5},
    				  		new int[] {2, 0, 5},
    				  		new int[] {3, 0, 5},
    				  		new int[] {4, 0, 5},
    				  		new int[] {5, 0, 5},
    				  		new int[] {6, 1, 5}
    				  		));
      roomAreas.put("Kitchen", area);
      
      area = new HashSet<int[]>(
    		  Arrays.asList(
    				  		new int[] {1, 10, 13},
    				  		new int[] {2, 8, 15},
    				  		new int[] {3, 8, 15},
    				  		new int[] {4, 8, 15},
    				  		new int[] {5, 8, 15},
    				  		new int[] {6, 8, 15},
    				  		new int[] {7, 8, 15}
    				  		));
      roomAreas.put("Ball Room", area);
      
      area = new HashSet<int[]>(
    		  Arrays.asList(
    				  		new int[] {1, 18, 23},
    				  		new int[] {2, 18, 23},
    				  		new int[] {3, 18, 23},
    				  		new int[] {4, 18, 23},
    				  		new int[] {5, 19, 22}
    				  		));
      roomAreas.put("Conservatory", area);
      
      area = new HashSet<int[]>(
    		  Arrays.asList(
    				  		new int[] {9, 0, 4},
    				  		new int[] {10, 0, 7},
    				  		new int[] {11, 0, 7},
    				  		new int[] {12, 0, 7},
    				  		new int[] {13, 0, 7},
    				  		new int[] {14, 0, 7},
    				  		new int[] {15, 0, 7}
    				  		));
      roomAreas.put("Dining Room", area);
      
      area = new HashSet<int[]>(
    		  Arrays.asList(
    				  		new int[] {8, 18, 23},
    				  		new int[] {9, 18, 23},
    				  		new int[] {10, 18, 23},
    				  		new int[] {11, 18, 23},
    				  		new int[] {12, 18, 23}
    				  		));
      roomAreas.put("Billiard Room", area);
      
      area = new HashSet<int[]>(
    		  Arrays.asList(
    				  		new int[] {14, 18, 22},
    				  		new int[] {15, 17, 23},
    				  		new int[] {16, 17, 23},
    				  		new int[] {17, 17, 23},
    				  		new int[] {18, 18, 22}
    				  		));
      roomAreas.put("Library", area);
      
      area = new HashSet<int[]>(
    		  Arrays.asList(
    				  		new int[] {19, 0, 6},
    				  		new int[] {20, 0, 6},
    				  		new int[] {21, 0, 6},
    				  		new int[] {22, 0, 6},
    				  		new int[] {23, 0, 6},
    				  		new int[] {24, 0, 5}
    				  		));
      roomAreas.put("Lounge", area);
      
      area = new HashSet<int[]>(
    		  Arrays.asList(
    				  		new int[] {18, 9, 14},
    				  		new int[] {19, 9, 14},
    				  		new int[] {20, 9, 14},
    				  		new int[] {21, 9, 14},
    				  		new int[] {22, 9, 14},
    				  		new int[] {23, 9, 14},
    				  		new int[] {24, 9, 14}
    				  		));
      roomAreas.put("Hall", area);
      
      area = new HashSet<int[]>(
    		  Arrays.asList(
    				  		new int[] {21, 17, 23},
    				  		new int[] {22, 17, 23},
    				  		new int[] {23, 17, 23},
    				  		new int[] {24, 18, 23}
    				  		));
      roomAreas.put("Study", area);
      
      //Set the room entry positions
      
      roomEntries.put("Kitchen", Arrays.asList(
    		  	positions[6][4]
    		));
      
      roomEntries.put("Ball Room", Arrays.asList(
		  		positions[5][8],
		  		positions[5][15],
		  		positions[7][9],
		  		positions[7][14]
		  	));
      
      roomEntries.put("Conservatory", Arrays.asList(
		  		positions[4][18]
		  	));
      
      roomEntries.put("Billiard Room", Arrays.asList(
		  		positions[9][18],
		  		positions[12][22]
		  	));
      
      roomEntries.put("Library", Arrays.asList(
		  		positions[14][20],
		  		positions[16][17]
		  	));
      
      roomEntries.put("Study", Arrays.asList(
		  		positions[21][17]
		  	));
      
      roomEntries.put("Hall", Arrays.asList(
		  		positions[18][12],
		  		positions[18][11],
		  		positions[20][14]
		  	));
      
      roomEntries.put("Lounge", Arrays.asList(
		  		positions[19][6]
		  	));
      
      roomEntries.put("Dining Room", Arrays.asList(
		  		positions[15][6],
		  		positions[12][7]
		  	));
      
      //Set the inaccessible positions
      inaccessibleTiles.add(positions[0][0]);
      inaccessibleTiles.add(positions[0][1]);
      inaccessibleTiles.add(positions[0][2]);
      inaccessibleTiles.add(positions[0][3]);
      inaccessibleTiles.add(positions[0][4]);
      inaccessibleTiles.add(positions[0][5]);
      inaccessibleTiles.add(positions[0][6]);
      inaccessibleTiles.add(positions[0][7]);
      inaccessibleTiles.add(positions[0][8]);
      inaccessibleTiles.add(positions[0][10]);
      inaccessibleTiles.add(positions[0][11]);
      inaccessibleTiles.add(positions[0][12]);
      inaccessibleTiles.add(positions[0][13]);
      inaccessibleTiles.add(positions[0][15]);
      inaccessibleTiles.add(positions[0][16]);
      inaccessibleTiles.add(positions[0][17]);
      inaccessibleTiles.add(positions[0][18]);
      inaccessibleTiles.add(positions[0][19]);
      inaccessibleTiles.add(positions[0][20]);
      inaccessibleTiles.add(positions[0][21]);
      inaccessibleTiles.add(positions[0][22]);
      inaccessibleTiles.add(positions[0][23]);
      
      inaccessibleTiles.add(positions[1][6]);
      inaccessibleTiles.add(positions[1][17]);
      
      inaccessibleTiles.add(positions[5][23]);
      
      inaccessibleTiles.add(positions[6][0]);
      
      inaccessibleTiles.add(positions[7][23]);
      
      inaccessibleTiles.add(positions[8][0]);
      
      inaccessibleTiles.add(positions[10][10]);
      inaccessibleTiles.add(positions[10][11]);
      inaccessibleTiles.add(positions[10][12]);
      inaccessibleTiles.add(positions[10][13]);
      inaccessibleTiles.add(positions[10][14]);
      
      inaccessibleTiles.add(positions[11][10]);
      inaccessibleTiles.add(positions[11][11]);
      inaccessibleTiles.add(positions[11][12]);
      inaccessibleTiles.add(positions[11][13]);
      inaccessibleTiles.add(positions[11][14]);
      
      inaccessibleTiles.add(positions[12][10]);
      inaccessibleTiles.add(positions[12][11]);
      inaccessibleTiles.add(positions[12][12]);
      inaccessibleTiles.add(positions[12][13]);
      inaccessibleTiles.add(positions[12][14]);
      
      inaccessibleTiles.add(positions[13][10]);
      inaccessibleTiles.add(positions[13][11]);
      inaccessibleTiles.add(positions[13][12]);
      inaccessibleTiles.add(positions[13][13]);
      inaccessibleTiles.add(positions[13][14]);
      inaccessibleTiles.add(positions[13][23]);
      
      inaccessibleTiles.add(positions[14][10]);
      inaccessibleTiles.add(positions[14][11]);
      inaccessibleTiles.add(positions[14][12]);
      inaccessibleTiles.add(positions[14][13]);
      inaccessibleTiles.add(positions[14][14]);
      inaccessibleTiles.add(positions[14][23]);
      
      inaccessibleTiles.add(positions[15][10]);
      inaccessibleTiles.add(positions[15][11]);
      inaccessibleTiles.add(positions[15][12]);
      inaccessibleTiles.add(positions[15][13]);
      inaccessibleTiles.add(positions[15][14]);
      
      inaccessibleTiles.add(positions[16][0]);
      inaccessibleTiles.add(positions[16][10]);
      inaccessibleTiles.add(positions[16][11]);
      inaccessibleTiles.add(positions[16][12]);
      inaccessibleTiles.add(positions[16][13]);
      inaccessibleTiles.add(positions[16][14]);
      
      inaccessibleTiles.add(positions[18][0]);
      inaccessibleTiles.add(positions[18][23]);
      
      inaccessibleTiles.add(positions[20][23]);
      
      inaccessibleTiles.add(positions[24][6]);
      inaccessibleTiles.add(positions[24][8]);
      inaccessibleTiles.add(positions[24][15]);
      inaccessibleTiles.add(positions[24][17]);
    }
    public enum Direction{
		NORTH,
		EAST,
		SOUTH,
		WEST,
    }

    public boolean setRoomAreas(HashMap<String, Set<int[]>> aRoomAreas){
    if (roomAreas != null) {
    		roomAreas = aRoomAreas;
    		return true;
    }
    else return true;
  }

  public boolean setRoomEntries(HashMap<String, List<Position>> aRoomEntries)
  {
    if (roomAreas != null) {
    		roomEntries = aRoomEntries;
    		return true;
    }
    else return false;
  }

  public HashMap<String, Set<int[]>> getRoomAreas()
  {
    return roomAreas;
  }

  public HashMap<String, List<Position>> getRoomEntries()
  {
    return roomEntries;
  }
  
  public HashMap<String, List<Position>> getRoomPerimeters(){
	  return roomPerimeters;
  }
  
  public boolean isValidMove(Player player, Direction dir) {
	  if (dir == null || player == null) return false;
	  if (isAccessableTile(player.requestPoint(player, dir))) {
			  return true;
		  }
	  else return false;
  }
  public boolean isAccessableTile(Point point) {
	  if (point == null) return false;
	  for (Map.Entry<String, List<Position>> entry : roomPerimeters.entrySet()) {
		  for (Position p : entry.getValue()) {
			  if (point.equals(p)) {
				  return false;
			  }
		  }
	  }
	  return true;
  }
  
  /**
   * Given two positions, decides whether moving
   * between them is valid.
   * 
   * @param	startPos	The starting position. Must be a member of the positions array.
   * @param	endPos		The position to move to. Must be a member of the positions array.
   * 
   * @return	Whether moving between positions is valid.*/
  public boolean moveValid(Position startPos, Position endPos) {
	  
	  int 		sx = startPos.getX(),
			  	sy = startPos.getY(),
			  	ex = endPos.getX(),
			  	ey = endPos.getY();
	  
	  //Check if endPos or startPos is inaccessible
	  if (inaccessibleTiles.contains(endPos) || inaccessibleTiles.contains(startPos)) return false;
	  
	  //Check if there is a room boundary in the way
	  Optional<Room> startRoom = this.inRoom(new Point(sx, sy));
	  boolean startInRoom = startRoom.isPresent();
	  Optional<Room> endRoom = this.inRoom(new Point(ex, ey));
	  boolean endInRoom = endRoom.isPresent();
	  
	  if (startInRoom ^ endInRoom) {
		  //If one pos is in a room but not the other, movement could be blocked
		  //Move is valid if one position is an entry position
		  boolean startIsEntry = startInRoom && roomEntries.get(startRoom.get().getName()).contains(startPos);
		  boolean endIsEntry = endInRoom && roomEntries.get(endRoom.get().getName()).contains(endPos);
		  if (!startIsEntry && !endIsEntry) return false;
	  }
	  
	  return true;
  }

  /**
   * Draws the Board, logging it to the console.*/
  public void draw(){
	  String toDraw = "";
	  
	  int leftBorderNum = 0;
	  int rightBorderNum = 0;
	  
	  toDraw += LEFT_BORDER.charAt(leftBorderNum++) + TOP_BORDER + RIGHT_BORDER.charAt(rightBorderNum++) + "\n";
	  
	  for (int r = 0; r < positions.length; r++) {
		  
		  Position[] row = positions[r];
		  
		  toDraw += LEFT_BORDER.charAt(leftBorderNum++);
		  
		  //Draw the top border:
		  for (int c = 0; c < 24; c++) {
			  toDraw += "-";
			  //Check if entry is blocked to the squares above
			  if (r - 1 >= 0 && moveValid(positions[r][c], positions[r - 1][c])) {
				  toDraw += "  ";
			  } else {
				  toDraw += "--";
			  }
		  }
		  
		  toDraw += "-" + RIGHT_BORDER.charAt(rightBorderNum++) + "\n" + LEFT_BORDER.charAt(leftBorderNum++);
		  
		  //Draw the row and its contents
		  for (int c = 0; c < row.length; c++) {
			  Position cell = positions[r][c];
			  
			  //See if cell to the left is accessible
			  if (c - 1 >= 0 && moveValid(positions[r][c], positions[r][c - 1])) {
				  toDraw += " ";
			  } else {
				  toDraw += "|";
			  }
			  
			  boolean cellHasPlayer = cell.getPlayer() != null;
			  if (inaccessibleTiles.contains(cell)) toDraw += "~~";
			  else toDraw += 	(cellHasPlayer ? cell.getPlayer().getCharacter().draw() : " ") +
					  			cell.getContents().draw();		  	
		  }
		  
		  //Draw the final vertical border
		  toDraw += "|" + RIGHT_BORDER.charAt(rightBorderNum++) + "\n";
	  }
	  
	  //Draw the final horizontal border
	  toDraw += LEFT_BORDER.charAt(leftBorderNum++);
	  for (int i = 0; i < 24; i++) {
		  toDraw += "---";
	  }
	  
	  toDraw += "-" + RIGHT_BORDER.charAt(rightBorderNum++) + "\n";
	  
	  toDraw += LEFT_BORDER.charAt(leftBorderNum++) + BOT_BORDER + RIGHT_BORDER.charAt(rightBorderNum++);
	  
	  System.out.println(toDraw);
  }
  
  public boolean movePlayer(Direction dir, Player player){
	  Point newPoint = player.requestPoint(player, dir);
	  Point currPoint = player.getPoint();
	  
	  //Check if we're going to move out of bounds
	  if (newPoint.x < 0 || newPoint.x > 23 || newPoint.y < 0 || newPoint.y > 24) return false;
	  
	  Position newPos = positions[newPoint.y][newPoint.x];
	  Position currPos = positions[currPoint.y][currPoint.x];
	  
	  if (moveValid(currPos, newPos) && newPos.getPlayer() == null) {
		  player.setPoint(newPoint);
		  
		  //Update the board
		  currPos.setPlayer(null);
		  newPos.setPlayer(player);
		  
		  return true;
	  }
	  else return false;
  }
  
  /**
   * Finds the Room containing a point.
   * 
   * @param	point	The point inside some room.
   * @return		An Optional yielding the Room the Point is in.*/
  public Optional<Room> inRoom(Point point) {
	  
	  for (Map.Entry<String, Set<int[]>> roomArea : this.roomAreas.entrySet()) {
		  for (int[] line : roomArea.getValue()) {
			  
			  //Check if the point is on the line
			  if (point.y == line[0] && point.x >= line[1] && point.x <= line[2]) {
				  //The point is in this Room, find the Room from the name
				  return Game.ROOM_CARDS	.stream()
						  				.filter(r -> r.getName().equals(roomArea.getKey()))
						  				.findFirst();
			  }
 		  }
	  }
	  
	  return Optional.empty();
  }
  
  /**
   * Places the Drawable at a random unnoccupied position in
   * the room.*/
  public void placeInRoom(String room, Drawable toPlace) {
	  
	  List<int[]> lineList = this.roomAreas.get(room).stream().collect(Collectors.toList());
	  
	  int[] randLine = Game.randomFromList(lineList, new Random());
	  
	  int randC = new Random().nextInt(randLine[2] - randLine[1]) + randLine[1];
	  
	  if (positions[randLine[0]][randC].getContents() != empty) placeInRoom(room, toPlace);
	  	  
	  positions[randLine[0]][randC].setContents(toPlace);
  }
  
  /**
   * Places a Player at a random unnoccupied position in
   * the room.
   * 
   * @param	room	The name of the room to place the player in.
   * @param	toPlace	The player to place in the room.*/
  public void placeInRoom(String room, Player toPlace) {
	  
	  List<int[]> lineList = this.roomAreas.get(room).stream().collect(Collectors.toList());
	  
	  int[] randLine = Game.randomFromList(lineList, new Random());
	  
	  int randC = new Random().nextInt(randLine[2] - randLine[1]) + randLine[1];
	  
	  Position newPos = positions[randLine[0]][randC];
	  
	  if (newPos.getPlayer() != null) placeInRoom(room, toPlace);
	  	  
	  newPos.setPlayer(toPlace);
	  toPlace.setPoint(new Point(newPos.getX(), newPos.getY()));
  }

  /**
   * Gets the name of the Room that has
   * just been entered, or an empty optional
   * if a room hasn't been entered.
   * 
   * @param	pos	The position that has been moved to. Must be member of positions array.
   * 
   * @return	An optional yielding the name of the room*/
  public Optional<String> getRoomEntered(Position pos) {
	  for (Map.Entry<String, List<Position>> entry : roomEntries.entrySet()) {
		  for (Position roomEntry : entry.getValue()) {
			  if (roomEntry == pos) {
				  //Player entered the room
				  return Optional.of(entry.getKey());
			  }
		  }
	  }
	  return Optional.empty();
  }
  
  /**
   * @return a Stream of all the positions
   * on the board. The order of positions is
   * left to right, top to bottom.
   * */
  public Stream<Position> positionStream() {
	  
	  Stream.Builder<Position> builder = Stream.builder();
	  
	  for (Position[] row : this.positions) {
		  for (Position cell : row) {
			  builder.accept(cell);
		  }
	  }
	  return builder.build();
  }

  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "roomAreas" + "=" + (getRoomAreas() != null ? !getRoomAreas().equals(this)  ? getRoomAreas().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "roomEntries" + "=" + (getRoomEntries() != null ? !getRoomEntries().equals(this)  ? getRoomEntries().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  ";
  }  
  

  
}