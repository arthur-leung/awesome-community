package com.arthur.awesome.community.mapper;

import com.arthur.awesome.community.model.Question;

public interface QuestionExtMapper {

    int incViewCount(Question record);
}