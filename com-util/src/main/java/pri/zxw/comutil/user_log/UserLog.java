package pri.zxw.comutil.user_log;

import com.alibaba.fastjson.JSONObject;
import pri.zxw.comutil.MyHttpClient;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 * 插入用户操作日志
 * 规则：
 * userType		必填	只能为0和1 	0:数据管理者;1:资源用户
 * userId			必填	用户id
 * content         必填	填入日志类型编号，例如：01
 * 编号：
 * 01,"数据采集>新增文件采集>数据名称"
 * 02,"数据采集>新增接口注册>数据名称"
 * 03,"数据采集>新增库表同步>数据名称"
 * 04,"数据采集>删除同步任务>同步任务名称"
 * 05,"数据采集>编辑同步策略>同步任务名称"
 * 06,"数据采集>新增数据源>数据源名称"
 * 07,"数据采集>删除数据源>数据源名称"
 * 08,"数据采集>启动任务>同步任务名称"
 * 11,"数据管理>删除文件数据>数据名称"
 * 12,"数据管理>删除接口数据>数据名称"
 * 13,"数据管理>删除库表数据>数据名称"
 * 14,"数据管理>新增目录>目录名称"
 * 15,"数据管理>新增子目录>子目录名称"
 * 16,"数据管理>删除目录>目录名称"
 * 17,"数据管理>删除子目录>子目录名称"
 * 18,"数据管理>发布目录>目录名称+子目录名称"
 * 21,"数据治理>新增治理流程>治理名称"
 * 22,"数据治理>删除治理流程>治理名称"
 * 23,"数据治理>启动治理流程>治理名称"
 * 24,"数据治理>停止治理流程>治理名称"
 * 25,"数据治理>编辑治理策略>治理名称"
 * 31,"数据服务>订阅服务>服务名称"
 * 32,"数据服务>删除已订阅服务>服务名称"
 * 33,"数据服务>删除云封装接口>接口名称"
 * 34,"数据服务>新增云封装接口>接口名称"
 * 41,"登录>登录"
 * 42,"登录>登录>退出"
 * request              必填	用于获取真实ip地址
 * operationTime	 必填	用户操作时间，只能传入当前时间的毫秒数
 */
public class UserLog {
//    private static final String url = "http://172.16.155.7:8888/api/monitoring/insertUserOperationsLog";


    public static JSONObject insertUserLog(String url, String userType, String userId, String content,String businessName, HttpServletRequest request, String operationTime) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userType", userType);
        jsonObject.put("userId", userId);
        jsonObject.put("content", content);
        jsonObject.put("businessName", businessName);
        jsonObject.put("ip", ip);
        jsonObject.put("operationTime", operationTime);
        Map<String, Object> stringObjectMap = MyHttpClient.postMethod(url, jsonObject);
        String result = (String) stringObjectMap.get("result");
        return JSONObject.parseObject(result);
    }
}
