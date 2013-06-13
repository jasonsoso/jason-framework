package com.jason.framework.constants;

import java.io.Serializable;

/**
 * photo conf
 * 
 * @author Jason
 * @date 2013-2-12 上午08:37:43
 */
public class PhotoConf implements Serializable {

	private static final long serialVersionUID = 1L;

	private PhotoConf() {
	}

	private long photoMaxSize = 5 * 1024 * 1024;
	private String photoPath = "resources/upload";
	private String photoTypes = "jpg,jpeg,bmp,gif,png,ico";
	private String photoThumbType = ThumbType.THUMB_SOURCE.asType();

	/**
	 * Thumb Type
	 * 
	 * @author Jason
	 * @date 2013-2-12 上午08:43:28
	 */
	public static enum ThumbType {

		THUMB_SOURCE("source"), 
		THUMB_180_180("180_180"), 
		THUMB_100_100("100_100"), 
		THUMB_50_50("50_50"), 
		THUMB_30_30("30_30");

		private final String type;

		private ThumbType(final String type) {
			this.type = type;
		}

		public String asType() {
			return type;
		}
	}

	public long getPhotoMaxSize() {
		return photoMaxSize;
	}

	public PhotoConf setPhotoMaxSize(long photoMaxSize) {
		this.photoMaxSize = photoMaxSize;
		return this;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public PhotoConf setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
		return this;
	}

	public String getPhotoTypes() {
		return photoTypes;
	}

	public PhotoConf setPhotoTypes(String photoTypes) {
		this.photoTypes = photoTypes;
		return this;
	}

	public String getPhotoThumbType() {
		return photoThumbType;
	}

	public PhotoConf setPhotoThumbType(String photoThumbType) {
		this.photoThumbType = photoThumbType;
		return this;
	}

}
