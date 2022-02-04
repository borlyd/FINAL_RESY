package letenky;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class App {
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			JFrame frame = new RezervaciaGUI();

			frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			frame.setVisible(true);
		});
	}

}
