package com.kaithavalappil.getmybus_admin.utlils;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.turf.TurfMisc;

import java.util.List;

public class NearestPoint {
    public Feature getNearestPoint(LatLng point, List<Point> polyline){
        Point new_point = Point.fromLngLat(point.getLongitude(),point.getLatitude());
        Feature nearestPoint = TurfMisc.nearestPointOnLine(new_point,polyline);
        return nearestPoint;
    }
}