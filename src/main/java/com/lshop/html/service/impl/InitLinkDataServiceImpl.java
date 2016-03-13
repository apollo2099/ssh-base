package com.lshop.html.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.gv.core.datastructure.Dto;
import com.gv.core.hibernate3.HibernateBaseDAO;
import com.lshop.common.pojo.logic.LvLinkUrl;
import com.lshop.common.util.Constants;
import com.lshop.html.bean.HtmlStaticDto;
import com.lshop.html.service.HtmlStaticService;
import com.lshop.html.service.InitLinkDataService;

/**
 * 静态化服務類
 * 
 * @author xusl
 * 
 */
@Service("InitLinkDataService")
public class InitLinkDataServiceImpl implements InitLinkDataService {

	private static final Log logger = LogFactory.getLog(InitLinkDataServiceImpl.class);
	@Resource
	private HibernateBaseDAO dao;
	private Dto dto;

	public InitLinkDataServiceImpl() {

	}

	/**
	 * 批量初始化静态化页面 入口
	 * 
	 * @param vo
	 *            包含项说明
	 */
	public synchronized void init(Dto dto) {
		
		
		if (logger.isInfoEnabled()) {
			logger.info("***** InitLinkDataServiceImpl.init(BaseVo vo) method Begin*****");
		}
        this.dto=dto;
        new Thread(this).start();
		if (logger.isInfoEnabled()) {
			logger.info("***** InitLinkDataServiceImpl.init(BaseVo vo) method end*****");
		}
	}

	/**
	 * 批量生成静态化页面 不包含静态化地址的参数
	 * 
	 * @param gvLinkUrl
	 * @param htmlpath
	 * @param domainname
	 */
	public void createHtmlImpl(LvLinkUrl gvLinkUrl, String htmlpath, String domainname) {
		if (logger.isInfoEnabled()) {
			logger.info("***** InitLinkDataServiceImpl.createHtmlImpl() method begin*****");
		}
		if (gvLinkUrl.getDynamicurl().indexOf("@") != -1 && !"".equals(gvLinkUrl.getHql())) {
			createHtmlByHql(gvLinkUrl, htmlpath, domainname);
		} else {
			//HtmlStaticThreds actionTask = new HtmlStaticThreds();
			HtmlStaticService taskserver = new HtmlStaticServiceImpl(gvLinkUrl, null, htmlpath, domainname);
			try {
				taskserver.createHtmlByURL();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
//			actionTask.setTaskserver(taskserver);
//			actionTask.start();
		}
		if (logger.isInfoEnabled()) {
			logger.info("***** InitLinkDataServiceImpl.createHtmlImpl() method end*****");
		}
	}

	/**
	 * 根据hql语句批量静态化
	 * 
	 * @param urlobj
	 * @param htmlpath
	 * @param domainname
	 */
	@SuppressWarnings("unchecked")
	public void createHtmlByHql(LvLinkUrl urlobj, String htmlpath, String domainname) {
		if (logger.isInfoEnabled()) {
			logger.info("***** InitLinkDataServiceImpl.createHtmlByHql() method begin*****");
		}
		List objs = dao.find(urlobj.getHql(), null);
		for (Object parms : objs) {
			HtmlStaticService taskserver = new HtmlStaticServiceImpl(urlobj, parms, htmlpath, domainname);
			try {
				taskserver.createHtmlByURL();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			HtmlStaticThreds actionTask = new HtmlStaticThreds();
//			actionTask.setTaskserver(taskserver);
//			actionTask.start();

		}
		if (logger.isInfoEnabled()) {
			logger.info("***** InitLinkDataServiceImpl.createHtmlByHql() method end*****");
		}
	}

	@Override
	public void run() {
		try {
			//线程执行任务启动
			//Constants.INITHTMLACTION_STATUS=0;
			List<HtmlStaticDto> list= (List<HtmlStaticDto>) dto.get("list");
			for (HtmlStaticDto htmlStaticDto : list) {
				String hql = "FROM LvLinkUrl WHERE storeFlag=:storeFlag";
				Map map = new HashMap();
				map.put("storeFlag", htmlStaticDto.getStoreFlag());
				List<LvLinkUrl> linkUrls = (List<LvLinkUrl>) dao.find(hql, map);
				// 如果单项说明为空 则所以项初始化
				// 否则 初始化单项
				String note = (String) dto.get("note");
				String domainname = htmlStaticDto.getDomainname();
				String htmlpath = htmlStaticDto.getHtmlpath();
				for (LvLinkUrl gvLinkUrl : linkUrls) {

					// 根据链接说明判断是批量静态化还是单项静态化
					if (note != null && !note.equals("")) {
						// 如果是单项静态化
						if (gvLinkUrl.getNote() != null && note.equals(gvLinkUrl.getNote().trim())) {
							createHtmlImpl(gvLinkUrl, htmlpath, domainname);
						}
					} else {
						createHtmlImpl(gvLinkUrl, htmlpath, domainname);
					}

				}
				
			}			
		} catch (Exception e) {
			//Constants.INITHTMLACTION_STATUS=-1;
			logger.error(e);
		} finally{
			//线程完成
			//Constants.INITHTMLACTION_STATUS=1;
		}
		
		
	}



}
