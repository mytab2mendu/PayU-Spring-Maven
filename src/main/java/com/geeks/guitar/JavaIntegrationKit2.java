package com.geeks.guitar;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import sun.net.www.http.HttpClient;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author root
 */
public class JavaIntegrationKit2 {

    private Integer error;

    public boolean empty(String s) {
        if (s == null || s.trim().equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public String hashCal1(String type, String str) {
        byte[] hashseq = str.getBytes();
        StringBuffer sb = new StringBuffer();// method1
        try {
            MessageDigest algorithm = MessageDigest.getInstance(type);
            algorithm.reset();
            algorithm.update(hashseq);
            byte[] mdbytes = algorithm.digest();
            for (int i = 0; i < mdbytes.length; i++) {
            	sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            System.out.println("Hex format1 : " + sb.toString());

        } catch (NoSuchAlgorithmException nsae) {
        }
        return sb.toString();
    }
    
    public String hashCal(String type,String str){
    	// http://passwordsgenerator.net/sha512-hash-generator/
		byte[] hashseq=str.getBytes();
		StringBuffer hexString = new StringBuffer();
		try{
		MessageDigest algorithm = MessageDigest.getInstance(type);
		algorithm.reset();
		algorithm.update(hashseq);
		byte messageDigest[] = algorithm.digest();
            
		
		for (int i=0;i<messageDigest.length;i++) {
			String hex=Integer.toHexString(0xFF & messageDigest[i]);
			if(hex.length()==1) hexString.append("0");
			hexString.append(hex);
		}
			
		}catch(NoSuchAlgorithmException nsae){ }
		
		return hexString.toString();
	}

    private String getKey(String env){

    	if(env.equals("secure")){
    		return "gu3gUwmf";
    	}else{
    		return "gtKFFx";//test
    	}
    }
    
    private String getSalt(String env){
    	if(env.equals("secure")){

    		return "ZcoOKlVupo";
    	}else{
    		return "eCwWELxi";//test
    		
    	}
    }
    
    private String getBaseURL(String env){
    	if(env.equals("secure")){
    		return "https://secure.payu.in";
    		
    	}else{
    		return "https://test.payu.in";//test
    		
    	}
    }
    
    protected Map<String, String> hashCalMethod(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action1 = "";
        String env = (request.getParameter("env")== null || empty(request.getParameter("env")) ) ? "test" : request.getParameter("env");
        String base_url = getBaseURL(env);
        
        error = 0;
        String hashString = "";
        Enumeration paramNames = request.getParameterNames();
        
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String> urlParams = new HashMap<String, String>();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String paramValue = request.getParameter(paramName);
            params.put(paramName, paramValue);
        }
        
        String txnid = "";
        if (empty(params.get("txnid"))) {
            Random rand = new Random();
            String rndm = Integer.toString(rand.nextInt()) + (System.currentTimeMillis() / 1000L);
            txnid = rndm;
            params.remove("txnid");
            txnid = hashCal("SHA-256", rndm).substring(0, 20);
            params.put("txnid", txnid);
        } else {
            txnid = params.get("txnid");
        }
        if (empty(params.get("key"))) {
            params.put("key", getKey(env));
        }
        String hash = "";
        String otherPostParamSeq = "phone|surl|furl|lastname|curl|address1|address2|city|state|country|zipcode|pg";
        String hashSequence = "key|txnid|amount|productinfo|firstname|email|udf1|udf2|udf3|udf4|udf5|udf6|udf7|udf8|udf9|udf10";
        if (empty(params.get("hash")) && params.size() > 0) {
            if (empty(params.get("key")) || empty(txnid) || empty(params.get("amount")) || empty(params.get("firstname")) || empty(params.get("email")) || empty(params.get("phone")) || empty(params.get("productinfo")) || empty(params.get("surl")) || empty(params.get("furl")) || empty(params.get("service_provider"))) {
                error = 1;
            } else {
                
                String[] hashVarSeq = hashSequence.split("\\|");
                for (String part : hashVarSeq) {
                    
                	hashString = (empty(params.get(part))) ? hashString.concat("") : hashString.concat(params.get(part).trim());
                    urlParams.put(part, empty(params.get(part)) ? "" : params.get(part).trim());
                    hashString = hashString.concat("|");
                    
                }
                if (env.equals("secure")) {
                	urlParams.put("service_provider", params.get("service_provider"));
                }
                hashString = hashString.concat(getSalt(env));
                System.out.println(hashString);
                urlParams.put("hashString1", hashString);
                hash = hashCal("SHA-512", hashString);
                action1 = base_url.concat("/_payment");
                String[] otherPostParamVarSeq = otherPostParamSeq.split("\\|");
                for (String part : otherPostParamVarSeq) {
                    urlParams.put(part, empty(params.get(part)) ? "" : params.get(part).trim());
                }

            }
        } else if (!empty(params.get("hash"))) {
            hash = params.get("hash");
            action1 = base_url.concat("/_payment");
        }

        urlParams.put("hash", hash);
        urlParams.put("action", action1);
        urlParams.put("hashString", hashString);
        return urlParams;
    }
/*
    public void generateHashFromServer(PaymentParams mPaymentParams){
        nextButton.setEnabled(false); // lets not allow the user to click the button again and again.
        // lets create the post params
 
        StringBuffer postParamsBuffer = new StringBuffer();
        postParamsBuffer.append(concatParams(PayuConstants.KEY, mPaymentParams.getKey()));
        postParamsBuffer.append(concatParams(PayuConstants.AMOUNT, mPaymentParams.getAmount()));
        postParamsBuffer.append(concatParams(PayuConstants.TXNID, mPaymentParams.getTxnId()));
        postParamsBuffer.append(concatParams(PayuConstants.EMAIL, null == mPaymentParams.getEmail() ? "" : mPaymentParams.getEmail()));
        postParamsBuffer.append(concatParams(PayuConstants.PRODUCT_INFO, mPaymentParams.getProductInfo()));
        postParamsBuffer.append(concatParams(PayuConstants.FIRST_NAME, null == mPaymentParams.getFirstName() ? "" : mPaymentParams.getFirstName()));
        postParamsBuffer.append(concatParams(PayuConstants.UDF1, mPaymentParams.getUdf1() == null ? "" : mPaymentParams.getUdf1()));
        postParamsBuffer.append(concatParams(PayuConstants.UDF2, mPaymentParams.getUdf2() == null ? "" : mPaymentParams.getUdf2()));
        postParamsBuffer.append(concatParams(PayuConstants.UDF3, mPaymentParams.getUdf3() == null ? "" : mPaymentParams.getUdf3()));
        postParamsBuffer.append(concatParams(PayuConstants.UDF4, mPaymentParams.getUdf4() == null ? "" : mPaymentParams.getUdf4()));
        postParamsBuffer.append(concatParams(PayuConstants.UDF5, mPaymentParams.getUdf5() == null ? "" : mPaymentParams.getUdf5()));
        postParamsBuffer.append(concatParams(PayuConstants.USER_CREDENTIALS, mPaymentParams.getUserCredentials() == null ? PayuConstants.DEFAULT : mPaymentParams.getUserCredentials()));
 
        // for offer_key
        if(null != mPaymentParams.getOfferKey())
            postParamsBuffer.append(concatParams(PayuConstants.OFFER_KEY, mPaymentParams.getOfferKey()));
        // for check_isDomestic
        if(null != cardBin)
            postParamsBuffer.append(concatParams("card_bin", cardBin));
 
        String postParams = postParamsBuffer.charAt(postParamsBuffer.length() - 1) == '&amp;' ? postParamsBuffer.substring(0, postParamsBuffer.length() - 1).toString() : postParamsBuffer.toString();
        // make api call
        GetHashesFromServerTask getHashesFromServerTask = new GetHashesFromServerTask();
        getHashesFromServerTask.execute(postParams);
    }
 
 
    protected String concatParams(String key, String value) {
        return key + "=" + value + "&amp;";
    }
 
 
    class GetHashesFromServerTask extends AsyncTask&lt;String, String, PayuHashes&gt;{
 
        @Override
        protected PayuHashes doInBackground(String ... postParams) {
            PayuHashes payuHashes = new PayuHashes();
            try {
//                URL url = new URL(PayuConstants.MOBILE_TEST_FETCH_DATA_URL);
//                        URL url = new URL("http://10.100.81.49:80/merchant/postservice?form=2");;
 
                URL url = new URL("https://payu.herokuapp.com/get_hash");
                //You need to replace above URL with your server URL to calculate hash on server
 
                // get the payuConfig first
                String postParam = postParams[0];
 
                byte[] postParamsByte = postParam.getBytes("UTF-8");
 
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty("Content-Length", String.valueOf(postParamsByte.length));
                conn.setDoOutput(true);
                conn.getOutputStream().write(postParamsByte);
 
                InputStream responseInputStream = conn.getInputStream();
                StringBuffer responseStringBuffer = new StringBuffer();
                byte[] byteContainer = new byte[1024];
                for (int i; (i = responseInputStream.read(byteContainer)) != -1; ) {
                    responseStringBuffer.append(new String(byteContainer, 0, i));
                }
 
                JSONObject response = new JSONObject(responseStringBuffer.toString());
 
                Iterator<String> payuHashIterator = response.keys();
                while(payuHashIterator.hasNext()){
                    String key = payuHashIterator.next();
                    switch (key){
                        case "payment_hash":
                            payuHashes.setPaymentHash(response.getString(key));
                            break;
                        case "get_merchant_ibibo_codes_hash": //
                            payuHashes.setMerchantIbiboCodesHash(response.getString(key));
                            break;
                        case "vas_for_mobile_sdk_hash":
                            payuHashes.setVasForMobileSdkHash(response.getString(key));
                            break;
                        case "payment_related_details_for_mobile_sdk_hash":
                            payuHashes.setPaymentRelatedDetailsForMobileSdkHash(response.getString(key));
                            break;
                        case "delete_user_card_hash":
                            payuHashes.setDeleteCardHash(response.getString(key));
                            break;
                        case "get_user_cards_hash":
                            payuHashes.setStoredCardsHash(response.getString(key));
                            break;
                        case "edit_user_card_hash":
                            payuHashes.setEditCardHash(response.getString(key));
                            break;
                        case "save_user_card_hash":
                            payuHashes.setSaveCardHash(response.getString(key));
                            break;
                        case "check_offer_status_hash":
                            payuHashes.setCheckOfferStatusHash(response.getString(key));
                            break;
                        case "check_isDomestic_hash":
                            payuHashes.setCheckIsDomesticHash(response.getString(key));
                            break;
                        default:
                            break;
                    }
                }
 
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return payuHashes;
        }
    
    public static void trustSelfSignedSSL() {
        try {
            final SSLContext ctx = SSLContext.getInstance(
                    "TLS");
            final X509TrustManager tm = new X509TrustManager() {
                //@Override
                public void checkClientTrusted(final X509Certificate[] xcs, final String string) throws CertificateException {
// do nothing
                }

                //@Override
                public void checkServerTrusted(final X509Certificate[] xcs, final String string) throws CertificateException {
// do nothing
                }

                //@Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLContext.setDefault(ctx);
        } catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public String get_SHA_512_SecurePassword(String toBeHashed, String salt){
		//toBeHashed = Merchant_Key+"$MERCHANT_KEY.'|'.$txnid.'|'.$amount.'|'.$productinfo.'|'.$firstname.'|'.$email.'|||||||||||'.$SALT";
	String infoHashed = null;
	    try {
	         MessageDigest md = MessageDigest.getInstance("SHA-512");
			 md.update(salt.getBytes("UTF-8"));
	         byte[] bytes = md.digest(toBeHashed.getBytes("UTF-8"));
	         StringBuilder sb = new StringBuilder();
	         for(int i=0; i< bytes.length ;i++){
	            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	         }
	         infoHashed = sb.toString();
	        } 
	       catch (NoSuchAlgorithmException e){
	        e.printStackTrace();
	       } catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    return infoHashed;
	}
	*/
}
