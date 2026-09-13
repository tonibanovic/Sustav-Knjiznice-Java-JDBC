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

    }

    public Knjiga proces_posudbe_dioKnjiga(){
        int opcija;
        Knjiga kg;

        System.out.println();
        System.out.println("Dostupne knjige su: ");

        ku.ispis_knjiga();

        System.out.println();
        System.out.print("Odaberite redni broj knjige koju clan zeli: ");

        opcija  = scanner.nextInt();
        scanner.nextLine();

        kg = ps.provjera_zalihe_i_dohvat(opcija);

        if(kg != null){
            System.out.println("Odabrana knjiga i njena zaliha:  " + kg.naslov + " | " + kg.zaliha);
        }
        else{
            System.out.println("Greška! Odabrna knjiga nema zaliha ili je pogrešan odabir!");
        }
        return kg;

    }

    public int proces_posudbe_dioClan(){
        int reg = 0;
        while (reg != 1 && reg != 2) {
            System.out.print("Je li clan koji zeli posuditi knjigu registriran (da = 1 / ne = 2): ");
            reg = scanner.nextInt();
            scanner.nextLine();

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
        int idbm = scanner.nextInt();
        scanner.nextLine();

        return idbm;


    }

}
