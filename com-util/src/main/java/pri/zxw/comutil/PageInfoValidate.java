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
public class PageInfoValidate {
	@ApiModelProperty(name = "pageIndex",notes = "当前第几页",dataType = "Integer",required = true)
	@Range(min=0,max = 99999999,message = "分页数量,只能是数字，最大99999999")
	private String pageIndex;
	@ApiModelProperty(name = "pageSize",notes = "一页显示多少行",dataType = "Integer",required = true)
	@Range(min=5,max = 100,message = "每页显示只能是数字，最小行数5，最大行数100")
	private String pageSize;

	public String getPageIndex() {
		if(pageIndex==null){
			return "0";
		}
		return  pageIndex;
	}

	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getPageSize()
	{
		if(pageSize==null){
			return "10";
		}
		return  pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
}