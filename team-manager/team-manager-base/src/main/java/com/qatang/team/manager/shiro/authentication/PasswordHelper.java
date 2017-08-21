package com.qatang.team.manager.shiro.authentication;

import com.zhangyu.arsenal.manager.utils.AuthenticatorUtils;
import com.zhangyu.ucenter.api.bean.user.UserInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author jinsheng
 * @since 2016-04-27 16:06
 */
@Component
public class PasswordHelper {

    @Value("${password.algorithmName}")
    private String algorithmName = "md5";
    @Value("${password.hashIterations}")
    private int hashIterations = 1;

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public void setHashIterations(int hashIterations) {
        this.hashIterations = hashIterations;
    }

    public void encryptPassword(UserInfo userInfo) {
        String randomSalt = AuthenticatorUtils.generateRandomSaltValue();
        userInfo.setSalt(randomSalt);

        String passwordHashValue = AuthenticatorUtils.generateHashValue(algorithmName, userInfo.getPassword(), this.getSalt(userInfo), hashIterations).toString();
        userInfo.setPassword(passwordHashValue);
    }

    public boolean validPassword(UserInfo userInfo, String password) {
        String passwordHashValue = AuthenticatorUtils.generateHashValue(algorithmName, password, this.getSalt(userInfo), hashIterations).toString();
        return userInfo.getPassword().equals(passwordHashValue);
    }

    public String getSalt(UserInfo userInfo) {
        return userInfo.getUsername() + userInfo.getSalt();
    }
}
