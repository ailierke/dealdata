package excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

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
 * 读取excel然后更改其中的类容
 *@description：
 *@Date:@2015年4月20日
 *@author ailierke
 */
public class DealExcel {
	public static void dealExcel() throws InvalidFormatException, IOException{
		File file = new File("D:\\mouth.xls");
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
			Cell cell = null; 
			for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {  
				Sheet st = wb.getSheetAt(sheetIndex);  
				for (int rowIndex = 0; rowIndex <=st.getLastRowNum(); rowIndex++) {  
					Row row = st.getRow(rowIndex); 
					cell = row.getCell(1);//获取第二行电话号码
					String value = cell.getStringCellValue();
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					try{
						cell.setCellValue(value);
					}catch(Exception e){
						e.printStackTrace();
					}
					
				}  
			}  
			/**
			 * 在更改过后一定还要再次将excel保存下来。因为excel的操作都是在内存中。不然修改不了
			 */
			FileOutputStream stream;
			stream = new FileOutputStream(file);
			wb.write(stream);
			stream.close();
	}
	public static void main(String[] args) throws InvalidFormatException, IOException {
		dealExcel();
	}
}
