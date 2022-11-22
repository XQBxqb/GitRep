package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author hp
 * @date 2022-11-07 14:16
 * @explain
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper=null;
    @Override
    public User queryByactAdpwd(Map<String, Object> map) {
        return userMapper.queryByacAdps(map);
    }

    @Override
    public List<User> queryAllUsers() {
        List<User> userList=userMapper.selectAllUsers();
        return userList;
    }
}
