package Lianjia.data;

import java.io.Serializable;
import java.util.Arrays;

public class RentList implements Serializable {
    private String request_id;
    private int errno;
    private String error;
    private Data data;

    public class Data {
        private int total_count;
        private int return_count;
        private int has_more_data;
        private Rented list[];



        public int getTotal_count() {
            return total_count;
        }

        public int getReturn_count() {
            return return_count;
        }

        public int getHas_more_data() {
            return has_more_data;
        }

        public Rented[] getList() {
            return list;
        }


        public void setTotal_count(int total_count) {
            this.total_count = total_count;
        }

        public void setReturn_count(int return_count) {
            this.return_count = return_count;
        }

        public void setHas_more_data(int has_more_data) {
            this.has_more_data = has_more_data;
        }

        public void setList(Rented[] list) {
            this.list = list;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "total_count=" + total_count +
                    ", return_count=" + return_count +
                    ", has_more_data=" + has_more_data +
                    ", list=" + Arrays.toString(list) +
                    '}';
        }
    }

    public String getRequest_id() {
        return request_id;
    }

    public int getErrno() {
        return errno;
    }

    public String getError() {
        return error;
    }

    public Data getData() {
        return data;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "List{" +
                "request_id='" + request_id + '\'' +
                ", errno=" + errno +
                ", error='" + error + '\'' +
                ", data=" + data +
                '}';
    }
}
