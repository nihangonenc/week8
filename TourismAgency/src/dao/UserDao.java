package dao;
import core.Db;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDao {
    private final Connection con;

    public UserDao() {
        this.con = Db.getInstance();
    }
    public ArrayList<User> findAll(){
        ArrayList<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM public.user";
        try {
            ResultSet rs = con.createStatement().executeQuery(sql);
            while (rs.next()){
                userList.add(this.match(rs));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return userList;
    }
    public ArrayList<User> selectByQuery(String query){
        ArrayList<User> userList = new ArrayList<>();
        try {
            ResultSet rs = this.con.createStatement().executeQuery(query);
            while (rs.next()){
                userList.add(this.match(rs));
            }

        }catch (SQLException throwables){
            throwables.printStackTrace();

        }
        return userList;
    }
    public User findByLogin(String username, String password){ // belirli kullanıcı adı ve şifreye sahip kullanıcıyı temsil eden bir User nesnesi döndürür
        User obj = null;
        String query = "SELECT * FROM public.user WHERE user_name = ? AND user_password = ?";
        try {
            PreparedStatement pr = this.con.prepareStatement(query);
            pr.setString(1,username);
            pr.setString(2, password);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                obj = this.match(rs);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return obj;
    }
    public User match(ResultSet rs) throws SQLException {
        User obj = new User();
        obj.setId(rs.getInt("id"));
        obj.setUsername(rs.getString("user_name"));
        obj.setPassword((rs.getString("user_password")));
        obj.setRole(rs.getString("user_role"));
        return obj;
    }
    public boolean save(User user){
        String query = "INSERT INTO public.user " +
                "(" +
                "user_name , " +
                "user_password , " +
                "user_role " +
                ")" +
                "VALUES (?,?,?)";
        try {
            PreparedStatement pr = this.con.prepareStatement(query);

            pr.setString(1, user.getUsername());
            pr.setString(2, user.getPassword());
            pr.setString(3, user.getRole());
            return pr.executeUpdate() != -1 ; // executeUpdate() etkilenen satır sayısını döndürür

        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    public boolean update(User user){ //user güncellemek için
        String query = "UPDATE public.user SET " +
                "user_name = ? ," +
                "user_password = ? ," +
                "user_role = ? " +
                "WHERE id = ?";
        try {
            PreparedStatement pr = this.con.prepareStatement(query);
            pr.setString(1, user.getUsername());
            pr.setString(2, user.getPassword());
            pr.setString(3, user.getRole());
            pr.setInt(4, user.getId());
            return pr.executeUpdate() != -1 ;

        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }
    public boolean delete (int id){ //user silmek için
        String query = "DELETE FROM public.user WHERE id = ?";
        try {
            PreparedStatement pr = this.con.prepareStatement(query);
            pr.setInt(1, id);
            return pr.executeUpdate() != -1 ;

        } catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }
    public User getById(int id){  //id'ye göre user nesnesi döner
        User obj = null;
        String query = "SELECT * FROM public.user WHERE id = ? ";
        try {
            PreparedStatement pr = this.con.prepareStatement(query);
            pr.setInt(1,id);
            ResultSet rs = pr.executeQuery();
            if(rs.next()){
                obj = this.match(rs);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return obj;
    }
}
