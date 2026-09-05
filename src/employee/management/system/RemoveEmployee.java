package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RemoveEmployee extends JFrame {

    JTextField tEmpID;
    JButton searchBtn, clearBtn, deleteBtn, backBtn;
    JLabel empName, empNameValue, empFatherName, empFatherValue, phone, phoneValue, email, emailValue;
    int foundEmpId = -1;

    RemoveEmployee(){
        setSize(920, 630);
        setLocation(250, 100);
        setLayout(null);

        JLabel emp_id = new JLabel("Enter the Employee ID");
        emp_id.setBounds(90,100,400,30);
        add(emp_id);
        tEmpID = new JTextField();
        tEmpID.setBounds(300,100,150,30);
        add(tEmpID);

        //search button
        searchBtn = new JButton("Search");
        searchBtn.setBounds(90,150,80,30);
        searchBtn.addActionListener(s->searchRecord());
        add(searchBtn);

        //clear button
        clearBtn = new JButton("Clear");
        clearBtn.setBounds(185,150,80,30);
        clearBtn.addActionListener(e-> clearFields());
        add(clearBtn);

        //delete button - hidden until a record is found
        deleteBtn = new JButton("Delete");
        deleteBtn.setBounds(280,150,80,30);
        deleteBtn.addActionListener(e -> deleteRecord());
        add(deleteBtn);

        //back button - always visible
        backBtn = new JButton("Back");
        backBtn.setBounds(370,150,80,30);
        backBtn.addActionListener(e -> {
            new Home();
            dispose();
        });
        add(backBtn);

        //Adding fields that will show after search
        empName = new JLabel("Employee Name");
        empName.setBounds(90, 200, 100, 30);
        add(empName);
        empName.setVisible(false);
        empNameValue = new JLabel();
        empNameValue.setBounds(210,200,100,30);
        add(empNameValue);
        empNameValue.setVisible(false);

        empFatherName = new JLabel("Father Name");
        empFatherName.setBounds(90, 250, 100, 30);
        add(empFatherName);
        empFatherName.setVisible(false);
        empFatherValue = new JLabel();
        empFatherValue.setBounds(210,250,100,30);
        add(empFatherValue);
        empFatherValue.setVisible(false);

        phone = new JLabel("Phone");
        phone.setBounds(90, 300, 100, 30);
        add(phone);
        phone.setVisible(false);
        phoneValue = new JLabel();
        phoneValue.setBounds(210,300,100,30);
        add(phoneValue);
        phoneValue.setVisible(false);

        email = new JLabel("Email");
        email.setBounds(90, 350, 100, 30);
        add(email);
        email.setVisible(false);
        emailValue = new JLabel();
        emailValue.setBounds(210,350,100,30);
        add(emailValue); // fixed: was adding empFatherValue again
        emailValue.setVisible(false);

        //setting up Remove employee image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Icons/RemoveEmployee.png"));
        Image i2 = i1.getImage().getScaledInstance(920,630, Image.SCALE_SMOOTH);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel img = new JLabel(i3);
        img.setBounds(0,0,920,630);
        add(img);

        setVisible(true);
    }

    void searchRecord() {
        try{
            int empId = Integer.parseInt(tEmpID.getText().trim());
            Conn conn = new Conn();
            String query = "SELECT * FROM employee WHERE employee_id = ?"; // fixed column name
            PreparedStatement ps = conn.connection.prepareStatement(query);
            ps.setInt(1,empId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                empNameValue.setText(rs.getString("name"));
                empFatherValue.setText(rs.getString("father_name"));
                phoneValue.setText(String.valueOf(rs.getLong("phone")));
                emailValue.setText(rs.getString("email"));

                empName.setVisible(true);
                empNameValue.setVisible(true);
                empFatherName.setVisible(true);
                empFatherValue.setVisible(true);
                phone.setVisible(true);
                phoneValue.setVisible(true);
                email.setVisible(true);
                emailValue.setVisible(true);

                foundEmpId = empId;

            } else {
                JOptionPane.showMessageDialog(null,"Please check the Employee ID.");
                foundEmpId = -1;
                hideFields();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Please enter a valid numeric Employee ID.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Search error: " + ex.getMessage());
        }
    }

    void deleteRecord() {
        if (foundEmpId == -1) {
            JOptionPane.showMessageDialog(null, "Please search for a valid employee first.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to delete this employee (ID: " + foundEmpId + ")?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        Conn conn = new Conn();
        try {
            String query = "DELETE FROM employee WHERE employee_id = ?";
            PreparedStatement ps = conn.connection.prepareStatement(query);
            ps.setInt(1, foundEmpId);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Employee deleted successfully.");
                clearFields();
            } else {
                JOptionPane.showMessageDialog(null, "No record was deleted. It may have already been removed.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Delete error: " + ex.getMessage());
        }
    }

    void clearFields() {
        tEmpID.setText("");
        hideFields();
        foundEmpId = -1;
    }

    void hideFields() {
        empName.setVisible(false);
        empNameValue.setVisible(false);
        empFatherName.setVisible(false);
        empFatherValue.setVisible(false);
        phone.setVisible(false);
        phoneValue.setVisible(false);
        email.setVisible(false);
        emailValue.setVisible(false);
    }

    public static void main(String[] args) {
        new RemoveEmployee();
    }
}