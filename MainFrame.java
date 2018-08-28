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
                menuBar.add(new JMenu("Turn"));
                menuBar.add(new JMenu("Game"));
                menuBar.add(Box.createHorizontalGlue());
                menuBar.setLayout(new FlowLayout(FlowLayout.LEFT));
                
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
				
				yesNo("You've entered the " + entered.get() + "! Would you like to make a suggestion?", 
					() -> {
						//Yes
						suggest(() -> {});
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
    public void suggest(Runnable next) {
    	Map<String, Integer> options = new HashMap<String, Integer>();
    	options.put("1", 1);
    	options.put("2", 2);
    	
    	select(options, res -> System.out.println(res));
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
        dialog.setSize(300, 200);
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