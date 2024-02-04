package view;

import business.PensionManager;
import core.ComboItem;
import core.Helper;
import entity.Hotel;
import entity.Pension;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PensionView extends Layout {
    private JComboBox cmb_pension;
    private JButton btn_save;
    private JLabel lbl_top;
    private JPanel container;
    private JPanel pnl_top;
    private Hotel hotel;
    private int hotelID;
    DefaultTableModel mdl_pension_table = new DefaultTableModel();
    private PensionManager pensionManager = new PensionManager();
    public PensionView(int hotelID){
        this.hotelID = hotelID;
        this.add(container);
        this.guiInitilaze(400, 300);

       btn_save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Pension pension = new Pension();
                boolean result;
                pension.setHotel_id(hotelID);
                pension.setType(cmb_pension.getSelectedItem().toString());
                result = pensionManager.save(pension);

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
