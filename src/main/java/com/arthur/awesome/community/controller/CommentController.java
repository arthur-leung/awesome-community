package com.arthur.awesome.community.controller;

import com.arthur.awesome.community.dto.CommentDTO;
import com.arthur.awesome.community.dto.ResponseDTO;
import com.arthur.awesome.community.exception.CustomizeErrorCode;
import com.arthur.awesome.community.model.Comment;
import com.arthur.awesome.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CommentController {

    @Autowired
    CommentService commentService;

    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    @ResponseBody
    public Object post(@RequestBody CommentDTO commentDTO,
                       HttpServletRequest res) {
        final Object user = res.getSession().getAttribute("user");
        if (user == null) {
            return ResponseDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        final Comment record = new Comment();
        record.setParentId(commentDTO.getParentId());
        record.setType(commentDTO.getType());
        record.setComment(commentDTO.getComment());
        record.setGmtModified(System.currentTimeMillis());
        record.setGmtCreate(record.getGmtModified());
        record.setCommentator(11);
        commentService.insert(record);
        Map<Object, Object> respObject = new HashMap<>();
        return ResponseDTO.okOf();
    }
}
