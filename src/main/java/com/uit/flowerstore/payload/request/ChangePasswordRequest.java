package com.uit.flowerstore.payload.request;

public class ChangePasswordRequest {
	private String currentPassword;
	  private String newPassword;
	  private String confirmPassword;
	public String getCurrentPassword() {
		return currentPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public ChangePasswordRequest(String currentPassword, String newPassword, String confirmPassword) {
		super();
		this.currentPassword = currentPassword;
		this.newPassword = newPassword;
		this.confirmPassword = confirmPassword;
	}
	

}
