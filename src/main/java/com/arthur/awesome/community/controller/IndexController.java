package com.arthur.awesome.community.controller;

import com.arthur.awesome.community.dto.QuestionDTO;
import com.arthur.awesome.community.mapper.QuestionMapper;
import com.arthur.awesome.community.mapper.UserMapper;
import com.arthur.awesome.community.model.Question;
import com.arthur.awesome.community.model.User;
import com.arthur.awesome.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    QuestionService questionService;

    @RequestMapping("/")
    public String index(HttpServletRequest req, Model model) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        req.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }

        List<QuestionDTO> questions = questionService.list();
        model.addAttribute("questions", questions);
        return "index";
    }
}
