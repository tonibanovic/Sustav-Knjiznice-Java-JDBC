import java.sql.Connection;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main (String[] args) {
        Scanner scanner = new Scanner(System.in);
        int izbor = 0;

        try {
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

                izbor = scanner.nextInt();
                scanner.nextLine();
                izbor_fun(izbor);

            }

        }
        catch(InputMismatchException e){
            System.out.println("Pogrešan upis!");
            scanner.nextLine();
            izbor = 0;
        }
    }
        public static void izbor_fun(int izbor){
            DbFunctions db = new DbFunctions();
            Connection conn = db.connect_to_db("postgres", "postgres", "Toni");
            ArrayList<Knjiga> popis_knjiga;
            ArrayList<Clanovi> popis_clanova;

            switch (izbor) {
                case 1 -> {
                    System.out.println("Ovdje ce se prikazati izbor knjiga...\n");
                    popis_knjiga = db.popis_knjiga(conn);
                    db.ispis_knjiga(popis_knjiga);
                }
                case 2 -> {
                    System.out.println("Uskoro apocinje proces unosenja knjiga...");
                    db.unos_knjige(conn);
                }
                case 3 -> System.out.println("Registracija clana...");
                case 4 -> System.out.println("Proces posudbe zapocnije...");
                case 5 -> System.out.println("Proces vracanja knjige zapocinje...");
                case 6 -> {
                    System.out.println("Prikaz svih članova..." + "\n");
                    popis_clanova = db.prikaz_clanova(conn);
                    db.ispis_clanova(popis_clanova);
                }

                case 7 -> {
                    System.out.println("Doviđenja!");
                    break;
                }
                default -> System.out.println("Pogresan izbor");
            }
        }
    }


