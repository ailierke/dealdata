package java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class Test {
	public static void main(String[] args) {
		List<Integer> names = new ArrayList<>();
		names.add(1);
		names.add(2);
		names.add(5);
		Collections.sort(names, (a,b)->{return b-a;});
		System.out.println(Arrays.toString(names.toArray()));
		String[] array = {"a", "b", "c"};
			for(Integer i : names){
				Stream.of(array).map(item ->  {return item.concat(i.toString());}).forEach(System.out::println);
			}
	}
}
