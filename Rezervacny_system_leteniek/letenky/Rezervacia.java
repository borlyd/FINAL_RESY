package letenky;

public class Rezervacia {
	Zakaznik zakaznik;
	Let let;
	int sedadlo;

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