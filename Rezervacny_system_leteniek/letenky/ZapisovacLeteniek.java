package letenky;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ZapisovacLeteniek {

    private Zakaznik zakaznik;
    private Pokladna pokladna;
    private ArrayList<Zakaznik> pasaziery;
    private ArrayList<Let> lety;
    private ArrayList<Rezervacia> rezervacie;
    private int let, druh, id;

    public ZapisovacLeteniek(Zakaznik zakaznik, ArrayList<Let> lety, int let, int druh, ArrayList<Zakaznik> pasaziery,
	    int id, ArrayList<Rezervacia> rezervacie, Pokladna pokladna) {
	this.zakaznik = zakaznik;
	this.pokladna = pokladna;
	this.pasaziery = pasaziery;
	this.lety = lety;
	this.rezervacie = rezervacie;
	this.let = let;
	this.druh = druh;
	this.id = id;

    };

    public void zapisLetenku() throws IOException {

	/**
	 * Vytvori subor a nasledne do neho zapise udaje z rezervacie letenky
	 */
	File rezervovanaLetenka = new File((zakaznik.getMeno() + zakaznik.getPriezvisko() + zakaznik.getId()) + ".txt");
	FileWriter fileWriter = new FileWriter(rezervovanaLetenka);
	PrintWriter printWriter = new PrintWriter(fileWriter);

	printWriter.println("-----------------------------LETENKA-----------------------------");
	printWriter.println("Id cislo vasej rezervacie je : " + zakaznik.getId());
	printWriter.println("Meno zakaznika : " + zakaznik.getMeno() + " " + zakaznik.getPriezvisko());
	printWriter.println("Adresa : " + zakaznik.getAdresa());
	printWriter.println("Nazov letu: " + lety.get(let).getNazov() + "\n" + "Cislo letu: #" + let + " | "
		+ lety.get(let).getCasOdletu() + "\n");
	if (druh == 1) {
	    printWriter.println("Druh letu: Jednosmerný let");
	} else {
	    printWriter.println("Druh letu: Spiatočný let");
	}
	printWriter.println("=================================================================");

	printWriter.println("Cisla rezervovanych sedadiel :");

	for (Zakaznik cestujuci : pasaziery) {
	    if (cestujuci.getId() == id) {
		for (Rezervacia rezervacia : rezervacie) {
		    if ((rezervacia.getZakaznik().getId() == cestujuci.getId()) && (rezervacia.getSedadlo() <= 10)) {
			printWriter.println("Sedadlo First class cislo: " + rezervacia.getSedadlo() + "\n");
		    } else if (rezervacia.getZakaznik().getId() == cestujuci.getId()) {
			printWriter.println("Sedadlo Economy class cislo: " + rezervacia.getSedadlo() + "\n");
		    }
		}
	    }
	}

	printWriter.println("=================================================================");
	printWriter.println("K uhrade:" + pokladna.getSuma() + " €");

	printWriter.close();

	// otvori subor s rezervovanou letenkou a vsetkymi udajmi rezervacie
	Desktop desktop = Desktop.getDesktop();
	desktop.open(rezervovanaLetenka);
	// desktop.print(rezervovanaLetenka);
    }
}
