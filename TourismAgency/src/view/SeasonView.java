package view;

import business.SeasonManager;
import core.Helper;
import entity.Season;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SeasonView extends Layout{
    private JLabel lbl_top;
    private JButton btn_save;
    private JPanel container;
    private JPanel pnl_top;
    private JPanel pnl_down;
    private JTextField fld_seasonStart;
    private JTextField fld_seasonFinish;
    private JLabel lbl_seasonStart;
    private JLabel lbl_seasonFinish;
    private int hotelId;
    private SeasonManager seasonManager = new SeasonManager();
    DefaultTableModel mdl_season_table = new DefaultTableModel();

    public SeasonView(int hotelId){
        this.hotelId = hotelId;
        this.add(container);
        this.guiInitilaze(400,300);

        btn_save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Season season = new Season();
                boolean result;
                season.setHotel_id(hotelId);
                season.setStart_date(fld_seasonStart.getText());
                season.setFinish_date(fld_seasonFinish.getText());
                result = seasonManager.save(season);

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
