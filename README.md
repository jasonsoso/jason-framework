
## Introduction ##
JasonFramewok is a Spring Framework based JavaEE application reference architecture.    
JasonFramewok是一个基于Spring框架JavaEE的应用框架。


## Design ##

请移步到[Jason技术流水账](http://www.jasonsoso.com/tech/201502/design-1/ "Jason技术流水账")


## Clone and install ##
`git clone https://github.com/jasonsoso/jason-framework.git`   
`cd jason-framework`   
`mvn clean compile install -Dmaven.test.skip=true`   
`mvn deploy`   //Publish to nexus ,not the Required-election    


## Code Generator ##
1. 修改配置文件/src/test/resources/META-INF/config/application.properties中的数据库连接；
2. 修改配置文件/src/test/resources/META-INF/config/framework.properties中的generate.*
3. 修改模板/src/test/java/com/jason/framework/generate/template；
4. 执行`mvn test -Dtest=GenerateTest`，生成代码在/src/out目录下


## Use (Dependency Information) ##

	<dependency>   
		<groupId>com.jason</groupId>   
		<artifactId>framework</artifactId>   
		<version>1.0.2</version>   
	</dependency>   
