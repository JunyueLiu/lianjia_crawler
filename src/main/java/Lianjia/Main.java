package Lianjia;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

import Lianjia.community.CommunityCrawl;
import Lianjia.app.Crawl;
import Lianjia.data.CityConfig;
import Lianjia.deal.DealCrawl;
import dataPreProcessing.coordinate.CoordinateConversion;
import dataPreProcessing.dataConcatenation.DataMerger;

public class Main {
	public static void main(String[] args) throws IOException {
		Properties properties = new Properties();
		try {
			properties.load(new
					InputStreamReader(new FileInputStream(
							new File("src/main/java/config.properties"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(true) {
			System.out.println("欢迎来到链家爬虫程序，请按选择爬取的数据（输入数字并按Enter进入）");
			System.out.println("1. 二手房成交 2. 租房成交 3. 小区 4. 拼接数据 5. 坐标转换");
			Scanner scanner = new Scanner(System.in);
			String type = scanner.next();
			if(type.equals("5")){
				System.out.println("输入待转换csv路径(分隔符必须是/)");
				String fromPath = scanner.next();
				System.out.println("输入lon的列名");
				String lon = scanner.next();
				System.out.println("输入lat的列名");
				String lat = scanner.next();
				System.out.println("输入原始的坐标系简称[bd,wgs,gcj]");
				String fromCor = scanner.next();
				System.out.println("输入转换后的坐标系简称[bd,wgs,gcj]");
				String toCor = scanner.next();
				System.out.println("输入转换后保存的csv路径(分隔符必须是\\)，.csv结尾");
				String toPath = scanner.next();
				CoordinateConversion coordinateConversion = new CoordinateConversion(fromPath,toPath,fromCor,toCor,lon,lat);
				coordinateConversion.converse();

			}else if(type.equals("4")){
				System.out.println("选择数据拼接源：1. 二手房成交 2. 租房成交");
				String m = scanner.next();
				if(m.equals("1")){
					File file = new File(properties.getProperty("save_path"),"Deal");
					System.out.println("选择想要拼接的城市，输入简称");
					System.out.println(Arrays.toString(file.list()));
					String ct = scanner.next();
					String cityFold = new File(file,ct).getAbsolutePath() + "/data";
					DataMerger dataMerger = new DataMerger(cityFold);
					File mergedSavePath = new File(properties.getProperty("save_path"),"Merge");
					if(!mergedSavePath.exists()){
						mergedSavePath.mkdirs();
					}

					String save = new File(mergedSavePath,"Deal_"+ct+".csv").getAbsolutePath();

					dataMerger.writeDealMergeData(save);


				}else if(m.equals("2")){
					File file = new File(properties.getProperty("save_path"),"Rent");
					System.out.println("选择想要拼接的城市，输入简称");
					System.out.println(Arrays.toString(file.list()));
					String ct = scanner.next();
					String cityFold = new File(file,ct).getAbsolutePath();
					System.out.println("输入开始的年月（格式如：200212,输入0不指定，不支持指定结束而不指定开始）");
					int start = scanner.nextInt();
					System.out.println("输入结束的年月（格式如：200212,输入0不指定，不支持指定结束而不指定开始）");
					int end = scanner.nextInt();
					DataMerger dataMerger = null;
					if(start==0&&end==0){
						end = Integer.parseInt(new SimpleDateFormat("yyyyMM").format(new Date(System.currentTimeMillis())));
						dataMerger = new DataMerger(cityFold);
					}else if(end==0){
						end = Integer.parseInt(new SimpleDateFormat("yyyyMM").format(new Date(System.currentTimeMillis())));
						dataMerger = new DataMerger(cityFold,start);
					}else if (start !=0 && end !=0){
						dataMerger = new DataMerger(cityFold,start,end);
					}else{
						System.out.println("不支持指定结束而不指定开始");
						continue;
					}
					File mergedSavePath = new File(properties.getProperty("save_path"),"Merge");
					if(!mergedSavePath.exists()){
						mergedSavePath.mkdirs();
					}
					String save = new File(mergedSavePath,"Rent_"+dataMerger.getStartDateInt()+"_"+dataMerger.getEndDateInt()+"_"+ct+".csv").getAbsolutePath();

					dataMerger.writeRentMergeData(save);


				}


			} else if (type.equals("2")) {
				System.out.println("按1选择创建数据模式，按2选择更新模式");
				String mode = scanner.next();
				System.out.println("可爬取的城市有");
				String configCity = properties.getProperty("rentCity");
				configCity = new String(configCity.getBytes(Charset.defaultCharset()), "utf-8");
				System.out.println(configCity);
				System.out.println("输入爬取的城市中文名(输入a为全部爬取)，Enter结束");
				String city = scanner.next();
				if (city.equals("a")) {
					String[] cities = configCity.split(" ");
					for (String c : cities) {
//					System.out.println(c);
						Crawl crawl = new Crawl(properties, c);
						if (mode.equals("1")) {
							crawl.mainProgram();
						} else if (mode.equals("2")) {
							crawl.update();
						}
					}

				} else {
					String[] cities = city.split(" ");
					for (String c : cities) {

						Crawl crawl = new Crawl(properties, c);
						if (mode.equals("1")) {
							crawl.mainProgram();
						} else if (mode.equals("2")) {
							crawl.update();
						}
					}

				}

			} else {
				System.out.println("可爬取的城市有");
				String configCity = properties.getProperty("dealCity");
				configCity = new String(configCity.getBytes(Charset.defaultCharset()), "utf-8");
				System.out.println(configCity);
				System.out.println("输入爬取的城市中文名(输入a为全部爬取)，Enter结束");
				String city = scanner.next();
				if (type.equals("1")) {
					if (city.equals("a")) {
						String[] cities = configCity.split(" ");
						for (String c : cities) {
//					System.out.println(c);
							DealCrawl crawl = new DealCrawl(properties, c);
							crawl.crawlAndWriteCSV();
						}

					} else {
						String[] cities = city.split(" ");
						for (String c : cities) {


							DealCrawl crawl = new DealCrawl(properties, c);
							crawl.crawlAndWriteCSV();
						}

					}

				} else if (type.equals("3")) {
					if (city.equals("a")) {
						String[] cities = configCity.split(" ");
						for (String c : cities) {
//					System.out.println(c);
							CommunityCrawl crawl = new CommunityCrawl(properties, c);
							crawl.crawlCommunity();
						}

					} else {
						String[] cities = city.split(" ");
						for (String c : cities) {

							CommunityCrawl crawl = new CommunityCrawl(properties, c);
							crawl.crawlCommunity();
						}


					}


				}


			}

		}




		
	}

}
