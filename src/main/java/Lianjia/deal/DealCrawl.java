package Lianjia.deal;

import java.io.*;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.Map.Entry;
import java.util.concurrent.*;

import Lianjia.data.CityInfo;
import Lianjia.data.DealInfo;
import Lianjia.data.DealInfo2;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.stream.JsonReader;

import dataPreProcessing.coordinate.Gps;
import dataPreProcessing.coordinate.PositionUtil;
import Lianjia.MySqlsetup;
import Lianjia.data.SmallRegionInfo;
import databaseUtilities.MysqlUtility;

/**
 * @author liu jun yue
 *
 */
public class DealCrawl {


	private String mysqlURL;
//	private String configFold;
	private String user;
	private String password;
	private String save_path;
	private String configPath;
	private String theoreticalTotal;
	private String cityId;
	private String cityName;
	private String cityAbbr;
	private String cityDealUrl;



	private HashSet<String> crawled = new HashSet();
	private int lastCrawled = 0;
	private int lastBatchCrawled = 0;
//	private Date date = new Date(System.currentTimeMillis());




	public DealCrawl(Properties properties,String cityName){
		this.mysqlURL = properties.getProperty("mysqlURL");
		this.user = properties.getProperty("mysqlUser");
		this.password = properties.getProperty("mysqlPassword");
		this.save_path = new File(properties.getProperty("save_path"),"Deal").getAbsolutePath();
		this.configPath = properties.getProperty("config_fold");
		this.cityName = cityName;
		readCityConfig();
	}



	
	
