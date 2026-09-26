public class Clanovi {
    String ime;
    String prezime;
    String datum_clanstva;
    int idbm;
    String ime_oca;
    boolean status_posudbe;

    Clanovi(String ime, String prezime, String datum_clanstva, int idbm, String ime_oca, boolean status_posudbe){
        this.ime = ime;
        this.prezime = prezime;
        this.datum_clanstva = datum_clanstva;
        this.idbm = idbm;
        this.ime_oca = ime_oca;
        this.status_posudbe = status_posudbe;

    }
}
