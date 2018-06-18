package cn.appsys.tools;

public enum ErrorStatus {
	FILEUPLOAD_ERROR_1(1111, " * APK信息不完整！"), FILEUPLOAD_ERROR_2(2222,
			" * 上传失败！"), FILEUPLOAD_ERROR_3(3333, " * 上传文件格式不正确！"), FILEUPLOAD_ERROR_4(
			4444, " * 上传文件过大！");

	private Integer code;
	private String errorMessage;

	private ErrorStatus(Integer code, String errorMessage) {
		this.code = code;
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the code
	 */
	public Integer getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(Integer code) {
		this.code = code;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage
	 *            the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public static ErrorStatus getErrorStatusByCode(Integer code) {
		switch (code) {
		case 1111:
			return FILEUPLOAD_ERROR_1;
		case 2222:
			return FILEUPLOAD_ERROR_2;
		case 3333:
			return FILEUPLOAD_ERROR_3;
		default:
			return FILEUPLOAD_ERROR_4;
		}
	}
}
