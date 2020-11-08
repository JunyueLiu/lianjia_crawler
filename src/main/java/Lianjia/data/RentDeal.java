package Lianjia.data;

public class RentDeal {
    private String title;
    private String cityId;
    private String district;
    private String smallRegion;
    private String communityId;
    private String communityName;
    private String houseCode;
    private String mURL;
    private String ziru;
    private String area;
    private String decoration;

    private String orientation;

    private String status;
    private String originRent;
    private String dealRent;
    private String roomType;
    private String rentType;
    private String boradDate;
    private String dealDate;
    private String floor;
    private String elevator;
    private String heating;
    private String age;
    private String lot;
    private String rentTime;
    private String ruzhu;
    private String dealPeriod;
    private String changePrice;
    private String show;
    private String follow;
    private String lon;
    private String lat;
    public String getDecoration() {
        return decoration;
    }

    public void setDecoration(String decoration) {
        this.decoration = decoration;
    }

    public String getElevator() {
        return elevator;
    }

    public void setElevator(String elevator) {
        this.elevator = elevator;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    private String pitures;
    private String payway;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getSmallRegion() {
        return smallRegion;
    }

    public void setSmallRegion(String smallRegion) {
        this.smallRegion = smallRegion;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getHouseCode() {
        return houseCode;
    }

    public void setHouseCode(String houseCode) {
        this.houseCode = houseCode;
    }

    public String getmURL() {
        return mURL;
    }

    public void setmURL(String mURL) {
        this.mURL = mURL;
    }

    public String getZiru() {
        return ziru;
    }

    public void setZiru(String ziru) {
        this.ziru = ziru;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOriginRent() {
        return originRent;
    }

    public void setOriginRent(String originRent) {
        this.originRent = originRent;
    }

    public String getDealRent() {
        return dealRent;
    }

    public void setDealRent(String dealRent) {
        this.dealRent = dealRent;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRentType() {
        return rentType;
    }

    public void setRentType(String rentType) {
        this.rentType = rentType;
    }

    public String getBoradDate() {
        return boradDate;
    }

    public void setBoradDate(String boradDate) {
        this.boradDate = boradDate;
    }

    public String getDealDate() {
        return dealDate;
    }

    public void setDealDate(String dealDate) {
        this.dealDate = dealDate;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getHeating() {
        return heating;
    }

    public void setHeating(String heating) {
        this.heating = heating;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getRentTime() {
        return rentTime;
    }

    public void setRentTime(String rentTime) {
        this.rentTime = rentTime;
    }

    public String getRuzhu() {
        return ruzhu;
    }

    public void setRuzhu(String ruzhu) {
        this.ruzhu = ruzhu;
    }

    public String getDealPeriod() {
        return dealPeriod;
    }

    public void setDealPeriod(String dealPeriod) {
        this.dealPeriod = dealPeriod;
    }

    public String getChangePrice() {
        return changePrice;
    }

    public void setChangePrice(String changePrice) {
        this.changePrice = changePrice;
    }

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }

    public String getFollow() {
        return follow;
    }

    public void setFollow(String follow) {
        this.follow = follow;
    }

    public String getPitures() {
        return pitures;
    }

    public void setPitures(String pitures) {
        this.pitures = pitures;
    }

    public String getPayway() {
        return payway;
    }

    public void setPayway(String payway) {
        this.payway = payway;
    }

    public static String title(){
        final StringBuilder sb = new StringBuilder();
        sb.append("\"").append("title").append("\"");
        sb.append(",\"").append("cityId").append("\"");
        sb.append(",\"").append("district").append("\"");
        sb.append(",\"").append("smallRegion").append("\"");
        sb.append(",\"").append("communityId").append("\"");
        sb.append(",\"").append("communityName").append("\"");
        sb.append(",\"").append("houseCode").append("\"");
        sb.append(",\"").append("mURL").append("\"");
        sb.append(",\"").append("ziru").append("\"");
        sb.append(",\"").append("area").append("\"");
        sb.append(",\"").append("orientation").append("\"");
        sb.append(",\"").append("decoration").append("\"");
        sb.append(",\"").append("status").append("\"");
        sb.append(",\"").append("originRent").append("\"");
        sb.append(",\"").append("dealRent").append("\"");
        sb.append(",\"").append("roomType").append("\"");
        sb.append(",\"").append("rentType").append("\"");
        sb.append(",\"").append("boradDate").append("\"");
        sb.append(",\"").append("dealDate").append("\"");
        sb.append(",\"").append("floor").append("\"");
        sb.append(",\"").append("elevator").append("\"");
        sb.append(",\"").append("heating").append("\"");
        sb.append(",\"").append("age").append("\"");
        sb.append(",\"").append("lot").append("\"");
        sb.append(",\"").append("rentTime").append("\"");
        sb.append(",\"").append("ruzhu").append("\"");
        sb.append(",\"").append("dealPeriod").append("\"");
        sb.append(",\"").append("changePrice").append("\"");
        sb.append(",\"").append("show").append("\"");
        sb.append(",\"").append("follow").append("\"");
        sb.append(",\"").append("lon").append("\"");
        sb.append(",\"").append("lat").append("\"");
        sb.append(",\"").append("pitures").append("\"");
        sb.append(",\"").append("payway").append("\"");
        return sb.toString();
    }

    public static String chineseTitle(){
        final StringBuilder sb = new StringBuilder();
        sb.append("\"").append("标题").append("\"");
        sb.append(",\"").append("cityId").append("\"");
        sb.append(",\"").append("区").append("\"");
        sb.append(",\"").append("区域").append("\"");
        sb.append(",\"").append("communityId").append("\"");
        sb.append(",\"").append("小区").append("\"");
        sb.append(",\"").append("houseCode").append("\"");
        sb.append(",\"").append("mURL").append("\"");
        sb.append(",\"").append("自如房源").append("\"");
        sb.append(",\"").append("面积").append("\"");
        sb.append(",\"").append("朝向").append("\"");
        sb.append(",\"").append("装修").append("\"");
        sb.append(",\"").append("现状").append("\"");
        sb.append(",\"").append("挂牌价格").append("\"");
        sb.append(",\"").append("成交价格").append("\"");
        sb.append(",\"").append("房型").append("\"");
        sb.append(",\"").append("方式").append("\"");
        sb.append(",\"").append("挂牌日期").append("\"");
        sb.append(",\"").append("成交日期").append("\"");
        sb.append(",\"").append("楼层").append("\"");
        sb.append(",\"").append("电梯").append("\"");
        sb.append(",\"").append("供暖").append("\"");
        sb.append(",\"").append("年代").append("\"");
        sb.append(",\"").append("车位").append("\"");
        sb.append(",\"").append("租期").append("\"");
        sb.append(",\"").append("入住时间").append("\"");
        sb.append(",\"").append("成交周期（天）").append("\"");
        sb.append(",\"").append("调价（次）").append("\"");
        sb.append(",\"").append("带看（次）").append("\"");
        sb.append(",\"").append("关注（人）").append("\"");
        sb.append(",\"").append("lon").append("\"");
        sb.append(",\"").append("lat").append("\"");
        sb.append(",\"").append("pitures").append("\"");
        sb.append(",\"").append("付款方式").append("\"");
        return sb.toString();
    }




    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\"").append(title).append("\"");
        sb.append(",\"").append(cityId).append("\"");
        sb.append(",\"").append(district).append("\"");
        sb.append(",\"").append(smallRegion).append("\"");
        sb.append(",\"").append(communityId).append("\"");
        sb.append(",\"").append(communityName).append("\"");
        sb.append(",\"").append(houseCode).append("\"");
        sb.append(",\"").append(mURL).append("\"");
        sb.append(",\"").append(ziru).append("\"");
        sb.append(",\"").append(area).append("\"");
        sb.append(",\"").append(orientation).append("\"");
        sb.append(",\"").append(decoration).append("\"");
        sb.append(",\"").append(status).append("\"");
        sb.append(",\"").append(originRent).append("\"");
        sb.append(",\"").append(dealRent).append("\"");
        sb.append(",\"").append(roomType).append("\"");
        sb.append(",\"").append(rentType).append("\"");
        sb.append(",\"").append(boradDate).append("\"");
        sb.append(",\"").append(dealDate).append("\"");
        sb.append(",\"").append(floor).append("\"");
        sb.append(",\"").append(elevator).append("\"");
        sb.append(",\"").append(heating).append("\"");
        sb.append(",\"").append(age).append("\"");
        sb.append(",\"").append(lot).append("\"");
        sb.append(",\"").append(rentTime).append("\"");
        sb.append(",\"").append(ruzhu).append("\"");
        sb.append(",\"").append(dealPeriod).append("\"");
        sb.append(",\"").append(changePrice).append("\"");
        sb.append(",\"").append(show).append("\"");
        sb.append(",\"").append(follow).append("\"");
        sb.append(",\"").append(lon).append("\"");
        sb.append(",\"").append(lat).append("\"");
        sb.append(",\"").append(pitures).append("\"");
        sb.append(",\"").append(payway).append("\"");
        return sb.toString().replaceAll("RentPicture","");
    }
}
