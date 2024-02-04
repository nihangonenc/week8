package view;

import business.HotelManager;
import business.PensionManager;
import business.RoomManager;
import business.SeasonManager;
import core.ComboItem;
import core.Helper;
import entity.Hotel;
import entity.Pension;
import entity.Room;
import entity.Season;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class RoomSaveView extends Layout{
    private JComboBox cmb_hotel;
    private JComboBox cmb_pension;
    private JComboBox cmb_season;
    private JComboBox cmb_room;
    private JTextField fld_bed;
    private JTextField fld_m2;
    private JTextField fld_stock;
    private JTextField fld_adult;
    private JTextField fld_child;
    private JCheckBox cb_tv;
    private JCheckBox cb_minibar;
    private JCheckBox cb_game;
    private JCheckBox cb_cashbox;
    private JButton btn_save;
    private JButton btn_exit;
    private JCheckBox cb_projection;
    private JLabel lbl_welcome;
    private JLabel lbl_hotel;
    private JLabel lbl_pension;
    private JLabel lbl_season;
    private JLabel lbl_room;
    private JLabel lbl_bed;
    private JLabel lbl_m2;
    private JLabel lbl_stock;
    private JLabel lbl_adult;
    private JLabel lbl_child;
    private JPanel container;
    DefaultTableModel mdl_roomSave_table = new DefaultTableModel();
    public RoomSaveView(){
        RoomManager roomManager = new RoomManager();
        SeasonManager seasonManager = new SeasonManager();
        HotelManager hotelManager = new HotelManager();
        Room room = new Room();
        Season season = new Season();
        Hotel hotel = new Hotel();

        this.add(container);
        this.guiInitilaze(500, 600);
        loadComponent();
        loadHotelFilter();

        btn_save.addActionListener(new ActionListener() { //ODA KAYDI DEĞERLENDİRME FORMU 14
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField[] fieldList = {fld_bed, fld_adult, fld_child, fld_stock, fld_m2};
                if (Helper.isFieldListEmpty(fieldList)) {
                    Helper.showMsg("fill");
                } else {

                    boolean result;
                    ComboItem selectedHotelInfo = (ComboItem) cmb_hotel.getSelectedItem();
                    room.setHotel_id(selectedHotelInfo.getKey());

                    ComboItem selectedPensionInfo = (ComboItem) cmb_pension.getSelectedItem();
                    room.setPension_id(selectedPensionInfo.getKey());

                    ComboItem selectedSeasonInfo = (ComboItem) cmb_season.getSelectedItem();
                    room.setSeason_id(selectedSeasonInfo.getKey());
                    room.setType(cmb_room.getSelectedItem().toString());
                    room.setStock(Integer.parseInt(fld_stock.getText()));
                    room.setAdult_price(Double.parseDouble(fld_adult.getText()));
                    room.setChild_price(Double.parseDouble(fld_child.getText()));
                    room.setBed_capacity(Integer.parseInt(fld_bed.getText()));
                    room.setSquare_meter(Integer.parseInt(fld_m2.getText()));
                    room.setTelevision(cb_tv.isSelected());
                    room.setMinibar(cb_minibar.isSelected());
                    room.setProjection(cb_projection.isSelected());
                    room.setGame_console(cb_game.isSelected());
                    room.setCash_box(cb_cashbox.isSelected());
                    room.setProjection(cb_projection.isSelected());

                    result = roomManager.save(room);

                    if (result) {
                        Helper.showMsg("done");

                    } else {
                        Helper.showMsg("error");
                    }
                }
            }
        });

        cmb_hotel.addActionListener(new ActionListener() { //otel comboboxı açılınca;
            @Override
            public void actionPerformed(ActionEvent e) {
                ComboItem selectedItem = (ComboItem) cmb_hotel.getSelectedItem();
                int selectedHotelId = selectedItem.getKey();
                loadPensionFilter(selectedHotelId);
                loadSeasonFilter(selectedHotelId);
            }
        });
    }
    private void loadComponent() { //ÇIKIŞ BUTONUNDAN ÇIKIŞ YAPAR
        btn_exit.addActionListener(e -> dispose());
    }
    public void loadHotelFilter(){ //Otel isimlerini comboboxa setledim
        HotelManager hotelManager = new HotelManager();
        this.cmb_hotel.removeAllItems();
        for( Hotel obj: hotelManager.findAll()){
            this.cmb_hotel.addItem(obj.getComboItem());
        }
        this.cmb_hotel.setSelectedItem(null); //seçeneksiz
    }
    public void loadPensionFilter(int id){
        PensionManager pensionManager = new PensionManager();
        this.cmb_pension.removeAllItems();
        ComboItem selectedItem = (ComboItem) cmb_hotel.getSelectedItem(); // Burası sayesinde sadece otele ait pansiyonlar geliyor
        int selectedHotelId = selectedItem.getKey();
        for( Pension obj: pensionManager.searchForTable(selectedHotelId)){
            this.cmb_pension.addItem(obj.getComboItem());
        }
        this.cmb_pension.setSelectedItem(null);
    }
    public void loadSeasonFilter(int id){
        SeasonManager seasonManager = new SeasonManager();
        this.cmb_season.removeAllItems();
        ComboItem selectedItem = (ComboItem) cmb_hotel.getSelectedItem(); // Burası sayesinde sadece otele ait sezonlar geliyor
        int selectedHotelId = selectedItem.getKey();
        for( Season obj: seasonManager.searchForTable(selectedHotelId)){
            this.cmb_season.addItem(obj.getComboItem());

        }
        this.cmb_season.setSelectedItem(null);
    }




}
