package com.haogrgr.buka.model;

import java.io.Serializable;
import java.util.Date;

public class ComicsDetail implements Serializable {
	
	private static final long serialVersionUID = 1206496981043907113L;
	
	private Integer id;
	private Integer mid;// mid
	private String name;// 漫画名
	private String author;// 作者
	private String intro;// 简介
	private Integer rate;// 评分(1-100)
	private String lastuptimeex;// 最后更新时间(2014-01-20 12:51:29)
	private Integer popular;// 战斗力(点击数)
	private Integer favor;// 收藏数
	private String finish;// 是否完结(0:未完结, 1:完结)
	private Integer discount;// 评论数
	private Date modifyTime;
	private Date createTime;

	public ComicsDetail() {
		super();
	}

	public ComicsDetail(Integer mid) {
		super();
		this.mid = mid;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMid() {
		return mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

	public String getLastuptimeex() {
		return lastuptimeex;
	}

	public void setLastuptimeex(String lastuptimeex) {
		this.lastuptimeex = lastuptimeex;
	}

	public Integer getPopular() {
		return popular;
	}

	public void setPopular(Integer popular) {
		this.popular = popular;
	}

	public Integer getFavor() {
		return favor;
	}

	public void setFavor(Integer favor) {
		this.favor = favor;
	}

	public String getFinish() {
		return finish;
	}

	public void setFinish(String finish) {
		this.finish = finish;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	
	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "ComicsDetail [mid=" + mid + ", name=" + name + ", author=" + author + ", intro=" + intro + ", rate=" + rate + ", lastuptimeex=" + lastuptimeex + ", popular=" + popular + ", favor="
				+ favor + ", finish=" + finish + ", discount=" + discount + "]";
	}
	
}
