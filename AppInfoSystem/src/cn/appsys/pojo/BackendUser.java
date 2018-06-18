package cn.appsys.pojo;

import java.io.Serializable;
import java.util.Date;

public class BackendUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String userCode;

	private String userName;

	private Long userType;

	private Long createdBy;

	private Date creationDate;

	private Long modifyBy;

	private Date modifyDate;

	private String userPassword;

	private String userTypeName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getUserType() {
		return userType;
	}

	public void setUserType(Long userType) {
		this.userType = userType;
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

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	/**
	 * @return the userTypeName
	 */
	public String getUserTypeName() {
		return userTypeName;
	}

	/**
	 * @param userTypeName
	 *            the userTypeName to set
	 */
	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BackendUser [id=" + id + ", userCode=" + userCode
				+ ", userName=" + userName + ", userType=" + userType
				+ ", createdBy=" + createdBy + ", creationDate=" + creationDate
				+ ", modifyBy=" + modifyBy + ", modifyDate=" + modifyDate
				+ ", userPassword=" + userPassword + ", userTypeName="
				+ userTypeName + "]";
	}
}