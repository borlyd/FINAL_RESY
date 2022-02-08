package letenky;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class RezevaciaTester {

	private static boolean rezervovaneSedadlo = false;

	private static Calendar nastavDatumACas(Calendar c, int rok, int mes, int den, int hod, int min, int sec) {
		c.set(Calendar.YEAR, rok);
		c.set(Calendar.MONTH, mes);
		c.set(Calendar.DATE, den);
		c.set(Calendar.HOUR, hod);
		c.set(Calendar.MINUTE, min);
		c.set(Calendar.SECOND, sec);
		return c;
	}

	public static void main(String[] args) throws IOException {
		int moznost = 0;
		ArrayList<Zakaznik> pasaziery = new ArrayList<Zakaznik>();
		ArrayList<Let> lety = new ArrayList<Let>();
		ArrayList<Rezervacia> rezervacie = new ArrayList<Rezervacia>();
		Objednavka objednavka = new Objednavka(pasaziery.get(0)); // TODO toto je nedokoncene

		Calendar c = Calendar.getInstance(); // https://docs.oracle.com/javase/7/docs/api/java/util/Calendar.html

		c = nastavDatumACas(c, 2022, 02, 12, 2, 30, 0);
		Date d1 = c.getTime();
		lety.add(new Let("Mnichov", 80, d1, 50, 10));

		c = nastavDatumACas(c, 2022, 01, 14, 5, 0, 0);
		Date d2 = c.getTime();
		lety.add(new Let("Londyn", 100, d2, 100, 30));

		c = nastavDatumACas(c, 2022, 05, 22, 3, 30, 0);
		Date d3 = c.getTime();
		lety.add(new Let("Barcelona", 120, d3, 40, 10));

		System.out.println("----------------------------------------");
		System.out.println("Rezervacia leteniek");
		System.out.println("----------------------------------------");
		System.out.println("Ceny:");
		System.out.println("Klasický lístok: 4,50€");
		System.out.println("Detský lístok: 3€");

		Scanner scanner = new Scanner(System.in);

		do {
			System.out.println("----------------------------------------");
			System.out.println("Zadajte 1 pre rezerváciu");
			System.out.println("Zadajte 2 pre zrušenie rezervácie");
			System.out.println("Zadajte 3 pre ukončenie");
			System.out.println("----------------------------------------");

			System.out.println("Zadajte možnosť: ");
			moznost = scanner.nextInt();
			scanner.nextLine();

			while (moznost != 1 && moznost != 2 && moznost != 3) {
				System.out.println("----------------------------------------");
				System.out.println("Zadali ste zlé číslo. Prosím zadajte 1-3.");
				moznost = scanner.nextInt();
				scanner.nextLine();
			}

			if (moznost == 1) {
				//pokladna.resetCeny();
				System.out.println("----------------------------------------");
				System.out.println("Zadali ste rezerváciu");
				System.out.println("----------------------------------------");

				System.out.println("Zadajte svoje meno: ");
				String meno = scanner.nextLine();
				System.out.println("Zadajte svoje priezvisko: ");
				String priezvisko = scanner.nextLine();
				System.out.println("Zadajte svoju adresu: ");
				String adresa = scanner.nextLine(); // tu musi byt line inak to nepojde ked das medzeru

				Random rand = new Random();
				int id = rand.nextInt(1000);
				Zakaznik zakaznik = new Zakaznik(id, meno, priezvisko, adresa);
				pasaziery.add(zakaznik);

				System.out.println("----------------------------------------");
				System.out.println("Vyberte let");
				System.out.println("----------------------------------------");

				for (int i = 0; i < lety.size(); i++) {
					int cisloLetu = i + 1;
					System.out.println("Let č.: " + cisloLetu);
					System.out.println("Nazov letu: " + lety.get(i).getNazov() + " cena letu: "
							+ lety.get(i).getCenaLetu() + " | " + lety.get(i).getCasOdletu());
					System.out.println();
				}

				System.out.println("----------------------------------------");
				System.out.println("Zadajte č. letu: ");
				int let = scanner.nextInt();

				while (let < 1 || let > lety.size()) {
					System.out.println("----------------------------------------");
					System.out.println("Zadali ste zlé číslo. Prosím zadajte 1-" + lety.size() + ".");
					let = scanner.nextInt();
				}
				System.out.println("Zadajte č. 1 pre jednosmerný let alebo č. 2 pre spiatocny let: ");
				int druh = scanner.nextInt();
				while (druh != 1 && druh != 2) {
					System.out.println("----------------------------------------");
					System.out.println("Zadali ste zlé číslo. Prosím zadajte 1-2.");
					druh = scanner.nextInt();
				}

				//pokladna.zaratajPlatbu("druh", druh, lety.get(0).getCenaLetu());
				int nakup = 0;

				do {
					rezervovaneSedadlo = false;
					System.out.println("----------------------------------------");
					System.out.println("Zadajte [1] pre klasický lístok alebo [2] pre detský lístok: ");
					int letenka = scanner.nextInt();

					while (letenka != 1 && letenka != 2) {
						System.out.println("----------------------------------------");
						System.out.println("Zadali ste zlé číslo. Prosím zadajte 1-2.");
						letenka = scanner.nextInt();
					}

					//pokladna.zaratajPlatbu("typ", letenka, 0);

					System.out.println("----------------------------------------");
					lety.get(let - 1).obsadenie();
					System.out.println("----------------------------------------");
					System.out.println(" Zvolte č. 1 pre biznis triedu a č. 2 pre ekonomickú triedu ");
					int trieda = scanner.nextInt();
					while (trieda != 1 && trieda != 2) {
						System.out.println("----------------------------------------");
						System.out.println("Zadali ste zlé číslo. Prosím zadajte 1-2.");
						trieda = scanner.nextInt();
					}

					//pokladna.zaratajPlatbu("trieda", trieda, 0);
					System.out.println("Vyberte sedadlo: ");
					int sedadlo = scanner.nextInt();

					while (sedadlo < 1 || sedadlo > 50) {

						System.out.println("----------------------------------------");
						System.out.println("Zadali ste zlé číslo. Prosím zadajte v rozmedzi 1-50.");
						sedadlo = scanner.nextInt();
					}

					do {
						if (trieda == 1 && sedadlo > 10 || sedadlo < 1) {
							System.out.println(
									"Zvolili ste nespravne sedadlo, pre volbu biznis triedy zadajte č. sedadla 1 az 10 ");
							sedadlo = scanner.nextInt();
						} else if (trieda == 2 && sedadlo <= 10) {
							System.out.println(
									"Zvolili ste nespravne sedalo, pre volbu ekonomickej triedy zadajte č. sedadla 11 az 50");
							sedadlo = scanner.nextInt();
						} else {
							rezervovaneSedadlo = true;
						}

					} while (rezervovaneSedadlo == false);

					System.out.println("----------------------------------------");
					Rezervacia rezervacia = new Rezervacia(zakaznik, lety.get(let - 1));

					if (rezervacia.rezervaciaSedadlo(sedadlo)) {
						rezervacie.add(rezervacia);
						System.out.println("Rezervovali ste si sedadlo.");
					} else {
						System.out.println("Sedadlo je obsadené.");
						//pokladna.zaratajPlatbu("typ", letenka, 0);
					}

					System.out.println("----------------------------------------");
					System.out
							.println("Zadajte [1] ak chcete rezervovať ďalšie sedadlo alebo [2] ak chcete zaplatiť: ");
					nakup = scanner.nextInt();

					while (nakup < 1 || nakup > 2) {
						System.out.println("----------------------------------------");
						System.out.println("Zadali ste zlé číslo. Prosím zadajte 1-2.");
						nakup = scanner.nextInt();
					}

				} while (nakup == 1);

				System.out.println("----------------------------------------");
				System.out.println("Cena: " + objednavka.getSuma() + "€");
				System.out.println("ID rezervácie: " + zakaznik.getId());

				ZapisovacLeteniek zapisovac = new ZapisovacLeteniek();
				zapisovac.zapisLetenky(objednavka);

			}

			if (moznost == 2) {
				//pokladna.resetCeny();
				System.out.println("----------------------------------------");
				System.out.println("Zadali ste zrušenie rezervácie");
				System.out.println("----------------------------------------");

				System.out.println("Zadajte vaše ID rezervácie: ");
				int id = scanner.nextInt();
				System.out.println("----------------------------------------");

				int zrusenie = 0;

				for (Zakaznik zakaznik : pasaziery) {
					if (zakaznik.getId() == id) {
						for (Rezervacia rezervacia : rezervacie) {
							if (rezervacia.getZakaznik().getId() == zakaznik.getId()) {
								rezervacia.zrusenieSedadlo();
								zrusenie = 1;
							}
						}
					}
				}

				if (zrusenie == 1) {
					System.out.println("Zrušili ste rezerváciu.");
				} else {
					System.out.println("Zadané ID rezervácie neexistuje.");
				}
			}

			if (moznost == 3) {
				System.out.println("----------------------------------------");
				System.out.println("Koniec programu.");
				System.out.println("----------------------------------------");
				System.exit(0);
			}
		} while (true);
	}
}
