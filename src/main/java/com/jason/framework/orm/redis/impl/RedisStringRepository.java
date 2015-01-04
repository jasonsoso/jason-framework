package com.jason.framework.orm.redis.impl;

import org.springframework.stereotype.Repository;

import com.jason.framework.orm.redis.StringRepository;
import com.jason.framework.orm.redis.support.StringJedisRepositorySupport;

@Repository
public class RedisStringRepository extends StringJedisRepositorySupport implements StringRepository {
	
}
