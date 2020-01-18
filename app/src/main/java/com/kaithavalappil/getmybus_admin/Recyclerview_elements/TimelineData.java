package com.kaithavalappil.getmybus_admin.Recyclerview_elements;

public class TimelineData {
    public int getTimeline_pos() {
        return timeline_pos;
    }

    public TimelineData(String location,String time,int timeline_pos, int timeline_state) {
        this.timeline_state = timeline_state;
        this.time = time;
        this.location = location;
        this.timeline_pos = timeline_pos;
    }

    public String getLocation() {
        return location;
    }

    private int timeline_pos;

    public int getTimeline_state() {
        return timeline_state;
    }
    public String getTime() {
        return time;
    }
    int timeline_state;
    private String location;
    private String time;
}
