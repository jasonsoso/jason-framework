package com.jason.framework.orm.elasticsearch.constant;

import java.io.Serializable;

/**
 * 索引常量
 * @author Jason
 * @date 2014-6-27
 */
public class IndexConstant implements Serializable {
	private static final long serialVersionUID = 1L;

	private IndexConstant() {
	}

	/**
	 * 索引
	 * @author Jason
	 * @date 2014-6-27
	 */
	public static enum Index {
		/**  index:资讯 */
		INFO("info"), 
		;
		
		private final String index;
		
		private Index(final String index) {
			this.index = index;
		}
		public String toString() {
			return index;
		}
		
	}
	
	/**
	 * 索引类型
	 * @author Jason
	 * @date 2014-6-27
	 */
	public static enum Type {
		/**  index:资讯  type:资讯 */
		INFO_INFO("info"), 
		;
		
		
		private final String type;
		private Type(final String type) {
			this.type = type;
		}
		public String toString() {
			return type;
		}
	}
	
	
	
	public static enum PlaceType {

		/** PlaceType:国家 513  */
		COUNTRY(513), 
		
		/** PlaceType:省、洲  514*/
		PROVINCE(514), 
		
		/** PlaceType：城市 515 */
		CITY(515), 
		;
		
		
		private final long index;
		private PlaceType(final long index) {
			this.index = index;
		}
		public long toLong() {
			return index;
		}
	}
}
