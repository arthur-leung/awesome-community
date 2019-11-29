package com.arthur.awesome.community.service;

import com.arthur.awesome.community.dto.PaginationDTO;
import com.arthur.awesome.community.dto.QuestionDTO;
import com.arthur.awesome.community.exception.CustomizeErrorCode;
import com.arthur.awesome.community.exception.CustomizeException;
import com.arthur.awesome.community.mapper.QuestionExtMapper;
import com.arthur.awesome.community.mapper.QuestionMapper;
import com.arthur.awesome.community.mapper.UserMapper;
import com.arthur.awesome.community.model.Question;
import com.arthur.awesome.community.model.QuestionExample;
import com.arthur.awesome.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;


    public PaginationDTO list(int page, int pageSize) {
        PaginationDTO paginationDTO = new PaginationDTO();
        int totalCount = (int) questionMapper.countByExample(new QuestionExample());
        if (page < 1) {
            page = 1;
        }
        if (page > totalCount) {
            page = totalCount;
        }
        paginationDTO.setPagination(totalCount, page, pageSize);

        int offset = (page - 1) * pageSize;
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        final QuestionExample example = new QuestionExample();
        final List<Question> questions = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, pageSize));
        if (questions != null) {
            for (Question question : questions) {
                final User user = userMapper.selectByPrimaryKey(question.getCreator());
                final QuestionDTO questionDTO = new QuestionDTO();
                BeanUtils.copyProperties(question, questionDTO);
                questionDTO.setUser(user);
                questionDTOList.add(questionDTO);
            }
        }
        paginationDTO.setQuestions(questionDTOList);

        return paginationDTO;
    }

    public PaginationDTO list(int userId, int page, int pageSize) {
        PaginationDTO paginationDTO = new PaginationDTO();
        int totalCount = (int) questionMapper.countByExample(new QuestionExample());
        if (page < 1) {
            page = 1;
        }
        if (page > totalCount) {
            page = totalCount;
        }
        paginationDTO.setPagination(totalCount, page, pageSize);

        int offset = (page - 1) * pageSize;
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        QuestionExample example = new QuestionExample();
        example.createCriteria()
                .andCreatorEqualTo(userId);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, pageSize));
        if (questions != null) {
            for (Question question : questions) {
                final User user = userMapper.selectByPrimaryKey(question.getCreator());
                final QuestionDTO questionDTO = new QuestionDTO();
                BeanUtils.copyProperties(question, questionDTO);
                questionDTO.setUser(user);
                questionDTOList.add(questionDTO);
            }
        }

        paginationDTO.setQuestions(questionDTOList);


        return paginationDTO;
    }

    public QuestionDTO getByUserIdAndId(Integer userId, Long id) {
        final QuestionExample example = new QuestionExample();
        example.createCriteria()
                .andCreatorEqualTo(userId)
                .andIdEqualTo(id);
        final List<Question> questions = questionMapper.selectByExample(example);
        Question question = questions.get(0);
        final User user = userMapper.selectByPrimaryKey(question.getCreator());
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }

    public QuestionDTO getById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        final User user = userMapper.selectByPrimaryKey(question.getCreator());
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            questionMapper.insert(question);
        } else {
            questionMapper.updateByPrimaryKeySelective(question);
        }
    }

    public void incView(Long id) {
       /* final Question question = questionMapper.selectByPrimaryKey(id);
        Question updateQuestion = new Question();
        BeanUtils.copyProperties(question, updateQuestion);
        updateQuestion.setViewCount(question.getViewCount() + 1);
        questionMapper.updateByPrimaryKey(updateQuestion);*/
        Question updateQuestion = new Question();
        updateQuestion.setId(id);
        updateQuestion.setViewCount(1);

        questionExtMapper.incViewCount(updateQuestion);
    }
}
