package com.kaithavalappil.getmybus_admin.DataIntermediate;

public class SerchRecyclerLData {
    public SerchRecyclerLData(String routeName, String from, String to,String id) {
        this.routeName = routeName;
        this.from = from;
        this.to = to;
        this.id = id;
    }

    private String routeName;
    private String from;
    private String to;
    private String id;
    public String getId(){
        return id;
    }
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
