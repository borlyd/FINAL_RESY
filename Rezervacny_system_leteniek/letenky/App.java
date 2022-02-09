package letenky;

import javax.swing.*;
import java.awt.*;

public class App {
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			JFrame frame = new RezervaciaGUI();
			frame.setBounds(350, 20, 520, 650);

			frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			frame.setVisible(true);
		});
	}

}
