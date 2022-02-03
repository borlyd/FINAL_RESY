package letenky;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Let {
    private String nazov;
	private Date casOdletu;
    private int obsadenie = 50;
    private double cenaLetu;
    private ArrayList<Sedadlo> sedadla;
	private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public Let(String nazov, double cenaLetu, Date d1) {
	this.nazov = nazov;
	this.cenaLetu = cenaLetu;
	this.casOdletu = d1;
	sedadla = new ArrayList<Sedadlo>();
	vytvorSedadla(this.obsadenie);
    }

    public String getNazov() {
	return nazov;
    }


	public String getCasOdletu() {
		return format.format(casOdletu);
	}

	public double getCenaLetu() {
	return cenaLetu;
    }

    public ArrayList<Sedadlo> getSedadla() {
	return sedadla;
    }

    public void vytvorSedadla(int obsadenie) {
	for (int i = 1; i <= obsadenie; i++) {
	    sedadla.add(new Sedadlo(i, false));
	}
    }

    public void obsadenie() {
		for (int i = 1; i < this.obsadenie; i++) {
			Sedadlo sedadlo = sedadla.get(i);
			if (sedadlo.getObsadene()) {
				System.out.print(" X ");
				continue;
			} else if (i == 1) {
				System.out.println("============FirstCLASS============");
			}

			if (i == 11) {
				System.out.println();
				System.out.println("===========Economy class==========");
			}
			System.out.printf("%2d ", i);
			if (i == 10 || i == 20 || i == 30 || i == 40 || i == 50) {
				System.out.println();
			}
		}
	}
}