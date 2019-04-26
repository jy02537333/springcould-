package pri.zxw.comutil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

/**
 * @Author: 张相伟
 * @Date: 2019/1/8 13:00
 * @Description: 分页 实体
 * @updater:
 * @update date:
 */
@ApiModel(value="page 分页对象",description="page 分页对象")
public class PageInfo {
	@ApiModelProperty(name = "pageIndex",notes = "当前第几页",dataType = "Integer",required = true)
	@Range(min=1,max = 99999999,message = "查询分页，从1开始，分页数量最大99999999")
	private int pageIndex;
	@ApiModelProperty(name = "pageSize",notes = "一页显示多少行",dataType = "Integer",required = true)
	@Range(min=5,max = 100,message = "每页显示最小行数5，最大行数100")
	private int pageSize=10;

	public PageInfo(String pageIndex,String pageSize){
		this.pageIndex=Integer.parseInt(pageIndex);
		this.pageSize=Integer.parseInt(pageSize);
	}

	public PageInfo(){
	}

	public int getPageIndex() {
		return  pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return  pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}