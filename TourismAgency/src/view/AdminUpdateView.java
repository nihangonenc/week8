package view;

import business.UserManager;
import core.ComboItem;
import core.Helper;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminUpdateView extends Layout{
    private JPanel container;
    private JTextField fld_user;
    private JTextField fld_password;
    private JComboBox cmb_rol;
    private JButton btn_update;
    private JLabel lbl_user;
    private JLabel lbl_password;
    private JLabel lbl_role;
    private User user;
    private UserManager userManager;
    DefaultTableModel mdl_userUpdate_table = new DefaultTableModel();


    public AdminUpdateView(User user){
        UserManager userManager = new UserManager();
        this.user = user;

        this.add(container);
        this.guiInitilaze(500, 500);
        this.fld_user.setText(this.user.getUsername()); //3 satır; default olarak güncelleme tablosu dolu gelecek
        this.fld_password.setText(this.user.getPassword());
        this.cmb_rol.setSelectedItem(this.user.getRole());

        btn_update.addActionListener(e -> {

            if (Helper.isFieldEmpty(fld_user) || Helper.isFieldEmpty(fld_password)) {
                Helper.showMsg("fill");
            } else {
                boolean result;
                this.user.setUsername(fld_user.getText());
                this.user.setPassword(fld_password.getText());
                this.user.setRole((String) cmb_rol.getSelectedItem());
                result = userManager.update(this.user);

                if (result) {
                    Helper.showMsg("done");
                    dispose();
                } else {
                    Helper.showMsg("error");
                }
            }

        });
    }

}

