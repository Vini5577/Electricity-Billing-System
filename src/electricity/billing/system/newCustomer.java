package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.util.Random;

public class newCustomer extends JFrame implements ActionListener {

    JLabel heading, meternumText, customerName, meterNum, address, city, state, email, phone;
    JButton next, cancel;
    TextField nameText, addressText, cityText, stateText, emailText, phoneText;

    newCustomer() {
        super("New Customer");
        setSize(700, 500);
        setLocation(400, 200);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(252, 186, 3));
        add(panel);

        heading = new JLabel("New Customer");
        heading.setBounds(180, 10, 200, 20);
        heading.setFont(new Font("Tahoma", Font.BOLD, 20));
        panel.add(heading);

        customerName = new JLabel("New Customer");
        customerName.setBounds(50, 80, 140, 20);
        panel.add(customerName);

        nameText = new TextField();
        nameText.setBounds(195, 80, 150, 20);
        panel.add(nameText);

        meterNum = new JLabel("Meter Number");
        meterNum.setBounds(50, 120, 140, 20);
        panel.add(meterNum);

        meternumText = new JLabel("");
        meternumText.setBounds(195, 120, 150, 20);
        panel.add(meternumText);

        Random ran = new Random();
        long number = ran.nextLong() % 100000;
        meternumText.setText(" " + Math.abs(number));

        address = new JLabel("Address ");
        address.setBounds(50, 160, 140, 20);
        panel.add(address);

        addressText = new TextField();
        addressText.setBounds(195, 160, 150, 20);
        panel.add(addressText);

        city = new JLabel("City ");
        city.setBounds(50, 200, 140, 20);
        panel.add(city);

        cityText = new TextField();
        cityText.setBounds(195, 200, 150, 20);
        panel.add(cityText);

        state = new JLabel("State ");
        state.setBounds(50, 240, 140, 20);
        panel.add(state);

        stateText = new TextField();
        stateText.setBounds(195, 240, 150, 20);
        panel.add(stateText);

        email = new JLabel("Email ");
        email.setBounds(50, 280, 140, 20);
        panel.add(email);

        emailText = new TextField();
        emailText.setBounds(195, 280, 150, 20);
        panel.add(emailText);

        phone = new JLabel("Phone ");
        phone.setBounds(50, 320, 140, 20);
        panel.add(phone);

        phoneText = new TextField();
        phoneText.setBounds(195, 320, 150, 20);
        panel.add(phoneText);

        next = new JButton("Next");
        next.setBounds(120, 390, 100, 25);
        next.setBackground(Color.BLACK);
        next.setForeground(Color.WHITE);
        next.addActionListener(this);
        panel.add(next);

        cancel = new JButton("Cancel");
        cancel.setBounds(230, 390, 100, 25);
        cancel.setBackground(Color.BLACK);
        cancel.setForeground(Color.white);
        cancel.addActionListener(this);
        panel.add(cancel);

        setLayout(new BorderLayout());
        add(panel, "Center");

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/boy.png"));
        Image i2 = i1.getImage().getScaledInstance(230, 200, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel imgLable = new JLabel(i3);
        add(imgLable, "West");
        setVisible(true);
    }

    public boolean isNumeric(String str) {
        return str != null && str.matches("-?\\d+(\\.\\d+)?");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == next) {
            String sname = nameText.getText();
            String smeter = meternumText.getText().trim();
            String saddreess = addressText.getText();
            String scity = cityText.getText();
            String sstate = stateText.getText();
            String eemail = emailText.getText();
            String sphone = phoneText.getText();

            String query_customer = "insert into new_customer values( ?, ?, ?, ?, ?, ?, ?)";

            String query_signup = "insert into Signup values(?, '', ?, '','')";
            if(!isNumeric(sphone)) {
                JOptionPane.showMessageDialog(null, "phone is only numbers");
                return;
            }
            try {
                database c = new database();
                PreparedStatement co = c.connection.prepareStatement(query_customer);
                co.setString(1, sname);
                co.setString(2, smeter);
                co.setString(3, saddreess);
                co.setString(4, scity);
                co.setString(5, sstate);
                co.setString(6, eemail);
                co.setString(7, sphone);
                co.executeUpdate();

                co = c.connection.prepareStatement(query_signup);
                co.setString(1, smeter);
                co.setString(2, sname);
                co.executeUpdate();

                JOptionPane.showMessageDialog(null, "Customer Details added successfully");
                setVisible(false);
                new meterInfo(smeter);
            } catch (Exception E) {
                E.printStackTrace();
            }
        } else {
            setVisible(false);
        }

    }

    // public static void main(String[] args) {
    // new newCustomer();
    // }
}
