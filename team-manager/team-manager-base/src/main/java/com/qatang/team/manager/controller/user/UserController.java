package com.qatang.team.manager.controller.user;

import com.google.common.collect.Lists;
import com.qatang.team.manager.core.constants.GlobalConstants;
import com.qatang.team.manager.core.controller.AbstractController;
import com.qatang.team.manager.core.exception.ValidateFailedException;
import com.qatang.team.manager.form.user.UserForm;
import com.qatang.team.manager.query.user.UserSearchable;
import com.qatang.team.manager.service.role.RoleService;
import com.qatang.team.manager.service.user.UserService;
import com.qatang.team.manager.shiro.authentication.PasswordHelper;
import com.qatang.team.manager.validator.user.PasswordChangeValidator;
import com.zhangyu.ucenter.api.bean.user.QUserInfo;
import com.zhangyu.ucenter.api.bean.user.UserInfo;
import com.zhangyu.ucenter.api.service.user.UserInfoApiService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author jinsheng
 * @since 2016-04-26 16:56
 */
@Controller
@RequestMapping("/user")
@SessionAttributes("userSearchable")
public class UserController extends AbstractController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserInfoApiService userInfoApiService;

    @Autowired
    private PasswordHelper passwordHelper;

    @Autowired
    private PasswordChangeValidator passwordChangeValidator;


    @RequiresPermissions("arsenal:user:list")
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(UserSearchable userSearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = QUserInfo.createdTime, direction = Sort.Direction.DESC) Pageable pageable, ModelMap modelMap) {
        ApiResponse<UserInfo> apiResponse = userService.findAll(userSearchable, pageable, GlobalConstants.SYSTEM_ARSENAL_IDENTIFIER);
        Page<UserInfo> page = new PageImpl<>(Lists.newArrayList(apiResponse.getPagedData()), pageable, apiResponse.getTotal());
        modelMap.addAttribute("page", page);
        return "user/list";
    }

    @RequiresPermissions("arsenal:user:view")
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable Long id, ModelMap modelMap) {
        modelMap.put("userInfo", userInfoApiService.findOne(id));
        return "user/view";
    }

    @RequiresPermissions("arsenal:user:changePassword")
    @RequestMapping(value = "/password/change", method = RequestMethod.GET)
    public String changePassword(@ModelAttribute UserForm userForm, ModelMap modelMap) {
        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        if (userInfo == null) {
            return "redirect:/signin";
        }
        Long userId = userInfo.getId();
        userInfo = userInfoApiService.findOne(userId);
        if (userInfo == null) {
            logger.error("修改密码失败：未查询到id={}的用户", userId);
            return "redirect:/signin";
        }

        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".userForm"), modelMap.get(BINDING_RESULT_KEY));
        }

        userForm.setUserInfo(userInfo);
        return "user/passwordChange";
    }

    @RequiresPermissions("arsenal:user:changePassword")
    @RequestMapping(value = "/password/change", method = RequestMethod.POST)
    public String changePassword(UserForm userForm, BindingResult result, RedirectAttributes redirectAttributes) {
        try {
            passwordChangeValidator.validate(userForm);
        } catch (ValidateFailedException e) {
            logger.error(e.getMessage(), e);
            result.addError(new ObjectError(e.getField(), e.getMessage()));
        }

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(userForm);
            return "redirect:/user/password/change";
        }
        UserInfo currentUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        currentUser.setPassword(userForm.getNewPassword());
        passwordHelper.encryptPassword(currentUser);
        userInfoApiService.update(currentUser);

        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE_KEY, "修改密码成功！");
        return "result";
    }

    @RequiresPermissions("arsenal:user:role")
    @RequestMapping(value = "/role/allot/{id}", method = RequestMethod.GET)
    public String allotRole(@PathVariable Long id, @ModelAttribute UserForm userForm, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".userForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        userForm.setUserInfo(userInfoApiService.findOne(id));
        userForm.setRoleIdList(userService.findRoleIdByUserId(id));
        modelMap.addAttribute("roles", roleService.findAll());
        return "user/role/allot";
    }

    @RequiresPermissions("arsenal:user:role")
    @RequestMapping(value = "/role/allot", method = RequestMethod.POST)
    public String allotRole(UserForm userForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(userForm);
            return "redirect:/user/role/allot/" + userForm.getUserInfo().getId();
        }
        userService.bindRole(userForm.getUserInfo().getId(), userForm.getRoleIdList());
        return "result";
    }

}
