package electricity.billing.system;

import java.sql.*;

public class databaseExample {

    Connection connection;
    Statement statement;

    databaseExample() {
        try {
            // connection = DriverManager.getConnection("jdbc:mysql://URL_DATABASE",//
            // USER_DB,
            // PASSWORD_DB);
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
