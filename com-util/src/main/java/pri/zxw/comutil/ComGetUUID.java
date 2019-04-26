package pri.zxw.comutil;


import java.util.UUID;

/**
 * @Auther: 张相伟
 * @Date: 2019/1/7 10:40
 * @Description: 获取统一UUId
 * @updater:
 * @update date:
 */
public class ComGetUUID {
	public static String get(){
		String uuid= UUID.randomUUID().toString();
		return uuid;
	}
//	public static void main(String[] args){
//		System.out.print(UUID.randomUUID().toString().length());
//	}
}