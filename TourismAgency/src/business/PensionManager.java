package business;

import core.Helper;
import dao.PensionDao;
import entity.Hotel;
import entity.Pension;
import entity.User;

import java.util.ArrayList;

public class PensionManager {
    private final PensionDao pensionDao;

    public PensionManager() {
        this.pensionDao = new PensionDao();
    }
    public ArrayList<Pension> findAll(){
        return this.pensionDao.findAll();
    }
    public Pension getById(int id) {
        return this.pensionDao.getById(id);
    }
    public ArrayList<Pension> searchForTable(Integer hotel_id){
        String select = "SELECT * FROM public.pension";
        ArrayList<String> whereList = new ArrayList<>();

        if (hotel_id != null){
            whereList.add("hotel_id = '"+ hotel_id.toString()+"'");
        }

        String whereStr = String.join(" AND ", whereList);
        String query = select;
        if (whereStr.length() > 0){
            query += " WHERE " + whereStr;
        }

        return this.pensionDao.selectByQuery(query);
    }
    public ArrayList<Object[]> getForTable(int size, ArrayList<Pension> pensionList) {

        ArrayList<Object[]> pensionObjList = new ArrayList<>();

        for (Pension obj : pensionList) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getId();
            rowObject[i++] = obj.getHotel_id();
            rowObject[i++] = obj.getType();

            pensionObjList.add(rowObject);
        }
        return pensionObjList;
    }
    public boolean save(Pension pension){
        if(this.getById(pension.getId())!= null){
            Helper.showMsg("error");
        }
        return this.pensionDao.save(pension);
    }
}
