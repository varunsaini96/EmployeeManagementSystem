package employee.management.system;

import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;

public class AddEmployee extends JFrame implements ActionListener {
    JTextField tname, tfatherName, taddress, tphone, taadhar, temail, tsalary, tdesignation;
    DatePicker datePicker;
    JComboBox educationBox;
    JButton adding, cancel;

    AddEmployee(){
        setSize(920,630);
        setLocation(250,100);
        setLayout(null);

        //label for name
        JLabel name = new JLabel("Name");
        name.setBounds(50,90,150,30);
        name.setFont(new Font("SAN_SERIF",Font.BOLD,20));
        add(name);

        //name text
        tname = new JTextField();
        tname.setBounds(200,90,150,30);
        add(tname);

        //label for Father Name
        JLabel fatherName = new JLabel("Father Name");
        fatherName.setBounds(450,90,150,30);
        fatherName.setFont(new Font("SAN_SERIF",Font.BOLD,20));
        add(fatherName);

        //Father name text
        tfatherName = new JTextField();
        tfatherName.setBounds(600,90,150,30);
        add(tfatherName);

        //label for DOB
        JLabel DOB = new JLabel("Date of Birth");
        DOB.setBounds(50,170,150,30);
        DOB.setFont(new Font("SAN_SERIF",Font.BOLD,20));
        add(DOB);

        //DOB Picker
        datePicker = new DatePicker();
        datePicker.setBounds(200,170,150,30);
        add(datePicker);

        //label for salary
        JLabel salary = new JLabel("Salary");
        salary.setBounds(450,170,150,30);
        salary.setFont(new Font("SAN_SERIF",Font.BOLD,20));
        add(salary);

        //text for salary
        tsalary = new JTextField();
        tsalary.setBounds(600,170,150,30);
        add(tsalary);

        //label for address
        JLabel address = new JLabel("Address");
        address.setBounds(50,250,150,30);
        address.setFont(new Font("SAN_SERIF",Font.BOLD,20));
        add(address);

        //text for address
        taddress = new JTextField();
        taddress.setBounds(200,250,150,30);
        add(taddress);

        //label for Phone
        JLabel phone = new JLabel("Phone");
        phone.setBounds(450,250,150,30);
        phone.setFont(new Font("SAN_SERIF",Font.BOLD,20));
        add(phone);

        //text for phone
        tphone = new JTextField();
        tphone.setBounds(600,250,150,30);
        add(tphone);

        //label for email
        JLabel email = new JLabel("Email");
        email.setBounds(50,330,150,30);
        email.setFont(new Font("SAN_SERIF",Font.BOLD,20));
        add(email);

        //text for email
        temail = new JTextField();
        temail.setBounds(200,330,150,30);
        add(temail);

        //label for highest qualification
        JLabel highestQualificaiton = new JLabel("Highest Qualificaiton");
        highestQualificaiton.setBounds(450,330,150,30);
        highestQualificaiton.setFont(new Font("SAN_SERIF",Font.BOLD,20));
        add(highestQualificaiton);

        //Box for eduction
        String educationLists [] = {"","BA","BBA","B.Tech","B.Com","BCA","B.Sc","MBA","MCA","M.Tech","M.Sc"};
        educationBox = new JComboBox(educationLists);
        educationBox.setBounds(600,330,150,30);
        add(educationBox);

        //label for aadhar
        JLabel aadhar = new JLabel("Aadhar");
        aadhar.setBounds(50,410,150,30);
        aadhar.setFont(new Font("SAN_SERIF",Font.BOLD,20));
        add(aadhar);

        //text for aadhar
        taadhar = new JTextField();
        taadhar.setBounds(200,410,150,30);
        add(taadhar);

        //label for designation
        JLabel designation = new JLabel("Designation");
        designation.setBounds(450,410,150,30);
        designation.setFont(new Font("SAN_SERIF",Font.BOLD,20));
        add(designation);

        //text for designation
        tdesignation = new JTextField();
        tdesignation.setBounds(600,410,150,30);
        add(tdesignation);

        //add button
        adding = new JButton("Add");
        adding.setBounds(200,490,150,40);
        adding.addActionListener(this);
        add(adding);


        //cancel button
        cancel = new JButton("Cancel");
        cancel.setBounds(450,490,150,40);
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent c) {
                new Home();
            }
        });
        add(cancel);

        //adding background image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Icons/AddEmployee.jpg"));
        Image i2 = i1.getImage().getScaledInstance(920,630,Image.SCALE_SMOOTH);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel img = new JLabel(i3);
        img.setBounds(0,0,920,630);
        add(img);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent a){
        if (a.getSource()==adding) {
            try {
                String name = tname.getText();
                String fatherName = tfatherName.getText();
                LocalDate localDate = datePicker.getDate();
                if (localDate == null) {
                    JOptionPane.showMessageDialog(null, "Please select a Date of Birth.");
                    return;
                }
                Date DOB = Date.valueOf(localDate);
                long salary = Long.parseLong(tsalary.getText());
                String address = taddress.getText();
                long phone = Long.parseLong(tphone.getText());
                String email = temail.getText();
                String highestQualification = (String) educationBox.getSelectedItem();
                long aadhar = Long.parseLong(taadhar.getText());
                String designation = tdesignation.getText();

                String query = "INSERT INTO employee(name, father_name, dob, salary, address, phone, email, highest_Qualification,Aadhar, Designation)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                Conn conn = new Conn();
                PreparedStatement ps = conn.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, name);
                ps.setString(2, fatherName);
                ps.setString(3, String.valueOf(DOB));
                ps.setLong(4, salary);
                ps.setString(5, address);
                ps.setLong(6, phone);
                ps.setString(7, email);
                ps.setString(8, highestQualification);
                ps.setLong(9, aadhar);
                ps.setString(10, designation);
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int employeeId = rs.getInt(1);
                    JOptionPane.showMessageDialog(null, "Employee Added Successfully!\nEmployee ID: " + employeeId);
                    new Home();
                    dispose();
                }
            } catch (SQLException ex) {
                ex.getStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            } catch (Exception exception) {
                exception.getStackTrace();
            }
        }
        }
    public static void main(String[] args) {
        new AddEmployee();
    }
    }
