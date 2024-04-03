package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class pay_bill extends JFrame implements ActionListener {

    Choice searchmonthCho;
    String meter;

    JButton pay, back;

    pay_bill(String meter) {
        this.meter = meter;

        setSize(900, 600);
        setLocation(300, 150);
        setLayout(null);

        JLabel heading = new JLabel("Pay Bill");
        heading.setFont(new Font("Tahoma", Font.BOLD, 24));
        heading.setBounds(120, 5, 400, 30);
        add(heading);

        JLabel meterNumber = new JLabel("Meter Number");
        meterNumber.setBounds(35, 80, 200, 20);
        add(meterNumber);

        JLabel meterNumberText = new JLabel("");
        meterNumberText.setBounds(300, 80, 200, 20);
        add(meterNumberText);

        JLabel name = new JLabel("Name");
        name.setBounds(35, 140, 200, 20);
        add(name);

        JLabel nameText = new JLabel("");
        nameText.setBounds(300, 140, 200, 20);
        add(nameText);

        JLabel month = new JLabel("Month");
        month.setBounds(35, 200, 200, 20);
        add(month);

        searchmonthCho = new Choice();
        searchmonthCho.add("January");
        searchmonthCho.add("February");
        searchmonthCho.add("March");
        searchmonthCho.add("April");
        searchmonthCho.add("May");
        searchmonthCho.add("June");
        searchmonthCho.add("July");
        searchmonthCho.add("Auhust");
        searchmonthCho.add("September");
        searchmonthCho.add("October");
        searchmonthCho.add("November");
        searchmonthCho.add("December");
        searchmonthCho.setBounds(300, 200, 150, 20);
        add(searchmonthCho);

        JLabel unit = new JLabel("Unit");
        unit.setBounds(35, 260, 200, 20);
        add(unit);

        JLabel unitText = new JLabel("");
        unitText.setBounds(300, 260, 200, 20);
        add(unitText);

        JLabel totalBill = new JLabel("Total Bill");
        totalBill.setBounds(35, 320, 200, 20);
        add(totalBill);

        JLabel totalBillText = new JLabel("");
        totalBillText.setBounds(300, 320, 200, 20);
        add(totalBillText);

        JLabel status = new JLabel("Status");
        status.setBounds(35, 380, 200, 20);
        add(status);

        JLabel statusText = new JLabel("");
        statusText.setBounds(300, 380, 200, 20);
        add(statusText);

        try {
            database c = new database();
            ResultSet resultSet =c.statement.executeQuery("select * from new_customer where meter_no ='"+meter+"' ");
            while(resultSet.next()) {
                meterNumberText.setText(meter);
                nameText.setText(resultSet.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        try{
            database c = new database();
            String query = "select * from bill where meter_no = ? and month = ?";
            PreparedStatement co = c.connection.prepareStatement(query);
            co.setString(1, meter);
            co.setString(2, searchmonthCho.getSelectedItem());
            co.executeQuery();
            ResultSet resultSet = co.executeQuery();
            while(resultSet.next()) {
                unitText.setText(resultSet.getString("unit"));
                totalBillText.setText(resultSet.getString("total_bill"));
                statusText.setText(resultSet.getString("status"));
                if(statusText.getText().equalsIgnoreCase("Paid")) {
                    statusText.setForeground(Color.green);
                } else {
                    statusText.setForeground(Color.red);
                }
            }
        } catch (Exception E) {
            E.printStackTrace();
        }


        searchmonthCho.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                try{
                    database c = new database();
                    String query = "select * from bill where meter_no = ? and month = ?";
                    PreparedStatement co = c.connection.prepareStatement(query);
                    co.setString(1, meter);
                    co.setString(2, searchmonthCho.getSelectedItem());
                    co.executeQuery();
                    ResultSet resultSet = co.executeQuery();
                    boolean found = false;

                    while (resultSet.next()) {
                        found = true;
                        unitText.setText(resultSet.getString("unit"));
                        totalBillText.setText(resultSet.getString("total_bill"));
                        statusText.setText(resultSet.getString("status"));
                        if (statusText.getText().equalsIgnoreCase("Paid")) {
                            statusText.setForeground(Color.green);
                        } else {
                            statusText.setForeground(Color.red);
                        }
                    }


                    if(!found) {
                        unitText.setText("");
                        totalBillText.setText("");
                        statusText.setText("");
                        statusText.setForeground(Color.red);
                        statusText.setText("Not Paid!");
                    }

                } catch (Exception E) {
                    E.printStackTrace();
                }
            }
        });

        pay = new JButton("Pay");
        pay.setBackground(Color.black);
        pay.setForeground(Color.white);
        pay.setBounds(100, 460, 100, 25);
        pay.addActionListener(this);
        add(pay);

        back = new JButton("Back");
        back.setBackground(Color.black);
        back.setForeground(Color.white);
        back.setBounds(230, 460, 100, 25);
        back.addActionListener(this);
        add(back);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == pay) {
            try {
                database c = new database();
                String query = "update bill set status = 'Paid' where meter_no = ? and month = ?";
                PreparedStatement co = c.connection.prepareStatement(query);
                co.setString(1, meter);
                co.setString(2, searchmonthCho.getSelectedItem());
                co.executeUpdate();
            } catch (Exception E) {
                E.printStackTrace();
            }

            setVisible(false);
            new payment_bill(meter);
        } else {
            setVisible(false);
        }
    }


}
