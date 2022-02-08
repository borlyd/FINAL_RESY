package letenky;

public class Letenka {
    public final Let let;
    public final String menoCestujuceho;
    public final int cisloSedadla;
    public final TypPasaziera typPasaziera;
    public final Trieda trieda;
    public final boolean spiatocnyLet;
    public final double cena;

    public Letenka(Let let, String menoCestujuceho, int cisloSedadla, TypPasaziera typPasaziera, Trieda trieda, boolean spiatocnyLet) {
        this.let = let;
        this.menoCestujuceho = menoCestujuceho;
        this.cisloSedadla = cisloSedadla;
        this.typPasaziera = typPasaziera;
        this.trieda = trieda;
        this.spiatocnyLet = spiatocnyLet;
        cena = vypocitajSumu(let.getCenaLetu());
    }

    private double vypocitajSumu (double zakladnaSuma){
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
        return hodnota;
    }
}