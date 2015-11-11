package ${packageName}${moduleName}.repository${subModuleName};

import java.util.List;
import com.jason.framework.orm.Page;
import ${packageName}${moduleName}.domain${subModuleName}.${ClassName};

/**
 * ${functionName} 持久层接口类
 * @author ${classAuthor}
 * @date ${classVersion}
 */
public interface ${ClassName}Repository {
	${ClassName} get(Long id);

	List<${ClassName}> query(Object object);

	Page<${ClassName}> queryPage(Page<${ClassName}> page);

	void update(${ClassName} entity);
	
	void delete(${ClassName} entity);

	void save(${ClassName} entity);
}
