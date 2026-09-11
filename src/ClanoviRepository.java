import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class ClanoviRepository {
    Connection conn;
    ClanoviRepository(Connection conn){
        this.conn = conn;
    }

    public ArrayList<Clanovi> prikaz_clanova(){
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

    public void unos_clana(String ime, String prezime, int idbm, String ime_oca, LocalDate datum){
        PreparedStatement statement = null;

        try{
            String query = "INSERT INTO clanovi (ime, prezime, datum_clanstva, idbm, ime_oca) VALUES (?, ?, ?, ?, ?)";
            statement = conn.prepareStatement(query);
            statement.setString(1, ime);
            statement.setString(2, prezime);
            statement.setDate(3, Date.valueOf(datum));
            statement.setInt(4, idbm);
            statement.setString(5, ime_oca);
            statement.executeUpdate();

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
