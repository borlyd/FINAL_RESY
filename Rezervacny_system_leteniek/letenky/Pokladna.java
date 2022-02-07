package letenky;

import java.util.ArrayList;

public class Pokladna {

	private double suma;

	private ArrayList <Letenka> letenky = new ArrayList<>();

	public Pokladna() {
	}

	 public void pridajLetenku (Letenka letenka){
		 letenky.add(letenka);
		 prepocitajSumu();
	}

	public void odstranLetenku (Letenka letenka){
		letenky.remove(letenka);
		prepocitajSumu();
	}

	private void prepocitajSumu (){
		double sum = 0;
		for(Letenka l : letenky){
			sum += l.cena;
		}
		this.suma = sum;
	}

	public ArrayList<Letenka> getLetenky() {
		return letenky;
	}

	public void reset() {
		this.suma = 0;
		this.letenky = new ArrayList<>();
	}


	public double getSuma() {
		return suma;
	}



}
