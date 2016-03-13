package com.lshop.manage.sysfile.action;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.gv.base.springSecurity.common.pojo.BaseUsers;
import com.gv.core.util.FileUpload;
import com.gv.core.util.ObjectUtils;
import com.lshop.common.util.Constants;
import com.lshop.manage.common.action.BaseManagerAction;

@SuppressWarnings("unchecked")
@Controller("UploadManagerAction")
@Scope("prototype")
public class UploadManagerAction extends BaseManagerAction{

	private File filedata;
	private String filedataFileName;
	private String dir;
	private String url;
	private String err;
	private String marking;
	private String storeFlag;
	



	
	public String uploadData() {
		String filepath=request.getRealPath("/res");
		if (dir!=null&&!"".equals(dir.trim())) {
			filepath+=dir;
		}
	    java.text.Format format=new SimpleDateFormat("yyyyMMddHHmmssSS");
		filedataFileName=format.format(new Date())+filedataFileName.substring(filedataFileName.lastIndexOf("."));
		FileUpload.upload(filedata, filepath, filedataFileName);
		url = "/res"+dir+"/" + filedataFileName;
		BaseUsers user=	(BaseUsers) getSession().getAttribute("USER");
		String nowmain=request.getServerName();
		String domain="";
		for(Map.Entry<String, String> entry:Constants.STORE_IDS.entrySet()){   
		    if(user.getStoreFlag().equals(entry.getValue())){
		    	domain=entry.getKey();
		    	break;
		    }
		}
		if("".equals(domain)){
			domain="www"+nowmain.substring(nowmain.indexOf("."));
		}
		url="http://"+domain+url;
		return JSON;
	}
	/**
	 * 
	 * @Method: uploadImgData 
	 * @Description:  [文本编辑器图片文件上传(带店铺标识)]  
	 * @Author:       [liaoxiongjian]     
	 * @CreateDate:   [2012-8-25 下午03:44:09]   
	 * @UpdateUser:   [liaoxiongjian]     
	 * @UpdateDate:   [2012-8-25 下午03:44:09]   
	 * @UpdateRemark: [说明本次修改内容]  
	 * @return 
	 * @return String
	 */
	public String uploadImgData(){

//		String resourceDomain=this.getText("lshop.base.resource.domain");//获取根目录
		String resourcePath=this.getText("lshop.base.resource.path");
		if(ObjectUtils.isEmpty(storeFlag)){
			BaseUsers user=	(BaseUsers) getSession().getAttribute("USER");
			String storeFlagTemp=user.getStoreFlag();
			String [] arr= storeFlagTemp.split(",");
			storeFlag=arr[0];
		}
		
		String systemFlag=Constants.STORE_TO_MALL_SYSTEM.get(storeFlag);
		String domainPostfix=Constants.MALL_TO_DOMAIN_POSTFIX.get(systemFlag);
		java.text.Format format=new SimpleDateFormat("yyyyMMddHHmmssSS");
		filedataFileName=marking+"_"+format.format(new Date())+filedataFileName.substring(filedataFileName.lastIndexOf("."));
		dto.put("dir", dir);
		dto.put("filedata", filedata);
		dto.put("request", request);
		dto.put("filedataFileName", filedataFileName);
		dto.put("storeFlag",storeFlag);
		dto.put("resourcePath", resourcePath);
		url=(String) this.doService("UploadService", "uploadImg", dto);
		
		String nowmain=request.getServerName();
		String domain="";
		for(Map.Entry<String, String> entry:Constants.STORE_IDS.entrySet()){   
		    if(storeFlag.equals(entry.getValue())){
		    	domain=entry.getKey();
		    	break;
		    }
		}
		if("".equals(domain)){
			domain="www"+nowmain.substring(nowmain.indexOf("."));
		}
//		url=resourceDomain+url;
		url="http://res"+domainPostfix+url;
		return JSON;
	}

	public String getFiledataFileName() {
		return filedataFileName;
	}

	public String getUrl() {
		return url;
	}

	public String getErr() {
		return err;
	}

	public void setFiledataFileName(String filedataFileName) {
		this.filedataFileName = filedataFileName;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setErr(String err) {
		this.err = err;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}
	public String getMarking() {
		return marking;
	}

	public void setMarking(String marking) {
		this.marking = marking;
	}

	public File getFiledata() {
		return filedata;
	}

	public void setFiledata(File filedata) {
		this.filedata = filedata;
	}
    
	public String getStoreFlag() {
		return storeFlag;
	}
	public void setStoreFlag(String storeFlag) {
		this.storeFlag = storeFlag;
	}
}
