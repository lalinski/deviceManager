package com.lalin.test.site.blog.mix.one.controller;

import com.lalin.test.site.blog.mix.one.utils.TimeFormatUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

/**
 * @task:
 * @discrption:
 * @author: Aere
 * @date: 2016/11/30 11:44
 * @version: 1.0.0
 */
@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping(path = "", method = {RequestMethod.GET, RequestMethod.POST})
    public String root() {
        return "forward:/index";
    }

    @GetMapping("/index")
    public String home(Model model) {
        SimpleDateFormat formatter = TimeFormatUtil.getFormatter(TimeFormatUtil.EXTENDED_CALENDAR_DATES_TIMES);
        model.addAttribute("dataTime", formatter.format(new Date()));
        return "index/index";
    }


    @RequestMapping("/haveLog")
    public String haveLog() {
        return "index/haveLog";
    }

    @RequestMapping("/login")
    public String login() {
        return "index/login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "index/login";
    }
    @RequestMapping("/home")
    public String register(Model model) {
        SimpleDateFormat formatter = TimeFormatUtil.getFormatter(TimeFormatUtil.EXTENDED_CALENDAR_DATES_TIMES);
        model.addAttribute("dataTime", formatter.format(new Date()));
        return "index/home";
    }


}
