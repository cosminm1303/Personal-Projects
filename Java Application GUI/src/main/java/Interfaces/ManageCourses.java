package Interfaces;

import connection.DatabaseConnectionC;
import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ManageCourses extends JFrame implements ActionListener {
    private JPanel panPrinc;
    private JPanel jPanel;
    private JTable table1;
    private JButton displayCoursesButton;
    private JButton deleteCourseButton;
    private JButton addCourseButton;
    private JButton closeButton;
    private JPanel tablePan;

    public ManageCourses() {
        this.setContentPane(this.getPanPrinc());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();
        this.setLocationRelativeTo(null);
        displayCoursesButton.addActionListener(this);
        closeButton.addActionListener(this);
        deleteCourseButton.addActionListener(this);
        addCourseButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addCourseButton) {
            new AddCourse();
        }
        if (e.getSource() == closeButton) {
            this.dispose();
        }
        if (e.getSource() == displayCoursesButton) {
            try {
                Connection con = DatabaseConnectionC.getInstance().getConnection();
                //Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/courses", "root", "password");
                PreparedStatement st = con.prepareStatement("SELECT course,credits,hours FROM courses");
                ResultSet rs = st.executeQuery();
                table1.setModel(DbUtils.resultSetToTableModel(rs));
                con.close();

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        if (e.getSource() == deleteCourseButton) {
            if (table1.getSelectedRow() != -1) {
                try {
                    Connection con = DatabaseConnectionC.getInstance().getConnection();
                    //Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/courses", "root", "password");
                    PreparedStatement pst = con.prepareStatement("DELETE FROM courses WHERE course=?");
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
            Connection con = DatabaseConnectionC.getInstance().getConnection();
            //Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/courses", "root", "password");
            PreparedStatement st = con.prepareStatement("SELECT course,credits,hours FROM courses");
            ResultSet rs = st.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
            con.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public JPanel getPanPrinc() {
        return panPrinc;
    }
}
