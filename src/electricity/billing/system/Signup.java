package electricity.billing.system;

import at.favre.lib.crypto.bcrypt.BCrypt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Signup extends JFrame implements ActionListener {
    Choice loginASCho;
    TextField meterText, EmployerText, userNameText, nameText;
    JPasswordField passwordText;
    JButton create, back;

    Signup() {
        super("Signup Page");
        getContentPane().setBackground(new Color(168, 203, 255));

        JLabel createAs = new JLabel("Create Account As");
        createAs.setBounds(30, 50, 125, 20);
        add(createAs);

        loginASCho = new Choice();
        loginASCho.add("Admin");
        loginASCho.add("Customer");
        loginASCho.setBounds(170, 50, 120, 20);
        add(loginASCho);

        JLabel meterNo = new JLabel("Meter Number");
        meterNo.setBounds(30, 100, 125, 20);
        meterNo.setVisible(false);
        add(meterNo);

        meterText = new TextField();
        meterText.setBounds(170, 100, 125, 20);
        meterText.setVisible(false);
        add(meterText);

        JLabel Employer = new JLabel("Employer ID");
        Employer.setBounds(30, 100, 125, 20);
        Employer.setVisible(true);
        add(Employer);

        EmployerText = new TextField();
        EmployerText.setBounds(170, 100, 125, 20);
        EmployerText.setVisible(true);
        add(EmployerText);

        JLabel userName = new JLabel("UserName");
        userName.setBounds(30, 140, 125, 20);
        add(userName);

        userNameText = new TextField();
        userNameText.setBounds(170, 140, 125, 20);
        add(userNameText);

        JLabel name = new JLabel("Name");
        name.setBounds(30, 180, 125, 20);
        add(name);

        nameText = new TextField("");
        nameText.setBounds(170, 180, 125, 20);
        add(nameText);

        meterText.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                try{
                    database c = new database();
                    String query = "select * from signup where meter_no = ? and usertype = ''";
                    PreparedStatement co = c.connection.prepareStatement(query);
                    co.setString(1, meterText.getText());
                    ResultSet resultSet = co.executeQuery();
                    if (resultSet.next()){
                        nameText.setText(resultSet.getString("name"));
                    }

                }catch (Exception E){
                    E.printStackTrace();
                }
            }
        });


        JLabel password = new JLabel("Password");
        password.setBounds(30, 220, 125, 20);
        add(password);

        passwordText = new JPasswordField();
        passwordText.setBounds(170, 220, 125, 20);
        add(passwordText);

        loginASCho.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String user = loginASCho.getSelectedItem();
                if (user.equals("Customer")) {
                    Employer.setVisible(false);
                    EmployerText.setVisible(false);
                    nameText.setEditable(false);
                    meterNo.setVisible(true);
                    meterText.setVisible(true);
                } else {
                    nameText.setEditable(true);
                    Employer.setVisible(true);
                    EmployerText.setVisible(true);
                    meterNo.setVisible(false);
                    meterText.setVisible(false);
                }
            }
        });

        create = new JButton("Create");
        create.setBackground(new Color(66, 127, 219));
        create.setForeground(Color.WHITE);
        create.setBounds(50, 280, 100, 25);
        create.addActionListener(this);
        add(create);

        back = new JButton("Back");
        back.setBackground(new Color(66, 127, 219));
        back.setForeground(Color.WHITE);
        back.setBounds(180, 280, 100, 25);
        back.addActionListener(this);
        add(back);

        ImageIcon boyIcon = new ImageIcon(ClassLoader.getSystemResource("icon/boy.png"));
        Image boyImage = boyIcon.getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT);
        ImageIcon boyIcon2 = new ImageIcon(boyImage);
        JLabel boyLabel = new JLabel(boyIcon2);
        boyLabel.setBounds(320, 30, 250, 250);
        add(boyLabel);

        setSize(600, 380);
        setLocation(500, 200);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public boolean isNumeric(String str) {
        return str != null && str.matches("-?\\d+(\\.\\d+)?");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == create) {
            String sloginAs = loginASCho.getSelectedItem();
            String susername = userNameText.getText();
            String sname = nameText.getText();
            String spassword = String.valueOf(passwordText.getPassword());
            String hashPasword = BCrypt.withDefaults().hashToString(12, spassword.toCharArray());
            String smeter = meterText.getText();

            try {
                database c = new database();
                PreparedStatement co;
                String query = null;
                if(sname.equals("") || susername.equals("") || smeter.equals("")) {
                    JOptionPane.showMessageDialog(null, "All fields cannot be empty!!");
                } else if(spassword.length() < 6 || spassword.length() > 10){
                    JOptionPane.showMessageDialog(null, "Password must be at least 6 characters and less than 12 characters");
                } else if(!isNumeric(smeter)) {
                    JOptionPane.showMessageDialog(null, "meter is only numbers");
                } else {
                    if (sloginAs.equals("Admin")) {
                        query = "insert into Signup value(?, ?, ?, ?, ?, ?)";
                        co = c.connection.prepareStatement(query);
                        co.setString(1, smeter);
                        co.setString(2, susername);
                        co.setString(3, sname);
                        co.setString(4, hashPasword);
                        co.setString(5, sloginAs);
                        co.executeUpdate();
                    } else {
                            query = "update Signup set username = ?, password = ?, usertype = ? where meter_no = ?";

                            co = c.connection.prepareStatement(query);
                            co.setString(1, susername);
                            co.setString(2, hashPasword);
                            co.setString(3, sloginAs);
                            co.setString(4, smeter);
                            co.executeUpdate();

                    }


                    JOptionPane.showMessageDialog(null, "Account Created");
                    setVisible(false);
                    new Login();
                }

            } catch (Exception E) {
                E.printStackTrace();
            }

        } else if (e.getSource() == back) {
            setVisible(false);
            new Login();
        }
    }
}
