package entity;

import core.ComboItem;

public class Pension {
    private int id;
    private int hotel_id;
    private String type;

    public Pension(int id, int hotel_id, String type) {
        this.id = id;
        this.hotel_id = hotel_id;
        this.type = type;
    }

    public Pension() {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Pension{" +
                "id=" + id +
                ", hotel_id=" + hotel_id +
                ", type='" + type + '\'' +
                '}';
    }
    public ComboItem getComboItem(){
        return new ComboItem(this.getId(),this.getType());
    }
}
