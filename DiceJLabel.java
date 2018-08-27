import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class DiceJLabel extends JLabel {

	private static final String IMG_PATH = "SWEN225-Assignment-2/res/dice/";
	
	public DiceJLabel(int num) {
		super();
		
		if (num < 1 || num > 6) throw new IllegalArgumentException("Must be between 1 and 6.");
		
		updateImage(num);
	}
	
	public void updateImage(int num) {
		String path = IMG_PATH + num + ".png";
		
		BufferedImage diceFace = null;
		try {
		    diceFace = ImageIO.read(new File(path));
		} catch (IOException e) {
			throw new Error(e);
		}
		
		this.setIcon(new ImageIcon(diceFace));
	}
}
