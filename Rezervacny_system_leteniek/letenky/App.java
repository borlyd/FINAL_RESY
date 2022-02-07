package letenky;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class App {
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			JFrame frame = new RezervaciaGUI();
			frame.setBounds(400, 150, 450, 450);

			frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			frame.setVisible(true);
		});
	}

}
