package pri.zxw.comutil;

import pri.zxw.comutil.entity.CronConvertDateTime;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @Author: 张相伟
 * @Date: 2019/3/27 14:03
 * @Description:
 * @updater:
 * @update date:
 */
public class DateUtil {


//	从左至右，七位元素的含义如下：0 15 10 * * ? *
//			1．秒（0–59）
//			2．分钟（0–59）
//			3．小时（0–23）
//			4．月份中的日期（1–31）
//			5．月份（1–12或JAN–DEC）
//			6．星期中的日期（1–7或SUN–SAT）
//			7．年份（1970–2099）
	//同步的时间单位：1=年；2=月；3=周；4=天；5=时;

	/**
	 * 将用户提供的同步时间转换为crontab时间格式
	 * @param dateUnit
	 * @param dateNum
	 * @param dateSql
	 * @param timeSql
	 */
	public static String dateTimeConvertCron(int dateUnit,int dateNum,java.sql.Date date,java.sql.Time time){
		String space=" ";
		String week=space+"*";
		String month=space+"*";
		String day=space+"*";
		String hour=space+"*";
		String minute="*";
		String cronFull="";
		String second=" sleep 0;";
		switch (dateUnit){
			case 1:
//				year=space+getYear(dateSql);
				month=space+getMonth(date);
				day=space+getDay(date);
				hour=space+getHour(time);
				minute=""+getMinute(time);
				second =space+ getSecond(time);
				break;
			case 2:
				day=space+getDay(date);
				hour=space+getHour(time);
				minute=""+getMinute(time);
				second =space+ getSecond(time);
				break;
			case 3:

				week=space+dateNum;
				hour=space+getHour(time);
				minute=""+getMinute(time);
				second =space+ getSecond(time);
				break;
			case 4:
				hour=space+getHour(time);
				minute=""+getMinute(time);
				second =space+ getSecond(time);
				break;
			case 5:
				minute=""+getMinute(time);
				second =space+ getSecond(time);
				break;
			default:
				break;
		}
		cronFull=minute+hour+day+month+week+second;
//		String outStr=second+hour+day+month+week+year;
		return cronFull;
	}
	/**
	 * 将数据库的cron时间格式转换为正常时间
	 * @param syncUnit 
	 */
	public static CronConvertDateTime cronConvertDateTime(int syncUnit, String cronStr){
		CronConvertDateTime cronConvertDateTime= new CronConvertDateTime();
		String[] vals=cronStr.split(" ");
		//同步的时间单位：1=年；2=月；3=周；4=天；5=时;
		//同步数组  0分 1时 2天 3月 4星期 6 秒
		if(syncUnit == 5){
			cronConvertDateTime.setDateUnit(5);
			cronConvertDateTime.setDateNum(1);
			cronConvertDateTime.setDateSql(null);
			cronConvertDateTime.setTimeSql(new java.sql.Time(0, Integer.parseInt(vals[0]),Integer.parseInt(vals[6])));
		}else
		if(syncUnit == 4){
			cronConvertDateTime.setDateUnit(4);
			cronConvertDateTime.setDateNum(1);
			cronConvertDateTime.setDateSql(null);
			cronConvertDateTime.setTimeSql(new java.sql.Time(Integer.parseInt(vals[1]), Integer.parseInt(vals[0]),Integer.parseInt(vals[6].replaceAll(";", ""))));
		}else
		if(syncUnit == 3){
			cronConvertDateTime.setDateUnit(3);
			int dateNum=Integer.parseInt(vals[4])-1;
			cronConvertDateTime.setDateNum(dateNum);
			cronConvertDateTime.setDateSql(null);
			cronConvertDateTime.setTimeSql(new java.sql.Time(Integer.parseInt(vals[1]), Integer.parseInt(vals[0]),Integer.parseInt(vals[6].replaceAll(";", ""))));
		}else
		if(syncUnit == 2){
			cronConvertDateTime.setDateUnit(2);
			cronConvertDateTime.setDateNum(1);
			cronConvertDateTime.setDateSql("0-0-"+vals[2]);
			cronConvertDateTime.setTimeSql(new java.sql.Time(Integer.parseInt(vals[1]), Integer.parseInt(vals[0]),Integer.parseInt(vals[6].replaceAll(";", ""))));
		}else
		if(syncUnit == 1){
			cronConvertDateTime.setDateUnit(1);
			cronConvertDateTime.setDateNum(1);
			cronConvertDateTime.setDateSql("0-"+ vals[3]+"-"+vals[2]);
			cronConvertDateTime.setTimeSql(new java.sql.Time(Integer.parseInt(vals[1]), Integer.parseInt(vals[0]),Integer.parseInt(vals[6].replaceAll(";", ""))));
		}

		return cronConvertDateTime;
	}


	public static int getYear(java.sql.Date dateSql){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(dateSql.getTime());
		return calendar.get(Calendar.YEAR);
	}
	public static int getMonth(java.sql.Date dateSql){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(dateSql.getTime());
		return calendar.get(Calendar.MONTH)+1;
	}
	public static int getDay(java.sql.Date dateSql){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(dateSql.getTime());
		return calendar.get(Calendar.DATE);
	}
	public static int getHour(java.sql.Time dateSql){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(dateSql.getTime());
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	public static int getMinute(java.sql.Time dateSql){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(dateSql.getTime());
		return calendar.get(Calendar.MINUTE);
	}
	public static String getSecond(java.sql.Time dateSql) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(dateSql.getTime());
		return "sleep "+ calendar.get(Calendar.SECOND) +";";

		
	}


	public static void main(String[] args){
		String cronStr=
				dateTimeConvertCron(5,0,new java.sql.Date(System.currentTimeMillis()),new java.sql.Time(System.currentTimeMillis()));
		System.out.println(cronStr);
		CronConvertDateTime cronConvertDateTime=cronConvertDateTime(0,cronStr);
		System.out.println(cronConvertDateTime.getDateNum());
		if(cronConvertDateTime.getDateSql()!=null){
			System.out.println(cronConvertDateTime.getDateSql().toString());
		}
		if(cronConvertDateTime.getTimeSql()!=null){
			System.out.println(cronConvertDateTime.getTimeSql().toString());
		}

//		java.sql.Time time=new java.sql.Time(0,01,55);
//		System.out.print(time.toString());
//		System.out.print(getMinute(new java.sql.Time(System.currentTimeMillis())));
	}

	/**
	 * 当前时间加一天
	 * @param date
	 * @return
	 */
	public static Date nowAddDay(Date date){
		Calendar calendar=new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE,1);
		date=calendar.getTime();
	return date;
	}
}