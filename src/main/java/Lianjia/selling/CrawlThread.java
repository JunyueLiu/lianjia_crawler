package Lianjia.selling;



public class CrawlThread extends Thread{
	private String district;
	private String seed;
	private String savepath;
	
	
	
	public CrawlThread(String district, String seed, String savepath) {
		super();
		this.district = district;
		this.seed = seed;
		this.savepath = savepath;
	}



	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		//Utility.Crawl_District(district, seed, savepath);
		System.out.println(district);
	}
}
