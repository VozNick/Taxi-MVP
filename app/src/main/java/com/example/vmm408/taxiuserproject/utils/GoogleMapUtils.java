package com.example.vmm408.taxiuserproject.utils;

import android.location.Address;
import android.location.Location;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class GoogleMapUtils {
    public static CameraUpdate getMyLocation(Location location) {
        return CameraUpdateFactory.newCameraPosition(new CameraPosition(
                new LatLng(location.getLatitude(), location.getLongitude()), 12f, 0f, 0f));
    }

    public static CameraUpdate getMarkerPosition(Address address, GoogleMap map) {
        return CameraUpdateFactory.newCameraPosition(new CameraPosition(initMarkerOptions(address, map).getPosition(), 12f, 0f, 0f));
    }

    public static CameraUpdate getMarkerBoundsPosition(List<Address> addressList, GoogleMap map) {
        return CameraUpdateFactory.newLatLngBounds(getMarkerBounds(addressList, map).build(), 50, 50, 5);
    }

    private static LatLngBounds.Builder getMarkerBounds(List<Address> addressList, GoogleMap map) {
        LatLngBounds.Builder markerBounds = new LatLngBounds.Builder();
        for (int i = 0; i < addressList.size(); i++) {
            markerBounds.include(initMarkerOptions(addressList.get(i), map).getPosition());
        }
        return markerBounds;
    }

    private static MarkerOptions initMarkerOptions(Address address, GoogleMap map) {
        MarkerOptions options = new MarkerOptions()
                .position(new LatLng(address.getLatitude(), address.getLongitude()))
                .title(address.getAddressLine(0));
        map.addMarker(options);
        return options;
    }
}
