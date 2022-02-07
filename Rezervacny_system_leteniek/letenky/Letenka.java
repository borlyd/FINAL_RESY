package letenky;

public class Letenka {
    public String nazovLetu;
    public int cisloSedadla;
    public double cena;
    public TypPasaziera typPasaziera;
    public Trieda trieda;
    public boolean spiatocnyLet;

    public Letenka(Let let, int cisloSedadla, TypPasaziera typPasaziera, Trieda trieda, boolean spiatocnyLet) {
        this.nazovLetu = let.getNazov();
        this.cisloSedadla = cisloSedadla;
        this.typPasaziera = typPasaziera;
        this.trieda = trieda;
        this.spiatocnyLet = spiatocnyLet;

        vypocitajSumu(let.getCenaLetu());
    }

    private void vypocitajSumu (double zakladnaSuma){
        double hodnota = zakladnaSuma;
        if (TypPasaziera.DOSPELY == typPasaziera) {
        hodnota += 4.50;
        }
        else {hodnota += 3.00;}
        if (Trieda.BIZNIS == trieda){
            hodnota += 30;
        }
        else {hodnota += 0;}
        if (spiatocnyLet){
            hodnota *= 2;
        }
        this.cena = hodnota;
    }
}