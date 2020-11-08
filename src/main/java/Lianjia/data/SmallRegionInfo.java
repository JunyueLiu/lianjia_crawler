package Lianjia.data;

public class SmallRegionInfo {
	private String city;
	private String district;
	private String smallReion;
	private String smallRegionUrl;
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getSmallReion() {
		return smallReion;
	}
	public void setSmallReion(String smallReion) {
		this.smallReion = smallReion;
	}
	public String getSmallRegionUrl() {
		return smallRegionUrl;
	}
	public void setSmallRegionUrl(String smallRegionUrl) {
		this.smallRegionUrl = smallRegionUrl;
	}
	@Override
	public String toString() {
		return city + "," + district + "," + smallReion + "," + smallRegionUrl;
	}
	
	

}
