package letenky;

public class Pokladna {

    private double suma;
    private String typPlatby;

    public Pokladna() {
    }

    public void pripocitajKSume(double cena) {
	this.suma += cena;
    }

    public void resetTyp() {
	this.typPlatby = "";
    }

    public void zaratajPlatbu(String typ, int zvolenaMoznost, double cenaLetu) {
	this.typPlatby = typ;
	switch (typPlatby) {
	case "typ":
	    if (zvolenaMoznost == 1) {
		pripocitajKSume(4.50);
		break;
	    } else if (zvolenaMoznost == 2) {
		pripocitajKSume(3);
		break;
	    }

	case "druh":
	    if (zvolenaMoznost == 1) {
		pripocitajKSume(cenaLetu);
		break;
	    } else if (zvolenaMoznost == 2) {
		pripocitajKSume(cenaLetu * 2);
		break;
	    }
	case "trieda":
	    if (zvolenaMoznost == 1) {
		pripocitajKSume(30);
		break;
	    } else if (zvolenaMoznost == 2) {
		pripocitajKSume(0);
		break;
	    }
	}

    }

    public void resetCeny() {
	this.suma = 0;
    }

    public double getSuma() {
	return suma;
    }

}
