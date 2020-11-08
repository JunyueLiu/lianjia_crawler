package Lianjia.data;

public class CityInfo {
    private String city_id;
    private String city_name;
    private String abbr;
    private int new_rent;


    public int getNew_rent() {
        return new_rent;
    }

    public void setNew_rent(int new_rent) {
        this.new_rent = new_rent;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }
}
