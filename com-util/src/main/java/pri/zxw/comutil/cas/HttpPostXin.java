package pri.zxw.comutil.cas;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpPostXin {



	public static Response login(String url,Map<String,String> params)  {

		if(params.isEmpty()||params==null){
			return null;
		}
		String parm = "";
		Set<String> keySet = params.keySet();
		for (String key : keySet) {
			String value = params.get(key);
			parm=parm+key+"="+value+"&";
		}

		OkHttpClient client = new OkHttpClient();
		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		RequestBody body = RequestBody.create(mediaType, parm);
		Request request = new Request.Builder()
				.url(url)
				.post(body)
				.addHeader("Content-Type", "application/x-www-form-urlencoded")
				.addHeader("cache-control", "no-cache")
				.build();

		Response response;
		try {
			response = client.newCall(request).execute();
			return  response;
		} catch (IOException e) {

			e.printStackTrace();
		}
		return null;

	}
	public static boolean logout(String url, HttpServletResponse response2){
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
				.url(url)
				.delete(null)
				.addHeader("cache-control", "no-cache")
				.build();

		try {
			Response response = client.newCall(request).execute();
			response2.setStatus(response.code());
			return response.isSuccessful();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		response2.setStatus(401);
		return false;
	}
	/**
	 * @Title: postXWFU 
	 * @Description: 用于传入 x-www-form-urlencoded类型的post请求
	 * @author 林逸鑫 <br>
	 * @version 1.0
	 * 创建时间2019年2月20日下午2:20:38<br>
	 * @param url,params
	 * @return  JSONObject
	 * @throws: 
	 */
	public static JSONObject postXWFU(String url ,Map<String,String> params) {

		return null;
	}

	/**
	 * 
	 * @Title: getXWFU 
	 * @Description: get x-www-from-urlencode
	 * @author 林逸鑫 <br>
	 * @version 1.0
	 * 创建时间2019年2月28日上午10:56:31<br>
	 * @param url
	 * @param params
	 * @return  JSONObject
	 * @throws:
	 */
	public static JSONObject getXWFU(String url ,Map<String,String> params) {
		OkHttpClient client = new OkHttpClient();

		url = url + '?' + getUrlParamsByMap(params);
		Request request = new Request.Builder()
				.url(url)
				.get()
				.addHeader("Content-Type", "application/json")
				.addHeader("cache-control", "no-cache")
				.build();

		try {
			Response response = client.newCall(request).execute();
			String jsonString = response.body().string();
			if(jsonString == null) {
				return null;
			}

			return JSONObject.parseObject(jsonString);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @Title: postJSON 
	 * @Description: 用于传入json类型的post请求
	 * @author 林逸鑫 <br>
	 * @version 1.0
	 * 创建时间2019年2月20日下午2:26:31<br>
	 * @param url,params
	 * @return  JSONObject
	 * @throws:
	 */
	public static JSONObject postJSON(String url ,JSONObject params) {
		OkHttpClient client = new OkHttpClient();
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, params.toString());
		Request request = new Request.Builder()
				.url(url)
				.post(body)
				.addHeader("Content-Type", "application/json")
				.addHeader("cache-control", "no-cache")
				.build();
		JSONObject json = new JSONObject();
		try {
			Response response = client.newCall(request).execute();
			String jsonString = response.body().string();
			json.put("status", response.code());
			if(jsonString != null) {
				json.put("data", JSONObject.parseObject(jsonString));
			}
			return json;
		} catch (Exception e) {
			/**
			 * 9001 代表异常，将异常放入json返回
			 */
			e.printStackTrace();
			json.put("code", 9001);
			json.put("data", e.getMessage());
			return json;
		}
	}


	/**
	 * 
	 * @Title: getUrlParamsByMap 
	 * @Description: map->queryString
	 * @author 林逸鑫 <br>
	 * @version 1.0
	 * 创建时间2019年3月4日上午9:33:20<br>
	 * @param map
	 * @return  String
	 * @throws:
	 */

	public static String getUrlParamsByMap(Map<String, String> map) {
		if (map == null ) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (Entry<String, String> entry : map.entrySet()) {
			sb.append(entry.getKey() + "=" + entry.getValue());
			sb.append("&");
		}

		return sb.deleteCharAt(sb.length() - 1).toString();
	}
	/** 
	 * @Title: postJSONHeader 
	 * @Description: 
	 * @author 林逸鑫 <br>
	 * @version 1.0
	 * 创建时间2019年3月4日上午10:35:24<br>
	 * @param metabaseUrl
	 * @param json
	 * @return  JSONObject
	 * @throws: 
	 */

	public static JSONObject postJSONHeader(String url, JSONObject params, Map<String,String> headers) {
		OkHttpClient client = new OkHttpClient();
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, params.toString());
		Builder builder = new Request.Builder()
				.url(url)
				.post(body)
				.addHeader("Content-Type", "application/json")
				.addHeader("cache-control", "no-cache");
		/**
		 * 校验并添加headers
		 */
		if(headers!=null ) {
			if(!headers.isEmpty()) {
				for (Entry<String, String> entry : headers.entrySet()) {
					builder.addHeader(entry.getKey(), entry.getValue());
				}
			}	
		}
		Request request = builder.build();
		JSONObject json = new JSONObject();
		try {
			Response response = client.newCall(request).execute();
			String jsonString = response.body().string();
			json.put("status", response.code());
			if(jsonString != null) {
				json.put("data", JSONObject.parseObject(jsonString));
			}
			return json;
		} catch (Exception e) {
			/**
			 * 9001 代表异常，将异常放入json返回
			 */
			e.printStackTrace();
			json.put("code", 9001);
			json.put("message", e.getMessage());
			return json;
		}
	}


}
