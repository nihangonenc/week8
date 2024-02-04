package business;

import core.Db;
import core.Helper;
import dao.SeasonDao;
import entity.Pension;
import entity.Season;

import java.util.ArrayList;

public class SeasonManager {
    private final SeasonDao seasonDao;

    public SeasonManager() {
        this.seasonDao = new SeasonDao();
    }
    public ArrayList<Season> findAll(){
        return this.seasonDao.findAll();
    }
    public Season getById(int id) {
        return this.seasonDao.getById(id);
    }
    public ArrayList<Season> searchForTable(Integer hotel_id){
        String select = "SELECT * FROM public.season";
        ArrayList<String> whereList = new ArrayList<>();

        if (hotel_id != null){
            whereList.add("hotel_id = '"+ hotel_id.toString()+"'");
        }

        String whereStr = String.join(" AND ", whereList);
        String query = select;
        if (whereStr.length() > 0){
            query += " WHERE " + whereStr;
        }

        return this.seasonDao.selectByQuery(query);
    }
    public ArrayList<Object[]> getForTable(int size, ArrayList<Season> seasonList) {

        ArrayList<Object[]> seasonObjList = new ArrayList<>();

        for (Season obj : seasonList) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getId();
            rowObject[i++] = obj.getHotel_id();
            rowObject[i++] = obj.getStart_date();
            rowObject[i++] = obj.getFinish_date();

            seasonObjList.add(rowObject);
        }
        return seasonObjList;
    }
    public boolean save(Season season){
        if(this.getById(season.getId())!= null){
            Helper.showMsg("error");
        }
        return this.seasonDao.save(season);
    }
}
