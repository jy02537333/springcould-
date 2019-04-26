package pri.zxw.comutil;


/**
 * @Auther: 张相伟
 * @Date: 2019/1/7 16:42
 * @Description: 敏感词过滤 工具类
 * @updater:
 * @update date:
 */
public class SensitiveWordFilteringUtil {
	/**
	 * 前端页面传递来的输入值，“id”，“类型”   值不需要过滤
	 * @param content
	 * @return
	 */
	public static String filtering(String content){
		if(content!=null){
			return content;
		}else{
			return null;
		}

	}
//public static void main(String[] args){
////String aa=	SensitiveWordFilteringUtil.filtering("前端页面传递来的输入值，“id”，“类型”   值不需要过滤");
//	String aa=	SensitiveWordFilteringUtil.filtering(null);
//System.out.print(aa);
//}
}