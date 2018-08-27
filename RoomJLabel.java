import javax.swing.JLabel;

public class RoomJLabel extends JLabel {

	public RoomJLabel(Room room) {
		super();
		
		this.setText(room.getName());
	}
}
