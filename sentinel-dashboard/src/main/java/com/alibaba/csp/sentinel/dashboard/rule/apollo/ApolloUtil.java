package com.alibaba.csp.sentinel.dashboard.rule.apollo;

import com.alibaba.fastjson.JSON;
import com.ctrip.framework.apollo.openapi.client.ApolloOpenApiClient;
import com.ctrip.framework.apollo.openapi.dto.NamespaceReleaseDTO;
import com.ctrip.framework.apollo.openapi.dto.OpenItemDTO;
import com.ctrip.framework.apollo.openapi.dto.OpenNamespaceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApolloUtil {
	@Autowired
	private ApolloOpenApiClient apolloOpenApiClient;

	public void set(String appId,String key,String values){
		// Increase the configuration
		OpenItemDTO openItemDTO = new OpenItemDTO();
		openItemDTO.setKey(key);
		openItemDTO.setValue(values);
		openItemDTO.setComment("Program auto-join");
		openItemDTO.setDataChangeCreatedBy("apollo");
		apolloOpenApiClient.createOrUpdateItem(appId, "DEV", "default", ApolloConfigUtil.NAMESPACE, openItemDTO);

		// Release configuration
		NamespaceReleaseDTO namespaceReleaseDTO = new NamespaceReleaseDTO();
		namespaceReleaseDTO.setEmergencyPublish(true);
		namespaceReleaseDTO.setReleaseComment("Modify or add configurations");
		namespaceReleaseDTO.setReleasedBy("apollo");
		namespaceReleaseDTO.setReleaseTitle("Modify or add configurations");
		apolloOpenApiClient.publishNamespace(appId, "DEV", "default", ApolloConfigUtil.NAMESPACE, namespaceReleaseDTO);
	};


	public String get(String appId,String key){
		OpenItemDTO item = apolloOpenApiClient.getItem(appId, "DEV", "default",
				ApolloConfigUtil.NAMESPACE, key);
		return item.getValue();
	}
}
