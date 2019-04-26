package pri.zxw.comutil;

import java.text.SimpleDateFormat;

/**
 * @author ：zhuxiaohong
 * @date ：Created in 2019/1/11 14:42
 * @description：日期校验
 * @modified By：
 * @version: 1.0
 */
public class ValidDate {
    /**
     * 检验输入是否为正确的日期格式(不含秒的任何情况),严格要求日期正确性,格式:yyyy-MM-dd HH:mm
     * @param sourceDate
     * @return
     */
    public static boolean checkDate(String sourceDate){
        if(sourceDate==null){
            return false;
        }
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateFormat.setLenient(false);
            dateFormat.parse(sourceDate);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

}
