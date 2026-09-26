import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class ClanoviUI {
    ClanoviService clanoviService;

    ClanoviUI(ClanoviService clanoviService){
        this.clanoviService = clanoviService;
    }

    public void ispis_clanova(){
        String ime = "Ime";
        String prezime = "Prezime";
        String ime_oca = "Ime_oca";
        String datum = "Datum_clanstva";
        String idbm = "idbm";
        ArrayList<Clanovi> popis = clanoviService.dohvatiSveClanove();

        System.out.printf("%s %23s %23s %26s %12s\n", ime, prezime, ime_oca, datum, idbm);

        for(Clanovi clan : popis){
            System.out.printf("%-20s %-22s %-18s %-20s %s\n", clan.ime, clan.prezime, clan.ime_oca, clan.datum_clanstva, clan.idbm);
        }

    }

    public void registracija_clana(){
        String ime;
        String prezime;
        String ime_oca;
        int idbm;
        boolean done = false;
        Scanner scanner = new Scanner(System.in);

        while(!done) {
            System.out.print("Unesite ime clana: ");
            ime = scanner.nextLine();

            System.out.print("Unesite prezime clana: ");
            prezime = scanner.nextLine();

            System.out.print("Unesite ime oca clana: ");
            ime_oca = scanner.nextLine();

            try{
                clanoviService.obrada_clanovi(ime, prezime, ime_oca);
                done = true;
            }
            catch(IllegalArgumentException e){
                System.out.println("Ime i prezime ne smije sadrzavati broj!");
                continue;
            }

            System.out.print("Unesite idbm clana: ");
            idbm = scanner.nextInt();

            scanner.nextLine();

            LocalDate datum = LocalDate.now();
            clanoviService.unesi_podatke(ime, prezime, idbm, ime_oca, datum);
        }


    }
}
