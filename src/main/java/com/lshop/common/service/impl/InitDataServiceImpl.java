package com.lshop.common.service.impl;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import net.sf.json.JSONObject;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.gv.core.hibernate3.HibernateBaseDAO;
import com.gv.core.message.jms.JmsMessageQueueSender;
import com.lshop.common.service.InitDataService;
import com.lshop.common.util.Constants;

@Lazy(false)
@Service("InitDataService")
public class InitDataServiceImpl implements InitDataService {
	
	@Resource 
	private HibernateBaseDAO dao;
	@Resource
	private JmsMessageQueueSender messageQueueSenderLshopManager;

	@PostConstruct
	@Override
	public void init() {
		// TODO Auto-generated method stub
		//initStoreIds();
		//initRateNumMap();
		//initMallSystem();
		//sendJSONToWeb();
	}


    /**
     * 
     * @Method: initStoreIds 
     * @Description:  [商城域名和商城与商家的关系初始化数据加载]  
     * @Author:       [liaoxiongjian]     
     * @CreateDate:   [2013-1-14 上午10:36:32]   
     * @UpdateUser:   [liaoxiongjian]     
     * @UpdateDate:   [2013-1-14 上午10:36:32]   
     * @UpdateRemark: [说明本次修改内容]   
     * @return void
     */
	@Override
	public Integer initStoreIds() {
     return null;
	}


	/**
	 * 
	 * @Method: initMallDomainPostfix 
	 * @Description:  [初始化商城体系和商城域名后缀的对应关系]  
	 * @Author:       [liaoxiongjian]     
	 * @CreateDate:   [2013-11-19 下午1:27:09]   
	 * @UpdateUser:   [liaoxiongjian]     
	 * @UpdateDate:   [2013-11-19 下午1:27:09]   
	 * @UpdateRemark: [说明本次修改内容]   
	 * @return void
	 */
	private void initMallSystem() {
//		Constants.MALL_TO_DOMAIN_POSTFIX.clear();
//		Constants.MALL_SYSTEM_TO_STORE.clear();
//		String hql="from LvMallSystem";
//		List<LvMallSystem> listMall= dao.find(hql, null);
//		if(listMall!=null&&listMall.size()>0){
//			for (LvMallSystem lvMallSystem : listMall) {
//				Constants.MALL_TO_DOMAIN_POSTFIX.put(lvMallSystem.getMallSystemFlag(), lvMallSystem.getDomainPostfix());
//				//存储商城体系标志对应商城店铺标志
//				hql="from LvStore where mallSystem=:mallSystem";
//				Map<String,String> param=new HashMap<String, String>();
//				param.put("mallSystem", lvMallSystem.getMallSystemFlag());
//				List<LvStore> storeList=dao.find(hql, param);
//				if(storeList!=null&&storeList.size()>0){
//					List<String> list=(List) new ArrayList<String>();
//					for (int i = 0; i < storeList.size(); i++) {
//						LvStore store=storeList.get(i);
//						list.add(store.getStoreFlag());
//					}
//					Constants.MALL_SYSTEM_TO_STORE.put(lvMallSystem.getMallSystemFlag(), list);
//				}
//			}
//		}
	}
    /**
     * 
     * @Method: initRateNumMap 
     * @Description:  [币种兑换率初始化数据加载]  
     * @Author:       [liaoxiongjian]     
     * @CreateDate:   [2013-1-14 上午10:36:32]   
     * @UpdateUser:   [liaoxiongjian]     
     * @UpdateDate:   [2013-1-14 上午10:36:32]   
     * @UpdateRemark: [说明本次修改内容]   
     * @return void
     */
	public void initRateNumMap(){
//		String hql="select t from LvExchangeRate t where 1=1";
//		List<LvExchangeRate> list=dao.find(hql, null);
//		for (LvExchangeRate lvExchangeRate : list) {
//			//目前只是处理人民币与美元的兑换
//			if(lvExchangeRate.getCurrency().equals("CYN")){
//				Constants.rateNum=lvExchangeRate.getExchangeRate();
//			}
//		}
	}
	
	protected void sendJSONToWeb() {
		Map param = new HashMap();
		param.put("rateNum", Constants.rateNum);//汇率信息
		param.put("STORE_IDS", Constants.STORE_IDS);
		param.put("STORE_LIST", Constants.STORE_LIST);
		param.put("STORE_LIST_ALL", Constants.STORE_LIST_ALL);
		param.put("STORE_TO_MALL_SYSTEM", Constants.STORE_TO_MALL_SYSTEM);
		param.put("STORE_FLAG_TO_IDS", Constants.STORE_FLAG_TO_IDS);
		param.put("MALL_TO_DOMAIN_POSTFIX", Constants.MALL_TO_DOMAIN_POSTFIX);
		param.put("MALL_SYSTEM_TO_STORE", Constants.MALL_SYSTEM_TO_STORE);
		messageQueueSenderLshopManager.sendObject(JSONObject.fromObject(param).toString());
	}
}
