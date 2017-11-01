package org.automation.model;

import java.util.Arrays;
import java.util.Date;

public class Batch{
	
	@Override
	public String toString() {
		return "Batch [id=" + id + ", clientId=" + clientId + ", inputSource=" + inputSource + ", uploadedBy="
				+ uploadedBy + ", sourceFilePath=" + sourceFilePath + ", state=" + state + ", supportedFiles="
				+ Arrays.toString(supportedFiles) + ", unsupportedFiles=" + Arrays.toString(unsupportedFiles)
				+ ", receivedDate=" + receivedDate + ", modifiedDate=" + modifiedDate + ", completedDate="
				+ completedDate + ", comments=" + comments + ", errorCode=" + errorCode + ", errorMessage="
				+ errorMessage + "]";
	}
	private String id;
	private String clientId;
	private int inputSource;
	private String uploadedBy;
	private String sourceFilePath;
	private int state;
	private String[] supportedFiles;
	private String[] unsupportedFiles;
	private Date receivedDate;
	private Date modifiedDate;
	private Date completedDate;
	private String comments;
	private String errorCode;
	private String errorMessage;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public int getInputSource() {
		return inputSource;
	}
	public void setInputSource(int inputSource) {
		this.inputSource = inputSource;
	}
	public String getUploadedBy() {
		return uploadedBy;
	}
	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}
	public String getSourceFilePath() {
		return sourceFilePath;
	}
	public void setSourceFilePath(String sourceFilePath) {
		this.sourceFilePath = sourceFilePath;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String[] getSupportedFiles() {
		return supportedFiles;
	}
	public void setSupportedFiles(String[] supportedFiles) {
		this.supportedFiles = supportedFiles;
	}
	public String[] getUnsupportedFiles() {
		return unsupportedFiles;
	}
	public void setUnsupportedFiles(String[] unsupportedFiles) {
		this.unsupportedFiles = unsupportedFiles;
	}
	public Date getReceivedDate() {
		return receivedDate;
	}
	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public Date getCompletedDate() {
		return completedDate;
	}
	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
}