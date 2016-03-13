package com.lshop.common.pojo.logic;

import java.util.Date;

import com.gv.core.datastructure.Key;


/**
 * LvCoupon entity. @author MyEclipse Persistence Tools
 */

public class LvCoupon extends com.gv.core.datastructure.impl.BasePo implements java.io.Serializable {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = 6755903181866344801L;
	private Integer id;
     private String couponCode;
     private Date validityDate;
     private Float faceValue;
     private String currency;
     private Short isActivate;
     private Short isDel;
     private String oid;
     private String couponName;
     private Short couponType;
     private String storeId;
     private String code;
     private Date createTime;
     private Date modifyTime;
     private Integer modifyUserId;
     private String modifyUserName;
     private String atrr;
     private String atrr1;
     private String atrr2;
     private String atrr3;


    // Constructors

    /** default constructor */
    public LvCoupon() {
    }

	/** minimal constructor */
    public LvCoupon(String couponCode, Float faceValue, Short isActivate, Short couponType, String storeId) {
        this.couponCode = couponCode;
        this.faceValue = faceValue;
        this.isActivate = isActivate;
        this.couponType = couponType;
        this.storeId = storeId;
    }
    
    /** full constructor */
    public LvCoupon(String couponCode, Date validityDate, Float faceValue, String currency, Short isActivate, Short isDel, String oid, String couponName, Short couponType, String storeId, String code, Date createTime, Date modifyTime, Integer modifyUserId, String modifyUserName, String atrr, String atrr1, String atrr2, String atrr3) {
        this.couponCode = couponCode;
        this.validityDate = validityDate;
        this.faceValue = faceValue;
        this.currency = currency;
        this.isActivate = isActivate;
        this.isDel = isDel;
        this.oid = oid;
        this.couponName = couponName;
        this.couponType = couponType;
        this.storeId = storeId;
        this.code = code;
        this.createTime = createTime;
        this.modifyTime = modifyTime;
        this.modifyUserId = modifyUserId;
        this.modifyUserName = modifyUserName;
        this.atrr = atrr;
        this.atrr1 = atrr1;
        this.atrr2 = atrr2;
        this.atrr3 = atrr3;
    }

   
    // Property accessors

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public String getCouponCode() {
        return this.couponCode;
    }
    
    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public Date getValidityDate() {
        return this.validityDate;
    }
    
    public void setValidityDate(Date validityDate) {
        this.validityDate = validityDate;
    }

    public Float getFaceValue() {
        return this.faceValue;
    }
    
    public void setFaceValue(Float faceValue) {
        this.faceValue = faceValue;
    }

    public String getCurrency() {
        return this.currency;
    }
    
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Short getIsActivate() {
        return this.isActivate;
    }
    
    public void setIsActivate(Short isActivate) {
        this.isActivate = isActivate;
    }

    public Short getIsDel() {
        return this.isDel;
    }
    
    public void setIsDel(Short isDel) {
        this.isDel = isDel;
    }

    public String getOid() {
        return this.oid;
    }
    
    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getCouponName() {
        return this.couponName;
    }
    
    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public Short getCouponType() {
        return this.couponType;
    }
    
    public void setCouponType(Short couponType) {
        this.couponType = couponType;
    }

    public String getStoreId() {
        return this.storeId;
    }
    
    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }

    public Date getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return this.modifyTime;
    }
    
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getModifyUserId() {
        return this.modifyUserId;
    }
    
    public void setModifyUserId(Integer modifyUserId) {
        this.modifyUserId = modifyUserId;
    }

    public String getModifyUserName() {
        return this.modifyUserName;
    }
    
    public void setModifyUserName(String modifyUserName) {
        this.modifyUserName = modifyUserName;
    }

    public String getAtrr() {
        return this.atrr;
    }
    
    public void setAtrr(String atrr) {
        this.atrr = atrr;
    }

    public String getAtrr1() {
        return this.atrr1;
    }
    
    public void setAtrr1(String atrr1) {
        this.atrr1 = atrr1;
    }

    public String getAtrr2() {
        return this.atrr2;
    }
    
    public void setAtrr2(String atrr2) {
        this.atrr2 = atrr2;
    }

    public String getAtrr3() {
        return this.atrr3;
    }
    
    public void setAtrr3(String atrr3) {
        this.atrr3 = atrr3;
    }

	public Key getPk() {
		// TODO Auto-generated method stub
		return null;
	}
   








}