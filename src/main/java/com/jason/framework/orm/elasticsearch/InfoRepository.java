package com.jason.framework.orm.elasticsearch;

import java.util.List;

import com.jason.framework.orm.Page;
import com.jason.framework.orm.elasticsearch.domain.Info;

/**
 * 资讯
 * @author Jason
 * @date 2014-8-5
 */
public interface InfoRepository {

	Info get(String indexId);

	void index(Info entity);
	
	void index(List<Info> list);
	
	void delete(String indexId);
	
	void deleteAll();
	
	/**
	 * 根据关键字进行搜索
	 * @param queryString 关键字
	 * @param size 记录条数
	 * @return List<Article>
	 */
	List<Info> query(String queryString,int size);

	/**
	 * 根据关键字分页搜索
	 * @param page 分页
	 * @param queryString 关键字
	 * @return Page<Info>
	 */
	Page<Info> queryPage(Page<Info> page, String queryString);
	/**
	 * 初始化索引Mapping
	 */
	void initIndexMapping();
	
	List<Info> moreLikeThis(String indexId,int size);
	
}
