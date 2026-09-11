import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class KnjigaRepository {
    Connection conn;

    KnjigaRepository(Connection conn){
        this.conn = conn;
    }

    public ArrayList<Knjiga> popis_knjiga() {
        Statement statement = null;
        ResultSet rs = null;
        String naslov;
        String autor_ime;
        String autor_prezime;
        int godina_izdanja;
        int zaliha;
        ArrayList<Knjiga> popis = new ArrayList<>();
        Knjiga knj;

        try {
            String query = "SELECT knjige.naslov, autori.ime, autori.prezime, knjige.godina_izdanja, knjige.zaliha FROM knjige JOIN autori ON knjige.autor_id = autori.id WHERE knjige.zaliha > 0";
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
                System.out.println(e);
            }

        }
        return popis;
    }


    public void upis_knjige(String naslov, int id, int godina, int zaliha){
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

    public int provjera_autora(String ime_autora, String prezime_autora){
        int id = 0;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            String query = "INSERT INTO autori (ime, prezime) VALUES (?, ?) " + "ON CONFLICT (ime, prezime) DO UPDATE SET ime = autori.ime " + "RETURNING id";
            statement = conn.prepareStatement(query);
            statement.setString(1, ime_autora);
            statement.setString(2, prezime_autora);
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

        return id;

    }

}
