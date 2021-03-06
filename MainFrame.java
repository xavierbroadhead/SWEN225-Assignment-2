import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.swing.*;
 
public class MainFrame extends JFrame {
	
    private Game game;
    static int playerCount = 0;
    
    private JLabel currentPlayerName;
    private JPanel dice;
    private JPanel cards;
    
    //Variables for moving
    private Player movingPlayer;
    private Set<Point> visitedTiles;
    private int numMoves;
    
    private Weapon selectedWeapon;
    private Character selectedCharacter;
    private Room selectedRoom;
    
    public MainFrame(final String title, final Game game) {
    	
        super(title);
        this.game = game;
        
        getPlayerCount(() -> {
        	
        	tellPlayerCharacters(() -> {
        		
        		game.assignWeapons();
        		
        		//Set layout manager
                this.getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
                
                //Create the menu bar
                JMenuBar menuBar = new JMenuBar();
                JMenu gameMenu = new JMenu("Game");
                menuBar.add(gameMenu);
                menuBar.add(Box.createHorizontalGlue());
                menuBar.setLayout(new FlowLayout(FlowLayout.LEFT));
                
                JMenuItem legend = new JMenuItem("Legend");
                legend.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						
						JPanel inDialog = new JPanel();
						inDialog.setLayout(new BoxLayout(inDialog, BoxLayout.Y_AXIS));
						
						String weaponText = "<html>";
						for (Weapon weapon : Game.WEAPON_CARDS) {
							weaponText += weapon.getDisplayCharacter() + ": " + weapon.getName() + "<br>";
						}
						
						JLabel weaponLabel = new JLabel(weaponText);
						
						inDialog.add(weaponLabel);
						
						for (Character character : Game.CHARACTER_CARDS) {
							JLabel charLabel = new JLabel(character.getName());
							charLabel.setForeground(character.getColor());
							inDialog.add(charLabel);
						}
						
						JOptionPane.showMessageDialog(MainFrame.this, inDialog);
					}
                	
                });
                JMenuItem accusation = new JMenuItem("Accusation");
                
                accusation.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						
						//Get the room
						Object roomString = JOptionPane.showInputDialog(	MainFrame.this, 
																	"Select a room.",
																	"Room Select",
																	JOptionPane.QUESTION_MESSAGE,
																	null,
																	Game.ROOM_CARDS.stream().map(r -> r.getName()).collect(Collectors.toList()).toArray(),
																	null);
						
						Room room = Game.ROOM_CARDS	.stream()
													.filter(r -> r.getName().equals(roomString))
													.findFirst()
													.get();
						
						//Get the weapon
						Object weaponString = JOptionPane.showInputDialog(	MainFrame.this, 
																	"Select a weapon.",
																	"Weapon Select",
																	JOptionPane.QUESTION_MESSAGE,
																	null,
																	Game.WEAPON_CARDS.stream().map(r -> r.getName()).collect(Collectors.toList()).toArray(),
																	null);
						
						Weapon weapon = Game.WEAPON_CARDS	.stream()
													.filter(r -> r.getName().equals(weaponString))
													.findFirst()
													.get();
						
						//Get the character
						Object characterString = JOptionPane.showInputDialog(	MainFrame.this, 
																	"Select a character.",
																	"Character Select",
																	JOptionPane.QUESTION_MESSAGE,
																	null,
																	Game.CHARACTER_CARDS.stream().map(r -> r.getName()).collect(Collectors.toList()).toArray(),
																	null);
						
						Character character = Game.CHARACTER_CARDS	.stream()
													.filter(r -> r.getName().equals(characterString))
													.findFirst()
													.get();
						
						game.accusation(room, character, weapon);
					}
                	
                });
                
                gameMenu.add(legend);
                gameMenu.add(accusation);
                
                this.add(menuBar);

                game.getBoard().draw();
                
                this.add(game.getBoard().panel);
                
                //Add the player's controls
                JPanel playerDashboard = new JPanel();
                playerDashboard.setLayout(new BoxLayout(playerDashboard, BoxLayout.Y_AXIS));
                currentPlayerName = new JLabel();
                currentPlayerName.setFont(new Font("Arial", Font.PLAIN, 28));
                playerDashboard.add(currentPlayerName);
                
                //Add the dice and cards
                JPanel playerControls = new JPanel();
                playerControls.setLayout(new BoxLayout(playerControls, BoxLayout.X_AXIS));
                playerDashboard.add(playerControls);
                
                dice = new JPanel();
                dice.setLayout(new BoxLayout(dice, BoxLayout.X_AXIS));

                cards = new JPanel();
                cards.setLayout(new BoxLayout(cards, BoxLayout.Y_AXIS));
                
                playerControls.add(dice);
                playerControls.add(Box.createHorizontalGlue());
                playerControls.add(cards);
                
                this.add(playerDashboard);
            	setSize(1024, 1050);
        	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        	    setVisible(true);
        	    
        	    game.updatePlayer();
        	    
        	    this.getContentPane().setFocusable(true);
            	this.getContentPane().requestFocusInWindow();
            	this.getContentPane().addKeyListener(new KeyListener() {

        			@Override 
        			public void keyPressed(KeyEvent e) {
        				movePlayer(e.getKeyChar());
        			}

        			@Override public void keyReleased(KeyEvent e) {}

        			@Override public void keyTyped(KeyEvent e) {}
           
            	});
        	});
        });
    }
    
    /**
     * Opens a dialog telling each player which character
     * they are.
     * 
     * @param	next	The function to be run once OK is pressed.*/
    private void tellPlayerCharacters(Runnable next) {
    	
    	String text = "";
    	int i = 1;
    	for (Player player : game.getPlayers()) {
    		text += "Player " + i++ + ", you are " + player.getCharacter().getName() + ".\n";
    	}
    	
    	simpleDialog(text, next);
    }
    
    /**
     * Gets the amount of players and sets the property
     * on Game.
     * 
     * @param	The next function to be executed.*/
    private void getPlayerCount(final Runnable next) {
    	
    	JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new FlowLayout());
        JTextField textarea = new JTextField(4);
        JLabel playerlabel = new JLabel("How many players?");
        JButton OKButton = new JButton("OK");
        JLabel errorMessage = new JLabel("");
        errorMessage.setForeground(Color.RED);
        dialogPanel.add(playerlabel);
        dialogPanel.add(textarea);
        dialogPanel.add(OKButton);
        dialogPanel.add(errorMessage);
        JDialog dialog = new JDialog(SwingUtilities.windowForComponent(this));
        dialog.add(dialogPanel);
        dialog.setSize(300, 150);
        dialog.setVisible(true);
        
        OKButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				Integer entered;
				try {
					entered = Integer.valueOf(textarea.getText());
				} catch (NumberFormatException error) {
					errorMessage.setText("Enter a number");
					return;
				}
				
				if (entered < 3 || entered > 6) {
					errorMessage.setText("Between 3 and 6 people can play.");
					return;
				}
				
				//Successful
				game.assignCharacters(entered);
				
				dialog.dispose();
				next.run();
			}
 
        });
    }
    
    /**
     * Brings up a dialog with an ok button.
     * 
     * @param	text	The text to be displayed in the dialog.
     * @param	next	The function that will be run when ok is pressed.*/
    public void simpleDialog(String text, Runnable next) {
    	text = "<html>" + text;
    	text = text.replace("\n", "<br>");
    	
    	JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new FlowLayout());
        JLabel textLabel = new JLabel(text);
        JButton OKButton = new JButton("OK");
        dialogPanel.add(textLabel);
        dialogPanel.add(OKButton);
        JDialog dialog = new JDialog(SwingUtilities.windowForComponent(this));
        dialog.add(dialogPanel);
        dialog.setSize(300, 200);
        dialog.setVisible(true);
        
        OKButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
				if (next != null) next.run();
			}
        	
        });
    }
    
    /**
     * Brings up a dialog with a yes/no option.
     * 
     * @param	text	The text to show.
     * @param	yes		The runnable to run if yes is clicked
     * @param	no		The runnable to run if no is clicked
     * */
    public void yesNo(String text, Runnable yes, Runnable no) {
    	
    	text = "<html>" + text;
    	text = text.replace("\n", "<br>");
    	
    	JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new FlowLayout());
        JLabel textLabel = new JLabel(text);
        JButton yesButton = new JButton("Yes");
        JButton noButton = new JButton("No");
        dialogPanel.add(textLabel);
        dialogPanel.add(yesButton);
        dialogPanel.add(noButton);
        JDialog dialog = new JDialog(SwingUtilities.windowForComponent(this));
        dialog.add(dialogPanel);
        dialog.setSize(300, 200);
        dialog.setVisible(true);
        
        yesButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				yes.run();
				dialog.dispose();
			}
        	
        });
        
        noButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				no.run();
				dialog.dispose();
			}
        	
        });
    }
    
    /**
     * Updates the information at the bottom of the
     * screen for the current player.
     * 
     * @param	newPlayer	The new current player.*/
    public void updatePlayer(Player newPlayer) {
    	
    	this.currentPlayerName.setText("Player " + (game.getPlayers().indexOf(newPlayer) + 1) + ": " + newPlayer.getCharacter().getName());
    	this.currentPlayerName.setForeground(newPlayer.getCharacter().getColor());
    	
    	dice.removeAll();
    	JButton rollDice = new JButton("Roll Dice");
    	dice.add(rollDice);
    	dice.revalidate();
    	dice.repaint();
    	
    	rollDice.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int diceOne = game.diceRoll(1);
				int diceTwo = game.diceRoll(1);
				
				dice.removeAll();
								
				dice.add(new DiceJLabel(diceOne));
				dice.add(new DiceJLabel(diceTwo));
				
				dice.revalidate();
				dice.repaint();
				
				currentPlayerName.setText(currentPlayerName.getText() + " (WASD to move)");
				
				startMove(newPlayer, diceOne + diceTwo);
			}
    		
    	});
    	
    	this.cards.removeAll();
    	for (Card card : newPlayer.getHand().getCards()) {
    		JLabel label = card.getLabel();
    		this.cards.add(label);
    	}
    	this.cards.revalidate();
    	this.cards.repaint();
    }
    
    /**
     * Starts the movement process.*/
    public void startMove(Player player, int numMoves) {
    	this.movingPlayer = player;
    	this.numMoves = numMoves;
    	this.visitedTiles = new HashSet<Point>();
    }
    
    /**
     * Moves the current player according to WASD keys.
     * 
     * @param	pressed	The character pressed on the keyboard.*/
    public void movePlayer(char pressed) {
    	if (movingPlayer == null) return;
    	
    	if (movingPlayer.getInGame() == false) {
    		JOptionPane.showMessageDialog(this, "You are not in the game. Skipping turn.");
    		game.updatePlayer();
    	}
    	
    	if (numMoves == 0) {
    		endMove();
    		return;
    	}
    	    	
    	Board.Direction dir = null;
    	switch(java.lang.Character.toUpperCase(pressed)) {
    	
    	case 'W':	dir = Board.Direction.NORTH;	break;
    	case 'A':	dir = Board.Direction.WEST;		break;
    	case 'S':	dir = Board.Direction.SOUTH;	break;
    	case 'D':	dir = Board.Direction.EAST;		break;
    		
    	}
    	
    	if (dir == null) {
    		throw new Error("Unknown input " + pressed);
    	}
    	
    	Point newPoint = movingPlayer.requestPoint(movingPlayer, dir);
		Point currPoint = movingPlayer.getPoint();
		visitedTiles.add(currPoint);
		
		if (visitedTiles.contains(newPoint)) {
			simpleDialog("Tile has already been visited.", null);
		} else if (game.getBoard().movePlayer(dir, movingPlayer)) {
			//Move successful
			visitedTiles.add(newPoint);
			numMoves--;
			
			//Check if we've entered a room
			Optional<String> entered = game.getBoard().getRoomEntered(game.getBoard().positions[newPoint.y][newPoint.x]);
			  
			if (entered.isPresent() && !game.getBoard().inRoom(currPoint).isPresent()) {
				
				yesNo("You've entered the " + entered.get() + "!\nWould you like to make a suggestion?", 
					() -> {
						//Yes
						suggest(entered.get(), () -> {
							yesNo("Would you like to continue using your moves?",
									() -> {
										//Yes
							}, 		() -> {
										//No
										endMove();
							});
						});
				}, 	() -> {
						//No
						yesNo("Would you like to continue using your moves?",
								() -> {
									//Yes
						}, 		() -> {
									//No
									endMove();
						});
				});
			}
		} else {
			//Move unsuccessful, check for the case that we can't move in any direction
			boolean stuck = true;
			Board.Direction[] directions = Board.Direction.values();
			Point curr = movingPlayer.getPoint();
			Position currPos = game.getBoard().positions[curr.y][curr.x];
			  
			for (Board.Direction tryDir : directions) {
				Point tryPoint = movingPlayer.requestPoint(movingPlayer, tryDir);
				Position tryPos = game.getBoard().positions[tryPoint.y][tryPoint.x];
				  
				if (game.getBoard().moveValid(currPos, tryPos) && tryPos.getPlayer() == null && !visitedTiles.contains(tryPoint)) {
					stuck = false;
					break;
				}
			}
			  
			if (stuck) {
				simpleDialog("You are stuck. Movement over.", () -> {
					endMove();
				});
			}
		}
		
		game.getBoard().draw();
    }
    
    /**
     * Ends the movement process.*/
    public void endMove() {
    	movingPlayer = null;
    	game.updatePlayer();
    }
    
    /**
     * Make a suggestion on behalf of the current player.
     * 
     * @param	next	The next function to run.*/
    public void suggest(String room, Runnable next) {
    	selectedRoom = Game.ROOM_CARDS.stream().filter(r -> r.getName().equals(room)).findFirst().get();
    	
    	//Get the weapon the player wants to suggest
    	Map<String, Weapon> weaponOptions = Game.WEAPON_CARDS	.stream()
    															.collect(Collectors.toMap(w -> w.getName(), w -> w));
    	
    	select(weaponOptions, (w) -> {
    		
    		selectedWeapon = w;
    		
    		//Get the character the player wants to suggest
    		Map<String, Character> charOptions = Game.CHARACTER_CARDS	.stream()
    																	.collect(Collectors.toMap(c -> c.getName(), c -> c));
    		
    		select(charOptions, (c) -> {
    			
    			selectedCharacter = c;
    			
    			//Find the player with the named character
    			Optional<Player> toMove = game.getPlayers().stream().filter(p -> p.getCharacter().equals(selectedCharacter)).findFirst();
    			boolean movePlayer = toMove.isPresent() && !(toMove.get() == movingPlayer);
    			  
    			//Remove the named character and weapon from their current location
    			game.getBoard().positionStream().filter(p -> p.getContents().equals(selectedWeapon)).findFirst().get().setContents(game.getBoard().empty);
    			if (movePlayer) game.getBoard().positionStream().filter(p -> p.getPlayer() != null && p.getPlayer().equals(toMove.get())).findFirst().get().setPlayer(null);
    		    
    			//Place the named character and weapon in the room
    			if (selectedRoom == null) System.out.println("hu");
    			game.getBoard().placeInRoom(selectedRoom.getName(), selectedWeapon);
    			if (movePlayer) game.getBoard().placeInRoom(selectedRoom.getName(), toMove.get());
    			
    			//Now, offer each player a chance to refute
    			int p = game.getPlayers().indexOf(movingPlayer);
    			  
    			//A map from the suggested card to whether it has been refuted
    			Map<Card, Boolean> refuted = new HashMap<Card, Boolean>();
    			refuted.put(selectedRoom, false);
    			refuted.put(selectedCharacter, false);
    			refuted.put(selectedWeapon, false);
    			  
    			for (int i = 0; i < game.getPlayers().size(); i++, p++) {
    				  //Loop through players in clockwise order, starting from current
    				  
    				  Player curr = game.getPlayers().get(p % game.getPlayers().size());
    				  
    				  boolean hasRoom = curr.getHand().indexOfCard(selectedRoom) != -1;
    				  boolean hasChar = curr.getHand().indexOfCard(selectedCharacter) != -1;
    				  boolean hasWeapon = curr.getHand().indexOfCard(selectedWeapon) != -1;
    				  
    				  //The number of refutation cards the player has:
    				  int numHas = (hasRoom ? 1 : 0) + (hasChar ? 1 : 0) + (hasWeapon ? 1 : 0);
    				  
    				  String playerDescriptor = "Player " + (game.getPlayers().indexOf(curr) + 1);
    				  
    				  if (numHas == 0) {
    					  //Player has no cards to refute
    					  JOptionPane.showMessageDialog(this, playerDescriptor + " cannot refute the suggestion!");
    				  } else if (numHas == 1) {
    					  //Player has a card to refute, do it for them
    					  String cardText = "";
    					  if (hasRoom) {
    						  cardText = selectedRoom.getName();
    						  refuted.put(selectedRoom, true);
    					  }
    					  if (hasChar) {
    						  cardText = selectedCharacter.getName();
    						  refuted.put(selectedCharacter, true);
    					  }
    					  if (hasWeapon) {
    						  cardText = selectedWeapon.getName();
    						  refuted.put(selectedWeapon, true);
    					  }
    					  JOptionPane.showMessageDialog(this, playerDescriptor + " refutes with card:\n" + cardText);
    				  } else {
    					  //Player has 2 or more cards to refute, let them choose
    
    					  JOptionPane.showMessageDialog(this, playerDescriptor + " must choose a card to refute. Pass the screen to them and don't look.");
    					  
    					  Map<String, Card> options = new HashMap<String, Card>();
    					  if (hasRoom) options.put(selectedRoom.getName(), selectedRoom);
    					  if (hasChar) options.put(selectedCharacter.getName(), selectedCharacter);
    					  if (hasWeapon) options.put(selectedWeapon.getName(), selectedWeapon);
    					  
    					  Object selected = JOptionPane.showInputDialog(	this, 
    							  											playerDescriptor + ", you can refute using the following cards:",
    							  											"Select Card", 
    							  											JOptionPane.QUESTION_MESSAGE, null, 
    							  											options.keySet().toArray(), null);
    					      					  
    					  refuted.put(options.get(selected), true);
    				  }
    			  }
    			  
    			  String dialogText = "";
    			  if (refuted.get(selectedRoom)) {
    				  dialogText += "Murder scenario " + selectedRoom.getName() + " has been proven false.\n";
    			  } else {
    				  dialogText += "Murder scenario " + selectedRoom.getName() + " could not be proven false.\n";
    			  }
    			  
    			  if (refuted.get(selectedCharacter)) {
    				  dialogText += "Murder scenario " + selectedCharacter.getName() + " has been proven false.\n";
    			  } else {
    				  dialogText += "Murder scenario " + selectedCharacter.getName() + " could not be proven false.\n";
    			  }
    			  
    			  if (refuted.get(selectedWeapon)) {
    				  dialogText += "Murder scenario " + selectedWeapon.getName() + " has been proven false.\n";
    			  } else {
    				  dialogText += "Murder scenario " + selectedWeapon.getName() + " could not be proven false.\n";
    			  }
    			  
    			  JOptionPane.showMessageDialog(this, dialogText);
    			  
    		});
    	});
    }
    
    /**
     * Opens a popup with radio buttons.
     * 
     * @param options	A Map from the displayed option to
     * 					what will be passed to the results
     * 					function.
     * @param results	A consumer that takes the result of
     * 					selection once ok is clicked.*/
    public <T> void select(Map<String, T> options, Consumer<T> results) {
    	
    	//Initialize the popup
    	JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
        
        JDialog dialog = new JDialog(SwingUtilities.windowForComponent(this));
        dialog.add(dialogPanel);
        dialog.setSize(300, 300);
        dialog.setVisible(true);
    	
    	//Initialise radio buttons
    	ButtonGroup buttonGroup = new ButtonGroup();
    	for (String text : options.keySet()) {
    		JRadioButton button = new JRadioButton(text);
    		buttonGroup.add(button);
    		dialogPanel.add(button);
    	}
    	
    	//Initialise ok button
    	JButton ok = new JButton("Ok");
    	dialogPanel.add(ok);
    	
    	ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//Find the selected button
				if (buttonGroup.getSelection() == null) {
					//Nothing selected
				} else {
					for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
						AbstractButton button = buttons.nextElement();
						if (button.isSelected()) {
							T result = options.get(button.getText());
							
							//Give the result to the consumer
							results.accept(result);
							
							//Close the window
							dialog.dispose();
							return;
						}
					}
				}
			}
    		
    	});
    }
}