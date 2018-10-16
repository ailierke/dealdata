package util;

import java.io.File;
import java.util.Date;

/**
 * <p>标题：文件处理工具类</p>
 * <p>描述：</p>
 * <p>Copyright：Copyright(c) 2014 diwinet</p>
 * <p>日期：2014-1-21</p>
 * @author	gb
 */

public class FileUtils {

	/**
	 * 
	 * <p>说明：删除一个目录下的临时文件</p>
	 * <p>时间：2014-1-21 下午3:30:59</p>
	 * @param filePath
	 */
	public static void deleteTempFile(String filePath) {
		try {
			File filePathObject = new File(filePath);
			if (filePathObject.exists() && filePathObject.isDirectory()) {
				File[] fileArray = filePathObject.listFiles();
				if(fileArray != null){
					for (int i = 0; i < fileArray.length; i++) {
						if (fileArray[i].isFile()) { // 只删除文件
							fileArray[i].delete();
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 
	 * <p>说明：删除一个目录下的过期文件，文件名在指定的小时前</p>
	 * <p>时间：2014-1-21 下午3:31:14</p>
	 * @param filePath
	 * @param beforeHours 过期多少小时的文件被删除
	 * @param fileType 删除的文件类型 void
	 */
	public static void deleteTempDateFile(String filePath, int beforeHours,
			String fileType) {
		try {
			File filePathObject = new File(filePath);
			if (filePathObject.exists() && filePathObject.isDirectory()) {
				File[] fileArray = filePathObject.listFiles();
				Date compareDate = DateUtils.getNextDate(new Date(), "H", -1
						* beforeHours);
				String compareDateString = DateUtils
						.getFullCompactString(compareDate);
				if(fileArray != null){
					for (int i = 0; i < fileArray.length; i++) {
						if (fileArray[i].isFile()) { // 只删除文件
							String fileName = fileArray[i].getName();
							boolean delete = false;
							if (fileType == null || "".equals(fileType)) {
								if (fileName.compareTo(compareDateString) < 0) {
									delete = true;
								}
							} else {
								if (fileName.endsWith(fileType)) {
									if (fileName.compareTo(compareDateString) < 0) {
										delete = true;
									}
								}
							}
							if (delete) {
								fileArray[i].delete();
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
