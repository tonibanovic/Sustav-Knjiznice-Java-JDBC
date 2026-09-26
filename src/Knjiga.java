public class Knjiga {
    int id;
    String naslov;
    String autor_ime;
    String autor_prezime;
    int godina_izdanja;
    int zaliha;

    Knjiga(int id, String naslov, String autor_ime, String autor_prezime, int godina_izdanja, int zaliha){
        this.id = id;
        this.naslov = naslov;
        this.autor_ime = autor_ime;
        this.autor_prezime = autor_prezime;
        this.godina_izdanja = godina_izdanja;
        this.zaliha = zaliha;
    }
}
