package Interfaces;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminLog extends JFrame implements ActionListener {
    private JPanel teach;
    private JPanel panPrinc;
    private JButton manageStudentsButton;
    private JButton manageCoursesButton;
    private JButton manageTeachersButton;
    private JButton button4;

    public AdminLog() {
        this.setContentPane(this.getPanPrinc());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();
        this.setLocationRelativeTo(null);
        button4.addActionListener(this);
        manageStudentsButton.addActionListener(this);
        manageCoursesButton.addActionListener(this);
        manageTeachersButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button4) {
            new Home();
            this.dispose();
        }
        if (e.getSource() == manageStudentsButton) {
            new ManageStud();
        }
        if (e.getSource() == manageCoursesButton) {
            new ManageCourses();
        }
        if (e.getSource() == manageTeachersButton) {
            new ManageTeachers();
        }
    }

    public JPanel getPanPrinc() {
        return panPrinc;
    }
}
