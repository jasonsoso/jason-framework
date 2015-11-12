package ${packageName}${moduleName}.service${subModuleName}.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jason.framework.orm.Page;
import ${packageName}${moduleName}.domain${subModuleName}.${ClassName};
import ${packageName}${moduleName}.repository${subModuleName}.${ClassName}Repository;
import ${packageName}${moduleName}.service${subModuleName}.${ClassName}Service;


/**
 * ${functionName} 业务逻辑实现类
 * @author ${classAuthor}
 * @date ${classVersion}
 */
@Transactional
@Service
public class ${ClassName}ServiceImpl  implements ${ClassName}Service {

	@Autowired
	private ${ClassName}Repository ${className}Repository;

	@Override
	public ${ClassName} get(Long id) {
		return ${className}Repository.get(id);
	}

	@Override
	public List<${ClassName}> query(Object object) {
		return ${className}Repository.query(object);
	}

	@Override
	public Page<${ClassName}> queryPage(Page<${ClassName}> page) {
		return ${className}Repository.queryPage(page);
	}

	@Override
	public void update(${ClassName} entity) {
		${className}Repository.update(entity);
	}

	@Override
	public void delete(${ClassName} entity) {
		${className}Repository.delete(entity);
	}

	@Override
	public void save(${ClassName} entity) {
		${className}Repository.save(entity);
	}
	
	
}
