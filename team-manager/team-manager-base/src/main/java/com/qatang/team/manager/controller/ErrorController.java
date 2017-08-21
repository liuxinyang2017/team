package com.qatang.team.manager.controller;

import com.qatang.team.manager.core.controller.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author jinsheng
 * @since 2016-10-08 14:27
 */
@Controller
@RequestMapping("/error")
public class ErrorController extends AbstractController {

    @RequestMapping(value = "/404", method = RequestMethod.GET)
    public String error404() {
        return "404";
    }

    @RequestMapping(value = "/500", method = RequestMethod.GET)
    public String error500() {
        return "500";
    }
}
