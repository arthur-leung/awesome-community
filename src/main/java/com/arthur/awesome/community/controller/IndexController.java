package com.arthur.awesome.community.controller;

import com.arthur.awesome.community.dto.PaginationDTO;
import com.arthur.awesome.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
    @Autowired
    QuestionService questionService;

    @RequestMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "1") int page,
                        @RequestParam(name = "size", defaultValue = "5") int pageSize) {

        PaginationDTO pagination = questionService.list(page, pageSize);
        model.addAttribute("pagination", pagination);
        return "index";
    }
}
