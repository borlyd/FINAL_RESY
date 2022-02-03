package letenky;

import java.util.ArrayList;

import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.awt.event.*;
import java.awt.*;

public class RezervaciaGUI extends JFrame {

	private static final int SIRKA = 500;
	private static final int VYSKA = 200;
	private RezervaciaTester rezervaciaTester;
	private JTextArea obrazovka;
	private Klavesnica klavesnica;
	private JButton tlacidlo1;
	private JButton tlacidlo2;
	private JButton tlacidlo3;
	private JPanel panelTlacidiel;

	ArrayList<Zakaznik> pasaziery = new ArrayList<Zakaznik>();
	ArrayList<Let> lety = new ArrayList<Let>();
	ArrayList<Rezervacia> rezervacie = new ArrayList<Rezervacia>();
	Pokladna pokladna = new Pokladna();

	private Rezervacia rezervacia;

	public RezervaciaGUI(Rezervacia rezervacia) {

		this.rezervacia = rezervacia;
		klavesnica = new Klavesnica();
		nastavAtributy();
		vytvorObrazovku();
		vytvorTlacidla();
		vytvorPanelTlacidiel();

		setLayout(new FlowLayout());

		add(klavesnica);
		add(obrazovka);
		add(panelTlacidiel);
		nastavObrazovku();

	}

	private void nastavObrazovku() {
		int stav = rezervacia.getStav();

		if (stav == Rezervacia.START) {
			obrazovka.setText("----------------------------------------\n" + "Rezervacia leteniek"
					+ "----------------------------------------\n" + "Ceny:\n" + "Klasický lístok: 4,50€\n"
					+ "Detský lístok: 3€\n" + "----------------------------------------\n"
					+ "Zadajte 1 pre rezerváciu\n" + "Zadajte 2 pre zrušenie rezervácie\n" + "Zadajte 3 pre ukončenie\n"
					+ "----------------------------------------" + "Zadajte možnosť:\n");

		} else if (stav == Rezervacia.REGISTRACIAMENO) {
			obrazovka.setText("Zadali ste rezerváciu\n" + "----------------------------------------\n"
					+ "Zadajte svoje meno: \n" + "Potvrďte kliknutím na 1 = OK");

		} else if (stav == Rezervacia.REGISTRACIAPRIEZVISKO) {
			obrazovka.setText("Zadajte svoje priezvisko:  \n" + "Potvrďte kliknutím na 1 = OK");

		} else if (stav == Rezervacia.REGISTRACIAADRESA) {
			obrazovka.setText("Zadajte svoju adresu:  \n" + "Potvrďte kliknutím na 1 = OK");

		} else if (stav == Rezervacia.VYBERLET) {
			obrazovka.setText("----------------------------------------  \n" + "Vyberte let\n"
					+ "----------------------------------------");

			for (int i = 0; i < lety.size(); i++) {
				int cisloLetu = i + 1;
				obrazovka.setText("Let č.: " + cisloLetu);
				obrazovka.setText("Nazov letu: " + lety.get(i).getNazov() + " cena letu: " + lety.get(i).getCenaLetu()
						+ " | " + lety.get(i).getCasOdletu());
			}
			obrazovka.setText("----------------------------------------  \n" + "Zadajte č. letu: \n"
					+ "----------------------------------------");

		} else if (stav == Rezervacia.VYBERDRUHLETU) {
			obrazovka.setText("Zadajte č. 1 pre jednosmerný let alebo č. 2 pre spiatocny let: ");

		} else if (stav == Rezervacia.VYBERTYPLETU) {
			obrazovka.setText("Zvolte č. 1 pre biznis triedu a č. 2 pre ekonomickú triedu: ");

		} else if (stav == Rezervacia.VYBERSEDADLO) {
			obrazovka.setText("Zadajte svoju adresu:  \n" + "Potvrďte kliknutím na 1 = OK");

		} else if (stav == Rezervacia.POKRACUJKONIEC) {
			obrazovka.setText("Zadajte svoju adresu:  \n" + "Potvrďte kliknutím na 1 = OK");

		} else if (stav == Rezervacia.ZRUSENIEREGISTRACIE) {
			obrazovka.setText("Zadajte svoju adresu:  \n" + "Potvrďte kliknutím na 1 = OK");

		} else if (stav == Rezervacia.IDREGISTRACIE) {
			obrazovka.setText("Zadajte svoju adresu:  \n" + "Potvrďte kliknutím na 1 = OK");

		} else if (stav == RezervaciaTester.UCET) {
			obrazovka.setText("Zvol ucet: \nA = Bezny ucet\nB = Sporiaci ucet \nC = Koniec");
		} else if (stav == RezervaciaTester.TRANSAKCIA) {
			obrazovka.setText("Aktualny zostatok: " + rezervaciaTester.getAktualnyZostatok() + "EUR"
					+ "\nZadaj ciastku a zvol operaciu: \nA = Vyber \nB = Vklad\nC = Krok spat");
		}

	}

