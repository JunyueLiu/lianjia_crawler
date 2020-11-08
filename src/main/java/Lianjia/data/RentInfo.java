package Lianjia.data;

import java.util.Arrays;

public class RentInfo {
    private String request_id;
    private int errno;
    private RentData data;
    private int cost;

    public class RentData{

        private RentPicture[] picture_list;
        private BasicInfo basic_info;
        private BasicList[] basic_list;
        private InfoList[] info_list;
        private Location location;
        private RentDealInfo deal_info;
        private Payway payway;
        public class RentPicture{
            private String[] img_url_list;
            private String group_name;
            private int group_id;
            private String dig_key;

            public String[] getImg_url_list() {
                return img_url_list;
            }

            public void setImg_url_list(String[] img_url_list) {
                this.img_url_list = img_url_list;
            }

            public String getGroup_name() {
                return group_name;
            }

            public void setGroup_name(String group_name) {
                this.group_name = group_name;
            }

            public int getGroup_id() {
                return group_id;
            }

            public void setGroup_id(int group_id) {
                this.group_id = group_id;
            }

            public String getDig_key() {
                return dig_key;
            }

            public void setDig_key(String dig_key) {
                this.dig_key = dig_key;
            }

            @Override
            public String toString() {
                return "RentPicture{" +
                        "img_url_list=" + Arrays.toString(img_url_list) +
                        ", group_name='" + group_name + '\'' +
                        ", group_id=" + group_id +
                        ", dig_key='" + dig_key + '\'' +
                        '}';
            }
        }
        public class BasicInfo{
            private String title;
            private String city_id;
            private String house_code;
            private String community_id;
            private String community_name;
            private String price;
            private String floor_state;
            private int blueprint_hall_num;
            private int blueprint_bedroom_num;
            private double area;
            private String orientation;
            private String m_url;
            private int is_ziroom;
            private String house_status;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getCity_id() {
                return city_id;
            }

            public void setCity_id(String city_id) {
                this.city_id = city_id;
            }

            public String getHouse_code() {
                return house_code;
            }

            public void setHouse_code(String house_code) {
                this.house_code = house_code;
            }

            public String getCommunity_id() {
                return community_id;
            }

            public void setCommunity_id(String community_id) {
                this.community_id = community_id;
            }

            public String getCommunity_name() {
                return community_name;
            }

            public void setCommunity_name(String community_name) {
                this.community_name = community_name;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getFloor_state() {
                return floor_state;
            }

            public void setFloor_state(String floor_state) {
                this.floor_state = floor_state;
            }

            public int getBlueprint_hall_num() {
                return blueprint_hall_num;
            }

            public void setBlueprint_hall_num(int blueprint_hall_num) {
                this.blueprint_hall_num = blueprint_hall_num;
            }

            public int getBlueprint_bedroom_num() {
                return blueprint_bedroom_num;
            }

            public void setBlueprint_bedroom_num(int blueprint_bedroom_num) {
                this.blueprint_bedroom_num = blueprint_bedroom_num;
            }

            public double getArea() {
                return area;
            }

            public void setArea(int area) {
                this.area = area;
            }

            public String getOrientation() {
                return orientation;
            }

            public void setOrientation(String orientation) {
                this.orientation = orientation;
            }

            public String getM_url() {
                return m_url;
            }

            public void setM_url(String m_url) {
                this.m_url = m_url;
            }

            public int getIs_ziroom() {
                return is_ziroom;
            }

            public void setIs_ziroom(int is_ziroom) {
                this.is_ziroom = is_ziroom;
            }

            public String getHouse_status() {
                return house_status;
            }

            public void setHouse_status(String house_status) {
                this.house_status = house_status;
            }

            @Override
            public String toString() {
                return "BasicInfo{" +
                        "title='" + title + '\'' +
                        ", city_id='" + city_id + '\'' +
                        ", house_code='" + house_code + '\'' +
                        ", community_id='" + community_id + '\'' +
                        ", community_name='" + community_name + '\'' +
                        ", price='" + price + '\'' +
                        ", floor_state='" + floor_state + '\'' +
                        ", blueprint_hall_num=" + blueprint_hall_num +
                        ", blueprint_bedroom_num=" + blueprint_bedroom_num +
                        ", area=" + area +
                        ", orientation='" + orientation + '\'' +
                        ", m_url='" + m_url + '\'' +
                        ", is_ziroom=" + is_ziroom +
                        ", house_status='" + house_status + '\'' +
                        '}';
            }
        }
        public class BasicList{
            private String name;
            private String value;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            @Override
            public String toString() {
                return "BasicList{" +
                        "name='" + name + '\'' +
                        ", value='" + value + '\'' +
                        '}';
            }
        }
        public class InfoList{
            private String name;
            private String value;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            @Override
            public String toString() {
                return "InfoList{" +
                        "name='" + name + '\'' +
                        ", value='" + value + '\'' +
                        '}';
            }
        }
        public class Location{
            private String title;
            private double baidu_la;
            private double baidu_lo;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public double getBaidu_la() {
                return baidu_la;
            }

