package entity;

import core.ComboItem;

public class Season {
    private int id;
    private int hotel_id;
    private String start_date;
    private String finish_date;

    public Season() {
    }
    public Season(int id, int hotel_id, String start_date, String finish_date) {
        this.id = id;
        this.hotel_id = hotel_id;
        this.start_date = start_date;
        this.finish_date = finish_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getFinish_date() {
        return finish_date;
    }

    public void setFinish_date(String finish_date) {
        this.finish_date = finish_date;
    }

    @Override
    public String toString() {
        return "Season{" +
                "id=" + id +
                ", hotel_id=" + hotel_id +
                ", start_date='" + start_date + '\'' +
                ", finish_date='" + finish_date + '\'' +
                '}';
    }
    public ComboItem getComboItem(){
        return new ComboItem(this.getId(),this.getStart_date()+" / "+this.getFinish_date());
    }
}
