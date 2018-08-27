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
        
        
    }
}