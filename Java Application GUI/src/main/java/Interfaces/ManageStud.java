package Interfaces;

import connection.DatabaseConnection;
import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ManageStud extends JFrame implements ActionListener {
    private JPanel pagPrinc;
    private JPanel jPan;
    private JButton deleteStudentButton;
    private JTable table1;
    private JButton displayButton;
    private JButton addStudentButton;
    private JButton closeButton;

    public ManageStud() {
        this.setContentPane(this.getjPan());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();
        this.setLocationRelativeTo(null);
        displayButton.addActionListener(this);
        closeButton.addActionListener(this);
        deleteStudentButton.addActionListener(this);
        addStudentButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addStudentButton) {
            new Create_Acc();
        }
        if (e.getSource() == closeButton) {
            this.dispose();
        }
        if (e.getSource() == displayButton) {
            try {
                Connection con = DatabaseConnection.getInstance().getConnection();
                //Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentdata", "root", "password");
                PreparedStatement st = con.prepareStatement("SELECT firstname,lastname,phone,email,adress FROM studentdata");
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    table1.setModel(DbUtils.resultSetToTableModel(rs));
                }
                con.close();

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        if (e.getSource() == deleteStudentButton) {

            if (table1.getSelectedRow() != -1) {
                try {
                    Connection con = DatabaseConnection.getInstance().getConnection();
                    //Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentdata", "root", "password");
                    PreparedStatement pst = con.prepareStatement("DELETE FROM studentdata WHERE firstname=?");
                    pst.setString(1, (String) table1.getModel().getValueAt(table1.getSelectedRow(), 0));
                    pst.executeUpdate();
                    con.close();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }

            } else {
                JOptionPane.showMessageDialog(this,
                        "No row selected",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
            refresh();
        }
    }

    public void refresh() {
        try {
            Connection con = DatabaseConnection.getInstance().getConnection();
            //Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentdata", "root", "password");
            PreparedStatement st = con.prepareStatement("SELECT firstname,lastname,phone,email,adress FROM studentdata");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                table1.setModel(DbUtils.resultSetToTableModel(rs));
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public JPanel getjPan() {
        return jPan;
    }
}
