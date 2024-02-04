package view;

import business.UserManager;
import core.Helper;
import entity.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends Layout {
    private JPanel container;
    private JLabel lbl_welcome;
    private JLabel lbl_welcome2;
    private JPanel w_top;
    private JTextField fld_username;
    private JPasswordField fld_password;
    private JButton btn_login;
    private JLabel lbl_username;
    private JLabel lbl_password;
    private JPanel w_top2;
    private final UserManager userManager;

    public LoginView(){
        this.userManager = new UserManager();
        this.add(container);
        this.guiInitilaze(400,400);
        btn_login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {   //DEĞERLENDİRME FORMU 9
                if (Helper.isFieldListEmpty(new JTextField[] {fld_password,fld_username})){
                    Helper.showMsg("fill");
                }else {
                    User loginUser = userManager.findByLogin(fld_username.getText(), fld_password.getText());

                    if (loginUser == null) {
                        Helper.showMsg("notFound");
                    } else if (loginUser.getRole().equals("ADMIN")) {
                        AdminView adminView = new AdminView(loginUser);
                        dispose();
                    }  else {
                        EmployeeView employeeView = new EmployeeView();
                        dispose();
                    }

                }
            }
        });

    }
}

