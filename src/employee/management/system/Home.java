package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home extends JFrame {
    public Home() {
        // Background Image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Icons/home.png"));
        Image i2 = i1.getImage().getScaledInstance(920, 630, Image.SCALE_SMOOTH);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel img = new JLabel(i3);
        img.setBounds(0, 0, 920, 630);
        setLayout(null);
        add(img);

        //Add employee
        JButton addEmployee = new JButton();
        addEmployee.setBounds(75, 250, 235, 210);

        // Make button completely transparent
        addEmployee.setOpaque(false);
        addEmployee.setContentAreaFilled(false);
        addEmployee.setBorderPainted(false);
        addEmployee.setFocusPainted(false);

        addEmployee.setCursor(new Cursor(Cursor.HAND_CURSOR));

        addEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddEmployee();
            }
        });
        img.add(addEmployee);

        //View employee
        JButton viewEmployee = new JButton();
        viewEmployee.setBounds(340, 250, 240, 210);

        viewEmployee.setOpaque(false);
        viewEmployee.setContentAreaFilled(false);
        viewEmployee.setBorderPainted(false);
        viewEmployee.setFocusPainted(false);

        viewEmployee.setCursor(new Cursor(Cursor.HAND_CURSOR));

        viewEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewEmployee();
            }
        });

        img.add(viewEmployee);

        //Remove employee
        JButton removeEmployee = new JButton();
        removeEmployee.setBounds(610, 250, 240, 210);

        removeEmployee.setOpaque(false);
        removeEmployee.setContentAreaFilled(false);
        removeEmployee.setBorderPainted(false);
        removeEmployee.setFocusPainted(false);

        removeEmployee.setCursor(new Cursor(Cursor.HAND_CURSOR));

        removeEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RemoveEmployee();
            }
        });

        img.add(removeEmployee);

        // Frame Settings
        setSize(920, 630);
        setLocation(250, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }


    public static void main(String[] args) {
        new Home();
    }
}
