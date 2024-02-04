package view;

import business.*;
import core.Helper;
import entity.Pension;
import entity.Reservation;
import entity.Room;
import entity.Season;

import javax.swing.*;

import javax.swing.table.DefaultTableModel;

import java.awt.event.*;

import java.util.ArrayList;
import java.util.Objects;

public class EmployeeView extends Layout{
    private JPanel container;
    private JTabbedPane tbd_employee;
    private JButton btn_exit;
    private JPanel pnl_hotel;
    private JPanel pnl_rooms;
    private JPanel pnl_reservation;
    private JLabel lbl_welcome;
    private JTable tbl_reservation;
    private JTable tbl_hotel;
    private JTable tbl_season;
    private JTable tbl_pension;
    private JButton btn_hotel_save;
    private JTextField fld_hotelname;
    private JTextField fld_country;
    private JTextField fld_startdate;
    private JTextField fld_finishdate;
    private JTextField fld_adult;
    private JTextField fld_child;
    private JButton btn_room_search;
    private JButton btn_reset;
    private JTable tbl_room;
    private JButton btn_room_save;
    private JLabel lbl_hotelname;
    private JLabel lbl_country;
    private JLabel lbl_startdate;
    private JLabel lbl_finishdate;
    private JLabel lbl_adult;
    private JLabel lbl_child;
    private JScrollPane fld_room;
    private JPanel pnl_room;
    private Object[] col_hotel;
    private Object[] col_season;
    private Object[] col_pension;
    private Object[] col_room;
    private Object[] col_reservation;
    private JPopupMenu hotel_menu;
    private JPopupMenu reservation_menu;
    private JPopupMenu room_menu;
    private HotelManager hotelManager = new HotelManager(); //bunu aşağıda değil yukarıda yazınca hoteller listelendi
    private PensionManager pensionManager = new PensionManager();
    private SeasonManager seasonManager = new SeasonManager();
    private RoomManager roomManager = new RoomManager();
    private ReservationManager reservationManager = new ReservationManager();
    DefaultTableModel mdl_employee_table = new DefaultTableModel();
    DefaultTableModel mdl_pension_table = new DefaultTableModel();
    DefaultTableModel mdl_season_table = new DefaultTableModel();
    DefaultTableModel mdl_room_table = new DefaultTableModel();
    DefaultTableModel mdl_reservation_table = new DefaultTableModel();
    private HotelSaveView hotelSaveView;
    private RoomSaveView roomSaveView;
    private ReservationView reservationView;
    public EmployeeView() {

        this.add(container);
        this.guiInitilaze(1300, 550);
        loadComponent(); //çıkış yapı çalıştırır
        loadHotelTable(null);
        loadPensionTable(null);
        loadSeasonTable(null);
        loadRoomTable(null);
        loadRoomComponent();
        loadReservationTable(null);
        loadReservationComponent();


        btn_hotel_save.addActionListener(e -> { // OTEL EKLE SAYFASINA YÖNLENDİRİR
           new HotelSaveView();

        });

        btn_room_save.addActionListener(e -> { // ODA EKLE SAYFASINA YÖNLENDİRİR
            new RoomSaveView();
        });

        btn_room_search.addActionListener(e -> { // ODA FİLTRELEME DEĞERLENDİRME FORMU 8
            ArrayList<Room> roomListBySearch = null;
            if(!Objects.equals(fld_child.getText(), "") && !Objects.equals(fld_adult.getText(), "")) {

                int guest = Integer.parseInt(fld_child.getText()) + Integer.parseInt(fld_adult.getText());
                try {
                    roomListBySearch = this.roomManager.searchForTable(
                            fld_hotelname.getText(),
                            fld_country.getText(),
                            fld_startdate.getText(),
                            fld_finishdate.getText(),
                            guest
                    );
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                ArrayList<Object[]> roomRowListBySearch = this.roomManager.getForTable(this.col_room.length, roomListBySearch);
                loadRoomTable(roomRowListBySearch);

            }else {
                JOptionPane.showMessageDialog(null, "Giriş / çıkış tarihlerinizi ve konaklayacak yetişkin / çocuk sayısını eksiksiz giriniz.");
            }

        });

        btn_reset.addActionListener(e -> { //ODA FİLTRELEMEDE TÜM KUTULARI TEMİZLER
            fld_hotelname.setText("");
            fld_country.setText("");
            fld_startdate.setText("");
            fld_finishdate.setText("");
            fld_adult.setText("");
            fld_child.setText("");
            loadRoomTable(null);
        });
    }
    private void loadComponent() { //ÇIKIŞ BUTONUNDAN ÇIKIŞ YAPAR
        btn_exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginView loginView = new LoginView();
            }
        });
    }

    public void loadHotelTable (ArrayList<Object[]> hotelList) { //DEĞERLENDİRME FORMU 8
        this.col_hotel = new Object[] {"ID", "Otel Adı", "Adres", "Mail","Telefon","Yıldız","Otopark","Wifi", "Havuz","Fitness","Concierge","Spa","Oda Servisi"};
        if(hotelList == null){
            hotelList = this.hotelManager.getForTable(col_hotel.length,this.hotelManager.findAll()); // içini doldurduk
        }
        this.createTable(mdl_employee_table, this.tbl_hotel, col_hotel, hotelList); //guiye setledik

        tableRowSelect(this.tbl_hotel);
        this.hotel_menu = new JPopupMenu(); //Otellere sağ tıklayınca seçenek çıkarır
        this.hotel_menu.add("Pansiyon Ekle").addActionListener(e -> {
            int selectedHotelId = this.getTableSelectedRow(tbl_hotel,0);

            PensionView pensionView = new PensionView(selectedHotelId);
            pensionView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadPensionTable(null);
                }
            });
            //PANSİYONLARI LİSTELEME
            ArrayList<Pension> pensionListBySearch = this.pensionManager.searchForTable(selectedHotelId);
            ArrayList<Object[]> pensionRowListBySearch = this.pensionManager.getForTable(this.col_pension.length, pensionListBySearch);
            loadPensionTable(pensionRowListBySearch);
        });


        this.hotel_menu.add("Sezon Ekle").addActionListener(e -> {
            int selectedRow = this.getTableSelectedRow(tbl_hotel, 0);
            SeasonView seasonView = new SeasonView(selectedRow);
            seasonView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadSeasonTable(null);
                }
            });

            //SEZONLARI LİSTELEME
            ArrayList<Season> seasonListBySearch = this.seasonManager.searchForTable(selectedRow);
            ArrayList<Object[]> seasonRowListBySearch = this.seasonManager.getForTable(this.col_season.length, seasonListBySearch);
            loadSeasonTable(seasonRowListBySearch);
        });
        this.tbl_hotel.setComponentPopupMenu(hotel_menu); //bunu unutursak tabloya setlemez göremeyiz
    }
    public void loadRoomComponent(){
        tableRowSelect(this.tbl_room);
        this.room_menu = new JPopupMenu();
        this.room_menu.add("Rezervasyon Oluştur").addActionListener(e -> { //DEĞERLENDİRME FORMU 20
            JTextField[] fieldList = {fld_finishdate, fld_startdate, fld_adult, fld_child};
            if (Helper.isFieldListEmpty(fieldList)) {
                Helper.optionPaneTR();
                JOptionPane.showMessageDialog(null, "Giriş / çıkış tarihlerini ve konaklayacak yetişkin / çocuk sayısını eksiksiz giriniz.");
            } else {
                int selectId = this.getTableSelectedRow(tbl_room,0);
                Room selectedRoom = this.roomManager.getById(selectId);
                ReservationView reservationView = new ReservationView(
                        selectedRoom,
                        fld_startdate.getText(),
                        fld_finishdate.getText(),
                        fld_adult.getText(),
                        fld_child.getText()
                );
                reservationView.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        loadReservationTable(null);
                        loadRoomTable(null);
                    }
                });

            }
        });
        this.tbl_room.setComponentPopupMenu(room_menu); //bunu unutursak tabloya setlemez göremeyiz
    }
    public void loadReservationComponent(){ //DEĞERLENDİRME FORMU 8
        tableRowSelect(this.tbl_reservation);
        this.reservation_menu = new JPopupMenu();
        this.reservation_menu.add("Misafir Bilgilerini Güncelle").addActionListener(e -> { //DEĞERLENDİRME FORMU 21
            int selectedRow = this.getTableSelectedRow(tbl_reservation,0);
            CustomerInfoUpdateView customerView = new CustomerInfoUpdateView(this.reservationManager.getById(selectedRow));

            customerView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadReservationTable(null); //üst pencere kapanınca tabloyu güncelliyor.
                }
            });


        });
        this.reservation_menu.add("Rezervasyonu İptal Et").addActionListener(e -> {  //DEĞERLENDİRME FORMU 22
            if (Helper.confirm("sure")){
                int selectId = this.getTableSelectedRow(tbl_reservation,0);

                if (this.reservationManager.getById(selectId) != null){
                    reservationManager.increaseStock(selectId); //İPTAL SONRASI STOĞU ARTTIRIR DEĞERLENDİRME FORMU 23
                    this.reservationManager.delete(selectId);
                    loadReservationTable(null);
                    loadRoomTable(null);
                    Helper.showMsg("done");

                }else {
                    Helper.showMsg("error");
                }
            }
        });
        this.tbl_reservation.setComponentPopupMenu(reservation_menu); //bunu unutursak tabloya setlemez göremeyiz
    }

    public void loadPensionTable (ArrayList<Object[]> pensionList) { //pansiyon tablosu oluşturma
        this.col_pension = new Object[] {"ID", "Otel ID", "Pansiyon Tipi"};
        if(pensionList == null){
            pensionList = this.pensionManager.getForTable(col_pension.length,this.pensionManager.findAll()); // içini doldurduk
        }
        this.createTable(mdl_pension_table, this.tbl_pension, col_pension, pensionList); //guiye setledik
    }

    public void loadSeasonTable (ArrayList<Object[]> seasonList) { //sezon tablosu oluşturma
        this.col_season = new Object[] {"ID", "Otel ID", "Sezon Başlangıç Tarihi", "Sezon Bitiş Tarihi"};
        if(seasonList == null){
            seasonList = this.seasonManager.getForTable(col_season.length,this.seasonManager.findAll()); // içini doldurduk
        }
        this.createTable(mdl_season_table, this.tbl_season, col_season, seasonList); //guiye setledik
    }

    public void loadRoomTable (ArrayList<Object[]> roomList) { //oda tablosu oluşturma
        this.col_room = new Object[] {"ID", "Pansiyon ID", "Sezon ID", "Oda Tipi","Stok","Yetişkin Fiyat","Çocuk Fiyat","Yatak Kapasitesi", "Metrekare","TV","Minibar","Oyun Konsolu","Kasa","Projeksiyon"};
        if(roomList == null){
            roomList = this.roomManager.getForTable(col_room.length,this.roomManager.findAll()); // içini doldurduk
        }
        this.createTable(mdl_room_table, this.tbl_room, col_room, roomList); //guiye setledik

    }
    public void loadReservationTable (ArrayList<Object[]> reservationList) { //rezervasyon tablosu oluşturma
        this.col_reservation = new Object[] {"ID", "Otel ID", "Giriş Tarihi", "Çıkış Tarihi","Toplam Tutar","Misafir Sayısı","Misafir Adı","Misafir Kimlik Numarası", "Mail","Telefon"};
        if(reservationList == null){
            reservationList = this.reservationManager.getForTable(col_reservation.length,this.reservationManager.findAll()); // içini doldurduk
        }
        this.createTable(mdl_reservation_table, this.tbl_reservation, col_reservation, reservationList); //guiye setledik
    }
}
