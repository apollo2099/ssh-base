package com.lshop.ws.server.bz.order.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.gv.core.hibernate3.HibernateBaseDAO;
import com.gv.core.service.impl.BaseServiceImpl;
import com.gv.core.util.ObjectUtils;
import com.lshop.common.pojo.logic.LvOrderDetails;
import com.lshop.common.pojo.logic.LvOrderPackageDetails;
import com.lshop.common.util.DateUtils;
import com.lshop.ws.server.bz.order.bean.LvOrderDto;
import com.lshop.ws.server.bz.order.bean.LvOrderDtoResposne;
import com.lshop.ws.server.bz.order.bean.LvProductDto;
import com.lshop.ws.server.bz.order.bean.Result;


@Service("WSOrderService")
@WebService(endpointInterface = "com.lshop.ws.server.bz.order.service.WSOrderService")
public class WSOrderServiceImpl extends BaseServiceImpl implements WSOrderService {

	@Resource
	private HibernateBaseDAO dao;
	
	private static final Log logger	= LogFactory.getLog(WSOrderServiceImpl.class);
	
	/**
	 * 同步已经审核的订单到商务接口定义
	 * @Method: synAuditOrderToBZ 
	 * @Description:  
	 * @param startTime  审核开始时间（查询范围起始时间，格式：yyyy-mm-dd HH:mm:ss）
	 * @param endTime    审核结束时间（查询范围结束时间，格式：yyyy-mm-dd HH:mm:ss）
	 * @return OrderList
	 */
	@Override
	public LvOrderDtoResposne synAuditOrderToBZ(String startTime, String endTime) {
		if(logger.isInfoEnabled()){
			  logger.info("*****WSOrderServiceImpl.synAuditOrderToBZ() method begin*****");
		}
		
		LvOrderDtoResposne lvOrderDtoResposne=new LvOrderDtoResposne();
		List<LvOrderDto> orderList=new ArrayList<LvOrderDto>();
		//判断查询日期时间差
		
		//根据审核时间查询已经审核订单集合
		try {
			if(ObjectUtils.isNotEmpty(startTime)&&ObjectUtils.isNotEmpty(endTime)){
				String hql=" select DISTINCT ls.ord as ord from  LvOrderLogs ls,LvOrder o " +
						" where ls.ord=o.oid " +
						" and o.isServiceAudit=1 " +
						" and o.isFinanceAudit=1 " +
						" and o.isdelete=0 " +
						" and ls.createTime>=:startTime and ls.createTime <=:endTime ";
				Map param=new HashMap();
				param.put("startTime", DateUtils.convertToDateTime(startTime));
				param.put("endTime", DateUtils.convertToDateTime(endTime));
				List listTmp = dao.getMapListByHql(hql, param).getList();
				if(ObjectUtils.isNotEmpty(listTmp)){
					for (int i = 0; i < listTmp.size(); i++) {
						Map map=(Map) listTmp.get(i);
						//查询订单信息
						LvOrderDto lvOrderDto=this.getOrderInfo(String.valueOf(map.get("ord")));
					    orderList.add(lvOrderDto);
					}
				}
			}
			lvOrderDtoResposne.setOrderList(orderList);
			lvOrderDtoResposne.getResult().setStatus(Result.STATUS_SUCCEED);
			lvOrderDtoResposne.getResult().setMessage("成功");
		} catch (Exception e) {
			lvOrderDtoResposne.getResult().setStatus(Result.STATUS_FAIL);
			lvOrderDtoResposne.getResult().setMessage(e.getMessage());
			e.printStackTrace();
		}
		
		if(logger.isInfoEnabled()){
			logger.info("*****WSOrderServiceImpl.synAuditOrderToBZ() method end*****");
		}
		return lvOrderDtoResposne;
	}
	
	
	/**
	 * 
	 * @Method: getOrderDetailsByOid 
	 * @Description: 根据订单号查询订单详情及对应的产品信息
	 * @param oid  订单号
	 * @return List<LvProductDto>
	 */
	protected List<LvProductDto> getOrderDetailsByOid(String oid){
		//订单详情(产品信息)
		//"pcode":"产品型号Code","oprice":"单价","pnum":"数量","coupons":"优惠码","specialprice":"优惠金额"
		String hql="from LvOrderDetails as ld where ld.orderId=:orderId";
		Map param=new HashMap();
		param.put("orderId", oid);
		List<LvOrderDetails> arrList  =dao.find(hql, param);
		List<LvProductDto> products=new ArrayList<LvProductDto>();
		for(LvOrderDetails orderDetails : arrList){//遍历订单map信息
			if(orderDetails!=null){
			if(ObjectUtils.isNotEmpty(orderDetails.getIsPackage())&&orderDetails.getIsPackage()==1){//为套餐
				hql="from LvOrderPackageDetails where orderDetailsCode='"+orderDetails.getCode()+"'";
				List<LvOrderPackageDetails> tmpList  =dao.find(hql, null);
				for (LvOrderPackageDetails lvOrderPackageDetails : tmpList) {
					if(lvOrderPackageDetails!=null){
						LvProductDto lvProductDto=new  LvProductDto();	
						if (ObjectUtils.isNotEmpty(lvOrderPackageDetails.getPcode())) {
							lvProductDto.setPcode(lvOrderPackageDetails.getPcode());
						}else{
							lvProductDto.setPcode("");
						}
						if (ObjectUtils.isNotEmpty(lvOrderPackageDetails.getOprice())) {
							lvProductDto.setOprice(lvOrderPackageDetails.getOprice());
						}else{
							lvProductDto.setOprice(0.0f);
						}
						if (ObjectUtils.isNotEmpty(lvOrderPackageDetails.getPnum())) {
							lvProductDto.setPunm(lvOrderPackageDetails.getPnum());
						}else{
							lvProductDto.setPunm(0);
						}
						if (ObjectUtils.isNotEmpty(lvOrderPackageDetails.getCouponCode())) {
							lvProductDto.setCoupons(lvOrderPackageDetails.getCouponCode());
						}else{
							lvProductDto.setCoupons("");
						}
						if (ObjectUtils.isNotEmpty(lvOrderPackageDetails.getCouponPrice())) {
							lvProductDto.setSpecialprice(lvOrderPackageDetails.getCouponPrice());
						}else{
							lvProductDto.setSpecialprice(0.0f);
						}
						products.add(lvProductDto);
					}
				}
			}else{//非套餐
				LvProductDto lvProductDto=new  LvProductDto();		
				if (ObjectUtils.isNotEmpty(orderDetails.getPcode())) {
					lvProductDto.setPcode(orderDetails.getPcode());
				}else{
					lvProductDto.setPcode("");
				}
				if (ObjectUtils.isNotEmpty(orderDetails.getOprice())) {
					lvProductDto.setOprice(orderDetails.getOprice());
				}else{
					lvProductDto.setOprice(0.0f);
				}
				if (ObjectUtils.isNotEmpty(orderDetails.getPnum())) {
					lvProductDto.setPunm(orderDetails.getPnum());
				}else{
					lvProductDto.setPunm(0);
				}
				if (ObjectUtils.isNotEmpty(orderDetails.getCouponCode())) {
					lvProductDto.setCoupons(orderDetails.getCouponCode());
				}else{
					lvProductDto.setCoupons("");
				}
				if (ObjectUtils.isNotEmpty(orderDetails.getCouponPrice())) {
					lvProductDto.setSpecialprice(orderDetails.getCouponPrice());
				}else{
					lvProductDto.setSpecialprice(0.0f);
				}
				products.add(lvProductDto);
			}
			}
		}
		return products;
	}
	
