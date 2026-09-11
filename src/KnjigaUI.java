import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

public class KnjigaUI {
    Scanner scanner = new Scanner(System.in);
    KnjigaService service;

    KnjigaUI(KnjigaService service){
        this.service = service;
    }

    public void ispis_knjiga(ArrayList<Knjiga> popis){
        String naslov = "Naslov";
        String ime = "Ime";
        String prezime = "Prezime";
        String godina = "Godina izdanja";
        String zaliha = "Zaliha";

        System.out.println(String.format("%11s %32s %32s %30s %19s", naslov, ime, prezime, godina, zaliha));
        for(Knjiga knjige: popis){
            System.out.printf("%-40s %-30s %-25s %-25d %-25d\n", knjige.naslov, knjige.autor_ime, knjige.autor_prezime, knjige.godina_izdanja, knjige.zaliha);
        }

    }

    public void unos_knjige(Connection conn){
        String ime;
        String prezime;
        String naslov;
        int godina;
        int zaliha;
        int id = 0;

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
