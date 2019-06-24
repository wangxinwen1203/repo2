package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.util.BeanFactory;

import java.util.List;

/**
 * @Auter Cheat
 * @Creat 2019-05-22 9:53
 */
public class FavoriteServiceImpl implements FavoriteService {
    // private FavoriteDao favoriteDao = new FavoriteDaoImpl();
    private FavoriteDao favoriteDao = (FavoriteDao) BeanFactory.getBean("favoriteDao");
    private PageBean<Route> pageBean=new PageBean<Route>();

    // 判断用户是否收藏
    @Override
    public boolean isFavorite(String rid, int uid) {
        // 调用方法查询用户是否收藏
        Favorite favorite = favoriteDao.findByRidAndUid(Integer.parseInt(rid), uid);
        return favorite != null;
    }

    // 添加收藏
    @Override
    public void add(String rid, int uid) {
        // 调用dao添加收藏
        favoriteDao.add(Integer.parseInt(rid), uid);
    }

    // 查询收藏排行榜
    @Override
    public PageBean<Route> findFavoriteRank(int currentPage, int pageSize, String rNameStr, String sPriceStr, String pPriceStr) {
        // 创建PageBean对象
        PageBean<Route> pb = new PageBean<>();
        // 查询总页数
        int totalCount = favoriteDao.findCountByRank(rNameStr, sPriceStr, pPriceStr);
        // 设置总页数
        pb.setTotalCount(totalCount);
        // 设置当前页
        pb.setCurrentPage(currentPage);
        // 获取总页数
        int totalPage = (totalCount - 1 + pageSize) / pageSize;
        // 设置总页数
        pb.setTotalPage(totalPage);
        // 设置每页显示大小
        pb.setPageSize(pageSize);
        // 调用dao分页查询收藏排行榜 获取每页显示内容
        int start = (currentPage - 1) * pageSize;
        List list = favoriteDao.findRank(start, pageSize, rNameStr, sPriceStr, pPriceStr);
        // 设置到pb对象中
        pb.setList(list);


        return pb;
    }

    // 分页展示用户收藏
    @Override
    public PageBean<Route> findFavorite(int uid,int currentPage,int pageSize) {
        int totalCount=favoriteDao.faindAllCountByUid(uid);
        // int totalCount;总记录数
        // int totalPage;//总页数
        int totalPage=(totalCount-1+pageSize)/pageSize;
        // int currentPage;//当前页码
        // int pageSize;//每页显示的条数
        // List<T> list;//每页显示的数据集合,通过uid，拿到所有的rid,通过rid获取所有的路线集合

        pageBean.setTotalCount(totalCount);
        pageBean.setTotalPage(totalPage);
        pageBean.setPageSize(pageSize);
        pageBean.setCurrentPage(currentPage);
        int start = (currentPage - 1) * pageSize;

        List<Route> routeslist= favoriteDao.findAllRouteByUid(uid,start,pageSize);

        pageBean.setList(routeslist);


        return pageBean;
    }
}
