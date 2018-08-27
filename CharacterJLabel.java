import javax.swing.JLabel;

public class CharacterJLabel extends JLabel {
	
	public CharacterJLabel(Character character) {
		super();
		
		this.setText(character.getName());
		this.setIcon(character.draw().getIcon());
	}
}
