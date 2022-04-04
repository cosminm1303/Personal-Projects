package Interfaces;

import connection.DatabaseConnection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class ChangeData extends JFrame implements ActionListener {
    private final String userc;
    private final String passc;
    private JPanel panprinc;
    private JPanel jPanel;
    private JLabel label1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextArea textArea1;
    private JButton submitButton;
    private JButton closeButton;

    public ChangeData(String user, String pass) {
        passc = pass;
        userc = user;
        this.setContentPane(this.getPanprinc());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();
        this.setLocationRelativeTo(null);
        closeButton.addActionListener(this);
        submitButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String[] res = userc.split("[.]", 2);
        int verify = 0;
        if (e.getSource() == closeButton) {
            this.dispose();
        }
        if (e.getSource() == submitButton) {
            if (!textField1.getText().isEmpty() || !textField2.getText().isEmpty() || !textArea1.getText().isEmpty()) {
                if (!textField1.getText().isEmpty()) {
                    try {
                        Connection con = DatabaseConnection.getInstance().getConnection();
                        //Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentdata", "root", "password");
                        PreparedStatement st = con.prepareStatement("UPDATE studentdata SET email=? WHERE firstname=? AND lastname=? AND password=?");
                        st.setString(1, textField1.getText());
                        st.setString(2, res[0]);
                        st.setString(3, res[1]);
                        st.setString(4, passc);

                        st.executeUpdate();
                        con.close();

                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }
                if (!textField2.getText().isEmpty()) {
                    int ok = 0;
                    for (char c : textField2.getText().toCharArray()) {
                        if (c >= 48 && c <= 57) {
                            ok = 1;
                        } else {
                            ok = 0;
                        }
                    }
                    if (ok == 1) {
                        try {
                            Connection con = DatabaseConnection.getInstance().getConnection();
                            //Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentdata", "root", "password");
                            PreparedStatement st = con.prepareStatement("UPDATE studentdata SET phone=? WHERE firstname=? AND lastname=? AND password=?");
                            st.setString(1, textField2.getText());
                            st.setString(2, res[0]);
                            st.setString(3, res[1]);
                            st.setString(4, passc);
                            st.executeUpdate();
                            con.close();

                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                        }

                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Phone field must contain numbers",
                                "Warning",
                                JOptionPane.WARNING_MESSAGE);
                        verify = 1;
                    }

                }
                if (!textArea1.getText().isEmpty()) {
                    try {
                        Connection con = DatabaseConnection.getInstance().getConnection();
                        //Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentdata", "root", "password");
                        PreparedStatement st = con.prepareStatement("UPDATE studentdata SET adress=? WHERE firstname=? AND lastname=? AND password=? ");
                        st.setString(1, textArea1.getText());
                        st.setString(2, res[0]);
                        st.setString(3, res[1]);
                        st.setString(4, passc);
                        st.executeUpdate();
                        con.close();

                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }
                if (verify == 0) {
                    this.dispose();
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Complete at least one field",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    public JPanel getPanprinc() {
        return panprinc;
    }
}