	private void vytvorPanelTlacidiel() {
		panelTlacidiel = new JPanel();

		panelTlacidiel.setLayout(new GridLayout(3, 1));
		panelTlacidiel.add(tlacidlo1);
		panelTlacidiel.add(tlacidlo2);
		panelTlacidiel.add(tlacidlo3);

	}

	private void vytvorTlacidla() {
		tlacidlo1 = new JButton("1");

		tlacidlo1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Rezervacia rezervacia;
				if (rezervacia.getStav() == Rezervacia.START) {
					rezervacia.setStav(2);
					nastavObrazovku();

				} else if (rezervacia.getStav() == Rezervacia.REGISTRACIAMENO) {
					String meno = String.valueOf(klavesnica.getText());
					rezervacia.setStav(3);
					klavesnica.setText("");
					nastavObrazovku();

				} else if (rezervacia.getStav() == Rezervacia.REGISTRACIAPRIEZVISKO) {
					String priezvisko = String.valueOf(klavesnica.getText());
					rezervacia.setStav(4);
					klavesnica.setText("");
					nastavObrazovku();

				} else if (rezervacia.getStav() == Rezervacia.REGISTRACIAADRESA) {
					String adresa = String.valueOf(klavesnica.getText());

					Random rand = new Random();
					int id = rand.nextInt(1000);

					Zakaznik zakaznik = new Zakaznik(id, meno, priezvisko, adresa);
					pasaziery.add(zakaznik);

					rezervacia.setStav(5);
					klavesnica.setText("");
					nastavObrazovku();

				} else if (rezervacia.getStav() == Rezervacia.VYBERLET) {
					String adresa = String.valueOf(klavesnica.getText());
					rezervacia.setStav(6);
					klavesnica.setText("");
					nastavObrazovku();

				} else if (rezervacia.getStav() == Rezervacia.VYBERDRUHLETU) {
					String adresa = String.valueOf(klavesnica.getText());
					rezervacia.setStav(7);
					klavesnica.setText("");
					nastavObrazovku();

				} else if (rezervacia.getStav() == Rezervacia.VYBERTYPLETU) {
					String adresa = String.valueOf(klavesnica.getText());
					rezervacia.setStav(8);
					klavesnica.setText("");
					nastavObrazovku();

				} else if (rezervacia.getStav() == Rezervacia.VYBERSEDADLO) {
					String adresa = String.valueOf(klavesnica.getText());
					rezervacia.setStav(9);
					klavesnica.setText("");
					nastavObrazovku();

				} else if (rezervacia.getStav() == Rezervacia.POKRACUJKONIEC) {
					String adresa = String.valueOf(klavesnica.getText());
					rezervacia.setStav(10);
					klavesnica.setText("");
					nastavObrazovku();
				}
			}
		});

		tlacidlo2 = new JButton("2");
		tlacidlo2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (rezervaciaTester.getStav() == RezervaciaTester.START) {
					nastavObrazovku();

				} else if (rezervaciaTester.getStav() == RezervaciaTester.TRANSAKCIA) {
					double ciastka = Double.parseDouble(klavesnica.getText());
					rezervaciaTester.vloz(ciastka);
					nastavObrazovku();

				}
			}
		});

		tlacidlo3 = new JButton("3");
		tlacidlo3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (rezervaciaTester.getStav() == RezervaciaTester.UCET) {
					rezervaciaTester.reset();
					klavesnica.setText("");
					nastavObrazovku();

				} else if (rezervaciaTester.getStav() == RezervaciaTester.TRANSAKCIA) {
					rezervaciaTester.nastavPredchadzajuciStav();
					klavesnica.setText("");
					nastavObrazovku();
				}
			}
		});

	}

	private void vytvorObrazovku() {
		obrazovka = new JTextArea(8, 20);
		obrazovka.setEditable(false);
		obrazovka.setBorder(BorderFactory.createLineBorder(Color.GRAY));
	}

	private void nastavAtributy() {
		setTitle("Rezervácia leteniek RESY");
		setSize(SIRKA, VYSKA);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

	}

}
