package com.arthur.awesome.community.mapper;

import com.arthur.awesome.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title, description, gmt_create, gmt_modified, creator, tag) values " +
            "(#{title}, #{description}, #{gmtCreate}, #{gmtModified}, #{creator}, #{tag})")
    public void insert(Question question);

    @Select("select * from question limit #{offset}, #{pageSize}")
    List<Question> list(int offset, int pageSize);

    @Select("select count(1) from question")
    int count();
}
