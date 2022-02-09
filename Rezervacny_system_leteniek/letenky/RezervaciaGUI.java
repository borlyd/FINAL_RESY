package letenky;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import static letenky.Stav.*;


public class RezervaciaGUI extends JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 *
	 */
	private static final int SIRKA = 600;
	private static final int VYSKA = 400;
	private JTextArea obrazovka;
	private Klavesnica klavesnica;
	private JButton tlacidlo1;
	private JButton tlacidlo2;
	private JButton tlacidlo3;
	private JPanel panelTlacidiel;

	private Stav stav;

	private ZapisovacLeteniek zapisovac = new ZapisovacLeteniek();

	ArrayList<Let> lety = new ArrayList<Let>();
	Objednavka objednavka;

	String meno;
	String priezvisko;
	String adresa;
	Let let;
	boolean spiatocny; // 1 jednosmerny a 2 obojsmerny
	TypPasaziera typPasaziera; // 1 dospely a 2 dieta
	Trieda triedaLetu; // ekonomická a biznis trieda
	String menoNaLetenke;
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


		add(obrazovka);
		add(klavesnica);
		add(panelTlacidiel);
		nastavObrazovku();

		Calendar c = Calendar.getInstance(); // https://docs.oracle.com/javase/7/docs/api/java/util/Calendar.html

		c = nastavDatumACas(c, 2022, 02, 12, 2, 30, 0);
		Date d1 = c.getTime();
		lety.add(new Let("Mníchov", 80, d1, 50, 10));

		c = nastavDatumACas(c, 2022, 01, 14, 5, 0, 0);
		Date d2 = c.getTime();
		lety.add(new Let("Londýn", 100, d2, 100, 30));

		c = nastavDatumACas(c, 2022, 05, 22, 3, 30, 0);
		Date d3 = c.getTime();
		lety.add(new Let("Barcelona", 120, d3, 40, 10));

	}

	private void nastavObrazovku() {

		if (stav == START) {
			obrazovka.setText(

					"========================\n" +  "   Rezervácia leteniek   \n" + "========================\n"
							+ " - Zadajte 1 pre rezerváciu\n" + " - Zadajte 2 pre zrušenie rezervácie  \n"
							+ " - Zadajte 3 pre ukončenie" );

		} else if (stav == REGISTRACIAMENO) {
			obrazovka.setText(
					"========================\n" + "   Zadali ste rezerváciu. \n" + "========================\n"
							+ " Zadajte svoje MENO\n a potvrďte kliknutím na 1. ");


		} else if (stav == REGISTRACIAPRIEZVISKO) {
			obrazovka.setText("========================\n" + "   Zadali ste svoje meno. \n" + "========================\n"
					+ " Zadajte svoje PRIEZVISKO.\n a potvrďte kliknutím na 1. ");

		} else if (stav == REGISTRACIAADRESA) {
			obrazovka.setText("========================\n" + "   Zadali ste priezvisko. \n" + "========================\n"
					+ " Zadajte svoju ADRESU.\n " + "Potvrďte kliknutím na 1. ");

		} else if (stav == VYBERLET) {
			var sb = new StringBuffer();

			sb.append( "==================================\n" +"	VYBERTE SI LET\n"
					+ "==================================\n");

			for (int i = 0; i < lety.size(); i++) {
				int cisloLetu = i + 1;
				sb.append(" Let č.: " + cisloLetu + "\n");
				sb.append(" Názov letu: " + lety.get(i).getNazov() + "\n" + " Cena letu: " + lety.get(i).getCenaLetu()
						+ " | " + "Čas odletu: " + lety.get(i).getCasOdletu() + "\n");
				sb.append("\n");

			}
			sb.append("==================================\n");
			sb.append( " ZVOĽTE č. letu (1, 2, 3): \n");

			obrazovka.setText(sb.toString());

		} else if (stav == VYBERDRUHLETU) {
			obrazovka.setText("========================\n" + " Stlačte 1 pre jednosmerný let \n alebo 2 pre spiatocny let: "
					+ "\n" + "========================\n");

		} else if (stav == VYBERTYPLETU) {
			obrazovka.setText("========================\n" + " Stlačte 1 pre klasický lístok \n alebo 2 pre detský lístok: "
					+ "\n" + "========================\n");

		} else if (stav == VYBERTRIEDU) {
			obrazovka.setText("========================\n" + " Stlačte 1 pre biznis triedu \n alebo 2 pre ekonomickú triedu: "
					+ "\n" + "========================\n");

		} else if (stav == VYPLNMENOLETENKY) {
			obrazovka.setText("==========================\n" + " Vyplňte MENO a PRIEZVISKO na letenku \n a potvrďte stlačením na tlačidlo 1: "
					+ "\n" + "==========================\n");

		} else if (stav == VYBERSEDADLO) {
			var bs = new StringBuffer();
			//nadpis pre business class
			bs.append("============Business CLASS============\n");
			// vypisat cisla pre business class
			for (int i = 1; i <= let.getBiznisObsadenie(); i++) {
				//vypytat spravne sedadlo
				Sedadlo sedadlo = let.getSedadla().get(i-1);
				bs.append(sedadlo.getObsadene() ? "X": i);
				bs.append(" ");
				if (i % 10 == 0){
					bs.append("\n");
				}
			}
			// nadpis pre ekonomicku triedu
			bs.append("============Economy CLASS============\n");
			//vypisat cisla pre ekonomicku triedu
			for (int i = let.getBiznisObsadenie() + 1; i <= let.getObsadenie(); i++) {
				//vypytat spravne sedadlo
				Sedadlo sedadlo = let.getSedadla().get(i-1);
				bs.append(sedadlo.getObsadene() ? "X": i);
				bs.append(" ");
				if (i % 10 == 0){
					bs.append("\n");
				}
			}
			bs.append("==================================\n" + "Vyberte si číslo sedadla\n" +
					" a potvrďte kliknutím na 1. \n"
					+ "==================================\n");
				obrazovka.setText(bs.toString());

		} else if (stav == OBSADENESEDADLO) {
			obrazovka.setText("========================\n" + "Toto sedadlo je obsadené.\n" +
					" Prosím, vyberte si zo sedadiel, ktoré nie sú obsadené.\n" +
					" Potvrďte kliknutím na 1." + "========================\n");

		} else if (stav == POKRACUJKONIEC) {
			obrazovka.setText("=============================\n"
					+ " Zvoľte 1, ak chcete rezervovať ďalšie sedadlo \n"
					+ " alebo č. 2 ak chcete zaplatiť.\n "
					+ "=============================\n");

		} else if (stav == ZRUSENIEREGISTRACIE) {
			obrazovka.setText(" Zadali ste zrušenie rezervácie ");

		} else if (stav == IDREGISTRACIE) {
			obrazovka.setText(" Zadajte vaše ID rezervácie ");

		} else if (stav == REGISTRACIAZRUSENA) {
			obrazovka.setText(" Zrušili ste rezerváciu. ");

		} else if (stav == KONIECPROGRAMU) {
			var st = new StringBuffer();
			st.append("========================\n");
			st.append("            Koniec programu. \n");
			st.append("========================\n");
			st.append(" Cena: " + objednavka.getSuma() + "€\n");
			st.append(" ID rezervácie: " + objednavka.getZakaznik().getId() + "\n");
			st.append("========================\n");
			st.append(" Stlačte 2 pre vygenerovanie letenky.\n");
			obrazovka.setText(st.toString());
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
		tlacidlo1.setFont(new Font("Tahoma", Font.BOLD, 20));
		tlacidlo1.setBounds(282, 84, 89, 23);

		tlacidlo1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

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
					var zakaznik = new Zakaznik(rand.nextInt(1000), meno, priezvisko, adresa);
					objednavka = new Objednavka(zakaznik);

					stav = VYBERLET;
					klavesnica.setText("");
					nastavObrazovku();

				} else if (stav == VYBERLET) {
					let = lety.get(0);

					stav = VYBERDRUHLETU;
					klavesnica.setText("");
					nastavObrazovku();

				} else if (stav == VYBERDRUHLETU) {
					spiatocny = false;


					stav = VYBERTYPLETU;
					klavesnica.setText("");
					nastavObrazovku();

				} else if (stav == VYBERTYPLETU) {
					typPasaziera = TypPasaziera.DOSPELY;

					stav = VYBERTRIEDU;

					klavesnica.setText("");
					nastavObrazovku();

				} else if (stav == VYBERTRIEDU) {
					triedaLetu = Trieda.BIZNIS;

					stav = VYPLNMENOLETENKY;
					klavesnica.setText("");
					nastavObrazovku();

				} else if (stav == VYPLNMENOLETENKY) {
					var meno = klavesnica.getText();
					if (!meno.isEmpty()) {
						menoNaLetenke = klavesnica.getText();
						stav = VYBERSEDADLO;
					}

					klavesnica.setText("");
					nastavObrazovku();
				} else if (stav == VYBERSEDADLO) {
					int zvoleneSedadlo = Integer.parseInt(klavesnica.getText());

					if (let.getSedadla().get(zvoleneSedadlo - 1).getObsadene()) {
						stav = OBSADENESEDADLO;
					} else {
						cisloSedadla = zvoleneSedadlo;
						let.getSedadla().get(zvoleneSedadlo-1).setObsadene(true);

						Letenka letenka = new Letenka(let, menoNaLetenke, cisloSedadla, typPasaziera, triedaLetu, spiatocny);
						objednavka.pridajLetenku(letenka);

						stav = POKRACUJKONIEC;
					}


					klavesnica.setText("");
					nastavObrazovku();
				}
				else if (stav == OBSADENESEDADLO)  {

					stav = VYBERSEDADLO;
					klavesnica.setText("");
					nastavObrazovku();

				} else if (stav == POKRACUJKONIEC) {
					stav = VYBERLET;
					klavesnica.setText("");
					nastavObrazovku();
				}
			}
		});

		tlacidlo2 = new JButton("2");
		tlacidlo2.setFont(new Font("Tahoma", Font.BOLD, 20));
		tlacidlo2.setBounds(200, 155, 89, 23);

		tlacidlo2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (stav == VYBERLET) {
					let = lety.get(1);

					stav = VYBERDRUHLETU;
					klavesnica.setText("");
					nastavObrazovku();

				} else if (stav == VYBERDRUHLETU) {
					spiatocny = true;

					stav = VYBERTYPLETU;
					klavesnica.setText("");
					nastavObrazovku();

				} else if (stav == VYBERTYPLETU) {
					typPasaziera = TypPasaziera.DIETA;


					stav = VYBERTRIEDU;
					klavesnica.setText("");
					nastavObrazovku();

				} else if (stav == VYBERTRIEDU) {
					triedaLetu = Trieda.EKONOMICKA;

					stav = VYBERSEDADLO;
					klavesnica.setText("");
					nastavObrazovku();

				} else if (stav == POKRACUJKONIEC) {
					stav = KONIECPROGRAMU;
					klavesnica.setText("");
					nastavObrazovku();

				} else if (stav == KONIECPROGRAMU) {
					try {
						zapisovac.zapisLetenky(objednavka);
					}catch (IOException ex) {
						ex.printStackTrace();
						System.exit(-1);
					}
				}
			}
		});

		tlacidlo3 = new JButton("3");
		tlacidlo3.setFont(new Font("Tahoma", Font.BOLD, 20));
		tlacidlo3.setBounds(282, 215, 89, 23);

		tlacidlo3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (stav == START) {
					stav = KONIECPROGRAMU;
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
		obrazovka = new JTextArea(0, 0);
		obrazovka.setSize(450, 200);
		obrazovka.setEditable(false);
		obrazovka.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		obrazovka.setFont(new Font("Serif", Font.ITALIC, 20));

	}

	private void nastavAtributy() {
		setTitle("Rezervácia leteniek RESY");
		setSize(SIRKA, VYSKA);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

	}

}
