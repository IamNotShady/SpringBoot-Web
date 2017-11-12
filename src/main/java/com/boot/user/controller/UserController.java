package com.boot.user.controller;

import com.boot.common.base.BaseController;
import com.boot.common.base.BaseException;
import com.boot.common.aop.LoggerAnnotation;
import com.boot.user.bean.UserBean;
import com.boot.user.service.UserService;
import com.boot.util.Constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController extends BaseController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserService pubUserService;

    @RequestMapping("login")
    @LoggerAnnotation(description="PubUserController.login")
    public String login(Model model,UserBean pubUserBean) {
        try {
            UserBean user = pubUserService.getUserByName(pubUserBean);
        } catch (BaseException e) {
            log.error(e.getMessage(),e);
            model.addAttribute("user", pubUserBean);
            model.addAttribute("info", e.getMessage());
            session.setAttribute("user",pubUserBean);
            return "login";
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            model.addAttribute("info", Constants.SYS_FAIL_MSG);
            return EXCEPTION_PAGE;
        }
        return "/main";
    }

    @RequestMapping("logout")
    @LoggerAnnotation(description="PubUserController.logout")
    public String logout(Model model) {
        model.addAttribute("user", new UserBean());
        return "/login";
    }
}
