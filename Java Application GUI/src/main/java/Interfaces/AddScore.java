package Interfaces;

import connection.DatabaseConnectionSi;
import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AddScore extends JFrame implements ActionListener {
    private JPanel panPrinc;
    private JPanel tablePan;
    private JTable table1;
    private JButton closeButton;
    private JButton displayScoreButton;
    private JButton deleteScoreButton;
    private JButton addScoreButton;

    public AddScore() {
        this.setContentPane(this.getPanPrinc());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();
        this.setLocationRelativeTo(null);
        addScoreButton.addActionListener(this);
        displayScoreButton.addActionListener(this);
        closeButton.addActionListener(this);
        deleteScoreButton.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addScoreButton) {
            new AddScoreInDB();
        }
        if (e.getSource() == closeButton) {
            this.dispose();
        }
        if (e.getSource() == displayScoreButton) {
            try {
                Connection con = DatabaseConnectionSi.getInstance().getConnection();
                //Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/situation", "root", "password");
                PreparedStatement st = con.prepareStatement("SELECT firstname,lastname,course,grade FROM situation");
                ResultSet rs = st.executeQuery();
                table1.setModel(DbUtils.resultSetToTableModel(rs));
                con.close();

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        if (e.getSource() == deleteScoreButton) {
            if (table1.getSelectedRow() != -1) {
                try {
                    Connection con = DatabaseConnectionSi.getInstance().getConnection();
                    //Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/situation", "root", "password");
                    PreparedStatement pst = con.prepareStatement("DELETE FROM situation WHERE firstname=? AND course=?");
                    pst.setString(1, (String) table1.getModel().getValueAt(table1.getSelectedRow(), 0));
                    pst.setString(2, (String) table1.getModel().getValueAt(table1.getSelectedRow(), 2));
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
            Connection con = DatabaseConnectionSi.getInstance().getConnection();
            //Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/situation", "root", "password");
            PreparedStatement st = con.prepareStatement("SELECT firstname,lastname,course,grade FROM situation");
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
