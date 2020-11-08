package Lianjia.data;

public class Rented {

    private String house_code;
    private String kv_house_type;
    private String title;
    private String cover_pic;
    private String price_str;
    private String price_unit;
    private String is_ziroom;
    private String desc;
    private String sign_time;

    public String getHouse_code() {
        return house_code;
    }

    public String getKv_house_type() {
        return kv_house_type;
    }

    public String getTitle() {
        return title;
    }

    public String getCover_pic() {
        return cover_pic;
    }

    public String getPrice_str() {
        return price_str;
    }

    public String getPrice_unit() {
        return price_unit;
    }

    public String getIs_ziroom() {
        return is_ziroom;
    }

    public String getDesc() {
        return desc;
    }

    public String getSign_time() {
        return sign_time;
    }

    public void setHouse_code(String house_code) {
        this.house_code = house_code;
    }

    public void setKv_house_type(String kv_house_type) {
        this.kv_house_type = kv_house_type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCover_pic(String cover_pic) {
        this.cover_pic = cover_pic;
    }

    public void setPrice_str(String price_str) {
        this.price_str = price_str;
    }

    public void setPrice_unit(String price_unit) {
        this.price_unit = price_unit;
    }

    public void setIs_ziroom(String is_ziroom) {
        this.is_ziroom = is_ziroom;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setSign_time(String sign_time) {
        this.sign_time = sign_time;
    }

    @Override
    public String toString() {
        return house_code + ',' +
                kv_house_type + ',' +
                title + ',' +
                cover_pic + ',' +
                price_str + ',' +
                price_unit + ',' +
                is_ziroom + ',' +
                desc + ',' +
                sign_time
                ;
    }

}
