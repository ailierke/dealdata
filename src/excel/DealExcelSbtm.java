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
 * 读取500水管家设备，然后将其状态设置
 * <p>标题：</p>
 * <p>描述：</p>
 * <p>Copyright：Copyright(c) 2016 diwinet</p>
 * <p>日期：2016年9月9日</p>
 * @author	jiangxing
 */
public class DealExcelSbtm {
	public static void dealExcel() throws InvalidFormatException, IOException, SQLException{

		Connection conn = DBUtil.getConnection();
		QueryRunner queryRunner = new QueryRunner();
		DBUtilsTemplate dbUtil = new DBUtilsTemplate(conn,queryRunner);

		File file = new File("D:\\数佳100.xlsx");
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
			for (int rowIndex = 1; rowIndex <=st.getLastRowNum(); rowIndex++) {  
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
				StringBuilder backup = new StringBuilder();
				WpCustomerBanding wp = null;
				SensorInfo sensor =null;
					try{
						sensor = dbUtil.findFirst(SensorInfo.class,"select * from T_SENSOR_INFO where sbtm = '"+sbtm+"'");
						if(sensor!=null&&sensor.getTmzt()!=2){
							backup.append("【未激活】");
							System.out.println("【"+sbtm+":未激活");
						}else if(sensor!=null&&sensor.getTmzt()==2){
							backup.append("【已激活】");
							Long sbbh = sensor.getSbbh();
							wp = dbUtil.findFirst(WpCustomerBanding.class,"select *  from  T_WP_CUSTOMER_BANDING where sbbh = "+sbbh);
							if(wp!=null){
								backup.append("【已绑定】");
								System.out.println("【"+sbtm+":已绑定】");
							}else{
								backup.append("【未绑定】");
								System.out.println("【"+sbtm+":未绑定】");
							}
						}else{
							backup.append("【设备不存在,未出库】");
						}
						cell = row.getCell(1);
						cell.setCellValue(backup.toString());
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
	
	public static void main(String[] args) throws InvalidFormatException, IOException, SQLException {
		dealExcel();
	}
}
