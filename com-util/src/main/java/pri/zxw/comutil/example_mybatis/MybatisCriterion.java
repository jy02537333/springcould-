package pri.zxw.comutil.example_mybatis;

import java.util.List;

/**
 * @Author: 张相伟
 * @Date: 2019/3/11 11:07
 * @Description: Mybatis 统一使用Criterion
 * @updater:
 * @update date:
 */
public class MybatisCriterion {
	private String condition;

	private Object value;

	private Object secondValue;

	private boolean noValue;

	private boolean singleValue;

	private boolean betweenValue;

	private boolean listValue;

	private String typeHandler;

	public String getCondition() {
		return condition;
	}

	public Object getValue() {
		return value;
	}

	public Object getSecondValue() {
		return secondValue;
	}

	public boolean isNoValue() {
		return noValue;
	}

	public boolean isSingleValue() {
		return singleValue;
	}

	public boolean isBetweenValue() {
		return betweenValue;
	}

	public boolean isListValue() {
		return listValue;
	}

	public String getTypeHandler() {
		return typeHandler;
	}

	public MybatisCriterion(String condition) {
		super();
		this.condition = condition;
		this.typeHandler = null;
		this.noValue = true;
	}

	public MybatisCriterion(String condition, Object value, String typeHandler) {
		super();
		this.condition = condition;
		this.value = value;
		this.typeHandler = typeHandler;
		if (value instanceof List<?>) {
			this.listValue = true;
		} else {
			this.singleValue = true;
		}
	}

	public MybatisCriterion(String condition, Object value) {
		this(condition, value, null);
	}

	public MybatisCriterion(String condition, Object value, Object secondValue, String typeHandler) {
		super();
		this.condition = condition;
		this.value = value;
		this.secondValue = secondValue;
		this.typeHandler = typeHandler;
		this.betweenValue = true;
	}

	public MybatisCriterion(String condition, Object value, Object secondValue) {
		this(condition, value, secondValue, null);
	}
}