	public DealInfo2 getDealInfo2(String deal_page) {
		DealInfo2 dealInfo = new DealInfo2();

		dealInfo.setUrl(deal_page);
		int c = 0;
		Document document = null;
		while (true) {
			try {

				document = Jsoup.connect(deal_page).get();
				// title
				Elements elements = document.getElementsByClass("wrapper");
				Element element = elements.first();
				elements = element.getElementsByTag("span");

				dealInfo.setDealDate(elements.first().text().replaceAll("成交", "").trim());
				dealInfo.setTitle(element.text().replaceAll(",", ";"));

				elements = document.getElementsByClass("price");
				element = elements.first();
				elements = element.getElementsByTag("span");

				dealInfo.setTotalPrice(elements.text());
				elements = element.getElementsByTag("b");
				dealInfo.setUnitPrice(elements.text());

				elements = document.getElementsByClass("deal-bread").first().getElementsByTag("a");
				dealInfo.setSmall_region(elements.get(3).text().replace("二手房成交价格", ""));

				// basic information
				element = document.getElementsByClass("msg").first();
				elements = element.getElementsByTag("span");
				for (Element e : elements) {
					String text = e.text();
					element = e.getElementsByTag("label").first();
					String value = element.text();
					if (text.contains("挂牌")) {
						dealInfo.setSellingPrice(value);
					} else if (text.contains("成交周期")) {
						dealInfo.setDaySelling(value);
					} else if (text.contains("调价")) {
						dealInfo.setChangePrice(value);
					} else if (text.contains("带看")) {
						dealInfo.setNum_show(value);
					} else if (text.contains("关注")) {
						dealInfo.setNum_follow(value);
					} else if (text.contains("浏览")) {
						dealInfo.setNum_read(value);
					}

				}
				// further information
				elements = document.getElementsByClass("base");
				elements = elements.get(0).getElementsByTag("li");
				for (Element element2 : elements) {
					Element element3 = element2.getElementsByTag("span").get(0);
					String lab = element3.text();
					String value = element2.text().replace(lab, "");

					/**
					 * 房屋户型 所在楼层 建筑面积 户型结构 套内面积 建筑类型 房屋朝向 建筑结构 装修情况 梯户比例 供暖方式 配备电梯 产权年限
					 * 
					 */
					if ("房屋户型".equals(lab)) {
						dealInfo.setHuxin(value);
					} else if ("所在楼层".equals(lab)) {
						dealInfo.setLouceng(value);
					} else if ("建筑面积".equals(lab)) {
						dealInfo.setArea(value);
					} else if ("户型结构".equals(lab)) {
						dealInfo.setJiegou(value);
					} else if ("套内面积".equals(lab)) {
						dealInfo.setInsideArea(value);
					} else if ("建筑类型".equals(lab)) {
						dealInfo.setJianzhuleixin(value);
					} else if ("房屋朝向".equals(lab)) {
						dealInfo.setCaoxiang(value);
					} else if ("建筑结构".equals(lab)) {
						dealInfo.setJianzhujiegou(value);
					} else if ("装修情况".equals(lab)) {
						dealInfo.setZhuangxiu(value);
					} else if ("梯户比例".equals(lab)) {
						dealInfo.setTihubili(value);
					} else if ("供暖方式".equals(lab)) {
						dealInfo.setGongnuan(value);
					} else if ("配备电梯".equals(lab)) {
						dealInfo.setDianti(value);
					} else if ("产权年限".equals(lab)) {
						dealInfo.setChanquan(value);
					} else if ("建成年代".equals(lab)) {
						dealInfo.setAge(value);
					}

				}
				elements = document.getElementsByClass("transaction");
				elements = elements.get(0).getElementsByTag("li");
				for (Element element2 : elements) {
					Element element3 = element2.getElementsByTag("span").get(0);
					String lab = element3.text();
					String value = element2.text().replace(lab, "");
					// System.out.println(lab);
					/**
					 * 挂牌时间 交易权属 上次交易 房屋用途 房屋年限 产权所属 抵押信息 房本备件
					 * 
					 */
					if ("挂牌时间".equals(lab)) {
						dealInfo.setGuapaishijian(value);
					} else if ("交易权属".equals(lab)) {
						dealInfo.setJiaoyiquanshu(value);
					} else if ("房屋用途".equals(lab)) {
						dealInfo.setFangwuyongtu(value);
					} else if ("房屋年限".equals(lab)) {
						dealInfo.setFangwunianxian(value);
					} else if ("产权所属".equals(lab)) {
						dealInfo.setChanquansuoshu(value);
					} else if ("链家编号".equals(lab)) {
						dealInfo.setLianjia_id(value);
					}

				}
				elements = document.getElementsByClass("record_list");
				elements = elements.first().getElementsByTag("li");
				StringBuilder sBuilder = new StringBuilder();
				for (Element e : elements) {
					Element e1 = e.getElementsByClass("record_price").first();

					sBuilder.append(e1.text());
					sBuilder.append("|");
					e1 = e.getElementsByClass("record_detail").first();
					sBuilder.append(e1.text().replaceAll(",", "|"));
					sBuilder.append(";");
				}
				dealInfo.setShangcijiaoyi(sBuilder.toString());
				// System.out.println(elements);

				// elements = document.getElementById(id)
				// System.out.println(document);
				try {
					elements = document.getElementsByClass("tags clear");
					String tag = elements.first().text().replaceAll("房源标签 ", "");
					dealInfo.setTag(tag);
					// System.out.println(elements.first().text());

				} catch (Exception e) {
					// TODO: handle exception
				}

				try {
					elements = document.getElementsByClass("baseattribute clear");
					StringBuilder stringBuilder = new StringBuilder();
					for (Element e : elements) {
						String name = e.getElementsByClass("name").first().text();
						String content = e.getElementsByClass("content").first().text();
						content.replaceAll(",", "，");
						// System.out.println(name+" "+content);
						if (name.equals("小区介绍")) {
							dealInfo.setXiaoqujieshao(content);
						} else if (name.equals("权属抵押")) {
							dealInfo.setQuanshudiya(content);
						} else if (name.equals("户型介绍")) {
							dealInfo.setHuxinjieshao(content);
						} else if (name.equals("核心卖点")) {
							dealInfo.setHexinmaidian(content);
						} else if (name.equals("税费解析")) {
							dealInfo.setShuifeijiexi(content);
						} else if (name.equals("交通出行")) {
							dealInfo.setJiaotongchuxin(content);
						}  else {
							stringBuilder.append(name + ":" + content);
							stringBuilder.append(";");
						}

					}

					dealInfo.setOther_Attribute(stringBuilder.toString());
					// System.out.println(elements);
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					elements = document.getElementsByClass("newwrap yezhuSell");
					elements = elements.first().getElementsByClass("txt");
					StringBuilder sBuilder2 = new StringBuilder();
					for (Element element2 : elements) {
						sBuilder2.append(element2.text());
						sBuilder2.append(";");
					}
					dealInfo.setYezhuzijian(sBuilder2.toString());

				} catch (Exception e) {
					// TODO: handle exception
				}

				elements = document.getElementsByClass("thumbnail").first().getElementsByTag("ul").first()
						.getElementsByTag("li");
				int count = 0;
				HashMap<String, String> picture = new HashMap<String, String>();
				for (Element element2 : elements) {
					String picurl = element2.attr("data-src");
					String picname = element2.attr("data-desc") + count;
					picture.put(picname, picurl);
					count++;

				}
				dealInfo.setImageMap(picture);

				elements = document.getElementsByTag("script");
				for (Element e : elements) {
					String text = e.toString();
					// System.out.println(text);
					if (text.contains("resblockPosition")) {
						int index = text.indexOf("resblockPosition");
						int endindex = text.substring(index).indexOf("      ");
						String location = text.substring(index, index + endindex).trim()
								.replace("resblockPosition:'", "").replace("',", "");
						int index2 = text.indexOf("resblockId");
						int endindex2 = text.substring(index2).indexOf("      ");
						String cpage = text.substring(index2, index2 + endindex2).trim().replace("resblockId:'", "")
								.replace("',", "");
						dealInfo.setCommunity_id(cpage);
						int index3 = text.indexOf("resblockName");
						int endindex3 = text.substring(index3).indexOf("      ");
						String resbolckname = text.substring(index3, index3 + endindex3).trim()
								.replace("resblockName:'", "").replace("',", "");
						dealInfo.setCommunity(resbolckname);

						String[] lStrings = location.split(",");
						try {
							dealInfo.setLon(lStrings[0]);
							dealInfo.setLat(lStrings[1]);
							double lon = Double.parseDouble(lStrings[0]);
							double lat = Double.parseDouble(lStrings[1]);
							Gps gps = PositionUtil.bd09_To_Gps84(lat, lon);
							dealInfo.setWGS84_lon("" + gps.getWgLon());
							dealInfo.setWGS84_lat("" + gps.getWgLat());

						} catch (Exception e1) {

						}
						break;
					} else if (text.contains("ljConf")) {
						int index = text.indexOf("city_id");
						// System.out.println(e);
						int endindex = text.substring(index).indexOf("    ");
						String city_id = text.substring(index, index + endindex).trim().replace("city_id: '", "")
								.replace("',", "");
						dealInfo.setCity_id(city_id);

						index = text.indexOf("city_abbr");
						endindex = text.substring(index).indexOf("    ");
						String city_abbr = text.substring(index, index + endindex).trim().replace("city_abbr: '", "")
								.replace("',", "");
						dealInfo.setCity_abbr(city_abbr);

						index = text.indexOf("city_name");
						endindex = text.substring(index).indexOf("    ");
						String city_name = text.substring(index, index + endindex).trim().replace("city_name: '", "")
								.replace("',", "");
						dealInfo.setCity_name(city_name);

						// System.out.println(e);
					}

				}

				// System.out.println("progressing:" + deal_Info.toString());
				break;

			} catch (UnknownHostException uhe){
				System.out.println("网络错误");

			}catch (IOException e) {
				// System.out.println("IOException" + deal_page);
				// return DealCrawl.getDealInfo(deal_page);
				c++;
				if (c > 100) {
					System.out.println(deal_page);
					e.printStackTrace();
					break;

				}
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}  catch (Exception e) {
				e.printStackTrace();
				// System.out.println("otherException");
				// System.out.println(deal_page);
				// TODO: handle exception
				// System.out.println("exception");
				break;
			}
		}
		return dealInfo;
	}

