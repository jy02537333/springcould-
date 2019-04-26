package pri.zxw.spring_base.model;

import java.util.Date;

public class User {
    private String id;

    private String rootId;

    private String subId;

    private String name;

    private String description;

    private String purpose;

    private Date createTime;

    private Date updateTime;

    private String creator;

    private Boolean isDelete;

    private Short dataType;

    private String dataSource;

    private Long dataSize;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRootId() {
        return rootId;
    }

    public void setRootId(String rootId) {
        this.rootId = rootId == null ? null : rootId.trim();
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId == null ? null : subId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose == null ? null : purpose.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Short getDataType() {
        return dataType;
    }

    public void setDataType(Short dataType) {
        this.dataType = dataType;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource == null ? null : dataSource.trim();
    }

    public Long getDataSize() {
        return dataSize;
    }

    public void setDataSize(Long dataSize) {
        this.dataSize = dataSize;
    }
}