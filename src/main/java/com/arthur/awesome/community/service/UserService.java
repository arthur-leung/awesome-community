package com.arthur.awesome.community.service;

import com.arthur.awesome.community.mapper.UserMapper;
import com.arthur.awesome.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public void createOrUpdate(User user) {
        User existUser = userMapper.findByAccountId(String.valueOf(user.getAccountId()));
        if (existUser == null) {
            user.setGmtCreate(user.getGmtModified());
            userMapper.insert(user);
        } else {
            // TODO 更新用户信息
//            userMapper.updateUserToken(token, System.currentTimeMillis(), existUser.getId());
            int id = existUser.getId();
            BeanUtils.copyProperties(user, existUser);
            existUser.setId(id);
            userMapper.update(existUser);
            System.out.println("test");
        }
    }
}
