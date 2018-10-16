package testHashCode_Equals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * <p>标题：list去重转换set 对象重写hashcode和equals</p>
 * <p>描述：</p>
 * <p>Copyright：Copyright(c) 2015 diwinet</p>
 * <p>日期：2015年12月15日</p>
 * @author	jiangxing
 */
public class TestList {
	public static void main(String[] args) throws InterruptedException {
		List<A> list = new ArrayList<A>();
		A a = new A("5050", "6060",new Date());
		Thread.sleep(1000);
		A a1 = new A("5050","6060",new Date());
		A a2 = new A("5050","7070",new Date());
		
		list.add(a);
		list.add(a1);
		Set<A> set = new HashSet<A>();
		set.addAll(list);
		set.add(a2);//加不进去，已经存在
//		list.clear();
//		list.addAll(set);
		System.out.println(Arrays.toString(set.toArray()));
		System.out.println(Arrays.toString(list.toArray()));
	}
}
