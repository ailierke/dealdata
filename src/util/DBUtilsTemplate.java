package util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

/**
 * <p>标题：</p>
 * <p>描述：将事物的管理转交给spring,去掉对dataSource的直接获取,使用spring的proxyDataSource来自动获取connection
 * 从而达到对事物的注解控制</p>
 * <p>Copyright：Copyright(c) 2015 </p>
 * <p>日期：2015年6月9日</p>
 * @author	jx
 */
public class DBUtilsTemplate {
	private static final Logger LOG = Logger.getLogger(DBUtilsTemplate.class.getName()) ;
	//直接使用spring注入的datasource已经在配置文件中进行配置
	private QueryRunner queryRunner ;
	private Connection conn;
	public DBUtilsTemplate(Connection conn,QueryRunner queryRunner) {
		this.queryRunner = queryRunner;
		this.conn = conn;
	}
	/*把所有的new QueryRunner()去掉，使用spring注入的*/
	/*queryRunner = new QueryRunner();*/

	/** 
	 * 执行sql语句 
	 * 
	 * @param sql 
	 *            sql语句 
	 * @return受影响的行数 
	 */ 
	public int update(String sql) throws SQLException{ 
		return update(sql, null); 
	} 

	/** 
	 * 执行sql语句 <code> 
	 * executeUpdate("update user set username = 'kitty' where username = ?" ,?se= =?==, "hello kitty");
	 * </code> 
	 * 
	 * @param sql 
	 *            sql语句 
	 * @param param 
	 *            参数 
	 * @return受影响的行数 
	 */ 
	public int update(String sql, Object param) throws SQLException{ 
		return update(sql, new Object[] { param }); 
	} /** 
	 * 执行sql语句 
	 * 
	 * @param sql 
	 *            sql语句 
	 * @return受影响的行数 
	 */ 
	public Long insert(String sql)throws SQLException { 
		//return update(sql, null);
		//获取新增记录的自增主键 
		//       Long id = (Long) query(conn, "SELECT LAST_INSERT_ID()", new ScalarHandler(1)); 
		Long id = queryRunner.insert(conn,sql,new ScalarHandler<Long>(1));   //query(conn, "SELECT LAST_INSERT_ID()", new ScalarHandler(1));
		return id;
	} 

	/** 
	 * 执行sql语句 <code> 
	 * executeUpdate("update user set username = 'kitty' where username = ?" ,?se= =?==, "hello kitty");
	 * </code> 
	 * 
	 * @param sql 
	 *            sql语句 
	 * @param param 
	 *            参数 
	 * @return受影响的行数 
	 */ 
	public Long insert(String sql, Object param) throws SQLException{ 
		//		return update(sql, new Object[] { param }); 
		Long id = queryRunner.insert(conn,sql,new ScalarHandler<Long>(1),param);   //query(conn, "SELECT LAST_INSERT_ID()", new ScalarHandler(1));
		return id;
	} 

	/**
	 * <p>说明：</p>
	 * <p>时间：2015年9月15日 下午3:30:45</p>
	 * @param sql
	 * @param param
	 * @return
	 */
	public Long insert(String sql, Object[] param) throws SQLException{ 
		//		return update(sql, param); 
		Long id = queryRunner.insert(conn,sql,new ScalarHandler<Long>(1),param);   //query(conn, "SELECT LAST_INSERT_ID()", new ScalarHandler(1));
		return id;
	} 
	/** 
	 * 执行sql语句 
	 * 
	 * @param sql 
	 *            sql语句 
	 * @return受影响的行数 
	 */ 
	public int delete(String sql) throws SQLException{ 
		return update(sql, null); 
	} 

