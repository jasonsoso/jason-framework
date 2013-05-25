package com.jason.framework.util;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class IOUtils {

	/**
	 * @param in
	 * @param out
	 * @throws IOException
	 */
	public static void piping(InputStream in, OutputStream out) throws IOException {
		piping(in, out, new byte[4 * 1024]);
	}

	/**
	 * @param in
	 * @param out
	 * @param buf
	 * @throws IOException
	 */
	public static void piping(InputStream in, OutputStream out, byte[] buf) throws IOException {
		int bytesRead = 0;

		while ((bytesRead = in.read(buf)) != -1) {
			out.write(buf, 0, bytesRead);
			out.flush();
		}
	}

	/**
	 * @param closeables
	 * @throws IOException
	 */
	public static void free(Closeable... closeables) throws IOException {
		for (Closeable closeable : closeables) {

			if (null != closeable) {
				closeable.close();
			}
		}
	}

	/**
	 * @param closeables
	 */
	public static void freeQuietly(Closeable... closeables) {

		for (Closeable closeable : closeables) {

			try {

				free(closeable);
			} catch (Exception e) {
				// ingore this exception
			}
		}
	}

	private IOUtils() {
	}
}
