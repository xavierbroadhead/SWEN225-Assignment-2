import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
import javax.swing.*;
 
public class MainFrame extends JFrame {
	
    private Game game;
    
    public MainFrame(String title, Game game) {
        super(title);
        this.game = game;
        //Set layout manager
        this.getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        
        //Create the menu bar
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(new JMenu("Turn"));
        menuBar.add(new JMenu("Game"));
        menuBar.add(Box.createHorizontalGlue());
        menuBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        this.add(menuBar);
        
        //Create the board grid
        Position[][] positions = game.getBoard().positions;
        JPanel board = new JPanel(new GridLayout(positions.length, positions[0].length));
        for (int r = 0; r < positions.length; r++) {
        	for (int c = 0; c < positions[r].length; c++) {
        		board.add(new JLabel("a"));
        	}
        }
        
        this.add(board);
        
        //Create swing components
        JButton rollDiceButton = new JButton("Roll dice");
        JTextField textarea = new JTextField();
        JLabel playerlabel = new JLabel("How many players?");
        
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
        textarea.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String text = textarea.getText();
				System.out.println(text);
			}
        	
        });
    }
}