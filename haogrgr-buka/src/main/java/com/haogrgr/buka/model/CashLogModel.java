package com.haogrgr.buka.model;

import java.math.BigDecimal;
import java.util.Date;

public class CashLogModel {
	private Integer id;

	private String orderId;

	private String batchNo;

	private String trxId;

	private Integer userId;

	private BigDecimal amount;

	private Integer accountId;

	private String accountType;

	private String historyType;

	private String transformType;

	private BigDecimal fee;

	private Integer feeAccountId;

	private String feeAccountType;

	private String feeHistoryType;

	private String feeTransformType;

	private String channel;

	private String bankSn;

	private String bankName;

	private String bankCardNo;

	private String productName;

	private String productDesc;

	private Date endTime;

	private String remark;

	private Integer operateUserId;

	private String errorMsg;

	private String platform;

	private String cashType;

	private String reviewType;

	private String status;

	private Date modifyTime;

	private Date createTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getTrxId() {
		return trxId;
	}

	public void setTrxId(String trxId) {
		this.trxId = trxId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getHistoryType() {
		return historyType;
	}

	public void setHistoryType(String historyType) {
		this.historyType = historyType;
	}

	public String getTransformType() {
		return transformType;
	}

	public void setTransformType(String transformType) {
		this.transformType = transformType;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public Integer getFeeAccountId() {
		return feeAccountId;
	}

	public void setFeeAccountId(Integer feeAccountId) {
		this.feeAccountId = feeAccountId;
	}

	public String getFeeAccountType() {
		return feeAccountType;
	}

	public void setFeeAccountType(String feeAccountType) {
		this.feeAccountType = feeAccountType;
	}

	public String getFeeHistoryType() {
		return feeHistoryType;
	}

	public void setFeeHistoryType(String feeHistoryType) {
		this.feeHistoryType = feeHistoryType;
	}

	public String getFeeTransformType() {
		return feeTransformType;
	}

	public void setFeeTransformType(String feeTransformType) {
		this.feeTransformType = feeTransformType;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getBankSn() {
		return bankSn;
	}

	public void setBankSn(String bankSn) {
		this.bankSn = bankSn;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getOperateUserId() {
		return operateUserId;
	}

	public void setOperateUserId(Integer operateUserId) {
		this.operateUserId = operateUserId;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getCashType() {
		return cashType;
	}

	public void setCashType(String cashType) {
		this.cashType = cashType;
	}

	public String getReviewType() {
		return reviewType;
	}

	public void setReviewType(String reviewType) {
		this.reviewType = reviewType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
		return "CashLogModel [id=" + id + ", orderId=" + orderId + ", batchNo=" + batchNo + ", trxId=" + trxId + ", userId=" + userId + ", amount=" + amount + ", accountId=" + accountId
				+ ", accountType=" + accountType + ", historyType=" + historyType + ", transformType=" + transformType + ", fee=" + fee + ", feeAccountId=" + feeAccountId + ", feeAccountType="
				+ feeAccountType + ", feeHistoryType=" + feeHistoryType + ", feeTransformType=" + feeTransformType + ", channel=" + channel + ", bankSn=" + bankSn + ", bankName=" + bankName
				+ ", bankCardNo=" + bankCardNo + ", productName=" + productName + ", productDesc=" + productDesc + ", endTime=" + endTime + ", remark=" + remark + ", operateUserId=" + operateUserId
				+ ", errorMsg=" + errorMsg + ", platform=" + platform + ", cashType=" + cashType + ", reviewType=" + reviewType + ", status=" + status + ", modifyTime=" + modifyTime + ", createTime="
				+ createTime + "]";
	}
	
}