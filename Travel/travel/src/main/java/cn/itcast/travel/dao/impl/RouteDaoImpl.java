package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auter Cheat
 * @Creat 2019-05-21 18:41
 */
public class RouteDaoImpl implements RouteDao {
    // 创建JdbcTemplate对象
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    // 查询总记录数
    @Override
    public int findTotalCount(int cid, String rname) {
        // 定义sql语句
        String sql = "select count(*) from tab_route where 1=1 ";
        // 拼接sql语句
        StringBuilder stringBuilder = new StringBuilder(sql);
        // 创建一个集合用于拼接参数
        List params = new ArrayList();
        // 判断cid是否有值
        if (cid != 0) {
            // 拼接字符串
            stringBuilder.append(" and cid = ? ");
            // 添加对应的值
            params.add(cid);
        }
        // 判断rname是否为空
        if (rname != null && !"null".equals(rname) && rname.length() > 0) {
            // 拼接字符串
            stringBuilder.append(" and rname like ? ");
            // 拼接参数
            params.add("%" + rname + "%");
        }
        // sql重新赋值
        sql = stringBuilder.toString();
        // 执行sql
        Integer integer = template.queryForObject(sql, Integer.class, params.toArray());
        return integer;
    }

    // 查询每页显示内容
    @Override
    public List findByPage(int cid, int start, int pageSize, String rname) {
        // 定义sql语句
        String sql = "select * from tab_route where  1=1 ";
        // 拼接sql语句
        StringBuilder stringBuilder = new StringBuilder(sql);
        // 创建一个集合用于拼接参数
        List params = new ArrayList();
        // 判断cid是否有值
        if (cid != 0) {
            // 拼接字符串
            stringBuilder.append(" and cid = ? ");
            // 添加对应的值
            params.add(cid);
        }
        // 判断rname是否为空
        if (rname != null && !"null".equals(rname) && rname.length() > 0) {
            // 拼接字符串
            stringBuilder.append(" and rname like ? ");
            // 拼接参数
            params.add("%" + rname + "%");
        }
        // 拼接分页条件
        stringBuilder.append(" limit ?,?");
        // 添加参数
        params.add(start);
        params.add(pageSize);
        // 重新赋值sql
        sql = stringBuilder.toString();
        return template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), params.toArray());
    }

    // 查询路线详细
    @Override
    public Route findByRid(String rid) {
        // 定义sql
        String sql = "select * from tab_route where rid=?";
        // 执行sql并返回结果
        return template.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), rid);
    }

    @Override
    public List<Route> findByRid1() {
        String sql = "SELECT * FROM tab_route,(SELECT rid,COUNT(*)FROM tab_favorite  GROUP BY rid  HAVING COUNT(*) > 10 ORDER BY COUNT(*) DESC) favorite WHERE tab_route.`rid`=favorite.rid ";
        List<Route> routes = template.query(sql, new BeanPropertyRowMapper<Route>(Route.class));
        return routes;
    }

    @Override
    public List<Route> find2(int cid) {
        String sql = "SELECT * FROM tab_route WHERE cid = ? ORDER BY rdate DESC;";
        List<Route> routes = template.query(sql, new BeanPropertyRowMapper<Route>(Route.class),cid);

        return routes;
    }
}
