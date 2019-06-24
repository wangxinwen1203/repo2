package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @Auter Cheat
 * @Creat 2019-05-18 16:25
 */
public class UserDaoImpl implements UserDao {
    // 创建JdbcTemplate对象
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    // 查询用户是否存在
    @Override
    public User findUsername(String username) {
        // 定义sql语句
        String sql = "select * from tab_user where username=?";
        // 执行sql
        try {
            // 查询到了
            return template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        } catch (DataAccessException e) {
            // 没有查询到
            return null;
        }

    }

    // 创建用户
    @Override
    public void createUser(User user) {
        // 定义sql语句
        String sql = "insert into tab_user values(?,?,?,?,?,?,?,?,?,?)";
        // 执行sql语句
        template.update(sql, null, user.getUsername(), user.getPassword(), user.getName(), user.getBirthday(),
                user.getSex(), user.getTelephone(), user.getEmail(), user.getStatus(), user.getCode());
    }

    // 根据激活码查询用户
    @Override
    public User findByCode(String code) {
        // 定义sql语句
        String sql = "select * from tab_user where code=?";
        // 执行sql
        try {
            return template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
        } catch (DataAccessException e) {
            return null;
        }
    }

    // 改变用户激活状态
    @Override
    public void updateStatus(User user) {
        // 定义sql
        String sql = "update tab_user set status = 'Y' where uid=?";
        // 执行sql
        template.update(sql, user.getUid());
    }

    // 登录方法
    @Override
    public User login(User user) {
        // 定义sql
        String sql = "select * from tab_user where username=? and password=?";
        try {
            // 执行sql
            return template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), user.getUsername(), user.getPassword());
        } catch (DataAccessException e) {
            return null;
        }
    }
}
