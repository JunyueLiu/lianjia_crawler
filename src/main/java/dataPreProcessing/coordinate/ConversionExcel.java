package dataPreProcessing.coordinate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class ConversionExcel {
	public static void fromBD_Converse(String excelfile,int sheet_num,int lon_column,int lat_column,String save_path) {
		try {
			HSSFWorkbook xls = new HSSFWorkbook(new FileInputStream(new File(excelfile)));
			Sheet sheet = xls.getSheetAt(sheet_num);
			Row row = sheet.getRow(0);
			int leng = row.getPhysicalNumberOfCells();
			
			Cell cell = row.createCell(leng);
			cell.setCellValue("WGS_lon");
			cell = row.createCell(leng+1);
			cell.setCellValue("WGS_lat");
			
			
			
			
			
			for(int j = 1;j<sheet.getPhysicalNumberOfRows();j++) {
				row = sheet.getRow(j);
				
				double lon = Double.parseDouble(row.getCell(lon_column).toString());
				double lat = Double.parseDouble(row.getCell(lat_column).toString());
				Gps wps = PositionUtil.bd09_To_Gps84(lat, lon);
				System.out.println(wps);
				Cell cell1 = row.createCell(leng);
				cell1.setCellValue(wps.getWgLon());
				cell1 = row.createCell(leng+1);
				cell1.setCellValue(wps.getWgLat());
				
				
			}
			
			xls.write(new FileOutputStream(save_path));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}	
	public static void fromGCJ_Converse(String excelfile,int sheet_num,int lon_column,int lat_column,String save_path) {
		try {
			HSSFWorkbook xls = new HSSFWorkbook(new FileInputStream(new File(excelfile)));
			Sheet sheet = xls.getSheetAt(sheet_num);
			Row row = sheet.getRow(0);
			int leng = row.getPhysicalNumberOfCells();
			
			Cell cell = row.createCell(leng);
			cell.setCellValue("WGS_lon");
			cell = row.createCell(leng+1);
			cell.setCellValue("WGS_lat");
			
			
			
			
			
			for(int j = 1;j<sheet.getPhysicalNumberOfRows();j++) {
				row = sheet.getRow(j);
				
				double lon = Double.parseDouble(row.getCell(lon_column).toString());
				double lat = Double.parseDouble(row.getCell(lat_column).toString());
				Gps wps = PositionUtil.gcj_To_Gps84(lat, lon);
				System.out.println(wps);
				Cell cell1 = row.createCell(leng);
				cell1.setCellValue(wps.getWgLon());
				cell1 = row.createCell(leng+1);
				cell1.setCellValue(wps.getWgLat());
				
				
			}
			
			xls.write(new FileOutputStream(save_path));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
