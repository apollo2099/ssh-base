package com.lshop.manage.llinkurl.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gv.core.datastructure.Dto;
import com.gv.core.datastructure.impl.BasePo;
import com.gv.core.datastructure.page.Pagination;
import com.gv.core.datastructure.page.SimplePage;
import com.gv.core.hibernate3.Finder;
import com.gv.core.hibernate3.HibernateBaseDAO;
import com.gv.core.util.ObjectUtils;
import com.lshop.common.pojo.logic.LvLinkUrl;
import com.lshop.common.pojo.logic.LvStore;
import com.lshop.common.util.Constants;
import com.lshop.common.util.StoreHelpUtil;
import com.lshop.common.util.StringUtil;
import com.lshop.manage.llinkurl.service.LinkUrlManageService;

@Service("LinkUrlManageService")
public class LinkUrlManageServiceImpl implements LinkUrlManageService {

	@Resource
	private HibernateBaseDAO lvlogicReadDao;
	@Resource
	private HibernateBaseDAO lvlogicWriteDao;
	@Override
	public Pagination list(Dto dto) {
		StringBuilder hql=new StringBuilder("FROM LvLinkUrl t where 1=1" );
		LvLinkUrl linkUrl=(LvLinkUrl) dto.getDefaultPo();
		SimplePage page = (SimplePage)dto.get("page");
		Map<String ,Object> params=new HashMap<String ,Object>();
		if(linkUrl!=null){
			if(ObjectUtils.isNotEmpty(linkUrl.getStoreFlag())){
				hql.append(" and t.storeFlag =:storeFlag ");
				params.put("storeFlag", linkUrl.getStoreFlag());
			}
		}
		hql.append(StoreHelpUtil.getShopRelevanceString(dto.getAsString("flag"),"storeFlag","t"));
		hql.append(" order by t.storeFlag desc ");
		Finder finder = Finder.create(hql.toString());
		finder.setParams(params);
		return lvlogicReadDao.find(finder, page.getNextPage(), page.getNumPerPage());
		
	}
	@Override
	public Integer addres(Dto dto) {
       Integer status=300;
       LvLinkUrl po=(LvLinkUrl) dto.getDefaultPo();
		try {
			if (po!=null) {
				//数据去掉空格，防止静态化连接失败
				po.setName(po.getName().trim());
				po.setDynamicurl(po.getDynamicurl().trim());
				po.setStaticurl(po.getStaticurl().trim());
				po.setHql(po.getHql().trim());
				po.setNote(po.getNote().trim());
				lvlogicWriteDao.save(po);
				status=200;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			status=300;
		}
		return status;
	}
	@Override
	public Integer updateres(Dto dto) {
		Integer status=300;
		LvLinkUrl po=(LvLinkUrl) dto.getDefaultPo();
		try {
			if (po!=null) {
				//数据去掉空格
				po.setName(po.getName().trim());
				po.setDynamicurl(po.getDynamicurl().trim());
				po.setStaticurl(po.getStaticurl().trim());
				po.setHql(po.getHql().trim());
				po.setNote(po.getNote().trim());
				lvlogicWriteDao.update(po);
				status=200;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			status=300;
		}
		return status;
	}
	@Override
	public Integer delres(Dto dto) {
		 	Integer status=300;
		 	String ids=dto.getAsString("ids");
			try {
				if (ids!=null) {
					lvlogicWriteDao.delete("DELETE LvLinkUrl WHERE id in ("+ids+")",null);
					status=200;
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				status=300;
			}
			return status;
	}
	
	@Override
	public BasePo viewres(Dto dto) {
		Integer id=dto.getAsInteger("id");	
		return (BasePo)lvlogicReadDao.load(LvLinkUrl.class, id);
	}
	

}
