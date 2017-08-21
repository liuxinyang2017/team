package com.qatang.team.manager.interceptor;

import com.google.common.collect.Lists;
import com.qatang.team.core.bean.AbstractBaseApiBean;
import com.qatang.team.manager.core.query.CommonSearchable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 根据 request URI 自动绑定 ModelAttribute
 * @author jinsheng
 * @since 2016-07-12 16:47
 */
@Component
public class ModelAttributeInterceptor extends HandlerInterceptorAdapter {

    private static final String HTTP_METHOD_GET = "GET";
    private static final String HTTP_METHOD_POST = "POST";
    private static final String REQUEST_URI_LIST = "/list";
    private static final String REQUEST_URI_CREATE = "/create";
    private static final String REQUEST_URI_UPDATE = "/update";
    private static final String METHOD_PARAMETER_NAME_END_SEARCHABLE = "Searchable";
    private static final String METHOD_PARAMETER_NAME_END_FORM = "Form";

    @Autowired
    private ModelAttributeFactory modelAttributeFactory;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String method = request.getMethod();
        String requestURI = request.getRequestURI();

        if (StringUtils.contains(requestURI, REQUEST_URI_LIST) && (Objects.equals(method, HTTP_METHOD_GET) || Objects.equals(method, HTTP_METHOD_POST))) {
            if (Objects.nonNull(modelAndView) && Objects.nonNull(modelAndView.getModelMap())) {
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                MethodParameter[] methodParameters = handlerMethod.getMethodParameters();
                if (Objects.nonNull(methodParameters)) {
                    ModelMap modelMap = modelAndView.getModelMap();
                    List<MethodParameter> methodParameterList = Arrays.asList(methodParameters);
                    for (MethodParameter methodParameter : methodParameterList) {
                        Class parameterType = methodParameter.getParameterType();
                        if (parameterType.getSimpleName().endsWith(METHOD_PARAMETER_NAME_END_SEARCHABLE)) {
                            if (Objects.isNull(parameterType.getDeclaredFields()) && (Objects.isNull(parameterType.getSuperclass()) || !Objects.equals(parameterType.getSuperclass(), CommonSearchable.class))) {
                                break;
                            }
                            List<Field> fieldList = Lists.newArrayList();
                            Field[] fields = parameterType.getDeclaredFields();
                            if (Objects.nonNull(fields)) {
                                for (Field field : fields) {
                                    if (field.getType().isEnum()) {
                                        fieldList.add(field);
                                    }
                                }
                            }
                            fields = parameterType.getSuperclass().getDeclaredFields();
                            if (Objects.nonNull(fields)) {
                                for (Field field : fields) {
                                    if (field.getType().isEnum()) {
                                        fieldList.add(field);
                                    }
                                }
                            }
                            for (Field field : fieldList) {
                                String attributeName = String.format("all%sList", field.getType().getSimpleName());
                                if (modelAttributeFactory.containsKey(attributeName)) {
                                    modelMap.addAttribute(attributeName, modelAttributeFactory.getModelAttributeList(attributeName));
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }

        if ((StringUtils.contains(requestURI, REQUEST_URI_CREATE) || StringUtils.contains(requestURI, REQUEST_URI_UPDATE)) && Objects.equals(method, HTTP_METHOD_GET)) {
            if (Objects.nonNull(modelAndView) && Objects.nonNull(modelAndView.getModelMap())) {
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                MethodParameter[] methodParameters = handlerMethod.getMethodParameters();
                if (Objects.nonNull(methodParameters)) {
                    ModelMap modelMap = modelAndView.getModelMap();
                    List<MethodParameter> methodParameterList = Arrays.asList(methodParameters);
                    for (MethodParameter methodParameter : methodParameterList) {
                        Class parameterType = methodParameter.getParameterType();
                        if (parameterType.getSimpleName().endsWith(METHOD_PARAMETER_NAME_END_FORM)) {
                            if (Objects.isNull(parameterType.getDeclaredFields())) {
                                break;
                            }
                            List<Field> fieldList = Lists.newArrayList();
                            Field[] fields = parameterType.getDeclaredFields();
                            if (Objects.nonNull(fields)) {
                                for (Field field : fields) {
                                    Class type = field.getType();
                                    if (Objects.equals(type.getSuperclass(), AbstractBaseApiBean.class)) {
                                        Field[] fields1 = type.getDeclaredFields();
                                        if (Objects.isNull(fields1)) {
                                            continue;
                                        }
                                        for (Field field2 : fields1) {
                                            Class type2 = field2.getType();
                                            if (type2.isEnum()) {
                                                fieldList.add(field2);
                                            }
                                        }
                                    }
                                }
                            }
                            for (Field field : fieldList) {
                                String simpleName = field.getType().getSimpleName();
                                String attributeName = String.format("%s%sList", StringUtils.substring(simpleName, 0, 1).toLowerCase(), StringUtils.substring(simpleName, 1));
                                if (!modelMap.containsKey(attributeName) && modelAttributeFactory.containsKey(attributeName)) {
                                    modelMap.addAttribute(attributeName, modelAttributeFactory.getModelAttributeList(attributeName));
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }
    }
}
