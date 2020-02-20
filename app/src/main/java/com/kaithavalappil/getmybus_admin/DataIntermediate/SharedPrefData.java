package com.kaithavalappil.getmybus_admin.DataIntermediate;

public class SharedPrefData {
    private static String busid;
    private static String busname;
    private static String email;

    public static String getBusid() {
        return busid;
    }

    public static void setBusid(String busid) {
        SharedPrefData.busid = busid;
    }

    public static String getBusname() {
        return busname;
    }

    public static void setBusname(String busname) {
        SharedPrefData.busname = busname;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        SharedPrefData.email = email;
    }
}
