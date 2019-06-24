package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

/**
 * @Auter Cheat
 * @Creat 2019-05-22 9:51
 */
public interface FavoriteService {

    // 判断是否收藏
    boolean isFavorite(String rid, int uid);

    // 添加收藏
    void add(String rid, int uid);

    // 查询收藏排行榜
    PageBean<Route> findFavoriteRank(int currentPage, int pageSize, String rNameStr, String sPriceStr, String pPriceStr);

    // 查询用户收藏
    PageBean<Route> findFavorite(int uid, int currentPage,int pageSize);
}
