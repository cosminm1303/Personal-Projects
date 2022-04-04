package Interfaces;

import connection.DatabaseConnection;
import connection.DatabaseConnectionC;
import connection.DatabaseConnectionSi;
import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StudLog extends JFrame implements ActionListener {
    private final String useri;
    private final String passi;
    private JTabbedPane princPan;
    private JPanel panel1;
    private JPanel panprinc;
    private JPanel schoolsit;
    private JPanel personal;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JLabel firstNameLabel;
    private JLabel lastNameLabel;
    private JLabel phoneLabel;
    private JLabel emailLabel;
    private JLabel adressLabel;
    private JButton changePersonalDataButton;
    private JPanel courses;
    private JButton button1;
    private JButton refreshButton;
    private JTable table1;
    private JTable table2;
    private JButton displayButton;
    private JButton displayButton1;

    public StudLog(String user, String pass) {
        useri = user;
        passi = pass;
        String[] res = user.split("[.]", 2);
        this.setContentPane(this.getPanprinc());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();
        this.setLocationRelativeTo(null);
        button1.addActionListener(this);
        changePersonalDataButton.addActionListener(this);
        refreshButton.addActionListener(this);
        displayButton.addActionListener(this);
        displayButton1.addActionListener(this);

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentdata", "root", "password");
            PreparedStatement st = con.prepareStatement("SELECT * FROM studentdata WHERE firstname=? AND lastname=? AND password=?");
            st.setString(1, res[0]);
            st.setString(2, res[1]);
            st.setString(3, pass);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                textField1.setText(rs.getString(2));
                textField4.setText(rs.getString(3));
                textField3.setText(rs.getString(6));
                textField5.setText(rs.getString(7));
                textField2.setText(rs.getString(8));
            }
            con.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == displayButton1) {
            try {
                Connection con = DatabaseConnectionC.getInstance().getConnection();
                //Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/courses", "root", "password");
                PreparedStatement st = con.prepareStatement("SELECT course,credits,hours FROM courses");
                ResultSet rs = st.executeQuery();
                table2.setModel(DbUtils.resultSetToTableModel(rs));
                con.close();

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        if (e.getSource() == displayButton) {
            String[] res = useri.split("[.]", 2);
            try {
                Connection con = DatabaseConnectionSi.getInstance().getConnection();
                //Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/situation", "root", "password");
                PreparedStatement st = con.prepareStatement("SELECT firstname, lastname, course, grade FROM situation WHERE firstname=? AND lastname=?");
                st.setString(1, res[0]);
                st.setString(2, res[1]);
                ResultSet rs = st.executeQuery();
                table1.setModel(DbUtils.resultSetToTableModel(rs));
                con.close();

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        if (e.getSource() == button1) {
            new Login_interf();
            this.dispose();
        }
        if (e.getSource() == changePersonalDataButton) {
            new ChangeData(useri, passi);
        }
        if (e.getSource() == refreshButton) {
            String[] res = useri.split("[.]", 2);
            try {
                Connection con = DatabaseConnection.getInstance().getConnection();
                //Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentdata", "root", "password");
                PreparedStatement st = con.prepareStatement("SELECT * FROM studentdata WHERE firstname=? AND lastname=? AND password=?");
                st.setString(1, res[0]);
                st.setString(2, res[1]);
                st.setString(3, passi);
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    textField1.setText(rs.getString(2));
                    textField4.setText(rs.getString(3));
                    textField3.setText(rs.getString(6));
                    textField5.setText(rs.getString(7));
                    textField2.setText(rs.getString(8));
                }
                con.close();

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public JPanel getPanprinc() {
        return panprinc;
    }
}
