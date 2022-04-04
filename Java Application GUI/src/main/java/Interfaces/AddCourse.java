package Interfaces;

import connection.DatabaseConnectionC;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddCourse extends JFrame implements ActionListener {
    private JPanel panPrinc;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton closeButton;
    private JButton submitButton;

    public AddCourse() {
        this.setContentPane(this.getPanPrinc());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();
        this.setLocationRelativeTo(null);
        closeButton.addActionListener(this);
        submitButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == closeButton) {
            this.dispose();
        }
        if (e.getSource() == submitButton) {
            try {
                int ok = 0;
                int verif = 0;
                Connection con = DatabaseConnectionC.getInstance().getConnection();
                //Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/courses","root","password");
                PreparedStatement pst = con.prepareStatement("INSERT INTO courses(course,credits,hours)VALUE(?,?,?)");
                if (!textField1.getText().isEmpty() && !textField2.getText().isEmpty() && !textField3.getText().isEmpty()) {

                    for (char c : textField1.getText().toCharArray()) {
                        if (c >= 48 && c <= 57) {
                            ok = 1;
                        } else {
                            ok = 0;
                        }
                    }
                    if (ok == 1) {
                        pst.setString(3, textField1.getText());
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Hours field must contain numbers",
                                "Warning",
                                JOptionPane.WARNING_MESSAGE);
                    }

                    for (char d : textField2.getText().toCharArray()) {
                        if (d >= 48 && d <= 57) {
                            verif = 1;
                        } else {
                            verif = 0;
                        }
                    }
                    if (verif == 1) {
                        pst.setString(2, textField2.getText());
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Credit field must contain numbers",
                                "Warning",
                                JOptionPane.WARNING_MESSAGE);
                    }
                    pst.setString(1, textField3.getText());

                    if (ok == 1 && verif == 1) {
                        pst.executeUpdate();
                        this.dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Please complete all fields",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    public JPanel getPanPrinc() {
        return panPrinc;
    }
}
