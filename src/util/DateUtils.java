package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * <p>标题：日期转换处理工具类</p>
 * <p>描述：</p>
 * <p>Copyright：Copyright(c) 2014 diwinet</p>
 * <p>日期：2014-1-10</p>
 * @author	gb
 */
public class DateUtils implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * <p>说明：获取格式化类型</p>
	 * <p>时间：2014-2-19 下午4:55:59</p>
	 * @param type 指定格式化类型标记
	 * @return 格式化后的日期对象
	 */
	private static String defaultDateFormatter(int type){
		SimpleDateFormat defaultDateFormatter = new SimpleDateFormat("yyyy");
		if(type == 1){
			defaultDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		}else if(type == 2){
			defaultDateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}else if(type == 3){
			defaultDateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
		}else if(type == 4){
			defaultDateFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		}
		return  defaultDateFormatter.format(new java.util.Date());
	}
	
	/**
	 * 
	 * <p>说明：取得系统当前的年份,格式如: (2008)</p>
	 * <p>时间：2014-1-21 下午3:16:02</p>
	 * @return
	 */
	public static String getSystemYearString() {
		return defaultDateFormatter(0);
	}

	/**
	 * 
	 * <p>说明：取得系统当前的日期字符表达式,格式如: (2008-05-12)</p>
	 * <p>时间：2014-1-21 下午3:16:13</p>
	 * @return
	 */
	public static String getSystemDateString() {
		return defaultDateFormatter(1);
	}

	/**
	 * 
	 * <p>说明：取得系统当前的日期时间字符表达式,格式如: (2008-05-12 14:28:36)</p>
	 * <p>时间：2014-1-21 下午3:16:22</p>
	 * @return
	 */
	public static String getSystemDateTimeString() {
		return defaultDateFormatter(2);
	}

	/**
	 * 
	 * <p>说明：取得系统当前的日期时间字符表达式,格式如: (20080512142836)</p>
	 * <p>时间：2014-1-21 下午3:16:30</p>
	 * @return
	 */
	public static String getSystemDateTimeCompactString() {
		return defaultDateFormatter(3);
	}

	/**
	 * 
	 * <p>说明：取得系统当前的日期时间字符表达式(带毫秒数),格式如: (20080512142836326)</p>
	 * <p>时间：2014-1-21 下午3:16:38</p>
	 * @return
	 */
	public static String getSystemDateTimeMillionCompactString() {
		return defaultDateFormatter(4);
	}

	/**
	 * 
	 * <p>说明：日期转换字符串,格式如: (2008-05-12)</p>
	 * <p>时间：2014-1-21 下午3:16:47</p>
	 * @param convertDate 需要转换的日期
	 * @return
	 */
	public static String getShortString(Date convertDate) {
		String returnValue = "";
		if (convertDate != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			returnValue = formatter.format(convertDate);
		}
		return returnValue;
	}

	/**
	 * 
	 * <p>说明：日期转换字符串,格式如: (2008-05-12 14:28:36)</p>
	 * <p>时间：2014-1-21 下午3:17:35</p>
	 * @param convertDate 需要转换的日期
	 * @return
	 */
	public static String getFullString(Date convertDate) {
		String returnValue = "";
		if (convertDate != null) {
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			returnValue = formatter.format(convertDate);
		}
		return returnValue;
	}

	/**
	 * 
	 * <p>说明：日期转换字符串,格式如: (20080512)</p>
	 * <p>时间：2014-1-21 下午3:17:59</p>
	 * @param convertDate 需要转换的日期
	 * @return
	 */
	public static String getShortCompactString(Date convertDate) {
		String returnValue = "";
		if (convertDate != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			returnValue = formatter.format(convertDate);
		}
		return returnValue;
	}

	/**
	 * 
	 * <p>说明：日期转换字符串,格式如: (20080512142836)</p>
	 * <p>时间：2014-1-21 下午3:18:25</p>
	 * @param convertDate 需要转换的日期
	 * @return
	 */
	public static String getFullCompactString(Date convertDate) {
		String returnValue = "";
		if (convertDate != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			returnValue = formatter.format(convertDate);
		}
		return returnValue;
	}

	/**
	 * 
	 * <p>说明：日期转换字符串,格式如: (2008年5月12日)</p>
	 * <p>时间：2014-1-21 下午3:18:54</p>
	 * @param convertDate 需要转换的日期
	 * @return
	 */
	public static String getChineseShortString(Date convertDate) {
		String returnValue = "";
		if (convertDate != null) {
			DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG);
			returnValue = dateFormat.format(convertDate);
		}
		return returnValue;
	}

	/**
	 * 
	 * <p>说明：日期转换字符串,格式如: (2008年05月12日14时)</p>
	 * <p>时间：2014-1-21 下午3:18:54</p>
	 * @param convertDate 需要转换的日期
	 * @return
	 */
	public static String getChineseMidString(Date convertDate) {
		String returnValue = "";
		if (convertDate != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			String temp = formatter.format(convertDate);
			returnValue = temp.substring(0, 4) + "年" + temp.substring(4, 6)
					+ "月" + temp.substring(6, 8) + "日" + temp.substring(8, 10)
					+ "时";
		}
		return returnValue;
	}

	/**
	 * 
	 * <p>说明：日期转换字符串,格式如: (2008年05月12日14时28分)</p>
	 * <p>时间：2014-1-21 下午3:21:10</p>
	 * @param convertDate 需要转换的日期
	 * @return
	 */
	public static String getChineseMiddleString(Date convertDate) {
		String returnValue = "";
		if (convertDate != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			String temp = formatter.format(convertDate);
			returnValue = temp.substring(0, 4) + "年" + temp.substring(4, 6)
					+ "月" + temp.substring(6, 8) + "日" + temp.substring(8, 10)
					+ "时" + temp.substring(10, 12) + "分";
		}
		return returnValue;
	}

	/**
	 * 
	 * <p>说明：日期转换字符串,包括星期,格式如: (2008年5月12日 星期一)</p>
	 * <p>时间：2014-1-21 下午3:21:45</p>
	 * @param convertDate 需要转换的日期
	 * @return
	 */
	public static String getChineseFullString(Date convertDate) {
		String returnValue = "";
		if (convertDate != null) {
			DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);
			returnValue = dateFormat.format(convertDate);
		}
		return returnValue;
	}

	/**
	 * <p>说明：字符串日期转换纯中文字符表达式,格式如: (2008-05-12 转换后得到 二00八年五月十二日)<</p>
	 * <p>时间：2014-1-21 下午3:22:27</p>
	 * @param dateString 需要转换的日期字符串
	 * @return
	 * @throws Exception
	 */
	public static String getPureChineseString(String dateString)
			throws Exception {
		String returnValue = "";
		if (dateString != null && !"".equals(dateString)) {
			String[] chinaNumber = new String[10];
			chinaNumber[0] = "0";
			chinaNumber[1] = "一";
			chinaNumber[2] = "二";
			chinaNumber[3] = "三";
			chinaNumber[4] = "四";
			chinaNumber[5] = "五";
			chinaNumber[6] = "六";
			chinaNumber[7] = "七";
			chinaNumber[8] = "八";
			chinaNumber[9] = "九";
			String tempString = "";
			try {
				tempString = dateString.substring(0, 4);
				for (int i = 0; i < tempString.length(); i++) {
					int cn = Integer.parseInt(tempString.substring(i, i + 1));
					returnValue += chinaNumber[cn];
				}
				returnValue += "年";
				tempString = dateString.substring(5, 7);
				for (int i = 0; i < tempString.length(); i++) {
					int cn = Integer.parseInt(tempString.substring(i, i + 1));
					if (i == 0) {
						if (cn == 1) {
							returnValue += "十";
						}
					} else {
						if (cn > 0)
							returnValue += chinaNumber[cn];
					}
				}
				returnValue += "月";
				tempString = dateString.substring(8);
				for (int i = 0; i < tempString.length(); i++) {
					int cn = Integer.parseInt(tempString.substring(i, i + 1));
					if (i == 0) {
						switch (cn) {
						case 0:
							break;
						case 1:
							returnValue += "十";
							break;
						default:
							returnValue += chinaNumber[cn] + "十";
							break;
						}
					} else {
						if (cn > 0)
							returnValue += chinaNumber[cn];
					}

				}
				returnValue += "日";
			} catch (Exception ex) {
				returnValue = "";
				ex.printStackTrace();
			}
		}
		return returnValue;
	}

	/**
	 * 
	 * <p>说明：字符串日期转换纯中文字符表达式,格式如: (2008-05-12 转换后得到 二○○八年五月十二日)</p>
	 * <p>时间：2014-1-21 下午3:24:10</p>
	 * @param dateString 需要转换的日期字符串
	 * @return
	 * @throws Exception
	 */
	public static String getPureChineseString2(String dateString)
			throws Exception {
		String returnValue = "";
		if (dateString != null && !"".equals(dateString)) {
			String[] chinaNumber = new String[10];
			chinaNumber[0] = "○";
			chinaNumber[1] = "一";
			chinaNumber[2] = "二";
			chinaNumber[3] = "三";
			chinaNumber[4] = "四";
			chinaNumber[5] = "五";
			chinaNumber[6] = "六";
			chinaNumber[7] = "七";
			chinaNumber[8] = "八";
			chinaNumber[9] = "九";
			String tempString = "";
			try {
				tempString = dateString.substring(0, 4);
				for (int i = 0; i < tempString.length(); i++) {
					int cn = Integer.parseInt(tempString.substring(i, i + 1));
					returnValue += chinaNumber[cn];
				}
				returnValue += "年";
				tempString = dateString.substring(5, 7);
				for (int i = 0; i < tempString.length(); i++) {
					int cn = Integer.parseInt(tempString.substring(i, i + 1));
					if (i == 0) {
						if (cn == 1) {
							returnValue += "十";
						}
					} else {
						if (cn > 0)
							returnValue += chinaNumber[cn];
					}
				}
				returnValue += "月";
				tempString = dateString.substring(8);
				for (int i = 0; i < tempString.length(); i++) {
					int cn = Integer.parseInt(tempString.substring(i, i + 1));
					if (i == 0) {
						switch (cn) {
						case 0:
							break;
						case 1:
							returnValue += "十";
							break;
						default:
							returnValue += chinaNumber[cn] + "十";
							break;
						}
					} else {
						if (cn > 0)
							returnValue += chinaNumber[cn];
					}

				}
				returnValue += "日";
			} catch (Exception ex) {
				returnValue = "";
				ex.printStackTrace();
			}
		}
		return returnValue;
	}

	/**
	 * 
	 * <p>说明：字符串转换成日期</p>
	 * <p>时间：2014-1-21 下午3:25:00</p>
	 * @param dateString 需要转换的日期字符串
	 * @param formatString 转换格式 yyyy-MM-dd HH:mm:ss 或 yyyy-MM-dd
	 * @return java.util.Date
	 * @throws Exception
	 */
	public static java.util.Date stringToDate(String dateString,
			String formatString) throws Exception {
		java.util.Date returnValue = null;
		try {
			if (dateString != null && !"".equals(dateString)) {
				SimpleDateFormat formatter = new SimpleDateFormat(formatString);
				formatter.setLenient(false); // 设置时间分析为严格的
				returnValue = formatter.parse(dateString);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return returnValue;
	}

	/**
	 * 
	 * <p>说明：取得指定的下一个日期(按月运算时，如果运算后当月没有该日，则为当月的最后一天)</p>
	 * <p>时间：2014-1-21 下午3:26:04</p>
	 * @param startDate 开始日期
	 * @param nextType 运算类型（Y=年数；M=月数；D=天数；H=小时数；m=分钟数；S=秒数）
	 * @param nextStep 运算长度（为负数指取以前的日期）
	 * @return java.util.Date
	 */
	public static java.util.Date getNextDate(Date startDate, String nextType,
			int nextStep) {
		Date endDate = startDate;
		if (startDate != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(startDate);
			if ("Y".equals(nextType)) {
				calendar.add(Calendar.YEAR, nextStep);
			} else if ("M".equals(nextType)) {
				calendar.add(Calendar.MONTH, nextStep);
			} else if ("D".equals(nextType)) {
				calendar.add(Calendar.DATE, nextStep);
			} else if ("H".equals(nextType)) {
				calendar.add(Calendar.HOUR, nextStep);
			} else if ("m".equals(nextType)) {
				calendar.add(Calendar.MINUTE, nextStep);
			} else if ("S".equals(nextType)) {
				calendar.add(Calendar.SECOND, nextStep);
			}
			endDate = calendar.getTime();
		}
		return endDate;
	}

	/**
	 * 
	 * <p>说明：比较两个时间大小</p>
	 * <p>时间：2014-1-21 下午3:27:29</p>
	 * @param date1
	 * @param date2
	 * @return 返回大的那个时间
	 * @throws ParseException
	 */
	public static String compareDate(String date1, String date2) throws ParseException{
		if(date1.equals("") || date2.equals("")){
			return "";
		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date dt1 = df.parse(date1);
        Date dt2 = df.parse(date2);
        if(dt1.getTime() == dt2.getTime()){
        	return "=";
        }else if(dt1.getTime() > dt2.getTime()){
        	return ">";
        }else{
        	return "<";
        }
	}
	/**
	 * <p>说明：将日期字符串转换为Date对象</p>
	 * <p>时间：2014-3-28 下午2:15:16</p>
	 * @param dt 日期字符串
	 * @return 日期对象
	 */
	public static Date getDateFormat(String dt){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
		Date date = new Date();
		try {
			date = df.parse(dt);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static void main(String[] args) {
		try {
			System.out.println(DateUtils.compareDate("2013-03-08 17:33:54", "2013-03-07 17:33:54"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
//		Date startDate = new Date();
//		Date endDate = getNextDate(startDate, "D", 16);
//		System.out.println("startDate = " + getFullString(startDate));
//		System.out.println("endDate = " + getFullString(endDate));
//		endDate = getNextDate(endDate, "M", -1);
//		System.out.println("endDate = " + getFullString(endDate));

		// // 测试日期运算
		// Calendar c = Calendar.getInstance();
		// c.add(Calendar.DATE, -7);
		// System.out.println(DateConvert.getShortString(c.getTime()));
		// String dateString = DateConvert.getShortString(new Date(System
		// .currentTimeMillis() - 7 * 24 * 3600000));
		// System.out.println(dateString);

	}

	public static int betweenDays(Date date2,Date date1) {
	        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
	        return days;
		
	}

}
