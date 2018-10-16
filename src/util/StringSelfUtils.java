package util;

import java.util.Date;


/**
 * 
 * <p>标题：</p>
 * <p>描述：</p>
 * <p>Copyright：Copyright(c) 2014 diwinet</p>
 * <p>日期：2014-1-10</p>
 * @author	gb
 */
public class StringSelfUtils {

	/**
	 * 
	 * <p>说明：null 转换为空字符串</p>
	 * <p>时间：2014-1-10 上午11:10:49</p>
	 * @param temp 需要转换的字符串
	 * @return String
	 */
	public static String nullToStr(String temp) {
		if (temp == null)
			temp = "";
		return temp;
	}
	
	/**
	 * 
	 * <p>说明：日期转化</p>
	 * <p>时间：2014-6-19 下午6:52:29</p>
	 * @param date
	 * @return
	 */
	public static String dateToOracleDateStr(Date date) {
		String fullStr = DateUtils.getFullString(date);
		return " TO_DATE('" + fullStr + "','yyyy-mm-dd,hh24:mi:ss') ";
	}
	
}
