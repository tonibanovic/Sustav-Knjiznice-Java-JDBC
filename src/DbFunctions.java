import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;

public class DbFunctions {
    public Connection connect_to_db(String dbname, String user, String pass) {
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbname, user, pass);
        } catch (Exception e) {
            System.out.println(e);
        }

        return conn;

    }


}
