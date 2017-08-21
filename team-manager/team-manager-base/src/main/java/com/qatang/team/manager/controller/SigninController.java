package com.qatang.team.manager.controller;


import com.qatang.team.manager.core.controller.AbstractController;
import com.qatang.team.manager.form.user.UserForm;
import com.zhangyu.ucenter.api.bean.user.UserInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;

/**
 * @author jinsheng
 * @since 2016-04-27 11:39
 */
@Controller
@RequestMapping("/signin")
public class SigninController extends AbstractController {

    @RequestMapping(method = RequestMethod.GET)
    public String signinPage(@ModelAttribute UserForm userForm, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".userForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        if (Objects.isNull(userForm.getUserInfo())) {
            userForm.setUserInfo(new UserInfo());
        }
        return "signin";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String signin(UserForm userForm, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(BINDING_RESULT_KEY, result.getAllErrors());
            redirectAttributes.addFlashAttribute(userForm);
            return "redirect:/signin";
        }

        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userForm.getUserInfo().getUsername(), userForm.getUserInfo().getPassword());
        usernamePasswordToken.setRememberMe(userForm.isRememberMe());

        Subject subject = SecurityUtils.getSubject();

        try {
            subject.login(usernamePasswordToken);
        } catch (AuthenticationException e) {
            logger.error(e.getMessage());
            result.addError(new ObjectError("userInfo.username", e instanceof IncorrectCredentialsException ? "用户名或密码不正确!" : e.getMessage()));
            redirectAttributes.addFlashAttribute(userForm);
            redirectAttributes.addFlashAttribute(BINDING_RESULT_KEY, result.getAllErrors());
            return "redirect:/signin";
        }

        return "redirect:/dashboard";
    }
}
