package com.kaithavalappil.getmybus_admin.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import androidx.annotation.NonNull;

import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.firebase.firestore.GeoPoint;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kaithavalappil.getmybus_admin.DataIntermediate.BusDetails;
import com.kaithavalappil.getmybus_admin.DataIntermediate.SoureDestPoint;
import com.kaithavalappil.getmybus_admin.R;
import com.kaithavalappil.getmybus_admin.utlils.NearestPoint;
import com.kaithavalappil.getmybus_admin.helper.FirebaseDbNewRide;
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.geojson.utils.PolylineUtils;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.PlaceAutocomplete;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.utils.BitmapUtils;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineCap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineJoin;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineWidth;
import static com.mapbox.core.constants.Constants.PRECISION_6;
import com.mapbox.mapboxsdk.plugins.annotation.OnSymbolDragListener;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions;
import com.mapbox.mapboxsdk.plugins.annotation.Symbol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Use the places plugin to take advantage of Mapbox's location search ("geocoding") capabilities. The plugin
 * automatically makes geocoding requests, has built-in saved locations, includes location picker functionality,
 * and adds beautiful UI into your Android project.
 */
public class New_Ride_Activity extends AppCompatActivity implements OnMapReadyCallback {
    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    private static final int REQUEST_SOURCE_CODE = 2;
    private static final int REQUEST_DESTINATION_CODE = 3;
    private static final String ICON_LAYER_ID_2 = "icon-layter-id-2";
    private MapView mapView;
    private MapboxMap mapboxMap;
    private CarmenFeature home;
    private CarmenFeature work;
    private String geojsonSourceLayerId = "geojsonSourceLayerId";
    private String symbolIconId = "symbolIconId";
    private final String ROUTE_LAYER_ID = "route-layer-id";
    private static final String ROUTE_SOURCE_ID = "route-source-id";
    private static final String MY_POINT_ID = "my-point-id";
    private static final String ICON_LAYER_ID = "icon-layer-id";
    private static final String ICON_SOURCE_ID = "icon-source-id";
    private static final String ICON_SOURCE_ID_2 = "icon-dest-id";
    private static final String RED_PIN_ICON_ID = "red-pin-icon-id";
    private DirectionsRoute currentRoute;
    private MapboxDirections client;
    private Point origin;
    private Point destination;
    private Point mysource = SoureDestPoint.getSource();
    private Point mydestination = SoureDestPoint.getDestination();
    private ImageView add_location,add_stops;

