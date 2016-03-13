package com.lshop.manage.llinkurl.service;

import com.gv.core.datastructure.Dto;
import com.gv.core.datastructure.impl.BasePo;
import com.gv.core.datastructure.page.Pagination;



/**
 * 页面静态化
 * @author tangd
 *
 */
public interface LinkUrlManageService {

	/**
	 * 链接列表
	 * @param dto 刷选条件
	 * @return
	 */
	public Pagination list(Dto dto);
	
	/**
	 * 添加资源
	 * @param dto
	 * @return
	 */
	public Integer addres(Dto dto);
	
	/**
	 * 修改资源
	 * @param dto
	 * @return
	 */
	public Integer updateres(Dto dto);
	
	/**
	 * 查看资源详细信息
	 * @param dto
	 * @return
	 */
	public BasePo viewres(Dto dto);
	/**
	 * 删除资源信息
	 * @param dto
	 * @return
	 */
	public Integer delres(Dto dto);
}
