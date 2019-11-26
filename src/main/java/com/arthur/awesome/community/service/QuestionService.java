package com.arthur.awesome.community.service;

import com.arthur.awesome.community.dto.PaginationDTO;
import com.arthur.awesome.community.dto.QuestionDTO;
import com.arthur.awesome.community.mapper.QuestionMapper;
import com.arthur.awesome.community.mapper.UserMapper;
import com.arthur.awesome.community.model.Question;
import com.arthur.awesome.community.model.User;
import lombok.val;
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


    public PaginationDTO list(int page, int pageSize) {
        int offset = (page - 1) * pageSize;

        List<Question> questions = questionMapper.list(offset, pageSize);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        PaginationDTO paginationDTO  = new PaginationDTO();

        if (questions != null) {
            for (Question question : questions) {
                final User user = userMapper.find(question.getCreator());
                final QuestionDTO questionDTO = new QuestionDTO();
                BeanUtils.copyProperties(question, questionDTO);
                questionDTO.setUser(user);
                questionDTOList.add(questionDTO);
            }
        }

        paginationDTO.setQuestions(questionDTOList);
        int totalCount = questionMapper.count();
        paginationDTO.setPagination(totalCount, page, pageSize);

        return paginationDTO;
    }
}
