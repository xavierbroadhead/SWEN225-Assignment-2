import org.junit.*;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.fail;
import java.awt.Point;
import java.util.*;

public class Tests {
	Game game = new Game(true);
	Board testboard = new Board(game);
	
	@Test
	public void testValidMove() {
		if(!testboard.moveValid(new Position(6, 5), new Position(6, 4))) {
			fail("Movement between these two positions should be valid");
		}
	}
	@Test
	public void testInvalidMove() {
		if (testboard.moveValid(new Position(6,4), new Position(5,4))) {
			fail("invalid move");
		}
	}
	@Test
	public void testRemoveRandomFromList() {
		List<Character> randomListCharacters = new ArrayList<Character>();
		randomListCharacters.add(new Character("Miss Scarlett", 'S', new Point(7, 24)));
		randomListCharacters.add(new Character("Colonel Mustard", 'C', new Point(0, 17)));
		randomListCharacters.add(new Character("Mrs. White", 'W', new Point(9, 0)));
		
		Game.removeRandomFromList(randomListCharacters, new Random());
		
		if (randomListCharacters.size() != 2) {
			fail("Error in removeRandomFromList method, didn't remove element from list");
		}
	}
	@Test
	public void testRandomFromList() {
		List<Character> randomListCharacters = Arrays.asList(	new Character("Miss Scarlett", 'S', new Point(7, 24)),
				new Character("Colonel Mustard", 'C', new Point(0, 17)),
				new Character("Mrs. White", 'W', new Point(9, 0)));
		Character charBuffer = Game.randomFromList(randomListCharacters, new Random());
		if (!(charBuffer instanceof Character && charBuffer != null)) {
			fail("Error in randomFromList method");
		}
	}
	@Test
	public void testInRoom() {
		if (testboard.inRoom(new Point(3, 4)).get().getName() != "Kitchen") {
			fail("Point should be within Kitchen");
		}
	}
	@Test
	public void testPlaceInRoom() {
		Player player = new Player(new Character("Miss Scarlett", 'S', new Point(7, 24)), testboard);
		testboard.placeInRoom("Kitchen", player);
		if (testboard.inRoom(player.getPoint()).get().getName() != "Kitchen") {
			fail("Player should be within kitchen");
		}
	}
	@Test
	public void testsetSolution() {
		Hand solution = new Hand();
		Room room = new Room("Ball Room", 		'A');
		Weapon weapon = new Weapon("Dagger",		'D');
		Character character = new Character("Mrs. Peacock",		'M', new Point(23, 6));
		solution.addCard(room);
		solution.addCard(weapon);
		solution.addCard(character);
		game.setSolution(solution);
		if (game.getSolution() != solution) {
			fail("Error in setSolution, could not set solution");
		}
	}
	@Test
	public void testAccusation() {
		Hand solution = new Hand();
		Room room = new Room("Ball Room", 		'A');
		Weapon weapon = new Weapon("Dagger",		'D');
		Character character = new Character("Mrs. Peacock",		'M', new Point(23, 6));
		solution.addCard(room);
		solution.addCard(weapon);
		solution.addCard(character);
		game.setSolution(solution);
		if (!game.accusation(room, character, weapon)) {
			fail("accusation method failed");
		}
	}
	@Test
	public void testSetRoomAreas() {
		HashMap<String, Set<int[]>> roomAreas = new HashMap<String, Set<int[]>>();
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
	      if (!testboard.setRoomAreas(roomAreas)) {
	    	  	fail("Could not set room areas");
	      }
	}
	
	@Test
	public void testSetRoomEntries() {
		Position positions[][] = new Position[25][24];
		HashMap<String, List<Position>> roomEntries = new HashMap<String, List<Position>>();
		roomEntries.put("Kitchen", Arrays.asList(
    		  	positions[6][4]
    		));
		if (!testboard.setRoomEntries(roomEntries)) {
			fail("Could not set room entries");
		}
	}
	@Test
	public void testMovePlayer1() {
		Player player = new Player(new Character("Miss Scarlett", 'S', new Point(7, 24)), testboard);
		if (!testboard.movePlayer(Board.Direction.NORTH, player)) {
			fail("Error in movePlayer method, could not move player north when it was a valid move");
		}
	}
	@Test
	public void testMovePlayer2() {
		Player player = new Player(new Character("Miss Scarlett", 'S', new Point(7, 24)), testboard);
		if (testboard.movePlayer(Board.Direction.SOUTH, player)) {
			fail("Error in movePlayer method, should have been an invalid move");
		}
	}
	@Test
	public void testSetName() {
		Character character = new Character("Miss Scarlett", 'S', new Point(7, 24));
		character.setName("Mr Plum");
		if (character.getName() != "Mr Plum") {
			fail("Couldn't set character name");
		}
	}
	@Test
	public void testDiceRoll() {
		//for loop to increase tests due to randomness
		for (int i = 0; i < 100; i++) {
			if (game.diceRoll(1) < 1 || game.diceRoll(1) > 6) {
				fail("dice rolled out of range");
			}
		}
	}
	@Test
	public void testGenerateSolution() {
		game.generateSolution();
		Hand hand = game.getSolution();
		Card card1 = hand.getCard(0);
		Card card2 = hand.getCard(1);
		Card card3 = hand.getCard(2);
		for (Card card : game.getAllCards()) {
			if (card.equals(card1) || card.equals(card2) || card.equals(card3)) {
				fail("Solution not generated correctly");
			}
		}
	}
	@Test
	public void testSetPoint() {
		Player player = new Player(new Character("Miss Scarlett", 'S', new Point(7, 24)), testboard);
		if (!player.setPoint(new Point(6,4))){
			fail("Couldn't set new point");
		}
	}
	@Test
	public void testSetRoom() {
		Player player = new Player(new Character("Miss Scarlett", 'S', new Point(7, 24)), testboard);
		if (!player.setRoom(new Room("Kitchen", 'K'))) {
			fail("Couldn't set new room");
		}
	}
	@Test 
	public void testCreateHand() {
		Hand hand = new Hand();
		hand.addCard(new Room("Kitchen",'K'));
		hand.addCard(new Weapon("Dagger", 'D'));
		hand.addCard(new Character("Miss Scarlett", 'S', new Point(7,24)));
		if (hand.numberOfCards() != 3) {
			fail("Hand wasn't created correctly");
		}
	}
	@Test
	public void testDisplayCharacter() {
		Character character = new Character("Miss Scarlett", 'S', new Point(7, 24));
		Room room = new Room("Kitchen", 'K');
		Weapon weapon = new Weapon("Dagger", 'D');
		if (character.getDisplayCharacter() != 'S' || room.getDisplayCharacter() != 'K' 
				|| weapon.getDisplayCharacter() != 'D') {
			fail("Incorrect display character, one of card subclasses incorrectly instantiated");
		}
	}
	@Test
	public void testGetName() {
		Character character = new Character("Miss Scarlett", 'S', new Point(7, 24));
		Room room = new Room("Kitchen", 'K');
		Weapon weapon = new Weapon("Dagger", 'D');
		if (character.getName() != "Miss Scarlett" || room.getName() != "Kitchen" || weapon.getName() != "Dagger") {
			fail("Incorrect name, one of card subclasses incorrectly instantiated");
		}
	}
}