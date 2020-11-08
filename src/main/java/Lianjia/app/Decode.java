package Lianjia.app;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;




public class Decode {


	private String appSecret = "93273ef46a0b880faf4466c48f74878f";
	private String appId = "20170324_android";


	public Decode(String appSecret,String appId){
		this.appSecret = appSecret;
		this.appId = appId;
	}
	

	
	public String code(String url,boolean POST) {
		HashMap<String,String> hashMap = new HashMap<String,String>();
        if(url == null || url.length() == 0) {
            return null;
        }
        else {
//            HashMap<String,String> v1 = new HashMap<>();
//            https://app.api.lianjia.com/house/zufang/detailpart1?house_code=105102152777
//            https://app.api.lianjia.com/house/rented/search?city_id=440300&priceRequest=&limit_offset=40&communityRequset=&moreRequest=&is_suggestion=0&limit_count=20&comunityIdRequest=&sugQueryStr=&areaRequest=&is_history=0&condition=&roomRequest=&isFromMap=false

            String paras = url.split("\\?")[1];
            String[] parse = paras.split("&");
            for (String string : parse) {
				String[] keyValuePair = string.split("=");
				String key = keyValuePair[0];
				String value = "";
				try {
					value = keyValuePair[1];
				}catch (Exception e) {
					// TODO: handle exception
					value = "";
				}
				
				
				hashMap.put(key, value);
            	
			}
            
            
        }
        if(POST==true){
        	hashMap.put("request_ts",System.currentTimeMillis()/1000 + "");
		}


        ArrayList<String> keys = new ArrayList<String>(hashMap.keySet());
        
        Collections.sort(keys);
        
        StringBuilder sBuilder = new StringBuilder(appSecret);
        for (String string : keys) {
//        	System.out.println(string+"="+hashMap.get(string));
			sBuilder.append(string+"="+hashMap.get(string));
		}

        String arg6 = sBuilder.toString();
        String arg8 = "";


		try {
			MessageDigest v0 = MessageDigest.getInstance("SHA-1");
			v0.update(arg6.getBytes());
			byte[] v6_1 = v0.digest();
			StringBuffer v0_1 = new StringBuffer();
			int v2;
			for(v2 = 0; v2 < v6_1.length; ++v2) {
				String v3 = Integer.toHexString(v6_1[v2] & 255);
				if(v3.length() < 2) {
					v0_1.append(0);
				}

				v0_1.append(v3);
			}

			arg8 =  v0_1.toString();
		}
		catch(NoSuchAlgorithmException v6) {
			v6.printStackTrace();
			arg8 =  "";
		}

		StringBuilder v0_3 = new StringBuilder();
		v0_3.append(appId);
		v0_3.append(":");
		v0_3.append(arg8);
        String v0_1 =  Base64.encodeBase64String(v0_3.toString().getBytes());
//				Base64.encodeToString(v0_3.toString().getBytes(), 2);
        
        
        
        
        
        return v0_1;
        
    }




	
	
	
	
	
	

}
