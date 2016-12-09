package apt.erp.common.vaadin;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import apt.erp.infrastructure.ResourceFileLoader;

public class ZipTownMap {

	private static final String FILENAME = "Towns.txt";
	
	private final Map<String, String> map;
	
	public ZipTownMap() {
		List<String> lines;
		
		try {
			lines = Files.readAllLines(ResourceFileLoader.loadPath(FILENAME), StandardCharsets.UTF_8);	
		} catch (IOException e) {
			throw new RuntimeException("Can not load " + FILENAME + " resource file");
		}
		
		Map<String, String> entries = new HashMap<>();		
		for(String line : lines) {
			String [] parts = line.split("\t");
			String zip = parts[0];
			String townName = parts[1];
			entries.put(zip, townName);
		}
		map = Collections.unmodifiableMap(entries);
	}

	String getTown(String zip) {
		return map.getOrDefault(zip, "");
	}
	
}
