import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DbFunctions {
    public Connection connect_to_db(String dbname, String user, String pass) {
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbname, user, pass);
            if (conn != null) {
                System.out.println("Connection Established");
            } else {
                System.out.println("Connection Failed");
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return conn;

    }

    public ArrayList<Knjiga> popis_knjiga(Connection conn) {
        Statement statement = null;
        ResultSet rs = null;
        String naslov;
        String autor_ime;
        String autor_prezime;
        int godina_izdanja;
        int zaliha;
        ArrayList<Knjiga> popis = new ArrayList<Knjiga>();
        Knjiga knj;

        try {
            String query = String.format("SELECT knjige.naslov, autori.ime, autori.prezime, knjige.godina_izdanja, knjige.zaliha FROM knjige JOIN autori ON knjige.autor_id = autori.id WHERE knjige.zaliha > 0;");
            statement = conn.createStatement();
            rs = statement.executeQuery(query);

            while (rs.next()) {
                naslov = rs.getString("naslov");
                autor_ime = rs.getString("ime");
                autor_prezime = rs.getString("prezime");
                godina_izdanja = rs.getInt("godina_izdanja");
                zaliha = rs.getInt("zaliha");

                knj = new Knjiga(naslov, autor_ime, autor_prezime, godina_izdanja, zaliha);
                popis.add(knj);

            }

        } catch (Exception e) {
            System.out.println(e);
        }
        finally{
            try {
                if(rs != null){
                    rs.close();}

                if(statement != null){
                    statement.close();}
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        return popis;
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
        PreparedStatement statement = null;
        ResultSet rs = null;
        Scanner scanner = new Scanner(System.in);
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

        try {
            String query = "INSERT INTO autori (ime, prezime) VALUES (?, ?) " + "ON CONFLICT (ime, prezime) DO UPDATE SET ime = autori.ime " + "RETURNING id";
            statement = conn.prepareStatement(query);
            statement.setString(1, ime);
            statement.setString(2, prezime);
            rs = statement.executeQuery();

            if (rs.next()) {
                id = rs.getInt("id");
            }
        }catch (Exception e) {
                System.out.println(e);
        }
        finally {
            try {
                if (statement != null) {
                    statement.close();
                }

                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        System.out.print("Unesite naslov knjige: ");
        naslov = scanner.nextLine();

        System.out.print("Unesite godinu izdanja: ");
        godina = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Unesite broj zaliha za knjigu: ");
        zaliha = scanner.nextInt();
        scanner.nextLine();

        upis_knjige(conn, naslov, id, godina, zaliha);

    }

    public void upis_knjige(Connection conn, String naslov, int id, int godina, int zaliha){
        PreparedStatement statement = null;
        try {
            String query = "INSERT INTO knjige (naslov, autor_id, godina_izdanja, zaliha) VALUES (?, ?, ?, ?) " + "ON CONFLICT (naslov, autor_id) " + "DO UPDATE SET zaliha = knjige.zaliha + EXCLUDED.zaliha";
            statement = conn.prepareStatement(query);
            statement.setString(1, naslov);
            statement.setInt(2, id);
            statement.setInt(3, godina);
            statement.setInt(4, zaliha);
            statement.executeUpdate();

        }
        catch (SQLException e) {
            System.out.println(e);
        }
        finally{
            try {
                if (statement != null) {
                    statement.close();
                }
            }
            catch(Exception e){
                System.out.println(e);
            }
        }

    }

    public ArrayList<Clanovi> prikaz_clanova(Connection conn){
        String ime;
        String prezime;
        java.sql.Date datum;
        int idbm;
        PreparedStatement statement = null;
        ResultSet rs = null;
        Clanovi clan;
        ArrayList<Clanovi> popis = new ArrayList<>();

        try{
            String query = "SELECT ime, prezime, datum_clanstva, idbm FROM clanovi";
            statement = conn.prepareStatement(query);
            rs = statement.executeQuery();


            while(rs.next()){
                ime = rs.getString("ime");
                prezime = rs.getString("prezime");
                datum = rs.getDate("datum_clanstva");
                String date = String.format("%1$td-%1$tm-%1$tY", datum);
                idbm = rs.getInt("idbm");

                clan = new Clanovi(ime, prezime, date, idbm);
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
                    System.out.println(e);;
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
        String datum = "Datum_clanstva";
        String idbm = "idbm";

        System.out.printf("%s %23s %26s %12s\n", ime, prezime, datum, idbm);

        for(Clanovi clan : popis){
            System.out.printf("%-20s %-20s %-20s %s\n", clan.ime, clan.prezime, clan.datum_clanstva, clan.idbm);
        }

    }

}
