package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

import java.util.List;

/**
 * @Auter Cheat
 * @Creat 2019-05-22 9:39
 */
public interface RouteService {

    // 分页查询
    PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname);

    // 查询路线详细
    Route findDetail(String rid);

    List<Route> find1();

    List<Route> find2(String cid);
}
