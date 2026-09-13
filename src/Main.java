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

        PosudbaRepository pr = new PosudbaRepository();
        PosudbaService ps = new PosudbaService(kr, pr, cr, cui);
        PosudbaUI pui = new PosudbaUI(kui, cui, ps);

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
                    izbor_fun(izbor, conn, kui, cr, cui, cs, pui, scanner);

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

            scanner.close();

    }
        public static void izbor_fun(int izbor, Connection conn, KnjigaUI kui, ClanoviRepository cr, ClanoviUI cui, ClanoviService cs, PosudbaUI pui, Scanner scanner){
            switch (izbor) {
                case 1 -> {
                    System.out.println("Ovdje ce se prikazati izbor knjiga...\n");
                    kui.ispis_knjiga();
                }
                case 2 -> {
                    System.out.println("Uskoro zapocinje proces unosenja knjiga...");
                    kui.unos_knjige();
                }
                case 3 -> {
                    System.out.println("Registracija clana...");
                    cui.registracija_clana();
                }
                case 4 -> {
                    System.out.println("Proces posudbe zapocnije...");
                    pui.ProcesPosudbe();
                }
                case 5 -> System.out.println("Proces vracanja knjige zapocinje...");
                case 6 -> {
                    System.out.println("Prikaz svih članova..." + "\n");
                    cui.ispis_clanova();
                }
                case 7 -> {
                    System.out.println("Doviđenja!");
                    break;
                }
                default -> System.out.println("Pogresan izbor");
            }
        }
    }


