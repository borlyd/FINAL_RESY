package letenky;

public class Sedadlo {
    private int cislo;
    private boolean obsadene;
    private String trieda;

    public String getTrieda() {
	return trieda;
    }

    public void setTrieda(String trieda) {
	this.trieda = trieda;
    }

    public Sedadlo(int cislo, boolean obsadene) {
	this.cislo = cislo;
	this.obsadene = obsadene;
    }

    public Sedadlo(int cislo, boolean obsadene, String trieda) {
        this.cislo = cislo;
        this.obsadene = obsadene;
        this.trieda = trieda;
    }



    public void setObsadene(boolean obsadene) {
	this.obsadene = obsadene;
    }

    public boolean getObsadene() {
	return this.obsadene;
    }

    public int getCislo() {
        return cislo;
	}

    }

