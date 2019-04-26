package pri.zxw.comutil.example_mybatis;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 张相伟
 * @Date: 2019/3/11 11:25
 * @Description: mybatis example 统一 generatedCriteria
 * @updater:
 * @update date:
 */
public class MybatisGeneratedCriteria {
	protected List<MybatisCriterion> criteria;

	protected MybatisGeneratedCriteria() {
		super();
		criteria = new ArrayList<MybatisCriterion>();
	}

	public boolean isValid() {
		return criteria.size() > 0;
	}

	public List<MybatisCriterion> getAllCriteria() {
		return criteria;
	}

	public List<MybatisCriterion> getCriteria() {
		return criteria;
	}

	protected void addCriterion(String condition) {
		if (condition == null) {
			throw new RuntimeException("Value for condition cannot be null");
		}
		criteria.add(new MybatisCriterion(condition));
	}

	protected void addCriterion(String condition, Object value, String property) {
		if (value == null) {
			throw new RuntimeException("Value for " + property + " cannot be null");
		}
		criteria.add(new MybatisCriterion(condition, value));
	}

	protected void addCriterion(String condition, Object value1, Object value2, String property) {
		if (value1 == null || value2 == null) {
			throw new RuntimeException("Between values for " + property + " cannot be null");
		}
		criteria.add(new MybatisCriterion(condition, value1, value2));
	}
}