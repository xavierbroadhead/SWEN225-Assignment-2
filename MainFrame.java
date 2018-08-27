import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.util.Arrays;

import javax.swing.*;
 
public class MainFrame extends JFrame {
	
    private Game game;
    static int playerCount = 0;
    public MainFrame(final String title, final Game game) {
    	
        super(title);
        this.game = game;
        
        getPlayerCount(() -> {
        	
        	tellPlayerCharacters(() -> {
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
                JPanel playerControls = new JPanel();
                playerControls.setLayout(new BoxLayout(playerControls, BoxLayout.Y_AXIS));
                JLabel playerName = new JLabel("Player 1");
                playerName.setForeground(Color.RED);
                playerName.setFont(new Font("Arial", Font.PLAIN, 32));
                playerControls.add(playerName);
                
                //Add the dice and cards
                JPanel diceAndCards = new JPanel();
                diceAndCards.setLayout(new BoxLayout(diceAndCards, BoxLayout.X_AXIS));
                diceAndCards.add(new DiceJLabel(2));
                diceAndCards.add(new DiceJLabel(6));
                diceAndCards.add(Box.createHorizontalGlue());
                JPanel cards = new JPanel();
                cards.setLayout(new BoxLayout(cards, BoxLayout.Y_AXIS));
                diceAndCards.add(cards);
                cards.add(new CharacterJLabel(Game.CHARACTER_CARDS.get(0)));
                cards.add(new WeaponJLabel(Game.WEAPON_CARDS.get(0)));
                cards.add(new RoomJLabel(Game.ROOM_CARDS.get(0)));
                
                playerControls.add(diceAndCards);
                
                this.add(playerControls);
            	setSize(1024, 1024);
        	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        	    setVisible(true);
        	});
        });
    }
    
    /**
     * Opens a dialog telling each player which character
     * they are.
     * 
     * @param	next	The function to be run once OK is pressed.*/
    private void tellPlayerCharacters(final Runnable next) {
    	
    	String text = "<html>";
    	int i = 1;
    	for (Player player : game.getPlayers()) {
    		text += "Player " + i + ", you are " + player.getCharacter().getName() + "!<br>";
    	}
    	
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
				next.run();
			}
        	
        });
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
}