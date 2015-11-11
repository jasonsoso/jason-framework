package ${packageName}${moduleName}.domain${subModuleName};

<#list setColumns as setColumns>
import ${setColumns};
</#list>
import com.jason.framework.domain.MyBatisDomainObject;

/**
 * ${functionName} Entity
 * @author ${classAuthor}
 * @date ${classVersion}
 */
public class ${ClassName} extends MyBatisDomainObject{
	private static final long serialVersionUID = 1L;
	
	<#list columns as columns>
	<#if columns.name!="id">
	private ${columns.jt} ${columns.property};
	</#if>
	</#list>
	
	
	<#list columns as columns>
	<#if columns.name!="id">
	public ${ClassName} set${columns.bigProperty}(${columns.jt} ${columns.property}) {
		this.${columns.property} = ${columns.property};
		return this;
	}
	public ${columns.jt} get${columns.bigProperty}() {
		return ${columns.property};
	}
	
	</#if>
	</#list>
}
