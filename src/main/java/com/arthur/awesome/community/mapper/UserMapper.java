package com.arthur.awesome.community.mapper;

import com.arthur.awesome.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {

    @Insert("insert into user (name, account_id,token, gmt_create,gmt_modified, bio) values (#{name}, #{accountId}, #{token},#{gmtCreate},#{gmtModified},#{bio})")
    void insert(User user);

    @Select("select * from user where id = #{id}")
    User find(int id);

    @Select("select * from user where token = #{token}")
    User findByToken(String token);

    @Select("select * from user where account_id = #{accountId}")
    User findByAccountId(String accountId);

    @Update("update user set token = '' where id = #{id}")
    void dropToken(int id);

    @Update("update user set token = #{token}, gmt_modified=#{gmtModified} where id = #{id}")
    void updateUserToken(String token, long gmtModified, int id);

    @Update("update user set name = #{name}, account_id = #{accountId}, token = #{token}, gmt_modified=#{gmtModified}, bio=#{bio} where id = #{id}")
    void updateUser(User user);
}
