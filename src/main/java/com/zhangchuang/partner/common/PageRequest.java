package com.zhangchuang.partner.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用分类请求参数
 *
 * @author chuang
 */
@Data
public class PageRequest implements Serializable {

    private static final long serialVersionUID = 101L;

    /**
     * 页面大小
     */
    protected int pageSite = 10;

    /**
     * 当前是第几页
     */
    protected int pageNum = 1;
}
