package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login extends JFrame implements ActionListener {
    JTextField tUserName;
    JPasswordField tPassword;
    JButton login, back;

    Login() {
        //Username label
        JLabel userName = new JLabel("Username");
        userName.setBounds(70,30,100,30);
        userName.setForeground(Color.white);
        add(userName);
        //Username text field
        tUserName = new JTextField();
        tUserName.setBounds(200,30,150,30);
        add(tUserName);

        //password label
        JLabel password = new JLabel("Password");
        password.setBounds(70,80,100,30);
        password.setForeground(Color.white);
        add(password);
        //password text field
        tPassword = new JPasswordField();
        tPassword.setBounds(200,80,150,30);
        add(tPassword);

        //login button
        login = new JButton("Login");
        login.setBounds(90,140,100,30);
        login.setBackground(Color.black);
        login.setForeground(Color.white);
        login.addActionListener(this);
        add(login);

        //back button
        back = new JButton("Back");
        back.setBounds(220,140,100,30);
        back.setBackground(Color.black);
        back.setForeground(Color.white);
        back.addActionListener(this);
        add(back);

        //setting login image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Icons/LoginB.jpg"));
        Image i2 = i1.getImage().getScaledInstance(500,300,Image.SCALE_SMOOTH);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel img = new JLabel(i3);
        img.setBounds(0,0,500,300);
        add(img);


        setSize(500,300);
        setLocation(450,200);
        setLayout(null);

        setVisible(true);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==login){
            try {
            String userName = tUserName.getText();
            String userPassword = tPassword.getText();

            Conn conn = new Conn();
                String query = "SELECT * FROM login WHERE username = ? AND password = ?";
                PreparedStatement ps = conn.connection.prepareStatement(query);
                ps.setString(1,userName);
                ps.setString(2,userPassword);
            ResultSet resultSet = ps.executeQuery();
            //checking whether the record is present or not
            if(resultSet.next()){
                //calling Home page
                new Home();

                } else {
                System.out.println("Row not found");
                    JOptionPane.showMessageDialog(null, "Invalid username or password.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new Login();
    }

}
