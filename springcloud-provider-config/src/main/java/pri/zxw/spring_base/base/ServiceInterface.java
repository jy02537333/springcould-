package pri.zxw.spring_base.base;



import pri.zxw.comutil.entity.ErrorReturn;

import java.util.List;

/**
 * 通用service接口
 * @author zhangy
 *
 * @param <T>
 */
public interface ServiceInterface<T> {

	/**
	 * 根据条件查询列表
	 * @param id 主键查询
	 */
	T selectByPrimaryKey(String id);
	/**
	 * 根据条件查询列表
	 * @param pageInfo 自定义的分页page参数与实体信息
	 */
	com.github.pagehelper.PageInfo selectByExample(T extends pri.zxw.comutil.PageInfo pageInfo);

	ErrorReturn insert(T record);
	/**
	 * 批量添加
	 */
	ErrorReturn inserts(List<T> list);
	/**
	 * 插入特定条件值
	 */
	ErrorReturn insertSelective(T record);

	/**
	 * 删除
	 */
	ErrorReturn deleteByPrimaryKey(String id);
	/**
	 * 软删除
	 */
	ErrorReturn updateStatusToDel(String id);

	/**
	 * 软删除
	 */
	ErrorReturn updateStatusToDelByDataId(String id);



	/**
	 * 根据主键按照条件更新
	 */
	ErrorReturn updateByPrimaryKeySelective(T record);

	/**
	 * 根据主键全部信息更新
	 */
	ErrorReturn updateByPrimaryKey(T record);


}
