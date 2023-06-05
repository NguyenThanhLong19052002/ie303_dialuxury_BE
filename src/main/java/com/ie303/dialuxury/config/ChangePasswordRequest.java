package com.ie303.dialuxury.config;

public class ChangePasswordRequest {
    private String currentPassword;
    private String newPassword;

    // Các constructor, getter và setter

    // Ví dụ về constructor
    public ChangePasswordRequest(String currentPassword, String newPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setCurrentPassword(String oldPassword) {
        this.currentPassword = oldPassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }
}
