package Lianjia.data;

import java.util.Arrays;

public class CityConfig {
    private String request_id;
    private String errno;
    private String error;
    private Data data;

    public class Data {
        private Search_filter search_filter;

        public class Search_filter {
            private Check_filters check_filters;


            public class Check_filters {
                private Option region;
                private Option rent_price;
                private Option rent_room;
                private Option rented_more;

                public class Option {
                    private String name;
                    private int type;
                    private String key;
                    private Option[] options;

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public int getType() {
                        return type;
                    }

                    public void setType(int type) {
                        this.type = type;
                    }

                    public String getKey() {
                        return key;
                    }

                    public void setKey(String key) {
                        this.key = key;
                    }

                    public Option[] getOptions() {
                        return options;
                    }

                    public void setOptions(Option[] options) {
                        this.options = options;
                    }

                    @Override
                    public String toString() {
                        final StringBuilder sb = new StringBuilder("Option{");
                        sb.append("name='").append(name).append('\'');
                        sb.append(", type=").append(type);
                        sb.append(", key='").append(key).append('\'');
                        sb.append(", options=").append(Arrays.toString(options));
                        sb.append('}');
                        return sb.toString();
                    }
                }


                public Option getRegion() {
                    return region;
                }

                public void setRegion(Option region) {
                    this.region = region;
                }

                public Option getRent_price() {
                    return rent_price;
                }

                public void setRent_price(Option rent_price) {
                    this.rent_price = rent_price;
                }

                public Option getRent_room() {
                    return rent_room;
                }

                public void setRent_room(Option rent_room) {
                    this.rent_room = rent_room;
                }

                public Option getRented_more() {
                    return rented_more;
                }

                public void setRented_more(Option rented_more) {
                    this.rented_more = rented_more;
                }

                @Override
                public String toString() {
                    final StringBuilder sb = new StringBuilder("Check_filters{");
                    sb.append("region=").append(region);
                    sb.append(", rent_price=").append(rent_price);
                    sb.append(", rent_room=").append(rent_room);
                    sb.append(", rented_more=").append(rented_more);
                    sb.append('}');
                    return sb.toString();
                }
            }

            public Check_filters getCheck_filters() {
                return check_filters;
            }

            public void setCheck_filters(Check_filters check_filters) {
                this.check_filters = check_filters;
            }

            @Override
            public String toString() {
                final StringBuilder sb = new StringBuilder("Search_filter{");
                sb.append("check_filters=").append(check_filters);
                sb.append('}');
                return sb.toString();
            }
        }

        public Search_filter getSearch_filters() {
            return search_filter;
        }

        public void setSearch_filters(Search_filter search_filters) {
            this.search_filter = search_filter;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Data{");
            sb.append("search_filter=").append(search_filter);
            sb.append('}');
            return sb.toString();
        }
    }
    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getErrno() {
        return errno;
    }

    public void setErrno(String errno) {
        this.errno = errno;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CityConfig{");
        sb.append("request_id='").append(request_id).append('\'');
        sb.append(", errno='").append(errno).append('\'');
        sb.append(", error='").append(error).append('\'');
        sb.append(", data=").append(data);
        sb.append('}');
        return sb.toString();
    }
}