	/** 
	 * 执行sql语句 <code> 
	 * executeUpdate("update user set username = 'kitty' where username = ?" ,?se= =?==, "hello kitty");
	 * </code> 
	 * 
	 * @param sql 
	 *            sql语句 
	 * @param param 
	 *            参数 
	 * @return受影响的行数 
	 */ 
	public int delete(String sql, Object param) throws SQLException{ 
		return update(sql, new Object[] { param }); 
	} 
	/** 
	 * 执行sql语句 
	 * 
	 * @param sql 
	 *            sql语句 
	 * @param params 
	 *            参数数组 
	 * @return受影响的行数 
	 */ 
	public int update(String sql, Object[] params)throws SQLException { 
		int affectedRows = 0; 
			if (params == null) { 
				affectedRows = queryRunner.update(conn,sql); 

			} else { 
				affectedRows = queryRunner.update(conn,sql, params); 
			} 

		return affectedRows; 
	} 

	/** 
	 * 执行批量sql语句 
	 * 
	 * @param sql 
	 *            sql语句 
	 * @param params 
	 *            二维参数数组 
	 * @return受影响的行数的数组 
	 */ 
	public int[] batchUpdate(String sql, Object[][] params)throws SQLException { 
		int[] affectedRows = new int[0]; 
			affectedRows = queryRunner.batch(conn,sql, params); 
		return affectedRows; 
	} 

	/** 
	 * 执行查询，将每行的结果保存到一个Map对象中，然后将所有Map对象保存到List中 
	 * 
	 * @param sql 
	 *            sql语句 
	 * @return查询结果 
	 */ 
	public List<Map<String, Object>> queryForMapList(String sql) throws SQLException{ 
		return queryForMapList(sql, null); 
	} 

	/** 
	 * 执行查询，将每行的结果保存到一个Map对象中，然后将所有Map对象保存到List中 
	 * 
	 * @param sql 
	 *            sql语句 
	 * @param param 
	 *            参数 
	 * @return查询结果 
	 */ 
	public List<Map<String, Object>> queryForMapList(String sql, Object param)throws SQLException { 
		return queryForMapList(sql, new Object[] { param }); 
	} 

	/** 
	 * 执行查询，将每行的结果保存到一个Map对象中，然后将所有Map对象保存到List中 
	 * 
	 * @param sql 
	 *            sql语句 
	 * @param params 
	 *            参数数组 
	 * @return查询结果 
	 */ 
	public List<Map<String, Object>> queryForMapList(String sql, Object[] params)throws SQLException { 
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(); 
			if (params == null) { 
				list = (List<Map<String, Object>>) queryRunner.query(conn,sql,
						new MapListHandler()); 
			} else { 
				list = (List<Map<String, Object>>) queryRunner.query(conn,sql,
						new MapListHandler(), params); 
			} 
		return list; 
	} 

	/** 
	 * 执行查询，将每行的结果保存到Bean中，然后将所有Bean保存到List中 
	 * 
	 * @param entityClass 
	 *            类名 
	 * @param sql 
	 *            sql语句 
	 * @return查询结果 
	 * @throws SQLException 
	 */ 
	public <T> List<T> queryForList( String sql,Class<T> entityClass) throws SQLException {
		return queryForList( sql,entityClass, null); 
	} 

	/** 
	 * 执行查询，将每行的结果保存到Bean中，然后将所有Bean保存到List中 
	 * 
	 * @param entityClass 
	 *            类名 
	 * @param sql 
	 *            sql语句 
	 * @param param 
	 *            参数 
	 * @return查询结果 
	 * @throws SQLException 
	 */ 
	public <T> List<T> queryForList(String sql,Class<T> entityClass,Object param) throws SQLException {
			return queryForList( sql,entityClass, new Object[] { param }); 

	} 
	/** 
	 * 执行查询，将每行的结果保存到Bean中，然后将所有Bean保存到List中 
	 * 
	 * @param entityClass 
	 *            类名 
	 * @param sql 
	 *            sql语句 
	 * @param params 
	 *            参数数组 
	 * @return查询结果 
	 * @throws SQLException 
	 */ 
	public <T> List<T> queryForList(String sql,Class<T> entityClass, Object[] params) throws SQLException {
		List<T> list = new ArrayList<T>(); 

		if (params == null) { 
			list = (List<T>) queryRunner.query(conn,sql, new BeanListHandler<T>(entityClass)); 
		} else { 
			list = (List<T>) queryRunner.query(conn,sql, new BeanListHandler<T>(entityClass), params); 
		} 

		return list; 
	} 

