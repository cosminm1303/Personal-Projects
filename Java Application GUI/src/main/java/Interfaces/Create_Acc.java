package Interfaces;

import connection.DatabaseConnection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Create_Acc extends JFrame implements ActionListener {
    private JPanel panel1;
    private JLabel titleca;
    private JTextField textField1;
    private JTextField textField2;
    private JPasswordField passwordField1;
    private JRadioButton femaleRadioButton;
    private JRadioButton maleRadioButton;
    private JTextField textField3;
    private JTextField textField4;
    private JTextArea adressBox;
    private JButton closeButton;
    private JButton createButton;
    private JLabel firstNameLabel;
    private JLabel lastNameLabel;
    private JLabel passwordLabel;
    private JLabel sexLabel;
    private JLabel phoneLabel;
    private JLabel emailLabel;
    private JLabel adressLabel;
    private String gender;

    public Create_Acc() {
        this.setContentPane(this.getPanel1());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();
        this.setLocationRelativeTo(null);
        femaleRadioButton.addActionListener(this);
        maleRadioButton.addActionListener(this);
        closeButton.addActionListener(this);
        createButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == closeButton) {
            this.dispose();
        }
        if (e.getSource() == maleRadioButton) {
            if (maleRadioButton.isSelected()) {
                femaleRadioButton.setSelected(false);
            }
        }
        if (e.getSource() == femaleRadioButton) {
            if (femaleRadioButton.isSelected()) {
                maleRadioButton.setSelected(false);
            }
        }
        if (e.getSource() == createButton) {
            try {
                int ok = 0;
                String st = new String(passwordField1.getPassword());
                Connection con = DatabaseConnection.getInstance().getConnection();
                //Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/studentdata","root","password");
                PreparedStatement pst = con.prepareStatement("INSERT INTO studentdata(firstname,lastname,password,gender,phone,email,adress)VALUE(?,?,?,?,?,?,?)");
                if (!textField1.getText().isEmpty() && !textField2.getText().isEmpty() && !textField3.getText().isEmpty() && !textField4.getText().isEmpty() && passwordField1.getPassword().length != 0 && (femaleRadioButton.isSelected() ^ maleRadioButton.isSelected()) && !adressBox.getText().isEmpty()) {
                    if (maleRadioButton.isSelected()) {
                        gender = "male";
                    } else {
                        gender = "female";
                    }

                    for (char c : textField3.getText().toCharArray()) {
                        if (c >= 48 && c <= 57) {
                            ok = 1;
                        } else {
                            ok = 0;
                        }
                    }
                    if (ok == 1) {
                        pst.setString(5, textField3.getText());
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Phone field must contain numbers",
                                "Warning",
                                JOptionPane.WARNING_MESSAGE);
                    }
                    pst.setString(1, textField2.getText());
                    pst.setString(2, textField1.getText());
                    pst.setString(3, st);
                    pst.setString(4, gender);
                    pst.setString(6, textField4.getText());
                    pst.setString(7, textField2.getText());
                    if (ok == 1) {
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

    public JPanel getPanel1() {
        return panel1;
    }


}