	/**
	 * 
	 * @Method: getOrderInfo 
	 * @Description: 根据订单号查询订单
	 * @param oid  订单号
	 * @return List<LvProductDto>
	 */
	protected LvOrderDto getOrderInfo(String oid){
		 LvOrderDto lvOrderDto = new LvOrderDto();
		if(ObjectUtils.isNotEmpty(oid)){
			//根据订单号查询订单信息
			String hql = "SELECT lvorder.oid AS oid,lvOrderAdress.relName AS relname,la.code as contryId ," +
			"lvOrderAdress.contryName AS contryname,lvOrderAdress.provinceName AS provincename,lvOrderAdress.cityName AS cityname," +
			"lvOrderAdress.adress AS adress,lvOrderAdress.postCode AS postcode,lvOrderAdress.tel AS tel,lvOrderAdress.mobile AS mobile, " +
			"lvorder.userEmail AS email, lvorder.postprice AS postage, lvorder.totalPrice AS totalPrice," +
			"lvorder.paymethod AS paymethod,lvorder.overtime AS overtime,lvorder.thirdPartyOrder as paynumber,lvorder.storeId as source, " +
			"lvorder.orderRemark AS oremark,lvorder.createTime AS createtime," +
			"lvorder.sendRemark AS sendremark,'' AS description,lvorder.currency as currency " +
			"FROM LvOrder AS lvorder,LvOrderAddress AS lvOrderAdress,LvArea as la  " +
			"WHERE   lvOrderAdress.orderId=lvorder.oid  " +
			"AND lvOrderAdress.contryId=la.id  " +
			"AND lvorder.oid=:oid";
			Map param=new HashMap();
			param.put("oid", oid);
			List orderListTmp=dao.getMapListByHql(hql, param).getList();
			if(ObjectUtils.isNotEmpty(orderListTmp)){
			  for (int k = 0; k < orderListTmp.size(); k++) {
				  Map orderMap = (Map) orderListTmp.get(k);
				  if(ObjectUtils.isNotEmpty(orderMap.get("oid"))){
					  lvOrderDto.setOid(String.valueOf(orderMap.get("oid")));  
				  }else{
					  lvOrderDto.setOid("");
				  }
				  if(ObjectUtils.isNotEmpty(orderMap.get("relname"))){
					  lvOrderDto.setRelname(String.valueOf(orderMap.get("relname")));  
				  }else{
					  lvOrderDto.setRelname("");  
				  }
				  if(ObjectUtils.isNotEmpty(orderMap.get("contryId"))){
					  lvOrderDto.setContryId(String.valueOf(orderMap.get("contryId"))); 
				  }else{
					  lvOrderDto.setContryId("");
				  }
				  if(ObjectUtils.isNotEmpty(orderMap.get("contryname"))){
					  lvOrderDto.setContryname(String.valueOf(orderMap.get("contryname")));  
				  }else{
					  lvOrderDto.setContryname("");
				  }
				  if(ObjectUtils.isNotEmpty(orderMap.get("provincename"))){
					  lvOrderDto.setProvincename(String.valueOf(orderMap.get("provincename")));
				  }else{
					  lvOrderDto.setProvincename("");
				  }
				  if(ObjectUtils.isNotEmpty(orderMap.get("adress"))){
					  lvOrderDto.setAdress(String.valueOf(orderMap.get("adress")));
				  }else{
					  lvOrderDto.setAdress("");
				  }
				  if(ObjectUtils.isNotEmpty(orderMap.get("postcode"))){
					  lvOrderDto.setPostcode(String.valueOf(orderMap.get("postcode")));
				  }else{
					  lvOrderDto.setPostcode("");
				  }
				  if(ObjectUtils.isNotEmpty(orderMap.get("mobile"))){
					  lvOrderDto.setMobile(String.valueOf(orderMap.get("mobile")));
				  }else{
					  lvOrderDto.setMobile("");
				  }
				  if(ObjectUtils.isNotEmpty(orderMap.get("email"))){
					  lvOrderDto.setEmail(String.valueOf(orderMap.get("email")));
				  }else{
					  lvOrderDto.setEmail("");
				  }
				  if(ObjectUtils.isNotEmpty(orderMap.get("totalPrice"))){
					  lvOrderDto.setTotalPrice(Float.valueOf(orderMap.get("totalPrice").toString()));
				  }else{
					  lvOrderDto.setTotalPrice(0.0f);
				  }
				  if(ObjectUtils.isNotEmpty(orderMap.get("paymethod"))){
					  lvOrderDto.setPaymethod(Integer.valueOf(orderMap.get("paymethod").toString()));
				  }else{
					  lvOrderDto.setPaymethod(0);
				  }
				  if(ObjectUtils.isNotEmpty(orderMap.get("overtime"))){
					  lvOrderDto.setOvertime((Date) orderMap.get("overtime"));
				  }
				  if(ObjectUtils.isNotEmpty(orderMap.get("paynumber"))){
					  lvOrderDto.setPaynumber(String.valueOf(orderMap.get("paynumber")));
				  }else{
					  lvOrderDto.setPaynumber("");
				  }
				  if(ObjectUtils.isNotEmpty(orderMap.get("sendtime"))){
					  lvOrderDto.setSendtime((Date)orderMap.get("sendtime"));
				  }else{
					  
				  }
				  if(ObjectUtils.isNotEmpty(orderMap.get("oremark"))){
					  lvOrderDto.setOremark(String.valueOf(orderMap.get("oremark")));
				  }else{
					  lvOrderDto.setOremark("");
				  }
				  if(ObjectUtils.isNotEmpty(orderMap.get("createtime"))){
					  lvOrderDto.setCreatetime((Date)orderMap.get("createtime"));
				  }else{
					  
				  }
				  if(ObjectUtils.isNotEmpty(orderMap.get("description"))){
					  lvOrderDto.setDescription(String.valueOf(orderMap.get("description")));
				  }else{
					  lvOrderDto.setDescription("");
				  }
				  if(ObjectUtils.isNotEmpty(orderMap.get("currency"))){
					  lvOrderDto.setCurrency(String.valueOf(orderMap.get("currency")));
				  }else{
					  lvOrderDto.setCurrency("");
				  }
				  if(ObjectUtils.isNotEmpty(orderMap.get("sendremark"))){
					  lvOrderDto.setSendremark(String.valueOf(orderMap.get("sendremark")));
				  }else{
					  lvOrderDto.setSendremark("");
				  }
				  
				  lvOrderDto.setSource("1");//??
				  

				  //查询订单详情
				  List<LvProductDto> products=this.getOrderDetailsByOid(String.valueOf(oid));
			      lvOrderDto.setProducts(products);

			  }	
			}
		}
		return lvOrderDto;
	}
}
