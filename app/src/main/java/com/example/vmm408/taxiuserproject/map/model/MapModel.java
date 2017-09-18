package com.example.vmm408.taxiuserproject.map.model;

import android.location.Address;

import com.example.vmm408.taxiuserproject.map.presenter.AcceptedDriverCallBack;
import com.example.vmm408.taxiuserproject.map.presenter.CurrentOrderCallBack;
import com.example.vmm408.taxiuserproject.models.UserModel;

import java.util.List;

public interface MapModel {
    UserModel getUserProfile();

    void findCurrentOrder(String userId, CurrentOrderCallBack callBack);

    void findOrderAcceptedDriver(String driverId, AcceptedDriverCallBack callBack);

    List<Address> getAddressFromLocation(double latitude, double longitude, int maxResults);

    List<Address> getAddressFromLocationName(String locationName, int maxResults);
}
