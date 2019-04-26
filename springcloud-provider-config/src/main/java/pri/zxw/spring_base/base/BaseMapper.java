package pri.zxw.spring_base.base;

import java.util.List;

public interface BaseMapper<T,Exam>{

	int countByExample(Exam example);

	int deleteByExample(Exam example);

	int deleteByPrimaryKey(String id);

	int insert(T record);

	int insertSelective(T record);

	List<T> selectByExample(Exam example);

	T selectByPrimaryKey(String id);

	int updateByExampleSelective( T record, Exam example);

	int updateByExample( T record, Exam example);

	int updateByPrimaryKeySelective(T record);

	int updateByPrimaryKey(T record);
	
}
