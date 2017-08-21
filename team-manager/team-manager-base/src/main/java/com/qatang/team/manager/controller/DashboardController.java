package com.qatang.team.manager.controller;

import com.qatang.team.manager.core.controller.AbstractController;
import com.zhangyu.ucenter.api.bean.user.UserInfo;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author jinsheng
 * @since 2016-04-27 11:42
 */
@Controller
public class DashboardController extends AbstractController {

    @RequestMapping("/dashboard")
    public String dashboard(ModelMap modelMap) {
        UserInfo currentUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        modelMap.addAttribute("currentUser", currentUser);
        return "dashboard";
    }
}
