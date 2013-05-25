package com.jason.framework.util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * jdk 图片处理
 * @author Jason
 * @date 2013-2-4 下午11:36:07
 */
public final class PhotoHelper {
	private static final  Logger logger = LoggerFactory.getLogger(PhotoHelper.class);


	/**
	 * 创建缩略图
	 * @param in
	 * @param thumbnail
	 * @param width
	 * @param height
	 */
	public static void createThumbnail(InputStream in, String thumbnail, int width, int height) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(in);

			int imageWidth = image.getWidth();
			int imageHeight = image.getHeight();
			double ratioV = (double) width / (double) imageWidth;
			double ratioH = (double) height / (double) imageHeight;

			if (ratioV >= 1.0 && ratioH >= 1.0) {
				createThumbnailInternal(image, thumbnail, imageWidth, imageHeight);
				return;
			}

			if (ratioV <= 0 || ratioH <= 0) {
				createThumbnailInternal(image, thumbnail, imageWidth, imageHeight);
				return;
			}

			double ratio = Math.min(ratioH, ratioV);
			int widthInt = (int) (ratio * imageWidth);
			int heightInt = (int) (ratio * imageHeight);

			createThumbnailInternal(image, thumbnail, widthInt, heightInt);
		} catch (Exception e) {
			logger.error("createThumbnail error！", e);
		}
	}

	private static void createThumbnailInternal(BufferedImage image, String thumbnail, int imageWidth, int imageHeight) throws IOException {
		Assert.hasText(thumbnail, "thumbnail must has text");
		
		BufferedImage thumb = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = thumb.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(image, 0, 0, imageWidth, imageHeight, null);
		g.dispose();

		String format = StringsHelper.suffix(thumbnail);
		ImageIO.write(thumb, StringUtils.isBlank(format) ? "jpg" : format, new File(thumbnail));
	}
	
	/**
	 * 打水印
	 * @param dest
	 * @param watermark
	 */
	public static void watermark(File dest, File watermark) {
		BufferedImage destImage = null;
		try {
			destImage = ImageIO.read(dest);
			BufferedImage watermarkImage = ImageIO.read(watermark);

			int destImageWidth = destImage.getWidth();
			int destImageHeight = destImage.getHeight();

			int pointX = Math.max(destImageWidth - watermarkImage.getWidth(), 0);
			int pointY = Math.max(destImageHeight - watermarkImage.getHeight(), 0);
			int width = Math.min(destImageWidth, watermarkImage.getWidth());
			int height = Math.min(destImageHeight, watermarkImage.getHeight());

			Graphics g = destImage.getGraphics();
			g.drawImage(watermarkImage, pointX, pointY, width, height, null);
			g.dispose();

			String format = StringsHelper.suffix(dest.getName());
			ImageIO.write(destImage, StringUtils.isBlank(format) ? "jpg" : format, dest);
		} catch (IOException e) {
			logger.error("watermark error！", e);
		}
	}

	/**
	 * 打水印
	 * @param dest
	 * @param watermark
	 */
	public static void watermark(String dest, String watermark) {
		watermark(new File(dest), new File(watermark));
	}

}
