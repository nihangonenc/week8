package business;

import core.Helper;
import dao.PensionDao;
import dao.RoomDao;
import entity.Hotel;
import entity.Pension;
import entity.Room;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RoomManager {
    private final RoomDao roomDao;

    public RoomManager() {
        this.roomDao = new RoomDao();

    }
    public boolean decreaseStock (int id){
        if (this.getById(id) == null){ //veritabanında böyle bir row yoksa
            Helper.showMsg(id + " ID kaydı bulunamadı");
            return false;
        }
        return this.roomDao.decreaseStock(id); // veri varsa daoda yaptığımız metodu uygular
    }


    public boolean save(Room room) {
        if (this.getById(room.getId()) != null) {
            Helper.showMsg("error");
            return false;
        }
        return this.roomDao.save(room);
    }
    public ArrayList<Room> findAll(){
        return this.roomDao.findAll();
    }
    public Room getById(int id) {
        return this.roomDao.getById(id);
    }
    public ArrayList<Object[]> getForTable(int size, ArrayList<Room> roomList) {

        ArrayList<Object[]> roomObjList = new ArrayList<>();

        for (Room obj : roomList) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getId();
            rowObject[i++] = obj.getPension_id();
            rowObject[i++] = obj.getSeason_id();
            rowObject[i++] = obj.getType();
            rowObject[i++] = obj.getStock();
            rowObject[i++] = obj.getAdult_price();
            rowObject[i++] = obj.getChild_price();
            rowObject[i++] = obj.getBed_capacity();
            rowObject[i++] = obj.getSquare_meter();
            rowObject[i++] = obj.isTelevision();
            rowObject[i++] = obj.isMinibar();
            rowObject[i++] = obj.isGame_console();
            rowObject[i++] = obj.isCash_box();
            rowObject[i++] = obj.isProjection();

            roomObjList.add(rowObject);
        }
        return roomObjList;
    }
    //DEĞERLENDİRME FORMU 15
    public ArrayList<Room> searchForTable(String hotelname, String country, String startdate, String finishdate, int guest) throws ParseException {
        Helper.optionPaneTR();

        String select = "SELECT * FROM public.room JOIN public.hotel ON hotel.id = room.hotel_id JOIN public.season ON hotel.id = season.hotel_id";
        ArrayList<String> whereList = new ArrayList<>();

       if (startdate.isEmpty() || finishdate.isEmpty() || guest <= 0) {
            JOptionPane.showMessageDialog(null, "Giriş / çıkış tarihlerini ve konaklayacak yetişkin / çocuk sayısını eksiksiz giriniz.");
       }

        whereList.add("stock > 0");
        whereList.add("bed_capacity >= '" + guest + "'"); //yatak kapasitesi misafir sayısından küçük olamaz
        if(hotelname !=null) {
            whereList.add("name ILIKE '%" + hotelname + "%'");
        }
        if(country != null) {
            whereList.add("address ILIKE '%" + country + "%'");
        }
        if(!startdate.isEmpty()|| !finishdate.isEmpty()) {
            Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startdate);
            whereList.add("start_date <= '" + new java.sql.Date(startDate.getTime()) + "'");

            Date finishDate = new SimpleDateFormat("yyyy-MM-dd").parse(finishdate);
            whereList.add("finish_date >= '" + new java.sql.Date(finishDate.getTime()) + "'");
        }

        String whereStr = String.join(" AND ", whereList);
        String query = select;

        if (whereStr.length() > 0) { //içinde değer varsa
            query += " WHERE " + whereStr; //özel query oluşturma
        }

        return this.roomDao.selectByQuery(query); //query e göre veri döndürme
    }
}
