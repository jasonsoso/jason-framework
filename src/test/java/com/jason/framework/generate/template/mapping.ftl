<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${packageName}${moduleName}.domain${subModuleName}.${ClassName}">

<resultMap type="${packageName}${moduleName}.domain${subModuleName}.${ClassName}" id="${className}Result">
<#list columns as columns>
<#if columns.name=="id">
	<id property="${columns.property}" column="${columns.name}"/>
</#if>
<#if columns.name!="id">
	<result property="${columns.property}" column="${columns.name}"/>
</#if>
</#list>
</resultMap>

<sql id="commonSelect">
	select 
		self.*
	from ${dbTable} self
</sql>

<insert id="save" keyProperty="id" useGeneratedKeys="true" >
	insert into ${dbTable}(
		<#list columns as columns><#if columns_index == 0>${columns.name}</#if><#if columns_index != 0>,${columns.name}</#if></#list>
	) values(
		<#list columns as columns><#if columns_index == 0>${r"#{"}${columns.property}${r"}"}</#if><#if columns_index != 0>,${r"#{"}${columns.property}${r"}"}</#if></#list>
	)
</insert>
<update id="update" >
	update ${dbTable} set 
		<#list columns as columns><#if columns_index == 0>${columns.name}=${r"#{"}${columns.property}${r"}"}</#if><#if columns_index != 0>,${columns.name}=${r"#{"}${columns.property}${r"}"}</#if></#list>
	where id=${r"#{id}"}
</update>

<delete id="delete" >
	delete from ${dbTable} where id=${r"#{id}"}
</delete>

<select id="get" resultMap="${className}Result">
	<include refid="commonSelect"/>
	where self.id=${r"#{id}"}
</select>

<select id="query" resultMap="${className}Result">
	<include refid="commonSelect"/>
</select>

<select id="queryPage" resultMap="${className}Result">
	<include refid="commonSelect"/>
	<!-- <where>
		<if test="params.name != null and params.name !=''">
			self.name like concat(concat('%',${r"#{params.name}"}),'%')
		</if>
	</where> -->
</select>



</mapper>
