import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.MissingFormatArgumentException;
import java.util.Scanner;

public class PosudbaUI {
    KnjigaUI ku;
    ClanoviUI cu;
    PosudbaService ps;
    Scanner scanner = new Scanner(System.in);

    PosudbaUI(KnjigaUI ku, ClanoviUI cu, PosudbaService ps){
        this.ku = ku;
        this.cu = cu;
        this.ps = ps;
    }


    public void ProcesPosudbe(){
        Knjiga odabranaKnjiga = proces_posudbe_dioKnjiga();
        if(odabranaKnjiga == null) return;
        int idbm = proces_posudbe_dioClan();

        ps.posudba_unos_ub(odabranaKnjiga, idbm);

    }

    public Knjiga proces_posudbe_dioKnjiga() {
        int opcija;
        Knjiga kg = null;
        boolean done = false;
        System.out.println();
        System.out.println("Dostupne knjige su: ");

        ku.ispis_knjiga();

        System.out.println();

        while (!done) {
            try {
                System.out.print("Odaberite redni broj knjige koju clan zeli: ");
                opcija = scanner.nextInt();
                scanner.nextLine();
                done = true;
            } catch (InputMismatchException e) {
                System.out.println("Opcija ne moze biti slovo!");
                scanner.nextLine();
                continue;
            }

            kg = ps.provjera_zalihe_i_dohvat(opcija);
            if (kg != null) {
                System.out.println("Odabrana knjiga i njena zaliha:  " + kg.naslov + " | " + kg.zaliha);
            } else {
                System.out.println("Greška! Odabrna knjiga nema zaliha ili je pogrešan odabir!");
            }


        }
        return kg;
    }

    public int proces_posudbe_dioClan() {
        int reg = 0;
        int idbm = 0;
        while (reg != 1 && reg != 2) {
            System.out.print("Je li clan koji zeli posuditi knjigu registriran (da = 1 / ne = 2): ");
            try {
                reg = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Trebate upisai broj!");
                scanner.nextLine();
                continue;
            }

            if (reg != 1 && reg != 2) {
                System.out.println("Pogrešan unos! Molimo unesite 1 za DA ili 2 za NE.");
            }
        }

        if (reg == 2) {
            cu.registracija_clana();
            System.out.println("Clan uspjesno registriran! ");
        }

        System.out.println("Lista clanova: ");
        cu.ispis_clanova();

        System.out.println();
        System.out.print("Unesite idbm clana koji zeli posuditi knjigu: ");

        boolean done = false;

        while (!done) {
            try {
                idbm = scanner.nextInt();
                scanner.nextLine();
                done = true;
            } catch (InputMismatchException e) {
                System.out.println("idbm mora biti samo u brojevima!");
            }

        }
        return idbm;
    }


    public void proces_vracanja(){
        int idbm = proces_vracanja_dioClan();
        ArrayList<Posudba> posClan;
        System.out.println("Clan koji ste izabrali i njihove knjige:\n");
        posClan = ispis_clanovaPosudili(idbm);
        int id = proces_vracanja_dioKnjiga(posClan);

        ps.vracanje_knjigeUBazu(id, idbm);
    }

    public ArrayList<Posudba> ispis_clanovaPosudili(int idbm){
        ArrayList<Posudba> popis = ps.proces_vracanja_izBaze(idbm);
        for(Posudba clan : popis){
            System.out.printf("%-30s %-20d %-30s %s\n", clan.naslov_knjige, clan.idbm_clana, clan.ime_clana, clan.prezime_clana);
        }
        System.out.println();

        return popis;
    }

    public int proces_vracanja_dioClan(){
        boolean ispravanUnos = false;
        int idbm = 0;
        while (!ispravanUnos) {
            System.out.print("Unesite idbm clana koji vraca knjigu: ");
            try {
                idbm = scanner.nextInt();
                scanner.nextLine();
                ispravanUnos = true;
            } catch (java.util.InputMismatchException e) {
                System.out.println("Pogrešan upis! Morate unijeti broj (idbm).");
                scanner.nextLine();
            }
        }
        return idbm;
    }

    public int proces_vracanja_dioKnjiga(ArrayList<Posudba> posClan){
        String naslov;
        System.out.print("Upišite naslov knjige koju član vraća: ");
        naslov = scanner.nextLine();
        boolean ispravno;

        ispravno = ps.provjeraUnosaKnjige(naslov, posClan);

        if(ispravno){
            int id = ps.knjiga(naslov);
            return id;
        }
        else{
            return 0;
        }
    }
}
