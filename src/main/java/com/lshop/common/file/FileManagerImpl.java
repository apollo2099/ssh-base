package com.lshop.common.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;

import com.gv.core.datastructure.page.Pagination;
import com.gv.core.util.ObjectUtils;

import common.Logger;
public class FileManagerImpl implements FileManager {
	private Logger log = common.Logger.getLogger(this.getClass());
	/**
	 * 删除文件或文件夹
	 */
	private String root;// 应用所在绝对路径
	public FileManagerImpl() {
	}
	public FileManagerImpl(String root) {
		this.root = root;
	}
	
	@Override
	public int delete(String[] names) {
		// TODO Auto-generated method stub
		File file = null;
		int count = 0;
		for (String name : names) {
			name = this.getRoot() + "/" + name;
			file = new File(name);
			if (file.isDirectory()) {
				try {
					FileUtils.deleteDirectory(file);
					count++;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (file.isFile()) {
				file.delete();
				count++;
			}
		}
		return 0;
	}
	/**
	 * 获取单个文件
	 */
	@Override
	public Tpl get(String path) {
		// TODO Auto-generated method stub
		File file = new File(path);
		if (file.exists()) {
			return new FileTpl(file, path);
		}
		return null;
	}

	public List<Tpl> getChild(String path) {
		File file = new File(path);
		File[] child = file.listFiles();
		if (child != null) {
			List<Tpl> list = new ArrayList<Tpl>();
			for (File f : child) {
				if (f.exists()) {
					list.add(new FileTpl(f, f.getPath()));
				}
			}
			return list;
		} else {
			return new ArrayList<Tpl>(0);
		}
	}
	
	/**
	 * 获取分页文件(设置文件过滤规则并且设置时间排序)
	 */
	public Pagination getChild(String path,Pagination page,final String searchFileName){
		File file = new File(path);
		File[] child = file.listFiles(new FileFilter(){//创建文件或者文件夹过滤规则
			@Override
			public boolean accept(File pathname) {
				if(ObjectUtils.isNotEmpty(searchFileName)){
					if(pathname.toString().indexOf(searchFileName)!=-1){
						return true;
					}
				}else{
					return true;
				}
				return false;
			}
		});
		List<Tpl> list = new ArrayList<Tpl>();
		//设置时间排序
		if(ObjectUtils.isNotEmpty(child)){
			Arrays.sort(child, new LastModifiedFileComparator().LASTMODIFIED_REVERSE);			
			//设置分页查询其实数目
			Integer startNum=(page.getPageNum()-1)*page.getNumPerPage();
			Integer endNum;
			if(child.length>=page.getPageNum()*page.getNumPerPage()){
				endNum=page.getPageNum()*page.getNumPerPage();
			}else{
				endNum=child.length;
			}
			
			for(int i=startNum;i<endNum;i++){
				File f=child[i];
				if (f.exists()) {
					list.add(new FileTpl(f, f.getPath()));
				}
			}
			page.setTotalCount(child.length);
		}
		page.setList(list);
		return page;
	}
	
	
	@Override
	public List<? extends Tpl> getListByPrefix(String prefix) {

		return null;
	}

	@Override
	public List<String> getNameListByPrefix(String prefix) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void rename(String orig, String dist) {
		File source = new File(this.getRoot() + "/" + orig);
		File targer = new File(this.getRoot() + "/" + dist);
		source.renameTo(targer);

	}


	@Override
	public void update(String path, String source) {
		File file = new File(path);
		if (file.isFile() && file.exists()) {
			BufferedWriter writer = null;
			FileOutputStream output =null; 
			OutputStreamWriter stream =null;
			try {
				output =new FileOutputStream(path);
				stream =new OutputStreamWriter(output,"UTF-8");
				writer = new BufferedWriter(stream);
				writer.write(source);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if(writer!=null){
						writer.close();
					}
					if(stream!=null){
						stream.close();
					}
					if(output!=null){
						output.close();
					}
 				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void save(String path, String source) {
		// TODO Auto-generated method stub
		File file = new File(path);
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		BufferedWriter writer = null;
		FileOutputStream output =null; 
		OutputStreamWriter stream =null;
		try {
			output =new FileOutputStream(path);
			stream =new OutputStreamWriter(output,"UTF-8");
			writer = new BufferedWriter(stream);
			writer.write(source);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(writer!=null){
					writer.close();
				}
				if(stream!=null){
					stream.close();
				}
				if(output!=null){
					output.close();
				}
				} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public boolean isCheckPath(String path) {
		// TODO Auto-generated method stub
		if (path != null && !path.trim().equals("")
				&& path.startsWith(this.getRoot())) {
			return true;
		}
		return false;
	}
    
	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

}
