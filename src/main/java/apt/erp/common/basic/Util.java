package apt.erp.common.basic;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Util {

	public static List<Integer> generateIntRangeList(int first, int last) {
    	return IntStream.rangeClosed(first, last).mapToObj(Integer::new).collect(Collectors.toList());
    }
	
}
