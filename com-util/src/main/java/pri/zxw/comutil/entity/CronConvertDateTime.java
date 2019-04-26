package pri.zxw.comutil.entity;

import java.sql.Date;
import java.sql.Time;

/**
 * @Author: 张相伟
 * @Date: 2019/3/27 15:59
 * @Description:
 * @updater:
 * @update date:
 */
public class CronConvertDateTime {
	/**
	 * 时间单位 1=年；2=月；3=周；4=天；5=时
	 */
	private int dateUnit;

	/**
	 * 时间量，年；月；天；时 默认等于1
	 * 周可以选择1-7
	 */
	private int dateNum;
	/**
	 * 月-日
	 */
	private  String dateSql;
	/**
	 * 时：分：秒
	 */
	private java.sql.Time timeSql;

	public int getDateUnit() {
		return dateUnit;
	}

	public void setDateUnit(int dateUnit) {
		this.dateUnit = dateUnit;
	}

	public int getDateNum() {
		return dateNum;
	}

	public void setDateNum(int dateNum) {
		this.dateNum = dateNum;
	}

	public String getDateSql() {
		return dateSql;
	}

	public void setDateSql(String dateSql) {
		this.dateSql = dateSql;
	}

	public Time getTimeSql() {
		return timeSql;
	}

	public void setTimeSql(Time timeSql) {
		this.timeSql = timeSql;
	}
}