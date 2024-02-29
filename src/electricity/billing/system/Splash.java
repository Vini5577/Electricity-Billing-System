package electricity.billing.system;

import java.awt.*;

import javax.swing.*;

public class Splash extends JFrame {

    Splash() {

        ImageIcon imageIcon = new ImageIcon(ClassLoader.getSystemResource("icon/splash/splash.jpg"));
        Image imageOne = imageIcon.getImage().getScaledInstance(600, 400, Image.SCALE_DEFAULT);
        ImageIcon imageIcone2 = new ImageIcon(imageOne);
        JLabel imageLabel = new JLabel(imageIcone2);
        add(imageLabel);

        setSize(400, 400);
        setLocation(300, 600);
        setVisible(true);

        try {
            Thread.sleep(3000);
            setVisible(false);

            new Login();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Splash();
    }
}
