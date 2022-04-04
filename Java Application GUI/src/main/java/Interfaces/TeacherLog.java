package Interfaces;

import connection.DatabaseConnection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TeacherLog extends JFrame implements ActionListener {
    private JPanel panPrinc;
    private JButton addScoreButton;
    private JButton button1;
    private JButton generateStudentFileButton;

    public TeacherLog() {
        this.setContentPane(this.getPanPrinc());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();
        this.setLocationRelativeTo(null);
        addScoreButton.addActionListener(this);
        button1.addActionListener(this);
        generateStudentFileButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == generateStudentFileButton) {
            BufferedWriter bw = null;
            try {
                Connection con = DatabaseConnection.getInstance().getConnection();
                PreparedStatement pst = con.prepareStatement("SELECT * FROM studentdata");
                ResultSet rs = pst.executeQuery();
                FileWriter fw = new FileWriter("src/main/resources/Students");
                bw = new BufferedWriter(fw);
                bw.write("First Name, Last Name, Gender, Phone, Email, Adress");
                bw.newLine();
                rs.next();
                while (rs.next()) {
                    String line = rs.getString(2) + ", " + rs.getString(3) + ", " + rs.getString(5) + ", " + rs.getString(6) + ", " + rs.getString(7) + ", " + rs.getString(8);
                    bw.write(line);
                    bw.newLine();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if (bw != null) {
                    try {
                        bw.close();
                    } catch (IOException ignored) {
                    }
                }
            }
        }
        if (e.getSource() == addScoreButton) {
            new AddScore();
        }
        if (e.getSource() == button1) {
            new Home();
            this.dispose();
        }
    }

    public JPanel getPanPrinc() {
        return panPrinc;
    }
}
