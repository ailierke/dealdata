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

import util.DBUtil;



/**
 * 读取excel然后更改其中的类�?
 *@description�?
 *@Date:@2015�?4�?20�?
 *@author ailierke
 */
public class DealSensor {
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
			
			Connection conn = DBUtil.getConnection();
			Statement stmt  = null;
			 try {
				stmt = conn.createStatement();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
             //执行SQL，并获取返回结果
			String staticSql = null;
			String SBTM = null;
			String SBMC =null;
			Integer PCXH = null;
			String SBHH =null;
			Integer SBLX = null;
			Integer SBFL = null;
			String dateStr = null;
			for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {  
				Sheet st = wb.getSheetAt(sheetIndex);  
				System.out.println("总行数："+st.getLastRowNum());
				for (int rowIndex = 0; rowIndex <=st.getLastRowNum(); rowIndex++) {
					System.out.println("当前行数�?"+rowIndex);
					Row row = st.getRow(rowIndex); 
					SBTM = row.getCell(0).getStringCellValue();
					SBMC =row.getCell(1).getStringCellValue();
					PCXH = (int)row.getCell(2).getNumericCellValue();
					SBHH =row.getCell(3).getStringCellValue();
					SBLX = (int)row.getCell(4).getNumericCellValue();
					SBFL = (int) row.getCell(5).getNumericCellValue();
					dateStr = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date());
					System.out.print("产品SBTM�?"+SBTM);//获取第二行电话号�?
					System.out.print("设备名称SBMC�?"+SBMC);
					System.out.print("批次序号PCXH�?"+PCXH);
					System.out.print("设备货号SBHH�?"+SBHH);
					System.out.print("设备类型SBLX�?"+SBLX);
					System.out.print("设备分类SBFL�?"+SBFL);
					staticSql = "insert into T_SENSOR_INFO(SBTM,SBMC,PCXH,SBHH,SBLX,SBFL,CPJG,BZTJ,CPZL,SFGD,HDGL,CPSM,ZBNX,SFYX,SCRQ) values('"+SBTM+"','"+SBMC+"',"+PCXH+",'"+SBHH+"',"+SBLX+","+SBFL+",200,'100*100*100',5,1,'3',3,1,1,'"+dateStr+"')";
					try {
						stmt.executeUpdate(staticSql);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} 
			}
			try {
				if(stmt!=null){
					stmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if(conn!=null){
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	public static void main(String[] args) throws InvalidFormatException, IOException {
		dealExcel();
	}
}
