package com.qatang.team.manager.controller.role;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.qatang.team.enums.EnableDisableStatus;
import com.qatang.team.manager.core.constants.GlobalConstants;
import com.qatang.team.manager.core.controller.AbstractController;
import com.qatang.team.manager.entity.resource.ResourceEntity;
import com.qatang.team.manager.entity.role.RoleEntity;
import com.qatang.team.manager.form.role.RoleForm;
import com.qatang.team.manager.query.role.RoleSearchable;
import com.qatang.team.manager.service.resource.ResourceService;
import com.qatang.team.manager.service.role.RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

/**
 * @author jinsheng
 * @since 2016-05-12 13:13
 */
@Controller
@RequestMapping("/role")
@SessionAttributes("roleSearchable")
public class RoleController extends AbstractController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private ResourceService resourceService;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.setAutoGrowCollectionLimit(5000);
    }

    @RequiresPermissions("arsenal:role:list")
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(RoleSearchable roleSearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable, ModelMap modelMap) {
        roleSearchable.setPageable(pageable);
        Page<RoleEntity> page = roleService.find(roleSearchable);
        modelMap.addAttribute("page", page);
        return "role/list";
    }

    @RequiresPermissions("arsenal:role:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(@ModelAttribute RoleForm roleForm, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".roleForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        if (Objects.isNull(roleForm.getRole())) {
            roleForm.setRole(new RoleEntity());
        }
        modelMap.put("action", "create");
        return "role/edit";
    }

    @RequiresPermissions("arsenal:role:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(RoleForm roleForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(roleForm);
            return "redirect:/role/create";
        }
        RoleEntity roleEntity = roleForm.getRole();
        roleService.save(roleEntity);
        return "result";
    }

    @RequiresPermissions("arsenal:role:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable Long id, @ModelAttribute RoleForm roleForm, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".roleForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        RoleEntity roleEntity = roleService.get(id);
        if (Objects.isNull(roleEntity)) {
            logger.error("修改角色,未查询[id={}]的角色信息", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据!");
            return "redirect:/error";
        }
        roleForm.setRole(roleEntity);
        modelMap.put("action", "update");
        return "role/edit";
    }

    @RequiresPermissions("arsenal:role:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(RoleForm roleForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(roleForm);
            return "redirect:/role/update/" + roleForm.getRole().getId();
        }
        RoleEntity role = roleForm.getRole();
        RoleEntity roleEntity = roleService.get(role.getId());
        roleEntity.setIdentifier(role.getIdentifier());
        roleEntity.setName(role.getName());
        roleEntity.setDescription(role.getDescription());
        roleEntity.setIsDefault(role.getIsDefault());
        roleService.update(roleEntity);
        return "result";
    }

    @RequiresPermissions("arsenal:role:view")
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable Long id, ModelMap modelMap) {
        modelMap.put("role", roleService.get(id));
        return "role/view";
    }

    @RequiresPermissions("arsenal:role:enable")
    @RequestMapping(value = "/enable/{id}", method = RequestMethod.GET)
    public String enable(@PathVariable Long id) {
        roleService.updateValid(id, EnableDisableStatus.ENABLE);
        return "redirect:/role/list";
    }

    @RequiresPermissions("arsenal:role:disable")
    @RequestMapping(value = "/disable/{id}", method = RequestMethod.GET)
    public String disable(@PathVariable Long id) {
        roleService.updateValid(id, EnableDisableStatus.DISABLE);
        return "redirect:/role/list";
    }

    @RequiresPermissions("arsenal:role:resource")
    @RequestMapping(value = "/resource/allot/{id}", method = RequestMethod.GET)
    public String allotResource(@PathVariable Long id, @ModelAttribute RoleForm roleForm, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".roleForm"), modelMap.get(BINDING_RESULT_KEY));
        }

        roleForm.setRole(roleService.get(id));

        List<ResourceEntity> resources = resourceService.findAll();

        resources.forEach(resource -> {
            if (resource.getParentId() != null) {
                resource.setParent(resourceService.get(resource.getParentId()));
            }
        });

        List<Long> resourceIdList = roleService.findResourceIdByRoleIdIn(Lists.newArrayList(id));
        List<ResourceEntity> checkedResources = resourceService.findAll(resourceIdList);

        List<Map<String, Object>> resourcesJsonList = new ArrayList<>();
        for (ResourceEntity resource : resources) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", resource.getId());
            map.put("pId", resource.getParent() == null ? 0 : resource.getParent().getId());
            map.put("name", resource.getName());
            if (checkedResources.contains(resource)) {
                map.put("checked", true);
            }
            resourcesJsonList.add(map);
        }

        modelMap.addAttribute("resources", JSON.toJSONString(resourcesJsonList));
        return "role/resource/allot";
    }

    @RequiresPermissions("arsenal:role:resource")
    @RequestMapping(value = "/resource/allot", method = RequestMethod.POST)
    public String allotResource(RoleForm roleForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(roleForm);
            return "redirect:/role/resource/allot/" + roleForm.getRole().getId();
        }
        roleService.bindResource(roleForm.getRole().getId(), roleForm.getResourceIdList());
        return "result";
    }
}
