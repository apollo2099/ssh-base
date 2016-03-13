package com.lshop.ws.server.popularize.order.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import com.lshop.ws.server.popularize.order.bean.POrderDtoResposne;




@WebService(name = "WSPPLOrderService")
@SOAPBinding(style=Style.RPC)
public interface WSPPLOrderService {
	public POrderDtoResposne getOrderDetail(@WebParam(name = "orderListNo") String orderListNo);

}
