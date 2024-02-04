package dao;

import core.Db;
import entity.Hotel;
import entity.Reservation;
import entity.Room;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReservationDao {
    private final Connection con;

    public ReservationDao() {
        this.con = Db.getInstance();
    }
    public ArrayList<Reservation> findAll(){ //veritabanından tüm room kayıtları çekip arraylistte depoladık
        ArrayList<Reservation> reservationList = new ArrayList<>();
        String sql = "SELECT * FROM public.reservation";
        try {
            ResultSet rs = con.createStatement().executeQuery(sql);
            while (rs.next()){
                reservationList.add(this.match(rs));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return reservationList;
    }
    public Reservation getById(int id) { //id'ye göre döndürür
        Reservation obj = null;
        String query = "SELECT * FROM public.reservation WHERE (id) = ?";
        try {
            PreparedStatement ps = this.con.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                obj = this.match(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }
    public boolean increaseStock(int id) { // stok artımı
        String query = "UPDATE public.room " +
                "SET stock = stock + 1 " +
                "FROM public.reservation " +
                "WHERE room.id = reservation.room_id AND reservation.id = ?";
        try {
            PreparedStatement pr = this.con.prepareStatement(query);
            pr.setInt(1, id);
            return pr.executeUpdate() != -1 ;

        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }
    public boolean save(Reservation reservation) {
        String query = "INSERT INTO public.reservation " +
                "(" +
                "room_id ," +
                "check_in_date ," +
                "check_out_date ," +
                "total_price ," +
                "guest_count ," +
                "guest_name ," +
                "guest_citizen_id ," +
                "guest_mail ," +
                "guest_phone " +
                ")" +
                "VALUES (?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement ps = this.con.prepareStatement(query);
            ps.setInt(1, reservation.getRoom_id());
            ps.setDate(2, java.sql.Date.valueOf(reservation.getCheck_in_date()));
            ps.setDate(3, java.sql.Date.valueOf(reservation.getCheck_out_date()));
            ps.setDouble(4, reservation.getTotal_price());
            ps.setInt(5, reservation.getGuest_count());
            ps.setString(6, reservation.getGuest_name());
            ps.setString(7, reservation.getGuest_citizen_id());
            ps.setString(8, reservation.getGuest_mail());
            ps.setString(9, reservation.getGuest_phone());


            return ps.executeUpdate() != -1;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    public boolean update(Reservation reservation){ //Misafir bilgilerinin güncellenmesinde kullanılacak
        String query = "UPDATE public.reservation SET " +
                "guest_name = ?," +
                "guest_citizen_id = ? ," +
                "guest_mail = ? ," +
                "guest_phone = ? " +
                "WHERE id = ?";
        try {
            PreparedStatement ps = this.con.prepareStatement(query);
            ps.setString(1, reservation.getGuest_name());
            ps.setString(2, reservation.getGuest_citizen_id());
            ps.setString(3, reservation.getGuest_mail());
            ps.setString(4, reservation.getGuest_phone());
            ps.setInt(5,reservation.getId());
            return ps.executeUpdate() != -1 ;

        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }
    public boolean delete (int id){
        String query = "DELETE FROM public.reservation WHERE id = ?";
        try {
            PreparedStatement pr = this.con.prepareStatement(query);
            pr.setInt(1, id);
            return pr.executeUpdate() != -1 ;

        } catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }
    public Reservation match(ResultSet rs) throws SQLException { //database verisinden entity verisine dönüştürme
        Reservation obj = new Reservation();
        obj.setId(rs.getInt("id"));
        obj.setRoom_id(rs.getInt("room_id"));
        obj.setCheck_in_date(String.valueOf(rs.getDate("check_in_date")));
        obj.setCheck_out_date(String.valueOf(rs.getDate("check_out_date")));
        obj.setTotal_price(rs.getDouble("total_price"));
        obj.setGuest_count(rs.getInt("guest_count"));
        obj.setGuest_name(rs.getString("guest_name"));
        obj.setGuest_citizen_id(rs.getString("guest_citizen_id"));
        obj.setGuest_mail(rs.getString("guest_mail"));
        obj.setGuest_phone(rs.getString("guest_phone"));
        return obj;
    }
}
