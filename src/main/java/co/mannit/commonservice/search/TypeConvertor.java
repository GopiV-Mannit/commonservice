package co.mannit.commonservice.search;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TypeConvertor {

	private static final Logger logger = LogManager.getLogger(TypeConvertor.class);
			
	static private Map<String, Function<String, ? extends Object>> dataTypeConverter = null;
	static {
		dataTypeConverter = new HashMap<>();
		dataTypeConverter.put("L", (t)-> Long.parseLong(t));
		dataTypeConverter.put("I", (t)-> Integer.parseInt(t));
		dataTypeConverter.put("S", (t)-> t);
		dataTypeConverter.put("D", (t)-> {
			SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
			try {
				Date d = sd.parse(t);
				
				Calendar calendar = Calendar.getInstance();
			    calendar.setTime(d);
			    calendar.add(Calendar.MINUTE, 330);
			    
			    d = calendar.getTime();
			    logger.debug("Date : {}",d );
				return d;
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return null;
		});
	}
	
	@SuppressWarnings("unchecked")
	static public <T> T convert(String type, String value){
		return (T) dataTypeConverter.get(type).apply(value);
	}
	
}
