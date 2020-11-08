package dataPreProcessing.dataConcatenation;


import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

public class DataMerger {
    private String folderPath;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
    private int startDateInt = 0;
    private int endDateInt = 0;

    public DataMerger(String folderPath) {
        this.folderPath = folderPath;
        this.startDateInt = Integer.parseInt(this.dateFormat.format(new Date(0)));
        this.endDateInt = Integer.parseInt(this.dateFormat.format(new Date(System.currentTimeMillis())));
    }
    public DataMerger(String folderPath,int startDateInt) {
        this.folderPath = folderPath;
        this.startDateInt = startDateInt;
        this.endDateInt = Integer.parseInt(this.dateFormat.format(new Date(System.currentTimeMillis())));
    }

    public DataMerger(String folderPath,int startDateInt, int endDateInt) {
        this.folderPath = folderPath;
        this.startDateInt = startDateInt;
        this.endDateInt = endDateInt;
    }

    public String[] findAllCityRent(){
        File file = new File(this.folderPath);
        File[] files = file.listFiles(x->x.isDirectory());
        ArrayList<String> paths = new ArrayList<>();
        int realStart = Integer.MAX_VALUE;
        for (File f:files) {
            String[] l = f.list((x,y)->{
                String fileString = y.replace(".csv","");
                int numDate = Integer.parseInt(fileString);
                return ((numDate - this.startDateInt) >=0) && ((numDate - this.endDateInt) <=0);});

            List<String> temp = Arrays.asList(l);



            for (String s : temp) {
                int fileStart = Integer.parseInt(s.replaceAll(".csv",""));
                if(fileStart < realStart){
                    realStart = fileStart;
                }

                paths.add(new File(f,s).getAbsolutePath());
            }
        }

        this.startDateInt = realStart;
        return (String[]) paths.toArray(new String[paths.size()]);

    }

    public void writeRentMergeData(String path) {
        String[] list = this.findAllCityRent();
        String head = "";
        List<String> data = new ArrayList<>();
        for (String s : list) {

            List<String> strings = null;
            try {
                strings = FileUtils.readLines(new File(s), "utf8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            head = strings.get(0);
            strings.remove(0);
            data.addAll(strings);
        }
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(new File(path), Charset.forName("utf8"));
            printWriter.println(head);
        } catch (IOException e) {
            e.printStackTrace();
        }


        for (String datum : data) {
            printWriter.println(datum);
            System.out.println("merging");
        }
        printWriter.flush();
        printWriter.close();

    }

    public String[] findAllCityDeal(){
        File file = new File(this.folderPath);
        ArrayList<String> paths = new ArrayList<>();
        for (String f:file.list()) {
            paths.add(new File(file,f).getAbsolutePath());
        }
        return (String[]) paths.toArray(new String[paths.size()]);
    }

    public void writeDealMergeData(String path){
        String[] list = this.findAllCityDeal();
        String head = "";
        List<String> data = new ArrayList<>();
        for (String s : list) {

            List<String> strings = null;
            try {
                strings = FileUtils.readLines(new File(s), "utf8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            head = strings.get(0);
            strings.remove(0);
            data.addAll(strings);
        }
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(new File(path), Charset.forName("utf8"));
            printWriter.println(head);
        } catch (IOException e) {
            e.printStackTrace();
        }


        for (String datum : data) {
            printWriter.println(datum);
            System.out.println("merging");
        }
        printWriter.flush();
        printWriter.close();
    }

    public int getStartDateInt() {
        return startDateInt;
    }

    public int getEndDateInt() {
        return endDateInt;
    }
}
