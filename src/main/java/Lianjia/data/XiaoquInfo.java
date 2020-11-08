package Lianjia.data;

import databaseUtilities.MysqlUtility;

import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;

public class XiaoquInfo {
	private String city;
	private String district;
	private String small_region;
	private String name;
	private String address;
	private String community_id;
	private ArrayList<String> pictures;
	private String pageUrl;
	private String age;
	private String type;
	private String fee;
	private String ManageCompany;
	private String ConstructionCompany;
	private String numBuilding;
	private String numHousing;
	private String sellingUrl;
	private String dealUrl;
	private String rentUrl;
	private String location;
	/*
	 * CREATE TABLE `lianjia`.`community` (
  `city` VARCHAR(10) NULL,
  `district` VARCHAR(10) NULL,
  `small_region` VARCHAR(45) NULL,
  `address` VARCHAR(100) NULL,
  `community` VARCHAR(100) NULL,
  `community_id` VARCHAR(45) NOT NULL,
  `url` VARCHAR(100) NULL,
  `age` VARCHAR(45) NULL,
  `type` VARCHAR(45) NULL,
  `fee` VARCHAR(45) NULL,
  `manage_company` VARCHAR(45) NULL,
  `construction_company` VARCHAR(45) NULL,
  `num_building` VARCHAR(45) NULL,
  `num_housing` VARCHAR(45) NULL,
  `selling_url` VARCHAR(100) NULL,
  `deal_url` VARCHAR(100) NULL,
  `rent_url` VARCHAR(100) NULL,
  PRIMARY KEY (`community_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

	 * */
	
	public void writeMysql(Connection connection,String table_name) {
		MysqlUtility.inserct(connection, table_name, 
				getCity(),getDistrict(),getSmall_region(),getAddress(),getName(),getCommunity_id(),
				getPageUrl(),getAge(),getType(),getFee(),getManageCompany(),getConstructionCompany(),
				getNumBuilding(),getNumHousing(),getSellingUrl(),getDealUrl(),getRentUrl());
		
		
	}


	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
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
	public String getSmall_region() {
		return small_region;
	}
	public void setSmall_region(String small_region) {
		this.small_region = small_region;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCommunity_id() {
		return community_id;
	}
	public void setCommunity_id(String community_id) {
		this.community_id = community_id;
	}
	
	public ArrayList<String> getPictures() {
		return pictures;
	}
	public void setPictures(ArrayList<String> pictures) {
		this.pictures = pictures;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPageUrl() {
		return pageUrl;
	}
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public String getManageCompany() {
		return ManageCompany;
	}
	public void setManageCompany(String manageCompany) {
		ManageCompany = manageCompany;
	}
	public String getConstructionCompany() {
		return ConstructionCompany;
	}
	public void setConstructionCompany(String constructionCompany) {
		ConstructionCompany = constructionCompany;
	}
	public String getNumBuilding() {
		return numBuilding;
	}
	public void setNumBuilding(String numBuilding) {
		this.numBuilding = numBuilding;
	}
	public String getNumHousing() {
		return numHousing;
	}
	public void setNumHousing(String numHousing) {
		this.numHousing = numHousing;
	}
	public String getSellingUrl() {
		return sellingUrl;
	}
	public void setSellingUrl(String sellingUrl) {
		this.sellingUrl = sellingUrl;
	}
	public String getDealUrl() {
		return dealUrl;
	}
	public void setDealUrl(String dealUrl) {
		this.dealUrl = dealUrl;
	}
	public String getRentUrl() {
		return rentUrl;
	}
	public void setRentUrl(String rentUrl) {
		this.rentUrl = rentUrl;
	}
	
	@Override
	public String toString() {
		return "\""+name + "\",\"" + pageUrl + "\",\"" + age + "\",\"" + type + "\",\"" + fee + "\",\"" + ManageCompany + "\",\""
				+ ConstructionCompany + "\",\"" + numBuilding + "\",\"" + numHousing + "\",\"" + sellingUrl + "\",\"" + dealUrl
				+ "\",\"" + rentUrl+"\"";
	}
	public static String title() {
		return "name,pageUrl,age,type,fee,ManageCompany,ConstructionCompany,numBuilding,numHousing,sellingUrl,dealUrl,rentUrl";
		
	}
	
	
}
