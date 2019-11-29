package com.arthur.awesome.community.service;

import com.arthur.awesome.community.enums.CommentTypeEnum;
import com.arthur.awesome.community.exception.CustomizeErrorCode;
import com.arthur.awesome.community.exception.CustomizeException;
import com.arthur.awesome.community.mapper.CommentMapper;
import com.arthur.awesome.community.mapper.QuestionExtMapper;
import com.arthur.awesome.community.mapper.QuestionMapper;
import com.arthur.awesome.community.model.Comment;
import com.arthur.awesome.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Transactional
    public void insert(Comment record) {
        if (record.getParentId() == null || record.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (record.getType() == null || CommentTypeEnum.exist(record.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_NOT_FOUND);
        }
        if ((int) record.getType() == CommentTypeEnum.QUESTION.getType()) {
            //问题类型
            Question question = questionMapper.selectByPrimaryKey(record.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }

            commentMapper.insert(record);

            // 增加评论数
            Question updateQuestion = new Question();
            updateQuestion.setId(record.getParentId());
            updateQuestion.setCommentCount(1);
            questionExtMapper.incCommentCount(updateQuestion);
        } else if ((int) record.getType() == CommentTypeEnum.COMMENT.getType()) {
            //评论类型
            Comment comment = commentMapper.selectByPrimaryKey(record.getParentId());
            if (comment == null) {
                throw new CustomizeException(CustomizeErrorCode.NOT_FOUND_COMMENT);
            }
            commentMapper.insert(record);
        }
    }
}
