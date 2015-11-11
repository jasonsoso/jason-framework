package ${packageName}.${moduleName}.service${subModuleName};

import java.util.List;

import com.jason.framework.orm.Page;
import ${packageName}.${moduleName}.domain${subModuleName}.${ClassName};

/**
 * ${functionName} 业务逻辑接口类
 * @author ${classAuthor}
 * @date ${classVersion}
 */
public interface ${ClassName}Service {

	void update(${ClassName} entity);

	void delete(${ClassName} entity);

	void save(${ClassName} entity);
	
	${ClassName} get(Long id);

	List<${ClassName}> query(Object object);

	Page<${ClassName}> queryPage(Page<${ClassName}> page);
}
