package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private String path =  "jdbc:postgresql://lallah.db.elephantsql.com:5432/ekukigmn";
    private String username = "ekukigmn";
    private String password = "Gjg68sTYqR_Acd9mu7WL5eIEiL0yYLkm";


    private Connection connection = null;
    private Connection connect() throws SQLException {
        return DriverManager.getConnection(path, username, password);
    }
    public Connection connection() {
        if (connection == null) {
            try {
                connection = connect();
            } catch (SQLException e) {
                throw new IllegalStateException();
            }
        }

        return this.connection;
    }
}
