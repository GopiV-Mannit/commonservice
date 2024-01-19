package co.mannit.commonservice.search;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import co.mannit.commonservice.ServiceCommonException;
import co.mannit.commonservice.common.util.TextUtil;

@Component
public class FilterBuilder {

	private static final Logger logger = LogManager.getLogger(FilterBuilder.class);
	
	public List<Search<? extends Object>> buildSearch(String searchStr) throws ServiceCommonException{
		logger.debug("<buildSearch>");
		if(TextUtil.isEmpty(searchStr)) return null;
		
		logger.debug("</buildSearch>");
		return parseFilter(searchStr);
	}
	

	private List<Search<?>> parseFilter(String searchStr) throws ServiceCommonException {
		logger.debug("<parseFilter>");
		
		List<Search<?>> lstSearch = new ArrayList<>();
		String[] tokens = searchStr.split("\\s+");
		int lenght = tokens.length;
		String[] nameop = null;
		try {
			if(lenght == 3) {
				
				nameop = tokens[0].split("_");
				
				Search<?> search = build(nameop[1]);
				search.setName(nameop[0]);
				search.setType(nameop[1]);
				search.setOperator(tokens[1]);
				search.setValue(TypeConvertor.convert(nameop[1], tokens[2]));
				lstSearch.add(search);
							
			}else if(lenght == 7) {
				nameop = tokens[0].split("_");
				
				Search<?> search = build(nameop[1]);
				search.setName(nameop[0]);
				search.setType(nameop[1]);
				search.setOperator(tokens[1]);
				search.setValue(TypeConvertor.convert(nameop[1], tokens[2]));
				lstSearch.add(search);
				
				search.setJoinOp(tokens[3]);
				
				nameop = tokens[4].split("_");
				
				Search<?> search1 = build(nameop[1]);
				search1.setName(nameop[0]);
				search1.setType(nameop[1]);
				search1.setOperator(tokens[5]);
				search1.setValue(TypeConvertor.convert(nameop[1], tokens[6]));
				lstSearch.add(search1);
			
			}else {
				throw new Exception();
			}
		} catch (Exception e) {
			throw new ServiceCommonException("109", new String[] {searchStr});
		}
		
		logger.debug("parseFilter filter {}",  lstSearch);
		return lstSearch;
	}
	
	
	private <T> Search<?> build(String type){
		Search<?> search = null;
		switch(type) {
			case "L":
				search = new Search<Long>();
				break;
			case "I":
				search = new Search<Integer>();
				break;
			case "S":
				search = new Search<String>();
				break;
			case "D":
				search = new Search<Date>();
				break;
		}
		
		return search;
	}
	
}
