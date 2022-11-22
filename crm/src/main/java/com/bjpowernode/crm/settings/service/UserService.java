package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.settings.domain.User;

import java.util.List;
import java.util.Map;

/**
 * @author hp
 * @date 2022-11-07 14:15
 * @explain
 */
public interface UserService {
    User queryByactAdpwd(Map<String, Object>map);

    List<User> queryAllUsers();
}
