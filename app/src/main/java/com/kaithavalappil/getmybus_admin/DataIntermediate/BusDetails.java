package com.kaithavalappil.getmybus_admin.DataIntermediate;

import com.google.firebase.firestore.GeoPoint;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.geojson.Point;

public class BusDetails {
    private static int busId;
    private static String busNumber;
    private static int busType;
    private static GeoPoint startingPoint;

    public static String getHashcode() {
        return hashcode;
    }

    private static String hashcode;
    public static String getSource() {
        return source;
    }

    public static String getDest() {
        return dest;
    }

    private static String source,dest;

    public static void setBusDetails(int busId, String busNumber, int busType, GeoPoint startingPoint, String hashcode) {
        BusDetails.busId = busId;
        BusDetails.busNumber = busNumber;
        BusDetails.busType = busType;
        BusDetails.startingPoint = startingPoint;
        BusDetails.hashcode = hashcode;
    }
    public static void setSrc(String source) {
        BusDetails.source = source;
    }
    public static void setDest(String dest) {
        BusDetails.dest = dest;
    }

    public static int getBusId() {
        return busId;
    }

    public static String getBusNumber() {
        return busNumber;
    }

    public static int getBusType() {
        return busType;
    }

    public static GeoPoint getStartingPoint() {
        return startingPoint;
    }
}
