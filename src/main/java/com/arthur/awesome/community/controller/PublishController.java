package com.arthur.awesome.community.controller;

import com.arthur.awesome.community.dto.QuestionDTO;
import com.arthur.awesome.community.mapper.QuestionMapper;
import com.arthur.awesome.community.mapper.UserMapper;
import com.arthur.awesome.community.model.Question;
import com.arthur.awesome.community.model.User;
import com.arthur.awesome.community.service.QuestionService;
import com.sun.deploy.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    QuestionService questionService;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable int id,
                       HttpServletRequest req,
                       Model model) {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        QuestionDTO question = questionService.getByUserIdAndId(user.getId(), id);
        if (question == null) {
            return "redirect:/";
        }

        model.addAttribute("id", question.getId());
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        return "publish";
    }

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam("id") Integer id,
                            @RequestParam("title") String title,
                            @RequestParam("description") String description,
                            @RequestParam("tag") String tag,
                            HttpServletRequest req,
                            Model model) {
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);

        if (title == null || title.isEmpty()) {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }

        if (description == null || description.isEmpty()) {
            model.addAttribute("error", "问题补充不能为空");
            return "publish";
        }

        if (tag == null || tag.isEmpty()) {
            model.addAttribute("error", "问题标签不能为空");
            return "publish";
        }

        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtModified(System.currentTimeMillis());

        if (id != null) {
            question.setId(id);
        } else {
            question.setGmtCreate(question.getGmtModified());
        }
        questionService.createOrUpdate(question);
        return "redirect:/";
    }
}
