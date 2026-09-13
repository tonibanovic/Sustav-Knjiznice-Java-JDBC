import java.util.ArrayList;
import java.util.Scanner;

public class KnjigaUI {
    Scanner scanner = new Scanner(System.in);
    KnjigaService service;

    KnjigaUI(KnjigaService service){
        this.service = service;
    }

    public void ispis_knjiga(){
        String naslov = "Naslov";
        String ime = "Ime";
        String prezime = "Prezime";
        String godina = "Godina izdanja";
        String zaliha = "Zaliha";
        int i = 0;
        ArrayList<Knjiga> popis = service.dohvatiSveKnjige();

        System.out.printf("%11s %37s %27s %32s %12s\n", naslov, ime, prezime, godina, zaliha);

        for(Knjiga knjige: popis){
            ++i;
            System.out.printf("%-4d %-40s %-25s %-25s %-20d %-20d\n", i, knjige.naslov, knjige.autor_ime, knjige.autor_prezime, knjige.godina_izdanja, knjige.zaliha);
        }

    }
    public void unos_knjige(){
        String ime;
        String prezime;
        String naslov;
        int godina;
        int zaliha;

        System.out.println("Unesite ime i prezime autora koji je napisao knjigu: ");
        System.out.print("Ime: ");
        ime = scanner.nextLine();
        System.out.print("Prezime: ");
        prezime = scanner.nextLine();

        System.out.print("Unesite naslov knjige: ");
        naslov = scanner.nextLine();

        System.out.print("Unesite godinu izdanja: ");
        godina = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Unesite broj zaliha za knjigu: ");
        zaliha = scanner.nextInt();
        scanner.nextLine();

        service.obradiIUnesiKnigu(naslov, ime, prezime, godina, zaliha);

    }


}
