package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.BeanFactory;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

/**
 * @Auter Cheat
 * @Creat 2019-05-18 16:22
 */
public class UserServiceImpl implements UserService {

    // 创建Dao层查询数据库
    // private UserDao userDao = new UserDaoImpl();
    private UserDao userDao = (UserDao) BeanFactory.getBean("userDao");

    // 查询用户是否存在
    @Override
    public User findUsername(String username) {
        // 调用方法查询用户名
        return userDao.findUsername(username);
    }

    // 创建用户方法
    @Override
    public void createUser(User user) {
        // 存入邮箱激活状态
        user.setStatus("N");
        // 存入激活码
        user.setCode(UuidUtil.getUuid());
        // 调用Dao层创建用户
        userDao.createUser(user);
        // 发送邮件
        String content = "<a herf = 'http:/localhost:8080/travel/user/activeUser?code=" + user.getCode() + "'>";
        MailUtils.sendMail(user.getEmail(), content, "激活邮件");
    }

    // 邮箱激活功能
    @Override
    public boolean active(String code) {
        // 调用方法查询数据库
        User user = userDao.findByCode(code);
        // 判断是否查询到相对应的对象
        if (user != null) {
            // 修改激活状态
            userDao.updateStatus(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public User login(User user) {
        // 调用Dao层查询用户
        return userDao.login(user);
    }
}
