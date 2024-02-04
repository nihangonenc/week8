package dao;

import core.Db;
import entity.Hotel;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HotelDao {
    private final Connection con;

    public HotelDao() {
        this.con = Db.getInstance();
    }
    public ArrayList<Hotel> findAll(){ //veritabanından tüm hotel kayıtlarını çekip arraylistte depoladık
        ArrayList<Hotel> hotelList = new ArrayList<>();
        String sql = "SELECT * FROM public.hotel";
        try {
            ResultSet rs = con.createStatement().executeQuery(sql);
            while (rs.next()){
                hotelList.add(this.match(rs));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return hotelList;
    }
    public Hotel getById(int id) { //id'ye göre döndürür
        Hotel obj = null;
        String query = "SELECT * FROM public.hotel WHERE (id) = ?";
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
    public ArrayList<Hotel> selectByQuery(String query) {
        ArrayList<Hotel> hotelList = new ArrayList<>();
        try {
            ResultSet rs = this.con.createStatement().executeQuery(query);
            while (rs.next()) {
                hotelList.add(this.match(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotelList;
    }
    public boolean save(Hotel hotel) {
        String query = "INSERT INTO public.hotel " +
                "(" +
                "name ," +
                "address ," +
                "phone ," +
                "mail ," +
                "star ," +
                "car_park ," +
                "wifi ," +
                "pool ," +
                "fitness ," +
                "concierge ," +
                "spa ," +
                "room_service " +
                ")" +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement ps = this.con.prepareStatement(query);
            ps.setString(1, hotel.getName());
            ps.setString(2, hotel.getAddress());
            ps.setString(3, hotel.getPhone());
            ps.setString(4, hotel.getMail());
            ps.setString(5, hotel.getStar());
            ps.setBoolean(6, hotel.isCar_park());
            ps.setBoolean(7, hotel.isWifi());
            ps.setBoolean(8, hotel.isPool());
            ps.setBoolean(9, hotel.isFitness());
            ps.setBoolean(10, hotel.isConcierge());
            ps.setBoolean(11, hotel.isSpa());
            ps.setBoolean(12, hotel.isRoom_service());

            return ps.executeUpdate() != -1;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    public Hotel match(ResultSet rs) throws SQLException { //database verisinden entity verisine dönüştürme
        Hotel obj = new Hotel();
        obj.setId(rs.getInt("id"));
        obj.setName(rs.getString("name"));
        obj.setAddress(rs.getString("address"));
        obj.setPhone(rs.getString("phone"));
        obj.setMail(rs.getString("mail"));
        obj.setStar(rs.getString("star"));
        obj.setWifi(rs.getBoolean("wifi"));
        obj.setPool(rs.getBoolean("pool"));
        obj.setFitness(rs.getBoolean("fitness"));
        obj.setConcierge(rs.getBoolean("concierge"));
        obj.setSpa(rs.getBoolean("spa"));
        obj.setRoom_service(rs.getBoolean("room_service"));
        return obj;
    }

}
