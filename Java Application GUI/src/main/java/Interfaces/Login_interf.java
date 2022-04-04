package Interfaces;

import connection.DatabaseConnection;
import connection.DatabaseConnectionT;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Login_interf extends JFrame implements ActionListener {

    private final String user = "admin";
    private final String pass = "pass";
    private JPanel Panel1;
    private JLabel login;
    private JTextField textField2;
    private JPasswordField passwordField1;
    private JPanel JPanelLog;
    private JLabel lImage;
    private JLabel passImage;
    private JButton LOGINButton;
    private JButton createNewAccountButton;
    private JButton button1;


    public Login_interf() {
        this.setContentPane(this.getJPanelLog());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();
        this.setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon("src/main/resources/lock-30.png");
        ImageIcon icon1 = new ImageIcon("src/main/resources/user.png");
        passImage.setIcon(icon);
        lImage.setIcon(icon1);
        if (!Home.verify) {
            LOGINButton.addActionListener(this);
            createNewAccountButton.setVisible(false);
        } else {
            createNewAccountButton.addActionListener(this);
            LOGINButton.addActionListener(this);
        }
        button1.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button1) {
            new Home();
            this.dispose();
        }
        if (e.getSource() == createNewAccountButton) {
            new Create_Acc();
        }
        if (Home.verify) {
            if (e.getSource() == LOGINButton) {
                String[] res = textField2.getText().split("[.]", 2);
                String str = new String(passwordField1.getPassword());
                int ok = 0;
                if (!textField2.getText().isEmpty() && passwordField1.getPassword().length != 0) {
                    if (res.length > 1) {
                        try {
                            Connection con = DatabaseConnection.getInstance().getConnection();
                            //Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentdata", "root", "password");
                            Statement st = con.createStatement();
                            ResultSet rs = st.executeQuery("SELECT * FROM studentdata");
                            while (rs.next()) {
                                if (res[0].equals(rs.getString(2)) && res[1].equals(rs.getString(3)) && str.equals(rs.getString(4))) {
                                    ok = 1;
                                }
                            }
                            if (ok == 1) {
                                new StudLog(textField2.getText(), str);
                                this.dispose();
                            } else {
                                JOptionPane.showMessageDialog(this,
                                        "Incorrect username or password",
                                        "Warning",
                                        JOptionPane.WARNING_MESSAGE);
                            }

                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                        }
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Incorrect username or password",
                                "Warning",
                                JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Introduce username and password",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        } else {
            if (e.getSource() == LOGINButton) {
                String[] rest = textField2.getText().split("[.]", 2);
                String strt = new String(passwordField1.getPassword());
                int ok = 0;
                if (!textField2.getText().isEmpty() && passwordField1.getPassword().length != 0) {
                    try {
                        Connection con = DatabaseConnectionT.getInstance().getConnection();
                        //Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/teachers", "root", "password");
                        Statement st = con.createStatement();
                        ResultSet rs = st.executeQuery("SELECT * FROM teachers");
                        if (strt.equals(pass) && textField2.getText().equals(user)) {
                            new AdminLog();
                            this.dispose();
                        } else if (rest.length > 1) {
                            while (rs.next()) {
                                if (rest[0].equals(rs.getString(2)) && rest[1].equals(rs.getString(3)) && strt.equals(rs.getString(4))) {
                                    ok = 1;
                                }
                            }
                            if (ok == 1) {
                                new TeacherLog();
                                this.dispose();
                            } else {
                                JOptionPane.showMessageDialog(this,
                                        "Incorrect username or password",
                                        "Warning",
                                        JOptionPane.WARNING_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(this,
                                    "Incorrect username or password",
                                    "Warning",
                                    JOptionPane.WARNING_MESSAGE);
                        }


                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Introduce username and password",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }

    public JPanel getJPanelLog() {
        return JPanelLog;
    }

}
