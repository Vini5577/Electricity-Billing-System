package electricity.billing.system;

import at.favre.lib.crypto.bcrypt.BCrypt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login extends JFrame implements ActionListener {

    JTextField userText;
    JPasswordField passwordText;
    Choice loginChoice;
    JButton loginButton, cancelButton, signupButton;

    Login() {
        super("Login");
        getContentPane().setBackground(Color.white);
        JLabel username = new JLabel("UserName");
        username.setBounds(300, 60, 100, 20);
        add(username);

        userText = new JTextField();
        userText.setBounds(400, 60, 150, 20);
        add(userText);

        JLabel password = new JLabel("Password");
        password.setBounds(300, 100, 100, 20);
        add(password);

        passwordText = new JPasswordField();
        passwordText.setBounds(400, 100, 150, 20);
        add(passwordText);

        JLabel loggin = new JLabel("Loggin In As");
        loggin.setBounds(300, 140, 100, 20);
        add(loggin);

        loginChoice = new Choice();
        loginChoice.add("Admin");
        loginChoice.add("Customer");
        loginChoice.setBounds(400, 140, 150, 20);
        add(loginChoice);

        loginButton = new JButton("Login");
        loginButton.setBounds(330, 180, 100, 20);
        loginButton.addActionListener(this);
        add(loginButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(460, 180, 100, 20);
        cancelButton.addActionListener(this);
        add(cancelButton);

        signupButton = new JButton("Signup");
        signupButton.setBounds(400, 210, 100, 20);
        signupButton.addActionListener(this);
        add(signupButton);

        ImageIcon profileOne = new ImageIcon(ClassLoader.getSystemResource("icon/profile.png"));
        Image profile = profileOne.getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT);
        ImageIcon fprofileOne = new ImageIcon(profile);
        JLabel profileLable = new JLabel(fprofileOne);
        profileLable.setBounds(5, 5, 250, 250);
        add(profileLable);

        setSize(640, 300);
        setLocation(400, 200);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == loginButton) {
            String susername = userText.getText();
            String spassword = String.valueOf(passwordText.getPassword());
            String suser = loginChoice.getSelectedItem();

            try {
                database c = new database();

                String query = "select * from Signup where username = ? and usertype = ?";
                PreparedStatement co = c.connection.prepareStatement(query);
                co.setString(1, susername);
                co.setString(2, suser);
                ResultSet resultSet = co.executeQuery();

                if (resultSet.next()) {
                    BCrypt.Result result = BCrypt.verifyer().verify(spassword.toCharArray(),
                            resultSet.getString("password"));
                    if (result.verified) {
                        String meter = resultSet.getString("meter_no");

                        setVisible(false);
                        new main_class(suser, meter);
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid Login");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Login");
                }

            } catch (Exception E) {
                E.printStackTrace();
            }
        } else if (e.getSource() == cancelButton) {
            setVisible(false);
        } else if (e.getSource() == signupButton) {
            setVisible(false);
            new Signup();
        }

    }
}
