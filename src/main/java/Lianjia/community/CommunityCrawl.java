package Lianjia.community;

import Lianjia.data.CityInfo;
import Lianjia.data.SmallRegionInfo;
import Lianjia.data.CommunitySimpleInfo;
import Lianjia.data.XiaoquInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.*;

public class CommunityCrawl {
	private String mysqlURL;
	private String user;
	private String password;
	private String save_path;
	private String configPath;
//	private String theoreticalTotal;
	private String cityId;
	private String cityName;
	private String cityAbbr;
	private String communityUrl;

	public CommunityCrawl() {

	}

	public CommunityCrawl(Properties properties, String cityName) {
		this.mysqlURL = properties.getProperty("mysqlURL");
		this.user = properties.getProperty("mysqlUser");
		this.password = properties.getProperty("mysqlPassword");
		File save = new File(properties.getProperty("save_path"),"Community");
		this.save_path = save.getAbsolutePath();
		this.configPath = properties.getProperty("config_fold");
		this.cityName = cityName;
		readCityConfig();
		if(!save.exists()){
			save.mkdirs();
		}

	}

	private void readCityConfig(){
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
					communityUrl = "https://"+cityAbbr+".lianjia.com/xiaoqu/";
					break;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public HashMap<String, SmallRegionInfo> get_Small_CommunityList(String city, String cityPage) {

		HashMap<String, String> map1 = new HashMap<String, String>();
		HashMap<String, SmallRegionInfo> map2 = new HashMap<String, SmallRegionInfo>();
		Document document = null;
		try {
			document = Jsoup.connect(cityPage).get();

			Elements elements = document.getElementsByAttributeValue("data-role", "ershoufang");
			elements = elements.first().getElementsByTag("a");
			for (Element e : elements) {
				String page = e.absUrl("href");

				// System.out.println(e.text()+" "+page);
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


	public ArrayList<CommunitySimpleInfo> communityPageGetCommunityList(HashMap<String, SmallRegionInfo> map) {
		ArrayList<CommunitySimpleInfo> list = new ArrayList<CommunitySimpleInfo>();

		class MyThread implements Callable<ArrayList<CommunitySimpleInfo>> {

			private SmallRegionInfo smallRegionInfo;

			public MyThread(SmallRegionInfo smallRegionInfo) {
				super();
				this.smallRegionInfo = smallRegionInfo;
				// TODO Auto-generated constructor stub
			}

			public ArrayList<CommunitySimpleInfo> call() throws Exception {
				// TODO Auto-generated method stub
				ArrayList<CommunitySimpleInfo> list = new ArrayList<CommunitySimpleInfo>();

				String url = smallRegionInfo.getSmallRegionUrl();
				Document document = null;
				while (true) {
					try {
						document = Jsoup.connect(url).get();
						Elements elements = document.getElementsByClass("info");
						for (Element e : elements) {
							CommunitySimpleInfo communitySimpleInfo = new CommunitySimpleInfo();
							Elements elements2 = e.getElementsByTag("a");
							Element e1 = elements2.first();
							communitySimpleInfo.setCity(smallRegionInfo.getCity());
							communitySimpleInfo.setDistrict(smallRegionInfo.getDistrict());
							communitySimpleInfo.setSmall_region(smallRegionInfo.getSmallReion());
							communitySimpleInfo.setCommunityUrl(e1.attr("href"));
							list.add(communitySimpleInfo);
						}
						try {
							elements = document.getElementsByAttribute("page-data");
							Element element = elements.get(0);
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
							jsonReader.close();
							if (currentpage < totalpage) {
								int nextpage = currentpage + 1;
								String urlnext = url.replace("pg" + currentpage + "/", "") + "pg" + nextpage + "/";
								HashMap<String, SmallRegionInfo> nextpages = new HashMap<String, SmallRegionInfo>();
								SmallRegionInfo smallRegionInfo1 = new SmallRegionInfo();
								smallRegionInfo1.setCity(smallRegionInfo.getCity());
								smallRegionInfo1.setDistrict(smallRegionInfo.getDistrict());
								smallRegionInfo1.setSmallReion(smallRegionInfo.getSmallReion());
								smallRegionInfo1.setSmallRegionUrl(urlnext);
								nextpages.put(smallRegionInfo1.getSmallReion(), smallRegionInfo1);
								list.addAll(communityPageGetCommunityList(nextpages));
							}

						} catch (Exception e) {
							e.printStackTrace();
							// TODO: handle exception
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						// e.printStackTrace();
					}
					System.out.println("完成爬取"+smallRegionInfo.getCity()+smallRegionInfo.getDistrict()+smallRegionInfo.getSmallReion());
					break;
				}
				return list;
			}

		}
		ExecutorService executorService = Executors.newCachedThreadPool();
		ArrayList<Future<ArrayList<CommunitySimpleInfo>>> futures = new ArrayList<Future<ArrayList<CommunitySimpleInfo>>>();

		for (Entry<String, SmallRegionInfo> entry : map.entrySet()) {
			SmallRegionInfo smallRegionInfo = entry.getValue();
			futures.add(executorService.submit(new MyThread(smallRegionInfo)));

		}
		executorService.shutdown();
		for (Future<ArrayList<CommunitySimpleInfo>> future : futures) {
			try {
				list.addAll(future.get());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return list;
	}

	public XiaoquInfo getCommunityInfo(CommunitySimpleInfo communitySimpleInfo) {
		XiaoquInfo xiaoquInfo = new XiaoquInfo();
		String page = communitySimpleInfo.getCommunityUrl();
		xiaoquInfo.setPageUrl(page);
		xiaoquInfo.setCity(communitySimpleInfo.getCity());
		xiaoquInfo.setDistrict(communitySimpleInfo.getDistrict());
		xiaoquInfo.setSmall_region(communitySimpleInfo.getSmall_region());

		Document document = null;
		while (true)
			try {
				document = Jsoup.connect(page).get();
				// System.out.println(document);
				Thread.yield();
				Elements elements = null;
				try {
					elements = document.getElementsByClass("detailHeader fl").get(0).getElementsByClass("detailTitle");
					xiaoquInfo.setName(elements.get(0).text());
					elements = document.getElementsByClass("detailHeader fl").get(0).getElementsByClass("detailDesc");
					xiaoquInfo.setAddress(elements.first().text());
					// System.out.println(elements);
				} catch (Exception e) {
					System.out.println(page);
				}
				try {
					elements = document.getElementsByClass("imgThumbnailList");
					elements = elements.first().getElementsByTag("li");
					ArrayList<String> arrayList = new ArrayList<String>();
					for (Element element : elements) {
						String string = element.attr("data-src");
						// System.out.println(string);
						arrayList.add(string);
					}

					xiaoquInfo.setPictures(arrayList);
				} catch (Exception e) {
					// TODO: handle exception
				}
				elements = document.getElementsByClass("xiaoquInfoItem");
				for (Element e : elements) {
					Elements es1 = e.getElementsByTag("span");
					String label = es1.get(0).text();
					String content = es1.get(1).text();
					// System.out.println(label);
					if ("建筑年代".equals(label)) {
						xiaoquInfo.setAge(content);
					} else if ("建筑类型".equals(label)) {
						xiaoquInfo.setType(content);
					} else if ("物业费用".equals(label)) {
						xiaoquInfo.setFee(content);
					} else if ("物业公司".equals(label)) {
						xiaoquInfo.setManageCompany(content);
					} else if ("开发商".equals(label)) {
						xiaoquInfo.setConstructionCompany(content);
					} else if ("楼栋总数".equals(label)) {
						xiaoquInfo.setNumBuilding(content);
					} else if ("房屋总数".equals(label)) {
						xiaoquInfo.setNumHousing(content);
					}
				}
				Element element = null;
				try {
					elements = document.getElementsByClass("goodSellHeader clear");
					element = elements.first();
					elements = element.getElementsByTag("a");
					element = elements.first();
					xiaoquInfo.setSellingUrl(element.attr("href"));
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					elements = document.getElementsByClass("rentListHeader clear");
					element = elements.first();
					elements = element.getElementsByTag("a");
					element = elements.first();
					xiaoquInfo.setRentUrl(element.attr("href"));
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					// frameDeal
					elements = document.getElementsByClass("frameDeal");
					elements = elements.get(0).getElementsByTag("a");
					element = elements.last();
					xiaoquInfo.setDealUrl(element.attr("href"));
				} catch (Exception e) {
					// TODO: handle exception
				}
				elements = document.getElementsByTag("script");
				for (Element e : elements) {
					String text = e.toString();
					// System.out.println(text);
					if (text.contains("resblockPosition")) {
						int index = text.indexOf("resblockPosition");
						// System.out.println(text);
						int endindex = text.substring(index).indexOf("    ");
						String location = text.substring(index, index + endindex).trim()
								.replace("resblockPosition:'", "").replace("',", "");
						xiaoquInfo.setLocation(location);
						int index2 = text.indexOf("id");
						int endindex2 = text.substring(index2).indexOf("    ");
						String cpage = text.substring(index2, index2 + endindex2).trim().replace("id:", "").replace(",",
								"");
						xiaoquInfo.setCommunity_id(cpage);
					}
				}
				// System.out.println(elements);
				System.out.println(xiaoquInfo);
				break;
				// System.out.println(xiaoquInfo);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("error");
				break;
			}

		return xiaoquInfo;
	}

	public void crawlCommunity() {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new File(save_path,this.cityName+"小区.csv"), Charset.forName("utf-8"));
			writer.println(XiaoquInfo.title());
		} catch (IOException e) {
			e.printStackTrace();
		}
		HashMap<String, SmallRegionInfo> map = get_Small_CommunityList(this.cityName, this.communityUrl);

		ArrayList<CommunitySimpleInfo> list = communityPageGetCommunityList(map);
		System.out.println("********************************************");
		System.out.println(list.size());

		class CrawlThread implements Callable<XiaoquInfo> {
			private CommunitySimpleInfo communitySimpleInfo;

			public CrawlThread(CommunitySimpleInfo communitySimpleInfo) {
				super();
				this.communitySimpleInfo = communitySimpleInfo;
			}

			public XiaoquInfo call() throws Exception {
				// TODO Auto-generated method stub
				return getCommunityInfo(communitySimpleInfo);
			}

		}

		ExecutorService executorService = Executors.newCachedThreadPool();
		ArrayList<Future<XiaoquInfo>> futures = new ArrayList<Future<XiaoquInfo>>();
		for (CommunitySimpleInfo communitySimpleInfo : list) {
			futures.add(executorService.submit(new CrawlThread(communitySimpleInfo)));

		}
		executorService.shutdown();
		for (Future<XiaoquInfo> future : futures) {
			try {
				XiaoquInfo xiaoquInfo = future.get();
				writer.println(xiaoquInfo.toString());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		writer.flush();
		writer.close();
	}


	public void crawlCommunity(String savePath,String city,String url) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(savePath, Charset.forName("utf-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		HashMap<String, SmallRegionInfo> map = get_Small_CommunityList(city, url);

		ArrayList<CommunitySimpleInfo> list = communityPageGetCommunityList(map);
		System.out.println("********************************************");
		System.out.println(list.size());

		class CrawlThread implements Callable<XiaoquInfo> {
			private CommunitySimpleInfo communitySimpleInfo;

			public CrawlThread(CommunitySimpleInfo communitySimpleInfo) {
				super();
				this.communitySimpleInfo = communitySimpleInfo;
			}

			public XiaoquInfo call() throws Exception {
				// TODO Auto-generated method stub
				return getCommunityInfo(communitySimpleInfo);
			}

		}

		ExecutorService executorService = Executors.newCachedThreadPool();
		ArrayList<Future<XiaoquInfo>> futures = new ArrayList<Future<XiaoquInfo>>();
		for (CommunitySimpleInfo communitySimpleInfo : list) {
			Future<XiaoquInfo> f = executorService.submit(new CrawlThread(communitySimpleInfo));
			futures.add(f);

		}
		executorService.shutdown();
		for (Future<XiaoquInfo> future : futures) {
			try {
				XiaoquInfo xiaoquInfo = future.get();
				writer.println(xiaoquInfo.getCity()+","+xiaoquInfo.getDistrict()+","+xiaoquInfo.getCommunity_id());
//				writer.flush();

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		writer.flush();
		writer.close();

	}

	public void crawlCommunity(Connection connection, String tableName, String city, String xiaoquUrl) {
		HashMap<String, SmallRegionInfo> map = get_Small_CommunityList(city, xiaoquUrl);

		ArrayList<CommunitySimpleInfo> list = communityPageGetCommunityList(map);
		System.out.println("********************************************");
		System.out.println(list.size());

		class CrawlThread implements Callable<XiaoquInfo> {
			private CommunitySimpleInfo communitySimpleInfo;

			public CrawlThread(CommunitySimpleInfo communitySimpleInfo) {
				super();
				this.communitySimpleInfo = communitySimpleInfo;
			}

			public XiaoquInfo call() throws Exception {
				// TODO Auto-generated method stub
				return getCommunityInfo(communitySimpleInfo);
			}

		}

		ExecutorService executorService = Executors.newCachedThreadPool();
		ArrayList<Future<XiaoquInfo>> futures = new ArrayList<Future<XiaoquInfo>>();
		for (CommunitySimpleInfo communitySimpleInfo : list) {
			futures.add(executorService.submit(new CrawlThread(communitySimpleInfo)));

		}
		executorService.shutdown();
		for (Future<XiaoquInfo> future : futures) {
			try {
				future.get().writeMysql(connection, tableName);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