	/** 
	 * 查询出结果集中的第一条记录，并封装成对象 
	 * 
	 * @param entityClass 
	 *            类名 
	 * @param sql 
	 *            sql语句 
	 * @return对象 
	 */ 
	public <T> T findFirst(Class<T> entityClass, String sql) throws SQLException{ 
		return findFirst(entityClass, sql, null); 
	} 

	/** 
	 * 查询出结果集中的第一条记录，并封装成对象 
	 * 
	 * @param entityClass 
	 *            类名 
	 * @param sql 
	 *            sql语句 
	 * @param param 
	 *            参数 
	 * @return对象 
	 */ 
	public <T> T findFirst(Class<T> entityClass, String sql, Object param) throws SQLException{ 
		return findFirst(entityClass, sql, new Object[] { param }); 
	} 

	/** 
	 * 查询出结果集中的第一条记录，并封装成对象 
	 * 
	 * @param entityClass 
	 *            类名 
	 * @param sql 
	 *            sql语句 
	 * @param params 
	 *            参数数组 
	 * @return对象 
	 */ 
	@SuppressWarnings("unchecked") 
	public <T> T findFirst(Class<T> entityClass, String sql, Object[] params)throws SQLException { 
		Object object = null; 
			if (params == null) { 
				object = queryRunner.query(conn,sql, new BeanHandler<T>(entityClass));
			} else { 
				object = queryRunner.query(conn,sql, new BeanHandler<T>(entityClass),
						params); 
			} 
		return (T) object; 
	} 

	/** 
	 * 查询出结果集中的第一条记录，并封装成Map对象 
	 * 
	 * @param sql 
	 *            sql语句 
	 * @return封装为Map的对象 
	 */ 
	public Map<String, Object> findFirst(String sql)throws SQLException { 
		return findFirst(sql, null); 
	} 

	/** 
	 * 查询出结果集中的第一条记录，并封装成Map对象 
	 * 
	 * @param sql 
	 *            sql语句 
	 * @param param 
	 *            参数 
	 * @return封装为Map的对象 
	 */ 
	public Map<String, Object> findFirst(String sql, Object param)throws SQLException { 
		return findFirst(sql, new Object[] { param }); 
	} 

	/** 
	 * 查询出结果集中的第一条记录，并封装成Map对象 
	 * 
	 * @param sql 
	 *            sql语句 
	 * @param params 
	 *            参数数组 
	 * @return封装为Map的对象 
	 */ 
	public Map<String, Object> findFirst(String sql, Object[] params)throws SQLException { 
		Map<String, Object> map = null; 
			if (params == null) { 
				map = (Map<String, Object>) queryRunner.query(conn,sql, 
						new MapHandler()); 
			} else { 
				map = (Map<String, Object>) queryRunner.query(conn,sql, 
						new MapHandler(), params); 
			} 
		return map; 
	} 

	/** 
	 * 查询某一条记录，并将指定列名的数据转换为Object 
	 * 
	 * @param sql 
	 *            sql语句 
	 * @param columnName 
	 *            列名 
	 * @return结果对象 
	 */ 
	public Object findBy(String sql, String columnName) throws SQLException{ 
		return findBy(sql, columnName, null); 
	} 

	/** 
	 * 查询某一条记录，并将指定列名的数据转换为Object 
	 * 
	 * @param sql 
	 *            sql语句 
	 * @param columnName 
	 *            列名 
	 * @param param 
	 *            参数 
	 * @return结果对象 
	 */ 
	public Object findBy(String sql, String columnName, Object param) throws SQLException{ 
		return findBy(sql, columnName, new Object[] { param }); 
	} 

