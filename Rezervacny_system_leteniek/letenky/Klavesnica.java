package letenky;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class Klavesnica extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// private JPanel panelTlacidiel;
	private JTextField textovePole;

	{
		vytvorTextovePole();
		// vytvorPanelTlacidiel();
		// vytvorTlacidloBodka();
		// vytvorTlacidloCE();

		setLayout(new BorderLayout());

		add(textovePole, BorderLayout.NORTH);
		// add(panelTlacidiel);
	}

	public void setText(String text) {
		textovePole.setText(text);
	}

	public String getText() {
		return textovePole.getText();
	}

	/**
	 * private void vytvorTlacidloBodka() { final String BODKA = "."; JButton
	 * tlacidlo = new JButton(BODKA);
	 * 
	 * tlacidlo.addActionListener(new ActionListener() {
	 * 
	 * @Override public void actionPerformed(ActionEvent e) { if
	 *           (!textovePole.getText().contains(BODKA) &&
	 *           !textovePole.getText().isEmpty()) {
	 *           textovePole.setText(textovePole.getText() + BODKA); } }
	 * 
	 *           });
	 * 
	 *           }
	 * 
	 *           private void vytvorPanelTlacidiel() { panelTlacidiel = new
	 *           JPanel(new GridLayout(4, 3));
	 * 
	 *           pridajNumerickeTlacidlo("1"); pridajNumerickeTlacidlo("2");
	 *           pridajNumerickeTlacidlo("3"); pridajNumerickeTlacidlo("4");
	 *           pridajNumerickeTlacidlo("5"); pridajNumerickeTlacidlo("6");
	 *           pridajNumerickeTlacidlo("7"); pridajNumerickeTlacidlo("8");
	 *           pridajNumerickeTlacidlo("9"); pridajNumerickeTlacidlo("0");
	 * 
	 *           }
	 * 
	 *           private void pridajNumerickeTlacidlo(String popis) { JButton
	 *           tlacidlo = new JButton(popis);
	 * 
	 *           tlacidlo.addActionListener(new ActionListener() {
	 * 
	 * @Override public void actionPerformed(ActionEvent e) {
	 *           textovePole.setText(textovePole.getText() + popis); } });
	 * 
	 *           panelTlacidiel.add(tlacidlo); }
	 * 
	 *           private void vytvorTlacidloCE() { JButton tlacidloCE = new
	 *           JButton("CE");
	 * 
	 *           tlacidloCE.addActionListener(new ActionListener() {
	 * 
	 * @Override public void actionPerformed(ActionEvent e) {
	 *           textovePole.setText(""); }
	 * 
	 *           }); panelTlacidiel.add(tlacidloCE); }
	 * 
	 */
	private void vytvorTextovePole() {
		textovePole = new JTextField(50);
		textovePole.setBounds(50, 100, 200, 30);
	}

}
