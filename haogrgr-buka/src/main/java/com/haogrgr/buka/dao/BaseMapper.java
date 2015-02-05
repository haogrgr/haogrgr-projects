package com.haogrgr.buka.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haogrgr.buka.util.PageInfo;

public interface BaseMapper<T> {

	public T findById(Integer id);

	public List<T> findByPage(PageInfo<T> pageInfo);

	public Integer findByPageCount(PageInfo<T> pageInfo);

	public Integer update(T obj);

	public List<T> all();

	public Integer count();

	public Integer insert(T obj);

	public Integer inserts(@Param("list") List<T> list);

	public Integer delete(Integer id);

	public Integer deletes(@Param("ids") Integer[] ids);

	public List<T> load(@Param("ids") Integer[] ids);

}
