package diwinet;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import util.DBUtil;
/**
 * <p>标题：租赁式充值价格修改，1年的金额除去12，半价金额废弃不用</p>
 * <p>描述：</p>
 * <p>Copyright：Copyright(c) 2016 diwinet</p>
 * <p>日期：2016年10月19日</p>
 * @author	jiangxing
 */
public class T_WP_COMPANY_LEASE_PRICE {
	public static void main(String[] args) throws SQLException, IOException {
		
		
		
		Connection conn = DBUtil.getConnection();
		conn.setAutoCommit(true);
		Statement statement = conn.createStatement();
		Statement statement1 = conn.createStatement();
		Statement statement2 = conn.createStatement();
		//查出所有是年卡的
		ResultSet  rs1 = statement.executeQuery("select JFBH as jfbh ,DJJE as djje,DJJE1 as djje1 FROM T_WP_COMPANY_LEASE_PRICE p WHERE JFFS = 1");
		Long jfbh = null;
		Double djje = null;
		Double djje1 = null;//修改后金额

		while(rs1.next()){
			jfbh = rs1.getLong(1);
			djje = rs1.getDouble(2);
			djje1=	rs1.getDouble(3);	
			MathContext mc = new MathContext(10, RoundingMode.HALF_UP);
			BigDecimal djje_1 = new BigDecimal(djje).divide(new BigDecimal(12),mc).setScale(2, RoundingMode.CEILING);
			BigDecimal djje1_1 = new BigDecimal(djje1).divide(new BigDecimal(12),mc).setScale(2, RoundingMode.CEILING);
			if(djje_1.doubleValue()==0){
				djje_1 = new BigDecimal(0.01);
			}
			if(djje1_1.doubleValue()==0){
				djje1_1 = new BigDecimal(0.01);
			}
			statement2.executeUpdate("update  T_WP_COMPANY_LEASE_PRICE  set DJJE ="+djje_1+",DJJE1 ="+djje1_1+" where JFBH = "+jfbh);
			System.out.println("年卡计费编号为：jfbh="+jfbh);
		}
		rs1.close();
		statement.close();
		statement1.close();
		statement2.close();
		conn.close();
}
}
