import javax.swing.JLabel;

public class WeaponJLabel extends JLabel {

	public WeaponJLabel(Weapon weapon) {
		super();
		
		this.setText(weapon.getName());
		this.setIcon(weapon.draw().getIcon());
	}
}
