package employee.management.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ViewEmployee extends JFrame {
    JTextField tid, tname, taadhar, tphone;
    JTable table;

    ViewEmployee() {
        setSize(920, 630);
        setLocation(250, 100);
        setLayout(null);

        JLabel idLabel = new JLabel("Employee ID");
        idLabel.setBounds(50, 100, 60, 30);
        add(idLabel);
        tid = new JTextField();
        tid.setBounds(110, 100, 90, 30);
        add(tid);

        JLabel nameLabel = new JLabel("Name");
        nameLabel.setBounds(220, 100, 60, 30);
        add(nameLabel);
        tname = new JTextField();
        tname.setBounds(280, 100, 130, 30);
        add(tname);

        JLabel aadharLabel = new JLabel("Aadhar");
        aadharLabel.setBounds(430, 100, 60, 30);
        add(aadharLabel);
        taadhar = new JTextField();
        taadhar.setBounds(490, 100, 120, 30);
        add(taadhar);

        JLabel phoneLabel = new JLabel("Phone");
        phoneLabel.setBounds(630, 100, 60, 30);
        add(phoneLabel);
        tphone = new JTextField();
        tphone.setBounds(690, 100, 110, 30);
        add(tphone);

        //Search button
        JButton searchBtn = new JButton("Search");
        searchBtn.setBounds(50, 140, 100, 30);
        searchBtn.addActionListener(e -> searchData());
        add(searchBtn);

        //Update button
        JButton updateBtn = new JButton("Update");
        updateBtn.setBounds(160, 140, 100, 30);
        updateBtn.addActionListener(e->updateRecord());
        add(updateBtn);

        //Clear button
        JButton clear = new JButton("Clear");
        clear.setBounds(270, 140, 100, 30);
        clear.addActionListener(e -> {
            tid.setText("");
            tname.setText("");
            taadhar.setText("");
            tphone.setText("");
            loadData();
        });
        add(clear);

        //Back button
        JButton backBtn = new JButton("Back");
        backBtn.setBounds(380, 140, 100, 30);
        backBtn.addActionListener(e->{
            new Home();
        });
        add(backBtn);


        table = new JTable();
        loadData();

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 180, 800, 390);
        scrollPane.setOpaque(false); //for frame
        scrollPane.getViewport().setOpaque(false); //for inner window
        add(scrollPane);

        //setting up image for view employee
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Icons/ViewEmployee.png"));
        Image i2 = i1.getImage().getScaledInstance(920,630,Image.SCALE_SMOOTH);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel img = new JLabel(i3);
        img.setBounds(0,0,920,630);
        add(img);


        setVisible(true);
    }

    void updateRecord() {
        int selectedRow = table.getSelectedRow();
        if(selectedRow==-1){
            JOptionPane.showMessageDialog(null,"Please select a row to update.");
            return;
        }
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int emp_ID = Integer.parseInt(model.getValueAt(selectedRow,0).toString());
        Conn conn = new Conn();
        try{
            //Fetch the current database values for the selected employee.
            PreparedStatement selectedPs = conn.connection.prepareStatement(
                    "Select * FROM employee where employee_id=?"
            );
            selectedPs.setInt(1,emp_ID);
            ResultSet rs = selectedPs.executeQuery();

            if(!rs.next()){
                JOptionPane.showMessageDialog(null,"Record not found.");
                return;
            }
            //Building SET clause dynamically
            StringBuilder query = new StringBuilder("UPDATE employee SET ");
            List<Object> params = new ArrayList<>();
            int columnCount = model.getColumnCount();
            boolean first = true;

            for (int col = 1; col < columnCount; col++) {//skip emp_id column
                String columnName = model.getColumnName(col);
                Object tableValue = model.getValueAt(selectedRow,col);
                Object dbValue = rs.getObject(columnName);

                if(!tableValue.toString().equals(String.valueOf(dbValue))){
                    if(!first) query.append(", ");
                    query.append(columnName).append(" = ?");
                    params.add(tableValue);
                    first = false;
                }
            }
            if (params.isEmpty()){
                JOptionPane.showMessageDialog(null,"No changes detected.");
                return;
            }
            query.append("WHERE employee_id = ?");
            params.add(emp_ID);

            //run dynamically build update
            PreparedStatement updatePs = conn.connection.prepareStatement(query.toString());
            for (int i = 0;i<params.size(); i++){
                updatePs.setObject(i+1, params.get(i));
            }
            int rowsAffected = updatePs.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Record updated successfully!");
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Update error: " + ex.getMessage());
        }
    }

    void loadData() {
        Conn conn = new Conn();
        try {
            ResultSet rs = conn.statement.executeQuery("SELECT * FROM employee");
            populateTable(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading records: " + e.getMessage());
        }
    }

    void searchData() {
        StringBuilder query = new StringBuilder("SELECT * FROM employee WHERE 1=1");
        List<Object> params = new ArrayList<>();
        if (!tid.getText().trim().isEmpty()) {
            query.append(" AND employee_id = ?");
            params.add(Integer.parseInt(tid.getText().trim()));
        }
        if (!tname.getText().trim().isEmpty()) {
            query.append(" AND name = ?");
            params.add(tname.getText().trim());
        }
        if (!taadhar.getText().trim().isEmpty()) {
            query.append(" AND Aadhar = ?");
            params.add(Long.parseLong(taadhar.getText().trim()));
        }
        if (!tphone.getText().trim().isEmpty()) {
            query.append(" AND phone = ?");
            params.add(Long.parseLong(tphone.getText().trim()));
        }

        if (params.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Enter at least one field to search.");
            return;
        }

        Conn conn = new Conn();
        try {
            PreparedStatement ps = conn.connection.prepareStatement(query.toString());
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            ResultSet rs = ps.executeQuery();
            populateTable(rs);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "ID, Aadhar, and Phone must be valid numbers.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Search error: " + ex.getMessage());
        }
    }

    void populateTable(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        String[] columnNames = new String[columnCount];
        for (int i = 0; i < columnCount; i++) {
            columnNames[i] = metaData.getColumnName(i + 1);
        }

        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            //makes the columns non-editable
            public boolean isCellEditable(int row, int column) {
                return column != 0; //locking the primary key
            }
        };

        while (rs.next()) {
            Object[] row = new Object[columnCount];
            for (int i = 0; i < columnCount; i++) {
                row[i] = rs.getObject(i + 1);
            }
            model.addRow(row);
        }

        table.setModel(model);

        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "No matching records found.");
        }
    }

    public static void main(String[] args) {
        new ViewEmployee();
    }
}