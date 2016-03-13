package com.lshop.manage.sysfile.service.impl;

import java.io.File;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.gv.core.datastructure.Dto;
import com.gv.core.exception.ServiceException;
import com.gv.core.hibernate3.HibernateBaseDAO;
import com.gv.core.util.FileUpload;
import com.gv.core.util.ObjectUtils;
import com.lshop.manage.sysfile.service.UploadService;

@Service("UploadService")
public class UploadServiceImpl implements UploadService {
	private static final Log logger	= LogFactory.getLog(UploadServiceImpl.class);
	@Resource 
	private HibernateBaseDAO dao;
	@Override
	public String uploadImg(Dto dto) throws ServiceException {
		String dir=(String) dto.get("dir");
		File filedata=(File) dto.get("filedata");
		String filedataFileName=(String) dto.get("filedataFileName");
		String storeFlag=(String) dto.get("storeFlag");
		String resourcePath=(String) dto.get("resourcePath");
		HttpServletRequest request=(HttpServletRequest) dto.get("request");
		if(ObjectUtils.isNotEmpty(storeFlag)){//店铺标示
			//String filepath=request.getRealPath("/web/"+storeFlag+"/res");
			String filepath=resourcePath+"/"+storeFlag+"/res";
			if (dir!=null&&!"".equals(dir.trim())) {
				filepath+=dir;
			}
			File file=new File(filepath);
			if(!file.exists()){//判断文件目录是否存在，不存在就重新创建一个目录结构
				file.mkdirs();
			}
			FileUpload.upload(filedata, filepath, filedataFileName);
			String url = "/"+storeFlag+"/res"+dir+"/"+filedataFileName;
			return url;
		}
        return null;
	}
}
