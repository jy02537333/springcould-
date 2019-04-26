package pri.zxw.comutil.cas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
/**
 * 
 * <p>项目名称：com_util</p>
 * <p>类名称：ParameterRequestWrapper</p>
 * @author 林逸鑫 <br>
 * @version 1.0
 * 创建时间2019年1月31日上午9:56:47<br>
 * 类描述：新建HttpServletRequestWrapper包装类，添加addParameter和addQueryString
 */
public class ParameterRequestWrapper extends  HttpServletRequestWrapper  {
	    
    private Map<String , String[]> params = new HashMap<String, String[]>();
    private String query = null;
    @SuppressWarnings("unchecked")
    public ParameterRequestWrapper(HttpServletRequest request) {
        // 将request交给父类，以便于调用对应方法的时候，将其输出，其实父亲类的实现方式和第一种new的方式类似
        super(request);
        //将参数表，赋予给当前的Map以便于持有request中的参数
        this.params.putAll(request.getParameterMap());
        this.query = (request.getQueryString());
    }
    //重载一个构造方法
    public ParameterRequestWrapper(HttpServletRequest request , Map<String , Object> extendParams) {
        this(request);
        addAllParameters(extendParams);//这里将扩展参数写入参数表
    }
    
    @Override
    public String getParameter(String name) {//重写getParameter，代表参数从当前类中的map获取
        String[] values = params.get(name);
        if(values == null || values.length == 0) {
            return null;
        }
        return values[0];
    }
    public String getQueryString(){
		return query;
    	
    }
    
    public  void setQueryString(String name) {//同上
         this.query = name;
    }
    
    
    public String[] getParameterValues(String name) {//同上
        return params.get(name);
   }
 
   public void addAllParameters(Map<String , Object>otherParams) {//增加多个参数
        for(Map.Entry<String , Object>entry : otherParams.entrySet()) {
            addParameter(entry.getKey() , entry.getValue());
        }
    }
   
   public void addQueryString(String str) {//增加参数
       if(query != null ) {
    	   query=query+"&"+str;
       }else{
    	   query=str;
       }
       	
   } 
    public void addParameter(String name , Object value) {//增加参数
        if(value != null ) {
        	if(value instanceof ArrayList){
        		String value1 = String.valueOf(value).substring(1,String.valueOf(value).length());
             	value = value1.substring(0,value1.length()-1);
             	params.put(name , new String[] {(String)value});
        	}
        	else if(value instanceof String[]) {
                params.put(name , (String[])value);
            }else if(value instanceof String) {
                params.put(name , new String[] {(String)value});
            }else {
                params.put(name , new String[] {String.valueOf(value)});
            }
        }
    } 
}
