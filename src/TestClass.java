import java.math.BigDecimal;
import java.math.RoundingMode;

public class TestClass {
	public static void main(String[] args) {
		 String userDir = System.getProperty("user.dir").replace("\\", "/");
		 System.out.println(userDir);
		 Double a = 20d;
		 Double b = 10d;
		 BigDecimal c = new BigDecimal(a.toString()).divide( new BigDecimal(b.toString()));
		 c = c.setScale(0, RoundingMode.DOWN);
		 System.out.println(c);
		 String sbtmStr= Long.toHexString(6590160278008801016l);
		 System.out.println(sbtmStr);
	}
}
