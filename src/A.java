
public class A {
	public static void main(String[] args) {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
        System.out.println(loader);
        System.out.println(loader.getParent());
        System.out.println(loader.getParent().getParent());//bootstrap 由c语言写的，所以找不到
        System.out.println(String.class.getClassLoader());
        
        
	}
}

