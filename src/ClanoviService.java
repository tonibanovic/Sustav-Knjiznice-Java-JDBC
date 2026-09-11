import java.time.LocalDate;
import java.util.ArrayList;

public class ClanoviService {
    ClanoviRepository clanoviRepository;

    ClanoviService(ClanoviRepository clanoviRepository){
        this.clanoviRepository = clanoviRepository;
    }

    public void unesi_podatke(String ime, String prezime, int idbm, String ime_oca, LocalDate datum){
        clanoviRepository.unos_clana(ime, prezime, idbm, ime_oca, datum);
    }

    public ArrayList<Clanovi> dohvatiSveClanove(){
        return clanoviRepository.prikaz_clanova();
    }
}
