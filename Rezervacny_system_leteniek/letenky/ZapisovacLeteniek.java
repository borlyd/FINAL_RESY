package letenky;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;

public class ZapisovacLeteniek {

	public void zapisLetenky(Objednavka objednavka) throws IOException, URISyntaxException {

		var zakaznik = objednavka.getZakaznik();

		/**
		 * Vytvori subor a nasledne do neho zapise udaje z rezervacie letenky
		 */
		File rezervovanaLetenka = new File((this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI()
				+ zakaznik.getMeno() + zakaznik.getPriezvisko() + zakaznik.getId()) + ".txt");

		FileWriter fileWriter = new FileWriter(rezervovanaLetenka);
		PrintWriter printWriter = new PrintWriter(fileWriter);

		System.out.println(rezervovanaLetenka.getAbsolutePath());

		printWriter.println("Id objednavky : " + zakaznik.getId());
		printWriter.println("Meno : " + zakaznik.getMeno() + " " + zakaznik.getPriezvisko());
		printWriter.println("Adresa : " + objednavka.getZakaznik().getAdresa());
		printWriter.println();
		printWriter.println("K uhrade : " + objednavka.getSuma() + " €");
		printWriter.println("=================================================================");

		for (Letenka letenka : objednavka.getLetenky()) {
			var let = letenka.let;
			printWriter.println("-----------------------------LETENKA-----------------------------");
			printWriter.println("Meno : " + letenka.menoCestujuceho);
			printWriter.println("Nazov letu : " + let.getNazov() + " | Čas odletu : " + let.getCasOdletu());
			printWriter.println("Čislo sedadla : " + letenka.cisloSedadla + " | " + letenka.trieda.toString());

			if (letenka.typPasaziera == TypPasaziera.DOSPELY) {
				printWriter.println("Dospelý");
			} else {
				printWriter.println("Dieťa");
			}
			if (letenka.spiatocnyLet) {
				printWriter.println("Druh letu: Spiatočný let");
			} else {
				printWriter.println("Druh letu: Jednosmerný let");
			}
			printWriter.println("=================================================================\n");
		}

		printWriter.close();

		// otvori subor s rezervovanou letenkou a vsetkymi udajmi rezervacie
		Desktop desktop = Desktop.getDesktop();
		desktop.open(rezervovanaLetenka);
		// desktop.print(rezervovanaLetenka);
	}
}
