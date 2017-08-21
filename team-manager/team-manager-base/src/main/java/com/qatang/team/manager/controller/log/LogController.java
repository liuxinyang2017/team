package com.qatang.team.manager.controller.log;


import com.qatang.team.manager.core.constants.GlobalConstants;
import com.qatang.team.manager.core.controller.AbstractController;
import com.qatang.team.manager.entity.log.LogEntity;
import com.qatang.team.manager.query.log.LogSearchable;
import com.qatang.team.manager.service.log.LogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author jinsheng
 * @since 2016-05-13 14:35
 */
@Controller
@RequestMapping("/log")
public class LogController extends AbstractController {

    @Autowired
    private LogService logService;

    @RequiresPermissions("arsenal:log:list")
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(LogSearchable logSearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, ModelMap modelMap) {
        logSearchable.setPageable(pageable);
        Page<LogEntity> page = logService.find(logSearchable);
        modelMap.addAttribute("page", page);
        return "log/list";
    }

    @RequiresPermissions("arsenal:log:view")
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable Long id, ModelMap modelMap) {
        modelMap.put("log", logService.get(id));
        return "log/view";
    }
}
