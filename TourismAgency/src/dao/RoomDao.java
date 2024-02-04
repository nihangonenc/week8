package dao;

import core.Db;
import entity.Hotel;
import entity.Pension;
import entity.Room;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoomDao {
    private final Connection con;

    public RoomDao() {
        this.con = Db.getInstance();
    }
    public ArrayList<Room> selectByQuery(String query){
        ArrayList<Room> roomList = new ArrayList<>();
        try {
            ResultSet rs = this.con.createStatement().executeQuery(query);
            while (rs.next()){
                roomList.add(this.match(rs));
            }

        }catch (SQLException throwables){
            throwables.printStackTrace();

        }
        return roomList;
    }
    public ArrayList<Room> findAll(){
        ArrayList<Room> roomList = new ArrayList<>();
        String sql = "SELECT * FROM public.room";
        try {
            ResultSet rs = con.createStatement().executeQuery(sql);
            while (rs.next()){
                roomList.add(this.match(rs));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return roomList;
    }
    public Room getById(int id) { //id'ye göre döndürür
        Room obj = null;
        String query = "SELECT * FROM public.room WHERE (id) = ?";
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
    public boolean save(Room room) {
        String query = "INSERT INTO public.room " +
                "(" +
                "hotel_id ," +
                "pension_id ," +
                "season_id ," +
                "type ," +
                "stock ," +
                "adult_price ," +
                "child_price ," +
                "bed_capacity ," +
                "square_meter ," +
                "television ," +
                "minibar ," +
                "game_console ," +
                "cash_box ," +
                "projection" +
                ")" +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement ps = this.con.prepareStatement(query);
            ps.setInt(1, room.getHotel_id());
            ps.setInt(2, room.getPension_id());
            ps.setInt(3, room.getSeason_id());
            ps.setString(4, room.getType());
            ps.setInt(5, room.getStock());
            ps.setDouble(6, room.getAdult_price());
            ps.setDouble(7, room.getChild_price());
            ps.setInt(8, room.getBed_capacity());
            ps.setInt(9, room.getSquare_meter());
            ps.setBoolean(10, room.isTelevision());
            ps.setBoolean(11, room.isMinibar());
            ps.setBoolean(12, room.isGame_console());
            ps.setBoolean(13, room.isCash_box());
            ps.setBoolean(14, room.isProjection());

            return ps.executeUpdate() != -1;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    public boolean decreaseStock(int id){ // oda stoğunu azaltmak için
        String query = "UPDATE public.room SET stock = stock -1 WHERE id = ?";
        try {
            PreparedStatement pr = this.con.prepareStatement(query);
            pr.setInt(1,id);

            return pr.executeUpdate() != -1 ;

        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    public Room match(ResultSet rs) throws SQLException { //database verisinden entity verisine dönüştürme
        Room obj = new Room();
        obj.setId(rs.getInt("id"));
        obj.setHotel_id(rs.getInt("hotel_id"));
        obj.setPension_id(rs.getInt("pension_id"));
        obj.setSeason_id(rs.getInt("season_id"));
        obj.setType(rs.getString("type"));
        obj.setStock(rs.getInt("stock"));
        obj.setAdult_price(rs.getDouble("adult_price"));
        obj.setChild_price(rs.getDouble("child_price"));
        obj.setBed_capacity(rs.getInt("bed_capacity"));
        obj.setSquare_meter(rs.getInt("square_meter"));
        obj.setTelevision(rs.getBoolean("television"));
        obj.setMinibar(rs.getBoolean("minibar"));
        obj.setGame_console(rs.getBoolean("game_console"));
        obj.setCash_box(rs.getBoolean("cash_box"));
        obj.setProjection(rs.getBoolean("projection"));
        return obj;
    }


}
