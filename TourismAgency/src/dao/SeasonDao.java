package dao;

import core.Db;
import entity.Pension;
import entity.Season;
import entity.User;

import java.sql.*;
import java.util.ArrayList;

public class SeasonDao {
    private final Connection con;

    public SeasonDao() {
        this.con = Db.getInstance();
    }
    public ArrayList<Season> selectByQuery(String query){
        ArrayList<Season> seasonList = new ArrayList<>();
        try {
            ResultSet rs = this.con.createStatement().executeQuery(query);
            while (rs.next()){
                seasonList.add(this.match(rs));
            }

        }catch (SQLException throwables){
            throwables.printStackTrace();

        }
        return seasonList;
    }
    public Season getById(int id) {
        Season obj = null;
        String query = "SELECT * FROM public.season WHERE (id) = ?";
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

    public ArrayList<Season> findAll(){
        ArrayList<Season> seasonList = new ArrayList<>();
        String sql = "SELECT * FROM public.season";
        try {
            ResultSet rs = con.createStatement().executeQuery(sql);
            while (rs.next()){
                seasonList.add(this.match(rs));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return seasonList;
    }
    public boolean save(Season season){
        String query = "INSERT INTO public.season " +
                "(" +
                "hotel_id ," +
                "start_date ," +
                "finish_date " +
                ")" +
                "VALUES (?,?,?)";
        try {
            PreparedStatement pr = this.con.prepareStatement(query);


            pr.setInt(1, season.getHotel_id());
            pr.setDate(2, Date.valueOf(season.getStart_date()));
            pr.setDate(3, Date.valueOf(season.getFinish_date()));
            return pr.executeUpdate() != -1 ;

        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }
    public Season match(ResultSet rs) throws SQLException {
        Season obj = new Season();
        obj.setId(rs.getInt("id"));
        obj.setHotel_id(rs.getInt("hotel_id"));
        obj.setStart_date((rs.getString("start_date")));
        obj.setFinish_date(rs.getString("finish_date"));
        return obj;
    }
}
