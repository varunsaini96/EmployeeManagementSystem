package employee.management.system;

import javax.swing.*;
import java.awt.*;

public class Splash extends JFrame {

    Splash(){

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Icons/front.gif"));
        Image i2 = i1.getImage().getScaledInstance(800,450,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0,0,800,450);
        add(image);

        setSize(800,450);
        setLayout(null);
        setVisible(true);
        setLocation(200,50);

        try {
            Thread.sleep(4000);
            setVisible(false);
            new Login();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void main(String[] args) {
        new Splash();
    }
}
