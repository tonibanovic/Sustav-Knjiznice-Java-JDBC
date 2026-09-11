import java.util.ArrayList;

public class KnjigaService {
    KnjigaRepository repository;

    KnjigaService(KnjigaRepository repository){
        this.repository = repository;
    }

    public void obradiIUnesiKnigu(String naslov, String imeAutora, String prezimeAutora, int godina, int zaliha){

       if(zaliha<0){
           throw new IllegalArgumentException("Zaliha ne može biti negativna!");
       }

       int autorId = repository.provjera_autora(imeAutora, prezimeAutora);

       repository.upis_knjige(naslov, autorId, godina, zaliha);

    }

    public ArrayList<Knjiga> dohvatiSveKnjige() {
        return repository.popis_knjiga();
    }


}