	public void insertDealInfoList(ArrayList<DealInfo2> lists) {
		MySqlsetup.createDealTable(user, password);
		try {
			MySqlsetup.insertDeallist(user, password, lists);
		} catch (SQLIntegrityConstraintViolationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public DealInfo getDealInfo(String deal_page) {
		DealInfo dealInfo = new DealInfo();
		dealInfo.setUrl(deal_page);

		Document document = null;
		while (true) {
			try {
				document = Jsoup.connect(deal_page).get();

				// title
				Elements elements = document.getElementsByClass("wrapper");
				Element element = elements.first();
				elements = element.getElementsByTag("span");

				dealInfo.setDealDate(elements.first().text().replaceAll("成交", "").trim());
				dealInfo.setTitle(element.text().replaceAll(",", ";"));

				elements = document.getElementsByClass("price");
				element = elements.first();
				elements = element.getElementsByTag("span");

				dealInfo.setTotalPrice(elements.text());
				elements = element.getElementsByTag("b");
				dealInfo.setUnitPrice(elements.text());

				elements = document.getElementsByClass("deal-bread").first().getElementsByTag("a");
				dealInfo.setSmall_region(elements.get(3).text().replace("二手房成交价格", ""));

				// basic information
				element = document.getElementsByClass("msg").first();
				elements = element.getElementsByTag("span");
				for (Element e : elements) {
					String text = e.text();
					element = e.getElementsByTag("label").first();
					String value = element.text();
					if (text.contains("挂牌")) {
						dealInfo.setSellingPrice(value);
					} else if (text.contains("成交周期")) {
						dealInfo.setDaySelling(value);
					} else if (text.contains("调价")) {
						dealInfo.setChangePrice(value);
					} else if (text.contains("带看")) {
						dealInfo.setNum_show(value);
					} else if (text.contains("关注")) {
						dealInfo.setNum_follow(value);
					} else if (text.contains("浏览")) {
						dealInfo.setNum_read(value);
					}

				}
				// further information
				elements = document.getElementsByClass("base");
				elements = elements.get(0).getElementsByTag("li");
				for (Element element2 : elements) {
					Element element3 = element2.getElementsByTag("span").get(0);
					String lab = element3.text();
					String value = element2.text().replace(lab, "");

					/**
					 * 房屋户型 所在楼层 建筑面积 户型结构 套内面积 建筑类型 房屋朝向 建筑结构 装修情况 梯户比例 供暖方式 配备电梯 产权年限
					 * 
					 */
					if ("房屋户型".equals(lab)) {
						dealInfo.setHuxin(value);
					} else if ("所在楼层".equals(lab)) {
						dealInfo.setLouceng(value);
					} else if ("建筑面积".equals(lab)) {
						dealInfo.setArea(value);
					} else if ("户型结构".equals(lab)) {
						dealInfo.setJiegou(value);
					} else if ("套内面积".equals(lab)) {
						dealInfo.setInsideArea(value);
					} else if ("建筑类型".equals(lab)) {
						dealInfo.setJianzhuleixin(value);
					} else if ("房屋朝向".equals(lab)) {
						dealInfo.setCaoxiang(value);
					} else if ("建筑结构".equals(lab)) {
						dealInfo.setJianzhujiegou(value);
					} else if ("装修情况".equals(lab)) {
						dealInfo.setZhuangxiu(value);
					} else if ("梯户比例".equals(lab)) {
						dealInfo.setTihubili(value);
					} else if ("供暖方式".equals(lab)) {
						dealInfo.setGongnuan(value);
					} else if ("配备电梯".equals(lab)) {
						dealInfo.setDianti(value);
					} else if ("产权年限".equals(lab)) {
						dealInfo.setChanquan(value);
					} else if ("建成年代".equals(lab)) {
						dealInfo.setAge(value);
					}

				}
				elements = document.getElementsByClass("transaction");
				elements = elements.get(0).getElementsByTag("li");
				for (Element element2 : elements) {
					Element element3 = element2.getElementsByTag("span").get(0);
					String lab = element3.text();
					String value = element2.text().replace(lab, "");
					// System.out.println(lab);
					/**
					 * 挂牌时间 交易权属 上次交易 房屋用途 房屋年限 产权所属 抵押信息 房本备件
					 * 
					 */
					if ("挂牌时间".equals(lab)) {
						dealInfo.setGuapaishijian(value);
					} else if ("交易权属".equals(lab)) {
						dealInfo.setJiaoyiquanshu(value);
					} else if ("房屋用途".equals(lab)) {
						dealInfo.setFangwuyongtu(value);
					} else if ("房屋年限".equals(lab)) {
						dealInfo.setFangwunianxian(value);
					} else if ("产权所属".equals(lab)) {
						dealInfo.setChanquansuoshu(value);
					}

				}
				elements = document.getElementsByClass("record_list");
				elements = elements.first().getElementsByTag("li");
				StringBuilder sBuilder = new StringBuilder();
				for (Element e : elements) {
					Element e1 = e.getElementsByClass("record_price").first();

					sBuilder.append(e1.text());
					sBuilder.append("^");
					e1 = e.getElementsByClass("record_detail").first();
					sBuilder.append(e1.text().replaceAll(",", "^"));
					sBuilder.append(";");
				}
				dealInfo.setShangcijiaoyi(sBuilder.toString());
				// System.out.println(elements);

				elements = document.getElementsByTag("script");
				for (Element e : elements) {
					String text = e.toString();
					if (text.contains("resblockPosition")) {
						int index = text.indexOf("resblockPosition");
						int endindex = text.substring(index).indexOf("      ");
						String location = text.substring(index, index + endindex).trim()
								.replace("resblockPosition:'", "").replace("',", "");
						int index2 = text.indexOf("resblockId");
						int endindex2 = text.substring(index2).indexOf("      ");
						String cpage = text.substring(index2, index2 + endindex2).trim().replace("resblockId:'", "")
								.replace("',", "");
						cpage = deal_page.substring(0, deal_page.indexOf("chengjiao")) + "xiaoqu/" + cpage + "/";
						// System.out.println(cpage);
						dealInfo.setCommunity(cpage);
						// System.out.println(location);
						String[] lStrings = location.split(",");
						try {
							dealInfo.setLon(lStrings[0]);
							dealInfo.setLat(lStrings[1]);

						} finally {

						}
						break;
					}

				}

				System.out.println("progressing:" + dealInfo.toString());
				break;

			} catch (IOException e) {
				// System.out.println("IOException" + deal_page);
				// return DealCrawl.getDealInfo(deal_page);

				// TODO Auto-generated catch block
				// e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
				// System.out.println("otherException");
				// System.out.println(deal_page);
				// TODO: handle exception
				// System.out.println("exception");
				break;
			}
		}
		return dealInfo;
	}

	public HashMap<String, SmallRegionInfo> get_Small_DealList(String city, String cityPage) {

		HashMap<String, String> map1 = new HashMap<String, String>();
		HashMap<String, SmallRegionInfo> map2 = new HashMap<String, SmallRegionInfo>();
		Document document = null;
		try {
			document = Jsoup.connect(cityPage).get();
			Element element1 = document.getElementsByClass("total fl").first();
			this.theoreticalTotal = element1.getElementsByTag("span").first().text();


			Elements elements = document.getElementsByAttributeValue("data-role", "ershoufang");
			elements = elements.first().getElementsByTag("a");
			for (Element e : elements) {
				String page = e.absUrl("href");

				System.out.println(e.text() + "  " + page);
				map1.put(e.text(), page);

			}
			for (Entry<String, String> entry : map1.entrySet()) {
				document = Jsoup.connect(entry.getValue()).get();
				String district = entry.getKey();
				elements = document.getElementsByAttributeValue("data-role", "ershoufang");
				elements = elements.first().getElementsByTag("div");
				try {
					Element element = elements.last();
					Elements e1 = element.getElementsByTag("a");
					for (Element e : e1) {
						SmallRegionInfo smallRegionInfo = new SmallRegionInfo();
						String page = e.absUrl("href");

						smallRegionInfo.setCity(city);
						smallRegionInfo.setDistrict(district);
						smallRegionInfo.setSmallReion(e.text());
						smallRegionInfo.setSmallRegionUrl(page);
						System.out.println(smallRegionInfo);

						map2.put(e.text(), smallRegionInfo);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}

		return map2;
	}

	public void insertSmallRegionintoDB(HashMap<String, SmallRegionInfo> infos) {
		MySqlsetup.createDealSmallRegionTable(user, password);
		for (Entry<String, SmallRegionInfo> entry : infos.entrySet()) {
			MySqlsetup.insertSmallRegionInfo(user, password, entry.getValue());
		}

	}

	public  ArrayList<String> getCompleteDealList(ArrayList<String> dealListUrl) {
		ArrayList<String> list = new ArrayList<String>();

		class MyThread1 implements Callable<ArrayList<String>> {

			private String url;

			public MyThread1(String url) {
				super();
				this.url = url;
			}

			public ArrayList<String> call() throws Exception {
				ArrayList<String> list1 = new ArrayList<String>();
				Document document = null;
				int c = 0;
				while (true) {
					try {
						document = Jsoup.connect(url).get();
						Thread.yield();

						Elements elements = document.getElementsByClass("listContent");
						Element element = elements.first();
						elements = element.getElementsByClass("info");
						for (Element e : elements) {
							Element element3 = e.getElementsByClass("totalPrice").first();
							String tp = element3.text();
							Element element2 = e.getElementsByClass("title").first().getElementsByTag("a").first();
							String url1 = element2.attr("href");
							if (tp.equals("暂无价格")) {
								// System.out.println(url1);
								continue;
							}
							list1.add(url1);
							// System.out.println(url1);
						}
						try {
							elements = document.getElementsByAttribute("page-data");
						
							element = elements.get(0);
							String attr = element.attr("page-data");
							StringReader reader = new StringReader(attr);
							JsonReader jsonReader = new JsonReader(reader);
							jsonReader.beginObject();
							int currentpage = 0;
							int totalpage = 0;
							while (jsonReader.hasNext()) {
								String name = jsonReader.nextName();
								if ("totalPage".equals(name)) {
									totalpage = jsonReader.nextInt();
								} else if ("curPage".equals(name)) {
									currentpage = jsonReader.nextInt();
								}

							}
							jsonReader.endObject();
							if (currentpage < totalpage) {
								int nextpage = currentpage + 1;
								String urlnext = url.replace("pg" + currentpage + "/", "") + "pg" + nextpage + "/";
								ArrayList<String> auList = new ArrayList<String>();
								auList.add(urlnext);
								//System.out.println(urlnext);
								list1.addAll(getCompleteDealList(auList));

							}
						} catch (IndexOutOfBoundsException e) {
							// TODO: handle exception
						}
						break;
					} catch (ConnectException exception) {
						//System.out.println(url);
					} catch(SocketTimeoutException exception) {
						//System.out.println(url);
					}catch(IOException exception) {
						//System.out.println(url);
					}catch (Exception e) {
						e.printStackTrace();
						if (c > 100) {
							System.out.println("页面出错：  " + url);
							break;
						}
						c++;

					}

				}
				return list1;
			}

		}

		ExecutorService pool = Executors.newCachedThreadPool();
		List<Future<ArrayList<String>>> list1 = new ArrayList<Future<ArrayList<String>>>();
		for (String url : dealListUrl) {

			Callable<ArrayList<String>> c = new MyThread1(url);
			Future<ArrayList<String>> f = pool.submit(c);
			list1.add(f);
		}
		for (Future<ArrayList<String>> f1 : list1) {
			try {
				list.addAll(f1.get());
				// System.out.println("倒腾中");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		pool.shutdown();
		// System.out.println("shutdown");
		return list;
	}

	public static ArrayList<String> getCompleteDealList(HashMap<String, SmallRegionInfo> small_lists) {
		ArrayList<String> list = new ArrayList<String>();

		class MyThread1 implements Callable<ArrayList<String>> {

			private String url;

			public MyThread1(String url) {
				super();
				this.url = url;
			}

			public ArrayList<String> call() throws Exception {
				ArrayList<String> list1 = new ArrayList<String>();
				Document document = null;
				int c = 0;
				while (true) {
					try {
						document = Jsoup.connect(url).get();
						Thread.yield();
						Elements e1 = document.getElementsByClass("total fl");

						e1 = e1.first().getElementsByTag("span");
						int num = Integer.parseInt(e1.first().text());
						int counter = 0;
						while (num == 0) {

							document = Jsoup.connect(url).timeout(10000).get();
							Thread.yield();
							e1 = document.getElementsByClass("total fl");
							e1 = e1.first().getElementsByTag("span");
							num = Integer.parseInt(e1.first().text());
							if (url.contains("/pg") && num == 0) {

							} else if ((counter > 5) && num == 0) {
								System.out.println(url + " fail to find data after five times of trying");
								return null;
							}
							if (counter > 20)
								break;
							// System.out.println(counter);
							counter++;

						}

						Elements elements = document.getElementsByClass("listContent");
						Element element = elements.first();
						elements = element.getElementsByClass("info");
						for (Element e : elements) {
							Element element3 = e.getElementsByClass("totalPrice").first();
							String tp = element3.text();
							Element element2 = e.getElementsByClass("title").first().getElementsByTag("a").first();
							String url1 = element2.attr("href");
							if (tp.equals("暂无价格")) {
								// System.out.println(url1);
								continue;
							}
							list1.add(url1);
							// System.out.println(url1);
						}

						elements = document.getElementsByAttribute("page-data");
						element = elements.get(0);
						String attr = element.attr("page-data");
						StringReader reader = new StringReader(attr);
						JsonReader jsonReader = new JsonReader(reader);
						jsonReader.beginObject();
						int currentpage = 0;
						int totalpage = 0;
						while (jsonReader.hasNext()) {
							String name = jsonReader.nextName();
							if ("totalPage".equals(name)) {
								totalpage = jsonReader.nextInt();
							} else if ("curPage".equals(name)) {
								currentpage = jsonReader.nextInt();
							}

						}
						jsonReader.endObject();
						if (currentpage < totalpage) {
							int nextpage = currentpage + 1;
							String urlnext = url.replace("pg" + currentpage + "/", "") + "pg" + nextpage + "/";

							HashMap<String, SmallRegionInfo> nextpages = new HashMap<String, SmallRegionInfo>();
							SmallRegionInfo smallRegionInfo = new SmallRegionInfo();
							smallRegionInfo.setSmallRegionUrl(urlnext);
							nextpages.put("", smallRegionInfo);
							list1.addAll(DealCrawl.getCompleteDealList(nextpages));

						}
						break;
					} catch (Exception e) {
						if (c > 100) {
							System.out.println("页面出错：  " + url);
							break;
						}
						c++;

					}

				}
				return list1;
			}

		}

		ExecutorService pool = Executors.newCachedThreadPool();
		List<Future<ArrayList<String>>> list1 = new ArrayList<Future<ArrayList<String>>>();
		for (Entry<String, SmallRegionInfo> entry : small_lists.entrySet()) {
			String url = entry.getValue().getSmallRegionUrl();
			Callable<ArrayList<String>> c = new MyThread1(url);
			Future<ArrayList<String>> f = pool.submit(c);
			list1.add(f);
		}
		for (Future<ArrayList<String>> f1 : list1) {
			try {
				list.addAll(f1.get());
				// System.out.println("倒腾中");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		pool.shutdown();
		// System.out.println("shutdown");
		return list;
	}


	public void deal_info2_CSVwriter(ArrayList<DealInfo2> infos2) throws IOException {
		File f1 = new File(this.save_path,this.cityAbbr);
		if(!f1.exists()){
			try {
				f1.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		HashMap<String,List<DealInfo2>> smallRegionInfo2 = new HashMap<>();
		for (DealInfo2 di2:infos2) {
			String smallRegion = di2.getSmall_region();
			if(smallRegion.equals(null)){
				System.out.println("小区域为null");
				smallRegion = "未知区域二手房成交";
			}

			if(smallRegionInfo2.containsKey(smallRegion)){
				List<DealInfo2> l = smallRegionInfo2.get(smallRegion);
				l.add(di2);
				smallRegionInfo2.put(smallRegion,l);
			}else{
				List<DealInfo2> l = new ArrayList<>();
				l.add(di2);
				smallRegionInfo2.put(smallRegion,l);
			}


		}

		for (Entry<String,List<DealInfo2>> entry:smallRegionInfo2.entrySet()) {
			File f2 = new File(f1,"data");
			if(!f2.exists()){
				f2.mkdirs();
			}

			File file = new File(f2, entry.getKey().replace("/","_")+".csv");
			FileWriter fileWriter = new FileWriter(file, Charset.forName("utf-8"),true);
			if(file.exists()){
				fileWriter.write(DealInfo2.lineTitle(",")+"\r\n");
			}

			for (DealInfo2 di2:entry.getValue()) {
				fileWriter.write(di2.toString()+"\r\n");
				crawled.add(di2.getLianjia_id());
			}
			fileWriter.flush();
			fileWriter.close();


		}



	}



	public ArrayList<String> getCommunityDealListFromDB(Connection connection, String tableName)
			throws SQLException {
		ArrayList<String> list = new ArrayList<String>();
		ResultSet resultSet = MysqlUtility.getResult(connection,
				"SELECT deal_url FROM " + tableName + " where deal_url is not NULL");
		while (resultSet.next()) {
			list.add(resultSet.getString(1));
			System.out.println(resultSet.getString(1));

		}
		System.out.println("**************************");
		ArrayList<String> list1 = getCompleteDealList(list);
		System.out.println("************************************************");

		return list1;
	}



	public void crawlAndWriteCSV() {

//		这里需要从配置文件中读取
//		'北京', 'https://bj.lianjia.com/chengjiao/'
		HashMap<String, SmallRegionInfo> map = this.get_Small_DealList(this.cityName, this.cityDealUrl);
		List<String> list = getCompleteDealList(map);

//		需要写个避免重复爬的东西
		List<String> crawleds = getCrawledList();
		for (String crawled: crawleds) {
			list.remove(crawled);
		}


		HashSet<String> hashSet = new HashSet<String>();
		hashSet.addAll(list);
		list.clear();
		list.addAll(hashSet);

		int size = list.size();
		System.out.println("总共有：" + size + "个成交页面");

		class Mythread implements Callable<DealInfo2> {
			private String url;

			public Mythread(String url) {
				super();
				this.url = url;
			}

			public DealInfo2 call() throws Exception {
				// TODO Auto-generated method stub
				DealInfo2 dealInfo2 = getDealInfo2(url);
//				System.out.println(dealInfo2.getLianjia_id());
				return dealInfo2;
			}
		}

		int counter = 0;

		ArrayList<List<String>> listList = new ArrayList<List<String>>();
		int divide = size - 1;
		int low = 0;
		while (divide > 49999) {
			List<String> list11 = list.subList(low, 49999 + low);
			listList.add(list11);
			divide = divide - 50000;
			low = low + 50000;
		}
		listList.add(list.subList(low, size - 1));


		for (List<String> alist : listList) {
			ArrayList<DealInfo2> infos = new ArrayList<DealInfo2>();
			List<Future<DealInfo2>> list1 = new ArrayList<Future<DealInfo2>>();
			ExecutorService pool = Executors.newScheduledThreadPool(100);

			for (String s : alist) {
				Callable<DealInfo2> c = new Mythread(s);
				Future<DealInfo2> f = pool.submit(c);
				list1.add(f);

				if (counter % 50 == 0) {
					try {
						// System.out.println("Sleep "+counter);
						Thread.sleep(1000);

					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				if (counter % 100 == 0) {
					if (size > 150000) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					System.out.println(this.cityName + " 进度： " + 100 * counter / size + "%   " + counter + "/" + size);
				}
				counter++;
			}

			pool.shutdown();

			try {
				pool.awaitTermination(3L, TimeUnit.HOURS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			for (Future<DealInfo2> f : list1) {
				try {
					infos.add(f.get());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			CountDownLatch countDownLatch = new CountDownLatch(1);
			class WriteCSV extends Thread {
				private String path;
				private ArrayList<DealInfo2> infos;

				public WriteCSV(String path,ArrayList<DealInfo2> infos) {
					super();
					this.path = path;
					this.infos = infos;
				}

				@Override
				public void run() {
					// TODO Auto-generated method stub
					super.run();
					try {
						System.out.println("正在写csv");
//						MySqlsetup.insertDeallist(user, password, infos);
// TODO: 2019/6/18 写硬盘的内容，根据小区域来写吧

						deal_info2_CSVwriter(infos);
						writeSimpleLog();


						System.out.println("写入完成");
						countDownLatch.countDown();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
			String path = new File(this.save_path,this.cityAbbr).getAbsolutePath();
			Thread t = new WriteCSV(path, infos);
			t.start();
			try {
				countDownLatch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}


	}


	public void crawlAndWriteMySQL(String city, boolean dealListfromDB) throws SQLException {

		Connection connection = MysqlUtility.getConnection(mysqlURL, user, password);
		connection.createStatement().executeQuery("USE lianjia");
		ResultSet resultSet = MysqlUtility.getResult(connection,
				"SELECT deal_root_url from lianjia.deal_city WHERE city='" + city + "'");
		String baseurl = null;
		if (resultSet.next()) {
			baseurl = resultSet.getString(1);
		}
		if (baseurl == null) {
			System.out.println("查无此城市");
			return;
		}

		MySqlsetup.createDealTable(user, password);
		List<String> list = new ArrayList<String>();
		if (!dealListfromDB) {
			HashMap<String, SmallRegionInfo> map = get_Small_DealList(city, baseurl);
			list = getCompleteDealList(map);
		} else {
			list = getCommunityDealListFromDB(connection, "lianjia.community");
		}

		String sql = "SELECT url FROM lianjia.deal WHERE city_name = '" + city + "'";
		ResultSet resultSet2 = MysqlUtility.getResult(connection, sql);

		while (resultSet2.next()) {
			list.remove(resultSet2.getString(1));
		}

		// System.out.println("所有成交链接获得");

		HashSet<String> hashSet = new LinkedHashSet<String>();
		hashSet.addAll(list);
		list.clear();
		list.addAll(hashSet);

		int size = list.size();
		System.out.println("总共有：" + size + "个成交页面");

		class Mythread implements Callable<DealInfo2> {
			private String url;

			public Mythread(String url) {
				super();
				this.url = url;
			}

			public DealInfo2 call() throws Exception {
				// TODO Auto-generated method stub

				return getDealInfo2(url);
			}
		}

		int counter = 0;

		ArrayList<List<String>> listList = new ArrayList<List<String>>();
		int divide = size - 1;
		int low = 0;
		while (divide > 49999) {
			List<String> list11 = list.subList(low, 49999 + low);
			listList.add(list11);
			divide = divide - 50000;
			low = low + 50000;
		}
		listList.add(list.subList(low, size - 1));
		// list.clear();
		// 不明白为什么加了这个就会报错
		// java.util.concurrentmodificationexception
		// 出错在for (String s : alist) { 这行
		// 可能与sublist方法的实现有关

		for (List<String> alist : listList) {
			ArrayList<DealInfo2> infos = new ArrayList<>();
			List<Future<DealInfo2>> list1 = new ArrayList<>();
			ExecutorService pool = Executors.newScheduledThreadPool(50);

			for (String s : alist) {
				Callable<DealInfo2> c = new Mythread(s);
				Future<DealInfo2> f = pool.submit(c);
				list1.add(f);

				if (counter % 50 == 0) {
					try {
						// System.out.println("Sleep "+counter);
						Thread.sleep(500);

					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				if (counter % 100 == 0) {
					if (size > 150000) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					System.out.println(city + " 进度： " + 100 * counter / size + "%   " + counter + "/" + size);
				}
				counter++;
			}

			pool.shutdown();

			for (Future<DealInfo2> f : list1) {
				try {
					infos.add(f.get());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			class WriteSQLThread extends Thread {
				private String user;
				private String password;
				private ArrayList<DealInfo2> infos;

				public WriteSQLThread(String user, String password, ArrayList<DealInfo2> infos) {
					super();
					this.user = user;
					this.password = password;
					this.infos = infos;
				}

				@Override
				public void run() {
					// TODO Auto-generated method stub
					super.run();
					try {
						System.out.println("正在写入数据库");
						MySqlsetup.insertDeallist(user, password, infos);
						System.out.println("写入完成");
					} catch (SQLIntegrityConstraintViolationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}

			new WriteSQLThread(user, password, infos).start();

		}
	}

	private void readCityConfig() {
		try {
			FileReader fileReader = new FileReader(this.configPath + "/cityConfig.json");
			Gson gson = new Gson();
			List<CityInfo> cityInfos = gson.fromJson(fileReader, new TypeToken<List<CityInfo>>() {
			}.getType());
			for (CityInfo info : cityInfos) {
				String cityNameTemp = info.getCity_name();
				if (cityNameTemp.equals(cityName)) {
					cityId = info.getCity_id();
					cityName = info.getCity_name();
					cityAbbr = info.getAbbr();
//					communityUrl = "https://" + cityAbbr + ".lianjia.com/xiaoqu/";
					cityDealUrl = "https://"+cityAbbr+".lianjia.com/chengjiao/";
					break;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}


	}

	private List<String> getCrawledList(){
//		List<String> list = new ArrayList<String>();
		File cityFold = new File(this.save_path,this.cityAbbr);
		if(!cityFold.exists()){
			 cityFold.mkdirs();
			 return new ArrayList<String>();
		}
		File indexFile = new File(cityFold,this.cityName+"_index.txt");
		if(!indexFile.exists()){
			return new ArrayList<String>();
		}

		List<String> list = null;
		try {
			list = IOUtils.readLines(new FileInputStream(indexFile),"UTF-8");

		} catch (IOException e) {
			e.printStackTrace();
		}


		this.lastCrawled = list.size();
		return list;


	}



	private void writeSimpleLog(){
//		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			FileWriter fw = new FileWriter(new File(new File(this.save_path,this.cityAbbr),this.cityName+"_index.txt"),Charset.forName("utf-8"),true);
			for (String id: this.crawled) {
				fw.write(id+"\r\n");
			}
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			FileWriter logWriter = new FileWriter(new File(new File(this.save_path,this.cityAbbr),this.cityName+"日志.txt"),Charset.forName("utf-8"),true);
			System.out.println("************保存日志中***********");
//			FileWriter logWriter = new FileWriter(savePath, true);
			logWriter.append("\r\n");
			logWriter.append("Renew Date： " + format2.format(new Date(System.currentTimeMillis())) + "\r\n");
			logWriter.append("Renew City: " + cityName + "\r\n");
			logWriter.append("lianjia Claimed Total: " + theoreticalTotal + "\r\n");
			logWriter.append("Total crawl: " + (crawled.size() + lastCrawled ) + "\r\n");
			logWriter.append("Renew crawl: " + (crawled.size() - lastBatchCrawled) + "\r\n");
//			logWriter.append("Last date rented: " + format.format(lastDate) + "\r\n");
			lastBatchCrawled = crawled.size();
			logWriter.append("-----------------------------------------------");
			logWriter.flush();
			logWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}






	}





}
