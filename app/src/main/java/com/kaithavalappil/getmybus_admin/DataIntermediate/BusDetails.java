package com.kaithavalappil.getmybus_admin.DataIntermediate;

import com.google.firebase.firestore.GeoPoint;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.geojson.Point;

public class BusDetails {
    public static void setBusId(int busId) {
        BusDetails.busId = busId;
    }

    public static String getSerchBId() {
        return serchBId;
    }

    public static void setSerchBId(String serchBId) {
        BusDetails.serchBId = serchBId;
    }

    private static String serchBId;
    private static int busId;

    public static void setBusNumber(String busNumber) {
        BusDetails.busNumber = busNumber;
    }

    public static void setBusType(int busType) {
        BusDetails.busType = busType;
    }

    private static String busNumber;
    private static int busType;
    private static GeoPoint startingPoint;
    public static String getSource() {
        return source;
    }

    public static String getDest() {
        return dest;
    }

    private static String source,dest;

    public static void setBusDetails(GeoPoint startingPoint) {
        BusDetails.startingPoint = startingPoint;
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
