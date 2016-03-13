//package com.lshop.ws.server.popularize.order.service;
//
//
//import java.util.ArrayList;
//import java.util.List;
//import javax.annotation.Resource;
//import javax.jws.WebParam;
//import javax.jws.WebService;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.stereotype.Service;
//import com.gv.core.exception.ServiceException;
//import com.gv.core.hibernate3.HibernateBaseDAO;
//import com.gv.core.util.ObjectUtils;
//import com.lshop.common.pojo.logic.LvOrder;
//import com.lshop.common.pojo.logic.LvOrderDetails;
//import com.lshop.common.util.DateUtils;
//import com.lshop.manage.lvOrder.service.LvOrderDetailsService;
//import com.lshop.manage.lvOrder.service.LvOrderService;
//import com.lshop.ws.server.popularize.order.bean.POrderDto;
//import com.lshop.ws.server.popularize.order.bean.POrderDtoResposne;
//import com.lshop.ws.server.popularize.order.bean.PProductDto;
//import com.lshop.ws.server.popularize.order.bean.Result;
//
//import freemarker.template.utility.DateUtil;
//
//
//
//@Service("WSPPLOrderService")
//@WebService(endpointInterface = "com.lshop.ws.server.popularize.order.service.WSPPLOrderService")
//public class WSPPLOrderServiceImpl implements WSPPLOrderService {
//
//	@Resource
//	private HibernateBaseDAO dao;
////	@Resource
////	private LvOrderService lvOrderService;
////	@Resource
////	private LvOrderDetailsService lvOrderDetailsService;
//	
//	private static final Log logger	= LogFactory.getLog(WSPPLOrderServiceImpl.class);
//	
//	/**
//	 * 根据订单号查询订单详情信息
//	 * @Method: getOrderDetail  
//	 * @param orderListNo  订单号(多个订单号用","隔开)
//	 * @return POrderDtoResposne
//	 */
//	@Override
//	public POrderDtoResposne getOrderDetail(
//			@WebParam(name = "orderListNo") String orderListNo) {
//
//		if(logger.isInfoEnabled()){
//			  logger.info("*****WSPPLOrderServiceImpl.getOrderDetail() method begin*****");
//		}
//		
//		POrderDtoResposne pOrderResposne = new POrderDtoResposne();
//		try {
//			List<POrderDto> orderListTmp=new ArrayList<POrderDto>();
//			String [] arrTmp=orderListNo.split(",");
//			for (int num = 0; num < arrTmp.length; num++) {
//				String orderNo=arrTmp[num];
//				//根据订单号查询订单信息
//				LvOrder lvOrder=lvOrderService.getOrder(orderNo);
//				if(lvOrder!=null){
//					POrderDto orderDto=new POrderDto();
//					orderDto.setOrderNo(lvOrder.getOid());
//					if(ObjectUtils.isNotEmpty(lvOrder.getCurrency())){//币种
//						orderDto.setCurrency(lvOrder.getCurrency());
//					}else{
//						orderDto.setCurrency("");
//					}
//					if(ObjectUtils.isNotEmpty(lvOrder.getOvertime())){//支付时间
//						orderDto.setPayTime(DateUtils.formatDate(lvOrder.getOvertime(), "yyyy-MM-dd HH:mm:ss"));
//					}else{
//						orderDto.setPayTime("");
//					}
//					if(ObjectUtils.isNotEmpty(lvOrder.getCreateTime())){
//						orderDto.setCreateTime(DateUtils.formatDate(lvOrder.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
//					}else{
//						orderDto.setCreateTime("");
//					}
//					Short payStatus= lvOrder.getPayStatus();//支付状态
//					Short status=lvOrder.getStatus();//订单状态
//					Short isdelete=lvOrder.getIsdelete();//删除状态
//					//返回状态定义：1已支付正常订单，2异常订单（删除或者退货的）,-1未支付订单
//					if(payStatus==1&&status!=3&&isdelete==0){
//						orderDto.setStatus(1);
//					}else if(payStatus==0&&status!=3&&isdelete==0){
//						orderDto.setStatus(-1);
//					}else if(payStatus==2&&status!=3&&isdelete==0){
//						orderDto.setStatus(-1);
//					}else if(status==3||isdelete==-1){
//						orderDto.setStatus(2);
//					}
//					
//					
//					//根据订单号查询订单详情信息
//					List<LvOrderDetails> orderDetailsList=lvOrderDetailsService.getOrderDetails(orderNo);
//					if(ObjectUtils.isNotEmpty(orderDetailsList)){
//						List<PProductDto> products=new ArrayList<PProductDto>();
//						for (int i = 0; i < orderDetailsList.size(); i++) {
//							LvOrderDetails orderDetails=orderDetailsList.get(i);
//							PProductDto productDto=new PProductDto();
//							if(ObjectUtils.isNotEmpty(orderDetails.getPnum())){//购买数量
//								productDto.setBuyNum(orderDetails.getPnum());
//							}else{
//								productDto.setBuyNum(0);
//							}
//							if(ObjectUtils.isNotEmpty(orderDetails.getOprice())){//购买单价
//								productDto.setBuyPrice(orderDetails.getOprice());
//							}else{
//								productDto.setBuyPrice(0.0f);
//							}
//							if(ObjectUtils.isNotEmpty(orderDetails.getProductCode())){//购买商品code
//								productDto.setProductNo(orderDetails.getProductCode());
//							}else{
//								productDto.setProductNo("");
//							}
//							products.add(productDto);
//						}
//						orderDto.setProducts(products);
//					}
//					
//					
//					orderListTmp.add(orderDto);
//				}
//			}
//			
//			pOrderResposne.getResult().setMessage("成功");
//			pOrderResposne.getResult().setStatus(Result.STATUS_SUCCEED);
//			pOrderResposne.setPOrderList(orderListTmp);
//		} catch (ServiceException e) {
//			pOrderResposne.getResult().setMessage("查询订单信息异常："+e.getMessage());
//			pOrderResposne.getResult().setStatus(Result.STATUS_FAIL);
//			e.printStackTrace();
//		} catch (IllegalArgumentException e) {
//			pOrderResposne.getResult().setMessage("查询订单信息异常："+e.getMessage());
//			pOrderResposne.getResult().setStatus(Result.STATUS_FAIL);
//			e.printStackTrace();
//		} catch (Exception e) {
//			pOrderResposne.getResult().setMessage("查询订单信息异常："+e.getMessage());
//			pOrderResposne.getResult().setStatus(Result.STATUS_FAIL);
//			e.printStackTrace();
//		}
//		
//		if(logger.isInfoEnabled()){
//			logger.info("*****WSPPLOrderServiceImpl.getOrderDetail() method end*****");
//		}
//		return pOrderResposne;
//	}
//
//}