	/** 
	 * 查询某一条记录，并将指定列的数据转换为Object 
	 * 
	 * @param sql 
	 *            sql语句 
	 * @param columnName 
	 *            列名 
	 * @param params 
	 *            参数数组 
	 * @return结果对象 
	 */ 
	public Object findBy(String sql, String columnName, Object[] params) throws SQLException{ 
		Object object = null; 
			if (params == null) { 
				object = queryRunner.query(conn,sql, new ScalarHandler<String>(columnName));
			} else { 
				object = queryRunner.query(conn,sql, new ScalarHandler<String>(columnName),params); 
			} 
		return object; 
	} 

	/** 
	 * 查询某一条记录，并将指定列的数据转换为Object 
	 * 
	 * @param sql 
	 *            sql语句 
	 * @param columnIndex 
	 *            列索引 
	 * @return结果对象 
	 */ 
	public Object findBy(String sql, int columnIndex) throws SQLException{ 
		return findBy(sql, columnIndex, null); 
	} 

	/** 
	 * 查询某一条记录，并将指定列的数据转换为Object 
	 * 
	 * @param sql 
	 *            sql语句 
	 * @param columnIndex 
	 *            列索引 
	 * @param param 
	 *            参数 
	 * @return结果对象 
	 */ 
	public Object findBy(String sql, int columnIndex, Object param)throws SQLException { 
		return findBy(sql, columnIndex, new Object[] { param }); 
	} 

	/** 
	 * 查询某一条记录，并将指定列的数据转换为Object 
	 * 
	 * @param sql 
	 *            sql语句 
	 * @param columnIndex 
	 *            列索引 
	 * @param params 
	 *            参数数组 
	 * @return结果对象 
	 */ 
	public Object findBy(String sql, int columnIndex, Object[] params)throws SQLException { 
		Object object = null; 
			if (params == null) { 
				object = queryRunner.query(conn,sql, new ScalarHandler<Integer>(columnIndex));
			} else { 
				object = queryRunner.query(conn,sql, new ScalarHandler<Integer>(columnIndex),params); 
			} 
		return object; 
	}


	public Integer queryForInt(String sql) throws SQLException {
		//两个方式
		Integer total=0;
		if((Long) this.findBy(sql, 1)!=null){
			total = ((Long) this.findBy(sql, 1)).intValue();
		}
		return total;
	}
	

	public Double queryForDouble(String sql) throws SQLException {
		//两个方式
		Double total=0d;
		if((Double) this.findBy(sql, 1)!=null){
			total = ((Double) this.findBy(sql, 1));
		}
		return total;
	}
	public Integer queryForDoubleInt(String sql) throws SQLException {
		//两个方式
		Double total=0d;
		if((Double) this.findBy(sql, 1)!=null){
			total = ((Double) this.findBy(sql, 1));
		}
		return total.intValue();
	}
	public Integer queryForDoubleInt(String sql,Object[] params) throws SQLException {
		//两个方式
		Double total=0d;
		if((Double) this.findBy(sql, 1,params)!=null){
			total = ((Double) this.findBy(sql, 1,params));
		}
		return total.intValue();
	}

	public int queryForInt(String sql,Object[] params) throws SQLException {
		//两个方式
		Integer total=0;
		if((Long) this.findBy(sql, 1,params)!=null){
			total = ((Long) this.findBy(sql, 1,params)).intValue();
		}
		return total;
	}
	
	public int queryForInt(String sql,Object param) throws SQLException {
		//两个方式
				Integer total=0;
				if((Long) this.findBy(sql, 1, param)!=null){
					total = ((Long) this.findBy(sql, 1,new Object[]{param})).intValue();
				}
				return total;
	}

	public <T> List<T> queryForListByKey(String sql, String key,Class<T> entityClass) throws SQLException {
		List<T> list = (List<T>) queryRunner.query(conn,sql, new ColumnListHandler<T>(key));
		return list;
	}

	public  <T> List<T> queryForListByKey(String sql, String key, Class<T> entityClass, Object... param) throws SQLException {
		List<T> list = (List<T>) queryRunner.query(conn,sql, new ColumnListHandler<T>(key),param);
		return list;
	}
}
