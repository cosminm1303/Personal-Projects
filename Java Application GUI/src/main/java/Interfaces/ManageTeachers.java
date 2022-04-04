package Interfaces;

import connection.DatabaseConnectionT;
import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ManageTeachers extends JFrame implements ActionListener {
    private JPanel panPrinc;
    private JPanel jPan;
    private JTable table1;
    private JButton closeButton;
    private JButton displayTeacherButton;
    private JButton deleteTeacherButton;
    private JButton addTeacherButton;

    public ManageTeachers() {
        this.setContentPane(this.getPanPrinc());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();
        this.setLocationRelativeTo(null);
        closeButton.addActionListener(this);
        displayTeacherButton.addActionListener(this);
        deleteTeacherButton.addActionListener(this);
        addTeacherButton.addActionListener(this);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addTeacherButton) {
            new AddTeacher();
        }
        if (e.getSource() == closeButton) {
            this.dispose();
        }
        if (e.getSource() == displayTeacherButton) {
            try {
                Connection con = DatabaseConnectionT.getInstance().getConnection();
                //Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/teachers", "root", "password");
                PreparedStatement st = con.prepareStatement("SELECT firstname,lastname,password FROM teachers");
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    table1.setModel(DbUtils.resultSetToTableModel(rs));
                }
                con.close();

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        if (e.getSource() == deleteTeacherButton) {
            if (table1.getSelectedRow() != -1) {
                try {
                    Connection con = DatabaseConnectionT.getInstance().getConnection();
                    //Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/teachers", "root", "password");
                    PreparedStatement pst = con.prepareStatement("DELETE FROM teachers WHERE firstname=?");
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

    private void refresh() {
        try {
            Connection con = DatabaseConnectionT.getInstance().getConnection();
            //Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/teachers", "root", "password");
            PreparedStatement st = con.prepareStatement("SELECT firstname,lastname,password FROM teachers");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                table1.setModel(DbUtils.resultSetToTableModel(rs));
            }
            con.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public JPanel getPanPrinc() {
        return panPrinc;
    }
}
