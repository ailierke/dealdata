package diwinet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



/**
 * 读取excel然后更改其中的类�?
 *@description�?
 *@Date:@2015�?4�?20�?
 *@author ailierke
 */
public class DealSensorCommon {
	public static void dealExcel() throws InvalidFormatException, IOException{
		File file = new File("D:\\sensor_info.xlsx");
			Workbook  wb = null;  
			InputStream in= new FileInputStream(file);
			if (!in.markSupported()) {
				in = new PushbackInputStream(in, 8);
			}
			if (POIFSFileSystem.hasPOIFSHeader(in)) {
				wb= new HSSFWorkbook(in);
			}else{
				if (POIXMLDocument.hasOOXMLHeader(in)) {
					wb= new XSSFWorkbook(OPCPackage.open(in));
				}
			}
			
			String SBTM = null;
			String SBTM1 = null;
			for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {  
				Sheet st = wb.getSheetAt(sheetIndex);  
				System.out.println("总行数："+st.getLastRowNum());
				/**
				 * 循环�?有的�?
				 */
				for (int rowIndex = 0; rowIndex <=st.getLastRowNum(); rowIndex++) {
					/**
					 * 与除了本身这行和�?有行进行比较
					 */
					Row row = st.getRow(rowIndex); 
					SBTM = row.getCell(0).getStringCellValue();
					Row row1= null;
					for (int index = 0; index <=st.getLastRowNum(); index++) {
						if(rowIndex ==index){
							continue;
						}
						row1 = st.getRow(index); 
						SBTM1 = row1.getCell(0).getStringCellValue();
						if(SBTM1.equals(SBTM)){
							System.out.println("SBTM出现相同的了:"+SBTM1);
						}
					}
				} 
			}
	}
	public static void main(String[] args) throws InvalidFormatException, IOException {
		dealExcel();
	}
}
