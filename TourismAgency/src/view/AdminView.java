package view;

import business.UserManager;
import core.ComboItem;
import core.Helper;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class AdminView extends Layout {
    private JPanel container;
    private JLabel lbl_welcome;
    private JTabbedPane tbd_admin;
    private JTextField fld_username;
    private JPasswordField fld_password;
    private JComboBox cmb_rol;
    private JButton btn_save;
    private JComboBox cmb_userrole;
    private JButton btn_search;
    private JLabel lbl_username;
    private JLabel lbl_password;
    private JLabel lbl_role;
    private JButton btn_exit;
    private JTable tbl_users;
    private JScrollPane scr_users;
    private JLabel lbl_top;
    private JLabel lbl_usersave;
    private JPopupMenu user_menu;
    private User user;
    private DefaultTableModel mdl_admin_table = new DefaultTableModel();
    private UserManager userManager;
    private Object[] col_user;

    public AdminView(User user) {
        this.userManager = new UserManager();
        this.user = new User();
        this.add(container);
        this.guiInitilaze(1000, 500);
        this.user = user;
        loadUserTable(null);
        loadUserComponent();
        loadComponent();

        this.lbl_welcome.setText("Hoş geldiniz " + this.user.getUsername());

        btn_save.addActionListener(e -> { //Yeni kullanıcı kayıt ediyor. -DEĞERLENDİRME FORMU 7
            User user1 = new User();
            if (Helper.isFieldEmpty(fld_username) || Helper.isFieldEmpty(fld_password)) {
                Helper.showMsg("fill");
            } else {
                boolean result;
                user1.setUsername(fld_username.getText());
                user1.setPassword(fld_password.getText());
                user1.setRole((String) cmb_rol.getSelectedItem());
                result = userManager.save(user1);

                if (result) {
                    Helper.showMsg("done");
                    loadUserTable(null); //Bu satırı eklediğimde anında tabloyu güncelledi
                } else {
                    Helper.showMsg("error");
                }
            }
        });

        this.btn_search.addActionListener(e -> { //Listeleme
            ArrayList<User> userListBySearch = this.userManager.searchForTable(cmb_userrole.getSelectedItem().toString());
            ArrayList<Object[]> userRowListBySearch = this.userManager.getForTable(this.col_user.length, userListBySearch);
            loadUserTable(userRowListBySearch);
        });
    }
    private void loadComponent() { //ÇIKIŞ YAPAR
        btn_exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginView loginView = new LoginView();
            }
        });
    }
    public void loadUserComponent() { //popupı yönetir
        tableRowSelect(this.tbl_users);
        this.user_menu = new JPopupMenu(); // satıra sağ tıklayınca 2 seçenek çıkacak

       this.user_menu.add("Güncelle").addActionListener(e -> {                     //DEĞERLENDİRME FORMU 7
           int selectedRow= this.getTableSelectedRow(tbl_users,0);
           AdminUpdateView adminUpdateView = new AdminUpdateView(this.userManager.getById(selectedRow)); // seçili id'i databaseden alıyor
           adminUpdateView.addWindowListener(new WindowAdapter() {
               @Override
               public void windowClosed(WindowEvent e) {
                   loadUserTable(null); //üst pencere kapanınca tabloyu güncelliyor.
               }
           });
       });

       this.user_menu.add("Sil").addActionListener(e -> {                  //DEĞERLENDİRME FORMU 7
           if (Helper.confirm("sure")){
               int selectId = this.getTableSelectedRow(tbl_users,0);
               if (this.userManager.delete(selectId)){
                    Helper.showMsg("done");
                    loadUserTable(null); //bu satırı ekleyince tabloyu anında güncelledi
               }else {
                    Helper.showMsg("error");
               }
           }
       });
       this.tbl_users.setComponentPopupMenu(user_menu); //bunu unutursak tabloya setlemez göremeyiz
    }
    public void loadUserTable (ArrayList<Object[]> userList) {
        this.col_user = new Object[] {"ID", "Kullanıcı Adı", "Şifre", "Rol"}; // columnlarını belirledik
        if(userList == null){
            userList = this.userManager.getForTable(col_user.length,this.userManager.findAll()); // içini doldurduk
        }
        this.createTable(mdl_admin_table, this.tbl_users, col_user, userList); //guiye setledik
    }
}
