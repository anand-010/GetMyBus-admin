package com.kaithavalappil.getmybus_admin.DataIntermediate;

public class SerchRecyclerLData {
    public SerchRecyclerLData(String routeName, String from, String to) {
        this.routeName = routeName;
        this.from = from;
        this.to = to;
    }

    private String routeName;
    private String from;
    private String to;

    public String getRouteName() {
        return routeName;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
}
