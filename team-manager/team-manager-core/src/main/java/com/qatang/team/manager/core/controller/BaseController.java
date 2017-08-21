package com.qatang.team.manager.core.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jinsheng
 * @since 2016-04-26 16:56
 */
public class BaseController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected final static String ERROR_MESSAGE_KEY = "errorMessage";
    protected final static String SUCCESS_MESSAGE_KEY = "successMessage";
    protected final static String WARNING_MESSAGE_KEY = "warningMessage";
    protected final static String FORWARD_URL_KEY = "forwardUrl";

    protected final static String BINDING_RESULT_KEY = "bindingResult";
}
