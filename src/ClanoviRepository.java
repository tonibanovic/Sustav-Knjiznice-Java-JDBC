import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class ClanoviRepository {

    public ArrayList<Clanovi> prikaz_clanova(Connection conn){
        String ime;
        String prezime;
        java.sql.Date datum;
        int idbm;
        String ime_oca;
        PreparedStatement statement = null;
        ResultSet rs = null;
        Clanovi clan;
        ArrayList<Clanovi> popis = new ArrayList<>();

        try{
            String query = "SELECT ime, prezime, datum_clanstva, idbm, ime_oca FROM clanovi";
            statement = conn.prepareStatement(query);
            rs = statement.executeQuery();


            while(rs.next()){
                ime = rs.getString("ime");
                prezime = rs.getString("prezime");
                datum = rs.getDate("datum_clanstva");
                String date = String.format("%1$td-%1$tm-%1$tY", datum);
                idbm = rs.getInt("idbm");
                ime_oca = rs.getString("ime_oca");

                clan = new Clanovi(ime, prezime, date, idbm, ime_oca);
                popis.add(clan);
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        finally{
            if(statement != null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println(e);
                }
            }
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println(e);
                }
            }
        }

        return popis;

    }

    public void ispis_clanova(ArrayList<Clanovi> popis){
        String ime = "Ime";
        String prezime = "Prezime";
        String ime_oca = "Ime_oca";
        String datum = "Datum_clanstva";
        String idbm = "idbm";

        System.out.printf("%s %23s %23s %26s %12s\n", ime, prezime, ime_oca, datum, idbm);

        for(Clanovi clan : popis){
            System.out.printf("%-20s %-22s %-18s %-20s %s\n", clan.ime, clan.prezime, clan.ime_oca, clan.datum_clanstva, clan.idbm);
        }

    }

    public void registracija_clana(Connection conn){
        String ime;
        String prezime;
        String ime_oca;
        int idbm;
        Scanner scanner = new Scanner(System.in);

        System.out.print("Unesite ime clana: ");
        ime = scanner.nextLine();

        System.out.print("Unesite prezime clana: ");
        prezime = scanner.nextLine();

        System.out.print("Unesite ime oca clana: ");
        ime_oca = scanner.nextLine();

        System.out.print("Unesite idbm clana: ");
        idbm = scanner.nextInt();

        scanner.nextLine();

        LocalDate datum = LocalDate.now();

        unos_clana(ime, prezime, idbm, ime_oca, datum, conn);

    }

    public void unos_clana(String ime, String prezime, int idbm, String ime_oca, LocalDate datum, Connection conn){
        PreparedStatement statement = null;

        try{
            String query = "INSERT INTO clanovi (ime, prezime, datum_clanstva, idbm, ime_oca) VALUES (?, ?, ?, ?, ?)";
            statement = conn.prepareStatement(query);
            statement.setString(1, ime);
            statement.setString(2, prezime);
            statement.setDate(3, Date.valueOf(datum));
            statement.setInt(4, idbm);
            statement.setString(5, ime_oca);
            statement.executeQuery();

        }
        catch(Exception e){
            System.out.println(e);
        }
        finally{
            try{
                if(statement != null){
                    statement.close();
                }
            }
            catch(Exception e){
                System.out.println(e);
            }
        }


    }

}
