package Lianjia;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;


import Lianjia.data.DealInfo2;
import Lianjia.data.SmallRegionInfo;
import databaseUtilities.MysqlUtility;

public class MySqlsetup {
	public static void createDealcityTable(String user, String password) {
		Connection connection = null;
		try {
			connection = databaseUtilities.MysqlUtility.getConnection(
					"jdbc:mysql://localhost:3306/"
							+ "sys?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false",
					user, password);
			MysqlUtility.createDatabase(connection, "lianjia");
			connection.createStatement().executeQuery("USE lianjia");
			MysqlUtility.createTable(connection, "deal_city",
					new String[] { MysqlUtility.columsBuilder("city", "VARCHAR(10)", false),
							MysqlUtility.columsBuilder("deal_root_url", "VARCHAR(100)", false),
							"PRIMARY KEY (deal_root_url)"

					}, "InnoDB", "utf8");

			// INSERT INTO `lianjia`.`deal_city` (`city`, `deal_root_url`) VALUES ('北京',
			// 'https://bj.lianjia.com/chengjiao/');
			// INSERT INTO `lianjia`.`deal_city` (`city`, `deal_root_url`) VALUES ('上海',
			// 'https://sh.lianjia.com/chengjiao/');
			// INSERT INTO `lianjia`.`deal_city` (`city`, `deal_root_url`) VALUES ('广州',
			// 'https://gz.lianjia.com/chengjiao/');
			// INSERT INTO `lianjia`.`deal_city` (`city`, `deal_root_url`) VALUES ('深圳',
			// 'https://sz.lianjia.com/chengjiao/');
			// INSERT INTO `lianjia`.`deal_city` (`city`, `deal_root_url`) VALUES ('成都',
			// 'https://cd.lianjia.com/chengjiao/');
			// INSERT INTO `lianjia`.`deal_city` (`city`, `deal_root_url`) VALUES ('南京',
			// 'https://nj.lianjia.com/chengjiao/');
			// INSERT INTO `lianjia`.`deal_city` (`city`, `deal_root_url`) VALUES ('杭州',
			// 'https://hz.lianjia.com/chengjiao/');
			// INSERT INTO `lianjia`.`deal_city` (`city`, `deal_root_url`) VALUES ('青岛',
			// 'https://qd.lianjia.com/chengjiao/');
			// INSERT INTO `lianjia`.`deal_city` (`city`, `deal_root_url`) VALUES ('大连',
			// 'https://dl.lianjia.com/chengjiao/');
			// INSERT INTO `lianjia`.`deal_city` (`city`, `deal_root_url`) VALUES ('苏州',
			// 'https://su.lianjia.com/chengjiao/');
			//
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getErrorCode());
			e.printStackTrace();
		}

	}

	public static void createDealSmallRegionTable(String user, String password) {
		Connection connection = null;
		try {
			connection = databaseUtilities.MysqlUtility.getConnection(
					"jdbc:mysql://localhost:3306/"
							+ "sys?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false",
					user, password);
			MysqlUtility.createDatabase(connection, "lianjia");
			connection.createStatement().executeQuery("USE lianjia");
			MysqlUtility.createTable(connection, "smallRegion",
					new String[] { MysqlUtility.columsBuilder("city", "VARCHAR(10)", false),
							MysqlUtility.columsBuilder("district", "VARCHAR(10)", false),
							MysqlUtility.columsBuilder("small_region", "VARCHAR(45)", false),

							MysqlUtility.columsBuilder("small_region_url", "VARCHAR(100)", false),
							"PRIMARY KEY (small_region_url)"

					}, "InnoDB", "utf8");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getErrorCode());
			e.printStackTrace();
		}

	}

	public static void insertSmallRegionInfo(String user, String password, SmallRegionInfo smallRegionInfo) {

		Connection connection = null;
		try {
			connection = databaseUtilities.MysqlUtility.getConnection(
					"jdbc:mysql://localhost:3306/"
							+ "sys?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false",
					user, password);

			MysqlUtility.createDatabase(connection, "lianjia");
			connection.createStatement().executeQuery("USE lianjia");

			MysqlUtility.inserct(connection, "smallRegion", smallRegionInfo.getCity(), smallRegionInfo.getDistrict(),
					smallRegionInfo.getSmallReion(), smallRegionInfo.getSmallRegionUrl());

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getErrorCode());
			e.printStackTrace();
		}

	}

	public static void createDealTable(String user, String password) {
		Connection connection = null;
		try {
			connection = databaseUtilities.MysqlUtility.getConnection(
					"jdbc:mysql://localhost:3306/"
							+ "sys?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false",
					user, password);
			MysqlUtility.createDatabase(connection, "lianjia");
			connection.createStatement().executeQuery("USE lianjia");
			// city_id + "," + city_abbr + "," + city_name + "," + lianjia_id + "," +
			// community_id + ","
			// title + "," + totalPrice + "," + unitPrice + "," + huxin + "," + louceng +
			// ","
			// + caoxiang + "," + zhuangxiu + "," + dianti + "," + age + "," + community +
			// "," + area + ","
			// + insideArea + "," + gongnuan + "," + chanquan + "," + jiegou + "," +
			// jianzhuleixin + ","
			// + jianzhujiegou + "," + tihubili + "," + guapaishijian + "," + shangcijiaoyi
			// + "," + fangwunianxian
			// + "," + jiaoyiquanshu + "," + fangwuyongtu + "," + chanquansuoshu + "," + lon
			// + "," + lat;
			// +","+ url + "," + dealDate + "," + sellingPrice + "," + daySelling + "," +
			// changePrice + ","
			// + num_show + "," + num_follow + "," + num_read+","+small_region;
			// ","+ xiaoqujieshao
			// + "," + quanshudiya + "," + huxinjieshao + "," + hexinmaidian + "," +
			// shuifeijiexi + ","
			// + jiaotongchuxin + "," + other_Attribute + "," + tag + "," + WGS84_lon + ","
			// + WGS84_lat + ","
			// + yezhuzijian + "," + imageMap;
			//
			MysqlUtility.createTable(connection, "deal", new String[] {
					// city_id + "," + city_abbr + "," + city_name + "," + lianjia_id + "," +
					// community_id + ","
					MysqlUtility.columsBuilder("city_id", "VARCHAR(20)", false),
					MysqlUtility.columsBuilder("city_abbr", "VARCHAR(10)", false),
					MysqlUtility.columsBuilder("city_name", "VARCHAR(45)", false),
					MysqlUtility.columsBuilder("lianjia_id", "VARCHAR(45)", false),
					MysqlUtility.columsBuilder("community_id", "VARCHAR(45)", false),
					// title + "," + totalPrice + "," + unitPrice + "," + huxin + "," + louceng +
					// ","
					// + caoxiang + "," + zhuangxiu + "," + dianti + "," + age + "," + community +
					// "," + area + ","
					// + insideArea + "," + gongnuan + "," + chanquan + "," + jiegou + "," +
					// jianzhuleixin + ","
					MysqlUtility.columsBuilder("title", "VARCHAR(100)", false),
					MysqlUtility.columsBuilder("totalPrice", "VARCHAR(45)", false),
					MysqlUtility.columsBuilder("unitPrice", "VARCHAR(45)", false),
					MysqlUtility.columsBuilder("huxin", "VARCHAR(45)", false),
					MysqlUtility.columsBuilder("louceng", "VARCHAR(45)", false),
					MysqlUtility.columsBuilder("caoxiang", "VARCHAR(45)", false),
					MysqlUtility.columsBuilder("zhuangxiu", "VARCHAR(45)", false),
					MysqlUtility.columsBuilder("dianti", "VARCHAR(45)", false),
					MysqlUtility.columsBuilder("age", "VARCHAR(45)", false),
					MysqlUtility.columsBuilder("community", "VARCHAR(45)", false),
					MysqlUtility.columsBuilder("area", "VARCHAR(45)", false),
					MysqlUtility.columsBuilder("insideArea", "VARCHAR(45)", false),
					MysqlUtility.columsBuilder("gongnuan", "VARCHAR(45)", false),
					MysqlUtility.columsBuilder("chanquan", "VARCHAR(45)", false),
					MysqlUtility.columsBuilder("jiegou", "VARCHAR(45)", false),
					MysqlUtility.columsBuilder("jianzhuleixin", "VARCHAR(45)", false),
					// + jianzhujiegou + "," + tihubili + "," + guapaishijian + "," + shangcijiaoyi
					// + "," + fangwunianxian
					// + "," + jiaoyiquanshu + "," + fangwuyongtu + "," + chanquansuoshu + "," + lon
					// + "," + lat;
					// +","+ url + "," + dealDate + "," + sellingPrice + "," + daySelling + "," +
					// changePrice + ","
					// + num_show + "," + num_follow + "," + num_read+","+small_region;
					// ","+ xiaoqujieshao
					MysqlUtility.columsBuilder("jianzhujiegou", "VARCHAR(45)", true),
					MysqlUtility.columsBuilder("tihubili", "VARCHAR(45)", true),
					MysqlUtility.columsBuilder("guapaishijian", "VARCHAR(45)", true),
					MysqlUtility.columsBuilder("shangcijiaoyi", "MEDIUMTEXT", true),
					MysqlUtility.columsBuilder("fangwunianxian", "VARCHAR(45)", true),
					MysqlUtility.columsBuilder("jiaoyiquanshu", "VARCHAR(45)", true),
					MysqlUtility.columsBuilder("fangwuyongtu", "VARCHAR(45)", true),
					MysqlUtility.columsBuilder("chanquansuoshu", "VARCHAR(45)", true),
					MysqlUtility.columsBuilder("lon", "VARCHAR(45)", true),
					MysqlUtility.columsBuilder("lat", "VARCHAR(45)", true),
					MysqlUtility.columsBuilder("url", "VARCHAR(150)", false),
					MysqlUtility.columsBuilder("dealDate", "VARCHAR(45)", true),
					MysqlUtility.columsBuilder("sellingPrice", "VARCHAR(45)", true),
					MysqlUtility.columsBuilder("daySelling", "VARCHAR(45)", true),
					MysqlUtility.columsBuilder("changePrice", "VARCHAR(45)", true),
					MysqlUtility.columsBuilder("num_show", "VARCHAR(45)", true),
					MysqlUtility.columsBuilder("num_follow", "VARCHAR(45)", true),
					MysqlUtility.columsBuilder("num_read", "VARCHAR(45)", true),
					MysqlUtility.columsBuilder("small_region", "VARCHAR(45)", true),

					// + "," + quanshudiya + "," + huxinjieshao + "," + hexinmaidian + "," +
					// shuifeijiexi + ","
					// + jiaotongchuxin + "," + other_Attribute + "," + tag + "," + WGS84_lon + ","
					// + WGS84_lat + ","
					// + yezhuzijian + "," + imageMap;

					MysqlUtility.columsBuilder("xiaoqujieshao", "VARCHAR(200)", true),
					MysqlUtility.columsBuilder("quanshudiya", "VARCHAR(200)", true),
					MysqlUtility.columsBuilder("huxinjieshao", "VARCHAR(200)", true),
					MysqlUtility.columsBuilder("hexinmaidian", "VARCHAR(200)", true),
					MysqlUtility.columsBuilder("shuifeijiexi", "VARCHAR(200)", true),
					MysqlUtility.columsBuilder("jiaotongchuxin", "VARCHAR(200)", true),
					MysqlUtility.columsBuilder("zhoubianpeitao", "VARCHAR(200)", true),
					MysqlUtility.columsBuilder("zhuangxiumiaoshu", "VARCHAR(200)", true),
					MysqlUtility.columsBuilder("shiyirenqun", "VARCHAR(200)", true),
					MysqlUtility.columsBuilder("other_Attribute", "VARCHAR(200)", true),
					MysqlUtility.columsBuilder("tag", "VARCHAR(45)", true),
					MysqlUtility.columsBuilder("WGS84_lon", "VARCHAR(45)", true),
					MysqlUtility.columsBuilder("WGS84_lat", "VARCHAR(45)", true),
					MysqlUtility.columsBuilder("yezhuzijian", "MEDIUMTEXT", true),
					MysqlUtility.columsBuilder("imageMap", "LONGTEXT", false),
					MysqlUtility.columsBuilder("GatheringTime", "DATE", false), "PRIMARY KEY (url)"

			}, "InnoDB", "utf8");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getErrorCode());
			e.printStackTrace();
		}

	}

	public static void insertDeal(String user, String password, DealInfo2 deal_Info2) {

		Connection connection = null;
		try {
			connection = databaseUtilities.MysqlUtility.getConnection(
					"jdbc:mysql://localhost:3306/"
							+ "sys?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false",
					user, password);

			MysqlUtility.createDatabase(connection, "lianjia");
			connection.createStatement().executeQuery("USE lianjia");

			MysqlUtility.inserct(connection, "deal", deal_Info2.getCity_id(), deal_Info2.getCity_abbr(),
					deal_Info2.getCity_name(), deal_Info2.getLianjia_id(), deal_Info2.getCommunity_id(),
					deal_Info2.getTitle(), deal_Info2.getTotalPrice(), deal_Info2.getUnitPrice(), deal_Info2.getHuxin(),
					deal_Info2.getLouceng(), deal_Info2.getCaoxiang(), deal_Info2.getZhuangxiu(),
					deal_Info2.getDianti(), deal_Info2.getAge(), deal_Info2.getCommunity(), deal_Info2.getArea(),
					deal_Info2.getInsideArea(), deal_Info2.getGongnuan(), deal_Info2.getChanquan(),
					deal_Info2.getJiegou(), deal_Info2.getJianzhuleixin(), deal_Info2.getJianzhujiegou(),
					deal_Info2.getTihubili(), deal_Info2.getGuapaishijian(), deal_Info2.getShangcijiaoyi(),
					deal_Info2.getFangwunianxian(), deal_Info2.getJiaoyiquanshu(), deal_Info2.getFangwuyongtu(),
					deal_Info2.getChanquansuoshu(), deal_Info2.getLon(), deal_Info2.getLat(), deal_Info2.getUrl(),
					deal_Info2.getDealDate(), deal_Info2.getSellingPrice(), deal_Info2.getDaySelling(),
					deal_Info2.getChangePrice(), deal_Info2.getNum_show(), deal_Info2.getNum_follow(),
					deal_Info2.getNum_read(), deal_Info2.getSmall_region(), deal_Info2.getXiaoqujieshao(),
					deal_Info2.getQuanshudiya(), deal_Info2.getHuxinjieshao(), deal_Info2.getHexinmaidian(),
					deal_Info2.getShuifeijiexi(), deal_Info2.getJiaotongchuxin(), deal_Info2.getZhoubianpeitao(),
					deal_Info2.getZhuangxiumiaoshu(), deal_Info2.getShiyirenqun(), deal_Info2.getOther_Attribute(),
					deal_Info2.getTag(), deal_Info2.getWGS84_lon(), deal_Info2.getWGS84_lat(),
					deal_Info2.getYezhuzijian(), deal_Info2.getImageMap().toString(),
					new Date(System.currentTimeMillis()));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getErrorCode());
			e.printStackTrace();
		}

	}

