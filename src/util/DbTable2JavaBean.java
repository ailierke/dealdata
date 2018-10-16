package util;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;


public class DbTable2JavaBean {

	/* 要生成jopo对象的表名或对象名,使用;进行分割 */

	private String tableName = "T_LEASE_PAY";

	/* 要生成jopo对象的sql,使用;进行分割（与tableName一一对应） */

	private String tableSql = "select * from T_LEASE_PAY";

	/* 包名公共部分 */
	private String packPublicUrl = "diwinet.wp.vo";
	/* 包名私有部分 */
	private String packPrivateUrl = "";
	private String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://101.201.104.132:3306/dwater_all?useUnicode=true&characterEncoding=utf8";
	String user = "DW_2016"; 
	private String userName = "DW_2016";
	private String password = "dwater-173";
//	String url = "jdbc:mysql://192.168.0.42:3306/dwater_all?useUnicode=true&characterEncoding=utf8";
//	String userName = "dwater"; 
//	String password = "dwater_055";
	
	
	private String tableMatchPattern = "";
	private String matchPattern = "false";

	public static final int OBJECTTYPE = 0;
	public static final int COMMONLYTYPE = 1;

	public DbTable2JavaBean() {
	}

	public DbTable2JavaBean(boolean init) {
		if (init) {

		}
	}

	public void init(int ObjectTypeOrCommonlyType) {
		try {
			Class.forName(this.driver).newInstance();
			Connection conn = DriverManager.getConnection(this.url,
					this.userName, this.password);
			String[] tables = new String[0];
			String[] sqls = new String[0];
			ArrayList<String> tableal = new ArrayList<String>(20);
			ArrayList<String> sqlal = new ArrayList<String>(20);
			if ("true".equals(this.matchPattern)) {
				DatabaseMetaData dbmd = conn.getMetaData();
				ResultSet dbmdrs = dbmd.getTables(null,
						this.userName.toUpperCase(), this.tableMatchPattern,
						new String[] { "TABLE" });
				while (dbmdrs.next()) {
					tableal.add(dbmdrs.getString(3));
				}
				dbmdrs.close();
				if (tableal.size() == 0) {
					dbmdrs = dbmd.getTables(null, this.userName.toLowerCase(),
							this.tableMatchPattern, new String[] { "TABLE" });
					while (dbmdrs.next()) {
						tableal.add(dbmdrs.getString(3));
					}
					dbmdrs.close();
				}
				if (tableal.size() == 0) {
					dbmdrs = dbmd.getTables(null, this.userName,
							this.tableMatchPattern, new String[] { "TABLE" });
					while (dbmdrs.next()) {
						tableal.add(dbmdrs.getString(3));
					}
					dbmdrs.close();
				}
				tables = new String[tableal.size()];
				sqls = new String[sqlal.size()];
				for (int ti = 0; ti < tableal.size(); ti++) {
					tables[ti] = tableal.get(ti);
					sqls[ti] = sqlal.get(ti);
				}
			} else {
				tables = this.tableName.split(";");
				sqls = this.tableSql.split(";");
			}
			String strType;
			String strName;
			String className;
			String[] nameSect;
			StringBuilder tbn = new StringBuilder();
			StringBuilder tstr1 = new StringBuilder();
			StringBuilder tstr2 = new StringBuilder();
			File file = new File("JavaBean");
			if (!file.exists())
				file.mkdir();
			if (!file.isDirectory())
				file.mkdir();
			for (int i = 0; i < tables.length; i++) {
				nameSect = tables[i].split("_");
				for (String ns : nameSect) {
					tbn.append(ns.substring(0, 1).toUpperCase()
							+ ns.substring(1).toLowerCase());
				}

				className = tbn.toString();
				tbn.delete(0, tbn.length());
				
				if(StringUtils.isEmpty(this.packPrivateUrl)){					
					tstr1.append("package " + this.packPublicUrl + "; ");
				}else{
					tstr1.append("package " + this.packPublicUrl + "."
							+ this.packPrivateUrl + "; ");
				}
				
				tstr1.append("\n");
				tstr1.append("\n");
				tstr1.append("import java.util.Date; ");
				tstr1.append("\n");
//				tstr1.append("import javax.sql.*; ");
//				tstr1.append("\n");
				tstr1.append("import java.io.Serializable; ");
				tstr1.append("\n");
				tstr1.append("\n");
				tstr1.append("public class " + className
						+ " implements Serializable { ");
				tstr1.append("\n");
				tstr1.append("\n");
				tstr1.append("\tprivate static final long serialVersionUID = 1L; ");
				tstr1.append("\n");
				tstr1.append("\n");
				try {
					Statement statement = conn.createStatement();
					// ResultSet rs =
					// statement.executeQuery("select * from "+tables[i]);
					ResultSet rs = statement.executeQuery(sqls[i]);
					ResultSetMetaData rsd = rs.getMetaData();
					int cc = rsd.getColumnCount();
					for (int j = 1; j <= cc; j++) {
						if (ObjectTypeOrCommonlyType == OBJECTTYPE) {
							strType = this.getObjectType(rsd.getColumnType(j));
						} else {
							strType = this.getCommonlyType(j);
						}
						if (strType == null)
							continue;
						strName = rsd.getColumnName(j);
						tstr1.append("\tprivate " + strType + " "
								+ strName.toLowerCase() + ";");
						tstr1.append("\n");
						tstr2.append("\tpublic void set"
								+ strName.substring(0, 1).toUpperCase()
								+ strName.substring(1).toLowerCase() + "("
								+ strType + " " + strName.toLowerCase() + ") {");
						tstr2.append("\n");
						tstr2.append("\t\tthis." + strName.toLowerCase()
								+ " = " + strName.toLowerCase() + ";");
						tstr2.append("\n");
						tstr2.append("\t}");
						tstr2.append("\n");
						tstr2.append("\tpublic " + strType + " get"
								+ strName.substring(0, 1).toUpperCase()
								+ strName.substring(1).toLowerCase() + "() {");
						tstr2.append("\n");
						tstr2.append("\t\treturn this." + strName.toLowerCase()
								+ ";");
						tstr2.append("\n");
						tstr2.append("\t}");
						tstr2.append("\n");
					}
					rs.close();
					statement.close();
				} catch (Exception tableE) {
					tableE.printStackTrace();
				}
				tstr2.append("} ");
				tstr1.append("\n");
				tstr1.append(tstr2.toString());
				tstr1.append("\n");
				file = new File("src/"
						+ StringUtils.replace(this.packPublicUrl, ".", "/")
						+ "/" + this.packPrivateUrl + "/" + className + ".java");
				FileWriter fw = new FileWriter(file);
				fw.write(tstr1.toString());
				fw.flush();
				fw.close();
				tstr1.delete(0, tstr1.length());
				tstr2.delete(0, tstr2.length());
			}
			conn.close();
		} catch (Exception driverE) {
			driverE.printStackTrace();
		}

	}

