package com.example.go.forbuyer;

import static android.view.View.INVISIBLE;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.go.Class.FB;
import com.example.go.Class.Users;
import com.example.go.Class.text;
import com.example.go.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * The {@code map} class is an activity that displays a map, allowing users to view their
 * current location and a specified destination. It integrates with Google Maps to show markers
 * and draw routes between locations. The class also handles user requests via an alert dialog
 * and manages Firebase authentication and data retrieval.
 */

public class map extends AppCompatActivity implements OnMapReadyCallback {

    private  final  int FINE_PERMISSION_CODE=1;private GoogleMap map ;
    Button btn; FirebaseUser currentUser;String e;
    Location mylocation;String rq;
    Users buy;int t =0;int in =0;
    DatabaseReference myref;
    Intent gi;String address; String city;Intent intent;
    FusedLocationProviderClient fus;

    /**
     * Initializes the activity. Sets up the layout, Firebase authentication,
     * location services, and retrieves necessary data from the intent.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.
     *                           <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_map);
        FirebaseApp Firstapp = FirebaseApp.getInstance("firstProject");
       FirebaseAuth first = FirebaseAuth.getInstance(Firstapp);
        currentUser = first.getCurrentUser();
        btn  = findViewById(R.id.button5);
        intent = new Intent(this, FirebasedatabseService.class);
        gi = getIntent();
        city = gi.getStringExtra("c");
        e= gi.getStringExtra("e");
        rq = gi.getStringExtra("r");
       address =  gi.getStringExtra("a");
        FB.buyer.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data:snapshot.getChildren()){
                    if(data.getValue(Users.class).getEmail().equals(e)){
                    buy = data.getValue(Users.class);
                    t= in;
                    myref = FB.F.getReference("Users/user"+(t+1));}
                    in++;
                }
                in = 0;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        fus = LocationServices.getFusedLocationProviderClient(this);
        getlastloaction();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();


            }
        });
    }


    /**
     * Retrieves the last known location of the device.
     * If location permissions are not granted, requests them from the user.
     * Once the location is obtained, updates the map.
     */
    private void getlastloaction() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},FINE_PERMISSION_CODE);

            return;
        }
        Task<Location> task =  fus.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    mylocation = location;
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    mapFragment.getMapAsync(map.this);
                }

            }
        })
                .addOnFailureListener(e -> {
                    Log.e("LocationError", "Error retrieving location", e);
                });

    }

    /**
     * Called when the map is ready to be used. Initializes the map,
     * and displays the location specified by the address and city.
     *
     * @param googleMap The GoogleMap instance.
     */
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map= googleMap;
        Log.d("MainActivity", "Error: " + address+city+ "Israel");
        showLocationOnMap(address+","+city+", Israel");

    }

    /**
     * Handles the result of a permission request. If granted,
     * retrieves the last known location.
     *
     * @param requestCode The request code passed in {@link #requestPermissions(String[], int)}.
     * @param permissions The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *     which is either {@link android.content.pm.PackageManager#PERMISSION_GRANTED}
     *     or {@link android.content.pm.PackageManager#PERMISSION_DENIED}. Never null.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == FINE_PERMISSION_CODE){
            if
            (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            { getlastloaction(); } else { Log.e("MainActivity", "Permission denied."); }
        }
    }

    /**
     * Displays a location on the map based on a given address. Uses Geocoder
     * to convert the address to LatLng, then adds a marker and moves the camera.
     *
     * @param address The address of the location to display.
     */
    public void showLocationOnMap(String address)
    { Geocoder geocoder = new Geocoder(this);
        List<Address> addresses;
        try
    { addresses = geocoder.getFromLocationName(address, 1);
        Log.d("check", addresses.toString());
        if (addresses.size() > 0)
        { LatLng location = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
            map.addMarker(new MarkerOptions().position(location).title("Your destination"));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
            LatLng sy = new LatLng(mylocation.getLatitude(),mylocation.getLongitude());
            map.addMarker(new MarkerOptions().position(sy).title("My location"));
            map.moveCamera(CameraUpdateFactory.newLatLng(sy));
            getDirections(sy,location);
        } }
    catch (IOException e) { e.printStackTrace(); }
    }

    /**
     * using google map api to get routes by using an url request with key api.
     *
     */
    private void getDirections(LatLng origin, LatLng destination)
    {
        String url = "https://maps.googleapis.com/maps/api/directions/json?origin="+origin.latitude+","+origin.longitude+"&destination="+destination.latitude+","+destination.longitude+"&key=AIzaSyBnimj_K9I8GFNHCjFFF1v0bEI2Qtk--aA";
        Log.d("check", url);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback()
        { @Override public void onFailure(Call call, IOException e)
        { e.printStackTrace();

        } @Override
        public void onResponse(Call call, Response response)
                throws IOException { if (response.isSuccessful())
        { String responseData = response.body().string();
            try { JSONObject json = new JSONObject(responseData);
                JSONArray routes = json.getJSONArray("routes");
                JSONObject route = routes.getJSONObject(0);
                JSONObject overviewPolyline = route.getJSONObject("overview_polyline");
                String encodedPolyline = overviewPolyline.getString("points");
                List<LatLng> points = PolyUtil.decode(encodedPolyline);
                runOnUiThread(() -> {
                    PolylineOptions options = new PolylineOptions().addAll(points).color(Color.BLUE).width(3);
                    map.addPolyline(options);
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(origin, 1));
                });
            } catch (JSONException e) { e.printStackTrace(); } }

        }

        });}
    /**
     * Displays a AlertDialog on the screen based "to sent a request"
     * it sending a message to the seller in the firebase data base
     * and setting up a database lisner if the message is permitted by the seller when is premmited it will send notification.
     *
     */


    private void showAlertDialog()
    { AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("SEND A REQUEST");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
        { @Override public void onClick(DialogInterface dialog, int which)

        {
         String useremail = currentUser.getEmail();
            String msg  = useremail+ "send a request for:"+rq;
            text message = new text(useremail,msg);

            buy.getMsges().add(message);
            myref.setValue(buy);
            String refernce = myref.toString();
            intent.putExtra("myuser",refernce);
            intent.putExtra("msg", message.getContent());
            intent.putExtra("email",message.getemailuser());
            startService(intent);
            btn.setVisibility(INVISIBLE);
            dialog.dismiss(); }

        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        { @Override
        public void onClick(DialogInterface dialog, int which)
        {
        } });
        AlertDialog alertDialog = builder.create();
        alertDialog.show(); }




}
