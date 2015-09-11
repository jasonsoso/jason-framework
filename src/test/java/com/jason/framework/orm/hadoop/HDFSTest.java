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

public class HDFSTest {

	@Test
	public void test() throws IOException {
		
		String uri = "hdfs://hadoop-yarn.jasonsoso.com:8020";
		Configuration config = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(uri), config);
		
		// 列出目录所有文件
		FileStatus[] statuses = fs.listStatus(new Path("/data"));
		for (FileStatus status : statuses) {
			String type = status.isDirectory()?"目录":"文件";
			String name = status.getPath().getName();
			System.out.println(status+": 类型："+type+" 文件名："+name);
		}
		// 创建新文件
		FSDataOutputStream os = fs.create(new Path("/data/hdfs_test.txt"));
		os.write("测试HDFS第一条\r\n".getBytes());
		os.write("测试HDFS第二条\r\n".getBytes());
		os.flush();
		os.close();
		
		//上传文件
		//FSDataOutputStream fSDataOutputStream = fs.create(new Path("/data/up.txt"));
		//FileInputStream fileInputStream = new FileInputStream("up.txt");
		//IOUtils.copyBytes(fileInputStream, fSDataOutputStream, 1024, true);
		
		//创建目录
		fs.mkdirs(new Path("/new"));
		
		//删除目录
		fs.delete(new Path("/new"),true);
		
		
		// 读取文件
		InputStream is = fs.open(new Path("/data/hdfs_test.txt"));
		IOUtils.copyBytes(is, System.out, 1024, true);
	}
}
