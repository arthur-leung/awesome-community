package com.arthur.awesome.community.service;

import com.arthur.awesome.community.mapper.UserMapper;
import com.arthur.awesome.community.model.User;
import com.arthur.awesome.community.model.UserExample;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public void createOrUpdate(User user) {
        final UserExample example = new UserExample();
        example.createCriteria()
                .andAccountIdEqualTo(String.valueOf(user.getAccountId()));
        final List<User> users = userMapper.selectByExample(example);
        if (users.size() == 0) {
            user.setGmtCreate(user.getGmtModified());
            userMapper.insert(user);
        } else {
            User existUser = users.get(0);
            int id = existUser.getId();
            BeanUtils.copyProperties(user, existUser);
            final UserExample userExample = new UserExample();
            userExample.createCriteria()
                    .andIdEqualTo(id);
            userMapper.updateByExampleSelective(existUser, userExample);
        }
    }
}
