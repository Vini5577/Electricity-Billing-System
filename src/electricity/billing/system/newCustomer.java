package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class newCustomer extends JFrame {

    JLabel heading, customerName, meterNum, address;
    TextField nameText, meternumText, addresText;

    newCustomer() {
        super("New Customer");

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(252, 186, 3));
        add(panel);

        heading = new JLabel("New Customer");
        heading.setBounds(180, 10, 200, 20);
        heading.setFont(new Font("Tahoma", Font.BOLD, 20));
        panel.add(heading);

        customerName = new JLabel("New Customer");
        customerName.setBounds(50, 80, 150, 20);
        panel.add(customerName);

        nameText = new TextField();
        nameText.setBounds(180, 80, 150, 20);
        panel.add(nameText);

        meterNum = new JLabel("Meter Number");
        meterNum.setBounds(50, 120, 150, 20);
        panel.add(meterNum);

        meternumText = new TextField("");
        meternumText.setBounds(180, 120, 150, 20);
        panel.add(meternumText);

        Random ran = new Random();
        long number = ran.nextLong() % 100000;
        meternumText.setText(" " + Math.abs(number));

        setSize(700, 500);
        setLocation(400, 200);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new newCustomer();
    }
}
