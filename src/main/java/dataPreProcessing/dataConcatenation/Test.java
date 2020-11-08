package dataPreProcessing.dataConcatenation;

public class Test {
    public static void main(String[] args) {
        DataMerger dataMerger = new DataMerger("C:\\Users\\liu jun yue\\eclipse-workspace\\lianjia\\src\\main\\resources\\data\\Rent\\bj");
//        dataMerger.findAllCityRent();

        dataMerger.writeRentMergeData("C:\\Users\\liu jun yue\\eclipse-workspace\\lianjia\\src\\main\\resources\\data\\test.csv");


    }
}
