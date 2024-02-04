package view;

import business.HotelManager;
import business.UserManager;
import core.Helper;
import entity.Hotel;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HotelSaveView extends Layout{
    private JPanel container;
    private JTextField fld_hotel_name;
    private JTextField fld_mail;
    private JTextField fld_phone;
    private JComboBox cmb_star;
    private JTextField fld_address;
    private JCheckBox cb_car_park;
    private JCheckBox cb_wifi;
    private JCheckBox cb_pool;
    private JCheckBox cb_fitness;
    private JCheckBox cb_concierge;
    private JCheckBox cb_spa;
    private JCheckBox cb_room_service;
    private JButton btn_save;
    private JButton btn_exit;
    private JLabel fld_welcome;
    private JLabel lbl_hotel_name;
    private JLabel lbl_mail;
    private JLabel lbl_phone;
    private JLabel lbl_address;
    private JLabel lbl_star;
    private HotelManager hotelManager;
    private Hotel hotel;
    private EmployeeView employeeView;
    DefaultTableModel mdl_hotelSave_table = new DefaultTableModel();
    public HotelSaveView (){

        HotelManager hotelManager = new HotelManager();
        Hotel hotel1 = new Hotel();

        this.add(container);
        this.guiInitilaze(500, 400);
        loadComponent();
        btn_save.addActionListener(new ActionListener() { //OTEL KAYDETE BASINCA ÇALIŞIYOR
            @Override
            public void actionPerformed(ActionEvent e) { //DEĞERLENDİRME FORMU 10
                JTextField[] fieldList = {fld_hotel_name, fld_phone, fld_address, fld_mail};
                if (Helper.isFieldListEmpty(fieldList)) {
                    Helper.showMsg("fill");
                } else {
                    boolean result;
                    hotel1.setName(fld_hotel_name.getText());
                    hotel1.setAddress(fld_address.getText());
                    hotel1.setMail(fld_mail.getText());
                    hotel1.setPhone(fld_phone.getText());
                    hotel1.setStar(cmb_star.getSelectedItem().toString());
                    hotel1.setWifi(Boolean.parseBoolean(cb_wifi.getText()));
                    hotel1.setPool(Boolean.parseBoolean(cb_pool.getText()));
                    hotel1.setFitness(Boolean.parseBoolean(cb_fitness.getText()));
                    hotel1.setConcierge(Boolean.parseBoolean(cb_concierge.getText()));
                    hotel1.setSpa(Boolean.parseBoolean(cb_spa.getText()));
                    hotel1.setRoom_service(Boolean.parseBoolean(cb_room_service.getText()));

                    result = hotelManager.save(hotel1);

                    if (result) {
                        Helper.showMsg("done");
                        employeeView.loadHotelTable(null);

                    } else {
                        Helper.showMsg("error");
                    }
                }

            }
        });
    }
    private void loadComponent() { //ÇIKIŞ BUTONUNDAN ÇIKIŞ YAPAR
        btn_exit.addActionListener(e -> dispose());
    } //çıkış yap

}