    //    this is draggable addition
    private static final String MAKI_ICON_PlUS = "hospital-15";
//    private static final String MAKI_ICON_CAFE = "cafe-15";
//    private static final String MAKI_ICON_HARBOR = "harbor-15";
//    private static final String MAKI_ICON_AIRPORT = "airport-15";
    Feature nearest_point;
    List<Point> polyline;
    private SymbolManager symbolManager;
    private SymbolManager stops_symbol_manager;
    private SymbolManager stops_real_symbol_manager;
    List<Symbol> symbol_list = new ArrayList<>();
    List<Point> waypoint_list = new ArrayList<>();
    List<Point> stops_list = new ArrayList<>();
    private Style style;
    private List<Symbol> symbol_stops_list =  new ArrayList<>();
    private List<Symbol> symbol_stops_real_list =  new ArrayList<>();
//    this is draggable addition

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "pk.eyJ1IjoiYW5hbmQ5Mjg4IiwiYSI6ImNrNHk2dHJpdDA3dHEzZm82Y2hnY252cjEifQ.W-3fm0taJg_noVA_zzJO7g");
        // This contains the MapView in XML and needs to be called after the access token is configured.
        setContentView(R.layout.activity_new__ride_);
        add_location = findViewById(R.id.add_location);
        add_stops = findViewById(R.id.add_stops);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        add_location.setOnClickListener(v -> {
            Symbol symbol = symbolManager.create(new SymbolOptions()
                    .withLatLng(new LatLng(mapboxMap.getCameraPosition().target.getLatitude(), mapboxMap.getCameraPosition().target.getLongitude()))
                    .withIconImage(MAKI_ICON_PlUS)
                    .withIconSize(2.0f)
                    .withDraggable(true));
            symbol_list.add(symbol);
            waypoint_list.add(Point.fromLngLat(mapboxMap.getCameraPosition().target.getLongitude(),mapboxMap.getCameraPosition().target.getLatitude()));
            getRoute(mapboxMap, mysource, mydestination,waypoint_list);
        });
        add_stops.setOnClickListener(v->{
            init_symbolmanager_stops_real();
            init_symbolmanager_stops();
        });
        FirebaseDbNewRide db = new FirebaseDbNewRide(New_Ride_Activity.this,mysource,mydestination);
        Double totoal = mysource.latitude()+mysource.longitude()+mydestination.latitude()+mydestination.longitude();
        String text = String.valueOf(totoal);
        final HashCode hashCode = Hashing.sha256().hashString(text, Charset.defaultCharset());
        findViewById(R.id.continue_btn).setOnClickListener(v -> {
            if (polyline!=null)
                if (polyline.size()>0){
//                    db.putData(polyline);
                }
                if (stops_list.size()>0){
//                    db.putStops(stops_list);
                }
            BusDetails.setBusDetails(450,"Kl0478",2,new GeoPoint(polyline.get(0).latitude(),polyline.get(0).longitude()),hashCode.toString());
                startActivity(new Intent(New_Ride_Activity.this, NewrideConformation.class));
//            todo need to add upload ith to database
        });
    }

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        /*
        This is my new adding of stops displaying layer
        */
                /*
        This is my new adding of stops displaying layer
        */

        mapboxMap.addOnMapClickListener(point -> {
            Symbol symbol = stops_symbol_manager.create(new SymbolOptions()
                    .withLatLng(new LatLng(point.getLatitude(),point.getLongitude()))
                    .withIconImage(MAKI_ICON_PlUS)
                    .withIconSize(2.0f)
                    .withDraggable(true));
            symbol_stops_list.add(symbol);

//            adding real stops
            NearestPoint np = new NearestPoint();
//            todo need to be check if the polyline is null
            Feature feature = np.getNearestPoint(point,polyline);

            // Ensure the feature has properties defined
            Log.d("Hai", "onAnnotationDragFinished: "+feature.toJson());
            JSONArray coordinate_array;
            try {
                coordinate_array = new JSONObject(feature.toJson()).getJSONObject("geometry").getJSONArray("coordinates");
                Log.d("Hai", "onAnnotationDragFinished: "+coordinate_array.get(0));
                Symbol symbol2 = stops_real_symbol_manager.create(new SymbolOptions()
                        .withLatLng(new LatLng(coordinate_array.getDouble(1),coordinate_array.getDouble(0)))
                        .withIconImage("stop_icon")
                        .withDraggable(false));
                symbol_stops_real_list.add(symbol2);
                stops_list.add(Point.fromLngLat(coordinate_array.getDouble(0),coordinate_array.getDouble(1)));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return false;
        });
        mapboxMap.setStyle(Style.MAPBOX_STREETS, style -> {
            //        draggable test
            this.style = style;
            BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.bus_iconpng);
            Bitmap b=bitmapdraw.getBitmap();
            Bitmap smallMarker = Bitmap.createScaledBitmap(b, 50, 50, false);
            style.addImage("stop_icon",smallMarker);
            inti_symbolmanager();


//        draggable test
            // Set the origin location to the Alhambra landmark in Granada, Spain.
            initSource(style);
            initLayers(style);
            getRoute(mapboxMap, mysource, mydestination);
//            animate the camera
                    mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                            new CameraPosition.Builder()
                                    .target(new LatLng(mysource.latitude(),mysource.longitude()))
                                    .zoom(14)
                                    .build()), 4000);
            // Add the symbol layer icon to map for future use
            style.addImage(symbolIconId, BitmapFactory.decodeResource(
                    New_Ride_Activity.this.getResources(), R.drawable.mapbox_marker_icon_default));
            // Create an empty GeoJSON source using the empty feature collection
            setUpSource(style);
            // Set up a new symbol layer for displaying the searched location's feature coordinates
            setupLayer(style);
        });
    }

    private void inti_symbolmanager() {
        symbolManager = new SymbolManager(mapView, mapboxMap, style);
        symbolManager.setIconAllowOverlap(true);
        symbolManager.setTextAllowOverlap(true);
        symbolManager.addClickListener(symbol -> {
            Toast.makeText(New_Ride_Activity.this,"clicked", Toast.LENGTH_SHORT).show();
            symbol.setIconColor("blue");
            symbolManager.update(symbol);
        });
// Add long click listener and change the symbol to an airport icon on long click
        symbolManager.addLongClickListener((symbol -> {
            Toast.makeText(New_Ride_Activity.this,"long clicked", Toast.LENGTH_SHORT).show();
            symbol.setIconColor("red");
            symbolManager.update(symbol);
        }));
        symbolManager.addDragListener(new OnSymbolDragListener() {
            @Override
// Left empty on purpose
            public void onAnnotationDragStarted(Symbol annotation) {
            }

            @Override
// Left empty on purpose
            public void onAnnotationDrag(Symbol symbol) {
            }

            @Override
// Left empty on purpose
            public void onAnnotationDragFinished(Symbol annotation) {
                int i=0;
                for (Symbol test_sim: symbol_list){
                    if (test_sim==annotation){
                        waypoint_list.set(i,Point.fromLngLat(annotation.getLatLng().getLongitude(),annotation.getLatLng().getLatitude()));
                    }
                    i++;
                }
                getRoute(mapboxMap, mysource, mydestination,waypoint_list);
            }
        });
    }
    private void init_symbolmanager_stops() {
        stops_symbol_manager = new SymbolManager(mapView, mapboxMap, style);
        stops_symbol_manager.setIconAllowOverlap(true);
        stops_symbol_manager.setTextAllowOverlap(true);
//        stops_symbol_manager.addClickListener(symbol -> {
//            Toast.makeText(New_Ride_Activity.this,"clicked", Toast.LENGTH_SHORT).show();
//            symbol.setIconColor("blue");
//            stops_symbol_manager.update(symbol);
//        });
//// Add long click listener and change the symbol to an airport icon on long click
//        stops_symbol_manager.addLongClickListener((symbol -> {
//            Toast.makeText(New_Ride_Activity.this,"long cslicked", Toast.LENGTH_SHORT).show();
//            symbol.setIconColor("red");
//            stops_symbol_manager.update(symbol);
//        }));
        stops_symbol_manager.addDragListener(new OnSymbolDragListener() {
            @Override
// Left empty on purpose
            public void onAnnotationDragStarted(Symbol annotation) {
            }

            @Override
// Left empty on purpose
            public void onAnnotationDrag(Symbol symbol) {
                int i=0;
                for (Symbol test_sim: symbol_stops_list){
                    if (test_sim==symbol){
                        NearestPoint np = new NearestPoint();
//                      todo need to be check if the polyline is null
                        Feature feature = np.getNearestPoint(symbol.getLatLng(),polyline);
                        JSONArray coordinate_array;
                        try {
                            coordinate_array = new JSONObject(feature.toJson()).getJSONObject("geometry").getJSONArray("coordinates");
                            symbol_stops_real_list.get(i).setLatLng(new LatLng(coordinate_array.getDouble(1),coordinate_array.getDouble(0)));
                            Log.d("Hai", "onAnnotationDragFinished: "+coordinate_array.get(0));
                            stops_real_symbol_manager.update(symbol_stops_real_list.get(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    i++;
                }
            }

            @Override
// Left empty on purpose
            public void onAnnotationDragFinished(Symbol annotation) {
                int i=0;
                for (Symbol test_sim: symbol_stops_list){
                    if (test_sim==annotation){
                        NearestPoint np = new NearestPoint();
//                      todo need to be check if the polyline is null
                        Feature feature = np.getNearestPoint(annotation.getLatLng(),polyline);
                        JSONArray coordinate_array;
                        try {
                            coordinate_array = new JSONObject(feature.toJson()).getJSONObject("geometry").getJSONArray("coordinates");
                            symbol_stops_real_list.get(i).setLatLng(new LatLng(coordinate_array.getDouble(1),coordinate_array.getDouble(0)));
                            Log.d("Hai", "onAnnotationDragFinished: "+coordinate_array.get(0));
                            stops_real_symbol_manager.update(symbol_stops_real_list.get(i));
                            stops_list.set(i,Point.fromLngLat(coordinate_array.getDouble(0),coordinate_array.getDouble(1)));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    i++;
                }
            }
        });
    }
    private void init_symbolmanager_stops_real() {
        stops_real_symbol_manager = new SymbolManager(mapView, mapboxMap, style);
        stops_real_symbol_manager.setIconAllowOverlap(true);
        stops_real_symbol_manager.setTextAllowOverlap(true);
    }
    private void setUpSource(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addSource(new GeoJsonSource(geojsonSourceLayerId));
    }

    private void setupLayer(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addLayer(new SymbolLayer("SYMBOL_LAYER_ID", geojsonSourceLayerId).withProperties(
                iconImage(symbolIconId),
                iconOffset(new Float[] {0f, -8f})
        ));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_AUTOCOMPLETE) {
            // Retrieve selected location's CarmenFeature
            CarmenFeature selectedCarmenFeature = PlaceAutocomplete.getPlace(data);
            // Create a new FeatureCollection and add a new Feature to it using selectedCarmenFeature above.
            // Then retrieve and update the source designated for showing a selected location's symbol layer icon
            if (mapboxMap != null) {
                Style style = mapboxMap.getStyle();
                if (style != null) {
                    GeoJsonSource source = style.getSourceAs(geojsonSourceLayerId);
                    if (source != null) {
                        source.setGeoJson(FeatureCollection.fromFeatures(
                                new Feature[] {Feature.fromJson(selectedCarmenFeature.toJson())}));
                    }
                    // Move map camera to the selected location
                    mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                            new CameraPosition.Builder()
                                    .target(new LatLng(((Point) selectedCarmenFeature.geometry()).latitude(),
                                            ((Point) selectedCarmenFeature.geometry()).longitude()))
                                    .zoom(14)
                                    .build()), 4000);
                }
            }
        }
    }
    /**
     * Add the route and marker sources to the map
     */
    private void initSource(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addSource(new GeoJsonSource(ROUTE_SOURCE_ID,
                FeatureCollection.fromFeatures(new Feature[] {})));

        GeoJsonSource iconGeoJsonSource = new GeoJsonSource(ICON_SOURCE_ID, FeatureCollection.fromFeatures(new Feature[] {
                Feature.fromGeometry(Point.fromLngLat(mysource.longitude(), mysource.latitude())),
                Feature.fromGeometry(Point.fromLngLat(mydestination.longitude(), mydestination.latitude()))}));
        loadedMapStyle.addSource(iconGeoJsonSource);
    }
    /**
     * Add the route and marker icon layers to the map
     */
    private void initLayers(@NonNull Style loadedMapStyle) {
        LineLayer routeLayer = new LineLayer(ROUTE_LAYER_ID, ROUTE_SOURCE_ID);

// Add the LineLayer to the map. This layer will display the directions route.
        routeLayer.setProperties(
                lineCap(Property.LINE_CAP_ROUND),
                lineJoin(Property.LINE_JOIN_ROUND),
                lineWidth(5f),
                lineColor(Color.parseColor("#009688"))
        );
        loadedMapStyle.addLayer(routeLayer);

// Add the red marker icon image to the map
        loadedMapStyle.addImage(RED_PIN_ICON_ID, BitmapUtils.getBitmapFromDrawable(
                getResources().getDrawable(R.drawable.mapbox_marker_icon_default)));

// Add the red marker icon SymbolLayer to the map
        loadedMapStyle.addLayer(new SymbolLayer(ICON_LAYER_ID, ICON_SOURCE_ID).withProperties(
                iconImage(RED_PIN_ICON_ID),
                iconIgnorePlacement(true),
                iconAllowOverlap(true),
                iconOffset(new Float[] {0f, -9f})));
        //todo this is my second line of added for marker
        loadedMapStyle.addLayer(new SymbolLayer(ICON_LAYER_ID_2, ICON_SOURCE_ID_2).withProperties(
                iconImage(RED_PIN_ICON_ID),
                iconIgnorePlacement(true),
                iconAllowOverlap(true),
                iconOffset(new Float[] {0f, -9f})));
    }
    /**
     * Make a request to the Mapbox Directions API. Once successful, pass the route to the
     * route layer.
     * @param mapboxMap the Mapbox map object that the route will be drawn on
     * @param origin      the starting point of the route
     * @param destination the desired finish point of the route
     */
    private void getRoute(final MapboxMap mapboxMap, Point origin, Point destination) {
        client = MapboxDirections.builder()
                .origin(origin)
                .destination(destination)
                .overview(DirectionsCriteria.OVERVIEW_FULL)
                .profile(DirectionsCriteria.PROFILE_DRIVING)
                .accessToken("pk.eyJ1IjoiYW5hbmQ5Mjg4IiwiYSI6ImNrNHk2dHJpdDA3dHEzZm82Y2hnY252cjEifQ.W-3fm0taJg_noVA_zzJO7g")
                .build();

        client.enqueueCall(new Callback<DirectionsResponse>() {
            @Override
            public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
// You can get the generic HTTP info about the response
                Timber.d("Response code: " + response.code());
                if (response.body() == null) {
                    Timber.e("No routes found, make sure you set the right user and access token.");
                    return;
                } else if (response.body().routes().size() < 1) {
                    Timber.e("No routes found");
                    return;
                }

// Get the directions route
                currentRoute = response.body().routes().get(0);
                polyline = PolylineUtils.decode(currentRoute.geometry(),PRECISION_6);
// Make a toast which displays the route's distance
                Toast.makeText(New_Ride_Activity.this,currentRoute.distance().toString(), Toast.LENGTH_SHORT).show();

                if (mapboxMap != null) {
                    mapboxMap.getStyle(new Style.OnStyleLoaded() {
                        @Override
                        public void onStyleLoaded(@NonNull Style style) {

// Retrieve and update the source designated for showing the directions route
                            GeoJsonSource source = style.getSourceAs(ROUTE_SOURCE_ID);

// Create a LineString with the directions route's geometry and
// reset the GeoJSON source for the route LineLayer source
                            if (source != null) {
                                Timber.d("onResponse: source != null");
                                source.setGeoJson(FeatureCollection.fromFeature(
                                        Feature.fromGeometry(LineString.fromPolyline(currentRoute.geometry(), PRECISION_6))));
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                Timber.e("Error: " + throwable.getMessage());
                Toast.makeText(New_Ride_Activity.this, "Error: " + throwable.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getRoute(final MapboxMap mapboxMap, Point origin, Point destination,List<Point> waypoints) {
        Toast.makeText(New_Ride_Activity.this,String.valueOf(waypoints.size()),Toast.LENGTH_SHORT).show();
        Toast.makeText(New_Ride_Activity.this,String.valueOf(waypoints.get(0).longitude())+String.valueOf(waypoints.get(0).latitude()),Toast.LENGTH_SHORT).show();
        MapboxDirections.Builder direction_builder = MapboxDirections.builder()
                .origin(origin)
                .addWaypoint(waypoints.get(0))
                .destination(destination)
                .overview(DirectionsCriteria.OVERVIEW_FULL)
                .profile(DirectionsCriteria.PROFILE_DRIVING)
                .accessToken("pk.eyJ1IjoiYW5hbmQ5Mjg4IiwiYSI6ImNrNHk2dHJpdDA3dHEzZm82Y2hnY252cjEifQ.W-3fm0taJg_noVA_zzJO7g");
        for (Point waypoint: waypoints){
            direction_builder.addWaypoint(waypoint);
        }
        client = direction_builder.build();
        client.enqueueCall(new Callback<DirectionsResponse>() {
            @Override
            public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
// You can get the generic HTTP info about the response
                Timber.d("Response code: " + response.code());
                if (response.body() == null) {
                    Timber.e("No routes found, make sure you set the right user and access token.");
                    return;
                } else if (response.body().routes().size() < 1) {
                    Timber.e("No routes found");
                    return;
                }

// Get the directions route
                currentRoute = response.body().routes().get(0);
// Make a toast which displays the route's distance
//                this is the list of points
                polyline = PolylineUtils.decode(currentRoute.geometry(),PRECISION_6);
                if (mapboxMap != null) {
                    mapboxMap.getStyle(new Style.OnStyleLoaded() {
                        @Override
                        public void onStyleLoaded(@NonNull Style style) {

// Retrieve and update the source designated for showing the directions route
                            GeoJsonSource source = style.getSourceAs(ROUTE_SOURCE_ID);

// Create a LineString with the directions route's geometry and
// reset the GeoJSON source for the route LineLayer source
                            if (source != null) {
                                Timber.d("onResponse: source != null");
                                source.setGeoJson(FeatureCollection.fromFeature(
                                        Feature.fromGeometry(LineString.fromPolyline(currentRoute.geometry(), PRECISION_6))));
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                Timber.e("Error: " + throwable.getMessage());
                Toast.makeText(New_Ride_Activity.this, "Error: " + throwable.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    // Add the mapView lifecycle to the activity's lifecycle methods
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }
    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }
    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}


