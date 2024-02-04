package business;

import core.Helper;
import dao.HotelDao;
import dao.UserDao;
import entity.Hotel;

import java.util.ArrayList;

public class HotelManager {
    private final HotelDao hotelDao;

    public HotelManager() {
        this.hotelDao = new HotelDao();
    }
    public ArrayList<Hotel> findAll(){
        return this.hotelDao.findAll();
    }
    public Hotel getById(int id) {
        return this.hotelDao.getById(id);
    }
    public boolean save(Hotel hotel) {

        if (this.getById(hotel.getId()) != null) {
            Helper.showMsg("error");
            return false;
        }
        return this.hotelDao.save(hotel);
    }
    public ArrayList<Object[]> getForTable(int size, ArrayList<Hotel> hotelList) {

        ArrayList<Object[]> hotelObjList = new ArrayList<>();

        for (Hotel obj : hotelList) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getId();
            rowObject[i++] = obj.getName();
            rowObject[i++] = obj.getAddress();
            rowObject[i++] = obj.getMail();
            rowObject[i++] = obj.getPhone();
            rowObject[i++] = obj.getStar();
            rowObject[i++] = obj.isCar_park();
            rowObject[i++] = obj.isWifi();
            rowObject[i++] = obj.isPool();
            rowObject[i++] = obj.isFitness();
            rowObject[i++] = obj.isConcierge();
            rowObject[i++] = obj.isSpa();
            rowObject[i++] = obj.isRoom_service();
            hotelObjList.add(rowObject);
        }
        return hotelObjList;
    }

}
