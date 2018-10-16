package util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

/**
 * <p>标题：MemCachedManager管理类</p>
 * <p>描述：对Memcached进行缓存管理</p>
 * <p>Copyright：Copyright(c) 2014 diwinet</p>
 * <p>日期：2014-1-10</p>
 * @author	Jack Lee
 */
public class MemCachedManager {

	/**
	 * 创建 MemCachedClient 全局的唯一实例
	 */
	private MemCachedClient mcc = new MemCachedClient();
	
	/**
	 * 声明MemCachedManager
	 */
	private static MemCachedManager memCachedManager = null;
	private MemCachedManager() {}

	/**
	 * <p>说明：初始化MemCached链接</p>
	 * <p>时间：2014-3-10 下午3:19:41</p>
	 */
	private void initConnection() {
		
		String server = "192.168.0.42";
		String port = "11211";
		String weight = "1";
		
		if(!StringUtil.isEmpty(server) && !StringUtil.isEmpty(port)){			
			String[] servers = server.split(",");
			for (int i = 0; i < servers.length; i++) {
				servers[i] = servers[i] + ":" + port;
			}
			Integer[] weights = getMemWeights(weight);
			initPool(servers, weights);
		}
	}
	/**
	 * <p>说明：初始化连接池</p>
	 * <p>时间：2014-3-10 下午3:20:07</p>
	 * @param servers 服务器地址数组
	 * @param weights 权重
	 */
	private void initPool(String[] servers, Integer[] weights) {
		// 获取socke连接池的实例对象
		SockIOPool pool = SockIOPool.getInstance();
		// 设置服务器信息
		pool.setServers(servers);
		if(weights != null){			
			pool.setWeights(weights);
		}
		// 设置初始连接数、最小和最大连接数以及最大处理时间
		pool.setInitConn(50);
		pool.setMinConn(50);
		pool.setMaxConn(3000);
		pool.setMaxIdle(0);
		// 设置主线程的睡眠时间
		pool.setMaintSleep(10);
		// 设置TCP的参数，连接超时等
		pool.setNagle(false);
		pool.setSocketTO(3000);
		pool.setSocketConnectTO(0);
		pool.setHashingAlg(SockIOPool.CONSISTENT_HASH);
		// 初始化连接池
		pool.initialize();
	}

	/**
	 * <p>说明：单列构建MemCachedManager对象</p>
	 * <p>时间：2014-3-10 下午3:20:50</p>
	 * @return MemCachedManager对象
	 */
	public static MemCachedManager getInstance() {
		synchronized (MemCachedManager.class) {
			if (memCachedManager == null) {
				memCachedManager = new MemCachedManager();
				memCachedManager.initConnection();
			}
		}
		return memCachedManager;
	}

	/**
	 * 添加一个指定的值到缓存中.
	 * @param key key
	 * @param value 存入值
	 * @return 添加状态
	 */
	public boolean set(String key, Object value) {
		return mcc.set(key, value);
	}

	/**
	 * <p>说明：将值存入缓存中</p>
	 * <p>时间：2014-3-10 下午3:21:36</p>
	 * @param key 指定key
	 * @param value 指定key存入的value
	 * @param minute 有效时间 分钟
	 * @return 存入状态
	 */
	public boolean set(String key, Object value, Integer minute) {
		return mcc.set(key, value, new Date(minute.longValue() * 60 * 1000));
	}
	/**
	 * <p>说明：将值存入缓存中</p>
	 * <p>时间：2014-3-10 下午3:21:36</p>
	 * @param second 有效时间 分
	 * @param key 指定key
	 * @param value 指定key存入的value
	 * @return 存入状态
	 */
	public boolean set(Integer second, String key, Object value) {
		return mcc.set(key, value, new Date(second.longValue() * 1000));
	}
	
	/**
	 * <p>说明：删除缓存</p>
	 * <p>时间：2014-3-10 下午3:22:21</p>
	 * @param key 指定删除缓存Key
	 * @return 删除状态
	 */
	public boolean delete(String key) {
		return mcc.delete(key);
	}
	
	/**
	 * <p>说明：清除所有</p>
	 * <p>时间：2014-3-12 下午6:01:22</p>
	 * @return 清除状态
	 */
	public boolean flushAll() {
		return mcc.flushAll();
	}

	/**
	 * <p>说明：更新缓存对象</p>
	 * <p>时间：2014-3-10 下午3:22:48</p>
	 * @param key 指定缓存key
	 * @param value 指定更新缓存值
	 * @return 更新结果,true更新成功  false更新失败
	 */
	public boolean replace(String key, Object value) {
		return mcc.replace(key, value);
	}
	
	/**
	 * <p>说明：更新缓存对象</p>
	 * <p>时间：2014-3-10 下午3:22:48</p>
	 * @param key 指定缓存key
	 * @param value 指定更新缓存值
	 * @param minute 有效时间 分钟
	 * @return 更新结果,true更新成功  false更新失败
	 */
	public boolean replace(String key, Object value, Integer minute) {
		Long timLong = minute.longValue() * 60 * 1000;
		return mcc.replace(key, value, new Date(timLong));
	}

	/**
	 * <p>说明：判断Key是否存在</p>
	 * <p>时间：2014-3-10 下午3:24:14</p>
	 * @param key 判断缓存中的key是否存在
	 * @return true 存在  false不存在
	 */
	public boolean keyExists(String key) {
		return mcc.keyExists(key);
	}

	/**
	 * <p>说明：根据指定key获取对象</p>
	 * <p>时间：2014-3-10 下午3:24:47</p>
	 * @param key 获取对象key
	 * @return 指定对象
	 */
	public Object get(String key) {
		return mcc.get(key);
	}

	/**
	 * <p>说明：获取配置权重</p>
	 * <p>时间：2014-3-10 下午3:27:09</p>
	 * @param weight 权重字符串
	 * @return 权重数组
	 */
	private Integer[] getMemWeights(String weight) {
		Integer[] weights = null;
		if(!StringUtil.isEmpty(weight)){			
			String[] weightArry = weight.split(",");
			int length = weightArry.length;
			if(length > 0){		
				weights = new Integer[length];
				for (int i = 0; i < length; i++) {
					weights[i] = Integer.valueOf(weightArry[i]);
				}
			}
		}
		return weights;
	}
	
	
	public static void main(String[] args) throws Exception {
		MemCachedManager cache = MemCachedManager.getInstance();
		
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("6590161178008879147", DateUtils.stringToDate("2017-04-01 10:11:11", "yyyy-MM-dd hh:mm:ss").getTime());
		m.put("6590161178008885186", DateUtils.stringToDate("2017-04-13 10:11:11", "yyyy-MM-dd hh:mm:ss").getTime());
		boolean t = cache.set("MEM_DW_STATUS_19001", m,0);
		System.out.println(cache.get("MEM_DW_STATUS_19001"));
		
		
	}
}