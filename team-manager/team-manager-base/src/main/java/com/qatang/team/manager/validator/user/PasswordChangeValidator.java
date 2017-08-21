package com.qatang.team.manager.validator.user;


import com.qatang.team.manager.core.exception.ValidateFailedException;
import com.qatang.team.manager.core.validator.AbstractValidator;
import com.qatang.team.manager.form.user.UserForm;
import com.qatang.team.manager.shiro.authentication.PasswordHelper;
import com.zhangyu.ucenter.api.bean.user.UserInfo;
import com.zhangyu.ucenter.api.service.user.UserInfoApiService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jinsheng
 * @since 2016-05-13 13:53
 */
@Component
public class PasswordChangeValidator extends AbstractValidator<UserForm> {

    @Autowired
    private UserInfoApiService userInfoApiService;

    @Autowired
    private PasswordHelper passwordHelper;

    @Override
    public boolean validate(UserForm userForm) throws ValidateFailedException {
        UserInfo currentUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        if (currentUser == null) {
            String msg = String.format("currentUser不能为空");
            logger.error(msg);
            throw new ValidateFailedException("userForm", msg);
        }
        currentUser = userInfoApiService.findOne(currentUser.getId());
        if (currentUser == null) {
            String msg = String.format("currentUser不能为空");
            logger.error(msg);
            throw new ValidateFailedException("userForm", msg);
        }
        if (userForm == null || userForm.getUserInfo() == null) {
            String msg = String.format("userForm参数不能为空");
            logger.error(msg);
            throw new ValidateFailedException("userForm", msg);
        }
        if (StringUtils.isEmpty(userForm.getUserInfo().getPassword())) {
            String msg = String.format("旧密码不能为空");
            logger.error(msg);
            throw new ValidateFailedException("user.password", msg);
        }
        if (userForm.getUserInfo().getPassword().length() < 6 || userForm.getUserInfo().getPassword().length() > 16) {
            String msg = String.format("旧密码长度必须在6-16个字符之间");
            logger.error(msg);
            throw new ValidateFailedException("user.password", msg);
        }
        if (StringUtils.isEmpty(userForm.getNewPassword())) {
            String msg = String.format("新密码不能为空");
            logger.error(msg);
            throw new ValidateFailedException("newPassword", msg);
        }
        if (userForm.getNewPassword().length() < 6 || userForm.getNewPassword().length() > 16) {
            String msg = String.format("新密码长度必须在6-16个字符之间");
            logger.error(msg);
            throw new ValidateFailedException("newPassword", msg);
        }
        if (StringUtils.isEmpty(userForm.getConPassword())) {
            String msg = String.format("确认密码不能为空");
            logger.error(msg);
            throw new ValidateFailedException("conPassword", msg);
        }
        if (userForm.getConPassword().length() < 6 || userForm.getConPassword().length() > 16) {
            String msg = String.format("确认密码长度必须在6-16个字符之间");
            logger.error(msg);
            throw new ValidateFailedException("conPassword", msg);
        }
        if (!userForm.getNewPassword().equals(userForm.getConPassword())) {
            String msg = String.format("两次输入密码不一致");
            logger.error(msg);
            throw new ValidateFailedException("newPassword", msg);
        }

        if (!passwordHelper.validPassword(currentUser, userForm.getUserInfo().getPassword())) {
            String msg = String.format("旧密码错误");
            logger.error(msg);
            throw new ValidateFailedException("user.password", msg);
        }

        if (passwordHelper.validPassword(currentUser, userForm.getNewPassword())) {
            String msg = String.format("新密码不能和旧密码一样");
            logger.error(msg);
            throw new ValidateFailedException("user.password", msg);
        }
        return true;
    }
}
