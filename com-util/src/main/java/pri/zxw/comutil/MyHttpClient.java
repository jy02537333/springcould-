package pri.zxw.comutil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName: DataopenExceptions
 * @Description: httpClient工具类
 * @author: WB
 * @update:林逸鑫1月17日修改,为所有post方法解决了重定向处理问题
 * @date: 2019年1月4日 上午10:26:53
 */
public class MyHttpClient {

	private static CloseableHttpClient client = HttpClients.createDefault();

	private static RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(6000)
			.setConnectTimeout(6000).setSocketTimeout(6000).build();

	private static final Logger logger = LoggerFactory.getLogger(MyHttpClient.class);

	public static String getMethod(String url) throws Exception {
		HttpGet get = null;
		CloseableHttpResponse response = null;
		InputStream in = null;
		try {
			get = new HttpGet(url);
			get.setConfig(requestConfig);
			String result = "";
			response = client.execute(get);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				long len = entity.getContentLength();
				if (len != -1 && len < 2048) {
					result = EntityUtils.toString(entity);
				} else {
					entity = new BufferedHttpEntity(entity);
					in = entity.getContent();
					byte[] read = new byte[1024];
					byte[] all = new byte[0];
					int num;
					while ((num = in.read(read)) > 0) {
						byte[] temp = new byte[all.length + num];
						System.arraycopy(all, 0, temp, 0, all.length);
						System.arraycopy(read, 0, temp, all.length, num);
						all = temp;
					}
					result = new String(all, "UTF-8");
				}
			}
			return result;
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			try {
				if (in != null) {
					in.close();
					response.close();
					get.releaseConnection();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static String getMethod(String url, Header header) {
		HttpGet httpGet = null;
		CloseableHttpResponse response = null;

		try {
			httpGet = new HttpGet(url);
			httpGet.setHeader(header);
			response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			String result = EntityUtils.toString(entity);
			return result;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Map<String, Object> getWithStatus(String url) {
		HttpGet httpGet = null;
		CloseableHttpResponse response = null;
		Map<String, Object> result = new HashMap<String, Object>();
		try {

			httpGet = new HttpGet(url);
			httpGet.setHeader("accept", "application/json");
			httpGet.setHeader("Content-Type", "application/json;charset=UTF-8");
			response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			result.put("status", response.getStatusLine().getStatusCode());
			result.put("result", EntityUtils.toString(entity));
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result.put("status", 500);
		} catch (IOException e) {
			e.printStackTrace();
			result.put("status", 500);
		}
		return result;
	}

	public static JSONObject getJSONWithStatus(String url) {
		HttpGet httpGet = null;
		CloseableHttpResponse response = null;
		JSONObject result = new JSONObject();
		try {
			httpGet = new HttpGet(url);
			httpGet.setHeader("accept", "application/json");
			httpGet.setHeader("Content-Type", "application/json;charset=UTF-8");
			response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			result.put("status", response.getStatusLine().getStatusCode());
			result.put("data", EntityUtils.toString(entity));
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result.put("status", 500);
		} catch (IOException e) {
			e.printStackTrace();
			result.put("status", 500);
		}
		return result;
	}

	public static Map<String, Object> getMethod(String url, Map<String, String> params) {

		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (params != null) {
				List<NameValuePair> formparams = new ArrayList<NameValuePair>();
				for (Entry<String, String> param : params.entrySet()) {
					formparams.add(new BasicNameValuePair(param.getKey(), String.valueOf(param.getValue())));
				}
				String suffix = EntityUtils.toString(new UrlEncodedFormEntity(formparams, Consts.UTF_8));
				url = url + "?" + suffix;
			}
			result = getWithStatus(url);

		} catch (Exception ex) {
			ex.printStackTrace();
			result.put("status", 500);
		}

		return result;
	}

	public static String postMethod(String url, @SuppressWarnings("rawtypes") Map parameters) throws Exception {
		StringBuilder log = new StringBuilder();
		log.append("MyHttpClient post, url=");
		log.append(url);
		log.append(", params=");
		Iterator iterator = parameters.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry entry = (Entry) iterator.next();
			log.append("key:" + entry.getKey() + " value:" + entry.getValue());
		}
		long begin = System.currentTimeMillis();
		HttpPost post = null;
		CloseableHttpResponse response = null;
		try {
			post = new HttpPost(url);
			post.setHeader("accept", "application/json");
			post.setHeader("Content-Type", "application/json;charset=UTF-8");
			// post.addHeader("Content-type", "application/json;charset=UTF-8");
			// post.addHeader("Accept-Language", Locale.getDefault().toString());
			// post.addHeader("accept", "*/*");
			// post.addHeader("connection", "Keep-Alive");
			// post.addHeader("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT
			// 5.1;SV1)");

			// post.setHeader(HttpHeaders.CONTENT_TYPE, "multipart/form-data");
			if (parameters != null) {
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				@SuppressWarnings("rawtypes")
				Iterator it = parameters.entrySet().iterator();
				while (it.hasNext()) {
					@SuppressWarnings("rawtypes")
					Entry entry = (Entry) it.next();
					String key = (String) entry.getKey();
					String value = (String) entry.getValue();
					nvps.add(new BasicNameValuePair(key, value));
				}
				post.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
			}
			response = client.execute(post);
			HttpEntity entity = response.getEntity();
			String result = EntityUtils.toString(entity);
			log.append("|Time-consuming:" + (System.currentTimeMillis() - begin));
			log.append("|Response content:");
			log.append(result);
			// Logit.apiAccessLog(log.toString());
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			log.append(e);
		} finally {
			try {
				response.close();
				post.releaseConnection();
			} catch (Exception e) {
				e.printStackTrace();
				log.append(e);
				// Logit.apiAccessLog(log.toString());
			}
			// Logit.apiAccessLog(log.toString());
		}
		// Logit.apiAccessLog(log.toString());
		return null;
	}

	public static String postMethod(String url, Map parameters, Header header) throws Exception {
		HttpPost post = null;
		CloseableHttpResponse response = null;
		try {
			post = new HttpPost(url);
			post.setHeader(header);
			if (parameters != null) {
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				Iterator it = parameters.entrySet().iterator();
				while (it.hasNext()) {
					Entry entry = (Entry) it.next();
					String key = (String) entry.getKey();
					String value = (String) entry.getValue();
					nvps.add(new BasicNameValuePair(key, value));
				}
				post.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
			}
			response = client.execute(post);
			HttpEntity entity = response.getEntity();
			String result = EntityUtils.toString(entity);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
				post.releaseConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static Map<String, Object> postMethod(String url, String json) throws Exception {

		Map<String, Object> result = new HashMap<String, Object>();

		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url);
		try {

			StringEntity stringEntity = new StringEntity(json, "utf-8");

			httppost.setEntity(stringEntity);
			httppost.setHeader("accept", "application/json");
			httppost.setHeader("Content-Type", "application/json;charset=UTF-8");
			httppost.setConfig(requestConfig);
			// System.out.println("executing request " + httppost.getURI());
			// System.out.println("Entity " + EntityUtils.toString(httppost.getEntity()));

			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					if (response.getStatusLine().getStatusCode() == 302) {
						String locationUrl = response.getLastHeader("Location").getValue();
						result = postMethod(locationUrl, json);// 跳转到重定向的url
					} else {
						result.put("status", response.getStatusLine().getStatusCode());
						result.put("result", EntityUtils.toString(entity, "UTF-8"));
					}
				}
			} finally {
				response.close();
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new Exception(e);
			}
		}
		return result;
	}

	/**
	 * 附加basicAuth认证请求头
	 *
	 * @param url
	 * @param json
	 * @param headers
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> postMethod(String url, String json, Map<String, String> headers) throws Exception {

		Map<String, Object> result = new HashMap<String, Object>();

		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url);
		try {
			StringEntity stringEntity = new StringEntity(json, "utf-8");
			httppost.setEntity(stringEntity);
			httppost.setHeader("accept", "application/json");
			httppost.setHeader("Content-Type", "application/json;charset=UTF-8");
			for (Entry<String, String> entry : headers.entrySet()) {
				httppost.setHeader(entry.getKey(), entry.getValue());
			}
			httppost.setConfig(requestConfig);
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					if (response.getStatusLine().getStatusCode() == 302) {
						String locationUrl = response.getLastHeader("Location").getValue();
						result = postMethod(locationUrl, json);//跳转到重定向的url
					} else {
						String myResult = EntityUtils.toString(entity, "UTF-8");
						if (myResult.length() > 2000) {
							myResult = myResult.substring(0, 2000);
						}
						result.put("status", response.getStatusLine().getStatusCode());
						result.put("result", myResult);
					}
				}
			} finally {
				response.close();
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new Exception(e);
			}
		}
		return result;
	}


	/**
	 * post json参数
	 *
	 * @param url
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> myPostMethod(String url, String json) throws Exception {

		Map<String, Object> result = new HashMap<String, Object>();

		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url);
		try {
			StringEntity stringEntity = new StringEntity(json, "utf-8");
			httppost.setEntity(stringEntity);
			httppost.setHeader("accept", "application/json");
			httppost.setHeader("Content-Type", "application/json;charset=UTF-8");
			httppost.setConfig(requestConfig);
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					if (response.getStatusLine().getStatusCode() == 302) {
						String locationUrl = response.getLastHeader("Location").getValue();
						result = postMethod(locationUrl, json);//跳转到重定向的url
					} else {
						result.put("status", response.getStatusLine().getStatusCode());
						result.put("result", EntityUtils.toString(entity, "UTF-8"));
					}
				}
			} finally {
				response.close();
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new Exception(e);
			}
		}
		return result;
	}


	private Map<String, Object> postMethodWithBasicAuth(String url, String jsonStr, String authHeader) {
		Map<String, Object> result = new HashMap<String, Object>();
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		System.out.println("要发送的数据" + jsonStr);
		post.addHeader("Authorization", authHeader);
		post.setHeader("accept", "application/json");
		post.setHeader("Content-Type", "application/json;charset=UTF-8");
		String responseContent = null; // 响应内容
		CloseableHttpResponse response = null;
		try {
			StringEntity myEntity = new StringEntity(jsonStr, "utf-8"); // 构造请求数据
			post.setEntity(myEntity); // 设置请求体
			response = client.execute(post);
			System.out.println(response);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					if (response.getStatusLine().getStatusCode() == 302) {
						String locationUrl = response.getLastHeader("Location").getValue();
						result = postMethodWithBasicAuth(locationUrl, jsonStr, authHeader);//跳转到重定向的url
					} else {
						result.put("status", response.getStatusLine().getStatusCode());
						result.put("result", EntityUtils.toString(entity, "UTF-8"));
					}
				}
				responseContent = EntityUtils.toString(entity, "UTF-8");
			}

			if (response != null) {
				response.close();
			}
			if (client != null) {
				client.close();
			}

			System.out.println("responseContent:" + responseContent);

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Map<String, Object> postXmlMethod(String url, String xmlStr) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url);
		try {
			StringEntity stringEntity = new StringEntity(xmlStr, "utf-8");
			httppost.setEntity(stringEntity);
			httppost.setHeader("accept", "application/json");
			httppost.setHeader("Content-Type", "text/xml;charset=UTF-8");
			httppost.setConfig(requestConfig);
			// System.out.println("executing request " + httppost.getURI());
			// System.out.println("Entity " + EntityUtils.toString(httppost.getEntity()));

			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					if (response.getStatusLine().getStatusCode() == 302) {
						String locationUrl = response.getLastHeader("Location").getValue();
						result = postXmlMethod(locationUrl, xmlStr);//跳转到重定向的url
					} else {
						result.put("status", response.getStatusLine().getStatusCode());
						result.put("result", EntityUtils.toString(entity, "UTF-8"));
					}
				}
			} finally {
				response.close();
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new Exception(e);
			}
		}
		return result;
	}

	/**
	 * 附加basicAuth认证请求头
	 *
	 * @param url
	 * @param xmlStr
	 * @param headers
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> postXmlMethod(String url, String xmlStr, Map<String, String> headers) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url);
		try {
			StringEntity stringEntity = new StringEntity(xmlStr, "utf-8");
			httppost.setEntity(stringEntity);
			httppost.setHeader("accept", "application/json");
			httppost.setHeader("Content-Type", "text/xml;charset=UTF-8");
			for (Entry<String, String> entry : headers.entrySet()) {
				httppost.setHeader(entry.getKey(), entry.getValue());
			}
			httppost.setConfig(requestConfig);
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					if (response.getStatusLine().getStatusCode() == 302) {
						String locationUrl = response.getLastHeader("Location").getValue();
						result = postXmlMethod(locationUrl, xmlStr);//跳转到重定向的url
					} else {
						result.put("status", response.getStatusLine().getStatusCode());
						String myResult = EntityUtils.toString(entity, "UTF-8");
						if (myResult.length() > 2000) {
							myResult = myResult.substring(0, 2000);
						}
						result.put("result", myResult);
					}
				}
			} finally {
				response.close();
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new Exception(e);
			}
		}
		return result;
	}


	public static Map<String, Object> getMethedMap(String url) throws Exception {
		HttpGet get = null;
		CloseableHttpResponse response = null;
		InputStream in = null;
		try {
			get = new HttpGet(url);
			get.setConfig(requestConfig);
			Map<String, Object> resultMap = new HashMap<>();
			response = client.execute(get);
			HttpEntity entity = response.getEntity();
			int status = 0;
			String result = "";
			if (entity != null) {
				long len = entity.getContentLength();
				if (len != -1 && len < 2048) {
					result = EntityUtils.toString(entity);
					status = response.getStatusLine().getStatusCode();
					resultMap.put("status", status);
					resultMap.put("result", result);
				} else {
					entity = new BufferedHttpEntity(entity);
					in = entity.getContent();
					byte[] read = new byte[1024];
					byte[] all = new byte[0];
					int num;
					while ((num = in.read(read)) > 0) {
						byte[] temp = new byte[all.length + num];
						System.arraycopy(all, 0, temp, 0, all.length);
						System.arraycopy(read, 0, temp, all.length, num);
						all = temp;
					}
					result = new String(all, "UTF-8");
					status = response.getStatusLine().getStatusCode();
					logger.info("status is:{}", status);
					resultMap.put("status", status);
					resultMap.put("result", result);
				}
			}
			return resultMap;
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			try {
				if (in != null) {
					in.close();
					response.close();
					get.releaseConnection();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 附加basicAuth认证请求头
	 *
	 * @param url
	 * @param headers
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> getMethedMap(String url, Map<String, String> headers) throws Exception {
		HttpGet get = null;
		CloseableHttpResponse response = null;
		InputStream in = null;
		try {
			get = new HttpGet(url);
			for (Entry<String, String> entry : headers.entrySet()) {
				get.setHeader(entry.getKey(), entry.getValue());
			}
			get.setConfig(requestConfig);
			Map<String, Object> resultMap = new HashMap<>();
			response = client.execute(get);
			HttpEntity entity = response.getEntity();
			int status = 0;
			String result = "";
			if (entity != null) {
				long len = entity.getContentLength();
				if (len != -1 && len < 2048) {
					result = EntityUtils.toString(entity, "UTF-8");
					status = response.getStatusLine().getStatusCode();
					if (result.length() > 2000) {
						result = result.substring(0, 2000);
					}
					resultMap.put("status", status);
					resultMap.put("result", result);
				} else {
					entity = new BufferedHttpEntity(entity);
					in = entity.getContent();
					byte[] read = new byte[1024];
					byte[] all = new byte[0];
					int num;
					while ((num = in.read(read)) > 0) {
						byte[] temp = new byte[all.length + num];
						System.arraycopy(all, 0, temp, 0, all.length);
						System.arraycopy(read, 0, temp, all.length, num);
						all = temp;
					}
					result = new String(all, "UTF-8");
					status = response.getStatusLine().getStatusCode();
					logger.info("status is:{}", status);
					resultMap.put("status", status);
					resultMap.put("result", result);
				}
			}
			return resultMap;
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			try {
				if (in != null) {
					in.close();
					response.close();
					get.releaseConnection();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}


	public static String postMethod(String url, String bodyJson, String accessKeyId, String sign) throws Exception {
		HttpPost post = null;
		CloseableHttpResponse response = null;
		try {
			post = new HttpPost(url);
			if (accessKeyId != null && !"".equals(accessKeyId)) {
				post.setHeader("accessKeyId", accessKeyId);
			}
			if (sign != null && !"".equals(sign)) {
				post.setHeader("sign", sign);
			}
			StringEntity se = new StringEntity(bodyJson);
			se.setContentType("application/json");
			se.setContentEncoding("utf-8");
			post.setEntity(se);
			response = client.execute(post);
			HttpEntity entity = response.getEntity();
			String result;
			if (response.getStatusLine().getStatusCode() == 302) {
				String locationUrl = response.getLastHeader("Location").getValue();
				result = postMethod(url, bodyJson, accessKeyId, sign);//跳转到重定向的url
			} else {
				result = EntityUtils.toString(entity);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
				post.releaseConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static String postMethod(String url, HttpEntity entity, Header header) {
		HttpPost httpPost = null;
		CloseableHttpResponse response = null;

		try {
			httpPost = new HttpPost(url);
			httpPost.addHeader(header);
			httpPost.setEntity(entity);
			response = client.execute(httpPost);
			String result;
			HttpEntity resultEntity = response.getEntity();
			if (response.getStatusLine().getStatusCode() == 302) {
				String locationUrl = response.getLastHeader("Location").getValue();
				result = postMethod(locationUrl, entity, header);//跳转到重定向的url
			} else {
				result = EntityUtils.toString(resultEntity);
			}

			return result;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public static Map<String, Object> postMethod(String url, JSONObject json) {

		Map<String, Object> result = new HashMap<String, Object>();

		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url);
		try {

			StringEntity stringEntity = new StringEntity(json.toJSONString(), "utf-8");
			httppost.setEntity(stringEntity);

			httppost.setHeader("accept", "application/json");
			httppost.setHeader("Content-Type", "application/json;charset=UTF-8");

			Header[] headers = httppost.getAllHeaders();
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (response.getStatusLine().getStatusCode() == 302) {
					String locationUrl = response.getLastHeader("Location").getValue();
					result = postMethod(locationUrl, json);//跳转到重定向的url
				} else {
					result.put("status", response.getStatusLine().getStatusCode());
					result.put("result", EntityUtils.toString(entity, "UTF-8"));
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			logger.error(e.getMessage(), e);
			result.put("status", 500);
		} catch (UnsupportedEncodingException e1) {
			logger.error(e1.getMessage(), e1);
			result.put("status", 500);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			result.put("status", 500);
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return result;

	}

	public static JSONObject postMethods(String url, JSONObject json) throws Exception {

		JSONObject result = new JSONObject();

		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url);
		try {

			StringEntity stringEntity = new StringEntity(json.toJSONString(), "utf-8");
			httppost.setEntity(stringEntity);

			httppost.setHeader("accept", "application/json");
			httppost.setHeader("Content-Type", "application/json;charset=UTF-8");

			Header[] headers = httppost.getAllHeaders();
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (response.getStatusLine().getStatusCode() == 302) {
					String locationUrl = response.getLastHeader("Location").getValue();
					result = postMethods(locationUrl, json);//跳转到重定向的url
				} else {
					result.put("status", response.getStatusLine().getStatusCode());
					result.put("result", EntityUtils.toString(entity, "UTF-8"));
				}
			} finally {
				response.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.put("status", 500);
			throw e;
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return result;

	}

	public static String putMethod(String url, HttpEntity entity, Header header) {
		HttpPut httpPut = null;
		CloseableHttpResponse response = null;
		try {
			httpPut = new HttpPut(url);
			httpPut.addHeader(header);
			httpPut.setEntity(entity);
			response = client.execute(httpPut);
			HttpEntity resultEntity = response.getEntity();
			String result = EntityUtils.toString(resultEntity);
			return result;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}


	public static String myPutMethod(String url) {
		HttpPut httpPut = null;
		CloseableHttpResponse response = null;

		try {
			httpPut = new HttpPut(url);
//			StringEntity entity = new StringEntity(sourceId);
//			httpPut.setEntity(entity);
			httpPut.setHeader("accept", "application/json");
			httpPut.setHeader("Content-Type", "application/json;charset=UTF-8");
			response = client.execute(httpPut);
			HttpEntity resultEntity = response.getEntity();
			String result = EntityUtils.toString(resultEntity);
			return result;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}


	public static String putMethod(String url, Header header) {
		return putMethod(url, null, header);
	}

	public static String deleteMethod(String url, Header header) {

		HttpDelete httpDelete = null;
		CloseableHttpResponse response = null;
		try {
			httpDelete = new HttpDelete(url);
			httpDelete.addHeader(header);
			response = client.execute(httpDelete);
			HttpEntity resultEntity = response.getEntity();
			String result = EntityUtils.toString(resultEntity);
			return result;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static JSONObject delete(String url) {

		HttpDelete httpDelete = null;
		CloseableHttpResponse response = null;
		JSONObject result = new JSONObject();
		try {
			httpDelete = new HttpDelete(url);
			// httpDelete.addHeader(header);
			response = client.execute(httpDelete);
			HttpEntity entity = response.getEntity();
			int status = response.getStatusLine().getStatusCode();
			result.put("status", status);
			if (status == 200 || status == 201 || status == 204) {
				result.put("data", "succeed");
			} else {
				result.put("data", EntityUtils.toString(entity));
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("status", 500);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("status", 500);
		}
		return result;
	}

	public static BasicNameValuePair getNameValuePair(String name, String value) {
		return new BasicNameValuePair(name, value);
	}

	public static JSONObject postFormUrlEncoded(String url, Map<String, String> params) {

		JSONObject result = new JSONObject();
		try {

			// 创建url资源
			URL urlObject = new URL(url);
			// 建立http连接
			HttpURLConnection conn = (HttpURLConnection) urlObject.openConnection();
			// 设置允许输出
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setConnectTimeout(5000);

			// 设置不用缓存
			conn.setUseCaches(false);
			// 设置传递方式
			conn.setRequestMethod("POST");
			// 设置维持长连接
			conn.setRequestProperty("Connection", "Keep-Alive");
			// 设置文件字符集:
			conn.setRequestProperty("Charset", "UTF-8");
			// 转换为字节数组
			byte[] data = getDataString(params).getBytes();
			// 设置文件长度
			conn.setRequestProperty("Content-Length", String.valueOf(data.length));
			// 设置文件类型:
			conn.setRequestProperty("contentType", "application/x-www-form-urlencoded");
			// 开始连接请求
			conn.connect();
			OutputStream out = conn.getOutputStream();
			// 写入请求的字符串
			out.write(data);
			out.flush();
			out.close();

			// 请求返回的状态
			int status = conn.getResponseCode();
			result.put("status", status);

			// 请求返回的数据
			if (status == 200 || status == 201) {
				InputStream in = conn.getInputStream();
				String a = null;

				byte[] data1 = new byte[in.available()];
				in.read(data1);
				// 转成字符串
				a = new String(data1);
				result.put("data", a);
			} else {
				InputStream in = conn.getErrorStream();
				String a = null;

				byte[] data1 = new byte[in.available()];
				in.read(data1);
				// 转成字符串
				a = new String(data1);
				result.put("data", a);
			}

		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", 500);
		}
		return result;
	}

	public static String getDataString(Map<String, String> params) throws UnsupportedEncodingException {
		StringBuilder result = new StringBuilder();
		boolean first = true;
		for (Entry<String, String> entry : params.entrySet()) {
			if (first) {
				first = false;
			} else {
				result.append("&");
			}
			result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
			result.append("=");
			result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
		}
		return result.toString();
	}


}
