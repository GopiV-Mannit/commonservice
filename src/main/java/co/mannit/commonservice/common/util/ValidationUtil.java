package co.mannit.commonservice.common.util;

import org.springframework.util.StringUtils;

import co.mannit.commonservice.pojo.BaseReqParam;

public class ValidationUtil {

	static public boolean validateDomainAndId(String domain, String subDomain, String id) {
		if(!StringUtils.hasLength(domain) || !StringUtils.hasLength(subDomain) || !StringUtils.hasLength(id)) {
			return false;
		}
		
		return true;
	}
	
	
	static public boolean validateDomainAndId(BaseReqParam baseReqParam) {
		return validateDomainAndId(baseReqParam.getDomain(), baseReqParam.getSubdomain(), baseReqParam.getUserId());
	}
}
