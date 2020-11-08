package dataPreProcessing.coordinate;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class CoordinateConversion {
    private String fromPath;
    private String toPath;
    private String fromCor;
    private String toCor;
    private String lonColName;
    private String latColName;

    public CoordinateConversion(String fromPath, String toPath,String fromCor,String toCor, String lonColName, String latColName) {
        this.fromPath = fromPath;
        this.toPath = toPath;
        this.lonColName = lonColName;
        this.latColName = latColName;
        this.toCor = toCor;
        this.fromCor = fromCor;
    }

    public void converse(){
        System.out.println("开始转换");
        List<String> from = null;
        List<String> to = new ArrayList<>();
        try {
            from = FileUtils.readLines(new File(this.fromPath), "utf8");
        } catch (IOException e) {
//            e.printStackTrace();
            return;
        }
        String[] titles = from.get(0).split(",");
        to.add(from.get(0)+",\""+this.toCor+"_lon\""+",\""+this.toCor+"_lat\"");
        from.remove(0);
        int lonCol = -1;
        int latCol = -1;
        for (int i = 0; i < titles.length; i++) {
            if(titles[i].replaceAll("\"","").equals(this.lonColName)){
                lonCol = i;
            }else if(titles[i].replaceAll("\"","").equals(this.latColName)){
                latCol = i;
            }
            
        }
        if(lonCol==-1||latCol==-1){
            System.out.println("没有"+this.lonColName+"或"+this.latColName+"列，请检查");
            return;
        }
        for (String s : from) {
            String[] row = s.split(",");
            double lon = Double.parseDouble(row[lonCol].replaceAll("\"",""));
            double lat = Double.parseDouble(row[latCol].replaceAll("\"",""));
            Gps gps = null;
            if(this.fromCor.equals("bd")){
                if(this.toCor.equals("wgs")){
                    gps = PositionUtil.bd09_To_Gps84(lat,lon);
                }else if(this.toCor.equals("gcj")){
                    gps = PositionUtil.bd09_To_Gcj02(lat,lon);
                }
                else{
                    System.out.println("不支持转换");
                    return;
                }

            }else if(this.fromCor.equals("gcj")){
                if(this.toCor.equals("wgs")){
                    gps = PositionUtil.gcj_To_Gps84(lat,lon);
                }else if(this.toCor.equals("bd")){
                    gps = PositionUtil.gcj02_To_Bd09(lat,lon);
                }else{
                    System.out.println("不支持转换");
                    return;
                }

            }else{
                System.out.println("不支持转换");
                return;
            }


            to.add(s+",\""+gps.getWgLon()+"\",\""+gps.getWgLat()+"\"");
//            gps.getWgLat();



        }
        System.out.println("writing ->" + this.toPath);
        try {

            PrintWriter printWriter = new PrintWriter(new File(this.toPath),"utf8");
            for (String s : to) {
                printWriter.println(s);
            }
            printWriter.flush();
            printWriter.close();
//            FileUtils.writeLines(new File(this.toPath),to,"utf8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("完成转换");

    }





}
