package com.ttsource.model.result;

/**
 * **********************************************
 * <p/>
 * Date: 2019-01-21 18:17
 * <p/>
 * Author: SinPingWu
 * <p/>
 * Email: wuxinping@ubinavi.com.cn
 * <p/>
 * brief:
 * <p/>
 * history:
 * <p/>
 * **********************************************
 */
public class ResultInfoPage extends ResultInfo {

    private Long total;
    private Integer currPage;
    private Integer pageSize;

    public ResultInfoPage(int code, Object data, String des) {
        super(code, data, des);
    }

    public ResultInfoPage(int code, Object data, String des, Long total, Integer currPage, Integer pageSize) {
        super(code, data, des);

        this.total = total;
        this.currPage = currPage;
        this.pageSize = pageSize;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getCurrPage() {
        return currPage;
    }

    public void setCurrPage(Integer currPage) {
        this.currPage = currPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
