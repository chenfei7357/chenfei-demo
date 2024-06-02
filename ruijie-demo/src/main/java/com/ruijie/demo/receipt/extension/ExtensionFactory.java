package com.ruijie.demo.receipt.extension;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * 拓展点工厂类，统一收口获取的拓展点
 */
@Service
public class ExtensionFactory implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	private Map<Class, List<IExtensionPoint>> extensionMap = Maps.newConcurrentMap();

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext	= applicationContext;
	}


	/**
	 * 获取具体的拓展点
	 * @param param
	 * @param extensionClass
	 * @param <P>
	 * @param <T>
	 * @return
	 */
	public <P,T> T getExtension(P param,Class<T> extensionClass){
		//先尝试从缓存中获取对应的拓展点
		List<IExtensionPoint> iExtensionPoints = extensionMap.get(extensionClass);
		if(CollectionUtils.isEmpty(iExtensionPoints)){
			iExtensionPoints = Lists.newArrayList();
			Map<String, IExtensionPoint> IExtensionPointMap = applicationContext.getBeansOfType(IExtensionPoint.class);
			if(!CollectionUtils.isEmpty(IExtensionPointMap)){
				for (IExtensionPoint extensionPoint : IExtensionPointMap.values()) {
					if(extensionClass.isAssignableFrom(extensionPoint.getClass())){
						iExtensionPoints.add(extensionPoint);
					}
				}
			}
			//存入缓存
			extensionMap.put(extensionClass,iExtensionPoints);
		}
		for (IExtensionPoint iExtensionPoint : iExtensionPoints) {
			if(iExtensionPoint.match(param)){
				return (T)iExtensionPoint;
			}
		}
		throw new RuntimeException(String.format("未匹配到具体的拓展点,%s", JSONObject.toJSON(param)));
	}
}
