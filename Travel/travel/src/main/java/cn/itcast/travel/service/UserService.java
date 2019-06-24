package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

/**
 * @Auter Cheat
 * @Creat 2019-05-18 16:15
 */
public interface UserService {
    // 查询用户名是否存在方法
    User findUsername(String username);

    // 创建用户方法
    void createUser(User user);

    // 邮箱激活功能
    boolean active(String code);

    // 登录方法
    User login(User user);
}
