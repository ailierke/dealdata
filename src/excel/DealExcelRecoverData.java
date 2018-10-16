package excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
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

import diwinet.wp.vo.SensorInfo;
import diwinet.wp.vo.TWaterReportHistory;
import diwinet.wp.vo.TWaterReportHistoryRT;
import diwinet.wp.vo.WpCustomerBanding;
import util.DBUtil;
import util.DBUtilsTemplate;


/**
 * 读取excel中数据，将未被绑定的设备条码提取出来
 * <p>标题：</p>
 * <p>描述：</p>
 * <p>Copyright：Copyright(c) 2016 diwinet</p>
 * <p>日期：2016年9月9日</p>
 * @author	jiangxing
 */
public class DealExcelRecoverData {
	public static void dealExcel() throws InvalidFormatException, IOException, SQLException{

		Connection conn = DBUtil.getConnection();
		QueryRunner queryRunner = new QueryRunner();
		DBUtilsTemplate dbUtil = new DBUtilsTemplate(conn,queryRunner);

		File file = new File("D:\\回复数据.xls");
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
		StringBuilder hasBanding = new StringBuilder();
		StringBuilder noBanding = new StringBuilder();
		for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {  
			Sheet st = wb.getSheetAt(sheetIndex);  
			Long sbbh = null;
			for (int rowIndex = 1; rowIndex <=st.getLastRowNum(); rowIndex++) {
				sbbh = null;
				Row row = st.getRow(rowIndex); 
				cell = row.getCell(0);
				if(cell==null){
					continue;
				}
				String sbtm = cell.getStringCellValue();
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				//如果粗航设备条码为null或者为空
				if(sbtm==null||sbtm.equals("")){
					continue;
				}
				WpCustomerBanding wp = null;
				SensorInfo sensor =null;
					try{
						String sql = "select * from T_SENSOR_INFO where sbtm = '"+sbtm+"'";
						sensor = dbUtil.findFirst(SensorInfo.class,sql);
						if(sensor!=null){
							sbbh = sensor.getSbbh();
							sql  = "select * from  T_WP_CUSTOMER_BANDING where sbbh = "+sbbh;
							wp = dbUtil.findFirst(WpCustomerBanding.class,sql);
							if(wp!=null){
								st.removeRow(row);
								hasBanding.append(sbtm+";");
							}else{
								noBanding.append(sbtm+";");
								cell = row.getCell(1);
								cell.setCellValue(sbbh);
							}
						}
					}catch(Exception e){
						e.printStackTrace();
					}
			}
		}
		System.out.println("已被绑定:"+hasBanding.toString());
		System.out.println("未被绑定:"+noBanding.toString());
		/**
		 * 在更改过后一定还要再次将excel保存下来。因为excel的操作都是在内存中。不然修改不了
		 */
					FileOutputStream stream;
					stream = new FileOutputStream(file);
					wb.write(stream);
					stream.close();
	}
	
	public static void main(String[] args) throws InvalidFormatException, IOException, SQLException {
		dealExcel();
	}
}
