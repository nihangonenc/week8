package dao;

import core.Db;
import entity.Hotel;
import entity.Pension;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PensionDao {
    private final Connection con;

    public PensionDao() {
        this.con = Db.getInstance();
    }
    public ArrayList<Pension> findAll(){ //veritabanından tüm hotel kayıtlarını çekip arraylistte depoladık
        ArrayList<Pension> pensionList = new ArrayList<>();
        String sql = "SELECT * FROM public.pension";
        try {
            ResultSet rs = con.createStatement().executeQuery(sql);
            while (rs.next()){
                pensionList.add(this.match(rs));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return pensionList;
    }
    public ArrayList<Pension> selectByQuery(String query){
        ArrayList<Pension> pensionList = new ArrayList<>();
        try {
            ResultSet rs = this.con.createStatement().executeQuery(query);
            while (rs.next()){
                pensionList.add(this.match(rs));
            }

        }catch (SQLException throwables){
            throwables.printStackTrace();

        }
        return pensionList;
    }
    public Pension getById(int id) { //id'ye göre döndürür
        Pension obj = null;
        String query = "SELECT * FROM public.pension WHERE (id) = ?";
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
    public boolean save(Pension pension){
        String query = "INSERT INTO public.pension " +
                "(" +
                "hotel_id , " +
                "pension_type " +
                ")" +
                "VALUES (?,?)";
        try {
            PreparedStatement pr = this.con.prepareStatement(query);


            pr.setInt(1, pension.getHotel_id());
            pr.setString(2, pension.getType());
            return pr.executeUpdate() != -1 ; // executeUpdate() etkilenen satır sayısını döndürür

        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }
    public Pension match(ResultSet rs) throws SQLException { //database verisinden entity verisine dönüştürme
        Pension obj = new Pension();
        obj.setId(rs.getInt("id"));
        obj.setHotel_id(rs.getInt("hotel_id"));
        obj.setType(rs.getString("pension_type"));
        return obj;
    }
}
