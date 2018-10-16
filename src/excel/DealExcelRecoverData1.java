package excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
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

import diwinet.wp.vo.SensorBinding;
import util.DBUtil;
import util.DBUtilsTemplate;


/**
 * 读取excel中数据，将数据库中没有的删除
 * <p>标题：</p>
 * <p>描述：</p>
 * <p>Copyright：Copyright(c) 2016 diwinet</p>
 * <p>日期：2016年9月9日</p>
 * @author	jiangxing
 */
public class DealExcelRecoverData1 {
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
		HashMap<String,Long> sbbhSet = new HashMap<String,Long>();
		for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {  
			Sheet st = wb.getSheetAt(sheetIndex);  
			Long sbbh = null;
			for (int rowIndex = 1; rowIndex <=st.getLastRowNum(); rowIndex++) {
				sbbh = null;
				Row row = st.getRow(rowIndex); 
				cell = row.getCell(1);
				if(cell==null){
					continue;
				}
				sbbh = (long) cell.getNumericCellValue();
				cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				//如果粗航设备条码为null或者为空
				sbbhSet.put(""+sbbh, 1l);
			}
		}
		String sql = "select * from T_SENSOR_BINDING ";
		List<SensorBinding> list = dbUtil.queryForList(sql, SensorBinding.class);
		if(list!=null&&!list.isEmpty()){
			for(SensorBinding sensorBanding : list){
				String sbbhStr = sensorBanding.getSbbh().toString();
				if(!sbbhSet.containsKey(sbbhStr)){
					dbUtil.delete("delete from T_SENSOR_BINDING where bdbh =? ", sensorBanding.getBdbh());
					hasBanding.append(sensorBanding.getSbbh()+";");
				}
			}
		}
		System.out.println("被删除的："+hasBanding.toString());
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
