import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.*;
 
public class MainFrame extends JFrame {
	
    private Game game;
    static int playerCount = 0;
    public MainFrame(String title, Game game) {
    	
        super(title);
        this.game = game;
        
        //get player count 
        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new FlowLayout());
        JTextField textarea = new JTextField(4);
        JLabel playerlabel = new JLabel("How many players?");
        JButton OKButton = new JButton("OK");
        dialogPanel.add(playerlabel);
        dialogPanel.add(textarea);
        dialogPanel.add(OKButton);
        JDialog dialog = new JDialog(SwingUtilities.windowForComponent(this));
        dialog.setSize(getPreferredSize());
        dialog.add(dialogPanel);
        dialog.pack();
        dialog.setVisible(true);
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
        

        //Create swing components
        JButton rollDiceButton = new JButton("Roll dice");
        


        //Add components to pane
        //Container c = getContentPane();
       // c.add(rollDiceButton, GridBagConstraints.BELOW_BASELINE_TRAILING);
       // c.add(textarea, GridBagConstraints.BELOW_BASELINE_LEADING);
       
        //Add behaviour
        rollDiceButton.addActionListener(new ActionListener()  {
           
            @Override
            public void actionPerformed(ActionEvent e) {
                game.diceRoll(2);
                System.out.println(game.diceRoll(2));
            }
           
        });
        OKButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (Integer.parseInt(textarea.getText()) < 3 || Integer.parseInt(textarea.getText()) > 6) {
					JLabel warning = new JLabel("Input must be between 3 and 6!");
					dialog.add(warning);
					dialog.pack();
				}
				else {
					String buffer = textarea.getText();
					playerCount = Integer.parseInt(buffer);
					System.out.println("Player count: " + playerCount);
					dialog.setVisible(false);
				}
			}
        	
        });
    }
}