package Lianjia.app;

import Lianjia.community.CommunityCrawl;
import Lianjia.data.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

/**
 *
 */
public class Crawl {

    private String cityId;
    private String cityName;
    private String cityAbbr;
    private String communityUrl;
    private Date date = new Date(System.currentTimeMillis());
    private Date lastDate = new Date(0L);
    private int thisTimeCrawl = 0;
    private int numHouseCrawled = 0;
    private int totalHouseCrawled = 0;
    private List<Rented> updateList = new ArrayList<Rented>(); //used only for update purpose
    private Properties properties;
    private Decode decode;
    private Set<String> idSet = new HashSet<String>();
    private HashMap<String, HashMap<String, String>> regions = new HashMap<String, HashMap<String, String>>();
    private HashMap<String, String> rent_price = new HashMap<String, String>();
    private HashMap<String, HashMap<String, String>> rent_room = new HashMap<String, HashMap<String, String>>();
    private HashMap<String, HashMap<String, String>> rented_more = new HashMap<String, HashMap<String, String>>();


    public Crawl(String cityName) {
        this.properties = new Properties();
        this.cityName = cityName;

        File file = new File("src/main/java/config.properties");
        try {
            properties.load(new FileInputStream(file));

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            readCityInfo();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public Crawl(String propertiesPath, String cityName) {
        this.properties = new Properties();
        this.cityName = cityName;
        File file = new File(propertiesPath);
        try {
            properties.load(new FileInputStream(file));
            readCityInfo();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Crawl(Properties properties, String cityName) {
        this.properties = properties;
        this.cityName = cityName;
        try {
            readCityInfo();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void readCityInfo() throws FileNotFoundException {
        String configFold = properties.getProperty("config_fold");
        readCityConfig(configFold);
        String appSecret = properties.getProperty("appSecret");
        String appId = properties.getProperty("appId");
        decode = new Decode(appSecret,appId);
        File file = new File("src/main/resources/config/" + cityId + ".json");

        Gson gson = new Gson();
        CityConfig cityConfig = gson.fromJson(new FileReader(file), CityConfig.class);
        CityConfig.Data.Search_filter.Check_filters filters
                = cityConfig.getData().getSearch_filters().getCheck_filters();
        CityConfig.Data.Search_filter.Check_filters.Option region = filters.getRegion();
        CityConfig.Data.Search_filter.Check_filters.Option[] districtOptions = region.getOptions()[0] //获得分区的选项
                .getOptions();
        for (CityConfig.Data.Search_filter.Check_filters.Option option : districtOptions) {
            String name = option.getName();
            if ("不限".equals(name))
                continue;
            HashMap<String, String> smallRegion = null;
            if (regions.containsKey(name)) {
                smallRegion = regions.get(name);
            } else {
                smallRegion = new HashMap<String, String>();
            }
            for (CityConfig.Data.Search_filter.Check_filters.Option o : option.getOptions()) {
                smallRegion.put(o.getName(), o.getKey().replace("/", ""));
            }
            regions.put(name, smallRegion);
        }

        CityConfig.Data.Search_filter.Check_filters.Option[] rp = filters.getRent_price().getOptions();
        for (CityConfig.Data.Search_filter.Check_filters.Option option : rp) {
            String name = option.getName();
            String key = option.getKey();
            if ("不限".equals(name))
                continue;
            rent_price.put(name, key);
        }

        CityConfig.Data.Search_filter.Check_filters.Option[] roomOptions = filters.getRent_room().getOptions();
        for (CityConfig.Data.Search_filter.Check_filters.Option roomOption : roomOptions
        ) {

            String roomName = roomOption.getName();
            HashMap<String, String> roomDetails = null;
            if (rent_room.containsKey(roomName)) {
                roomDetails = rent_room.get(roomName);
            } else
                roomDetails = new HashMap<String, String>();

            for (CityConfig.Data.Search_filter.Check_filters.Option o :
                    roomOption.getOptions()) {
                roomDetails.put(o.getName(), o.getKey());
            }
            rent_room.put(roomName, roomDetails);
        }


        CityConfig.Data.Search_filter.Check_filters.Option[] rms = filters.getRented_more().getOptions();
        for (CityConfig.Data.Search_filter.Check_filters.Option option : rms) {
            String moreName = option.getName();
            HashMap<String, String> moreTypes = null;
            if (rented_more.containsKey(moreName)) {
                moreTypes = rented_more.get(moreName);
            } else {
                moreTypes = new HashMap<String, String>();
            }
            CityConfig.Data.Search_filter.Check_filters.Option[] typeOptions = option.getOptions();
            for (CityConfig.Data.Search_filter.Check_filters.Option typeOption : typeOptions) {
                moreTypes.put(typeOption.getName(), typeOption.getKey());
            }
            rented_more.put(moreName, moreTypes);

        }


    }

    public RentList crawlListContent(String url) {
        String auth = decode.code(url, false);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Accept-Encoding", "gzip");
        headers.put("Connection", "Keep-Alive");
        headers.put("Authorization", auth);
        Document doc = null;
        while (true) {
            try {
                doc = Jsoup.connect(url).headers(headers).ignoreContentType(true).get();
                String listJson = doc.getElementsByTag("body").first().text();
//                System.out.println(doc);
                Gson gson = new Gson();
                RentList rentList = gson.fromJson(listJson, RentList.class);
                if (rentList != null) {
                    return rentList;
                } else {
                    System.out.println(url);
                    System.out.println(doc);
                }
            } catch (Exception e) {
//                e.printStackTrace();
//                System.out.println(doc);

            }
        }
    }

    public void crawlQuickUpdateList(String page) {
        List<Rented> rl = new ArrayList<Rented>();
        String url = "https://app.api.lianjia.com/" +
                "house/rented/search?" +
                "city_id=" + cityId +
                "&priceRequest=" +
                "&limit_offset=" + page +
                "&communityRequset=" +
                "&moreRequest=" +
                "&is_suggestion=0" +
                "&limit_count=100" +
                "&comunityIdRequest=" +
                "&sugQueryStr=" +
                "&areaRequest=" +
                "&is_history=0" +
                "&condition=" +
                "&roomRequest=" +
                "&isFromMap=false";


//        System.out.println(url);

        try {
            RentList rentList = crawlListContent(url);
            RentList.Data data = rentList.getData();
            if (data.getList() == null) {
                return;
            }
            List<Rented> infos = Arrays.asList(data.getList());


            int total_count = data.getTotal_count();
            int has_more_data = data.getHas_more_data();
//            ExecutorService service = Executors.newCachedThreadPool();

            this.updateList.addAll(infos);

            if (page.equals("2000")) {
                return;
            }
            final int nextPage = Integer.parseInt(page) + 100;
            int totalPage = (int) (100 * Math.ceil(total_count / 100.0));
            System.out.println(this.cityName + " 爬取下一页：" + nextPage + " / " + totalPage);
            crawlQuickUpdateList("" + nextPage);

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public void crawlUpdateList(String page) {
        List<Rented> rl = new ArrayList<Rented>();
        String url = "https://app.api.lianjia.com/" +
                "house/rented/search?" +
                "city_id=" + cityId +
                "&priceRequest=" +
                "&limit_offset=" + page +
                "&communityRequset=" +
                "&moreRequest=" +
                "&is_suggestion=0" +
                "&limit_count=100" +
                "&comunityIdRequest=" +
                "&sugQueryStr=" +
                "&areaRequest=" +
                "&is_history=0" +
                "&condition=" +
                "&roomRequest=" +
                "&isFromMap=false";


//        System.out.println(url);

        try {
            RentList rentList = crawlListContent(url);
            RentList.Data data = rentList.getData();
            if (data.getList() == null) {
                return;
            }
            List<Rented> infos = Arrays.asList(data.getList());


            int total_count = data.getTotal_count();
            int has_more_data = data.getHas_more_data();
            ExecutorService service = Executors.newCachedThreadPool();
            if (total_count > 2000) {
                //需要查找更细节的信息，先价格然后再户型
//
                for (Map.Entry<String, HashMap<String, String>> srs : regions.entrySet()) {
                    String district = srs.getKey();
                    HashMap<String, String> pair = srs.getValue();
                    for (Map.Entry<String, String> e : pair.entrySet()) {
                        final String d = district;
                        final String sr = e.getKey();

                        if (!e.getKey().equals("不限"))
                            crawlUpdateList(d, sr, "0");


                    }

                }


            } else {
                this.updateList.addAll(infos);
                if (has_more_data == 0) {

                } else {
                    final int nextPage = Integer.parseInt(page) + 100;
                    int totalPage = (int) (100 * Math.ceil(total_count / 100.0));
                    System.out.println(this.cityName + " 爬取下一页：" + nextPage + " / " + totalPage);
                    service.submit(new Runnable() {
                        public void run() {
                            crawlUpdateList("" + nextPage);
                        }
                    });
                }

            }
            service.shutdown();


        } catch (Exception e) {
            e.printStackTrace();

        }

    }


    public void crawlUpdateList(String district, String smallRegion, String rentPriceTag, String page) {
//        List<Rented> rl = new ArrayList<Rented>();
        String smallRegionCode = regions.get(district).get(smallRegion);


        String tagEng = rent_price.get(rentPriceTag);
        String url = "https://app.api.lianjia.com/house/rented/search?" +
                "city_id=" + cityId + "&" +
                "districtNameText=" + district + "&" +
                "min_longitude=&" +
                "priceRequest=" + tagEng + "&" +
                "limit_offset=" + page + "&" +
                "moreRequest=&" +
                "communityRequset=&" +
                "is_suggestion=0&" +
                "limit_count=100&" +
                "max_longitude=&" +
                "max_latitude=&" +
                "sugQueryStr=" +
                "&comunityIdRequest=&" +
                "areaRequest=" + smallRegionCode + "/" + "&" +
                "regionNameText=" + smallRegion + "&" +
                "areaTabText=" + smallRegion + "&" +
                "is_history=0&" +
                "condition=" + smallRegionCode + "/" + tagEng + "&" +
                "areaNameText=区域&" +
                "roomRequest=" + "&" +
                "isFromMap=false&" +
                "min_latitude=&" +
                "housePriceText=" + rentPriceTag;


//        System.out.println(url);

        try {
            RentList rentList;
            rentList = crawlListContent(url);
            RentList.Data data = rentList.getData();
            if (data.getList() == null) {
                return;
            }
            List<Rented> infos = Arrays.asList(data.getList());


            int total_count = data.getTotal_count();
            int has_more_data = data.getHas_more_data();
            ExecutorService service = Executors.newFixedThreadPool(20);
            if (total_count > 2000) {
                //需要查找更细节的信息，先价格然后再户型
                HashMap<String, String> cat = rent_room.get("卧室");


                for (Map.Entry<String, String> entry : cat.entrySet()) {
                    final String room = entry.getKey();
                    final String d = district;
                    final String sr = smallRegion;
                    final String rpt = rentPriceTag;
                    crawlUpdateList(d, sr, rpt, "0");


                }


//                rl.addAll(crawlUpdateList(district, smallRegion, rentPriceTag, room, "0"));

//                for (Map.Entry<String,HashMap<String,String>> srs : regions.entrySet()) {
//
//
//                    rl.addAll(crawlList(cityId, key, "0"));
//                }


            } else {
                this.updateList.addAll(infos);
                if (has_more_data == 0) {
                    return;
                } else {
                    final int nextPage = Integer.parseInt(page) + 100;
                    int totalPage = (int) (100 * Math.ceil(total_count / 100.0));
                    System.out.println(this.cityName + district + smallRegion + rentPriceTag + " 爬取下一页：" + nextPage + " / " + totalPage);
                    final String d = district;
                    final String sr = smallRegion;
                    final String rpt = rentPriceTag;
                    Thread.sleep(1000);

                    service.submit(new Runnable() {
                        public void run() {
                            crawlUpdateList(d, sr, rpt, "" + nextPage);
                        }
                    });

                }

            }
//            while (true){
//                if(service.isTerminated()){
//
//                    System.out.println(cityName+district+smallRegion+rentPriceTag+page+"完成");
//                    service.shutdown();
//                    break;
//                }
//            }
            service.shutdown();

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public void crawlUpdateList(String district, String smallRegion, String page) {
//        List<Rented> rl = new ArrayList<Rented>();
        HashMap<String, String> srp = regions.get(district);

        String smallRegionCode = srp.get(smallRegion);
        String url = "https://app.api.lianjia.com/house/rented/search?" +
                "city_id=" + cityId + "&" +
                "districtNameText=" + district + "&" +
                "min_longitude=&" +
                "priceRequest=&" +
                "limit_offset=" + page + "&" +
                "moreRequest=&" +
                "communityRequset=&" +
                "is_suggestion=0&" +
                "limit_count=100&" +
                "max_longitude=&" +
                "max_latitude=&" +
                "sugQueryStr=" +
                "&comunityIdRequest=&" +
                "areaRequest=" + smallRegionCode + "/" + "&" +
                "regionNameText=" + smallRegion + "&" +
                "areaTabText=" + smallRegion + "&" +
                "is_history=0&" +
                "condition=" + smallRegionCode + "/" + "&" +
                "areaNameText=区域&" +
                "roomRequest=&" +
                "isFromMap=false&" +
                "min_latitude=";


//        System.out.println(url);

        try {
            RentList rentList;
            rentList = crawlListContent(url);
            RentList.Data data = rentList.getData();
            if (data.getList() == null) {
                return;
            }
            List<Rented> infos = Arrays.asList(data.getList());


            int total_count = data.getTotal_count();
            int has_more_data = data.getHas_more_data();
            ExecutorService service = Executors.newCachedThreadPool();
            if (total_count > 2000) {
                //需要查找更细节的信息，先价格然后再户型
                for (Map.Entry<String, String> entry : rent_price.entrySet()) {
                    final String d = district;
                    final String sr = smallRegion;
                    final String rp = entry.getKey();

                    crawlUpdateList(d, sr, rp, "0");


                }
            } else {
                this.updateList.addAll(infos);
                if (has_more_data == 0) {
                    return;
                } else {
                    final int nextPage = Integer.parseInt(page) + 100;
//                    System.out.println(nextPage);
                    int totalPage = (int) (100 * Math.ceil(total_count / 100.0));
                    System.out.println(this.cityName + district + smallRegion + " 爬取下一页：" + nextPage + " / " + totalPage);
                    final String d = district;
                    final String sr = smallRegion;


                    service.submit(new Runnable() {
                        public void run() {
                            crawlUpdateList(d, sr, "" + nextPage);
                        }
                    });
                }

            }

//            while (true){
//                if(service.isTerminated()){
//
//                    System.out.println(cityName+district+smallRegion+page+"完成");
//                    service.shutdown();
//                    break;
//                }
//            }
            service.shutdown();

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public void crawlUpdateList(String district, String smallRegion,
                                String rentPriceTag, String roomTag, String page) {
        List<Rented> rl = new ArrayList<Rented>();
        String smallRegionCode = this.regions.get(district).get(smallRegion);
        String tagEng = rent_price.get(rentPriceTag);
        String roomEng = rent_room.get("卧室").get(rentPriceTag);
        String url = "https://app.api.lianjia.com/house/rented/search?" +
                "city_id=" + cityId + "&" +
                "districtNameText=" + district + "&" +
                "min_longitude=&" +
                "priceRequest=" + tagEng + "&" +
                "limit_offset=" + page + "&" +
                "moreRequest=&" +
                "limit_count=100&" +
                "max_longitude=&" +
                "comunityIdRequest=&" +
                "areaRequest=" + smallRegionCode + "/" + "&" +
                "condition=" + smallRegionCode + "/" + tagEng + roomEng + "&" +
                "roomRequest=" + roomEng + "&" +
                "isFromMap=false&" +
                "roomCountText=" + roomTag + "," + "&" +
                "housePriceText=" + rentPriceTag + "&" +
                "communityRequset=&" +
                "is_suggestion=0&" +
                "max_latitude=&" +
                "sugQueryStr=&" +
                "areaTabText=" + smallRegion + "&" +
                "regionNameText=" + smallRegion + "&" +
                "is_history=0&" +
                "areaNameText=区域&" +
                "roomTabText=" + roomTag + "&" +
                "min_latitude=&";


//        System.out.println(url);

        try {
            RentList rentList;
            rentList = crawlListContent(url);
            RentList.Data data = rentList.getData();
            if (data.getList() == null) {
                return;
            }
            List<Rented> infos = Arrays.asList(data.getList());


            int total_count = data.getTotal_count();
            int has_more_data = data.getHas_more_data();

            // TODO: 2019/5/22 是不是可以再增加一些往下的内容

            this.updateList.addAll(infos);
            ExecutorService service = Executors.newFixedThreadPool(20);
            if (has_more_data == 0 || page.equals("2000")) {

            } else {
                final int nextPage = Integer.parseInt(page) + 100;
                int totalPage = (int) (100 * Math.ceil(total_count / 100.0));
                System.out.println(this.cityName + district + smallRegion + rentPriceTag + roomTag + " 爬取下一页：" + nextPage + " / " + totalPage);
                final String d = district;
                final String sr = smallRegion;
                final String rpt = rentPriceTag;
                final String rt = roomTag;

                service.submit(new Runnable() {
                    public void run() {
                        crawlUpdateList(d, sr, rpt, rt, "" + nextPage);
                    }
                });

            }
            service.shutdown();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public List<Rented> crawlList(String comunityIdRequest, String page) {

        List<Rented> rl = new ArrayList<Rented>();
        String url = "https://app.api.lianjia.com/" +
                "house/rented/search?" +
                "city_id=" + cityId +
                "&priceRequest=" +
                "&limit_offset=" + page +
                "&communityRequset=" +
                "&moreRequest=" +
                "&is_suggestion=0" +
                "&limit_count=100" +
                "&comunityIdRequest=" + comunityIdRequest +
                "&sugQueryStr=" +
                "&areaRequest=" +
                "&is_history=0" +
                "&condition=" + comunityIdRequest +
                "&roomRequest=" +
                "&isFromMap=false";
//        System.out.println(url);

        try {
            RentList rentList = crawlListContent(url);
            RentList.Data data = rentList.getData();
            if (data.getList() == null) {
                return new ArrayList<Rented>();
            }
            List<Rented> infos = Arrays.asList(data.getList());


            int total_count = data.getTotal_count();
            int has_more_data = data.getHas_more_data();
            if (total_count > 2000) {
                //需要查找更细节的信息，先价格然后再户型

                for (String key : rent_price.keySet()) {
                    rl.addAll(crawlList(comunityIdRequest, key, "0"));
                }
                return rl;

            } else {
                rl.addAll(infos);
                if (has_more_data == 0) {
                    return rl;
                } else {
                    int nextPage = Integer.parseInt(page) + 100;
//                    System.out.println(nextPage);
                    rl.addAll(crawlList(comunityIdRequest, "" + nextPage));
                    return rl;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    public List<Rented> crawlList(String comunityIdRequest, String rentpriceTag, String page) throws Exception {
        List<Rented> renteds = new ArrayList<Rented>();


        String url = "https://app.api.lianjia.com/house/rented/search?" +
                "city_id=" + cityId + "&" +
                "priceRequest=" + rentpriceTag + "&" +
                "limit_offset=" + page + "&" +
                "communityRequset=&" +
                "moreRequest=&" +
                "is_suggestion=0&" +
                "limit_count=100&" +
                "sugQueryStr=&" +
                "comunityIdRequest=" + comunityIdRequest + "&" +
                "areaRequest=&" +
                "is_history=0&" +
                "condition=" + comunityIdRequest + rentpriceTag + "&" +
                "roomTabText=" + "房型" + "&" +//%E6%88%BF%E5%9E%8B& 房型
                "roomRequest=&" +
                "isFromMap=false&" +
                "roomCountText=&" +
                "housePriceText=" + rent_price.get(rentpriceTag);  //2000%E5%85%83%E4%BB%A5%E4%B8%8B
//        System.out.println(url);

        RentList rentList = crawlListContent(url);
        RentList.Data data = rentList.getData();
        if (data.getList() == null) {
            System.out.println("no data");
            return new ArrayList<Rented>();
        }

        int total_count = data.getTotal_count();
        int has_more_data = data.getHas_more_data();
        if (total_count > 2000) {
            for (String key : rent_room.get("卧室").keySet()) {
                renteds.addAll(
                        crawlList(comunityIdRequest, rentpriceTag, key, "0"));
            }
            return renteds;
        } else {
            List<Rented> infos = Arrays.asList(data.getList());
//            for(Rented info : infos){
//                System.out.println(info);
//            }
            renteds.addAll(infos);
            if (has_more_data == 0) {
                return renteds;
            } else {
                if (Integer.parseInt(page) > 2000) {
                    System.out.println(comunityIdRequest);
                    System.out.println(url);

                }
                int nextPage = Integer.parseInt(page) + 100;
                System.out.println(nextPage);
                renteds.addAll(crawlList(comunityIdRequest, rentpriceTag, "" + nextPage));
                return renteds;
            }

        }

    }

    public List<Rented> crawlList(String comunityIdRequest, String rentpriceTag, String roomTag, String page) throws Exception {
        List<Rented> renteds = new ArrayList<Rented>();

        String url = "https://app.api.lianjia.com/house/rented/search?" +
                "city_id=" + cityId + "&" +
                "priceRequest=" + rentpriceTag + "&" +
                "limit_offset=" + page + "&" +
                "communityRequset=&" +
                "moreRequest=&" +
                "is_suggestion=0&" +
                "limit_count=100&" +
                "sugQueryStr=&" +
                "comunityIdRequest=" + comunityIdRequest + "&" +
                "areaRequest=&" +
                "is_history=0&" +
                "condition=" + comunityIdRequest + rentpriceTag + roomTag + "&" +
                "roomTabText=" + rent_room.get("卧室").get(roomTag) + "&" +
                "roomRequest=" + roomTag + "&" +
                "isFromMap=false&" +
                "roomCountText=" + rent_room.get("卧室").get(roomTag) + "，" + "&" +
                "housePriceText=" + rent_price.get(rentpriceTag);
//        System.out.println(url);

        RentList rentList = crawlListContent(url);
        RentList.Data data = rentList.getData();
        if (data.getList() == null) {
            return new ArrayList<Rented>();
        }
        int total_count = data.getTotal_count();
        int has_more_data = data.getHas_more_data();
        if (total_count > 2000) {
            System.out.println(" 依旧超过两千条   " + url);
        }
        List<Rented> infos = Arrays.asList(data.getList());
        renteds.addAll(infos);
        for (Rented rented :
                infos) {
            System.out.println(comunityIdRequest + " " + page + " " + infos);
        }
        if (has_more_data == 0 || page.equals("2000")) {
            return renteds;
        } else {
            int nextPage = Integer.parseInt(page) + 100;
            renteds.addAll(crawlList(comunityIdRequest, rentpriceTag, roomTag, "" + nextPage));
            return renteds;
        }

    }


    private Map<String, String> getCommunityId(String path) throws IOException {
        Map<String, String> communityDistrictPair = new HashMap<String, String>();
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(new FileInputStream(new File(path))));
        String data = bufferedReader.readLine();
        while (data != null) {
            String[] token = data.split(",");
            if (!token[2].equals("null")) {
                communityDistrictPair.put(token[2], token[0] + "/" + token[1]);
            }
            data = bufferedReader.readLine();
        }
        return communityDistrictPair;
    }

    private Set<String> readHouseCode(String path) throws IOException {
        Set<String> set = new HashSet<String>();
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(new FileInputStream(new File(path))));
        String data = bufferedReader.readLine();
        while (data != null) {
            if (!data.equals("null")) {
                set.add(data);
            } else {
                System.out.println(data);
            }
            data = bufferedReader.readLine();
        }

        return set;

    }


    /**
     *
     */
    public void crawAllList() throws IOException {
        String saveFile = properties.getProperty("save_path") + "/Rent/" + cityAbbr;
        String communityIdFile = new File(new File(saveFile), cityAbbr + "_community.csv").getAbsolutePath();
        Map<String, String> communityDistrictPair = getCommunityId(communityIdFile);
        File houseCode = new File(saveFile, cityId + "_index.txt");
        Set<String> houseCodeSet = null;
        if (houseCode.exists()) {
            houseCodeSet = readHouseCode(houseCode.getAbsolutePath());
        } else {
            houseCodeSet = new HashSet<String>();
        }

        class myThread implements Callable<List<Rented>> {
            private String cityId;
            private String communityId;

            public myThread(String cityId, String communityId) {
                super();
                this.cityId = cityId;
                this.communityId = communityId;
            }

            public List<Rented> call() throws Exception {
//                System.out.println("传进的参数 "+this.communityId);
                List<Rented> list = crawlList(this.cityId, "c" + this.communityId, "0");

                return list;
            }
        }
        ExecutorService executorService = Executors.newScheduledThreadPool(100);
        ArrayList<Future<List<Rented>>> futures = new ArrayList<Future<List<Rented>>>();

        for (Map.Entry<String, String> entry : communityDistrictPair.entrySet()) {
//            File file1 = new File(saveFile, entry.getValue());
//            if (!file1.exists()) {
//                file1.mkdirs();
//                System.out.println("make dirs:" + file1.getAbsolutePath());
//            }
            futures.add(executorService.submit(new myThread(cityId, entry.getKey())));
        }
        executorService.shutdown();
        for (Future<List<Rented>> future : futures) {
            try {
                List<Rented> rentedList = future.get();
                for (Rented rented : rentedList) {
                    houseCodeSet.add(rented.getHouse_code());
                }

            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ExecutionException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        PrintWriter printWriter = new PrintWriter(houseCode.getAbsoluteFile());
        for (String code : houseCodeSet) {
            printWriter.println(code);
        }
        printWriter.flush();
        printWriter.close();


    }

    public RentDeal crawlRentedHouse(String houseCode) {
        StringBuilder rentString = new StringBuilder();
        String url = "https://app.api.lianjia.com/house/zufang/detailpart1?house_code=" + houseCode;
        RentDeal rentDeal = new RentDeal();

        RentInfo rentInfo = crawlRentedHouseContent(url);

        RentInfo.RentData data = rentInfo.getData();
        rentDeal.setPitures(Arrays.toString(data.getPicture_list()));
        RentInfo.RentData.BasicInfo basicInfo = data.getBasic_info();
        rentDeal.setTitle(basicInfo.getTitle());
        rentDeal.setCityId(basicInfo.getCity_id());
        rentDeal.setCommunityId(basicInfo.getCommunity_id());
        rentDeal.setCommunityName(basicInfo.getCommunity_name());
        rentDeal.setHouseCode(basicInfo.getHouse_code());
        rentDeal.setmURL(basicInfo.getM_url());
        rentDeal.setOrientation(basicInfo.getOrientation());
        rentDeal.setFloor(basicInfo.getFloor_state());
        int ziru = basicInfo.getIs_ziroom();
        rentDeal.setZiru("" + ziru);
        rentDeal.setArea("" + basicInfo.getArea());
        rentDeal.setDealRent(basicInfo.getPrice());
        rentDeal.setStatus(basicInfo.getHouse_status());
        RentInfo.RentData.BasicList[] bl = data.getBasic_list();

        for (RentInfo.RentData.BasicList b : bl) {
            String name = b.getName();
            String value = b.getValue();
            if ("房型".equals(name)) {
                rentDeal.setRoomType(value);
            }

        }
        for (RentInfo.RentData.InfoList tuple : data.getInfo_list()) {
            String name = tuple.getName();
            String value = tuple.getValue();
            if ("方式：".equals(name)) {
                rentDeal.setRentType(value);
            } else if ("发布：".equals(name)) {
                rentDeal.setBoradDate(value);
            } else if ("电梯：".equals(name)) {
                rentDeal.setElevator(value);
            } else if ("供暖：".equals(name)) {
                rentDeal.setHeating(value);
            } else if ("年代：".equals(name)) {
                rentDeal.setAge(value);
            } else if ("车位：".equals(name)) {
                rentDeal.setLot(value);
            } else if ("装修：".equals(name)) {
                rentDeal.setDecoration(value);
            } else if ("现状：".equals(name)) {
                rentDeal.setStatus(value);
            } else if ("租期：".equals(name)) {
                rentDeal.setRentTime(value);
            } else if ("入住：".equals(name)) {
                rentDeal.setRuzhu(value);
            } else if ("成交：".equals(name)) {
                rentDeal.setDealDate(value);
            }
        }

        for (RentInfo.RentData.RentDealInfo.Review.Tuple tuple
                : data.getDeal_info().getReview().getList()) {
            String name = tuple.getName();
            String value = tuple.getValue();
            if (name.contains("挂牌价格")) {
                rentDeal.setOriginRent(value);
            } else if ("成交周期（天）".equals(name)) {
                rentDeal.setDealPeriod(value);
            } else if ("调价（次）".equals(name)) {
                rentDeal.setChangePrice(value);
            } else if ("带看（次）".equals(name)) {
                rentDeal.setShow(value);
            } else if ("关注（人）".equals(name)) {
                rentDeal.setFollow(value);
            }


        }


//        if (ziru == 1) {
//
//            data.getPayway().toString();
//            rentDeal.setPayway(data.getPayway().toString());
//        }

        rentDeal.setLon("" + data.getLocation().getBaidu_lo());
        rentDeal.setLat("" + data.getLocation().getBaidu_la());
        String title = data.getLocation().getTitle();

        try {
            String[] titles = title.split("，");
            rentDeal.setDistrict(titles[0]);
            rentDeal.setSmallRegion(titles[1]);
        }catch (ArrayIndexOutOfBoundsException e){
            rentDeal.setDistrict("未知");
            rentDeal.setSmallRegion("未知");
        }
        return rentDeal;

    }

    public RentInfo crawlRentedHouseContent(String url) {
        String auth = decode.code(url, false);
        HashMap<String, String> headers = new HashMap<String, String>();
//        Page-Schema: RentalHouseTransactionsActivity
//        If-None-Match: W/"75c87dc1f38d67ca48c25d5627eb7013"

//        Referer: chengjiaorent%2Flist%3Fcity_id%3D440300%26priceRequest%3D%26limit_offset%3D0%26communityRequset%3D%26moreRequest%3D%26is_suggestion%3D0%26limit_count%3D20%26sugQueryStr%3D%26comunityIdRequest%3Dc2411053195041%26areaRequest%3D%26is_history%3D1%26condition%3Dc2411053195041%26roomRequest%3D%26isFromMap%3Dfalse%26queryStringText%3D%E4%B8%87%E5%AE%B6%E7%81%AF%E7%81%AB
//        Cookie: lianjia_udid=99000869041522;lianjia_ssid=30ee6c10-ef75-422a-a7e9-4af865b661e5;lianjia_uuid=4877d394-059e-4629-a98d-79c152055f29
//        extension: lj_imei=99000869041522&lj_duid=null&lj_android_id=742a866d9d390de9&lj_device_id_android=99000869041522&mac_id=B4:0B:44:B6:02:8C
//        headers.put("User-Agent","HomeLink9.1.9;SMARTISAN OD105; Android 7.1.1");
//        headers.put("Lianjia-Channel","Android_lianjia_pc");
//        headers.put("Lianjia-Device-Id","99000869041522");
//        这个头很关键，就是有了这个头才有具体的成交信息
        headers.put("Lianjia-Version", "9.1.9");
//        headers.put("Lianjia-City-Id", "440300");
//        Authorization: MjAxNzAzMjRfYW5kcm9pZDozZWNmN2UwN2U1NjBkNWY2YjExOTg4Mzk3ZjE0NDg0MTczMzNiOTA1
//        Lianjia-Im-Version: 2.29.0
//        Host: app.api.lianjia.com
//

//        headers.put("Page-Schema","RentalHouseTransactionsActivity");
//        headers.put("If-None-Match","W/\"75c87dc1f38d67ca48c25d5627eb7013\"");
//        headers.put("Cookie","lianjia_udid=99000869041522;lianjia_ssid=30ee6c10-ef75-422a-a7e9-4af865b661e5;lianjia_uuid=4877d394-059e-4629-a98d-79c152055f29");
//        headers.put("extension","lj_imei=99000869041522&lj_duid=null&lj_android_id=742a866d9d390de9&lj_device_id_android=99000869041522&mac_id=B4:0B:44:B6:02:8C");
//        headers.put("Referer","chengjiaorent%2Flist%3Fcity_id%3D440300%26priceRequest%3D%26limit_offset%3D0%26communityRequset%3D%26moreRequest%3D%26is_suggestion%3D0%26limit_count%3D20%26sugQueryStr%3D%26comunityIdRequest%3Dc2411053195041%26areaRequest%3D%26is_history%3D1%26condition%3Dc2411053195041%26roomRequest%3D%26isFromMap%3Dfalse%26queryStringText%3D%E4%B8%87%E5%AE%B6%E7%81%AF%E7%81%AB");
        headers.put("Accept-Encoding", "gzip");
        headers.put("Connection", "Keep-Alive");
        headers.put("Authorization", auth);
        Document doc = null;
        while (true) {
            try {
//                System.out.println(url);
                doc = Jsoup.connect(url).headers(headers).ignoreContentType(true).timeout(10000).get();
                String listJson = doc.getElementsByTag("body").first().text();
//                System.out.println(doc);
                Gson gson = new Gson();
                RentInfo list = gson.fromJson(listJson, RentInfo.class);


                if (list != null) {
                    synchronized (this) {
                        numHouseCrawled++;
                        System.out.println(this.cityName + "完成进度:  " + numHouseCrawled + "/" + thisTimeCrawl);
                    }


                    return list;
                } else {
                    System.out.println("return a null list: " + url);
//                    System.out.println(doc);
                }
            } catch (Exception e) {
//                e.printStackTrace();
//                System.out.println(doc);

            }
        }

    }


    // 从保存的housecode中读取，然后爬取，保存数据，数据较多，每五万写一次，每个区保存为一个文件夹，然后文件夹下，小区id
    public void crawlAllRentedHouse() throws IOException {


        String savePath = properties.getProperty("save_path") + "/Rent/" + cityAbbr;
        File indexFile = new File(savePath, cityId + "_index.txt");
        File crawledList = new File(savePath, cityId + "_crawled_index.txt");
        if (!indexFile.exists()) {
            System.out.println("还没进行过该城市的爬虫,没有未完成的任务");
            return;
        }
        Set<String> houseCodes = readHouseCode(indexFile.getAbsolutePath());
        FileWriter crawledFileWriter = null;
        Set<String> crawled = new HashSet<String>();
        if (!crawledList.exists()) {
            crawledFileWriter = new FileWriter(crawledList.getAbsoluteFile(), true);

        } else {
            crawledFileWriter = new FileWriter(crawledList.getAbsoluteFile(), true);
            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(new FileInputStream(crawledList)));
            String line = bufferedReader.readLine();
            while (line != null) {
                crawled.add(line);
                line = bufferedReader.readLine();
            }

        }
        houseCodes.removeAll(crawled);
        thisTimeCrawl = houseCodes.size();


        ArrayList<String> codeArray = new ArrayList<String>(houseCodes);
        ExecutorService service = Executors.newScheduledThreadPool(150);
        List<List<String>> codeArrayArray = new ArrayList<>();
        for (int i = 0; i < codeArray.size(); i = i + 20000) {
            int j = Math.min(i + 20000, codeArray.size());
//            List<String> strings = codeArray.subList(i, j);
            codeArrayArray.add(codeArray.subList(i,j));
        }

        for (List<String> strings : codeArrayArray) {
            ArrayList<Future<RentDeal>> futures = new ArrayList<Future<RentDeal>>();
            Map<String, Vector<RentDeal>> map = new ConcurrentHashMap<String, Vector<RentDeal>>();
            for (String string : strings) {

                Future<RentDeal> future = service.submit(new Callable<RentDeal>() {
                    public RentDeal call() throws Exception {
                        return crawlRentedHouse(string);
                    }
                });

            }

            for (Future<RentDeal> future : futures) {
                try {
                    RentDeal rentDeal = future.get();
                    String district = rentDeal.getDistrict();
                    String date2 = rentDeal.getDealDate();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
                    Date dateTemp = null;
                    try {
                        dateTemp = sdf.parse(date2);
                        if (dateTemp.after(lastDate)) {
                            lastDate = dateTemp;
                        }


                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    String date = rentDeal.getDealDate().substring(0, 7).replaceAll("\\.", "");
                    String key = district + "/" + date;
                    crawled.add(rentDeal.getHouseCode());
                    crawledFileWriter.append(rentDeal.getHouseCode() + "\r\n");


                    if (!map.containsKey(key)) {
                        Vector<RentDeal> vector = new Vector<RentDeal>();
                        vector.add(rentDeal);
                        map.put(key, vector);

                    } else {
                        Vector<RentDeal> vector = map.get(key);
                        vector.add(rentDeal);
                        map.put(key, vector);
                    }
                    System.out.println("fetching...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }

            for (Map.Entry<String, Vector<RentDeal>> entry : map.entrySet()) {
                File file2 = new File(savePath, entry.getKey().split("/")[0]);
                if (!file2.exists()) {
                    file2.mkdirs();
                }

                File file1 = new File(savePath, entry.getKey() + ".csv");
                //                System.out.println(file1.getAbsolutePath());
                if (!file1.exists()) {
                    file1.createNewFile();
                    FileWriter fileWriter = new FileWriter(file1.getAbsoluteFile(), true);
                    //                    FileWriter fileWriter = new FileWriter(file1.getAbsoluteFile(),true);
                    fileWriter.append(RentDeal.title() + "\r\n");
                    for (RentDeal rd : entry.getValue()) {
                        fileWriter.append(rd.toString() + "\r\n");
                    }
                    crawledFileWriter.flush();
                    fileWriter.flush();
                    fileWriter.close();
                } else {
                    FileWriter fileWriter = new FileWriter(file1.getAbsoluteFile(), true);
                    for (RentDeal rd : entry.getValue()) {
                        fileWriter.append(rd.toString() + "\r\n");
                    }
                    crawledFileWriter.flush();
                    fileWriter.flush();
                    fileWriter.close();
                }

                System.out.println("writing ->  " + file1.getAbsolutePath());


            }


        }
        crawledFileWriter.close();



        service.shutdown();
        try {
            service.awaitTermination(Long.MAX_VALUE,TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        totalHouseCrawled = crawled.size();

    }

    private void writeSimpleLog() {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String logName = this.cityName + "日志";
        String savePath = properties.getProperty("save_path") + "/Rent/" + cityAbbr + "/" + logName + ".txt";
        try {
            System.out.println("************保存日志中***********");
            FileWriter logWriter = new FileWriter(savePath, true);
            logWriter.append("\r\n");
            logWriter.append("Renew Date： " + format2.format(date) + "\r\n");
            logWriter.append("Renew City: " + cityName + "\r\n");
            logWriter.append("Total crawl: " + totalHouseCrawled + "\r\n");
            logWriter.append("Renew crawl: " + numHouseCrawled + "\r\n");
            logWriter.append("Last date rented: " + format.format(lastDate) + "\r\n");
            logWriter.append("-----------------------------------------------");
            logWriter.flush();
            logWriter.close();


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void readCityConfig(String configFold) {
        try {
            FileReader fileReader = new FileReader(configFold + "/cityConfig.json");
            Gson gson = new Gson();
            List<CityInfo> cityInfos = gson.fromJson(fileReader, new TypeToken<List<CityInfo>>() {
            }.getType());
            for (CityInfo info : cityInfos) {
                String cityNameTemp = info.getCity_name();
                if (cityNameTemp.equals(cityName)) {
                    if (info.getNew_rent() == 0) {
                        try {
                            throw new Exception("该城市无出租数据");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    cityId = info.getCity_id();
                    cityName = info.getCity_name();
                    cityAbbr = info.getAbbr();
                    communityUrl = "https://" + cityAbbr + ".lianjia.com/xiaoqu/";

                    break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }


    private void finishUnfinishedJobs() throws IOException, InterruptedException {
        System.out.println("开始未完成的爬取");
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    crawlAllRentedHouse();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        thread.join();


        System.out.println("已完成未完成的爬取");

    }


    public void update() {

        String savePath = properties.getProperty("save_path") + "/Rent/" + cityAbbr;
        File indexFile = new File(savePath, cityId + "_index.txt");
        System.out.println("开始更新");
//        crawlQuickUpdateList("0");
        Thread thread  = new Thread(new Runnable() {
            public void run() {
                crawlUpdateList("0");
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        wait();
        System.out.println("获取更新列表成功");
        Set<Rented> set = new HashSet<Rented>(this.updateList);


        try {
            FileWriter fileWriter = new FileWriter(indexFile, true);
            for (Rented rented : set) {
                fileWriter.append(rented.getHouse_code() + "\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            System.out.println("爬取更新住房");
            crawlAllRentedHouse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("更新成功");
        writeSimpleLog();

    }

    public void mainProgram() throws IOException {

        String dataPath = properties.getProperty("save_path") +"/Rent/" + cityAbbr;

        File dataFolder = new File(dataPath);

        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        } else {

            try {
                finishUnfinishedJobs();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        System.out.println("开始 " + cityName + " 小区的爬取");
        String communityIndex = new File(dataFolder, cityAbbr + "_community.csv").getAbsolutePath();
//        String communityIndex = properties.getProperty("community_index");
        CommunityCrawl communityCrawl = new CommunityCrawl();
        communityCrawl.crawlCommunity(communityIndex, cityName, communityUrl);
        System.out.println("完成小区的爬取");
        System.out.println("开始爬取所有小区的出租列表");
        this.totalHouseCrawled = 0;
        this.numHouseCrawled = 0;
        crawAllList();
        System.out.println("完成列表的爬取");
        System.out.println("开始");
        crawlAllRentedHouse();
        writeSimpleLog();
        System.out.println("完成对" + this.cityName + "的爬取");


    }

}
