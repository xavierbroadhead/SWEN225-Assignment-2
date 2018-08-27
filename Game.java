/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.28.0.4148.608b7c78e modeling language!*/


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

// line 2 "model.ump"
// line 74 "model.ump"
public class Game extends JFrame
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------
  public static final List<Character> CHARACTER_CARDS = Arrays.asList(	new Character("Miss Scarlett", 		Color.RED, new Point(7, 24)),
		  																new Character("Colonel Mustard", 	Color.YELLOW, new Point(0, 17)),
		  																new Character("Mrs. White",			Color.WHITE, new Point(9, 0)),
		  																new Character("Mr. Green",			Color.GREEN, new Point(14, 0)),
		  																new Character("Mrs. Peacock",		Color.BLUE, new Point(23, 6)),
		  																new Character("Professor Plum",		Color.MAGENTA, new Point(23, 19)));
  
  public static final List<Room> ROOM_CARDS = Arrays.asList(	new Room("Kitchen", 		'K'),
		  														new Room("Ball Room", 		'A'),
		  														new Room("Conservatory", 	'C'),
		  														new Room("Dining Room", 	'D'),
		  														new Room("Cellar", 			'Z'),
		  														new Room("Billiard Room", 	'B'),
		  														new Room("Library", 		'L'),
		  														new Room("Lounge", 			'O'),
		  														new Room("Hall", 			'H'),
		  														new Room("Study", 			'S'));
  
  public static final List<Weapon> WEAPON_CARDS = Arrays.asList(		new Weapon("Candlestick",	'C'),
		  															new Weapon("Dagger",		'D'),
		  															new Weapon("Lead Pipe",		'L'),
		  															new Weapon("Revolver",		'R'),
		  															new Weapon("Rope",			'O'),
		  															new Weapon("Spanner",		'S'));
  
  private List<Character> playingCharacterCards;
  private List<Room> playingRoomCards;
  private List<Weapon> playingWeaponCards;
  private List<Card> playingCards;

  //Game Attributes
  private Hand solution;
  private Player currentPlayer;

  //Game Associations
  private List<Player> players;
  private Board board;
  private Game guiGame;
  
  private Random randGen = new Random();

  //------------------------
  // CONSTRUCTORS
  //------------------------
  public Game(boolean test) {
	  
	  this.playingCharacterCards = new ArrayList<Character>(CHARACTER_CARDS);
	  this.playingRoomCards = new ArrayList<Room>(ROOM_CARDS);
	  this.playingWeaponCards = new ArrayList<Weapon>(WEAPON_CARDS);
	  this.playingCards = new ArrayList<Card>();
	  this.playingCards.addAll(playingCharacterCards);
	  this.playingCards.addAll(playingRoomCards);
	  this.playingCards.addAll(playingWeaponCards);
	  
	  this.board = new Board(this);
	  
	  //Generate a random solution
	  this.solution = new Hand();
	  this.generateSolution();
  }
  
  public Game(){
	  
	  //TODO: Initialise GUI
	  this.playingCharacterCards = new ArrayList<Character>(CHARACTER_CARDS);
	  this.playingRoomCards = new ArrayList<Room>(ROOM_CARDS);
	  this.playingWeaponCards = new ArrayList<Weapon>(WEAPON_CARDS);
	  this.playingCards = new ArrayList<Card>();
	  this.playingCards.addAll(playingCharacterCards);
	  this.playingCards.addAll(playingRoomCards);
	  this.playingCards.addAll(playingWeaponCards);
	  
	  this.board = new Board(this);
      setLayout(new BorderLayout());
      
	  //Generate a random solution
	  this.solution = new Hand();
	  this.generateSolution();
	  
	  //Add the players and deal cards
	  //TODO: Get amount of players with GUI
	  //System.out.println("How many people wish to play? (3-6)");
	  //int numPlayers = this.getIntBounded(3, 6, "Invalid Input. 3-6 People can play.");
	  
	  //this.assignCharacters(numPlayers);
	  this.assignWeapons();
	  
	  //TODO: Dialog with player characters
	  /*for (int p = 0; p < players.size(); p++) {
		  Player player = players.get(p);
		  System.out.println("Player " + (p + 1) + ", you are " + player.getCharacter().getName());
	  }*/
	  
	  //TODO: remove pause method
	  //pause(1000 * players.size());
	  
	  for (;;) {
	  	if (!nextTurn()) break;
	  	
	  	//Make it the next player's turn
	  	int nextIndex = players.indexOf(currentPlayer) + 1;
	  	if (nextIndex >= players.size()) nextIndex = 0;
	  	currentPlayer = players.get(nextIndex);
	  }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setSolution(Hand aSolution)
  {
    boolean wasSet = false;
    solution = aSolution;
    wasSet = true;
    return wasSet;
  }

  public boolean setCards(List<Card> aCards)
  {
    boolean wasSet = false;
    wasSet = true;
    return wasSet;
  }

  public boolean setCurrentPlayer(Player aCurrentPlayer)
  {
    boolean wasSet = false;
    currentPlayer = aCurrentPlayer;
    wasSet = true;
    return wasSet;
  }

  public Hand getSolution()
  {
    return solution;
  }

  public Player getCurrentPlayer()
  {
    return currentPlayer;
  }
  /* Code from template association_GetMany */
  public Player getPlayer(int index)
  {
    Player aPlayer = players.get(index);
    return aPlayer;
  }

  public List<Player> getPlayers()
  {
    List<Player> newPlayers = Collections.unmodifiableList(players);
    return newPlayers;
  }

  public int numberOfPlayers()
  {
    int number = players.size();
    return number;
  }

  public boolean hasPlayers()
  {
    boolean has = players.size() > 0;
    return has;
  }

  public int indexOfPlayer(Player aPlayer)
  {
    int index = players.indexOf(aPlayer);
    return index;
  }
  /* Code from template association_GetOne */
  public Board getBoard()
  {
    return board;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfPlayersValid()
  {
    boolean isValid = numberOfPlayers() >= minimumNumberOfPlayers() && numberOfPlayers() <= maximumNumberOfPlayers();
    return isValid;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPlayers()
  {
    return 3;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfPlayers()
  {
    return 6;
  }
  /* Code from template association_AddMNToOnlyOne */
  public List<Character> getCharacterCards(){
	  return playingCharacterCards;
  }
  public List<Room> getRoomCards(){
	  return playingRoomCards;
  }
  public List<Weapon> getWeaponCards(){
	  return playingWeaponCards;
  }
  public List<Card> getAllCards(){
	  return playingCards;
  }
  public boolean addPlayer(Player aPlayer)
  {
    boolean wasAdded = false;
    if (players.contains(aPlayer)) { return false; }
    if (numberOfPlayers() >= maximumNumberOfPlayers())
    {
      return wasAdded;
    }

    Game existingGame = aPlayer.getGame();
    boolean isNewGame = existingGame != null && !this.equals(existingGame);

    if (isNewGame && existingGame.numberOfPlayers() <= minimumNumberOfPlayers())
    {
      return wasAdded;
    }

    if (isNewGame)
    {
      aPlayer.setGame(this);
    }
    else
    {
      players.add(aPlayer);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePlayer(Player aPlayer)
  {
    boolean wasRemoved = false;
    //Unable to remove aPlayer, as it must always have a game
    if (this.equals(aPlayer.getGame()))
    {
      return wasRemoved;
    }

    //game already at minimum (3)
    if (numberOfPlayers() <= minimumNumberOfPlayers())
    {
      return wasRemoved;
    }
    players.remove(aPlayer);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPlayerAt(Player aPlayer, int index)
  {  
    boolean wasAdded = false;
    if(addPlayer(aPlayer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayers()) { index = numberOfPlayers() - 1; }
      players.remove(aPlayer);
      players.add(index, aPlayer);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePlayerAt(Player aPlayer, int index)
  {
    boolean wasAdded = false;
    if(players.contains(aPlayer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayers()) { index = numberOfPlayers() - 1; }
      players.remove(aPlayer);
      players.add(index, aPlayer);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPlayerAt(aPlayer, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=players.size(); i > 0; i--)
    {
      Player aPlayer = players.get(i - 1);
      aPlayer.delete();
    }
    Board existingBoard = board;
    board = null;
  }

  // line 9 "model.ump"
  
  /**
   * Waits for some amount of time.
   * Logs dots while waiting.
   * 
   * @param	time	The amount of milliseconds to wait.*/
  private void pause(long time) {
	  try {
		int dotInterval = 500;
		for (int i = 0; i < time / dotInterval; i++) {
			System.out.print(".");
			Thread.sleep(dotInterval);
		}
		System.out.println();
	} catch (InterruptedException e) {
		throw new Error(e);
	}
  }
  
  /**
   * Generates the Set of Cards representing
   * the solution.*/
  public void generateSolution() {
	  Character randChar = removeRandomFromList(playingCharacterCards, randGen);
	  Weapon randWeapon = removeRandomFromList(playingWeaponCards, randGen);
	  Room randRoom = removeRandomFromList(playingRoomCards, randGen);
	  
	  this.solution.addCard(randRoom);
	  this.solution.addCard(randWeapon);
	  this.solution.addCard(randChar);
	  
	  this.playingCards.remove(randRoom);
	  this.playingCards.remove(randWeapon);
	  this.playingCards.remove(randChar);
  }

  // line 10 "model.ump"
  /**
   * Creates Player objects to represent the players
   * and assigns them characters randomly. Deals the
   * players cards.
   * 
   * @param	numPlayers	The amount of players in the game*/
  public void assignCharacters(int numPlayers) {
	  this.players = new ArrayList<Player>();
	  List<Character> charPool = new ArrayList<Character>(CHARACTER_CARDS);
	  
	  //Add the players
	  for (int i = 0; i < numPlayers; i++) {
		  players.add(new Player(removeRandomFromList(charPool, randGen), this.board));
	  }
	  
	  //Place the players at their starting positions
	  for (Player player : this.players) {
		  Point startPos = player.getCharacter().getStartPos();
		  this.board.positions[startPos.y][startPos.x].setPlayer(player);
	  }
	  
	  //Deal the cards
	  int i = 0;
	  while (!playingCards.isEmpty()) {
		  players.get(i++ % numPlayers).getHand().addCard(removeRandomFromList(playingCards, randGen));
	  }
	  
	  //Set currPlayer
	  currentPlayer = players.get(0);
  }

  // line 11 "model.ump"
  /**
   * Randomly assigns the weapons to rooms,
   * no room should have more than one weapon.*/
  public void assignWeapons(){
	  List<Weapon> weaponPool = new ArrayList<Weapon>(WEAPON_CARDS);
	  List<Room> roomPool = new ArrayList<Room>(ROOM_CARDS);
	  
	  //Remove cellar, isn't accessible
	  roomPool.remove(ROOM_CARDS.get(4));
	  
	  //Put the weapons in rooms randomly
	  while (!weaponPool.isEmpty()) {
		  this.board.placeInRoom(removeRandomFromList(roomPool, randGen).getName(), removeRandomFromList(weaponPool, randGen));
	  }
  }

  // line 12 "model.ump"
  
  /**
   * Makes an accusation on behalf of the
   * current player. If the accusation is 
   * accurate, the player wins, otherwise,
   * they are excluded from making further
   * moves other than refuting suggestions.
   * 
   * @param	room		The accused room the murder occurred in
   * @param	character	The accused character
   * @param	weapon		The accused murder weapon.
   * 
   * @return	Whether the accusation was accurate. If false,
   * 			the character has been excluded from the game.*/
  public boolean accusation(Room room, Character character, Weapon weapon){
	  
	  for (Card card : solution.getCards()) {
		  if (!(card.equals(room) || card.equals(character) || card.equals(weapon))) {
			  //Incorrect accusation, exclude player from game
			  //TODO: do with dialog
			  //System.out.println("False accusation! You have been excluded from the game, but may still refute suggestions.");
			  currentPlayer.setInGame(false);
			  
			  //Remove their token from the board
			  board	.positionStream()
			  		.filter(p -> p.getPlayer() != null && p.getPlayer().equals(currentPlayer))
			  		.findFirst()
			  		.get()
			  		.setPlayer(null);
			  
			  //Show the correct solution
			  //TODO: show solution with GUI, remove obscure method
			  //System.out.println("The correct solution:");
			  //this.solution.getCards().forEach(c -> System.out.println(c.toString()));
			  
			  //System.out.println("Press C to continue...");
			  
			  //getChar(Arrays.asList('C'), "Press C.");
			  
			  //obscure();
			  
			  return false;
		  }
	  }
	  
	  //Game won
	  //TODO: Game won dialog
	  //System.out.println("Congratulations, you won!");
	  //System.out.println(character.getName() + " murdered the victim in the " + room.getName() + " using the " + weapon.getName() + ".");
	  return true;
  }

 /** Returns a random number between a range specified by the number of dice being rolled
  * 
  *@param numberOfDice number of dice being rolled
  * 
  **/
  public int diceRoll(int numberOfDice){
	    int randomNum = randGen.nextInt(numberOfDice * 6) + 1;
	    return randomNum;
  }

  // line 14 "model.ump"
  
  /**
   * Allows the current player to move on
   * their chosen path, as many spaces as
   * the diceRoll.
   * 
   * @param	diceRoll	The amount of spaces the player may move.*/
  //TODO: redo completely with keyboard listener
  /*public void movePlayer(int diceRoll){
	  System.out.println("Make your move using WASD " + "(Token " + currentPlayer.getCharacter().draw() +"): ");
	  
	  Set<Point> visitedTiles = new HashSet<Point>();
	  visitedTiles.add(currentPlayer.getPoint());
	  
	  while (diceRoll > 0) {
		  System.out.println("You have " + diceRoll + " moves remaining.");
		  
		  char direction = getChar(Arrays.asList('W', 'A', 'S', 'D'), "Enter W, A, S, or D.");
		  
		  Board.Direction dir = null;
		  switch (direction) {
		  case 'W':	dir = Board.Direction.NORTH;	break;
		  case 'A':	dir = Board.Direction.WEST;		break;
		  case 'S':	dir = Board.Direction.SOUTH;	break;
		  case 'D':	dir = Board.Direction.EAST;		break;
		  }
		  
		  Point newPoint = currentPlayer.requestPoint(currentPlayer, dir);
		  Point currPoint = currentPlayer.getPoint();
		  
		  if (visitedTiles.contains(newPoint)) {
			  System.out.println("Can't move that way, tile has already been visited.");
		  } else if (board.movePlayer(dir, currentPlayer)) {
			  //Move successful
			  boolean moveOver = false;
			  
			  //Check if we've entered a room
			  Optional<String> entered = board.getRoomEntered(board.positions[newPoint.y][newPoint.x]);
			  
			  if (entered.isPresent() && !board.inRoom(currPoint).isPresent()) {
				  System.out.println("You've entered the " + entered.get() + "! Would you like to make a suggestion? (Y/N)");
				  
				  char answer = getChar(Arrays.asList('Y', 'N'), "Please enter Y or N.");
				  
				  switch (answer) {
				  
				  case 'Y':
					  //Get suggestion parameters
					  currentPlayer.setRoom(board.inRoom(newPoint).get());
					  
					  System.out.println("Which character do you suspect?");
					  CHARACTER_CARDS.forEach(c -> System.out.println(c.draw() + ": " + c.getName()));
					  
					  char character = getChar(CHARACTER_CARDS.stream().map(c -> c.getDisplayCharacter()).collect(Collectors.toList()),
							  "Enter the letter for a character above.");
					  
					  System.out.println("Which murder weapon do you suspect?");
					  WEAPON_CARDS.forEach(w -> System.out.println(w.draw() + ": " + w.getName()));
					  
					  char weapon = getChar(WEAPON_CARDS.stream().map(w -> w.getDisplayCharacter()).collect(Collectors.toList()),
							  "Enter the letter for a weapon above.");
					  
					  this.suggest( CHARACTER_CARDS.stream().filter(c -> c.getDisplayCharacter() == character).findFirst().get(), 
							  		WEAPON_CARDS.stream().filter(w -> w.getDisplayCharacter() == weapon).findFirst().get());
				  }
				  
				  System.out.println("Would you like to continue using your moves? (Y/N)");
				  
				  answer = getChar(Arrays.asList('Y', 'N'), "Please enter Y or N.");
				  
				  switch (answer) {
				  
				  case 'N': moveOver = true;
				  
				  }
			  }
			  
			  visitedTiles.add(newPoint);
			  diceRoll--;
			  board.draw();
			  if (moveOver) return;
		  } else {
			  //Move unsuccessful, check for the case that we can't move in any direction
			  boolean stuck = true;
			  Board.Direction[] directions = Board.Direction.values();
			  Point curr = currentPlayer.getPoint();
			  Position currPos = board.positions[curr.y][curr.x];
			  
			  for (Board.Direction tryDir : directions) {
				  Point tryPoint = currentPlayer.requestPoint(currentPlayer, tryDir);
				  Position tryPos = board.positions[tryPoint.y][tryPoint.x];
				  
				  if (board.moveValid(currPos, tryPos) && tryPos.getPlayer() == null && !visitedTiles.contains(tryPoint)) {
					  stuck = false;
					  break;
				  }
			  }
			  
			  if (stuck) {
				  System.out.println("You are stuck. Movement over.");
				  return;
			  } else {
				  System.out.println("Couldn't move that way.");
			  }
		  }
	  }
	  
	  System.out.println("Movement over.");
  }*/

  // line 15 "model.ump"
  
  /**
   * Plays the turn for the current player.
   * 
   * In charge of the main game logic.
   * 
   * @return	Whether the game should continue.*/
  public boolean nextTurn(){
	  
	  if (!currentPlayer.getInGame()) {
		  //Player isn't in the game, check for the case that all players are out
		  if (players.stream().allMatch(p -> !p.getInGame())) {
			  //TODO: do with dialog
			  //System.out.println("All players are out... Ending Game.");
			  return false;
		  }
		  
		  //TODO: do with dialog
		  //System.out.println("Skipping player " + (players.indexOf(currentPlayer) + 1) + " because they aren't in the game.");
		  return true;
	  }
	  
	  //Announce player turn
	  //TODO: do with dialog
	  //System.out.println("Player " + (players.indexOf(currentPlayer) + 1) + ", it is your turn! (Token " + currentPlayer.getCharacter().draw() + ")");
	  
	  //Offer options
	  //TODO: redo completely with GUI
	  /*for (;;) {
		  System.out.println("What would you like to do?");
		  System.out.println("(C) View Cards");
		  System.out.println("(L) View Legend");
		  System.out.println("(A) Make Accusation");
		  System.out.println("(M) Move");
		  
		  char answer = getChar(Arrays.asList('C', 'L', 'A', 'M'), "Answer using a single character above.");
		  
		  switch (answer) {
		  
		  case 'C':
			  //View cards
			  
			  for (Card card : currentPlayer.getHand().getCards()) {
				  System.out.println(card.toString());
			  }
			  
			  break;
			  
		  case 'L':
			  //View legend
			  
			  System.out.println("Characters:");
			  CHARACTER_CARDS.forEach(c -> System.out.println(c.draw() + ": " + c.getName()));
			  
			  System.out.println("Weapons:");
			  WEAPON_CARDS.forEach(w -> System.out.println(w.draw() + ": " + w.getName()));
			  
			  System.out.println();
			  break;
			  
		  case 'A':
			  //Make accusation
			  
			  System.out.println("In which Room did the murder occur?");
			  ROOM_CARDS.forEach(c -> System.out.println(c.getDisplayCharacter() + ": " + c.getName()));
			  char roomChar = getChar(
					  ROOM_CARDS
					  .stream()
					  .map(r -> r.getDisplayCharacter())
					  .collect(Collectors.toList()), 
					  "Enter one of the Room letters above.");
			  Room room = ROOM_CARDS	.stream()
					  					.filter(r -> r.getDisplayCharacter() == roomChar)
					  					.findFirst()
					  					.get();
			  
			  System.out.println("Which weapon was used for the murder?");
			  WEAPON_CARDS.forEach(w -> System.out.println(w.getDisplayCharacter() + ": " + w.getName()));
			  char weaponChar = getChar(
					  WEAPON_CARDS
					  .stream()
					  .map(w -> w.getDisplayCharacter())
					  .collect(Collectors.toList()), 
					  "Enter one of the Weapon letters above.");
			  Weapon weapon = WEAPON_CARDS	.stream()
					  						.filter(w -> w.getDisplayCharacter() == weaponChar)
					  						.findFirst()
					  						.get();
			  
			  System.out.println("Finally, who was the murderer?");
			  CHARACTER_CARDS.forEach(c -> System.out.println(c.getDisplayCharacter() + ": " + c.getName()));
			  char characterChar = getChar(
					  CHARACTER_CARDS
					  .stream()
					  .map(c -> c.getDisplayCharacter())
					  .collect(Collectors.toList()), 
					  "Enter one of the Character letters above.");
			  Character character = CHARACTER_CARDS	.stream()
					  								.filter(c -> c.getDisplayCharacter() == characterChar)
					  								.findFirst()
					  								.get();
			  
			  return !accusation(room, character, weapon); 
		
		  case 'M':
			  //Move
			  
			  this.board.draw();
			  
			  //Do diceroll
			  System.out.print("Rolling dice");
			  int dice1 = diceRoll(1);
			  int dice2 = diceRoll(1);
			  int diceTot = dice1 + dice2;
			  pause(2000);
			  System.out.println("Got " + dice1 + " and " + dice2 + ".");
			  
			  //Move player
			  movePlayer(diceTot);
			  
			  return true;
		  }
	  }*/
	  return true;
  }

  // line 16 "model.ump"
  
  /**
   * Make a suggestion on behalf of the current player.
   * 
   * All other players must attempt to refute the
   * suggestion by producing a card matching the
   * suggested murder scenario (current room,
   * character, or weapon).
   * 
   * @param	character	The suggested murderer.
   * @param	weapon		The suggested murder weapon.*/
  public void suggest(Character character, Weapon weapon){
	  Room room = currentPlayer.getRoom();
	  
	  //Find the player with the named character
	  Optional<Player> toMove = players.stream().filter(p -> p.getCharacter().equals(character)).findFirst();
	  boolean movePlayer = toMove.isPresent() && !(toMove.get() == currentPlayer);
	  
	  //Remove the named character and weapon from their current location
	  this.board.positionStream().filter(p -> p.getContents().equals(weapon)).findFirst().get().setContents(board.empty);
	  if (movePlayer) this.board.positionStream().filter(p -> p.getPlayer() != null && p.getPlayer().equals(toMove.get())).findFirst().get().setPlayer(null);
    
	  //Place the named character and weapon in the room
	  this.board.placeInRoom(room.getName(), weapon);
	  if (movePlayer) this.board.placeInRoom(room.getName(), toMove.get());
	  
	  //Now, offer each player a chance to refute
	  int p = players.indexOf(currentPlayer);
	  
	  //A map from the suggested card to whether it has been refuted
	  Map<Card, Boolean> refuted = new HashMap<Card, Boolean>();
	  refuted.put(room, false);
	  refuted.put(character, false);
	  refuted.put(weapon, false);
	  
	  for (int i = 0; i < players.size(); i++, p++) {
		  //Loop through players in clockwise order, starting from current
		  
		  Player curr = players.get(p % players.size());
		  
		  boolean hasRoom = curr.getHand().indexOfCard(room) != -1;
		  boolean hasChar = curr.getHand().indexOfCard(character) != -1;
		  boolean hasWeapon = curr.getHand().indexOfCard(weapon) != -1;
		  
		  //The number of refutation cards the player has:
		  int numHas = (hasRoom ? 1 : 0) + (hasChar ? 1 : 0) + (hasWeapon ? 1 : 0);
		  
		  String playerDescriptor = "Player " + (players.indexOf(curr) + 1) + " (" + curr.getCharacter().draw() + ")";
		  
		  //TODO: use dialogs
		  if (numHas == 0) {
			  //Player has no cards to refute
			  //System.out.println(playerDescriptor + " cannot refute the suggestion!");
		  } else if (numHas == 1) {
			  //Player has a card to refute, do it for them
			  //System.out.println(playerDescriptor + " refutes with card:");
			  if (hasRoom) {
				  //System.out.println(room.toString());
				  refuted.put(room, true);
			  }
			  if (hasChar) {
				  //System.out.println(character.toString());
				  refuted.put(character, true);
			  }
			  if (hasWeapon) {
				  //System.out.println(weapon.toString());
				  refuted.put(weapon, true);
			  }
		  } else {
			  //Player has 2 or more cards to refute, let them choose
			  
			  //TODO: redo completely with GUI
			  
			  /* Wait so that the suggesting player has time
			   * to look away from the screen, and the choosing
			   * player can look instead.*/
			  
			  /*
			  System.out.println(playerDescriptor + " must choose a card to refute. Pass the screen to them and don't look.");
			  pause(3000);
			  
			  System.out.println(playerDescriptor + ", you can refute using one of the following cards:");
			  if (hasRoom) System.out.println("R: \n" + room.toString());
			  if (hasChar) System.out.println("C: \n" + character.toString());
			  if (hasWeapon) System.out.println("W: \n" + weapon.toString());
			  
			  List<java.lang.Character> options = new ArrayList<java.lang.Character>(3);
			  if (hasRoom) options.add('R');
			  if (hasChar) options.add('C');
			  if (hasWeapon) options.add('W');
			  
			  char answer = getChar(options, "Answer using one of the options above.");
			  
			  /* Block out the screen so that when the accusing
			   * player looks back, they don't see the choice.
			  obscure();
			  
			  System.out.println(playerDescriptor + " refutes using card:");
			  
			  switch (answer) {
			  
			  case 'R':
				  System.out.println(room.toString());
				  refuted.put(room, true);
			  	  break;
			  
			  case 'C':
				  System.out.println(character.toString());
				  refuted.put(character, true);
				  break;
				  
			  case 'W':
				  System.out.println(weapon.toString());
				  refuted.put(weapon, true);
				  break;
			  
			  }*/
		  }
	  }
	  
	  //Print a summary of the refutations
	  
	  //TODO: redo with dialog
	  /*
	  if (refuted.get(room)) {
		  System.out.println("Murder scenario " + room.getName() + " has been proven false.");
	  } else {
		  System.out.println("Murder scenario " + room.getName() + " could not be proven false.");
	  }
	  
	  if (refuted.get(character)) {
		  System.out.println("Murder scenario " + character.getName() + " has been proven false.");
	  } else {
		  System.out.println("Murder scenario " + character.getName() + " could not be proven false.");
	  }
	  
	  if (refuted.get(weapon)) {
		  System.out.println("Murder scenario " + weapon.getName() + " has been proven false.");
	  } else {
		  System.out.println("Murder scenario " + weapon.getName() + " could not be proven false.");
	  }
	  
	  System.out.println("Press C to continue and hide these clues...");
	  
	  getChar(Arrays.asList('C'), "Press C.");
	  
	  obscure();*/
  }
  
  public String toString() {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "solution" + "=" + (getSolution() != null ? !getSolution().equals(this)  ? getSolution().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "currentPlayer" + "=" + (getCurrentPlayer() != null ? !getCurrentPlayer().equals(this)  ? getCurrentPlayer().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "board = "+(getBoard()!=null?Integer.toHexString(System.identityHashCode(getBoard())):"null");
  }
  
  /**
   * Returns a random list item.
   * 
   * @param	list	The list
   * @return		The item chosen from the list.*/
  public static <T> T randomFromList(List<T> list, Random randGen) {
	  int index = randGen.nextInt(list.size());
	  return list.get(index);
  }
  
  /**
   * Returns and removes a random list item.
   * 
   * @param	list	The list
   * @return		The item removed from the list*/
  public static <T> T removeRandomFromList(List<T> list, Random randGen) {
	  int index = randGen.nextInt(list.size());
	  return list.remove(index);
  }
  
  public static void main(String[] args) {
	  new Game();
  }
}