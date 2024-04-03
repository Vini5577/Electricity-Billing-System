package electricity.billing.system;

import java.sql.*;

public class databaseExample {

    Connection connection;
    Statement statement;

    String database_URL = "";
    String user_db = "";
    String pass_db = "";

    databaseExample() {
        try {
            connection = DriverManager.getConnection(database_URL, user_db, pass_db);
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
