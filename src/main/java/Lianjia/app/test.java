package Lianjia.app;

import java.io.IOException;

public class test {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
//		Crawl.writeRentList(Crawl.crawlList(110000,0));
//		Crawl.crawAllList();
//		Crawl.tag2Utf8("rp1");
//		Crawl.crawlList("110000","c2020022262738910","rp1","l1","0");
//		String table = "C:\\Users\\liu jun yue\\eclipse-workspace\\Fang\\src\\main\\java\\Lianjia\\app\\table.csv";
//		Lianjia.Community.CommunityCrawl.crawlCommunity(table,"北京","https://bj.lianjia.com/xiaoqu/");
//		https://app.api.lianjia.com/house/rented/search?comunityIdRequest=c1111027375273&city_id=110000&sugCodition=c1111027375273&is_history=0&limit_offset=0&condition=c1111027375273&queryStringText=%E5%BC%98%E5%96%84%E5%AE%B6%E5%9B%AD&isFromMap=false&is_suggestion=1&limit_count=20
//		Crawl.crawlListContent("https://app.api.lianjia.com/config/config/initData?city_id=310000");
//		Crawl.crawAllList();
//		System.out.println(Crawl.crawlRentedHouse("BJ0002809513"));
//		Crawl.crawlAllRentedHouse();
//        Crawl.mainProgram();
		String[] cityList = {
				"深圳"};

		for (String cityId: cityList) {

			try {
				Crawl crawl = new Crawl(cityId);
				crawl.update();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}




	}
	


}
