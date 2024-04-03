package electricity.billing.system;


import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class bill_details extends JFrame {

    String meter;

    bill_details(String meter) {
        this.meter = meter;
        setSize(700, 650);
        setLocation(400, 150);
        setLayout(null);
        getContentPane().setBackground(Color.white);

        JTable table = new JTable();

        try {
            database c = new database();
            String query_bill = "select * from bill where meter_no = ?";
            PreparedStatement co = c.connection.prepareStatement(query_bill);
            co.setString(1, meter);
            ResultSet resultSet = co.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(resultSet));

        }catch (Exception e) {
            e.printStackTrace();
        }

        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(0, 0, 700, 650);
        add(sp);

        setVisible(true);
    }

    /*public static void main(String[] args) {
        new bill_details("");
    }*/
}
