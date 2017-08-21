package com.qatang.team.manager.controller.resource;


import com.qatang.team.enums.EnableDisableStatus;
import com.qatang.team.enums.resouce.ResourceType;
import com.qatang.team.manager.core.constants.GlobalConstants;
import com.qatang.team.manager.core.controller.AbstractController;
import com.qatang.team.manager.entity.resource.ResourceEntity;
import com.qatang.team.manager.form.resource.ResourceForm;
import com.qatang.team.manager.service.resource.ResourceService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;

/**
 * @author jinsheng
 * @since 2016-05-12 18:21
 */
@Controller
@RequestMapping("/resource")
public class ResourceController extends AbstractController {

    @Autowired
    private ResourceService resourceService;

    @ModelAttribute("resourceTypeList")
    public List<ResourceType> getResourceTypeList() {
        return ResourceType.list();
    }

    @RequiresPermissions("arsenal:resource:list")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(ResourceForm resourceForm, ModelMap modelMap) {
        Long parentId = null;
        if (resourceForm.getResource() != null && resourceForm.getResource().getParent() != null) {
            parentId = resourceForm.getResource().getParent().getId();
        }

        List<ResourceEntity> resourceList = resourceService.findByParentId(parentId);

        ResourceEntity parentResource = null;
        if (parentId != null) {
            parentResource = resourceService.get(parentId);
        }

        modelMap.addAttribute("parentResource", parentResource);
        modelMap.addAttribute("resourceList", resourceList);
        return "resource/list";
    }

    @RequiresPermissions("arsenal:resource:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(@ModelAttribute ResourceForm resourceForm, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".resourceForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        if (Objects.isNull(resourceForm.getResource())) {
            resourceForm.setResource(new ResourceEntity());
        }
        modelMap.put("action", "create");
        return "resource/edit";
    }

    @RequiresPermissions("arsenal:resource:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(ResourceForm resourceForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(resourceForm);
            return "redirect:/resource/create";
        }
        ResourceEntity resource = resourceForm.getResource();
        if (resource.getParent() == null || resource.getParent().getId() == null) {
            resource.setParent(null);
            resource.setTreeLevel(0);
        } else {
            ResourceEntity parentResource = resourceService.get(resource.getParent().getId());

            int treeLevel = parentResource.getTreeLevel() + 1;
            resource.setTreeLevel(treeLevel);
            if (treeLevel == GlobalConstants.DEFAULT_TREE_LEVEL - 1) {
                resource.setEnd(true);
            }
            resource.setParentId(parentResource.getId());
        }
        if (resource.getPriority() == null) {
            resource.setPriority(0);
        }
        resourceService.save(resource);
        return "result";
    }

    @RequiresPermissions("arsenal:resource:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable Long id, @ModelAttribute ResourceForm resourceForm, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".resourceForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        ResourceEntity resourceEntity = resourceService.get(id);
        if (Objects.isNull(resourceEntity)) {
            logger.error("修改资源,未查询[id={}]的资源信息", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据!");
            return "redirect:/error";
        }
        resourceForm.setResource(resourceEntity);
        modelMap.put("action", "update");
        return "resource/edit";
    }

    @RequiresPermissions("arsenal:resource:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(ResourceForm resourceForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(resourceForm);
            return "redirect:/resource/update/" + resourceForm.getResource().getId();
        }
        ResourceEntity resource = resourceForm.getResource();

        ResourceEntity resourceEntity = resourceService.get(resource.getId());
        resourceEntity.setIdentifier(resource.getIdentifier());
        resourceEntity.setName(resource.getName());
        resourceEntity.setUrl(resource.getUrl());
        resourceEntity.setPriority(resource.getPriority());
        resourceEntity.setType(resource.getType());
        resourceEntity.setResourceIcon(resource.getResourceIcon());
        resourceEntity.setMemo(resource.getMemo());

        resourceService.update(resourceEntity);
        return "result";
    }

    @RequiresPermissions("arsenal:resource:view")
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable Long id, ModelMap modelMap) {
        modelMap.put("resource", resourceService.get(id));
        return "resource/view";
    }

    @RequiresPermissions("arsenal:resource:enable")
    @RequestMapping(value = "/enable/{id}", method = RequestMethod.GET)
    public String enable(@PathVariable Long id) {
        resourceService.updateValid(id, EnableDisableStatus.ENABLE);
        return "redirect:/resource/list";
    }

    @RequiresPermissions("arsenal:resource:disable")
    @RequestMapping(value = "/disable/{id}", method = RequestMethod.GET)
    public String disable(@PathVariable Long id) {
        resourceService.updateValid(id, EnableDisableStatus.DISABLE);
        return "redirect:/resource/list";
    }
}
