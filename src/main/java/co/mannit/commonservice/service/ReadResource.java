package co.mannit.commonservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.mannit.commonservice.ServiceCommonException;
import co.mannit.commonservice.common.MongokeyvaluePair;
import co.mannit.commonservice.common.util.ValidationUtil;
import co.mannit.commonservice.dao.ResourceDao;
import co.mannit.commonservice.pojo.PaginationReqParam;

@Service
public class ReadResource {

	private static final Logger logger = LogManager.getLogger(ReadResource.class);
			
	@Autowired
	private ResourceDao resourceDao;
	
	public List<String> readResource(PaginationReqParam paginationReq) throws Exception {
		logger.debug("<readResource>");
		
		if(Optional.of(ValidationUtil.validateDomainAndId(paginationReq)).isEmpty()) {
			throw new ServiceCommonException("106");
		}
		
		List<MongokeyvaluePair<? extends Object>> lstKeyValuePairs = new ArrayList<>();
		lstKeyValuePairs.add(new MongokeyvaluePair<String>("domain", paginationReq.getDomain()));
		lstKeyValuePairs.add(new MongokeyvaluePair<String>("subdomain", paginationReq.getSubdomain()));
		lstKeyValuePairs.add(new MongokeyvaluePair<ObjectId>("userId", new ObjectId(paginationReq.getUserId())));
		
		
		List<Document> listDoc = resourceDao.findDoc(lstKeyValuePairs, paginationReq);
		
		List<String> lst = listDoc.stream().map(doc->doc.toJson()).collect(Collectors.toCollection(ArrayList::new));
		
		logger.debug("</readResource>");
//		return resourceDao.findDoc(lstKeyValuePairs);
//		return "Resource Created Successfully";
//		return listDoc;
		return lst;
	}
	
}
