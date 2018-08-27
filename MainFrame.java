import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
import javax.swing.*;
 
public class MainFrame extends JFrame {
	
    private Game game;
    
    public MainFrame(String title, Game game) {
        super(title);
        this.game = game;
        //Set layout manager
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints gc = new GridBagConstraints();
        setLayout(new GridBagLayout());
        
        //first column
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 0.5;
        gc.weighty = 0.5;
        
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(new JMenu("File"));
        menuBar.add(new JMenu("Game"));
        menuBar.add(Box.createHorizontalGlue());
        menuBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        this.add(menuBar);
        
        //Create swing components
        JButton rollDiceButton = new JButton("Roll dice");
        JTextField textarea = new JTextField();
        JLabel playerlabel = new JLabel("How many players?");
        
        //Add components to pane
        gc.anchor = GridBagConstraints.SOUTHWEST;
        gc.gridx = 0;
        gc.gridy = 5;
        add(rollDiceButton, gc);
        
        gc.anchor = GridBagConstraints.LINE_START;
        gc.gridx = 0;
        gc.gridy = 1;
        add(playerlabel, gc);
        
        gc.anchor = GridBagConstraints.LINE_END;
        gc.gridx = 0;
        gc.gridy = 1;
        add(textarea, gc);
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