import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
import javax.swing.JButton;
import javax.swing.JFrame;
 
public class MainFrame extends JFrame {
    private Game game;
    public MainFrame(String title, Game game) {
        super(title);
        this.game = game;
        //Set layout manager
        setLayout(new BorderLayout());
       
        //Create swing components
        JButton rollDiceButton = new JButton("Roll dice");
       
        //Add components to pane
        Container c = getContentPane();
        c.add(rollDiceButton, BorderLayout.SOUTH);
       
        //Add behaviour
        rollDiceButton.addActionListener(new ActionListener()  {
           
            @Override
            public void actionPerformed(ActionEvent e) {
                game.diceRoll(2);
                System.out.println(game.diceRoll(2));
            }
           
        });
    }
}