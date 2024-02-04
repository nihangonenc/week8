package business;

import core.Helper;
import dao.ReservationDao;
import entity.Hotel;
import entity.Reservation;
import entity.Room;
import entity.User;

import java.util.ArrayList;

public class ReservationManager {
    private final ReservationDao reservationDao;
    public ReservationManager() {
        this.reservationDao = new ReservationDao();
    }
    public ArrayList<Reservation> findAll(){
        return this.reservationDao.findAll();
    }
    public Reservation getById(int id) {
        return this.reservationDao.getById(id);
    }
    public ArrayList<Object[]> getForTable(int size, ArrayList<Reservation> reservationList) {

        ArrayList<Object[]> reservationObjList = new ArrayList<>();

        for (Reservation obj : reservationList) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getId();
            rowObject[i++] = obj.getRoom_id();
            rowObject[i++] = obj.getCheck_in_date();
            rowObject[i++] = obj.getCheck_out_date();
            rowObject[i++] = obj.getTotal_price();
            rowObject[i++] = obj.getGuest_count();
            rowObject[i++] = obj.getGuest_name();
            rowObject[i++] = obj.getGuest_citizen_id();
            rowObject[i++] = obj.getGuest_mail();
            rowObject[i++] = obj.getGuest_phone();

            reservationObjList.add(rowObject);
        }
        return reservationObjList;
    }
    public boolean increaseStock (int id){
       if (this.getById(id) == null){ //veritabanında böyle bir row yoksa
            Helper.showMsg(id + " ID kaydı bulunamadı");
            return false;
       }
       return this.reservationDao.increaseStock(id); // veri varsa daoda yaptığımız metodu uygular
    }

    public boolean save(Reservation reservation) {

        if (this.getById(reservation.getId()) != null) {
            Helper.showMsg("error");
            return false;
        }
        return this.reservationDao.save(reservation);
    }
    public boolean update(Reservation reservation){
        if (this.getById(reservation.getId()) == null){ //eşleşen id yoksa bulunamadı mesajı
            Helper.showMsg("notFound");

        }
        return this.reservationDao.update(reservation); //uygun bir id ise daodaki metodu uygula
    }
    public boolean delete(int id){
        if (this.getById(id) == null){ //veritabanında böyle bir row yoksa
            Helper.showMsg(id + " ID kaydı bulunamadı");
            return false;
        }
        return this.reservationDao.delete(id); // veri varsa daoda yaptığımız metodu uygular
    }
}
