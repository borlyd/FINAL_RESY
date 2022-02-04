package letenky;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static letenky.Stav.*;
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
	private JTextArea obrazovka;
	private Klavesnica klavesnica;
	private JButton tlacidlo1;
	private JButton tlacidlo2;
	private JButton tlacidlo3;
	private JPanel panelTlacidiel;

	private Stav stav;

	ArrayList<Zakaznik> pasaziery = new ArrayList<Zakaznik>();
	ArrayList<Let> lety = new ArrayList<Let>();
	ArrayList<Rezervacia> rezervacie = new ArrayList<Rezervacia>();
	Pokladna pokladna = new Pokladna();
	private Rezervacia rezervacia;

	String meno;
	String priezvisko;
	String adresa;
	Let let;
	int druhLetu; // 1 jednosmerny a 2 obojsmerny
	int typLetu; // 1 dospely a 2 dieta
	int trieda; // ekonomická a biznis trieda
	int cisloSedadla;

	private static Calendar nastavDatumACas(Calendar c, int rok, int mes, int den, int hod, int min, int sec) {
		c.set(Calendar.YEAR, rok);
		c.set(Calendar.MONTH, mes);
		c.set(Calendar.DATE, den);
		c.set(Calendar.HOUR, hod);
		c.set(Calendar.MINUTE, min);
		c.set(Calendar.SECOND, sec);
		return c;
	}

	public RezervaciaGUI() {

		this.stav = START;

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

		Calendar c = Calendar.getInstance(); // https://docs.oracle.com/javase/7/docs/api/java/util/Calendar.html

		c = nastavDatumACas(c, 2022, 02, 12, 2, 30, 0);
		Date d1 = c.getTime();
		lety.add(new Let("Mnichov", 80, d1));

		c = nastavDatumACas(c, 2022, 01, 14, 5, 0, 0);
		Date d2 = c.getTime();
		lety.add(new Let("Londyn", 100, d2));

		c = nastavDatumACas(c, 2022, 05, 22, 3, 30, 0);
		Date d3 = c.getTime();
		lety.add(new Let("Barcelona", 120, d3));

	}

	private void nastavObrazovku() {

		if (stav == START) {
			obrazovka.setText("----------------------------------------\n" + "Rezervacia leteniek"
					+ "----------------------------------------\n" + "Ceny:\n" + "Klasický lístok: 4,50€\n"
					+ "Detský lístok: 3€\n" + "----------------------------------------\n"
					+ "Zadajte 1 pre rezerváciu\n" + "Zadajte 2 pre zrušenie rezervácie\n" + "Zadajte 3 pre ukončenie\n"
					+ "----------------------------------------" + "Zadajte možnosť:\n");

		} else if (stav == REGISTRACIAMENO) {
			obrazovka.setText("Zadali ste rezerváciu\n" + "----------------------------------------\n"
					+ "Zadajte svoje meno: \n" + "Potvrďte kliknutím na 1 = OK");

		} else if (stav == REGISTRACIAPRIEZVISKO) {
			obrazovka.setText("Zadajte svoje priezvisko:  \n" + "Potvrďte kliknutím na 1 = OK");

		} else if (stav == REGISTRACIAADRESA) {
			obrazovka.setText("Zadajte svoju adresu:  \n" + "Potvrďte kliknutím na 1 = OK");

		} else if (stav == VYBERLET) {
			var sb = new StringBuffer();

			sb.append("----------------------------------------  \n" + "Vyberte let\n"
					+ "----------------------------------------");

			for (int i = 0; i < lety.size(); i++) {
				int cisloLetu = i + 1;
				sb.append("Let č.: " + cisloLetu);
				sb.append("Nazov letu: " + lety.get(i).getNazov() + " cena letu: " + lety.get(i).getCenaLetu() + " | "
						+ lety.get(i).getCasOdletu());
			}
			sb.append("----------------------------------------  \n" + "Zadajte č. letu: \n"
					+ "----------------------------------------");

			obrazovka.setText(sb.toString());

		} else if (stav == VYBERDRUHLETU) {
			obrazovka.setText("Zadajte č. 1 pre jednosmerný let alebo č. 2 pre spiatocny let: ");

		} else if (stav == VYBERTYPLETU) {
			obrazovka.setText("Zvolte č. 1 pre klasický lístok a č. 2 pre detský lístok: ");

		} else if (stav == VYBERTRIEDU) {
			obrazovka.setText("Zvolte č. 1 pre biznis triedu a č. 2 pre ekonomickú triedu: ");

		} else if (stav == VYBERSEDADLO) {
			obrazovka.setText("Vyberte si číslo sedadla:  \n" + "Potvrďte kliknutím na 1 = OK");

		} else if (stav == POKRACUJKONIEC) {
			obrazovka.setText("Zvoľte č. 1 ak chcete rezervovať ďalšie sedadlo alebo č. 2 ak chcete zaplatiť:");

		} else if (stav == ZRUSENIEREGISTRACIE) {
			obrazovka.setText("Zadali ste zrušenie rezervácie");

		} else if (stav == IDREGISTRACIE) {
			obrazovka.setText("Zadajte vaše ID rezervácie");

		} else if (stav == REGISTRACIAZRUSENA) {
			obrazovka.setText("Zrušili ste rezerváciu.");

		} else if (stav == KONIECPROGRAMU) {
			obrazovka.setText("Koniec programu.");

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
				if (stav == START) {
					stav = REGISTRACIAMENO;
					nastavObrazovku();

				} else if (stav == REGISTRACIAMENO) {
					meno = String.valueOf(klavesnica.getText());
					stav = REGISTRACIAPRIEZVISKO;
					klavesnica.setText("");
					nastavObrazovku();

				} else if (stav == REGISTRACIAPRIEZVISKO) {
					priezvisko = String.valueOf(klavesnica.getText());
					stav = REGISTRACIAADRESA;
					klavesnica.setText("");
					nastavObrazovku();

				} else if (stav == REGISTRACIAADRESA) {
					adresa = String.valueOf(klavesnica.getText());

					Random rand = new Random();
					int id = rand.nextInt(1000);

					Zakaznik zakaznik = new Zakaznik(id, meno, priezvisko, adresa);
					pasaziery.add(zakaznik);
					stav = VYBERLET;

					klavesnica.setText("");
					nastavObrazovku();

				} else if (stav == VYBERLET) {
					let = lety.get(0);

					stav = VYBERDRUHLETU;
					klavesnica.setText("");
					nastavObrazovku();

				} else if (stav == VYBERDRUHLETU) {
					druhLetu = 1;
					stav = VYBERTYPLETU;
					klavesnica.setText("");
					nastavObrazovku();

				} else if (stav == VYBERTYPLETU) {
					typLetu = 1;
					stav = VYBERTRIEDU;
					klavesnica.setText("");
					nastavObrazovku();

				} else if (stav == VYBERTRIEDU) {
					trieda = 1;
					stav = VYBERSEDADLO;
					klavesnica.setText("");
					nastavObrazovku();

				} else if (stav == VYBERSEDADLO) {
					cisloSedadla = Integer.valueOf(klavesnica.getText());
					stav = POKRACUJKONIEC;
					klavesnica.setText("");
					nastavObrazovku();

				} else if (stav == POKRACUJKONIEC) {
					String adresa = String.valueOf(klavesnica.getText());
					stav = IDREGISTRACIE;
					klavesnica.setText("");
					nastavObrazovku();
				}
			}
		});

		tlacidlo2 = new JButton("2");
		tlacidlo2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (stav == VYBERLET) {
					let = lety.get(1);

					stav = VYBERDRUHLETU;
					klavesnica.setText("");
					nastavObrazovku();

				} else if (stav == VYBERDRUHLETU) {
					druhLetu = 2;
					stav = VYBERTYPLETU;
					klavesnica.setText("");
					nastavObrazovku();

				} else if (stav == VYBERTYPLETU) {
					typLetu = 2;
					stav = VYBERTRIEDU;
					klavesnica.setText("");
					nastavObrazovku();

				} else if (stav == VYBERTRIEDU) {
					trieda = 2;
					stav = VYBERSEDADLO;
					klavesnica.setText("");
					nastavObrazovku();

				}
			}
		});

		tlacidlo3 = new JButton("3");
		tlacidlo3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (stav == KONIECPROGRAMU) {
					klavesnica.setText("");
					nastavObrazovku();

				} else if (stav == VYBERLET) {
					let = lety.get(2);

					stav = VYBERDRUHLETU;
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
