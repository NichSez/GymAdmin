package com.example.nich_sez.gymadmin;

public class Admin {

    private String adminId;
    private String adminName;
    private String adminFamily;
    private int adminPosition;
    public static String token;


    public Admin(String adminId, String adminName, String adminFamily, int adminPosition, String token) {
        this.adminId = adminId;
        this.adminName = adminName;
        this.adminFamily = adminFamily;
        this.adminPosition = adminPosition;
        Admin.token = token;
    }


    public String getAdminId() {
        return adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public String getAdminFamily() {
        return adminFamily;
    }

    public int getAdminPosition() {
        return adminPosition;
    }

    public String getToken() {
        return token;
    }

}
