package view;

import business.ReservationManager;
import core.Helper;
import entity.Reservation;
import entity.Room;

import javax.swing.*;

public class CustomerInfoUpdateView extends Layout{

    private JTextField fld_customer_name;
    private JTextField fld_customer_tc;
    private JTextField fld_customer_mail;
    private JTextField fld_customer_phone;
    private JButton btn_update;
    private JPanel container;
    private JLabel lbl_title;
    private JPanel pnl_center;
    private JLabel lbl_customer_ame;
    private JLabel lbl_customer_tc;
    private JLabel lbl_customer_mail;
    private JLabel lbl_customer_phone;
    private ReservationManager reservationManager = new ReservationManager();
    public CustomerInfoUpdateView(Reservation reservation){
        this.add(container);
        this.guiInitilaze(400, 400);

        btn_update.addActionListener(e -> { //Günceller
            JTextField[] fieldList = {fld_customer_mail, fld_customer_name, fld_customer_tc, fld_customer_phone};
            if (Helper.isFieldListEmpty(fieldList)) {
                Helper.showMsg("fill");
            } else {
                boolean result;
                reservation.setGuest_name(this.fld_customer_name.getText());
                reservation.setGuest_mail(this.fld_customer_mail.getText());
                reservation.setGuest_citizen_id(this.fld_customer_tc.getText());
                reservation.setGuest_phone(this.fld_customer_phone.getText());
                if(reservation.getId() != 0) {
                    result = reservationManager.update(reservation);
                    Helper.showMsg("done");
                    dispose();

                }else {
                    Helper.showMsg("error");
                    result = false;
                }
            }
        });
    }
}