public static void insertDeallist(String user,String password, ArrayList<DealInfo2> deal_Info2list) throws SQLIntegrityConstraintViolationException {
		
		Connection connection = null;
		try {
			connection = databaseUtilities.MysqlUtility.getConnection("jdbc:mysql://localhost:3306/"
					+ "sys?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false", 
					user, 
					password);
			MysqlUtility.createDatabase(connection, "lianjia");
			connection.createStatement().executeQuery("USE lianjia");
//			city_id + "," + city_abbr + "," + city_name + "," + lianjia_id + "," + community_id + ","
//			title + "," + totalPrice + "," + unitPrice + "," + huxin + "," + louceng + ","
//+ caoxiang + "," + zhuangxiu + "," + dianti + "," + age + "," + community + "," + area + ","
//+ insideArea + "," + gongnuan + "," + chanquan + "," + jiegou + "," + jianzhuleixin + ","
//+ jianzhujiegou + "," + tihubili + "," + guapaishijian + "," + shangcijiaoyi + "," + fangwunianxian
//+ "," + jiaoyiquanshu + "," + fangwuyongtu + "," + chanquansuoshu + "," + lon + "," + lat;
//			+","+ url + "," + dealDate + ","  + sellingPrice + "," + daySelling + "," + changePrice + ","
//			+ num_show + "," + num_follow + "," + num_read+","+small_region;
//			","+ xiaoqujieshao
//			+ "," + quanshudiya + "," + huxinjieshao + "," + hexinmaidian + "," + shuifeijiexi + ","
//			+ jiaotongchuxin + "," + other_Attribute + "," + tag + "," + WGS84_lon + "," + WGS84_lat + ","
//			+ yezhuzijian + "," + imageMap;
			
			for (DealInfo2 deal_Info22 : deal_Info2list) {
				MysqlUtility.inserct(connection, "deal",
					deal_Info22.getCity_id(),
					deal_Info22.getCity_abbr(),
					deal_Info22.getCity_name(),
					deal_Info22.getLianjia_id(),
					deal_Info22.getCommunity_id(),
					deal_Info22.getTitle(),
					deal_Info22.getTotalPrice(),
					deal_Info22.getUnitPrice(),
					deal_Info22.getHuxin(),
					deal_Info22.getLouceng(),
					deal_Info22.getCaoxiang(),
					deal_Info22.getZhuangxiu(),
					deal_Info22.getDianti(),
					deal_Info22.getAge(),
					deal_Info22.getCommunity(),
					deal_Info22.getArea(),
					deal_Info22.getInsideArea(),
					deal_Info22.getGongnuan(),
					deal_Info22.getChanquan(),
					deal_Info22.getJiegou(),
					deal_Info22.getJianzhuleixin(),
					deal_Info22.getJianzhujiegou(),
					deal_Info22.getTihubili(),
					deal_Info22.getGuapaishijian(),
					deal_Info22.getShangcijiaoyi(),
					deal_Info22.getFangwunianxian(),
					deal_Info22.getJiaoyiquanshu(),
					deal_Info22.getFangwuyongtu(),
					deal_Info22.getChanquansuoshu(),
					deal_Info22.getLon(),
					deal_Info22.getLat(),
					deal_Info22.getUrl(),
					deal_Info22.getDealDate(),
					deal_Info22.getSellingPrice(),
					deal_Info22.getDaySelling(),
					deal_Info22.getChangePrice(),
					deal_Info22.getNum_show(),
					deal_Info22.getNum_follow(),
					deal_Info22.getNum_read(),
					deal_Info22.getSmall_region(),
					deal_Info22.getXiaoqujieshao(),
					deal_Info22.getQuanshudiya(),
					deal_Info22.getHuxinjieshao(),
					deal_Info22.getHexinmaidian(),
					deal_Info22.getShuifeijiexi(),
					deal_Info22.getJiaotongchuxin(),
					deal_Info22.getZhoubianpeitao(),
					deal_Info22.getZhuangxiumiaoshu(),
					deal_Info22.getShiyirenqun(),
					deal_Info22.getOther_Attribute(),
					deal_Info22.getTag(),
					deal_Info22.getWGS84_lon(),
					deal_Info22.getWGS84_lat(),
					deal_Info22.getYezhuzijian(),
					deal_Info22.getImageMap().toString(),
					new Date(System.currentTimeMillis())
				);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getErrorCode());
			e.printStackTrace();
		}
		
		
	}

}
