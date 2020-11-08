package Lianjia.selling;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;


import Lianjia.data.SellingData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import com.google.gson.stream.JsonReader;


public class SellingCrawl {
	
	
	

	public static ArrayList<String> getSellinglist(String sellPage) {
		if(sellPage==null) {
			return null;
		}
		ArrayList<String> list = new ArrayList<String>();
		Document document;
		try {
			document = Jsoup.connect(sellPage).get();
			Elements elements = document.getElementsByClass("sellListContent");
			for (Element element : elements) {
				Elements elements2 = element.getElementsByClass("title");
				for (Element e : elements2) {
					String url = e.getElementsByTag("a").get(0).attr("href");
					list.add(url);

				}

			}

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
			if (currentpage < totalpage) {
				int nextpage = currentpage + 1;
				String urlnext = sellPage.replace("pg" + currentpage + "/", "") + "pg" + nextpage + "/";
				System.out.println(urlnext);
				list.addAll(SellingCrawl.getSellinglist(urlnext));

			}

			// System.out.print(totalpage+" "+currentpage);

		} catch (IOException e) {

		} catch (NullPointerException e) {

		} catch (Exception e) {
			System.out.println("error");
			//SellingCrawl.getSellinglist(sellPage);
		}

		return list;
	}

	public static SellingData getSellingData(String selldetailPage) {
		SellingData sData = new SellingData();
		Document document = null;
		try {
			document = Jsoup.connect(selldetailPage).get();
			Elements elements = document.getElementsByClass("title");
			Element element = elements.get(0);

			sData.setTitle(element.text());
			elements = document.getElementsByClass("price ");
			element = elements.get(0);
			elements = element.getElementsByClass("total");
			sData.setTotalPrice(elements.get(0).text());
			elements = element.getElementsByClass("unitPriceValue");
			sData.setUnitPrice(elements.get(0).text());

			element = document.getElementsByClass("houseInfo").get(0).getElementsByClass("area").get(0)
					.getElementsByClass("subInfo").get(0);
			// String age = element.text();
			sData.setAge(element.text());

			element = document.getElementsByClass("communityName").get(0).getElementsByClass("info ").get(0);
			sData.setCommunity(element.text());
			// System.out.println(elements);

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
					sData.setHuxin(value);
				} else if ("所在楼层".equals(lab)) {
					sData.setLouceng(value);
				} else if ("建筑面积".equals(lab)) {
					sData.setArea(value);
				} else if ("户型结构".equals(lab)) {
					sData.setJiegou(value);
				} else if ("套内面积".equals(lab)) {
					sData.setInsideArea(value);
				} else if ("建筑类型".equals(lab)) {
					sData.setJianzhuleixin(value);
				} else if ("房屋朝向".equals(lab)) {
					sData.setCaoxiang(value);
				} else if ("建筑结构".equals(lab)) {
					sData.setJianzhujiegou(value);
				} else if ("装修情况".equals(lab)) {
					sData.setZhuangxiu(value);
				} else if ("梯户比例".equals(lab)) {
					sData.setTihubili(value);
				} else if ("供暖方式".equals(lab)) {
					sData.setGongnuan(value);
				} else if ("配备电梯".equals(lab)) {
					sData.setDianti(value);
				} else if ("产权年限".equals(lab)) {
					sData.setChanquan(value);
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
					sData.setGuapaishijian(value);
				} else if ("交易权属".equals(lab)) {
					sData.setJiaoyiquanshu(value);
				} else if ("上次交易".equals(lab)) {
					sData.setShangcijiaoyi(value);
				} else if ("房屋用途".equals(lab)) {
					sData.setFangwuyongtu(value);
				} else if ("房屋年限".equals(lab)) {
					sData.setFangwunianxian(value);
				} else if ("产权所属".equals(lab)) {
					sData.setChanquansuoshu(value);
				}

			}

			elements = document.getElementsByTag("script");
			for (Element e : elements) {
				String text = e.toString();
				if (text.contains("resblockPosition")) {
					int index = text.indexOf("resblockPosition");
					int endindex = text.substring(index).indexOf("      ");
					String location = text.substring(index, index + endindex).trim().replace("resblockPosition:'", "")
							.replace("',", "");

					// System.out.println(location);
					String[] lStrings = location.split(",");
					try {
						sData.setLon(lStrings[0]);
						sData.setLat(lStrings[1]);

					} finally {

					}
					break;
				}

			}
			System.out.println(sData);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}catch (Exception e) {
			// TODO: handle exception
		}

		return sData;
	}

}
