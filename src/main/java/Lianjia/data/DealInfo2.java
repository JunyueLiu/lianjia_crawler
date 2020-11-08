package Lianjia.data;

import java.util.HashMap;

public class DealInfo2 extends DealInfo {
    private String city_id;
    private String city_abbr;
    private String city_name;
    private String lianjia_id;
    private String community_id;
    private String xiaoqujieshao;
    private String quanshudiya;
    private String huxinjieshao;
    private String hexinmaidian;
    private String shuifeijiexi;
    private String jiaotongchuxin;
    private String zhoubianpeitao;
    private String zhuangxiumiaoshu;
    private String shiyirenqun;
    private String other_Attribute;
    private String tag;
    private String WGS84_lon;
    private String WGS84_lat;
    private String yezhuzijian;
    private HashMap<String, String> imageMap;


    public String getZhoubianpeitao() {
        return zhoubianpeitao;
    }

    public void setZhoubianpeitao(String zhoubianpeitao) {
        this.zhoubianpeitao = zhoubianpeitao;
    }

    public String getZhuangxiumiaoshu() {
        return zhuangxiumiaoshu;
    }

    public void setZhuangxiumiaoshu(String zhuangxiumiaoshu) {
        this.zhuangxiumiaoshu = zhuangxiumiaoshu;
    }

    public String getShiyirenqun() {
        return shiyirenqun;
    }

    public void setShiyirenqun(String shiyirenqun) {
        this.shiyirenqun = shiyirenqun;
    }

    public HashMap<String, String> getImageMap() {
        return imageMap;
    }

    public void setImageMap(HashMap<String, String> imageMap) {
        this.imageMap = imageMap;
    }

    public String getYezhuzijian() {
        return yezhuzijian;
    }

    public void setYezhuzijian(String yezhuzijian) {
        this.yezhuzijian = yezhuzijian;
    }

    public String getJiaotongchuxin() {
        return jiaotongchuxin;
    }

    public void setJiaotongchuxin(String jiaotongchuxin) {
        this.jiaotongchuxin = jiaotongchuxin;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getCity_abbr() {
        return city_abbr;
    }

    public void setCity_abbr(String city_abbr) {
        this.city_abbr = city_abbr;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getWGS84_lon() {
        return WGS84_lon;
    }

    public void setWGS84_lon(String wGS84_lon) {
        WGS84_lon = wGS84_lon;
    }

    public String getWGS84_lat() {
        return WGS84_lat;
    }

    public void setWGS84_lat(String wGS84_lat) {
        WGS84_lat = wGS84_lat;
    }

    public String getLianjia_id() {
        return lianjia_id;
    }

    public void setLianjia_id(String lianjia_id) {
        this.lianjia_id = lianjia_id;
    }

    public String getCommunity_id() {
        return community_id;
    }

    public void setCommunity_id(String community_id) {
        this.community_id = community_id;
    }

    public String getXiaoqujieshao() {
        return xiaoqujieshao;
    }

    public void setXiaoqujieshao(String xiaoqujieshao) {
        this.xiaoqujieshao = xiaoqujieshao;
    }

    public String getQuanshudiya() {
        return quanshudiya;
    }

    public void setQuanshudiya(String quanshudiya) {
        this.quanshudiya = quanshudiya;
    }

    public String getHuxinjieshao() {
        return huxinjieshao;
    }

    public void setHuxinjieshao(String huxinjieshao) {
        this.huxinjieshao = huxinjieshao;
    }

    public String getHexinmaidian() {
        return hexinmaidian;
    }

    public void setHexinmaidian(String hexinmaidian) {
        this.hexinmaidian = hexinmaidian;
    }

    public String getShuifeijiexi() {
        return shuifeijiexi;
    }

    public void setShuifeijiexi(String shuifeijiexi) {
        this.shuifeijiexi = shuifeijiexi;
    }

    public String getOther_Attribute() {
        return other_Attribute;
    }

    public void setOther_Attribute(String other_Attribute) {
        this.other_Attribute = other_Attribute;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "\""+city_id + "\",\"" + city_abbr + "\",\"" + city_name + "\",\"" + lianjia_id + "\",\"" + community_id + "\"," + super.toString() + ",\"" + xiaoqujieshao
                + "\",\"" + quanshudiya + "\",\"" + huxinjieshao + "\",\"" + hexinmaidian + "\",\"" + shuifeijiexi + "\",\""
                + jiaotongchuxin + "\",\"" + other_Attribute + "\",\"" + tag + "\",\"" + WGS84_lon + "\",\"" + WGS84_lat + "\",\""
                + yezhuzijian + "\",\"" + imageMap.toString().replaceAll(",", ";") + "\"";
    }

    public static String lineTitle(String delimiter) {
        String[] title = {"city_id", "city_abbr", "city_name", "lianjia_id", "community_id", "Title",
                "TotalPrice",
                "UnitPrice","HouseType","Floor","Orientation",
                "Decoration","Elevator","Age","Community","Area","InsideArea",
                "Heating","Right","Structure","BuildingType","BuildingStructure","Elevator&Door"
                ,"StartSellingTime","LastTimeDeal","FixedNumberOfYearOfTheHouse",
                "DealingRightBelong","UseOfHouse","Share","lon","lat","Url", "dealDate", "sellingPrice", "daySelling", "changePrice", "num_show", "num_follow", "num_read", "small_region", "xiaoqujieshao",
                "quanshudiya", "huxinjieshao", "hexinmaidian", "shuifeijiexi", "jiaotongchuxin", "other_Attribute", "tag", "WGS84_lon", "WGS84_lat", "yezhuzijian", "image"};
        StringBuilder sBuilder = new StringBuilder();

        for (String tString : title) {
            sBuilder.append("\"" + tString + "\"");
            sBuilder.append(delimiter);

        }
        sBuilder.deleteCharAt(sBuilder.length() - 1);

        return sBuilder.toString();
    }


}
