package pri.zxw.comutil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: 张相伟
 * @Date: 2019/1/9 15:00
 * @Description:
 * @updater:
 * @update date:
 */
public class ComUtil {
	private static final String NUMBER="[0-9]*";
	/**
	 * 利用正则表达式判断字符串是否是数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){
		Pattern pattern = Pattern.compile(NUMBER);
		Matcher isNum = pattern.matcher(str);
		if( !isNum.matches() ){
			return false;
		}
		return true;
	}
	public static <T>void fatherToChild(T father,T child) throws Exception {
		if (child.getClass().getSuperclass()!=father.getClass()){
			throw new Exception("child 不是 father 的子类");
		}
		Class<?> fatherClass = father.getClass();
		Field[] declaredFields = fatherClass.getDeclaredFields();
		for (int i = 0; i < declaredFields.length; i++) {
			Field field=declaredFields[i];
			Method method=fatherClass.getDeclaredMethod("get"+upperHeadChar(field.getName()));
			Object obj = method.invoke(father);
			field.setAccessible(true);
			field.set(child,obj);
		}

	}
	public static String upperHeadChar(String in) {
		String head = in.substring(0, 1);
		String out = head.toUpperCase() + in.substring(1, in.length());
		return out;
	}

	/**
	 * 删除数据字符的最后一个字符
	 * @param in
	 * @return
	 */
	public static String arrayDelLast(String in) {
		if(in==null||in.trim().length()==0){
			return in;
		}
		String head = in.substring(0, in.length()-1);
		return head;
	}

//	public static void main(String[] args){
//	System.out.print( ComUtil.arrayDelLast("{},"));
//	}
}