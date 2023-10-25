package com.example.novemberechonew.Main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.novemberechonew.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize view
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        // Initialize map fragment
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);

        // Async map
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                // When map is loaded
                LatLng hongKong = new LatLng(22.3193, 114.1694);
                LatLng singapore = new LatLng(1.3521, 103.8198);
                LatLng tokyo = new LatLng(35.682839, 139.759455);
                LatLng mumbai = new LatLng(19.0760, 72.8777);

                // Add markers with custom info windows
                googleMap.addMarker(new MarkerOptions()
                        .position(hongKong)
                        .title("Hong Kong")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                        .snippet("Description: Hong Kong is a vibrant city.")
                        .infoWindowAnchor(0.5f, 0.5f));

                googleMap.addMarker(new MarkerOptions()
                        .position(singapore)
                        .title("Singapore")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                        .snippet("Description: Singapore is a diverse and beautiful city.")
                        .infoWindowAnchor(0.5f, 0.5f));

                googleMap.addMarker(new MarkerOptions()
                        .position(tokyo)
                        .title("Tokyo")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                        .snippet("Description: Tokyo is the capital of Japan.")
                        .infoWindowAnchor(0.5f, 0.5f));

                googleMap.addMarker(new MarkerOptions()
                        .position(mumbai)
                        .title("Mumbai")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                        .snippet("Description: Mumbai is a bustling metropolis in India.")
                        .infoWindowAnchor(0.5f, 0.5f));

                // Draw flight routes
                PolylineOptions hongKongToSingapore = new PolylineOptions()
                        .add(hongKong, singapore)
                        .width(5)
                        .color(0xFFFF0000); // Red
                PolylineOptions hongKongToTokyo = new PolylineOptions()
                        .add(hongKong, tokyo)
                        .width(5)
                        .color(0xFFFF0000);
                PolylineOptions hongKongToMumbai = new PolylineOptions()
                        .add(hongKong, mumbai)
                        .width(5)
                        .color(0xFFFF0000);

                // Add flight routes to the map
                Polyline polyline1 = googleMap.addPolyline(hongKongToSingapore);
                Polyline polyline2 = googleMap.addPolyline(hongKongToTokyo);
                Polyline polyline3 = googleMap.addPolyline(hongKongToMumbai);

                googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                    @Override
                    public View getInfoWindow(Marker marker) {
                        return null;
                    }

                    @Override
                    public View getInfoContents(Marker marker) {
                        View infoWindow = getLayoutInflater().inflate(R.layout.z_custom_info_contents, null);

                        // Customize the Info Window content here
                        return infoWindow;
                    }
                });

                googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        // Handle the Info Window click event
                    }
                });

                // Zoom to a suitable level to see all markers and routes
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hongKong, 4));
            }
        });
        // Return view
        return view;
    }
}
