package com.jason.framework.orm.elasticsearch;


import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jason.framework.AbstractTestBase;
import com.jason.framework.orm.elasticsearch.constant.IndexConstant.Index;
import com.jason.framework.orm.elasticsearch.domain.IndexData;



/**
 * 索引服务类测试类
 * @author Jason
 * @date 2014-10-10
 */
public class IndexRepositoryTests  extends AbstractTestBase {
	

    @Autowired
    private IndexRepository indexRepository;
    
    @Test
    public void testValues(){
    	Index[] index = Index.values();
    	for (Index i:index) {
    		System.out.println(i);
		}
    }
    
    @Test
    public void testFindIndexAlias(){
    	List<IndexData> indexs = indexRepository.findIndexAlias();
    	for (IndexData index:indexs) {
			System.out.println("index:"+index.getIndex()+" mappings:"+index.getMappings());
		}
    }
    
    @Test
    public void testDeleteIndex(){
    	String indexName = "test";
    	boolean fla = indexRepository.deleteIndex(indexName);
    	System.out.println("删除索引结果："+fla);
    }
    

}
