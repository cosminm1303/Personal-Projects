package Interfaces;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home extends JFrame implements ActionListener {
    public static boolean verify;
    private JPanel home;
    private JLabel photo;
    private JButton studentsButton;
    private JButton teachersButton;

    public Home() {
        this.setContentPane(this.getHome());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();
        this.setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon("src/main/resources/logo_utcn.gif");
        photo.setIcon(icon);
        studentsButton.addActionListener(this);
        teachersButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == studentsButton) {
            verify = true;
            new Login_interf();
            this.dispose();
        }
        if (e.getSource() == teachersButton) {
            verify = false;
            new Login_interf();
            this.dispose();
        }
    }

    public JPanel getHome() {
        return home;
    }

}
