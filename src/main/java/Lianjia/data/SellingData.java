package Lianjia.data;

import java.lang.reflect.Field;

public class SellingData {
	private String title;
	
	private String totalPrice;
	private String unitPrice;
	private String huxin;
	private String louceng;
	private String caoxiang;
	private String zhuangxiu;
	private String dianti;
	private String age;
	private String community;
	private String area;
	private String insideArea;
	private String gongnuan;
	private String chanquan;
	private String jiegou;
	private String jianzhuleixin;
	private String jianzhujiegou;
	private String tihubili;
	private String guapaishijian;
	private String shangcijiaoyi;
	private String fangwunianxian;
	private String jiaoyiquanshu;
	private String fangwuyongtu;
	private String chanquansuoshu;
	
	private String lon;
	private String lat;
	
	
	
	
	public String getDianti() {
		return dianti;
	}
	public void setDianti(String dianti) {
		this.dianti = dianti;
	}
	public String getInsideArea() {
		return insideArea;
	}
	public void setInsideArea(String insideArea) {
		this.insideArea = insideArea;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getHuxin() {
		return huxin;
	}
	public void setHuxin(String huxin) {
		this.huxin = huxin;
	}
	public String getLouceng() {
		return louceng;
	}
	public void setLouceng(String louceng) {
		this.louceng = louceng;
	}
	public String getCaoxiang() {
		return caoxiang;
	}
	public void setCaoxiang(String caoxiang) {
		this.caoxiang = caoxiang;
	}
	public String getZhuangxiu() {
		return zhuangxiu;
	}
	public void setZhuangxiu(String zhuangxiu) {
		this.zhuangxiu = zhuangxiu;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getCommunity() {
		return community;
	}
	public void setCommunity(String community) {
		this.community = community;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getGongnuan() {
		return gongnuan;
	}
	public void setGongnuan(String gongnuan) {
		this.gongnuan = gongnuan;
	}
	public String getChanquan() {
		return chanquan;
	}
	public void setChanquan(String chanquan) {
		this.chanquan = chanquan;
	}
	public String getJiegou() {
		return jiegou;
	}
	public void setJiegou(String jiegou) {
		this.jiegou = jiegou;
	}
	public String getJianzhuleixin() {
		return jianzhuleixin;
	}
	public void setJianzhuleixin(String jianzhuleixin) {
		this.jianzhuleixin = jianzhuleixin;
	}
	public String getJianzhujiegou() {
		return jianzhujiegou;
	}
	public void setJianzhujiegou(String jianzhujiegou) {
		this.jianzhujiegou = jianzhujiegou;
	}
	public String getTihubili() {
		return tihubili;
	}
	public void setTihubili(String tihubili) {
		this.tihubili = tihubili;
	}
	public String getGuapaishijian() {
		return guapaishijian;
	}
	public void setGuapaishijian(String guapaishijian) {
		this.guapaishijian = guapaishijian;
	}
	public String getShangcijiaoyi() {
		return shangcijiaoyi;
	}
	public void setShangcijiaoyi(String shangcijiaoyi) {
		this.shangcijiaoyi = shangcijiaoyi;
	}
	public String getFangwunianxian() {
		return fangwunianxian;
	}
	public void setFangwunianxian(String fangwunianxian) {
		this.fangwunianxian = fangwunianxian;
	}
	public String getJiaoyiquanshu() {
		return jiaoyiquanshu;
	}
	public void setJiaoyiquanshu(String jiaoyiquanshu) {
		this.jiaoyiquanshu = jiaoyiquanshu;
	}
	public String getFangwuyongtu() {
		return fangwuyongtu;
	}
	public void setFangwuyongtu(String fangwuyongtu) {
		this.fangwuyongtu = fangwuyongtu;
	}
	public String getChanquansuoshu() {
		return chanquansuoshu;
	}
	public void setChanquansuoshu(String chanquansuoshu) {
		this.chanquansuoshu = chanquansuoshu;
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
	@Override
	public String toString() {
		return "\""+title + "\",\"" + totalPrice + "\",\"" + unitPrice + "\",\"" + huxin + "\",\"" + louceng + "\",\""
				+ caoxiang + "\",\"" + zhuangxiu + "\",\"" + dianti + "\",\"" + age + "\",\"" + community + "\",\"" + area + "\",\""
				+ insideArea + "\",\"" + gongnuan + "\",\"" + chanquan + "\",\"" + jiegou + "\",\"" + jianzhuleixin + "\",\""
				+ jianzhujiegou + "\",\"" + tihubili + "\",\"" + guapaishijian + "\",\"" + shangcijiaoyi + "\",\"" + fangwunianxian
				+ "\",\"" + jiaoyiquanshu + "\",\"" + fangwuyongtu + "\",\"" + chanquansuoshu + "\",\"" + lon + "\",\"" + lat+"\"";
	}



	public String toLine(String delimiter) {
		return toString().replaceAll(", ", delimiter);	
	}
	public static String lineTitle(String delimiter) {
		String[] title = {"Title",
				"TotalPrice",
				"UnitPrice","HouseType","Floor","Orientation",
				"Decoration","Elevator","Age","Community","Area","InsideArea",
				"Heating","Right","Structure","BuildingType","BuildingStructure","Elevator&Door"
				,"StartSellingTime","LastTimeDeal","FixedNumberOfYearOfTheHouse",
				"DealingRightBelong","UseOfHouse","Share","lon","lat"};
		StringBuilder sBuilder = new StringBuilder();
		for(String tString:title) {
			sBuilder.append("\""+tString+"\"");
			sBuilder.append(delimiter);
			
		}
		sBuilder.deleteCharAt(sBuilder.length()-1);
		
		return sBuilder.toString();
	}
	
	

}
