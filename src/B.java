
public class B {
 
	void test1(){
		String s1 = "Monday";
		String s2 = "Mon"+"day"; 
		if(s1==s2){
			System.out.println("s1==s2");
		}else{
			System.out.println("s1!=s2");
		}
	}

	void test2(){
		String s1 = "Monday";  
		String s2 = new String("Monday");  
		if (s1 == s2)  
			System.out.println("s1 == s2");  
		else  
			System.out.println("s1 != s2");  
		if (s1.equals(s2)) System.out.println("s1 equals s2");  
		else  
			System.out.println("s1 not equals s2");  
	} 
	// 3. 字符串常量池 （指的是在编译期被确定，并被保存在已编译的.class文件中的一些数据。它包括了关于类、方法、接口等中的常量，也包括字符串常量。）	
	//原来，程序在运行的时候会创建一个字符常量池当使用 s2 = "Monday" 这样的表达是创建字符串的时候，程序首先会在这个String常量池中寻找相同值的对象，
	//（Java会确保一个字符串常量只有一个拷贝） 在第一个程序中因为例子中的s1和s2中的”Monday”都是字符串常量，它们在编译期就被确定了，
	//所以s1==s2为true；而”Mon”和”day”也都是字符串常量，当一个字符串由多个字符串常量连接而成时，它自己肯定也是字符串常量，
	//所以s2也同样在编译期就被解析为一个字符串常量，所以s2也是常量池中 ”Monday”的一个引用。 
	//第二段程序中，使用了 new 操作符，他明白的告诉程序："我要一个新的！不要旧的！"用new String() 创建的字符串不是常量，不能在编译期就确定，
	//所以new String() 创建的字符串不放入常量池中，它们有自己的地址空间
	void test3(){
		String s1 = "Monday";  
		String s2 = new String("Monday");  
		s2 = s2.intern();  
		if (s1 == s2)  
			System.out.println("s1 == s2");  
		else  
			System.out.println("s1 != s2");  
		if (s1.equals(s2)) System.out.println("s1 equals s2");  
		else  
			System.out.println("s1 not equals s2");  
	}


	//	 这次加入：s2 = s2.intern();  程序输出： s1 == s2   s1 equals s2
	//	原来，（java.lang.String的intern()方法"abc".intern()方法的返回值还是字符串"Monday"，
	//	表面上看起来好像这个方法没什么用处。但实际上，它做了个小动作：检查常量池里是否存在"Monday"这么一个字符串，如果存在，
	//	就返回常量池里的字符串；如果不存在，该方法会把"Monday"添加到常量池中，然后再返回它的引用。
	//	    更好的办法：
	//	    把所有的String都intern()到常量池去吧
	//	    最好在用到new的时候就进行这个操作
	//	    String s2 = new String("Monday").intern();
	//	    然后就可以用==比较两个字符串的值了
	
	
	/**
	 * 在一般的应用中你不需要了解hashcode的用法，但当你用到hashmap，hashset等集合类时要注意下hashcode。
		你想通过一个object的key来拿hashmap的value，hashmap的工作方法是，通过你传入的object的hashcode在内存中找地址，
		当找到这个地址后再通过equals方法来比较这个地址中的内容是否和你原来放进去的一样，一样就取出value。
	所以这里要匹配2部分，hashcode和equals但假如说你new一个object作为key去拿value是永远得不到结果的，因为每次new一个object，
	这个object的hashcode是永远不同的，所以我们要重写hashcode，你可以令你的hashcode是object中的一个恒量，
    	这样永远可以通过你的object的hashcode来找到key的地址，然后你要重写你的equals方法，使内存中的内容也相等
	 */
}
