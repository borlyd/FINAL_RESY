package letenky;

public class Pokladna {

	private double suma;

	RezervaciaGUI rezervaciaGUI = new RezervaciaGUI();

	public Pokladna() {
	}

	public void pripocitajKSume(double cena) {
		this.suma += cena;
	}

	public void zaratajPlatbuZaCisloLetu(int cisloLetu) {
		if (cisloLetu == 1) {
			pripocitajKSume(rezervaciaGUI.lety.get(0).getCenaLetu());
		} else if (cisloLetu == 2) {
			pripocitajKSume(rezervaciaGUI.lety.get(1).getCenaLetu());
		} else if (cisloLetu == 3) {
			pripocitajKSume(rezervaciaGUI.lety.get(2).getCenaLetu());
		}
	}

	public void zaratajPlatbuZaDruhLetu(int druhLetu) {
		if (druhLetu == 1) {
			pripocitajKSume(0);

		} else if (druhLetu == 2) {
			pripocitajKSume(suma);
		}
	}

	public void zaratajPlatbuZaTypLetu(int typLetu) {
		if (typLetu == 1) {
			pripocitajKSume(4.50);
		} else if (typLetu == 2) {
			pripocitajKSume(3);
		}
	}

	public void zaratajPlatbuZaTriedu(int triedaLetu) {
		if (triedaLetu == 1) {
			pripocitajKSume(30);
		} else if (triedaLetu == 2) {
			pripocitajKSume(0);
		}
	}

	public void resetCeny() {
		this.suma = 0;
	}

	public double getSuma() {
		return suma;
	}

}
