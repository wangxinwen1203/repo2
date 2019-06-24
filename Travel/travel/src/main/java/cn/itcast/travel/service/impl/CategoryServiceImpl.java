package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.*;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.BeanFactory;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Auter Cheat
 * @Creat 2019-05-20 12:38
 */
public class CategoryServiceImpl implements CategoryService {

    // 创建dao对象
    // private CategoryDao categoryDao = new CategoryDaoImpl();
    private CategoryDao categoryDao = (CategoryDao) BeanFactory.getBean("categoryDao");


    // 查询所有分类信息
    @Override
    public List findAll() {
        // 使用缓存优化 获取客户端
        Jedis jedis = JedisUtil.getJedis();
        // 创建List对象存储获取的内筒
        List<Category> list = null;
        // 获取存入的数据
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);
        // 判断是否为空
        if (categorys == null || categorys.size() == 0) {
            // 从数据库中查询数据
            list = categoryDao.findAll();
            // 遍历集合
            for (int i = 0; i < list.size(); i++) {
                // 取出内容存入jedis中
                jedis.zadd("category", list.get(i).getCid(), list.get(i).getCname());
            }
        } else {
            // 改变集合类型
            list = new ArrayList<Category>();
            for (Tuple tuple : categorys) {
                // 创建一个Category对象
                Category category = new Category();
                category.setCid((int) tuple.getScore());
                category.setCname(tuple.getElement());
                list.add(category);
            }
        }
        return list;
    }



}
