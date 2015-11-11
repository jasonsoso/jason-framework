package ${packageName}.${moduleName}.web${subModuleName};


import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.base.Objects;
import com.jason.framework.domain.EntityUtils;
import com.jason.framework.orm.Page;
import com.jason.framework.web.support.ControllerSupport;
import ${packageName}.${moduleName}.domain${subModuleName}.${ClassName};
import ${packageName}.${moduleName}.service${subModuleName}.${ClassName}Service;

/**
 * ${functionName} 控制层
 * @author ${classAuthor}
 * @date ${classVersion}
 */
@Controller
@RequestMapping(value = "/${urlPrefix}")
public class ${ClassName}Controller extends ControllerSupport {
	
	private static final String REDIRECT_LIST = "redirect:/${className}/list/";
	private static final String FORM = "${className}/form";
	private static final String LIST = "${className}/list";
	
	@Autowired
	private ${ClassName}Service ${className}Service;
	
	
	@RequestMapping(value = "/list/", method = GET)
	public String list(Page<${ClassName}> page, Model model) {
		
		page.setOrderBy("id").setOrder(Page.DESC);
		
		page = ${className}Service.queryPage(page);
		model.addAttribute("page",page);
		return LIST;
	}

	@RequestMapping(value = "/create/", method = GET)
	public String create(Model model) {
		model.addAttribute(new ${ClassName}());
		return FORM;
	}

	@RequestMapping(value = "/create/", method = POST)
	public String create(@Valid ${ClassName} entity, BindingResult result,RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			error(redirectAttributes,"创建${functionName}失败，请核对数据后重试");
			return REDIRECT_LIST;
		}
		${className}Service.save(entity);
		success(redirectAttributes,"${functionName}创建成功");
		return REDIRECT_LIST;
	}

	@RequestMapping(value = "/{id}/edit/", method = GET)
	public String edit(@PathVariable("id") Long id, Model model) {
		${ClassName} entity = ${className}Service.get(id);
		model.addAttribute(entity).addAttribute("_method", "PUT");
		return FORM;
	}

	@RequestMapping(value = "/{id}/edit/", method = PUT)
	public String edit(@PathVariable("id") Long id, HttpServletRequest request,RedirectAttributes redirectAttributes) {
		try {

			${ClassName} entity = ${className}Service.get(id);
			bind(request, entity);
			Assert.isTrue(Objects.equal(id, entity.getId()), "编辑Id不相符");
			
			${className}Service.update(entity);
			success(redirectAttributes,"${functionName}修改成功");
		} catch (Exception e) {
			error(redirectAttributes,"${functionName}修改失败，请核对数据重试",e);
		}
		return REDIRECT_LIST;
	}

	@RequestMapping(value = "/{id}/delete/", method = DELETE)
	public String delete(@PathVariable("id") Long id) {
		${className}Service.delete(${className}Service.get(id));
		return REDIRECT_LIST;
	}

	@RequestMapping(value = "/delete/", method = DELETE)
	public String delete(HttpServletRequest request) {
		for (String item : EntityUtils.nullSafe(request.getParameterValues("items"), new String[] {})) {
			delete(EntityUtils.toLong(item));
		}
		return REDIRECT_LIST;
	}
	
}
