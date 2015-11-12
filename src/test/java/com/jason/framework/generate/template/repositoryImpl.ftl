package ${packageName}${moduleName}.repository${subModuleName}.impl;

import org.springframework.stereotype.Repository;

import com.jason.framework.orm.mybatis.MybatisRepositorySupport;
import ${packageName}${moduleName}.repository${subModuleName}.${ClassName}Repository;
import ${packageName}${moduleName}.domain${subModuleName}.${ClassName};

/**
 * ${functionName} 持久层实现类
 * @author ${classAuthor}
 * @date ${classVersion}
 */
@Repository
public class ${ClassName}RepositoryImpl extends MybatisRepositorySupport<Long, ${ClassName}> implements ${ClassName}Repository {

	@Override
	protected String getNamespace() {
		return "${packageName}${moduleName}.domain${subModuleName}.${ClassName}";
	}
}
