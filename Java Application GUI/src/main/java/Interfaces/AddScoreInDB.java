package Interfaces;

import connection.DatabaseConnection;
import connection.DatabaseConnectionC;
import connection.DatabaseConnectionSi;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AddScoreInDB extends JFrame implements ActionListener {
    private JPanel jPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton closeButton;
    private JButton submitButton;

    public AddScoreInDB() {
        this.setContentPane(this.getjPanel());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();
        this.setLocationRelativeTo(null);
        submitButton.addActionListener(this);
        closeButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == closeButton) {
            this.dispose();
        }
        if (e.getSource() == submitButton) {
            int ok = 0;
            int verif = 0;
            int verifc = 0;
            if (!textField1.getText().isEmpty() && !textField2.getText().isEmpty() && !textField3.getText().isEmpty() && !textField4.getText().isEmpty()) {
                try {
                    Connection con = DatabaseConnection.getInstance().getConnection();
                    Connection cone = DatabaseConnectionSi.getInstance().getConnection();
                    Connection cons = DatabaseConnectionC.getInstance().getConnection();
                    PreparedStatement st = con.prepareStatement("SELECT firstname,lastname FROM studentdata");
                    PreparedStatement cst = cons.prepareStatement("SELECT course FROM courses");
                    PreparedStatement pst = cone.prepareStatement("INSERT INTO situation(firstname,lastname,course,grade)VALUE(?,?,?,?)");
                    ResultSet rs = st.executeQuery();
                    ResultSet rs1 = cst.executeQuery();

                    while (rs1.next()) {
                        if (textField3.getText().equals(rs1.getString(1))) {
                            verifc = 1;
                        }
                    }

                    while (rs.next()) {
                        if (textField1.getText().equals(rs.getString(1)) && textField2.getText().equals(rs.getString(2))) {
                            verif = 1;
                        }
                    }
                    for (char c : textField4.getText().toCharArray()) {
                        if (c >= 48 && c <= 57) {
                            ok = 1;
                        } else {
                            ok = 0;
                        }
                    }

                    if (verifc == 1) {
                        pst.setString(2, textField3.getText());
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "No course with that name",
                                "Warning",
                                JOptionPane.WARNING_MESSAGE);
                    }

                    if (verif == 1) {
                        pst.setString(1, textField1.getText());
                        pst.setString(2, textField2.getText());
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "No student with that name",
                                "Warning",
                                JOptionPane.WARNING_MESSAGE);
                    }
                    if (ok == 1) {
                        pst.setString(4, textField4.getText());
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Grade field must contain numbers",
                                "Warning",
                                JOptionPane.WARNING_MESSAGE);
                    }
                    pst.setString(3, textField3.getText());
                    if (ok == 1 && verif == 1 && verifc == 1) {
                        pst.executeUpdate();
                        this.dispose();
                        con.close();
                        cone.close();
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Please complete all fields",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    public JPanel getjPanel() {
        return jPanel;
    }
}
