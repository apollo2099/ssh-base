package com.lshop.emailsend.service;

import com.gv.core.datastructure.Dto;

public interface EmailSendService {

	/**
	 * 多邮件发送公共方法
	 * @param dto
	 * @return
	 */
	public Boolean sendEmailNoticePub(Dto dto);
	/**
	 * 发送邮件通知
	 */
	public Boolean sendEmailNotice (Dto dto);
	/**
	 * 发送邮件通知
	 */
	public Boolean sendRankEmailNotice (Dto dto);
	/**
	 * 发优惠券推广邮件
	 */
	public Boolean sendEmailCounp(Dto dto);

	/**
	 * 一码多用系统中发邮件
	 * @param dto
	 * @return
	 */
	public Boolean sendEmailCode(Dto dto);
	
	/**
	 * 发送订阅邮件
	 */
	public Boolean sendEmailSubscribe(Dto dto);
}
