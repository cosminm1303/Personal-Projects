package Interfaces;

import connection.DatabaseConnectionT;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddTeacher extends JFrame implements ActionListener {
    private JPanel panPrinc;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField4;
    private JButton closeButton;
    private JButton submitButton1;

    public AddTeacher() {
        this.setContentPane(this.getPanPrinc());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();
        this.setLocationRelativeTo(null);
        closeButton.addActionListener(this);
        submitButton1.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == closeButton) {
            this.dispose();
        }
        if (e.getSource() == submitButton1) {
            try {
                Connection con = DatabaseConnectionT.getInstance().getConnection();
                //Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/teachers","root","password");
                PreparedStatement pst = con.prepareStatement("INSERT INTO teachers(firstname,lastname,password)VALUE(?,?,?)");
                if (!textField1.getText().isEmpty() && !textField2.getText().isEmpty() && !textField4.getText().isEmpty()) {
                    pst.setString(1, textField1.getText());
                    pst.setString(2, textField2.getText());
                    pst.setString(3, textField4.getText());
                    pst.executeUpdate();
                    con.close();
                    this.dispose();
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
