package letenky;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
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
	private JButton tlacidlo1;
	private JButton tlacidlo2;
	private JButton tlacidlo3;
	private JPanel panelTlacidiel;
	private JTextField textovePole;

	private Stav stav;

	private ZapisovacLeteniek zapisovac = new ZapisovacLeteniek();

	ArrayList<Zakaznik> pasazieri = new ArrayList<Zakaznik>();
	ArrayList<Let> lety = new ArrayList<Let>();
	ArrayList<Rezervacia> rezervacie = new ArrayList<Rezervacia>();
	Objednavka objednavka;

	String meno;
	String priezvisko;
	String adresa;
	Let let;
	boolean spiatocny; // 1 jednosmerny a 2 obojsmerny
	TypPasaziera typPasaziera; // 1 dospely a 2 dieta
	Trieda triedaLetu; // ekonomick√° a biznis trieda
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

		nastavAtributy();
		vytvorObrazovku();
		vytvorTlacidla();
		vytvorPanelTlacidiel();
		vytvorTextovePole();

		setLayout(new FlowLayout());


		add(obrazovka);
		add(panelTlacidiel);
		nastavObrazovku();
		add(textovePole, BorderLayout.SOUTH);

		Calendar c = Calendar.getInstance(); // https://docs.oracle.com/javase/7/docs/api/java/util/Calendar.html

		c = nastavDatumACas(c, 2022, 02, 12, 2, 30, 0);
		Date d1 = c.getTime();
		lety.add(new Let("Mn√≠chov", 80, d1, 50, 10));

		c = nastavDatumACas(c, 2022, 01, 14, 5, 0, 0);
		Date d2 = c.getTime();
		lety.add(new Let("Lond√Ĺn", 100, d2, 100, 30));

		c = nastavDatumACas(c, 2022, 05, 22, 3, 30, 0);
		Date d3 = c.getTime();
		lety.add(new Let("Barcelona", 120, d3, 40, 10));

	}

	public void setText(String text) {
		textovePole.setText(text);
	}

	public String getText() {
		return textovePole.getText();
	}

	private void vytvorTextovePole() {
		textovePole = new JTextField(35);
		textovePole.setBounds(200, 100, 50, 50);
	}
	private void nastavObrazovku() {

		if (stav == START) {
			obrazovka.setText(

					"========================\n" +  "   Rezerv√°cia leteniek   \n" + "========================\n"
							+ " - Zadajte 1 pre rezerv√°ciu\n"
							+ " - Zadajte 2 pre zruŇ°enie rezerv√°cie\n"
							+ " - Zadajte 3 pre ukonńćenie programu\n");
		} else if (stav == REGISTRACIAMENO) {
			obrazovka.setText(
					"========================\n" + "   Zadali ste rezerv√°ciu. \n" + "========================\n"
							+ " Zadajte svoje MENO\n a potvrńŹte kliknut√≠m na 1. ");


		} else if (stav == REGISTRACIAPRIEZVISKO) {
			obrazovka.setText("========================\n" + "   Zadali ste svoje meno. \n" + "========================\n"
					+ " Zadajte svoje PRIEZVISKO.\n a potvrńŹte kliknut√≠m na 1. ");

		} else if (stav == REGISTRACIAADRESA) {
			obrazovka.setText("========================\n" + "   Zadali ste priezvisko. \n" + "========================\n"
					+ " Zadajte svoju ADRESU.\n " + "PotvrńŹte kliknut√≠m na 1. ");

		} else if (stav == VYBERLET) {
			var sb = new StringBuffer();

			sb.append( "==================================\n" +"	VYBERTE SI LET\n"
					+ "==================================\n");

			for (int i = 0; i < lety.size(); i++) {
				int cisloLetu = i + 1;
				sb.append(" Let ńć.: " + cisloLetu + "\n");
				sb.append(" N√°zov letu: " + lety.get(i).getNazov() + "\n" + " Cena letu: " + lety.get(i).getCenaLetu()
						+ " | " + "ńĆas odletu: " + lety.get(i).getCasOdletu() + "\n");
				sb.append("\n");

			}
			sb.append("==================================\n");
			sb.append( " ZVOńĹTE ńć. letu (1, 2, 3): \n");

			obrazovka.setText(sb.toString());

		} else if (stav == VYBERDRUHLETU) {
			obrazovka.setText("========================\n" + " Stlańćte 1 pre jednosmern√Ĺ let \n alebo 2 pre spiatocny let: "
					+ "\n" + "========================\n");

		} else if (stav == VYBERTYPLETU) {
			obrazovka.setText("========================\n" + " Stlańćte 1 pre klasick√Ĺ l√≠stok \n alebo 2 pre detsk√Ĺ l√≠stok: "
					+ "\n" + "========================\n");

		} else if (stav == VYBERTRIEDU) {
			obrazovka.setText("========================\n" + " Stlańćte 1 pre biznis triedu \n alebo 2 pre ekonomick√ļ triedu: "
					+ "\n" + "========================\n");

		} else if (stav == VYPLNMENOLETENKY) {
			obrazovka.setText("==========================\n" + " VyplŇąte MENO a PRIEZVISKO na letenku \n a potvrńŹte stlańćen√≠m na tlańćidlo 1: "
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
			bs.append("==================================\n" + "Vyberte si ńć√≠slo sedadla\n" +
					" a potvrńŹte kliknut√≠m na 1. \n"
					+ "==================================\n");
				obrazovka.setText(bs.toString());

		} else if (stav == OBSADENESEDADLO) {
			obrazovka.setText("========================\n" + "Toto sedadlo je obsaden√©.\n" +
					" Pros√≠m, vyberte si zo sedadiel, ktor√© nie s√ļ obsaden√©.\n" +
					" PotvrńŹte kliknut√≠m na 1." + "========================\n");

		} else if (stav == POKRACUJZAPLATIT) {
			obrazovka.setText("=============================\n"
					+ " Zvońĺte 1, ak chcete rezervovaŇ• ńŹalŇ°ie sedadlo \n"
					+ " alebo ńć. 2 ak chcete zaplatiŇ•.\n "
					+ "=============================\n");

		} else if (stav == ZRUSITIDREGISTRACIE) {
			obrazovka.setText(" Zadali ste zruŇ°enie rezerv√°cie.\n "
					+ "================================\n"
					+ " Pre zruŇ°enie zadajte ID VaŇ°ej rezerv√°cie.\n "
					+ "PotvrńŹte stlańćen√≠m 1. ");

		} else if (stav == REGISTRACIAZRUSENA) {
			obrazovka.setText(" VaŇ°a registr√°cia bola zruŇ°en√°. \n "
					+ "========================\n"
					+ " - Zadajte 1 pre nov√ļ rezerv√°ciu\n"
					+ " - Zadajte 2 pre zruŇ°enie rezerv√°cie\n"
					+ " - Zadajte 3 pre ukonńćenie programu\n");


		} else if (stav == ZAPLATIT) {
			var st = new StringBuffer();

			st.append(" Cena: " + objednavka.getSuma() + "‚ā¨\n");
			st.append(" ID rezerv√°cie: " + objednavka.getZakaznik().getId() + "\n");
			st.append("========================\n");
			st.append(" - Zadajte 1 pre rezerv√°ciu\n"
					+ " - Zadajte 2 pre zruŇ°enie rezerv√°cie\n"
					+ " - Zadajte 3 pre ukonńćenie programu\n");
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
					meno = String.valueOf(getText());
					stav = REGISTRACIAPRIEZVISKO;
					setText("");
					nastavObrazovku();

				} else if (stav == REGISTRACIAPRIEZVISKO) {
					priezvisko = String.valueOf(getText());
					stav = REGISTRACIAADRESA;
					setText("");
					nastavObrazovku();

				} else if (stav == REGISTRACIAADRESA) {
					adresa = String.valueOf(getText());
					Random rand = new Random();
					var zakaznik = new Zakaznik(rand.nextInt(1000), meno, priezvisko, adresa);
					objednavka = new Objednavka(zakaznik);

					stav = VYBERLET;
					setText("");
					nastavObrazovku();

				} else if (stav == VYBERLET) {
					let = lety.get(0);

					stav = VYBERDRUHLETU;
					setText("");
					nastavObrazovku();

				} else if (stav == VYBERDRUHLETU) {
					spiatocny = false;


					stav = VYBERTYPLETU;
					setText("");
					nastavObrazovku();

				} else if (stav == VYBERTYPLETU) {
					typPasaziera = TypPasaziera.DOSPELY;

					stav = VYBERTRIEDU;

					setText("");
					nastavObrazovku();

				} else if (stav == VYBERTRIEDU) {
					triedaLetu = Trieda.BIZNIS;

					stav = VYPLNMENOLETENKY;
					setText("");
					nastavObrazovku();

				} else if (stav == VYPLNMENOLETENKY) {
					var meno = getText();
					if (!meno.isEmpty()) {
						menoNaLetenke = getText();
						stav = VYBERSEDADLO;
					}

					setText("");
					nastavObrazovku();
				} else if (stav == VYBERSEDADLO) {
					int zvoleneSedadlo = Integer.parseInt(getText());

					if (let.getSedadla().get(zvoleneSedadlo - 1).getObsadene()) {
						stav = OBSADENESEDADLO;
					}
					else {
						cisloSedadla = zvoleneSedadlo;
						let.getSedadla().get(zvoleneSedadlo-1).setObsadene(true);

						Letenka letenka = new Letenka(let, menoNaLetenke, cisloSedadla, typPasaziera, triedaLetu, spiatocny);
						objednavka.pridajLetenku(letenka);

						stav = POKRACUJZAPLATIT;
					}


					setText("");
					nastavObrazovku();
				}
				else if (stav == OBSADENESEDADLO)  {

					stav = VYBERSEDADLO;
					setText("");
					nastavObrazovku();

				} else if (stav == POKRACUJZAPLATIT) {
					stav = VYBERLET;
					setText("");
					nastavObrazovku();
				}
				else if (stav == ZAPLATIT) {

					stav = START;
					setText("");
					nastavObrazovku();
				}
				else if (stav == ZRUSITIDREGISTRACIE) {
					int id = Integer.parseInt(getText());
					stav = REGISTRACIAZRUSENA;
					setText("");
					nastavObrazovku();

					for (Zakaznik zakaznik : pasazieri) {
						if (zakaznik.getId() == id) {
							for (Rezervacia rezervacia : rezervacie) {
								if (rezervacia.getZakaznik().getId() == zakaznik.getId()) {
									objednavka.reset();
								}
							}
						}
					}


				}
				else if (stav == REGISTRACIAZRUSENA) {

					stav = REGISTRACIAMENO;
					setText("");
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
				 if (stav == START) {
					stav = ZRUSITIDREGISTRACIE;
					setText("");
					nastavObrazovku();

				} else if (stav == VYBERLET) {
					let = lety.get(1);

					stav = VYBERDRUHLETU;
					setText("");
					nastavObrazovku();

				} else if (stav == VYBERDRUHLETU) {
					spiatocny = true;

					stav = VYBERTYPLETU;
					setText("");
					nastavObrazovku();

				} else if (stav == VYBERTYPLETU) {
					typPasaziera = TypPasaziera.DIETA;


					stav = VYBERTRIEDU;
					setText("");
					nastavObrazovku();

				} else if (stav == VYBERTRIEDU) {
					triedaLetu = Trieda.EKONOMICKA;

					stav = VYBERSEDADLO;
					setText("");
					nastavObrazovku();

				} else if (stav == POKRACUJZAPLATIT) {
					stav = ZAPLATIT;
					try {
						zapisovac.zapisLetenky(objednavka);
					}catch (IOException | URISyntaxException ex) {
						ex.printStackTrace();
						System.exit(-1);
					}
					setText("");
					nastavObrazovku();

				//} else if (stav == ZAPLATIT) {
				//	stav = ZRUSITIDREGISTRACIE;
				//	klavesnica.setText("");
				//	nastavObrazovku();
				}
				 else if (stav == REGISTRACIAZRUSENA) {

					 stav = ZRUSITIDREGISTRACIE;
					 setText("");
					 nastavObrazovku();
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
					System.exit(0);
					setText("");
					nastavObrazovku();

				} else if (stav == VYBERLET) {
					let = lety.get(2);

					stav = VYBERDRUHLETU;
					setText("");
					nastavObrazovku();
				}
				else if (stav == ZAPLATIT) {
					stav = KONIECPROGRAMU;
					System.exit(0);
					setText("");
					nastavObrazovku();
				}

				else if (stav == REGISTRACIAZRUSENA) {
					stav = KONIECPROGRAMU;
					System.exit(0);
					setText("");
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
		setTitle("Rezerv√°cia leteniek RESY");
		setSize(SIRKA, VYSKA);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

	}

}
