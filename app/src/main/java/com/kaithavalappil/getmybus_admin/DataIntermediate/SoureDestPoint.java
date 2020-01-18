package com.kaithavalappil.getmybus_admin.DataIntermediate;

import com.mapbox.geojson.Point;

public class SoureDestPoint {
    public static void setSourceDest(Point source, Point destination) {
        SoureDestPoint.source = source;
        SoureDestPoint.destination = destination;
    }

    public static Point getSource() {
        return source;
    }

    public static Point getDestination() {
        return destination;
    }

    static Point source;
    static Point destination;

}
