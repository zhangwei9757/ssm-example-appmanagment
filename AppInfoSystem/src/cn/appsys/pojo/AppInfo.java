package cn.appsys.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class AppInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String softwareName;

	private String APKName;

	private String supportROM;

	private String interfaceLanguage;

	private BigDecimal softwareSize;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updateDate;

	private Long devId;

	private String appInfo;

	private Long status;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date onSaleDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date offSaleDate;

	private Long flatformId;

	private Long categoryLevel3;

	private Long downloads;

	private Long createdBy;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date creationDate;

	private Long modifyBy;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date modifyDate;

	private Long categoryLevel1;

	private Long categoryLevel2;

	private String logoPicPath;

	private String logoLocPath;

	private Long versionId;

	private String statusName;// app状态名称
	private String flatformName;// app所属平台名称
	private String categoryLevel3Name;// 所属三级分类名称
	private String devName;// 开发者名称
	private String categoryLevel1Name;// 所属一级分类名称
	private String categoryLevel2Name;// 所属二级分类名称
	private String versionNo;// 最新的版本号


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSoftwareName() {
		return softwareName;
	}

	public void setSoftwareName(String softwareName) {
		this.softwareName = softwareName;
	}

	public String getAPKName() {
		return APKName;
	}

	public void setAPKName(String APKName) {
		this.APKName = APKName;
	}

	public String getSupportROM() {
		return supportROM;
	}

	public void setSupportROM(String supportROM) {
		this.supportROM = supportROM;
	}

	public String getInterfaceLanguage() {
		return interfaceLanguage;
	}

	public void setInterfaceLanguage(String interfaceLanguage) {
		this.interfaceLanguage = interfaceLanguage;
	}

	public BigDecimal getSoftwareSize() {
		return softwareSize;
	}

	public void setSoftwareSize(BigDecimal softwareSize) {
		this.softwareSize = softwareSize;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Long getDevId() {
		return devId;
	}

	public void setDevId(Long devId) {
		this.devId = devId;
	}

	public String getAppInfo() {
		return appInfo;
	}

	public void setAppInfo(String appInfo) {
		this.appInfo = appInfo;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Date getOnSaleDate() {
		return onSaleDate;
	}

	public void setOnSaleDate(Date onSaleDate) {
		this.onSaleDate = onSaleDate;
	}

	public Date getOffSaleDate() {
		return offSaleDate;
	}

	public void setOffSaleDate(Date offSaleDate) {
		this.offSaleDate = offSaleDate;
	}

	public Long getFlatformId() {
		return flatformId;
	}

	public void setFlatformId(Long flatformId) {
		this.flatformId = flatformId;
	}

	public Long getCategoryLevel3() {
		return categoryLevel3;
	}

	public void setCategoryLevel3(Long categoryLevel3) {
		this.categoryLevel3 = categoryLevel3;
	}

	public Long getDownloads() {
		return downloads;
	}

	public void setDownloads(Long downloads) {
		this.downloads = downloads;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Long getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(Long modifyBy) {
		this.modifyBy = modifyBy;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Long getCategoryLevel1() {
		return categoryLevel1;
	}

	public void setCategoryLevel1(Long categoryLevel1) {
		this.categoryLevel1 = categoryLevel1;
	}

	public Long getCategoryLevel2() {
		return categoryLevel2;
	}

	public void setCategoryLevel2(Long categoryLevel2) {
		this.categoryLevel2 = categoryLevel2;
	}

	public String getLogoPicPath() {
		return logoPicPath;
	}

	public void setLogoPicPath(String logoPicPath) {
		this.logoPicPath = logoPicPath;
	}

	public String getLogoLocPath() {
		return logoLocPath;
	}

	public void setLogoLocPath(String logoLocPath) {
		this.logoLocPath = logoLocPath;
	}

	public Long getVersionId() {
		return versionId;
	}

	public void setVersionId(Long versionId) {
		this.versionId = versionId;
	}

	/**
	 * @return the statusName
	 */
	public String getStatusName() {
		return statusName;
	}

	/**
	 * @param statusName
	 *            the statusName to set
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	/**
	 * @return the flatformName
	 */
	public String getFlatformName() {
		return flatformName;
	}

	/**
	 * @param flatformName
	 *            the flatformName to set
	 */
	public void setFlatformName(String flatformName) {
		this.flatformName = flatformName;
	}

	/**
	 * @return the categoryLevel3Name
	 */
	public String getCategoryLevel3Name() {
		return categoryLevel3Name;
	}

	/**
	 * @param categoryLevel3Name
	 *            the categoryLevel3Name to set
	 */
	public void setCategoryLevel3Name(String categoryLevel3Name) {
		this.categoryLevel3Name = categoryLevel3Name;
	}

	/**
	 * @return the devName
	 */
	public String getDevName() {
		return devName;
	}

	/**
	 * @param devName
	 *            the devName to set
	 */
	public void setDevName(String devName) {
		this.devName = devName;
	}

	/**
	 * @return the categoryLevel1Name
	 */
	public String getCategoryLevel1Name() {
		return categoryLevel1Name;
	}

	/**
	 * @param categoryLevel1Name
	 *            the categoryLevel1Name to set
	 */
	public void setCategoryLevel1Name(String categoryLevel1Name) {
		this.categoryLevel1Name = categoryLevel1Name;
	}

	/**
	 * @return the categoryLevel2Name
	 */
	public String getCategoryLevel2Name() {
		return categoryLevel2Name;
	}

	/**
	 * @param categoryLevel2Name
	 *            the categoryLevel2Name to set
	 */
	public void setCategoryLevel2Name(String categoryLevel2Name) {
		this.categoryLevel2Name = categoryLevel2Name;
	}

	/**
	 * @return the versionNo
	 */
	public String getVersionNo() {
		return versionNo;
	}

	/**
	 * @param versionNo
	 *            the versionNo to set
	 */
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AppInfo [id=" + id + ", softwareName=" + softwareName
				+ ", APKName=" + APKName + ", supportROM=" + supportROM
				+ ", interfaceLanguage=" + interfaceLanguage
				+ ", softwareSize=" + softwareSize + ", updateDate="
				+ updateDate + ", devId=" + devId + ", appInfo=" + appInfo
				+ ", status=" + status + ", onSaleDate=" + onSaleDate
				+ ", offSaleDate=" + offSaleDate + ", flatformId=" + flatformId
				+ ", categoryLevel3=" + categoryLevel3 + ", downloads="
				+ downloads + ", createdBy=" + createdBy + ", creationDate="
				+ creationDate + ", modifyBy=" + modifyBy + ", modifyDate="
				+ modifyDate + ", categoryLevel1=" + categoryLevel1
				+ ", categoryLevel2=" + categoryLevel2 + ", logoPicPath="
				+ logoPicPath + ", logoLocPath=" + logoLocPath + ", versionId="
				+ versionId + ", statusName=" + statusName + ", flatformName="
				+ flatformName + ", categoryLevel3Name=" + categoryLevel3Name
				+ ", devName=" + devName + ", categoryLevel1Name="
				+ categoryLevel1Name + ", categoryLevel2Name="
				+ categoryLevel2Name + ", versionNo=" + versionNo + "]";
	}
}