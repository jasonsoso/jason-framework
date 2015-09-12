package com.jason.framework.orm.hadoop;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.junit.Test;

/**
 * HDFS 学习
 * @author Jason
 *
 */
public class HDFSTest {
	
	public FileSystem getFileSystem() throws IOException{
		String uri = "hdfs://hadoop-yarn.jasonsoso.com:8020";
		Configuration config = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(uri), config);
		return fs;
	}
	@Test
	public void testListStatus() throws IOException {
		FileSystem fs = getFileSystem();
		// 列出目录所有文件
		FileStatus[] statuses = fs.listStatus(new Path("/data"));
		for (FileStatus status : statuses) {
			String type = status.isDirectory()?"目录":"文件";
			String name = status.getPath().getName();
			System.out.println(status+": 类型："+type+" 文件名："+name);
		}
	}
	@Test
	public void testCreate() throws IOException {
		FileSystem fs = getFileSystem();
		// 创建新文件
		FSDataOutputStream os = fs.create(new Path("/data/hdfs_test.txt"));
		os.write("测试HDFS第一条\r\n".getBytes());
		os.write("测试HDFS第二条\r\n".getBytes());
		os.flush();
		os.close();
	}
	@Test
	public void testCreate2() throws IOException {
		FileSystem fs = getFileSystem();
		//上传文件
		FSDataOutputStream fSDataOutputStream = fs.create(new Path("/data/README.md"));
		FileInputStream fileInputStream = new FileInputStream("C:/Users/tanjianna/git/jason-framework/README.md");
		IOUtils.copyBytes(fileInputStream, fSDataOutputStream, 1024, true);
	}
	
	@Test
	public void testMkdirs() throws IOException {
		FileSystem fs = getFileSystem();
		
		//创建目录
		fs.mkdirs(new Path("/new"));
		
		//删除目录
		fs.delete(new Path("/new"),true);
	}
	@Test
	public void testOpen() throws IOException {
		FileSystem fs = getFileSystem();
		
		// 读取文件
		InputStream is = fs.open(new Path("/data/hdfs_test.txt"));
		IOUtils.copyBytes(is, System.out, 1024, true);
	}
}
