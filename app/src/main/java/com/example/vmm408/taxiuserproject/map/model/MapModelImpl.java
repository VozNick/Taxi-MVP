package com.example.vmm408.taxiuserproject.map.model;

import android.location.Address;
import android.location.Geocoder;

import com.example.vmm408.taxiuserproject.App;
import com.example.vmm408.taxiuserproject.DatabaseValueEventListener;
import com.example.vmm408.taxiuserproject.map.presenter.AcceptedDriverCallBack;
import com.example.vmm408.taxiuserproject.map.presenter.CurrentOrderCallBack;
import com.example.vmm408.taxiuserproject.models.OrderModel;
import com.example.vmm408.taxiuserproject.models.UserModel;
import com.example.vmm408.taxiuserproject.utils.PreferenceUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.vmm408.taxiuserproject.constants.FirebaseDataBaseKeys.CURRENT_ORDER_REF_KEY;
import static com.example.vmm408.taxiuserproject.constants.FirebaseDataBaseKeys.USERS_REF_KEY;

public class MapModelImpl implements MapModel {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference;
    private Geocoder geocoder;

    @Override
    public UserModel getUserProfile() {
        return PreferenceUtil.getUserProfileFromShared(App.getAppBaseContext());
    }

    @Override
    public void findCurrentOrder(String userId, CurrentOrderCallBack callBack) {
        reference = database.getReference(CURRENT_ORDER_REF_KEY).child(userId);
        reference.addListenerForSingleValueEvent(new DatabaseValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                callBack.setCurrentOrder(dataSnapshot.getValue(OrderModel.class));
            }
        });
    }

    @Override
    public void findOrderAcceptedDriver(String driverId, AcceptedDriverCallBack callBack) {
        reference = database.getReference(USERS_REF_KEY).child(driverId);
        reference.addListenerForSingleValueEvent(new DatabaseValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                callBack.setOrderAcceptedDriver(dataSnapshot.getValue(UserModel.class));
            }
        });
    }

    @Override
    public List<Address> getAddressFromLocation(double latitude, double longitude, int maxResults) {
        isGeocoderNull();
        try {
            return geocoder.getFromLocation(latitude, longitude, maxResults);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList();
    }

    @Override
    public List<Address> getAddressFromLocationName(String locationName, int maxResults) {
        isGeocoderNull();
        try {
            return geocoder.getFromLocationName(locationName, maxResults);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList();
    }

    private void isGeocoderNull() {
        if (geocoder == null) {
            geocoder = new Geocoder(App.getAppBaseContext(), Locale.getDefault());
        }
    }
}
