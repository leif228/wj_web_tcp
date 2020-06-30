package com.wujie.app.framework.page;

import com.jfinal.plugin.activerecord.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


/**
 * @Title PageUtil
 * @Description JPA分页工具类
 * @Author wy
 * @Date 2019/7/10 17:47
 *
 *
 * 入则恳恳以尽忠，出则谦谦以自悔。
 */
public class PageUtil {

    private static final Integer DEFAULTPAGESIZE = 10;
    private static final Integer DEFAULTPAGECURRENT = 0;


    /**
     * 默认值检查赋值,如果不需要排序则 direction 传为null
     *
     * @param PageCurrent 当前页
     * @param pageSize    每页量
     * @param direction   分页排序规则
     * @param properties  排序字段
     * @return 分页插件
     */
    public static Pageable getPageable(Integer PageCurrent, Integer pageSize, Sort.Direction direction, String... properties) {
        PageCurrent = checkPageCurrent(PageCurrent);//分页检查 为空则附上默认值
        pageSize = checkPageSize(pageSize);//分页检查 为空则附上默认值
        //分页
        if (properties == null || properties.length < 1) {
            return PageRequest.of(PageCurrent, pageSize);
        }
        return PageRequest.of(PageCurrent, pageSize, direction, properties);
    }

    /**
     * 不检查默认值检查赋值
     *
     * @param PageCurrent 当前页
     * @param pageSize    每页量
     * @param direction   分页排序规则
     * @param properties  排序字段
     * @return 分页插件
     */
    public static Pageable getPageableNotCheck(Integer PageCurrent, Integer pageSize, Sort.Direction direction, String... properties) {
        return PageRequest.of(PageCurrent-1, pageSize, direction, properties);
    }

    public static PageAction setPageAction(Page page) {
        PageAction resultPage = new PageAction();
        Pageable pageable = page.getPageable();
        int totalPages = page.getTotalPages();       //总页数
        int pageSize = pageable.getPageSize();       //每页长度
        int pageNumber = pageable.getPageNumber() + 1; //当前页
        int totalElements = Long.valueOf(page.getTotalElements()).intValue();// 总数据条数

        resultPage.setTotalCount(totalElements);//设置总条数
        resultPage.setTotalPage(totalPages);//设置总页数
        resultPage.setPageSize(pageSize);//每页长度
        resultPage.setCurrentPage(pageNumber);//当前页

        return resultPage;
    }

    public static PageAction setPageAction(Integer totalElements, Integer totalPages, Integer pageSize, Integer pageNumber, boolean isFirstPage, boolean isLastPage) {
        PageAction resultPage = new PageAction();
        resultPage.setTotalCount(totalElements);//设置总条数
        resultPage.setTotalPage(totalPages);//设置总页数
        resultPage.setPageSize(pageSize);//每页长度
        resultPage.setCurrentPage(pageNumber);//当前页
        return resultPage;
    }

    public static PageAction setPageAction(com.jfinal.plugin.activerecord.Page<Record> page) {
        int pageNumber = page.getPageNumber();
        int totalPage = page.getTotalPage();
        int pageSize = page.getPageSize();
        int totalRow = page.getTotalRow();
        boolean firstPage = page.isFirstPage();
        boolean lastPage = page.isLastPage();
        return setPageAction(totalRow,totalPage,pageSize,pageNumber,firstPage,lastPage);
    }

    public static Pageable conditionRanking(Integer pageCurrent, Integer pageSize, Integer sortOrder, String... orderCondition) {

        Sort.Direction desc = Sort.Direction.DESC; // 1 是升序 2 是逆序
        if (sortOrder != null) {
            if (sortOrder == 1) {
                desc = Sort.Direction.ASC;
            }
        }
        //分页工具类处理分页
        return PageUtil.getPageable(pageCurrent, pageSize, desc, orderCondition);
    }


    public static Integer checkPageSize(Integer pageSize) {
        if (pageSize == null || pageSize == 0) {
            return DEFAULTPAGESIZE;
        }
        return pageSize;
    }

    public static Integer checkPageCurrent(Integer pageCurrent) {
        if (pageCurrent == null || pageCurrent < 0) {
            return DEFAULTPAGECURRENT;
        }
        return pageCurrent-1;
    }

//    public static void checkPageCurrentAndPageSize(Integer pageCurrent,Integer pageSize){
//        pageCurrent=checkPageCurrent(pageCurrent);
//        pageSize=checkPageSize(pageSize);
//    }

}
