
import java.util.ArrayList;

public class PosudbaService {
    KnjigaRepository kr;
    PosudbaRepository pr;
    ClanoviRepository cr;
    ClanoviUI cui;
    Knjiga kg;

    PosudbaService(KnjigaRepository kr, PosudbaRepository pr, ClanoviRepository cr, ClanoviUI cui){
        this.kr = kr;
        this.pr = pr;
        this.cr = cr;
        this.cui = cui;
    }

    public Knjiga provjera_zalihe_i_dohvat(int opcija){
        ArrayList<Knjiga> popis_knjiga = kr.popis_knjiga();
        opcija -= 1;
        int zaliha;
        String naslov;

        zaliha = popis_knjiga.get(opcija).zaliha;

        if (opcija < 0 || opcija > popis_knjiga.size()){
            return null;
        }

        Knjiga odabranaKnjiga = popis_knjiga.get(opcija);

        if(odabranaKnjiga.zaliha > 0){
            return odabranaKnjiga;
        }
        else{
            return null;
        }


    }

    public void posudba_unos_ub(Knjiga kg, int idbm){
        pr.proces_posudbe_ubazu(kg, idbm);
    }

    public ArrayList<Posudba> proces_vracanja_izBaze(int idbm){
        ArrayList<Posudba> posudba = pr.proces_vracanja_izBaze_popis(idbm);
        return posudba;
    }

    public boolean provjeraUnosaKnjige(String naslov, ArrayList<Posudba> posClan){

        for(Posudba pos : posClan){
            if(naslov.equalsIgnoreCase(pos.naslov_knjige)){
                return true;
            }
        }

        return false;

    }

    public void vracanje_knjigeUBazu(int id, int idbm){
        pr.proces_vracanja_uBazu(id, idbm);
    }

    public int knjiga(String naslov){
        return kr.dohvatiIdKnjigePoNaslovu(naslov);

    }



}
