package org.ohalo.favorite.entity;

import java.util.Map;

import org.ohalo.base.entity.BaseEntity;

import com.mongodb.DBObject;

/**
 * 
 * 内容entity
 * 
 * @author halo
 * 
 */
public class ZContentEntity extends BaseEntity {

  /**
	 * 
	 */
  private static final long serialVersionUID = 8100466758671051619L;

  /**
   * 内容名称
   */
  private String cname;

  /**
   * 描述信息
   */
  private String remark;

  /**
   * 链接地址
   */
  private String linkInAddress;

  /**
   * 标签编码
   */
  private String tagCode;

  public ZContentEntity(String cname, String remark, String linkInAddress,
      String tagCode) {
    super();
    this.cname = cname;
    this.remark = remark;
    this.linkInAddress = linkInAddress;
    this.tagCode = tagCode;
  }

  public String getCname() {
    return cname;
  }

  public void setCname(String cname) {
    this.cname = cname;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getLinkInAddress() {
    return linkInAddress;
  }

  public void setLinkInAddress(String linkInAddress) {
    this.linkInAddress = linkInAddress;
  }

  /**
   * <pre>
   * 方法体说明：(方法详细描述说明、方法参数的具体涵义)
   * 作者：赵辉亮
   * 日期：2013-9-11
   * </pre>
   * 
   * @return
   */
  @Override
  public String toString() {
    return "{cname:" + cname + ", remark:" + remark + ", linkInAddress:"
        + linkInAddress + ", tagCode:" + tagCode + ", toString():"
        + super.toString() + "}";
  }

  /***
   * @return the tagCode
   */
  public String getTagCode() {
    return tagCode;
  }

  /***
   * @param tagCode
   *          the tagCode to set
   */
  public void setTagCode(String tagCode) {
    this.tagCode = tagCode;
  }

@Override
public Map<String, Object> toMap() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public DBObject toDBObject() {
	// TODO Auto-generated method stub
	return null;
}

}