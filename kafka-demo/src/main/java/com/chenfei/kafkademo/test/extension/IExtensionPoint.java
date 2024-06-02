package com.chenfei.kafkademo.test.extension;

/**
 * 扩展点统一接口
 * @param <P>
 */
public interface IExtensionPoint<P> {

	/**
	 * 判断业务身份和当前扩展点是否匹配
	 * @param pamrm
	 * @return
	 */
	boolean match(P pamrm);
}
