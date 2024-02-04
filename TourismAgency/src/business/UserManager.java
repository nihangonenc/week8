package business;

import core.Helper;
import dao.UserDao;
import entity.User;
import view.LoginView;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class UserManager {
    private final UserDao userDao;

    public UserManager() {
        this.userDao = new UserDao();
    }
    public User findByLogin(String username, String password){
        return this.userDao.findByLogin(username,password);
    }
    public ArrayList<Object[]>getForTable(int size, ArrayList<User> users){ // Defaulttable a setlemek için Arraye dönüşüm. Sütun sayısı kadar içerde obje oluşturduk
        ArrayList<Object[]> userObjList = new ArrayList<>();
        for (User obj : users){
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getId();
            rowObject[i++] = obj.getUsername();
            rowObject[i++] = obj.getPassword();
            rowObject[i++] = obj.getRole();
            userObjList.add(rowObject);
        }
        return userObjList;
    }
    public ArrayList<User> findAll(){
        return this.userDao.findAll();
    }
    public User getById(int id){
        return this.userDao.getById(id);
    } //daodaki metodu uygula
    public ArrayList<User> searchForTable(String role){
        String select = "SELECT * FROM public.user";
        ArrayList<String> whereList = new ArrayList<>();

        if (role != null){
            whereList.add("user_role = '"+ role.toString()+"'");
        }

        String whereStr = String.join(" AND ", whereList);
        String query = select;
        if (whereStr.length() > 0){
            query += " WHERE " + whereStr;
        }

        return this.userDao.selectByQuery(query);
    }
    public boolean update(User user){
        if (this.getById(user.getId()) == null){ //eşleşen id yoksa bulunamadı mesajı
            Helper.showMsg("notFound");

        }
        return this.userDao.update(user); //uygun bir id ise daodaki metodu uygula
    }
    public boolean delete(int id){
        if (this.getById(id) == null){ //veritabanında böyle bir row yoksa
            Helper.showMsg(id + " ID kayıtlı marka bulunamadı");
            return false;
        }
        return this.userDao.delete(id); // veri varsa daoda yaptığımız metodu uygular
    }
    public boolean save(User user){
        if(this.getById(user.getId())!= null){
            Helper.showMsg("error");
        }
        return this.userDao.save(user);
    }

}