	public String getObjectType(int type) {
		switch (type) {
		case Types.ARRAY:
			return null;
		case Types.BIGINT:
			return "Long";
		case Types.BINARY:
			return null;
		case Types.BIT:
			return "Byte";
		case Types.BLOB:
			return "Blob";
		case Types.BOOLEAN:
			return "Boolean";
		case Types.CHAR:
			return "String";
		case Types.CLOB:
			return "Clob";
		case Types.DATALINK:
			return null;
		case Types.DATE:
			return "Date";
		case Types.DECIMAL:
			return "Double";
		case Types.DISTINCT:
			return null;
		case Types.DOUBLE:
			return "Double";
		case Types.FLOAT:
			return "Float";
		case Types.INTEGER:
			return "Integer";
		case Types.NUMERIC:
			return "Integer";
		case Types.JAVA_OBJECT:
			return null;
		case Types.LONGVARBINARY:
			return null;
		case Types.LONGVARCHAR:
			return null;
		case Types.NULL:
			return null;
		case Types.OTHER:
			return null;
		case Types.REAL:
			return null;
		case Types.REF:
			return null;
		case Types.SMALLINT:
			return "Short";
		case Types.STRUCT:
			return null;
		case Types.TIME:
			return "Time";
		case Types.TIMESTAMP:
			return "Timestamp";
		case Types.TINYINT:
			return "Short";
		case Types.VARBINARY:
			return null;
		case Types.VARCHAR:
			return "String";
		default:
			return null;
		}
	}

	public String getCommonlyType(int type) {
		switch (type) {
		case Types.ARRAY:
			return null;
		case Types.BIGINT:
			return "long";
		case Types.BINARY:
			return null;
		case Types.BIT:
			return "byte";
		case Types.BLOB:
			return "String";
		case Types.BOOLEAN:
			return "boolean";
		case Types.CHAR:
			return "String";
		case Types.CLOB:
			return "String";
		case Types.DATALINK:
			return null;
		case Types.DATE:
			return "Date";
		case Types.DECIMAL:
			return "double";
		case Types.DISTINCT:
			return null;
		case Types.DOUBLE:
			return "double";
		case Types.FLOAT:
			return "float";
		case Types.INTEGER:
			return "int";
		case Types.NUMERIC:
			return "int";
		case Types.JAVA_OBJECT:
			return null;
		case Types.LONGVARBINARY:
			return null;
		case Types.LONGVARCHAR:
			return null;
		case Types.NULL:
			return null;
		case Types.OTHER:
			return null;
		case Types.REAL:
			return null;
		case Types.REF:
			return null;
		case Types.SMALLINT:
			return "short";
		case Types.STRUCT:
			return null;
		case Types.TIME:
			return "Time";
		case Types.TIMESTAMP:
			return "Timestamp";
		case Types.TINYINT:
			return "short";
		case Types.VARBINARY:
			return null;
		case Types.VARCHAR:
			return "String";
		default:
			return null;
		}
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setTableMatchPattern(String tableMatchPattern) {
		this.tableMatchPattern = tableMatchPattern;
	}

	public void setMatchPattern(String matchPattern) {
		this.matchPattern = matchPattern;
	}

	public String getDriver() {
		return this.driver;
	}

	public String getUrl() {
		return this.url;
	}

	public String getUserName() {
		return this.userName;
	}

	public String getPassword() {
		return this.password;
	}

	public String getTableName() {
		return this.tableName;
	}

	public String getTableMatchPattern() {
		return this.tableMatchPattern;
	}

	public String getMatchPattern() {
		return this.matchPattern;
	}

	public String getPackPublicUrl() {
		return packPublicUrl;
	}

	public void setPackPublicUrl(String packPublicUrl) {
		this.packPublicUrl = packPublicUrl;
	}

	public String getPackPrivateUrl() {
		return packPrivateUrl;
	}

	public void setPackPrivateUrl(String packPrivateUrl) {
		this.packPrivateUrl = packPrivateUrl;
	}

	public String getTableSql() {
		return tableSql;
	}

	public void setTableSql(String tableSql) {
		this.tableSql = tableSql;
	}

	public static void main(String[] args) {
		DbTable2JavaBean d2j = new DbTable2JavaBean(true);
		d2j.init(OBJECTTYPE);
		
		
		System.out.println("OK");
	}
}
