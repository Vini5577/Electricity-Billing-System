package electricity.billing.system;

import java.sql.*;

public class databaseExample {
    
    Connection connection;
    Statement statement;

    databaseExample() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bill_system", "root", "123456987");
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
