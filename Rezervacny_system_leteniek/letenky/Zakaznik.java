package letenky;

public class Zakaznik {
	private int id;
	private String meno;
	private String priezvisko;
	private String adresa;

	public Zakaznik(int id, String meno, String priezvisko, String adresa) {
		this.id = id;
		this.meno = meno;
		this.priezvisko = priezvisko;
		this.adresa = adresa;
	}

	public int getId() {
		return this.id;
	}

	public String getMeno() {
		return meno;
	}

	public String getPriezvisko() {
		return priezvisko;
	}

	public String getAdresa() {
		return adresa;
	}

}