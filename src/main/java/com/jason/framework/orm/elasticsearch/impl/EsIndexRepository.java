package com.jason.framework.orm.elasticsearch.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jason.framework.orm.elasticsearch.IndexRepository;
import com.jason.framework.orm.elasticsearch.domain.IndexData;
import com.jason.framework.orm.elasticsearch.support.ESRepositorySupport;

@Repository
public class EsIndexRepository extends ESRepositorySupport implements IndexRepository {

	@Override
	public List<IndexData> findIndexAlias() {
		return super.findIndexAlias();
	}

	@Override
	public boolean deleteIndex(String indexName) {
		return super.deleteIndex(indexName);
	}

}
