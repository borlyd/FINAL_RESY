package letenky;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Klavesnica extends JPanel {

	private JPanel panelTlacidiel;
	private JTextField textovePole;

	{
		vytvorTextovePole();
		vytvorTlacidloBodka();
		vytvorTlacidloCE();

		setLayout(new BorderLayout());

		add(textovePole, BorderLayout.NORTH);
		add(panelTlacidiel);
	}

	public void setText(String text) {
		textovePole.setText(text);
	}

	public String getText() {
		return textovePole.getText();
	}

	private void vytvorTlacidloBodka() {
		final String BODKA = ".";
		JButton tlacidlo = new JButton(BODKA);

		tlacidlo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!textovePole.getText().contains(BODKA) && !textovePole.getText().isEmpty()) {
					textovePole.setText(textovePole.getText() + BODKA);
				}
			}

		});

	}

	private void pridajNumerickeTlacidlo(String popis) {
		JButton tlacidlo = new JButton(popis);

		tlacidlo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				textovePole.setText(textovePole.getText() + popis);
			}
		});

		panelTlacidiel.add(tlacidlo);
	}

	private void vytvorTlacidloCE() {
		JButton tlacidloCE = new JButton("CE");

		tlacidloCE.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				textovePole.setText("");
			}

		});
		panelTlacidiel.add(tlacidloCE);
	}

	private void vytvorTextovePole() {
		textovePole = new JTextField(12);
	}

}
