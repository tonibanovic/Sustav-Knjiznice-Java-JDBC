import java.sql.Connection;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {


    public static void main (String[] args) {
        Scanner scanner = new Scanner(System.in);

        DbFunctions db = new DbFunctions();
        Connection conn = db.connect_to_db("postgres", "postgres", "Toni");

        KnjigaRepository kr = new KnjigaRepository(conn);
        KnjigaService ks = new KnjigaService(kr);
        KnjigaUI kui = new KnjigaUI(ks);

        ClanoviRepository cr = new ClanoviRepository(conn);
        ClanoviService cs = new ClanoviService(cr);
        ClanoviUI cui = new ClanoviUI(cs);

        int izbor = 0;
            while (izbor != 7) {
                System.out.println("\n--- SUSTAV KNJIZNICE ---");
                System.out.println("1. Pregled knjiga");
                System.out.println("2. Unesi novu knjigu");
                System.out.println("3. Registiraj člana");
                System.out.println("4. Posudi knjigu");
                System.out.println("5. Vrati knjigu");
                System.out.println("6. Prikaži registrirane članove");
                System.out.println("7. Izlaz");
                System.out.print("Izaberi opciju: ");
                try {
                    izbor = scanner.nextInt();
                    scanner.nextLine();
                    izbor_fun(izbor, conn, kr, kui, ks, cr, cui, cs);

                } catch (InputMismatchException e) {
                    System.out.println("Pogrešan upis!");
                    scanner.nextLine();
                    izbor = 0;
                }
            }

            try{
                if(conn != null){
                    conn.close();
                }
            }
            catch(Exception e){
                System.out.println(e);
            }

    }
        public static void izbor_fun(int izbor, Connection conn, KnjigaRepository kr, KnjigaUI kui, KnjigaService ks, ClanoviRepository cr, ClanoviUI cui, ClanoviService cs){
            switch (izbor) {
                case 1 -> {
                    System.out.println("Ovdje ce se prikazati izbor knjiga...\n");
                    ArrayList<Knjiga> popis_knjiga = null;
                    popis_knjiga = ks.dohvatiSveKnjige();
                    kui.ispis_knjiga(popis_knjiga);
                }
                case 2 -> {
                    System.out.println("Uskoro zapocinje proces unosenja knjiga...");
                    kui.unos_knjige(conn);
                }
                case 3 -> {
                    System.out.println("Registracija clana...");
                    cui.registracija_clana();
                }
                case 4 -> System.out.println("Proces posudbe zapocnije...");
                case 5 -> System.out.println("Proces vracanja knjige zapocinje...");
                case 6 -> {
                    System.out.println("Prikaz svih članova..." + "\n");
                    ArrayList<Clanovi> popis_clanova = null;
                    popis_clanova = cs.dohvatiSveClanove();
                    cui.ispis_clanova(popis_clanova);
                }

                case 7 -> {
                    System.out.println("Doviđenja!");
                    break;
                }
                default -> System.out.println("Pogresan izbor");
            }
        }
    }