            public void setBaidu_la(double baidu_la) {
                this.baidu_la = baidu_la;
            }

            public double getBaidu_lo() {
                return baidu_lo;
            }

            public void setBaidu_lo(double baidu_lo) {
                this.baidu_lo = baidu_lo;
            }

            @Override
            public String toString() {
                return "Location{" +
                        "title='" + title + '\'' +
                        ", baidu_la=" + baidu_la +
                        ", baidu_lo=" + baidu_lo +
                        '}';
            }
        }
        public class RentDealInfo{
            private String name;
            private Review review;

            public class Review{
                private String name;
                private Tuple[] list;
                public class Tuple{
                    private String name;
                    private String value;

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getValue() {
                        return value;
                    }

                    public void setValue(String value) {
                        this.value = value;
                    }

                    @Override
                    public String toString() {
                        return "Tuple{" +
                                "name='" + name + '\'' +
                                ", value='" + value + '\'' +
                                '}';
                    }
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public Tuple[] getList() {
                    return list;
                }

                public void setList(Tuple[] list) {
                    this.list = list;
                }

                @Override
                public String toString() {
                    return "Review{" +
                            "name='" + name + '\'' +
                            ", list=" + Arrays.toString(list) +
                            '}';
                }
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Review getReview() {
                return review;
            }

            public void setReview(Review review) {
                this.review = review;
            }

            @Override
            public String toString() {
                return "RentDealInfo{" +
                        "name='" + name + '\'' +
                        ", review=" + review +
                        '}';
            }
        }
        public class Payway{
            private String name;
            private Triple[] list;
            public class Triple{
                private String title;
                private String price;
                private String desc;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public String getDesc() {
                    return desc;
                }

                public void setDesc(String desc) {
                    this.desc = desc;
                }

                @Override
                public String toString() {
                    return "{" +
                            "title='" + title + '\'' +
                            ", price='" + price + '\'' +
                            ", desc='" + desc + '\'' +
                            '}';
                }
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Triple[] getList() {
                return list;
            }

            public void setList(Triple[] list) {
                this.list = list;
            }

            @Override
            public String toString() {
                return Arrays.toString(list);

            }
        }

        public RentDealInfo getDeal_info() {
            return deal_info;
        }

        public void setDeal_info(RentDealInfo deal_info) {
            this.deal_info = deal_info;
        }

        public Payway getPayway() {
            return payway;
        }

        public void setPayway(Payway payway) {
            this.payway = payway;
        }

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public RentPicture[] getPicture_list() {
            return picture_list;
        }

        public void setPicture_list(RentPicture[] picture_list) {
            this.picture_list = picture_list;
        }

        public BasicInfo getBasic_info() {
            return basic_info;
        }

        public void setBasic_info(BasicInfo basic_info) {
            this.basic_info = basic_info;
        }

        public BasicList[] getBasic_list() {
            return basic_list;
        }

        public void setBasic_list(BasicList[] basic_list) {
            this.basic_list = basic_list;
        }

        public InfoList[] getInfo_list() {
            return info_list;
        }

        public void setInfo_list(InfoList[] info_list) {
            this.info_list = info_list;
        }

        @Override
        public String toString() {
            return "RentData{" +
                    "picture_list=" + Arrays.toString(picture_list) +
                    ", basic_info=" + basic_info +
                    ", basic_list=" + Arrays.toString(basic_list) +
                    ", info_list=" + Arrays.toString(info_list) +
                    ", location=" + location +
                    ", deal_info=" + deal_info +
                    ", payway=" + payway +
                    '}';
        }
    }


    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public RentData getData() {
        return data;
    }

    public void setData(RentData data) {
        this.data = data;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "RentInfo{" +
                "request_id='" + request_id + '\'' +
                ", errno=" + errno +
                ", data=" + data +
                ", cost=" + cost +
                '}';
    }
}
