package com.jason.framework.orm.redis.impl;

import org.springframework.stereotype.Repository;

import com.jason.framework.orm.redis.HashMapRepository;
import com.jason.framework.orm.redis.support.HashMapJedisRepositorySupport;

@Repository
public class RedisHashMapRepository extends HashMapJedisRepositorySupport implements HashMapRepository {
	
}
