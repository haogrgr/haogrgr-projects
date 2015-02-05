package com.haogrgr.buka.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 分页 实体类 （封装分页请求和结果数据）
 */
public class PageInfo<T> implements Serializable {

    private static final long serialVersionUID = 5367449251268716436L;
    public static final int DEFAULT_PAGE_SIZE = 12;

    /** 分页信息 */
    private int pageSize = DEFAULT_PAGE_SIZE; // 每页记录条数
    private int pageNo = 1; // 页码 从1开始

    /** 查询参数 **/
    private Map<String, Object> paramMap; //查询条件
    private Object paramObj; //查询对象

    /** 结果数据 */
    private Integer total; // 总记录数
    private List<T> rows; // 当前页显示数据
    private Map<String, Object> resultMap;//其他的要显示的数据

    private boolean plugin = false; //是否走分页插件
    
    private String sqlId; //用于标识具体执行的分页标识
    
    public PageInfo() {
        super();
    }
    
    public PageInfo(Integer pageNo, Integer pageSize) {
        this.pageNo = pageNo != null ? pageNo : 1;
        this.pageSize = pageSize != null ? pageSize : PageInfo.DEFAULT_PAGE_SIZE;
    }
    
    public PageInfo(Integer pageNo, Integer pageSize, Object paramObj) {
        this.pageNo = pageNo != null ? pageNo : 1;
        this.pageSize = pageSize != null ? pageSize : PageInfo.DEFAULT_PAGE_SIZE;
        this.paramObj = paramObj;//防止sql配置文件中空判断时报空指针
    }
    
    /**
     * 获取分页begin参数 limit #{begin}, #{end}
     */
    @JsonIgnore
    public int getBegin() {
        Integer begin = (pageNo - 1) * pageSize;
        if (begin < 0) {
            begin = 0;
        }
        return begin;
    }

    /**
     * 获取分页end参数 limit {begin}, {end}
     */
    @JsonIgnore
    public int getEnd() {
        if (pageSize <= 0) {
            return DEFAULT_PAGE_SIZE;
        } else {
            return pageSize;
        }
    }
    
    /**
     * 添加查询参数
     * @param key 属性名
     * @param value 属性值(为空则不添加)
     * @return this
     */
    public PageInfo<T> addParam(String key, Object value){
        if(value != null){
            if(this.paramMap == null){
                this.paramMap = new HashMap<String, Object>(6);
            }
            this.paramMap.put(key, value);
        }
        return this;
    }
    
    /**
     * 添加非空字符串查询参数
     * @param key 属性名
     * @param value 属性值(为空则不添加)
     * @return this
     */
    public PageInfo<T> addParamIfNotBlank(String key, String value){
        if(value != null && value.trim().length() > 0){
            addParam(key, value);
        }
        return this;
    }
    
    /**
     * 添加满足条件的查询参数
     * @param key 属性名
     * @param value 属性值(为空则不添加)
     * @param exp 条件, 如果为true则添加, 否则不添加
     * @return this
     */
    public PageInfo<T> addParam(String key, Object value, Boolean exp){
        if(exp){
            addParam(key, value);
        }
        return this;
    }
    
    /**
     * 添加结果
     * @param key 属性名
     * @param value 属性值(为空则不添加)
     * @return this
     */
    public PageInfo<T> addResult(String key, Object value){
        if(value != null){
            if(this.resultMap == null){
                this.resultMap = new HashMap<String, Object>(4);
            }
            this.resultMap.put(key, value);
        }
        return this;
    }
    
    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
    
    @JsonIgnore
    public Map<String, Object> getParamMap() {
        if(paramMap == null){
            paramMap = new HashMap<String, Object>(4);
        }
        return paramMap;
    }

    public void setParamMap(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }
    
    @JsonIgnore
    public Object getParamObj() {
        return paramObj;
    }

    public void setParamObj(Object paramObj) {
        this.paramObj = paramObj;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }


    public Map<String, Object> getResultMap() {
        return resultMap;
    }

    public void setResultMap(Map<String, Object> resultMap) {
        this.resultMap = resultMap;
    }

    @JsonIgnore
    public boolean isPlugin() {
        return plugin;
    }

    public void setPlugin(boolean plugin) {
        this.plugin = plugin;
    }
    
    @JsonIgnore
    public String getSqlId() {
        return sqlId;
    }

    public void setSqlId(String type) {
        this.sqlId = type;
    }

}
