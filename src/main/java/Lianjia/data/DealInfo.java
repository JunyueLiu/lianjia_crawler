package Lianjia.data;


public class DealInfo extends SellingData {
	private String url;
	
	private String dealDate;
	

	private String sellingPrice;
	private String daySelling;
	private String changePrice;
	private String num_show;
	private String num_follow;
	private String num_read;
	private String small_region;
	
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getDealDate() {
		return dealDate;
	}
	public void setDealDate(String dealDate) {
		this.dealDate = dealDate;
	}
	
	public String getSellingPrice() {
		return sellingPrice;
	}
	public void setSellingPrice(String sellingPrice) {
		this.sellingPrice = sellingPrice;
	}
	public String getDaySelling() {
		return daySelling;
	}
	public void setDaySelling(String daySelling) {
		this.daySelling = daySelling;
	}
	public String getChangePrice() {
		return changePrice;
	}
	public void setChangePrice(String changePrice) {
		this.changePrice = changePrice;
	}
	public String getNum_show() {
		return num_show;
	}
	public void setNum_show(String num_show) {
		this.num_show = num_show;
	}
	public String getNum_follow() {
		return num_follow;
	}
	public void setNum_follow(String num_follow) {
		this.num_follow = num_follow;
	}
	public String getNum_read() {
		return num_read;
	}
	public void setNum_read(String num_read) {
		this.num_read = num_read;
	}
	
	public String getSmall_region() {
		return small_region;
	}
	public void setSmall_region(String small_region) {
		this.small_region = small_region;
	}
	@Override
	public String toString() {
		return super.toString()+",\""+ url + "\",\"" + dealDate + "\",\""  + sellingPrice + "\",\"" + daySelling + "\",\"" + changePrice + "\",\""
				+ num_show + "\",\"" + num_follow + "\",\"" + num_read+"\",\""+small_region+"\"";
	}
	
	
	
	public static String lineTitle(String delimiter) {
		String[] title = {"Url","dealDate","sellingPrice","daySelling","changePrice","num_show","num_follow","num_read","small_region"};
		StringBuilder sBuilder = new StringBuilder();
		
		for(String tString:title) {
			sBuilder.append("\""+tString+"\"");
			sBuilder.append(delimiter);
			
		}
		sBuilder.deleteCharAt(sBuilder.length()-1);
		
		return sBuilder.toString();
	}
	



}
