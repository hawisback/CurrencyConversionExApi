package utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UtilHelper {
	
	private static String relativePropertiesFilePath = "\\src\\test\\resources\\config.properties";
	private static String jsonFileRelativePath = "src/test/resources/jsonFeed";
	
	public static SimpleDateFormat getPstDateFormat() {
		SimpleDateFormat pstDateFormat = null;
		try {
			pstDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			pstDateFormat.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
		} catch (Exception e) {
			System.out.println("Failed to set the date formatter." + e.getMessage());
		}
		return pstDateFormat;
	}
	
	public static SimpleDateFormat getSimpleDateFormat() {
		SimpleDateFormat simpleDateFormat = null;
		try {
			simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		} catch (Exception e) {
			System.out.println("Failed to set the date formatter." + e.getMessage());
		}
		return simpleDateFormat;
	}
	
	public static String getProperty(String propertyName ) {
		Properties properties;
		BufferedReader reader;
		String propertyFilePath = System.getProperty("user.dir")
				+ relativePropertiesFilePath;
		try {
			reader = new BufferedReader(new FileReader(propertyFilePath));
			properties = new Properties();
			try {
				properties.load(reader);
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("config.properties not found at " + propertyFilePath);
		}
		
		if(properties.getProperty(propertyName)!=null) {
			return properties.getProperty(propertyName);
		} else {
			throw new RuntimeException("Unable to find property with name '" + propertyName + "', please recheck the file name and config.properties.");
		}
	}
	
	public static Map<String, Double> getJsonFileFeed(String filename) {
		Map<String, Double> referenceRates = new HashMap<String, Double>();
		try {
			File file = new File(jsonFileRelativePath);
			String abs = file.getAbsolutePath();
			ObjectMapper mapper = new ObjectMapper();
			referenceRates = mapper.readValue(new FileInputStream(abs+"/"+ filename), Map.class);

		} catch(Exception ex) {
			System.out.println("Error in getting the reference rates quotes from the Json file name provided."+ ex);
		}
		return referenceRates;
	}
}