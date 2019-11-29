package com.arthur.awesome.community.controller;

import com.arthur.awesome.community.dto.CommentDTO;
import com.arthur.awesome.community.mapper.CommentMapper;
import com.arthur.awesome.community.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CommentController {

    @Autowired
    CommentMapper commentMapper;

    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    @ResponseBody
    public Object post(@RequestBody CommentDTO commentDTO) {
        final Comment record = new Comment();
        record.setParentId(commentDTO.getParentId());
        record.setType(commentDTO.getType());
        record.setComment(commentDTO.getComment());
        record.setGmtModified(System.currentTimeMillis());
        record.setGmtCreate(record.getGmtModified());
        record.setCommentator(11);
        commentMapper.insert(record);

        return null;
    }
}
