import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class PosudbaRepository {
    Connection conn;

    PosudbaRepository(Connection conn){
        this.conn = conn;
    }

    public void proces_posudbe_ubazu(Knjiga kg, int idbm) {
        PreparedStatement stmtInsert = null;
        PreparedStatement stmtUpdate = null;
        PreparedStatement stmtUpdatecl = null;

        try{
            String queryInsert = "INSERT INTO posudbe (clan_idbm, knjiga_id, datum_posudbe) VALUES (?, ?, ?)";
            stmtInsert = conn.prepareStatement(queryInsert);
            stmtInsert.setInt(1, idbm);
            stmtInsert.setInt(2, kg.id);
            stmtInsert.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
            stmtInsert.executeUpdate();


            String queryUpdate = "UPDATE knjige SET zaliha = zaliha - 1 WHERE id = ?";
            stmtUpdate = conn.prepareStatement(queryUpdate);
            stmtUpdate.setInt(1, kg.id);
            stmtUpdate.executeUpdate();

            String queryUpdatecl = "UPDATE clanovi SET status_posudbe = true WHERE idbm = ?";
            stmtUpdatecl = conn.prepareStatement(queryUpdatecl);
            stmtUpdatecl.setInt(1, idbm);
            stmtUpdatecl.executeUpdate();

            System.out.println("Baza uspjesno azurirana!");

        }
        catch(Exception e){
            System.out.println(e);
        }
        finally{
            if(stmtUpdatecl != null && stmtInsert != null && stmtUpdate != null){
                try {
                    stmtUpdatecl.close();
                    stmtInsert.close();
                    stmtUpdate.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
        }

    }

    public ArrayList<Posudba> proces_vracanja_izBaze_popis(int idbm_koris){
        PreparedStatement statement = null;
        ResultSet rs = null;
        String naslov;
        int idbm;
        String ime_clana;
        String prezime_clana;
        ArrayList<Posudba> popis_posudenog = new ArrayList<>();

        try {
            String querry = "SELECT knjige.naslov, clan_idbm, clanovi.ime, clanovi.prezime FROM posudbe JOIN knjige ON posudbe.knjiga_id = knjige.id JOIN clanovi ON posudbe.clan_idbm = clanovi.idbm WHERE clan_idbm = ? AND posudbe.vraceno = false";
            statement = conn.prepareStatement(querry);
            statement.setInt(1, idbm_koris);
            rs = statement.executeQuery();
            Posudba posudba;

            while (rs.next()){
                naslov = rs.getString("naslov");
                idbm = rs.getInt("clan_idbm");
                ime_clana = rs.getString("ime");
                prezime_clana = rs.getString("prezime");


                posudba = new Posudba(naslov, idbm, ime_clana, prezime_clana);
                popis_posudenog.add(posudba);
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        finally{
            if(statement != null){
                try {
                    statement.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
                if(rs != null){
                    try{
                        rs.close();
                    }
                    catch(Exception e){
                        System.out.println(e);
                    }
                }
            }

        }

        return popis_posudenog;
    }

    public void proces_vracanja_uBazu(int id, int idbm){
        PreparedStatement statknj = null;
        PreparedStatement statpos = null;

        try{
            String querry1 = "UPDATE posudbe SET vraceno = true WHERE knjiga_id = ? AND clan_idbm = ? AND vraceno = false";
            statpos = conn.prepareStatement(querry1);
            statpos.setInt(1, id);
            statpos.setInt(2, idbm);
            statpos.executeUpdate();

            String querry2 = "UPDATE knjige SET zaliha = zaliha + 1 WHERE id = ?";
            statknj = conn.prepareStatement(querry2);
            statknj.setInt(1, id);
            statknj.executeUpdate();

            System.out.println("Knjiga uspješno vraćena! Zaliha ažurirana.");

        }
        catch(Exception e){
            System.out.println(e);
        }
        finally {
            try {
                if (statpos != null) statpos.close();
            } catch (Exception e) {
                System.out.println(e);
            }
            try {
                if (statknj != null) statknj.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }


    }

}
