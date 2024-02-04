package view;

import business.HotelManager;
import business.PensionManager;
import business.ReservationManager;
import business.RoomManager;
import core.Helper;
import entity.Hotel;
import entity.Pension;
import entity.Reservation;
import entity.Room;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReservationView extends Layout {
    private JPanel container;
    private JTextField fld_hotelname;
    private JTextField fld_country;
    private JTextField fld_star;
    private JCheckBox cb_carpark;
    private JCheckBox cb_wifi;
    private JCheckBox cb_pool;
    private JCheckBox cb_fitness;
    private JCheckBox cb_concierge;
    private JCheckBox cb_spa;
    private JCheckBox cb_roomservice;
    private JTextField fld_roomtype;
    private JTextField fld_pension;
    private JTextField fld_startdate;
    private JTextField fld_finishdate;
    private JTextField fld_totalprice;
    private JTextField fld_bed;
    private JTextField fld_roomsize;
    private JCheckBox cb_tv;
    private JCheckBox cb_minibar;
    private JCheckBox cb_game;
    private JCheckBox cb_projection;
    private JCheckBox cb_cashbox;
    private JTextField fld_customersize;
    private JTextField fld_customerName;
    private JTextField fld_customerTC;
    private JTextField fld_customerMail;
    private JTextField fld_customerPhone;
    private JButton btn_reservation;
    private JButton btn_exit;
    private JLabel lbl_hotelname;
    private JLabel lbl_country;
    private JLabel lbl_star;
    private JPanel pnl_hotelinfo;
    private JPanel pnl_roominfo;
    private JPanel pnl_customer;
    private JLabel pnl_roomtype;
    private JLabel lbl_pension;
    private JLabel lbl_bed;
    private JLabel lbl_startdate;
    private JLabel lbl_finishdate;
    private JLabel lbl_roomsize;
    private JLabel lbl_totalprice;
    private JLabel lbl_customersize;
    private JLabel lbl_customerName;
    private JLabel lbl_customerTC;
    private JLabel lbl_customerMail;
    private JLabel lbl_customerPhone;
    DefaultTableModel mdl_reservation_table = new DefaultTableModel();
    private HotelManager hotelManager = new HotelManager();
    private RoomManager roomManager= new RoomManager();
    private PensionManager pensionManager = new PensionManager();
    private ReservationManager reservationManager = new ReservationManager();
    private EmployeeView employeeView;
    private double totalPrice;

    public ReservationView(Room room, String start_date, String finish_date, String adult, String child){
        this.add(container);
        this.guiInitilaze(700, 700);
        Hotel selectedHotel = this.hotelManager.getById(room.getHotel_id());
        Room selectedRoom = this.roomManager.getById(room.getId());
        Pension selectedPension = this.pensionManager.getById(room.getPension_id());

        this.fld_hotelname.setText(selectedHotel.getName()); //içindekiler default dolu gelecek
        this.fld_country.setText(selectedHotel.getAddress());
        this.fld_star.setText(selectedHotel.getStar());
        this.fld_roomtype.setText(selectedRoom.getType());
        this.fld_bed.setText(String.valueOf(selectedRoom.getBed_capacity()));
        this.fld_roomsize.setText(String.valueOf(selectedRoom.getSquare_meter()));
        this.fld_pension.setText(selectedPension.getType());
        this.cb_carpark.setSelected(selectedHotel.isCar_park());
        this.cb_concierge.setSelected(selectedHotel.isConcierge());
        this.cb_roomservice.setSelected(selectedHotel.isRoom_service());
        this.cb_spa.setSelected(selectedHotel.isSpa());
        this.cb_fitness.setSelected(selectedHotel.isFitness());
        this.cb_pool.setSelected(selectedHotel.isPool());
        this.cb_wifi.setSelected(selectedHotel.isWifi());
        this.fld_startdate.setText(start_date);
        this.fld_finishdate.setText(finish_date);
        this.cb_tv.setSelected(selectedRoom.isTelevision());
        this.cb_minibar.setSelected(selectedRoom.isMinibar());
        this.cb_game.setSelected(selectedRoom.isGame_console());
        this.cb_projection.setSelected(selectedRoom.isProjection());
        this.cb_cashbox.setSelected(selectedRoom.isCash_box());
        //DEĞERLENDİRME FORMU 17
        int totalCustomer = Integer.parseInt(adult) + Integer.parseInt(child); // toplam misafir sayısı
        this.fld_customersize.setText(String.valueOf(totalCustomer));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //String günlerin farkını bulma
        LocalDate date1  = LocalDate.parse(start_date, formatter);
        LocalDate date2 = LocalDate.parse(finish_date, formatter);
        long differenceInDays = date2.toEpochDay() - date1.toEpochDay();

        totalPrice = ((room.getAdult_price()*Integer.parseInt(adult))+ (room.getChild_price()*Integer.parseInt(child)))*differenceInDays;
        this.fld_totalprice.setText(String.valueOf(totalPrice)); //toplam tutar hesaplama

        btn_exit.addActionListener(e -> { //ÇIKIŞ YAP
            dispose();
        });

        btn_reservation.addActionListener(e -> { //REZERVASYON OLUŞTURUR DEĞERLENDİRME FORMU 18
            JTextField[] fieldList = {fld_customerName,fld_customerMail,fld_customerTC,fld_customerPhone};
            if (Helper.isFieldListEmpty(fieldList)) {
                Helper.showMsg("fill");
            } else {
                boolean result;
                Reservation reservation1 = new Reservation(room.getId(),
                        start_date,
                        finish_date,
                        totalPrice,
                        totalCustomer,
                        fld_customerName.getText(),
                        fld_customerTC.getText(),
                        fld_customerMail.getText(),
                        fld_customerPhone.getText());
                result = reservationManager.save(reservation1);

                roomManager.decreaseStock(room.getId()); // REZERVASYON OLUŞTURULUNCA STOĞU AZALTIR DEĞERLENDİRME FORMU 19

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
