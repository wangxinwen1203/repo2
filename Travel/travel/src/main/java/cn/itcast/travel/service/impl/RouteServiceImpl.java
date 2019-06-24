package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.util.BeanFactory;

import java.util.List;

/**
 * @Auter Cheat
 * @Creat 2019-05-22 9:40
 */
public class RouteServiceImpl implements RouteService {

    // 创建dao
    /*private RouteDao routeDao = new RouteDaoImpl();
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();*/
    private RouteDao routeDao = (RouteDao) BeanFactory.getBean("routeDao");
    private RouteImgDao routeImgDao = (RouteImgDao) BeanFactory.getBean("routeImgDao");
    private SellerDao sellerDao = (SellerDao) BeanFactory.getBean("sellerDao");
    private FavoriteDao favoriteDao = (FavoriteDao) BeanFactory.getBean("favoriteDao");


    // 根据条件查询分页
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname) {
        // 创建pageBean对象
        PageBean<Route> pb = new PageBean<>();
        // 设置当前页
        pb.setCurrentPage(currentPage);
        // 设置每页展示大小
        pb.setPageSize(pageSize);
        // 获取总记录数
        int totalCount = routeDao.findTotalCount(cid, rname);
        pb.setTotalCount(totalCount);
        // 设置总页数
        pb.setTotalPage((totalCount - 1 + pageSize) / pageSize);
        // 获取每页显示内容
        int start = (currentPage - 1) * pageSize;
        List list = routeDao.findByPage(cid, start, pageSize, rname);
        pb.setList(list);
        return pb;
    }

    // 查询路线详细
    @Override
    public Route findDetail(String rid) {
        // 根据rid查询RouteDao
        Route route = routeDao.findByRid(rid);
        // 根据rid查询图片
        List<RouteImg> imgs = routeImgDao.findImgs(rid);
        // 设置到route对象中
        route.setRouteImgList(imgs);
        // 根据route对象总的sid查询信息
        Seller seller = sellerDao.findBySid(route.getSid());
        // 设置到route对象中
        route.setSeller(seller);
        // 查询收藏次数
        int count = favoriteDao.findCountByRid(route.getRid());
        route.setCount(count);
        // 返回对象
        return route;
    }

    @Override
    public List<Route> find1() {
        return routeDao.findByRid1();
    }

    @Override
    public List<Route> find2(String cid) {
        return routeDao.find2(Integer.parseInt(cid));
    }
}
