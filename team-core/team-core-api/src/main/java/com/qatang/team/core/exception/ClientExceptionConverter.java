package com.qatang.team.core.exception;

import com.qatang.team.core.bean.ErrorInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

/**
 * @author wangzhiliang
 */
public class ClientExceptionConverter {
    private static Logger logger = LoggerFactory.getLogger(ClientExceptionConverter.class);

    private static final ClientExceptionConverter INSTANCE = new ClientExceptionConverter();

    public static ClientExceptionConverter instance() {
        return INSTANCE;
    }

    private ClientExceptionConverter() {

    }

    private ClientExceptionRegistry clientExceptionRegistry = ClientExceptionRegistry.instance();

    public ClientException converter(ErrorInfo errorInfo) {
        Class<? extends ClientException> cls;
        if (errorInfo != null) {
            cls = clientExceptionRegistry.get(errorInfo.getModule(), errorInfo.getCode());
            String errorMessage = errorInfo.getMessage();
            try {
                return cls.getDeclaredConstructor(String.class).newInstance(errorMessage);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                logger.error("通过参数为string的构造方法, 实例化client exception出错");
                logger.error(e.getMessage(), e);
            } catch (NoSuchMethodException e) {

                // 如果没有参数为string类型的构造函数，尝试通过默认构造方法实例化exception
                try {
                    return cls.getDeclaredConstructor().newInstance();
                } catch (Exception e1) {
                    logger.error("通过无参数的构造方法, 实例化client exception出错");
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return new ClientException();
    }
}
