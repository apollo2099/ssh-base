package com.lshop.common.payutil.payDollar.secure;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

import com.lshop.common.payutil.payDollar.secure.PaydollarSecure;
import com.lshop.common.payutil.payDollar.secure.PaydollarSecureException;
import com.lshop.common.payutil.payDollar.secure.SHAAlgorithmUtil;

public class SHAPaydollarSecure implements PaydollarSecure {

	private SHAAlgorithmUtil algorithmUtil;

	public SHAPaydollarSecure() {
		algorithmUtil = new SHAAlgorithmUtil();
	}

	public String generatePaymentSecureHash(String merchantId,
			String merchantReferenceNumber, String currencyCode, String amount,
			String paymentType, String secureHashSecret)
			throws PaydollarSecureException {

		StringBuffer buffer = new StringBuffer();
		buffer.append(merchantId).append("|").append(merchantReferenceNumber)
				.append("|").append(currencyCode).append("|").append(amount)
				.append("|").append(paymentType).append("|").append(
						secureHashSecret);

		try {

			return algorithmUtil.operationAlgorithm(buffer.toString());

		} catch (NoSuchAlgorithmException e) {
			throw new PaydollarSecureException(e);
		} catch (UnsupportedEncodingException e) {
			throw new PaydollarSecureException(e);
		} finally {
			buffer.delete(0, buffer.length());
		}

	}

	public boolean verifyPaymentDatafeed(String src, String prc,
			String successCode, String merchantReferenceNumber,
			String payDollarReferenceNumber, String currencyCode,
			String amount, String payerAuthenticationStatus,
			String secureHashSecret, String secureHash) {

		StringBuffer buffer = new StringBuffer();
		buffer.append(src).append("|").append(prc).append("|").append(
				successCode).append("|").append(merchantReferenceNumber)
				.append("|").append(payDollarReferenceNumber).append("|")
				.append(currencyCode).append("|").append(amount).append("|")
				.append(payerAuthenticationStatus).append("|").append(
						secureHashSecret);

		try {
			
			

			String verifyData = algorithmUtil.operationAlgorithm(buffer
					.toString());
			System.out.println("****************verify Begin**************");
			System.out.println("secureHash=["+secureHash+"]");
			System.out.println("ExpectedSecureHash=["+verifyData+"]");
			System.out.println("****************verify end**************");
			if (secureHash.equals(verifyData))
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		} finally {
			buffer.delete(0, buffer.length());
		}

	}

	public String loadSecureHashSecret() throws NullPointerException {
		URL configUrl = SHAPaydollarSecure.class
				.getResource("/secureHashSecret.config");
		System.out.println(configUrl.getFile());

		StringBuffer buffer = new StringBuffer();
		InputStream in = null;
		try {
			in = configUrl.openStream();
			 

			 
				byte[] data = new byte[1024];

				int readLength=in.read(data);
				while(readLength>0){
					String dataStr= new String(data,0,readLength);
					buffer.append(dataStr);
					readLength=in.read(data);
				}
			 

				
				 
			 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			throw new NullPointerException(
					"It can not load the Secrue Hash Secret Data!\n"
							+ e.getMessage());
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		 
		 

		return buffer.toString();
	}

}
