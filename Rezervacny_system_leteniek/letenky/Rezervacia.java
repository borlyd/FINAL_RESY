package letenky;

public class Rezervacia {
	Zakaznik zakaznik;
	Let let;
	int sedadlo;

	public static final int START = 1;
	public static final int REGISTRACIAMENO = 2;
	public static final int REGISTRACIAPRIEZVISKO = 3;
	public static final int REGISTRACIAADRESA = 4;

	public static final int VYBERLET = 5;
	public static final int VYBERDRUHLETU = 6;
	public static final int VYBERTYPLETU = 7;
	public static final int VYBERSEDADLO = 8;

	public static final int POKRACUJKONIEC = 9;

	public static final int ZRUSENIEREGISTRACIE = 10;
	public static final int IDREGISTRACIE = 11;

	int stav;

	public Rezervacia(Zakaznik zakaznik, Let let) {
		this.zakaznik = zakaznik;

		this.let = let;
	}

	public Rezervacia() {

	}

	public void setStav(int stav) {
		this.stav = stav;
	}

	public int getStav() {
		return stav;
	}

	public Zakaznik getZakaznik() {
		return this.zakaznik;
	}

	public void setSedadlo(int sedadlo) {
		this.sedadlo = sedadlo;
	}

	public Let getLet() {
		return let;
	}

	public int getSedadlo() {
		return sedadlo;
	}

	public boolean rezervaciaSedadlo(int sedadlo) {
		if (let.getSedadla().get(sedadlo).getObsadene()) {
			return false;
		} else {
			let.getSedadla().get(sedadlo).setObsadene(true);
			setSedadlo(sedadlo);
			return true;
		}
	}

	public boolean zrusenieSedadlo() {
		let.getSedadla().get(this.sedadlo).setObsadene(false);
		return true;
	}
}